package multiplethreading.countdownlatchtest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Use CountDownLatch to count down the completed number of multiple threads.
 * The main thread will continue once count number is 0
 * 
 */

public class ShippingConnectivityService {

	// ExecutorService is thread pool, which can help to reuse thread
	private final static ExecutorService executorService = Executors.newFixedThreadPool(5);

	public static void main(String[] args) {

		// init CountDownlatch object with number of thread
		final CountDownLatch latch = new CountDownLatch(3);

		// init different service and shared the same CountDownLatch
		VVService vv = new VVService(latch);
		VTService vt = new VTService(latch);
		TVService tv = new TVService(latch);

		// calculate the start time
		long start = System.currentTimeMillis();

		// execute service with different thread
		executorService.execute(vv);
		executorService.execute(vt);
		executorService.execute(tv);

		// block main thread till all thread are completed
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// calculate the end time
		long end = System.currentTimeMillis();

		System.out.println("time used is : " + (end - start));

		executorService.shutdown();

	}

}

/**
 * Handle business logic for vessel to vessel
 * 
 */
class VVService implements Runnable {

	private CountDownLatch latch;

	public VVService(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				if (i % 2 == 0) {
					System.out.println("VVService >>>" + i);
				}
			}
		} finally {
			// count down number of thread once this thread is completed
			latch.countDown();
		}
	}
}

/**
 * Handle business logic for vessel to train
 * 
 */
class VTService implements Runnable {

	private CountDownLatch latch;

	public VTService(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				if (i % 2 == 0) {
					System.out.println("VTService >>>" + i);
				}
			}
		} finally {
			// count down number of thread once this thread is completed
			latch.countDown();
		}
	}
}

/**
 * Handle business logic for train to vessel
 * 
 */
class TVService implements Runnable {

	private CountDownLatch latch;

	public TVService(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i < 10; i++) {
				if (i % 2 == 0) {
					System.out.println("TVService >>>" + i);
				}
			}
		} finally {
			// count down number of thread once this thread is completed
			latch.countDown();
		}
	}
}