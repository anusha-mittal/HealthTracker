package com.example.healthtracker.Models;

public class Users {
    String name,number,pass;

    Users(){
    }

    public Users(String name, String number, String pass) {
        this.name = name;
        this.number = number;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
