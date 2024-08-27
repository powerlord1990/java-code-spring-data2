package com.company.projection;

public interface  EmployeeProjection {

    default String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    String getFirstName();

    String getLastName();

    String getPosition();

    String getDepartmentName();
}
