package com.bofa.services;

import com.bofa.entities.ClaimStatus;
import com.bofa.entities.Status;
import com.bofa.entities.Task;

import com.bofa.entities.User;
import com.bofa.exceptions.InvalidTaskException;
import com.bofa.repos.TaskRepo;

import java.time.LocalDate;
import java.util.List;

public class TaskService {

    private TaskRepo taskRepo;


    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<Task> getAllTasks() {
        return taskRepo.getAll();
    }

    public List<Task> getUnclaimedTasks() {
        return taskRepo.getUnclaimedTasks();
    }

    public Task createTask(String description, LocalDate dueDate, User assignedUser) {
        Task newTask = new Task();
        newTask.setDescription(description);
        newTask.setDueDate(dueDate);
        newTask.setAssignedUser(assignedUser);
        newTask.setOwner(assignedUser.getUsername());
        newTask.setClaimStatus(ClaimStatus.CLAIMED);
        return taskRepo.save(newTask);
    }
    public Task createTask(String description, LocalDate dueDate) {
        Task newTask = new Task();
        newTask.setDescription(description);
        newTask.setDueDate(dueDate);
        return taskRepo.save(newTask);
    }

    public void updateTaskStatus(int taskId, Status status) throws InvalidTaskException {
        Task task = taskRepo.getById(taskId);
        if (task != null) {
            task.setStatus(status);
            taskRepo.update(task);
        } else {
            throw new InvalidTaskException("Task not found.", "Task with ID " + taskId + " not found");
        }
    }





    public void claimTask(User user, int taskId) throws InvalidTaskException {
        Task task = taskRepo.getById(taskId);

        if (task != null) {
            if (task.getStatus() != Status.COMPLETED && task.getClaimStatus() != ClaimStatus.CLAIMED) {
                task.setClaimStatus(ClaimStatus.CLAIMED);
                task.setAssignedUser(user);
                task.updateOwner(user.getUsername()); // Update the owner to the claiming user's username
                user.getTasks().add(task);
                taskRepo.update(task);
            } else {
                throw new InvalidTaskException("Task is already claimed or completed.", "Task with ID " + taskId + " is claimed or completed");
            }
        } else {
            throw new InvalidTaskException("Task not found.", "Task with ID " + taskId + " not found");
        }
    }
}

