package com.example.onlytest.models;

public class User {
    private String email;
    private String phone;
    private String fullName;
    private String role;
    private String site_id;


    public User(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public User(String email, String phone, String fullName, String role ,String site_id) {
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.role = role;
        this.site_id = site_id;
    }

    public User(String email, String phone, String fullName, String role) {
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.role = role;
    }

    public User(String email, String fullName, String phone) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getrole() {
        return role;
    }

    public void setrole(String role) {
        this.role = role;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }
}
