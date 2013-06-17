import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;


public class Hospital {

	int clock;
	String name;
	LinkedBlockingDeque <Patient> patients;
	RandomGenerator random;
	
	public Hospital(String name){
		this.random = new RandomGenerator();
		this.patients = new LinkedBlockingDeque<Patient>();
		this.clock = 0;
		this.name = name;
	}
	
	public static void main(String[] args) {
		Hospital hospital = new Hospital("SS-H");
		hospital.init();
		
	}
	
	public void init(){
		int simulationTime = this.getSimulTime();
		this.initPatients(simulationTime * 60);
		this.startSimulation(simulationTime * 60);
	}
	
	
	private void startSimulation(int time){
		
		while(this.clock < time){
			if(this.clock == patients.peek().getEntranceTime()){
				// TODO -> GO ON 
				random.getPart();
			}
		}
	}
	
	private void initPatients(int totalTime){
		int time = 0;
		while(time < totalTime){
			Patient p = new Patient(this.patients.size(), time);
			this.patients.add(p);
			time += this.generateNextCustomerEntranceInterval();
		}
	}
	
	private int getSimulTime(){
		Scanner sc = new Scanner(System.in);
		System.out.println("با سلام \nلطفا زمان مد نظر خود را برای طول مدت شبیه سازی سیستم وارد نمایید:");
		System.out.println("لطفا توجه داشته باشید که زمان مورد نظر به ساعت می باشد");
		int simulationTime = sc.nextInt();
		System.out.println("خیلی ممنون. پس شد " + simulationTime + "ساعت ");
		return simulationTime;
	}

	/*
	 * Change To Poisson
	 */
	private double generateNextCustomerEntranceInterval(){
//		return Distributions.PoissonRandomNumber(200/24*600);
		return Distributions.UniformRandomNumber();
	}
	
}
