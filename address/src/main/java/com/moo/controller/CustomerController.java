package com.moo.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moo.exception.CustomerNotFoundException;
import com.moo.mock.database.MockDatabase;
import com.moo.model.Customer;

@RestController
public class CustomerController {

	@Autowired
	private MockDatabase mockDatabase;

	@GetMapping(value = "/customersname/{name}")
	public ResponseEntity<List<Customer>> getCustomerByName(@PathVariable String name, HttpServletResponse res) {
		List<Customer> result = Optional.ofNullable(mockDatabase.getCustomerListByName(name))
				.orElseThrow(() -> new CustomerNotFoundException(name));
		return ResponseEntity.ok().body(result);
	}


	@GetMapping(value = "/customer/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id, HttpServletResponse res) {
		Customer result = Optional.ofNullable(mockDatabase.getCustomerById(id))
				.orElseThrow(() -> new CustomerNotFoundException(id));
		return ResponseEntity.ok().body(result);

	}

	@RequestMapping("/allcustomers")
	public List<Customer> getCustomers() {
		return mockDatabase.getCustomers();

	}

	@RequestMapping("/names")
	public Set<String> getNames() {
		return mockDatabase.getNames();
	}
}
