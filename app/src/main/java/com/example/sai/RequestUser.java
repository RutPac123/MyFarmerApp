package com.example.sai;

import com.example.sai.com.Model.Order;

import java.util.List;

public class RequestUser {
    private String address;
    private String total;
    private List<Order> items;
    private String email;

    public RequestUser(){

    }

    public RequestUser(String address, String total,String email, List<Order> items) {
        this.address = address;
        this.total = total;
        this.email = email;
        this.items = items;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getItems() {
        return items;
    }

    public void setItems(List<Order> items) {
        this.items = items;
    }
}
