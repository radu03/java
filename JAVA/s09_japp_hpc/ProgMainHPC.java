package eu.ase.threads.parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProgMainHPC {

	public static void main(String[] args) {
		int NTH = 2;
		float[] v1 = new float[40_000_000];
		float[] v2 = new float[40_000_000];
		float[] vr = new float[40_000_000];

		for (int i = 0; i < v1.length; i++) {
			if (i < 10_000_000) {
				v1[i] = 1;
				v2[i] = 2;
			}
			if (i > 10_000_000 && i < 20_000_000) {
				v1[i] = 3;
				v2[i] = 5;
			}
			if (i > 20_000_000) {
				v1[i] = 9;
				v2[i] = 6;
			}
			vr[i] = 0.0f;

		}

		long startT = 0, stopT = 0;
		int startIdx = 0, stopIdx = 0;

		// 1.1 seq
		startT = System.currentTimeMillis();

		for (int i = 0; i < v1.length; i++)
			vr[i] = v1[i] + v2[i];

		stopT = System.currentTimeMillis();
		System.out.println("1. Arrays sum seq = " + vr[vr.length - 1] + " took ms " + (stopT - startT));

		// 2.1 std multi-thread
		startT = System.currentTimeMillis();
		MyRTask[] vtask = new MyRTask[NTH]; // one class for every thread
		Thread[] vth = new Thread[NTH];

		for (int it = 0; it < NTH; it++) { // for every thread
			startIdx = it * (v1.length / NTH);
			stopIdx = (it + 1) * (v1.length / NTH) - 1;
			vtask[it] = new MyRTask(v1, v2, vr, startIdx, stopIdx);
			vth[it] = new Thread(vtask[it]);
		}

		for (int i = 0; i < NTH; i++)
			vth[i].start();
		for (int i = 0; i < NTH; i++)
			try {
				vth[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		stopT = System.currentTimeMillis();
		System.out.println("2. Arrays sum std multi-thd = " + vr[vr.length - 1] + " took ms " + (stopT - startT));

		// 2.2 Executor - service
		startT = System.currentTimeMillis();
		ExecutorService execThPool=Executors.newFixedThreadPool(NTH);
		
		for (int it = 0; it < NTH; it++) { // for every thread
			startIdx = it * (v1.length / NTH);
			stopIdx = (it + 1) * (v1.length / NTH) - 1;
			vtask[it] = new MyRTask(v1, v2, vr, startIdx, stopIdx);
			execThPool.execute(vtask[it]);
		}
		
		execThPool.shutdown();
		try {
			execThPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		stopT = System.currentTimeMillis();
		System.out.println("2.2 Arrays sum Executor Service = " + vr[vr.length - 1] + " took ms " + (stopT - startT));
		
		
		//2.3 Lambda
		startT = System.currentTimeMillis();
		ExecutorService execThPoolL=Executors.newFixedThreadPool(NTH);
		Runnable[] vtaskl = new Runnable[NTH];
		
		for (int it = 0; it < NTH; it++) { // for every thread
			startIdx = it * (v1.length / NTH);
			stopIdx = (it + 1) * (v1.length / NTH) - 1;
			vtaskl[it] = () -> {
			
			for(int i=0; i<vr.length; i++)
				vr[i]=v1[i]+v2[i];	
			};
			execThPoolL.execute(vtask[it]);
		}
		
		execThPool.shutdown();
		try {
			execThPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		stopT = System.currentTimeMillis();
		System.out.println("2.3 Arrays sum Executor Service + Lambda = " + vr[vr.length - 1] + " took ms " + (stopT - startT));
	}

}
