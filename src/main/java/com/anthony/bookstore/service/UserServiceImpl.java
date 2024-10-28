package com.anthony.bookstore.service;

import com.anthony.bookstore.model.dao.User;
import com.anthony.bookstore.utility.Utilities;

public class UserServiceImpl implements UserService{

    @Override
    public User newUser(String name, Integer age, String address) {

        User user = new User();
        if(Utilities.isNull(name)||Utilities.isNull(age)||Utilities.isNull(address)){
            return null;
        }
        else{

    
            return user;
        }

    }
    
}
