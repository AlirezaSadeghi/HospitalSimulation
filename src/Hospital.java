
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class Hospital {

	public static int clock = 0;

	private String name;
	private LinkedBlockingDeque<Patient> patients;
	private RandomGenerator random;
	private HashMap<HospitalPart, Sector> sectors;
	private HashMap<HospitalPart, LinkedBlockingDeque<Patient>> doc_queues;
	private ArrayList<Patient> allPatients;
	private Max max;
	private int docCount;
	private int lamdas[] = { 0, 20, 20, 15, 15, 10, 15, 20, 2880, 2880, 2880 };

	public Hospital(String name) {
		Scanner sc = new Scanner(System.in);
		System.out.println("تعداده دکترارو وارد کن");
		this.docCount = sc.nextInt();
		this.random = new RandomGenerator();
		this.patients = new LinkedBlockingDeque<Patient>();
		this.name = name;
		this.doc_queues = new HashMap<HospitalPart, LinkedBlockingDeque<Patient>>();
		this.sectors = new HashMap<HospitalPart, Sector>();
		int i = 0;
		for (HospitalPart sp : HospitalPart.values()) {
			this.doc_queues.put(sp, new LinkedBlockingDeque<Patient>());
			this.sectors.put(sp, new Sector(sectors.size(), sp, docCount));
			this.sectors.get(sp).setLamda(lamdas[i]);
			i++;
		}
		max = new Max();
		this.allPatients = new ArrayList<>();
	}

	public static void main(String[] args) {
		Hospital hospital = new Hospital("SS-H");
		hospital.init();

	}

	public void init() {
		int simulationTime = this.getSimulTime();
		this.initPatients(simulationTime * 60);
		this.startSimulation(simulationTime * 60);
	}

	private void startSimulation(int time) {

		while (clock < time) {
			if (patients.size() > 0
					&& clock == patients.peek().getEntranceTime()) {
				ArrayList<Patient> new_patients = new ArrayList<>();

				while (patients.size() > 0
						&& patients.peek().getEntranceTime() == clock) {

					new_patients.add(patients.poll());
					allPatients.add(new_patients.get(new_patients.size() - 1));
					for (Patient patient : new_patients) {

						Hospital.log("umad too hospital", clock, patient,
								"khow Hospital", -10);
					}
				}

				for (Patient patient : new_patients) {
					Part part = random.getPart();
					switch (part) {
					case PUBLIC:
						doPublicStuff(patient);
						break;
					case URGANCE:
						doUrganceStuff(patient);
						break;
					case SPECIALIST:
						doSpecialistStuff(patient);
						break;

					default:
						System.out.println("never here ");

					}
				}
			}
			clock++;
			handlePublicQueue();
			handleSpecialistQueues();
			handleUrganceQueue();
		}

		this.finishing();
		
	}

	private void doPublicStuff(Patient patient) {
		LinkedBlockingDeque<Patient> q = doc_queues.get(HospitalPart.PUBLIC);
		q.add(patient);
		log("enter " + "Public" + " Q", clock, patient, "Public", doc_queues
				.get(HospitalPart.PUBLIC).size());
		if (q.size() > max.getMaxQ()) {
			max.setMaxQ(q.size());
			max.setHp(HospitalPart.PUBLIC);
		}
		handlePublicQueue();
	}

	private void handlePublicQueue() {

		HospitalPart hp = HospitalPart.PUBLIC;
		Sector sec = sectors.get(hp);
		LinkedBlockingDeque<Patient> q = doc_queues.get(hp);

		if (sec.hasBusyDoc()) {
			int[] tmp = sec.getFinishTimes();
			for (int i = 0; i < sec.getDocCount(); i++) {
				if (tmp[i] == clock) {
					Patient p = sec.goOut(i, "Public", q.size());
				}
			}
		}
		if (!sec.isBusy() && q.size() > 0) {
			Patient p = q.poll();

			PublicWorkType part = random.getPublicWorkType();
			int docId = 0;
			switch (part) {
			case PRESCRIBE:
				Hospital.log("biya tooo Public", clock, p, hp.toString(),
						q.size());
				docId = sec.setPatient(p);
				sec.setFinish_time(15 + clock, docId);
				break;
			case HOSPTILIZE:
				Hospital.log("biya tooo Public", clock, p, hp.toString(),
						q.size());
				docId = sec.setPatient(p);
				sec.setFinish_time(25 + clock, docId);
				break;
			case REFER:
				Hospital.log("biya tooo Public", clock, p, hp.toString(),
						q.size());
				doSpecialistStuff(p);
				break;

			default:
				System.out.println("never2");
			}
		}
	}

	private void doSpecialistStuff(Patient patient) {
		HospitalPart part = random.getSpecialist();
		LinkedBlockingDeque<Patient> q = doc_queues.get(part);
		q.add(patient);
		log("enter " + part + " Q", clock, patient, part.toString(), q.size());
		if (q.size() > max.getMaxQ()) {
			max.setMaxQ(q.size());
			max.setHp(part);
		}

		handleSpecialistQueues();

	}

	private void handleSpecialistQueues() {
		for (int i = 1; i < 8; i++) {
			HospitalPart hp = HospitalPart.values()[i];
			Sector sec = sectors.get(hp);
			LinkedBlockingDeque<Patient> q = doc_queues.get(hp);

			if (sec.hasBusyDoc()) {
				int[] tmp = sec.getFinishTimes();
				for (int j = 0; j < sec.getDocCount(); j++) {
					if (tmp[j] == clock) {
						Patient patientOut = sec.goOut(j, hp.toString(),
								q.size());
						// System.out.println(q.size());
						if (q.size() > 0) {
							// for (Patient patient : q) {
							// System.out.println("@@@@@@@@@@@@@");
							// System.out.println(patient.getPatientId());
							// if ( sec.isBusy() )
							// System.out.println("WtF ?!");
							// }
						}

						SpecialistWorkType workType = random
								.getSpecialistWorkType();
						if (workType == SpecialistWorkType.HOSPITALIZE) {
							// HospitalPart hp2 = random.getHospitalizeRoom();
							doUrganceStuff(patientOut);
							// doc_queues.get(hp2).add(patientOut);
						}
					}
				}
			}
//			System.out.println("HERE::   " + sec.isBusy() + " " + q.size());
			if (q.size() > 0 && !sec.isBusy()) {
				Patient p = q.poll();
				int docId = sec.setPatient(p);
				// TODO expo
				 sec.setFinish_time((int)Distributions.ExponentialRandomNumber(sec.lamda ), docId);
				// + clock);
//				sec.setFinish_time(10 + clock, docId);
				Hospital.log("biya tooo " + hp, clock, p, hp.toString(),
						q.size());

			}
		}
	}

	private void doUrganceStuff(Patient patient) {
		HospitalPart part = random.getHospitalizeRoom();
		LinkedBlockingDeque<Patient> q = doc_queues.get(part);
		q.add(patient);
		patient.setHospitalized(true);
		log("enter " + part + " Q", clock, patient, part.toString(), doc_queues
				.get(part).size());

		if (q.size() > max.getMaxQ()) {
			max.setMaxQ(q.size());
			max.setHp(part);
		}

		handleUrganceQueue();
	}

	private void handleUrganceQueue() {
		for (int i = 8; i < 11; i++) {
			HospitalPart hp = HospitalPart.values()[i];
			Sector sec = sectors.get(hp);
			LinkedBlockingDeque<Patient> q = doc_queues.get(hp);

			if (sec.hasBusyDoc()) {
				int[] tmp = sec.getFinishTimes();
				for (int j = 0; j < sec.getDocCount(); j++)
					if (tmp[j] == clock) {
						Patient patientOut = sec.goOut(j, "Urgance", q.size());
					}
			}

			if (q.size() > 0 && !sec.isBusy()) {
				Patient p = q.poll();
				int docId = sec.setPatient(p);
				// TODO expo
				 sec.setFinish_time((int)Distributions.ExponentialRandomNumber(sec.lamda), docId);
				// + clock);
//				sec.setFinish_time(30 + clock, docId);
				Hospital.log("biya tooo " + hp, clock, p, hp.toString(),
						q.size());
			}
		}
	}

	private void initPatients(int totalTime) {
		int time = 0;
		while (time < totalTime) {
			Patient p = new Patient(this.patients.size(), time);
			this.patients.add(p);
			time += this.generateNextCustomerEntranceInterval();
		}
	}

	private int getSimulTime() {
		Scanner sc = new Scanner(System.in);
		System.out
				.println("با سلام \nلطفا زمان مد نظر خود را برای طول مدت شبیه سازی سیستم وارد نمایید:");
		System.out
				.println("لطفا توجه داشته باشید که زمان مورد نظر به ساعت می باشد");
		int simulationTime = sc.nextInt();
		System.out.println("خیلی ممنون. پس شد " + simulationTime + "ساعت ");
		return simulationTime;
	}

	/*
	 * Change To Poisson
	 */
	private double generateNextCustomerEntranceInterval() {
//		 return Distributions.PoissonRandomNumber(200/(24*60));
		return Distributions.UniformRandomNumber();
	}

	static public void log(String event, int clock, Patient p, String qName,
			int qSize) {
		System.out.println(String.format("%-12s", clock)
				+ String.format("%-30s", event)
				+ String.format("%-5s", p.getPatientId())
				+ String.format("%-30s", qName)
				+ String.format("%-30s", Integer.toString(qSize)));

	}

	private double average() {
		double sum = 0, number = 0;
		for (Patient p : this.allPatients) {
			if (!p.isHospitalized()) {
				sum += p.getTotalTime();
				number++;
			}
		}
		return sum / number;
	}

	private double variance() {
		double ave = average();
		double var = 0, sum = 0, number = 0;
		for (Patient p : this.allPatients) {
			if (!p.isHospitalized()) {
				sum += Math.pow(ave - p.getTotalTime(), 2.0);
				number++;
			}
		}

		var = sum / number;
		return var;
	}

	private void finishing() {
		System.out.println("Max Q was for " + max.getHp().toString()
				+ " section with " + max.getMaxQ());
		System.out.println("Ave for patients is " + average() + " var is "
				+ variance());
		
		System.out.println(allPatients.size());
	}
}
