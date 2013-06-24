public class Distributions {

	
	public static void main(String[] args) {
		double d = 0.05 ;
		double sum = 0;
		for (int i = 0; i < 60; i++) {
			double t = ExponentialRandomNumber(d);
			sum += t;
			System.out.println(t);
		}
		
		System.out.println(sum/100);
		
	}
	
	
	public static int PoissonRandomNumber(double lambda){
		RandomGenerator rand = new RandomGenerator();
		
		double l = Math.pow(Math.E, -lambda)  , p = 1;
		int k = 0;
		do{
			k++;
			p = rand.nextRand() * p;
		}while(p > l);
		return k-1;
	}
	
	public static double ExponentialRandomNumber(double lambda){
		RandomGenerator rand = new RandomGenerator();
		double x = rand.nextRand();
		double numinator = -(naturalLog(x) );
		return numinator/lambda; 
	}
	
	public static double naturalLog(double x){
		return Math.log10(x) / Math.log10(Math.E);
	}
	
	public static double UniformRandomNumber(){
		return 24*60/200;
	}
	
}
