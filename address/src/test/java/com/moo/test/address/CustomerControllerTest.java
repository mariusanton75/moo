package com.moo.test.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moo.controller.CustomerController;
import com.moo.model.Address;
import com.moo.model.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CustomerControllerTest {

	private static final Logger LOGGER = Logger.getLogger(CustomerControllerTest.class.getName());

	private static final String NEW_LINE = "\n";

	private TestRestTemplate testRestTemplate = new TestRestTemplate();
	private HttpHeaders headers = new HttpHeaders();

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private CustomerController customerController;

	private MockMvc mockMvc = null;

	@Before
	public void before() throws Exception {
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void after() throws Exception {
		mockMvc = null;
	}

	@Test
	public void contextLoads() {
		assertThat(customerController).isNotNull();
	}

	/**
	 * Test the customer received as JSON string
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetCustomerByIdAsString() throws Exception {
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = testRestTemplate.exchange(createURLWithPort("/customer/7"), HttpMethod.GET,
				entity, String.class);

		String expected = "{\"id\":7,\"firstname\":\"Armand\",\"lastname\":\"Bardin\"," + "\"address\":"
				+ "{\"id\":2,\"number\":113,\"street\":\"Maidstone Road\",\"city\":\"WESSINGTON\",\"zip\":\"DE55 9TU\",\"country\":\"UK\"},"
				+ "\"email\":\"r9mz386lv6@fakemailgenerator.net\",\"phone\":\"+44077 6354 8111\"}";

		printLog(response.getBody());
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	/**
	 * Test the customers received as array of entities
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetCustomersByNameAsEntity() throws Exception {

		ResponseEntity<Customer[]> response = testRestTemplate.getForEntity(createURLWithPort("/customersname/Danaila"),
				Customer[].class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		Customer[] array = response.getBody();

		List<Customer> resultList = Arrays.asList(new Customer(25, "Razvan", "Danaila", null, null, null),
				new Customer(26, "Lucia", "Danaila", null, null, null));

		for (Customer customer : array) {
			this.printLog(customer.toString());
			assertTrue(resultList.contains(customer));
		}
	}

	/**
	 * Test the customers received as entity
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetCustomerByIdAsEntity() throws Exception {

		ResponseEntity<Customer> response = testRestTemplate.getForEntity(createURLWithPort("/customer/7"),
				Customer.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		Customer customerExpected = new Customer(7, "Armand", "Bardin",
				new Address(2L, 113, "Maidstone Road", "WESSINGTON", "DE55 9TU", "UK"),
				"r9mz386lv6@fakemailgenerator.net", "+44077 6354 8111");
		assertEquals(customerExpected, response.getBody());
	}

	/**
	 * Test the search for a non existing customer
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRetrieveRestTemplateFail() throws Exception {

		printLog("START TEST ERROR");

		Object result = this.callToRestService(HttpMethod.GET, createURLWithPort("/customer/78"), null, Customer.class);

		assertNull(result);
		printLog("END ERROR TEST");
	}

	// Testing Happy Path scenario
	// use mockMvc way of testing REST services
	@Test
	public void testCustomerFound() throws Exception {
		final MockHttpServletRequestBuilder builder = get(createURLWithPort("/customer/{id}"), 25);
		final ResultActions result = mockMvc.perform(builder);

		result.andExpect(status().isOk());

		String contentAsString = result.andReturn().getResponse().getContentAsString();

		Customer response = objectMapper.readValue(contentAsString, Customer.class);
		printLog("\n\n The result is: " + response);
	}

	// Testing Error scenario
	// use mockMvc way of testing REST services
	@Test
	public void testCustomerNotFound() throws Exception {
		final MockHttpServletRequestBuilder builder = get(createURLWithPort("/customer/{id}"), 100);
		final ResultActions result = mockMvc.perform(builder);
		result.andExpect(status().isNotFound());

		printLog("\n\n The error message is: " + result.andReturn().getResolvedException().getMessage());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:8080" + uri;
	}

	/**
	 * Specific method to treat different kind of errors
	 * 
	 * 
	 * 
	 * @param httpMethod
	 * @param url
	 * @param requestObject
	 * @param responseObject
	 * @return
	 */
	private Object callToRestService(HttpMethod httpMethod, String url, Object requestObject, Class<?> responseObject) {

		printLog("Url : " + url);

		try {
			printLog("callToRestService Request : " + objectMapper.writeValueAsString(requestObject));

			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Object> entity = new HttpEntity<>(requestObject, requestHeaders);

			long start = System.currentTimeMillis();

			ResponseEntity<?> responseEntity = restTemplate.exchange(url, httpMethod, entity, responseObject);

			printLog("callToRestService Status : " + responseEntity.getStatusCodeValue());

			printLog("callToRestService Body : " + objectMapper.writeValueAsString(responseEntity.getBody()));

			long elapsedTime = System.currentTimeMillis() - start;
			printLog("callToRestService Execution time: " + elapsedTime + " Milliseconds)");

			if (responseEntity.getStatusCodeValue() == 200 && responseEntity.getBody() != null) {
				return responseEntity.getBody();
			}

		} catch (HttpClientErrorException exception) {
			printLog("callToRestService HttpClientErrorException :" + exception.getResponseBodyAsString());
		} catch (HttpStatusCodeException exception) {
			printLog("callToRestService HttpClientErrorException :" + exception.getResponseBodyAsString());
		} catch (JsonProcessingException exception) {
			printLog("JsonProcessingException Error :" + exception.getMessage());
		}
		return null;
	}

	private void printLog(String message) {
		LOGGER.info(new StringBuilder().append(NEW_LINE).append(message).append(NEW_LINE).toString());
	}
}
