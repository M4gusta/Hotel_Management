package com.magusta.HotelRestAPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magusta.HotelRestAPI.models.Employee;
import com.magusta.HotelRestAPI.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private EmployeeController employeeController;

    @Test
    void insertEmployeeTest_validInput() throws Exception {
        Employee employee = new Employee("John", "Carve", 19700101, 20000, "Manager");

        mockMvc.perform(post("/api/employees")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(employee)))
            .andExpect(status().isOk());

    }


    @Test
    void insertEmployeeTest_nullValue() throws Exception {
        Employee employee = new Employee(null, "Carve", 19700101, 20000, "Manager");

        mockMvc.perform(post("/api/employees")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void insertEmployeeTest_mapsToBusinessModel() throws Exception {
        Employee employee = new Employee("John", "Carve", 19700101, 20000, "Manager");

        mockMvc.perform(post("/api/employees")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)));

        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeController, times(1)).insertEmployee(employeeArgumentCaptor.capture());
        assertThat(employeeArgumentCaptor.getValue().getFirstName()).isEqualTo("John");
        assertThat(employeeArgumentCaptor.getValue().getSalary()).isEqualTo(20000);
    }

    @Test
    void updateEmployeeTest_mapsToBusinessModel() throws Exception {
        Employee employee = new Employee("John", "Carve", 19700101, 20000, "Manager");

        mockMvc.perform(put("/api/employees/1")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(employee)));

        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeController, times(1)).updateEmployee(eq(1), employeeArgumentCaptor.capture());
        assertThat(employeeArgumentCaptor.getValue().getFirstName()).isEqualTo("John");
        assertThat(employeeArgumentCaptor.getValue().getSalary()).isEqualTo(20000);
    }

    @Test
    void getEmployeeByIdTest_whenExists() throws Exception {

        Employee employee = new Employee("John", "Carve", 19700101, 20000, "Manager");

        given(employeeController.getEmployeeById(1)).willReturn(employee);

        MockHttpServletResponse response = mockMvc
                .perform(get("/api/employees/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();



        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(employee));
    }

    @Test
    void getAllEmployeesTest_whenExists() throws Exception {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("John", "Carve", 19700101, 20000, "Manager"));
        employees.add(new Employee("John", "Carve", 19700101, 20000, "Manager"));
        employees.add(new Employee("John", "Carve", 19700101, 20000, "Manager"));

        given(employeeController.getAllEmployees()).willReturn(employees);

        MockHttpServletResponse response = mockMvc.perform(get("/api/employees").accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(employees));

    }
    @Test
    void deleteEmployeeTest() throws Exception {

        mockMvc.perform(delete("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}