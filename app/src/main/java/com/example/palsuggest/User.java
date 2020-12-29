package com.example.palsuggest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private final String username;
    private final String password;
    private final String email;
    private final List<String> likes;
    private final List<String> friends;
    private final boolean admin;

    public User(Map<String, Object> newUser) {
        username = (String) newUser.get("username");
        password = (String) newUser.get("password");
        email = (String) newUser.get("email");
        likes = (List<String>) newUser.get("likes");
        friends = (List<String>) newUser.get("friends");
        admin = (boolean) newUser.get("admin");
    }
}
