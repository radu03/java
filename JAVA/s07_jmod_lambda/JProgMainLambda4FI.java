package eu.ase.lambdausesfi;

import eu.ase.fi_lambda.GreetingService;
import eu.ase.fi_lambda.MathOpClass;
import eu.ase.fi_lambda.MathOperation;

public class JProgMainLambda4FI {

	public static void main(String[] args) {
		MathOpClass testMath = new MathOpClass();
		
		MathOperation add = (int x, int y) -> {
			return x + y;
		};
		MathOperation sub =  (w, z)-> w - z;
		MathOperation div = (w, z)-> w/z;
		MathOperation mul = (w, z)-> w*z;
		MathOperation pow = (w, z)-> {
			double r=Math.pow(w,z);
			return (int)r;};
		
		System.out.println("27 + 3 = "+ testMath.operator(27,3,add));
		System.out.println("27 - 3 = "+ testMath.operator(27,3,sub));
		System.out.println("27 / 3 = "+ testMath.operator(27,3,div));
		System.out.println("27 * 3 = "+ testMath.operator(27,3,mul));
		System.out.println("27 ^ 3 = "+ testMath.operator(27,3,pow));
		
		GreetingService gs1 = (String msgUm) -> {
			System.out.println("Hello "+msgUm+"!");
			
			return;
		};
		
		gs1.sayMessage("V");
		
		GreetingService gs2 = m -> System.out.println("Bonjour "+m+"!");
		gs2.sayMessage("E");

	}

}
