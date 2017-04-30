package com.example.zainuel.services;

/**
 * Created by reddy on 29/4/17.
 */

public class ProjectObj {
    String status;
    String address;
    String assignedEmployee;
    String employeeRating;
    String date;
    String time;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(String assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public String getEmployeeRating() {
        return employeeRating;
    }

    public void setEmployeeRating(String employeeRating) {
        this.employeeRating = employeeRating;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
