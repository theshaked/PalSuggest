package com.example.palsuggest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private final String username;
    private final String password;
    private final String email;
    private List<String> likes;
    private List<String> friends;
    private final boolean admin;

    public User(Map<String, Object> newUser) {
        username = (String) newUser.get("username");
        password = (String) newUser.get("password");
        email = (String) newUser.get("email");
        likes = (List<String>) newUser.get("likes");
        friends = (List<String>) newUser.get("friends");
        admin = (boolean) newUser.get("admin");
    }

    public String getUsername() {
        return username;
    }

    public List<String> getLikes() {
        return likes;
    }

    public List<String> getFriends() {
        return friends;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void AddFriend(String suggesterName) {
        friends.add(suggesterName);
    }

    public void RemoveFriend(String suggesterName) {
        friends.remove(suggesterName);
    }

    public void AddLike(String productName) {
        likes.add(productName);
    }

    public void RemoveLike(String productName) {
        likes.remove(productName);
    }


}
