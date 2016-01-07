package com.valeriymiller.vktop.model;

/**
 * Created by Михаил on 24.10.2015.
 */
public class User {

    private int id;
    private String vkId;
    private String lastName;
    private String firstName;

    public User() {
    }

    public User(String vkId, String lastName, String firstName) {
        this.vkId = vkId;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVkId() {
        return vkId;
    }

    public void setVkId(String vkId) {
        this.vkId = vkId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
