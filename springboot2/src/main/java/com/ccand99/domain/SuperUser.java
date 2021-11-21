package com.ccand99.domain;

import com.ccand99.ioc.Super;

@Super
public class SuperUser extends User{
    private String address;
    public SuperUser(String id) {
        super(id);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}
