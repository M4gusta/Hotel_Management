package com.magusta.HotelRestAPI.controllers;


import com.magusta.HotelRestAPI.models.Employee;
import com.magusta.HotelRestAPI.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {


    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") int empId){
        return employeeRepository.findById(empId).orElseThrow();
    }

    @GetMapping("/employees/?fname={fname}&lname={lname}")
    public List<Employee> findEmployeeByName(@PathVariable(value = "fname") String fName, @PathVariable(value = "lname") String lName){
        return employeeRepository.findByFirstNameAndLastName(fName, lName);
    }


    @PostMapping("/employees")
    public void insertEmployee(@Valid @RequestBody Employee employee){
        employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public void updateEmployee(@PathVariable(value = "id")int empId, @Valid @RequestBody Employee employee){
        //Employee employee1 = employeeRepository.findById(empId).orElseThrow();
        employee.setEmployeeId(empId);

        employeeRepository.save(employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int empId){
        Employee employee = employeeRepository.findById(empId).orElseThrow();

        employeeRepository.delete(employee);

        return ResponseEntity.ok().build();
    }
}
