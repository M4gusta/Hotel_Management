package com.magusta.HotelRestAPI.controllers;


import com.magusta.HotelRestAPI.models.Employee;
import com.magusta.HotelRestAPI.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable(value = "id") int empId){
        return employeeRepository.findById(empId).orElseThrow();
    }

    @GetMapping("/employees/{fname}&{lname}")
    public List<Employee> findByFirstnameAndLastname(@PathVariable(value = "fname") String fName, @PathVariable(value = "lname") String lName){
        return employeeRepository.findByFirstNameAndLastName(fName, lName);
    }


    @PostMapping("/employees")
    public Employee insertEmployee(@Validated @RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @PutMapping("/employees/{id}")
    public Employee updateEmployee(@PathVariable(value = "id")int empId, @Validated @RequestBody Employee employee){
        Employee employee1 = employeeRepository.findById(empId).orElseThrow();
        employee.setEmployeeId(employee1.getEmployeeId());

        return employeeRepository.save(employee);
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int empId){
        Employee employee = employeeRepository.findById(empId).orElseThrow();

        employeeRepository.delete(employee);

        return ResponseEntity.ok().build();
    }
}