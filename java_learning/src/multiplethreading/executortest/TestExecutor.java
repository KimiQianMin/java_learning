package multiplethreading.executortest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestExecutor {

	public static void main(String[] args) {

		ExecutorService pool = Executors.newFixedThreadPool(5);

//		Ticket t = new Ticket();
//		List<Future<String>> list = new ArrayList<>();
//
//		try {
//
//			for (int i = 0; i < 10; i++) {
//				list.add(pool.submit(t, "finished"));
//			}
//
//		} finally {
//			pool.shutdown(); // shutdown the pool
//		}
//
//		for (int i = 0; i < list.size(); i++) {
//			try {
//				System.out.println(list.get(i).get());
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
//		}

		Ticket2 t2 = new Ticket2();
		List<Future<Integer>> list = new ArrayList<>();

		try {

			for (int i = 0; i < 10; i++) {
				Future<Integer> furture = pool.submit(t2);
				list.add(furture);
			}

		} finally {
			pool.shutdown(); // shutdown the pool
		}

		for (int i = 0; i < list.size(); i++) {
			try {
				System.out.println(list.get(i).get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

	}

}

class Ticket implements Runnable {

	private int count = 100;

	private Lock lock = new ReentrantLock();

	@Override
	public void run() {
		lock.lock();

		try {
			while (count > 0) {
				System.out.println(Thread.currentThread().getName() + " " + --count);
			}
		} finally {
			lock.unlock();
		}
	}

}

class Ticket2 implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		int outcome = 0;
		for (int i = 0; i < 100; i++) {
			outcome = outcome + i;
		}
		System.out.println(Thread.currentThread().getName() + " " + outcome);
		return outcome;
	}

}
