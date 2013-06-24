
public class Sector {
	
	private HospitalPart type;
	private int id ;
	private boolean [] docStates_IsBusy;
	private int [] finishTimes;
	private Patient [] patients;
	public double lamda;
	private int docCount;
	
	
	public Sector(int size , HospitalPart t, int docCount) {
		this.id = size;
		this.type = t;
		this.docCount = docCount;
		this.docStates_IsBusy = new boolean [docCount];
		this.finishTimes = new int [docCount];
		this.patients = new Patient[docCount]; 
	}
	
	public Patient getPatient(int docId) {
		return this.patients[docId];
	}
	public double getLamda() {
		return lamda;
	}
	public void setLamda(double lamda) {
		this.lamda = 1 / lamda;
	}
	
	public void setDocPatient(int docId, Patient value){
		this.patients[docId] = value;
	}
	
	public int setPatient(Patient patient) {
		int docIndex = this.setBusy();
		this.patients[docIndex] = patient;
		return docIndex;
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
		for(boolean b : this.docStates_IsBusy)
			if(!b)
				return false;
		return true;
	}
	
	public int setBusy() {
		for(int i=0; i<this.docStates_IsBusy.length;i++)
			if(!this.docStates_IsBusy[i])
			{
				this.docStates_IsBusy[i] = true;
				return i;
			}
		return -1;
	}
	
	public void setDocBusy(int docId, boolean value){
		this.docStates_IsBusy[docId] = value;
	}
	
	public int getFinish_time(int docId) {
		return this.finishTimes[docId];
	}
	
	public int[] getFinishTimes(){
		return this.finishTimes;
	}
	
	public boolean hasBusyDoc(){
		for(boolean b : this.docStates_IsBusy)
			if(b)
				return true;
		return false;
	}
	
	public void setFinish_time(int finishTime, int docId){
		this.finishTimes[docId] = finishTime;
	}
	
	public Patient goOut(int docId, String str, int size){
		Patient p = this.getPatient(docId);
		p.setExitTime(Hospital.clock);
		Hospital.log("gomsho birun az " + str, Hospital.clock, this.patients[docId] , "0" , size );
		
		this.setDocPatient(docId, null);
		this.setFinish_time(-1, docId);
		this.setDocBusy(docId, false);

		return p;
	}
	
	public int getDocCount(){
		return this.docCount;
	}
}
