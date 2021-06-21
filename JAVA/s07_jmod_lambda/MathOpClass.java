package eu.ase.fi_lambda;

public class MathOpClass {
	public int operator(int a, int b, MathOperation mathOperationLambdaObj)
	{
		return mathOperationLambdaObj.operation(a, b);
		
	}
}
