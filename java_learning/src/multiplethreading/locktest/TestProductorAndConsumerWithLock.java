package multiplethreading.locktest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductorAndConsumerWithLock {

	public static void main(String[] args) {

		ClerkWithLock clerk = new ClerkWithLock();

		ProductorWithLock producter = new ProductorWithLock(clerk);
		ConsumerWithLock consumer = new ConsumerWithLock(clerk);

		new Thread(producter, "Product A").start();
		new Thread(consumer, "Consumer B").start();

		// new Thread(producter, "Product AA").start();
		// new Thread(consumer, "Consumer BB").start();
	}

}

class ClerkWithLock {

	private int product = 0;

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	// buy in
	public void get() {
		lock.lock();

		try {
			while (product >= 1) {
				System.out.println(Thread.currentThread().getName() + " : " + "product is full");

				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			System.out.println(Thread.currentThread().getName() + " : " + ++product);
			condition.signalAll();

		} finally {
			lock.unlock();
		}

	}

	// sell out
	public void sale() {
		lock.lock();
		
		try {
			while (product <= 0) {
				System.out.println(Thread.currentThread().getName() + " : " + "product out of stock");

				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " : " + --product);
			condition.signalAll();

		} finally {
			lock.unlock();
		}
	}

}

class ProductorWithLock implements Runnable {
	private ClerkWithLock clerk;

	public ProductorWithLock(ClerkWithLock clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clerk.get();
		}
	}

}

class ConsumerWithLock implements Runnable {
	private ClerkWithLock clerk;

	public ConsumerWithLock(ClerkWithLock clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}

}