package com.bofa.repos;

import com.bofa.entities.User;

import com.bofa.exceptions.InvalidTaskException;
import com.bofa.exceptions.InvalidUserException;
import com.bofa.utils.MockDB;

import java.util.List;

public class UserRepo implements GenericRepo<User> {
    @Override
    public List<User> getAll() {
        return MockDB.registeredUsers;
    }

    @Override
    public User getById(int userId) throws InvalidUserException {
        for (User user : MockDB.registeredUsers) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        throw new InvalidUserException("User not found with ID");
    }

    @Override
    public User save(User userToSave) {
        MockDB.registeredUsers.add(userToSave);
        return userToSave;
    }

    @Override
    public void update(User userToUpdate) {
        int index = -1;
        for (int i = 0; i < MockDB.registeredUsers.size(); i++) {
            if (MockDB.registeredUsers.get(i).getUserId() == userToUpdate.getUserId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            MockDB.registeredUsers.set(index, userToUpdate);
        }
    }

    @Override
    public void delete(int userId) {
        MockDB.registeredUsers.removeIf(user -> user.getUserId() == userId);
    }

    public User getUserByUsername(String username) throws InvalidUserException {
        for (User user : MockDB.registeredUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new InvalidUserException("User not found with username: " + username);
    }
}
