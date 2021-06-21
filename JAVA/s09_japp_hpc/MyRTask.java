package eu.ase.threads.parallel;

public class MyRTask implements Runnable {

	private float[] vi1;
	private float[] vi2;
	private float[] vres;

	private int startIdx;
	private int stopIdx;

	public MyRTask(float[] vi1, float[] vi2, float[] vres, int startIdx, int stopIdx) {
		this.vi1 = vi1;
		this.vi2 = vi2;
		this.vres = vres;
		this.startIdx = startIdx;
		this.stopIdx = stopIdx;
	}

	@Override
	public void run() {
		for(int i=startIdx; i<stopIdx; i++)
			this.vres[i]=this.vi1[i]+this.vi2[i];
	}

}
