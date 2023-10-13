/*
 *TaskForge is a team task tracking application
 * Users are - Managers - Members
 * Both users can register and log in
 *  - Members can: Claim Tasks, View Tasks in Pool, Update Task Status
 *  - Managers can Create tasks for members and general unassigned tasks
 *
 * Entities:
 * USER
 * - username (String)
 * - password (String)
 *  -Type of User (Enum String)
 *
 * TASKS
 * - Assigned Member
 * - Task Description
 * - Task Status
 *
 */


import com.bofa.entities.Role;
import com.bofa.entities.Task;
import com.bofa.entities.User;
import com.bofa.entities.Status;
import com.bofa.exceptions.InvalidTaskException;
import com.bofa.exceptions.InvalidUserException;
import com.bofa.repos.TaskRepo;
import com.bofa.services.TaskService;
import com.bofa.services.UserService;
import com.bofa.utils.MockDB;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.List;



public class TaskForge {

    private static Scanner scanner = new Scanner(System.in);
    private static UserService userService = new UserService();
    private static TaskService taskService;

    private static User loggedInUser = null;

    public static void main(String[] args) {

        TaskRepo taskRepo = new TaskRepo();
        taskService = new TaskService(taskRepo);

        int userChoice;

        // Start an infinite loop to keep the application running
        while (true) {
            // Reset loggedInUser to allow logging in as a new user
            loggedInUser = null;

            // Display welcome menu and collect user input
            welcomeMenu();
            userChoice = collectUserInput();

            // Handle user input based on the welcome menu
            if (userChoice == 1) {
                createNewAccountMenu();
            } else if (userChoice == 2) {
                loginMenu();
            } else if (userChoice == 0) {
                // If user chooses to quit, thank them and continue to the next iteration
                goodbye();
                continue;
            } else {
                invalidInput();
            }

            // Execute the application logic as long as the user is logged in
            while (loggedInUser != null) {
                if (loggedInUser.getRole() == Role.TEAM) {
                    teamWelcomeMenu();
                    int input = collectUserInput();
                    parseTeamInput(input);
                } else if (loggedInUser.getRole() == Role.MANAGER) {
                    managerMenu();
                    int input = collectUserInput();
                    parseManagerInput(input);
                }
            }
        }
    }

    // Method to handle welcome menu user input
    private static void parseWelcomeMenuInput(int userChoice) {
        switch (userChoice) {
            case 1:
                createNewAccountMenu();
                break;

            case 2:
                loginMenu();
                break;

            case 0:
                goodbye();

                break;

            default:
                invalidInput();
        }
    }


    private static void createNewAccountMenu() {
        System.out.println("Welcome! Choose a username:");
        String username = scanner.nextLine();
        System.out.println("Please select your role:");
        System.out.println("1. Team Member");
        System.out.println("2. Manager");
        int roleChoice = Integer.parseInt(scanner.nextLine());

        Role role;
        String password;

        if (roleChoice == 1) {
            role = Role.TEAM;
            System.out.println("Thank you, " + username + ". Please create a password for your account:");
            password = scanner.nextLine();
        } else if (roleChoice == 2) {
            System.out.println("Please enter the manager password:");
            String managerPassword = scanner.nextLine();

            if (!managerPassword.equals("taskforgerocks")) {
                System.out.println("Invalid password. Manager account creation failed.");
                return;
            }

            role = Role.MANAGER;
            System.out.println("Thank you, " + username + ". Please create a password for your account:");
            password = scanner.nextLine();
        } else {
            System.out.println("Invalid role choice. Account creation failed.");
            return;
        }

        // Ensure that the password is not empty
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty. Account creation failed.");
            return;
        }

        // Register a new user based on provided information
        User newUser = userService.registerNewUser(username, password, role);
        System.out.println("New User created: " + newUser);
        System.out.println("Welcome " + newUser.getUsername() + ". You've successfully created your TaskForge account. Please login to continue to the application.");
    }


    private static void loginMenu() {
        while (loggedInUser == null) {
            System.out.println("Please enter your username");
            String username = scanner.nextLine();
            System.out.println("Please enter your password");
            String password = scanner.nextLine();


            try {
                loggedInUser = userService.loginUser(username, password);
            } catch (InvalidUserException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
        System.out.println("Welcome back to TaskForge, " + loggedInUser.getUsername() + ". Loading your menu now.");
    }



    private static void displayUserMenu(User user) {
        if (user.getRole().equals(Role.TEAM)) {
            while (loggedInUser != null) {
                teamWelcomeMenu();
                int input = collectUserInput();
                parseTeamInput(input);
            }
        } else if (user.getRole().equals(Role.MANAGER)) {
            while (loggedInUser != null) {
                managerMenu();
                int input = collectUserInput();
                parseManagerInput(input);
            }
        }
    }

    private static void managerMenu() {
        System.out.println("SECURED INFORMATION FOR MANAGER USE");
        System.out.println("Welcome " + loggedInUser.getUsername() + ". Please select from the choices below:");
        System.out.println("1. View Current Tasks");
        System.out.println("2. Create New Tasks");
        System.out.println("3. Update Task Status");
        System.out.println("0. Log Out and Quit TaskForge");
    }

    // Method to parse team member input
    private static void parseTeamInput(int input) {
        switch (input) {
            case 1: {
                System.out.println("Here is a list of available tasks:");
                List<Task> availableTasks = MockDB.taskPool;  // Fetch available tasks from MockDB
                for (Task task : availableTasks) {
                    System.out.println(task);
                }
                break;
            }


            case 2: {
                System.out.println("Here are all tasks currently assigned to you:");
                List<Task> assignedTasks = loggedInUser.getTasks();
                for (Task task : assignedTasks) {
                    System.out.println(task);
                }
                System.out.println("Please enter the ID of the task you want to claim:");
                int taskId = Integer.parseInt(scanner.nextLine());
                try {
                    taskService.claimTask(loggedInUser, taskId);
                    System.out.println("Task claimed successfully!");
                } catch (InvalidTaskException e) {
                    System.out.println("Unable to claim the task: " + e.getMessage());
                }
                break;
            }

            case 3: {
                System.out.println("Here are the tasks you are assigned:");
                List<Task> assignedTasks = loggedInUser.getTasks();
                for (Task task : assignedTasks) {
                    System.out.println(task);
                }
                break;
            }

            case 4: {
                if (loggedInUser.getTasks().isEmpty()) {
                    System.out.println("You have no tasks assigned at this time.");
                    break;
                }

                System.out.println("Here are your assigned tasks:");
                List<Task> assignedTasks = loggedInUser.getTasks();
                for (Task task : assignedTasks) {
                    System.out.println(task);
                }

                System.out.println("To update the task status, enter the task ID or 'x' to ignore:");
                String confirmation = scanner.nextLine();
                if (confirmation.equalsIgnoreCase("x")) {
                    System.out.println("Returning to the main menu.");
                    break;
                }

                int taskId = Integer.parseInt(confirmation);

                try {
                    taskService.updateTaskStatus(taskId, Status.COMPLETED);  // Updated to Status.COMPLETED
                    System.out.println("Task status updated successfully.");
                    System.out.println("Here are the current tasks assigned to you:");
                    List<Task> updatedTasks = loggedInUser.getTasks();
                    for (Task task : updatedTasks) {
                        System.out.println(task);
                    }
                } catch (InvalidTaskException e) {
                    System.out.println("Unable to update task status: " + e.getMessage());
                }
                break;
            }

            case 0:
                goodbye();
                break;

            default:
                invalidInput();
        }
    }



    private static void teamWelcomeMenu() {
        System.out.println("TEAM MEMBER MENU");
        System.out.println("Welcome to TaskForge, " + loggedInUser.getUsername() + ". Please choose an option below:");
        System.out.println("1. View Available Tasks");
        System.out.println("2. Claim a Task");
        System.out.println("3. View Assigned Tasks");
        System.out.println("4. Update Task Status");
        System.out.println("0. Log Out and Quit TaskForge");
    }

    private static void parseManagerInput(int input) {
        switch (input) {
            case 1: {
                System.out.println("Here is a list of all tasks:");
                List<Task> allTasks = taskService.getAllTasks();
                for (Task task : allTasks) {
                    System.out.println(task);
                }
                break;
            }

            case 2: {
                System.out.println("Enter the description of the new task:");
                String description = scanner.nextLine();
                // Prompt for due date
                System.out.println("Please enter the due date (YYYY-MM-DD):");
                LocalDate dueDate = LocalDate.parse(scanner.nextLine());


                // Prompt to assign an employee
                System.out.println("Do you want to assign this task to an employee? (yes/no)");
                boolean assignEmployee = scanner.nextLine().equalsIgnoreCase("yes");

                // If yes, prompt for employee's username
                User assignedUser = null;
                Task newTask = null;
                if (assignEmployee) {
                    System.out.println("Please enter the username of the assigned employee:");
                    String assignedUsername = scanner.nextLine();
                    try {
                        assignedUser = userService.getUserByUsername(assignedUsername);
                        if (assignedUser == null) {
                            System.out.println("Invalid username. Task creation failed.");
                            return;
                        }
                    } catch (InvalidUserException e) {
                        System.out.println("Error: " + e.getMessage());
                        return;
                    }
                     newTask = taskService.createTask(description, dueDate, assignedUser);
                } else {
                     newTask = taskService.createTask(description, dueDate);
                }


                System.out.println("New task created: " + newTask);
                break;
            }

            case 3: {
                System.out.println("Here is a list of all tasks:");
                List<Task> allTasks = taskService.getAllTasks();
                for (Task task : allTasks) {
                    System.out.println(task);
                }

                System.out.println("Enter the task ID to update its status:");
                int taskId = Integer.parseInt(scanner.nextLine());

                System.out.println("Select the new status:");
                System.out.println("1. Completed");
                System.out.println("2. In Progress");
                int statusChoice = Integer.parseInt(scanner.nextLine());

                Status status = (statusChoice == 1) ? Status.COMPLETED : Status.INPROGRESS;  // Updated to Status

                try {
                    taskService.updateTaskStatus(taskId, status);  // Updated to Status
                    System.out.println("Task status updated successfully.");
                } catch (InvalidTaskException e) {
                    System.out.println("Unable to update task status: " + e.getMessage());
                }
                break;
            }

            case 0: {
                logout();
                break;
            }

            default: {
                invalidInput();
            }
        }
    }

    private static void logout() {
        loggedInUser = null;
        System.out.println("Logged out successfully.");
    }

    private static void goodbye() {
        loggedInUser = null;
        System.out.println("Thank you for using TaskForge!");

    }

    private static void invalidInput() {
        System.out.println("That was not a valid option. Please choose an option below.");
    }

    private static int collectUserInput() {
        String input = "";
        do {
            input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                System.out.println("That is not one of the choices. Please try using only the options listed.");
            }

        } while (input.isEmpty());
        return -1;
    }

    private static void welcomeMenu() {
        System.out.println("Welcome to TaskForge! Please choose from the options listed below:");
        System.out.println("1. Create an account");
        System.out.println("2. Log in to your account");
        System.out.println("0. Quit");
    }

    public static void stillWorking() {
        System.out.println("We are still implementing this feature.");
    }
}




/*
Taskforge is a base level project task tracking application for teams in a work environment.
User Stories
1. As a user I can register for an account
2. As a user, I can log into an account
3. As a user I am able to check task status
4. As a team member I can view unclaimed tasks from the task pool
5. As a team member I can claim any unclaimed tasks and assign them to myself
6. As a team member I can update the status of a task
7. As a Manager I am able to create tasks

Users
- user signifies both the team member and manager abilities
- manager can do what team members can do but team members cannot do what managers can do
Manager - can create new tasks
team member - can create accounts, log in, view tasks in pool, update task status, and claim tasks
 */