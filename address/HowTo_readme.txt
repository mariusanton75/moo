Steps.

1. Clone or download.

2. In command line change folder to address

3 execute "mvn clean package" command to compile and execute the unit tests and package the application

4. change folder to "target"

5. execute "address-0.0.1.jar"   ( jar is executable"

6. Open a browser and call the following URLs
	- http://localhost:8080/allcustomers    			to show a list of short informations about customers
	- http://localhost:8080/names						to show a list of distinct names of customers
	- http://localhost:8080/customersname/{name}   		- change {name} with one of the names found on the 
list of names to show a list of customers having the same family name.
Example: "http://localhost:8080/customersname/Danaila"


	- http://localhost:8080/customer/{id} 			- change {id} with the desired ID to display all the informations about one customer
as example "http://localhost:8080/customer/26"

	- http://localhost:8080/shutdown 				to shutdown the application


