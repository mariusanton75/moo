package com.moo.mock.database;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.moo.model.Address;
import com.moo.model.Customer;


@Component
public class MockDatabase {
	private static final List<Customer> LIST = Arrays.asList(
			new Customer(1, "John", "Smith", new Address(1L, 32, "Ploughley Rd", "TIPPACOTT", "EX35 8WY", "UK"),
					"z5evwmpn1y@fakemailgenerator.net", "+44077 0516 1180"),
			new Customer(7, "Armand", "Bardin", new Address(2L, 113, "Maidstone Road", "WESSINGTON", "DE55 9TU", "UK"),
					"r9mz386lv6@fakemailgenerator.net", "+44077 6354 8111"),
			new Customer(2, "Alexia", "Hollin", new Address(3L, 101, "Old Edinburgh Road", "BELTON", "LE12 7TR", "UK"),
					"szamkdril5l@groupbuff.com", "+44077 4937 8712"),
			new Customer(3, "Patricia", "Golay", new Address(4L, 124, "Tadcaster Rd", "PILLOWELL", "GL15 5SD", "UK"),
					"yji6ce27dce@meantinc.com", "+44079 1882 0715"),
			new Customer(21, "Quinton", "Kephart", new Address(5L, 74, "City Walls Rd", "CLIPSTON", "LE16 2HZ", "UK"),
					"first@email.com", "+441231231231"));

	private static final Map<String, Customer> customerByName = LIST.stream()
			.collect(Collectors.toMap(x -> x.getLastname(), x -> x));

	public List<Customer> getCustomers() {
		return Collections.unmodifiableList(LIST);
	}

	public Customer getCustomerByName(String name) {
//		return customerByName.getOrDefault(name, new Customer(0, "Not found", null, null, null, null));
		return customerByName.get(name);
	}
	
	public Set<String> getNames(){
		return Collections.unmodifiableSet(customerByName.keySet());
	}
}
