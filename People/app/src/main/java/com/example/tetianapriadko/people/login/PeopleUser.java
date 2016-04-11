package com.example.tetianapriadko.people.login;

import com.backendless.BackendlessUser;

public class PeopleUser extends BackendlessUser {
    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public String getName() {
        return (String) super.getProperty("name");
    }

    public void setName(String name) {
        super.setProperty("name", name);
    }
}