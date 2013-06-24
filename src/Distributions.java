
public class Distributions {

	public static int PoissonRandomNumber(double lambda){
		RandomGenerator rand = new RandomGenerator();
		int k=0;                          
		int max_k = 1000;           
		double p = rand.nextRand(); 
		double P = Math.exp(-lambda);          
		double sum = P;                     

		if (sum >= p)
			return 0;             
		
		for(k=1; k < max_k;++k) {         
			P*=lambda/(double)k;            
			sum+=P;                         
			if (sum>=p)
				break;              
		}
		return k;                         
	}
	
	public static double ExponentialRandomNumber(double lambda){
		RandomGenerator rand = new RandomGenerator();
		double t;
		double x = rand.nextRand();
		double numinator = -(naturalLog(x) * x);
		return numinator/lambda; 
	}
	
	public static double naturalLog(double x){
		return Math.log10(x) / Math.log10(Math.E);
	}
	
	public static double UniformRandomNumber(){
		return 24*60/200;
	}
	
}
