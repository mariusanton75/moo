package com.moo.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moo.mock.database.MockDatabase;
import com.moo.model.Customer;

@RestController
public class CustomerController {

	@Autowired
	private MockDatabase mockDatabase;

	@GetMapping(value = "/customer/{name}")
	public ResponseEntity<Customer> getCustomerByName(@PathVariable String name,
            HttpServletResponse res) {
		
		Customer result = mockDatabase.getCustomerByName(name);
		
		if(result == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok().body(result);
		}

	}

	@RequestMapping("/customers")
	public List<Customer> getCustomers() {
		return mockDatabase.getCustomers();

	}

	@RequestMapping("/names")
	public Set<String> getNames() {
		return mockDatabase.getNames();
	}
}
