package multiplethreading.synchronizedtest;

public class TicketDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Ticket ticket = new Ticket();

		Thread t1 = new Thread(ticket);
		Thread t2 = new Thread(ticket);
		Thread t3 = new Thread(ticket);
		Thread t4 = new Thread(ticket);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}

}

class Ticket implements Runnable {

	private int count = 100;
	boolean contine = true;

	@Override
	public void run() {

		while (contine) {
			bookTicket();
		}
	}

	synchronized void bookTicket() {
		System.out.printf("%s%n", Thread.currentThread().getName());
		if (count > 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.printf("%s = %s%n", Thread.currentThread().getName(), count--);
		} else {
			contine = false;
		}
	}

}
