package com.example;

import java.util.List;

public class User {
    public String name;
    public int age;
    public String email;
    public Address primaryAddress;
    public List<Address> addresses;
    public List<Phone> phones;
    public List<Order> orders;
    
    public User() {
    }
    
    public static class Address {
        public String street;
        public String city;
        public String state;
        public String zip;
        public String country;
        
        public Address() {
        }
    }
    
    public static class Phone {
        public String type;
        public String number;
        
        public Phone() {
        }
    }
    
    public static class Order {
        public String orderId;
        public String product;
        public double amount;
        public String date;
        
        public Order() {
        }
    }
}