package com.coffee_just.mychat.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.UUID;

public class User extends LitePalSupport implements Serializable {
    @Override
    public String toString() {
        return name ;
    }

    private static User mUser;
    private String name;

    public String getId() {
        return uuid;
    }

    private String uuid;

    public static User InstanceUser() {
        if (mUser == null) {
            mUser = new User();
        }
        return mUser;
    }

    private User() {
        this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public void setName(String name) {
        this.name = name;
    }

}
