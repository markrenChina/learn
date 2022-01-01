package com.ccand99.domain;

import java.util.Properties;

public class User {
    private String id;

    private int age;

    private City city;

    private Company company;

    private Properties context;

    private String contextAsText;

    /**
     * 必须要有空的
     */
    public User() {
    }

    public User(String id) {
        this();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public static User createUser() {
        User user = new User();
        user.id = "method";
        return user;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Properties getContext() {
        return context;
    }

    public void setContext(Properties context) {
        this.context = context;
    }

    public String getContextAsText() {
        return contextAsText;
    }

    public void setContextAsText(String contextAsText) {
        this.contextAsText = contextAsText;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", city=" + city +
                ", company=" + company +
                ", context=" + context +
                ", contextAsText='" + contextAsText + '\'' +
                '}';
    }
}
