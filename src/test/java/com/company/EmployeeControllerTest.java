package com.company;

import com.company.controller.EmployeeController;
import com.company.entity.Department;
import com.company.entity.Employee;
import com.company.projection.EmployeeProjection;
import com.company.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Department testDepartment;
    private Employee testEmployee;

    @BeforeEach
    public void setup() {
        testDepartment = new Department("IT");
        testEmployee = new Employee("John", "Doe", "Developer", 60000.0, testDepartment);
    }

    @Test
    public void createEmployee_WhenValidEmployee_ShouldReturnCreatedEmployee() throws Exception {
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(testEmployee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value("Developer"));
    }

    @Test
    public void getEmployeeById_WhenEmployeeExists_ShouldReturnEmployee() throws Exception {
        when(employeeService.getEmployeeById(anyLong())).thenReturn(testEmployee);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value("Developer"));
    }

    @Test
    public void getAllEmployees_ShouldReturnEmployeeList() throws Exception {
        EmployeeProjection projection = new EmployeeProjection() {
            @Override
            public String getFirstName() { return "John"; }
            @Override
            public String getLastName() { return "Doe"; }
            @Override
            public String getPosition() { return "Developer"; }
            @Override
            public String getDepartmentName() { return "IT"; }
        };
        List<EmployeeProjection> projections = Arrays.asList(projection);
        when(employeeService.getAllEmployees()).thenReturn(projections);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].position").value("Developer"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].departmentName").value("IT"));
    }

    @Test
    public void updateEmployee_WhenValidEmployee_ShouldReturnUpdatedEmployee() throws Exception {
        when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn(testEmployee);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value("Developer"));
    }

    @Test
    public void deleteEmployee_WhenEmployeeExists_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/1"))
                .andExpect(status().isNoContent());
    }
}