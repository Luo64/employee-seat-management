package com.example.seat.dto;

import org.springframework.aop.target.EmptyTargetSource;

public class EmployeeResponse {
    private String empId;
    
    public EmployeeResponse(){}

    public EmployeeResponse(String empId){
        this.empId=empId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
