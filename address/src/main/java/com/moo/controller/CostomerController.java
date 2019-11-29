package com.moo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moo.model.Address;
import com.moo.model.Customer;

@RestController
public class CostomerController {
	@RequestMapping("/customer")
	  public Customer getCustomerByName(@RequestParam(value="name") String name) {
	    Customer customer = new Customer();
	    customer.setFirstname("John");
	    customer.setLastname(name);
	    customer.setId(20);
	    
	    Address address = new Address();
	    address.setId(2500L);
	    address.setCity("Bucov");
	    address.setCountry("Romania");
	    address.setNumber(20);
	    address.setProvince("Prahova");
	    address.setStreet("Al.I. Cuza");
	    address.setZip("107113");
	    
	    customer.setAddress(address);
	    customer.setEmail("ionvasalie@yahoo.com");
	    customer.setPhone("+40.722.367.503");
	    
	    
		return customer;
	  }
}
