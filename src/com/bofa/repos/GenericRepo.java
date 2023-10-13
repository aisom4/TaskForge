package com.bofa.repos;

import com.bofa.exceptions.InvalidTaskException;
import com.bofa.exceptions.InvalidUserException;

import java.util.List;

public interface GenericRepo<A> {
    List<A> getAll();
    A getById(int objectId) throws Exception;
    A save(A objectToSave);
    void update(A objectToUpdate);
    void delete(int objectId);
}


