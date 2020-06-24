package com.magusta.HotelRestAPI.repositories;

import com.magusta.HotelRestAPI.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Integer> {
    //wyszukuje pracowników po nazwisku i imieniu
    Employee findByFirstNameAndLastName(String fName, String lName);
}
