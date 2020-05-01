package multiplethreading.callabletest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * To test callable interface, which will be third way to start new thread
 */
public class TestCallable {

	public static void main(String[] args) {

		ThreadDemo td = new ThreadDemo();
		FutureTask<Integer> result = new FutureTask<Integer>(td);
		new Thread(result).start();

		ThreadDemo1 td1 = new ThreadDemo1();
		FutureTask<Integer> result1 = new FutureTask<Integer>(td1);
		new Thread(result1).start();

		try {
			Integer sum = result.get();
			Integer sum1 = result1.get();
			// System.out.println(TestCallable.class.getSimpleName() + " sum >>> " + sum);
			// System.out.println(TestCallable.class.getSimpleName() + " sum1 >>> " + sum1);

			Integer total = sum + sum1;
			System.out.println(TestCallable.class.getSimpleName() + " total >>> " + total);
			System.out.println("-----------------------------------------------------");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

}

class ThreadDemo implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			sum = sum + i;
		}
		System.out.println(this.getClass().getSimpleName() + " sum >>> " + sum);
		return sum;
	}

}

class ThreadDemo1 implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for (int i = 0; i < 100; i++) {
			sum = sum + i;
		}
		System.out.println(this.getClass().getSimpleName() + " sum >>> " + sum);
		return sum;
	}

}