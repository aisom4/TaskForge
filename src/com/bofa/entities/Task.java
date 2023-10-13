//package com.bofa.entities;
//
//
//
//import java.time.LocalDate;
//
//
//
//public class Task { //this is a constructor ? that includes what a task needs
//
//   private static int idTracker = 0;
//    private final int taskId;
//
//    private String description;
//    private String owner;
//
//    private Status status;
//   private boolean openToClaim;
//
//    //What else does a task need???
//
//    private LocalDate dueDate;
//
//    public Task(){
//        this.taskId = ++idTracker;
//        this.openToClaim = true;
//
//
//    }
//    public Task(String description, LocalDate dueDate, Status status) {
//        this.taskId = ++idTracker;
//        this.description = description;
//        this.dueDate = dueDate;
//        this.status = status;
//        this.owner = null;
//    }
//
//// Getters and Setters for tasks
//    public int getTaskId() {
//        return taskId;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getOwner() {
//        return owner;
//    }
//
//    public void setOwner(String owner) {
//        this.owner = owner;
//    }
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }
//
//    public boolean isOpenToClaim() {
//        return openToClaim;
//    }
//
//    public void setOpenToClaim(boolean openToClaim) {
//        this.openToClaim = openToClaim;
//    }
//
//    public LocalDate getDueDate() {
//        return dueDate;
//    }
//
//    public void setDueDate(LocalDate dueDate) {
//        this.dueDate = dueDate;
//    }
//
//
//
//    // Annotation
//    @Override
//    public String toString() {
//        return "Task{" +
//                "taskId=" + taskId +
//                ", description='" + description + '\'' +
//                ", dueDate=" + dueDate +
//                ", status=" + status +
//                ", owner='" + owner + '\'' +
//                ", openToClaim=" + openToClaim +
//                '}';
//    }
//
//    public class TaskStatus {
//
//    }
//}
//
//
////------------------------------------------------------------------------------------------------------------------------





package com.bofa.entities;

import java.time.LocalDate;

public class Task {

    public static Status TaskStatus;
    private static int idTracker = 0;
    private final int taskId;
    private String description;
    private String owner;
    private Status status;
    private ClaimStatus claimStatus;
    private boolean openToClaim;
    private LocalDate dueDate;
    private User assignedUser;
    public Task() {
        this.taskId = ++idTracker;
        this.claimStatus = ClaimStatus.NOT_CLAIMED;
        this.status = Status.NOTSTARTED; // Default status

    }

    public Task(String description, LocalDate dueDate, Status status) {
        this.taskId = ++idTracker;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.claimStatus = ClaimStatus.NOT_CLAIMED;
    }

    public User getAssignedUser() {
        return assignedUser;
    }
    public void updateOwner(String owner) {
        this.owner = owner;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }


    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ClaimStatus getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(ClaimStatus claimStatus) {
        this.claimStatus = claimStatus;
    }

    public boolean isOpenToClaim() {
        return openToClaim;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", owner='" + owner + '\'' +
                ", claimStatus=" + claimStatus +
                '}';
    }
}