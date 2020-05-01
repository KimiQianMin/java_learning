package multiplethreading.locktest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * To test Lock interface, which is explicit locking way(synchronized is
 * implicit)
 */
public class TestLock {

	public static void main(String[] args) {

		Ticket ticket = new Ticket();

		Thread t1 = new Thread(ticket);
		t1.start();

		Thread t2 = new Thread(ticket);
		t2.start();

		Thread t3 = new Thread(ticket);
		t3.start();

	}

}

class Ticket implements Runnable {

	private int count = 100;

	private Lock lock = new ReentrantLock();

	@Override
	public void run() {

		lock.lock(); // To lock

		try {
			while (count > 0) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(this.getClass().getSimpleName() + " count " + --count);
			}
		} finally {
			lock.unlock(); // release lock ***
		}
	}

}
