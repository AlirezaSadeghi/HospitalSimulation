
public class Patient {

	private int patientId;
	private boolean isHospitalized;
	private int entranceTime;
	private int exitTime;
	private int totalTime;
	
	public Patient(){
	}
	
	public Patient(int patientId, int entranceTime){
		this.patientId = patientId;
		this.entranceTime = entranceTime;
		this.isHospitalized = false;
		this.totalTime  = 0;
	}

	public int getTotalTime(){
		return this.totalTime;
	}
	
	public boolean isHospitalized() {
		return isHospitalized;
	}

	public void setHospitalized(boolean isHospitalized) {
		this.isHospitalized = isHospitalized;
	}

	public int getEntranceTime() {
		return entranceTime;
	}

	public void setEntranceTime(int entranceTime) {
		this.entranceTime = entranceTime;
	}

	public int getExitTime() {
		
		return exitTime;
	}

	public void setExitTime(int exitTime) {
		this.totalTime = exitTime - this.getEntranceTime() ;
		this.exitTime = exitTime;
	}

	public int getPatientId() {
		return patientId;
	}
}
