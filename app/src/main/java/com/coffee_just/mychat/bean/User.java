package com.coffee_just.mychat.bean;

import java.io.Serializable;
import java.util.UUID;

public class User  implements Serializable {
    @Override
    public String toString() {
        return  name + '|' + id ;
    }

    private static User mUser;
        private String name;

    public void setId(UUID id) {
        this.id = id;
    }

    private UUID  id;

        public static User InstanceUser(){
            if(mUser==null)
            {
                mUser = new User();
            }
            return mUser;
        }
    private User() {
        this.id = UUID.randomUUID();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

}
