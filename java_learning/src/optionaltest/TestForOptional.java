package optionaltest;

import java.util.Optional;

public class TestForOptional {

	public static void main(String[] args) {
		Customer customer = new Customer();
		System.out.println(getCourseName2(customer));
	}

	public static String getCourseName(Customer customer) {
		return customer.getCourse().getCourseName();
	}
	
	public static String getCourseName2(Customer customer) {
		return Optional.ofNullable(customer)
				.map(cust -> cust.getCourse())
				.map(cour -> cour.getCourseName())
				.orElseThrow(() -> new IllegalArgumentException("param is invalid"));
	}
	
}
