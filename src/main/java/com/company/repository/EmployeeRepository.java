package com.company.repository;

import com.company.entity.Employee;
import com.company.projection.EmployeeProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<EmployeeProjection> findAllBy();
}
