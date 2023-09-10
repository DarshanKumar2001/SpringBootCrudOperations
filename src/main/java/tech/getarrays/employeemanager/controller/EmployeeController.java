package tech.getarrays.employeemanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import tech.getarrays.employeemanager.model.Employee;

import tech.getarrays.employeemanager.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		List<Employee> employees = employeeService.findAllEmployees();
		return new ResponseEntity<>(employees,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id){
		Employee employee = employeeService.findEmployeeById(id);
		return new ResponseEntity<>(employee,HttpStatus.OK);
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
		Employee newEmployee = employeeService.addEmployee(employee);
		return new ResponseEntity<>(newEmployee,HttpStatus.CREATED);
		
	}
	
//	@PutMapping("/update")
//	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
//		Employee updateEmployee = employeeService.updateEmployee(employee);
//		return new ResponseEntity<>(updateEmployee,HttpStatus.OK);
//		
//	}
	
	@PutMapping("/update")
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
	    // Check if an employee with the same name already exists
	    Employee existingEmployee = employeeService.findEmployeeByName(employee.getName());

	    if (existingEmployee != null && !existingEmployee.getId().equals(employee.getId())) {
	        // An employee with the same name already exists, and it's not the current employee being updated
	        // You can choose to return an error response or handle this situation as needed
	        return new ResponseEntity<>(HttpStatus.CONFLICT); // Conflict status code (HTTP 409) indicates a conflict or duplicate resource.
	    }

	    // No conflict, proceed with the update
	    Employee updatedEmployee = employeeService.updateEmployee(employee);

	    return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
	}

	@Transactional
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") Long id){
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	

}
