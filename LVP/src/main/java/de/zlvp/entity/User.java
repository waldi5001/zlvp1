package de.zlvp.entity;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    private Set<String> roles = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getGroups() {
        return roles;
    }
}
