package com.ccand99.domain;

public class User {
    private String id;

    private int age;

    private City city;

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

    public static User createUser(){
        User user = new User();
        user.id ="method";
        return user;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", age=" + age +
                ", city=" + city +
                '}';
    }
}
