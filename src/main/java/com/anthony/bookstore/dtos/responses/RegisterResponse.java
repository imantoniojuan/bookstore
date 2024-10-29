package com.anthony.bookstore.dtos.responses;

import java.time.LocalDateTime;

import com.anthony.bookstore.entities.User;

public class RegisterResponse extends ResponseStatus{
    private String email;
    private LocalDateTime createdAt;

    public RegisterResponse(){
        
    }

    public RegisterResponse(User user){
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
