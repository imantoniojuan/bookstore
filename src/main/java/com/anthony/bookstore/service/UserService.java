package com.anthony.bookstore.service;

import com.anthony.bookstore.model.dao.User;

public interface UserService {
    User newUser (String name, Integer age, String address);
}
