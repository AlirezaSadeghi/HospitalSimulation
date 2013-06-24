
public class RandomGenerator {

	long m = (long) Math.pow(2.0 , 14);
	long time = System.currentTimeMillis();
	int a = (int) (4*((time % 500)) + 1) ;
	int c = (int) (2 * (time % 10000 ) + 1) ;
	static int x = 1 ;
	
	
	double nextRand(){
		x = (int) (( a * x + c ) % m) ;
		return (double)x/m;
	}
	
	/**
	 * kodom motekhases bayad tane lashesho bebare 
	 * @return
	 */
	public HospitalPart getSpecialist(){
		double t = nextRand();
		if ( t < 0.15 )
			return HospitalPart.CARDIO;
		else if ( t < 0.3 )
			return HospitalPart.NEURO;
		else if ( t < 0.5 )
			return HospitalPart.INTERNAL;
		else if ( t < 0.63 )
			return HospitalPart.ORTHOPED;
		else if ( t < 0.75 )
			return HospitalPart.BREAK_FXIER;
		else if ( t < 0.88 )
			return HospitalPart.EAR_MOUTH_NOSE;
		else 
			return HospitalPart.OPTOMETER;
	}
	
	/**
	 * taraf age raft umomi karesh che tipiye 
	 * @return
	 */
	public PublicWorkType getPublicWorkType() {
		double t = nextRand();
		if ( t < 0.5 )
			return PublicWorkType.PRESCRIBE;
		else if ( t < 0.8 )
			return PublicWorkType.HOSPTILIZE;
		else 
			return PublicWorkType.REFER;
	}
	
	/**
	 * moshakhas mikone bimar miyad too koja bayad bere , omomi , motekhases ya urgans
	 * @return be tartib umomi ,motekhases , urgans
	 */
	public Part getPart() {
		double t = nextRand();
		if ( t < 0.4 )
			return Part.PUBLIC;
		else if ( t < 0.9 )
			return Part.SPECIALIST;
		else 
			return Part.URGANCE;
	}
	
	/**
	 * motekhases mige chi kar kone , tajvize daro ya bastari shodan 
	 * @return
	 */
	SpecialistWorkType getSpecialistWorkType(){
		double t = nextRand();
		if ( t < 0.8 )
			return SpecialistWorkType.PRESCRIBE;
		else 
			return SpecialistWorkType.HOSPITALIZE;
	}
	
	/**
	 * too bimarestan chetori bastari mishe , otagh amal , ICU ya CCU 
	 * @return
	 */
	HospitalPart getHospitalizeRoom() {
		double t = nextRand();
		if ( t < 0.5 )
			return HospitalPart.SURGERY;
		else if ( t < 0.75 )
			return HospitalPart.ICU;
		else 
			return HospitalPart.CCU;
	}
	
}