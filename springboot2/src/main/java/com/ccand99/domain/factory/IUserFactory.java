package com.ccand99.domain.factory;

import com.ccand99.domain.User;

public interface IUserFactory {

    default User createUser(){
        return new User("DefaultUserFactory");
    };

}
