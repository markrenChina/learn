package com.ccand99.domain;

public class User {
  private String id;

  private int age;

  public User(String id) {
    super();
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "User{" +
            "id='" + id + '\'' +
            ", age=" + age +
            '}';
  }
}
