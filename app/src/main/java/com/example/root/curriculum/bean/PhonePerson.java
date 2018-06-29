package com.example.root.curriculum.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 手机的系统联系人实体类
 */
public class PhonePerson implements Serializable {

    private String id;
    private String userName;
    private String address;
    private String email;
    private List<String> numbers;   //可能不止一个号码


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
}
