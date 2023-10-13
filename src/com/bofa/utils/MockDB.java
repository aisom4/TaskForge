package com.bofa.utils;


import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;  // Added import for LocalDate
import com.bofa.entities.User;
import com.bofa.entities.Task;
import com.bofa.entities.Status;
import com.bofa.entities.Role;

//Database that includes tasks for the general pool of tasks
public class MockDB {

    public static List<User> registeredUsers = new ArrayList<>();
    public static List<Task> taskPool = new ArrayList<>();

    static{
        User user1 = new User("SrDevE", "Java123", Role.TEAM);
        User user2 = new User("ilead0101", "Java123", Role.MANAGER);
        User user3 = new User("bestmanager", "Java123", Role.MANAGER);
        User user4 = new User("TeamSupporter", "Java123", Role.TEAM);
        User user5 = new User("workhard", "Java123", Role.TEAM);
        User user6 = new User("morecodemoreprobs", "Java123", Role.TEAM);
        registeredUsers.add(user1);
        registeredUsers.add(user2);
        registeredUsers.add(user3);
        registeredUsers.add(user4);
        registeredUsers.add(user5);
        registeredUsers.add(user6);


        LocalDate date1 = LocalDate.of(2023, 10, 23);
        Task t1 = new Task("Update Customer Reports", date1, Status.COMPLETED);

        LocalDate date2 = LocalDate.of(2023, 1, 23);
        Task t2 = new Task("Debug Bug Reports", date2, Status.INPROGRESS);

        LocalDate date3 = LocalDate.of(2023, 12, 25);
        Task t3 = new Task("Learn a new programming language", date3, Status.NOTSTARTED);

        LocalDate date4 = LocalDate.of(2024, 2, 26);
        Task t4 = new Task("Clean out corrupted files", date4, Status.INPROGRESS);

        LocalDate date5 = LocalDate.of(2024, 5, 27);
        Task t5 = new Task("Analyze data reports to generate business predictions", date5, Status.INPROGRESS);
        taskPool.add(t1);
        taskPool.add(t2);
        taskPool.add(t3);
        taskPool.add(t4);
        taskPool.add(t5);

    }
}
