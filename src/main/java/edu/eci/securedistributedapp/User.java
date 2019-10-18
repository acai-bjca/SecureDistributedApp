/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.securedistributedapp;

/**
 *
 * @author amalia
 */
public class User {
    private String name;
    private String user;
    private String email;
    private String passwHash;

    public User(String name, String user, String email, String pasw) {
        this.name = name;
        this.user = user;
        this.email = email;
        this.passwHash = pasw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswHash() {
        return passwHash;
    }

    public void setPasswHash(String pasw) {
        this.passwHash = pasw;
    }
    
    
}
