package com.cdboost.mongodb.model;

import java.io.Serializable;

public class UserRole implements Serializable{

    private Long id;
    private Long roleID;
    private Long userID;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "UserMapRole [id=" + id + ", roleID=" + roleID + ", userID=" + userID + "]";
    }

}