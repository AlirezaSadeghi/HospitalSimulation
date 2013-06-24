
public class Sector {
	
	private HospitalPart type;
	private int id ;
	private boolean isBusy;
	private int finish_time;
	private Patient patient;
	public double lamda;
	
	
	public Sector(int size , HospitalPart t, int docCount) {
		this.id = size;
		this.type = t;
		this.finish_time = -1;
		this.docCount = docCount;
		this.isBusy = false;
	}
	
	public Patient getPatient() {
		return patient;
	}
	public double getLamda() {
		return lamda;
	}
	public void setLamda(double lamda) {
		this.lamda = 1 / lamda;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public HospitalPart getType() {
		return type;
	}
	public void setType(HospitalPart type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isBusy() {
		return isBusy;
	}
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}
	public int getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(int finish_time) {
		this.setBusy(true);
		this.finish_time = finish_time;
	}
	public Patient goOut(String str, int size){
		Patient p = this.getPatient();
		p.setExitTime(Hospital.clock);
		Hospital.log("gomsho birun az " + str, Hospital.clock, this.patient , "0" , size );
		
		this.setPatient(null);
		this.setFinish_time(-1);
		this.setBusy(false);

		return p;

	}
}
