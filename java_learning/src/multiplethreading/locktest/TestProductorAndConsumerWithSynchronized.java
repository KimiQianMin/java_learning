package multiplethreading.locktest;

public class TestProductorAndConsumerWithSynchronized {

	public static void main(String[] args) {

		Clerk clerk = new Clerk();

		Productor producter = new Productor(clerk);
		Consumer consumer = new Consumer(clerk);

		new Thread(producter, "Product A").start();
		new Thread(consumer, "Consumer B").start();

		new Thread(producter, "Product AA").start();
		new Thread(consumer, "Consumer BB").start();
	}

}

class Clerk {

	private int product = 0;

	// buy in
	public synchronized void get() {
		while (product >= 1) {
			System.out.println(Thread.currentThread().getName() + " : " + "product is full");

			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} // else {
		System.out.println(Thread.currentThread().getName() + " : " + ++product);
		this.notifyAll();
		// }
	}

	// sell out
	public synchronized void sale() {
		while (product <= 0) {
			System.out.println(Thread.currentThread().getName() + " : " + "product out of stock");

			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // else {
		System.out.println(Thread.currentThread().getName() + " : " + --product);
		this.notifyAll();
		// }
	}

}

class Productor implements Runnable {
	private Clerk clerk;

	public Productor(Clerk clerk) {
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

class Consumer implements Runnable {
	private Clerk clerk;

	public Consumer(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}

}