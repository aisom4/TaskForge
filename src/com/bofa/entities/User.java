package com.bofa.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User {

    public static int userIdCount = 0;

    //Attributes of user
    private int userId;
    private String username;
    private String password;
    private Role role;
    private List<Task> tasks;


// Constructor begins

    public User(){
        this.userId = ++userIdCount;
        this.tasks = new ArrayList<>();
//        this.tasks = null;  // Fix and replace to this.tasks = newArrayList <>();
    }


  public User(String username, String password, Role type){
        this.userId = ++ userIdCount;
        this.username = username;
        this.password = password;
        this.role = type;
      this.tasks = new ArrayList<>();

  }


  //Getters and Setters
    public int getUserId(){
        return this.userId;

    }

    public void setUserId(int userId){
        this.userId = userId;
    }


    //Getting the username

    public String getUsername(){
        return this.username;
    }

    //Setting a username
    public void setUsername(String username){
        this.username = username; //Can use an if statement to set and exception on username # of chars
    }

    //Getting and Setting Passwords

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Role getRole(){
        return this.role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public List<Task> getTasks(){
       return this.tasks;
    }

    @Override
    public String toString(){
        return "Users [" +
                ", userId= '" + this.userId +
                ", username= '" + this.username +
                ", password='" + this.password +
                ", type= '" + this.role +
                ", tasks= " + this.tasks +
                ']';
    }


}
