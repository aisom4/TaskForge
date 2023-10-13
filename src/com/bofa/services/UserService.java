package com.bofa.services;

import com.bofa.entities.Role;
import com.bofa.entities.Task;
import com.bofa.entities.User;
import com.bofa.exceptions.InvalidTaskException;
import com.bofa.exceptions.InvalidUserException;
import com.bofa.repos.TaskRepo;
import com.bofa.repos.UserRepo;

public class UserService {

    UserRepo userRepo = new UserRepo();
    TaskRepo taskRepo = new TaskRepo();

    public User registerNewUser(String username, String password, Role role) {
        if (username.length() > 7 && password.length() > 8) {
            User newUser = new User(username, password, role);

            User savedUser = userRepo.save(newUser);
            return savedUser;
        }
        System.out.println("UserName or Password do not meet requirements");
        return null;
    }
    public User getUserByUsername(String username) throws InvalidUserException {
        User retrievedUser = userRepo.getUserByUsername(username);
        if (retrievedUser != null) {
            return retrievedUser;
        } else {
            throw new InvalidUserException("User with username " + username + " does not exist.");
        }
    }

    public User loginUser(String username, String password) throws InvalidUserException {
        User retrievedUser = userRepo.getUserByUsername(username);

        if (retrievedUser != null) {
            if (retrievedUser.getPassword().equals(password)) {
                System.out.println("Log in Successful!");
                return retrievedUser;
            } else {
                throw new InvalidUserException("Username or password do not match our system.");
            }
        } else {
            throw new InvalidUserException("User with username " + username + " is invalid");
        }
    }

    public void updateUserInformation(User userToUpdate) throws InvalidUserException {
        User retrivedUser = userRepo.getById(userToUpdate.getUserId());

        if (retrivedUser != null) {
            userRepo.update(userToUpdate);
        }
    }

    public void checkTask(User user, int taskId) throws InvalidTaskException {
        Task task = taskRepo.getById(taskId);
        user.getTasks().add(task);
        userRepo.update(user);
    }



    public void returnTask(User user, int taskId) throws InvalidTaskException {
        Task taskToComplete = taskRepo.getById(taskId);
        user.getTasks().remove(taskToComplete);
        userRepo.update(user);
    }
}
