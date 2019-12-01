package com.moo.mock.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
					"first@email.com", "+441231231231"),
			new Customer(25, "Razvan", "Danaila", new Address(6L, 61, "St John's Rd", "Arlesey", "SG15 6ST", "UK"),
					"razvan@spotyphoto.com", "+4498989898989"),
			new Customer(26, "Lucia", "Danaila", new Address(6L, 61, "St John's Rd", "Arlesey", "SG15 6ST", "UK"),
					"lucia@spotyphoto.com", "+44967676767679"));

	private static final Map<String, List<Customer>> listByName = LIST.stream()
			.collect(Collectors.groupingBy(Customer::getLastname, Collectors.toCollection(ArrayList::new)));

	private static final Map<Integer, Customer> customerById = LIST.stream()
			.collect(Collectors.toMap(x -> x.getId(), x -> x));

// 	return only id, first name and last name of the customer
	public List<Customer> getCustomers() {
		return getListLimitedInformation(LIST);
	}

	// 	return only id, first name and last name of the customer
	public List<Customer> getCustomerListByName(String name) {
		return getListLimitedInformation(listByName.get(name));
	}

	public Set<String> getNames() {
		return Collections.unmodifiableSet(listByName.keySet());
	}

	public Customer getCustomerById(int id) {
		return customerById.get(id);
	}

	/**
	 * clone the elements of the list received as a parameter
	 * and duplicate only ID, First name and Last name.
	 *  
	 * @param listToBeFiltered
	 * @return cloned objects but with limited informations
	 */
	private List<Customer> getListLimitedInformation(List<Customer> listToBeFiltered) {
		 // just to make the java code more difficult to read                   ;)
		return Collections.unmodifiableList(Optional.ofNullable(listToBeFiltered).orElse(Collections.emptyList()).stream()
				.map(c -> new Customer(c.getId(), c.getFirstname(), c.getLastname(), null, null, null))
				.collect(Collectors.toList()));
	}

}
