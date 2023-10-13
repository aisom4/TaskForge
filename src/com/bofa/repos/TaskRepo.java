//package com.bofa.repos;
//
//
//import com.bofa.entities.Task;
//import com.bofa.exceptions.InvalidTaskException;
//import com.bofa.utils.MockDB;
//
//import java.util.List;
//
//public class TaskRepo implements GenericRepo<Task> {
//
//    @Override
//    public List<Task> getAll() {
//        return MockDB.taskPool;
//    }
//
//    @Override
//    public Task getById(int taskId) throws InvalidTaskException {
//        for (Task task : MockDB.taskPool) {
//            if (task.getTaskId() == taskId) {
//                return task;
//            }
//        }
//        throw new InvalidTaskException("Task not found", "Task with ID " + taskId + " not found");
//    }
//
//    @Override
//    public Task save(Task taskToSave) {
//        MockDB.taskPool.add(taskToSave);
//        return taskToSave;
//    }
//
//    @Override
//    public void update(Task taskToUpdate) {
//        int index = -1;
//        for (int i = 0; i < MockDB.taskPool.size(); i++) {
//            if (MockDB.taskPool.get(i).getTaskId() == taskToUpdate.getTaskId()) {
//                index = i;
//                break;
//            }
//        }
//        if (index != -1) {
//            MockDB.taskPool.set(index, taskToUpdate);
//        }
//    }
//
//    @Override
//    public void delete(int taskId) {
//        MockDB.taskPool.removeIf(task -> task.getTaskId() == taskId);
//    }
//}




//----------------------------------------------------------------------------------------------------------------------
package com.bofa.repos;

import com.bofa.entities.Task;
import com.bofa.exceptions.InvalidTaskException;
import com.bofa.utils.MockDB;

import java.util.ArrayList;
import java.util.List;

public class TaskRepo implements GenericRepo<Task> {

    @Override
    public List<Task> getAll() {
        return MockDB.taskPool;
    }

    @Override
    public Task getById(int taskId) throws InvalidTaskException {
        for (Task task : MockDB.taskPool) {
            if (task.getTaskId() == taskId) {
                return task;
            }
        }
        throw new InvalidTaskException("Task not found", "Task with ID " + taskId + " not found");
    }

    @Override
    public Task save(Task taskToSave) {
        MockDB.taskPool.add(taskToSave);
        return taskToSave;
    }

    @Override
    public void update(Task taskToUpdate) {
        for (int i = 0; i < MockDB.taskPool.size(); i++) {
            Task task = MockDB.taskPool.get(i);
            if (task.getTaskId() == taskToUpdate.getTaskId()) {
                MockDB.taskPool.set(i, taskToUpdate);
                return;
            }
        }
    }

    @Override
    public void delete(int taskId) {
        MockDB.taskPool.removeIf(task -> task.getTaskId() == taskId);
    }

    // Additional method to get unclaimed tasks
    public List<Task> getUnclaimedTasks() {
        List<Task> unclaimedTasks = new ArrayList<>();
        for (Task task : MockDB.taskPool) {
            if (task.isOpenToClaim()) {
                unclaimedTasks.add(task);
            }
        }
        return unclaimedTasks;
    }
}