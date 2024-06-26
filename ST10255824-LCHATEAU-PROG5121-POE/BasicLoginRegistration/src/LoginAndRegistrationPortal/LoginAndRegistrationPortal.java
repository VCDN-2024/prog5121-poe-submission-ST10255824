package LoginAndRegistrationPortal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main class for managing user login, registration, and task management in EasyKanban.
 */
public class LoginAndRegistrationPortal {
    private static Map<String, String[]> users = new HashMap<>();
    private static boolean loggedIn = false;
    private static ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Main method to start the EasyKanban application.
     */
    public static void main(String[] args) {
        showMessage("Welcome to EasyKanban");
        
        // //Adapted from https://blog.vidursoft.com/2011/11/create-login-form-using-netbeans-ide.html
        
        performLogin();

        if (loggedIn) {
            manageTasks();
        }

        showMessage("Closing EasyKanban. Goodbye!");
        System.exit(0);
    }

    /**
     * Performs user login by prompting for username and password.
     */
    ////Adapted from https://www.theserverside.com/blog/Coffee-Talk-Java-News-Stories-and-Opinions/Java-user-input-with-a-Swing-JOptionPane-example
    public static void performLogin() {
        while (!loggedIn) {
            String username = getInput("Enter username:");

            if (!users.containsKey(username)) {
                int option = confirmDialog("Username not found. Do you want to register?", "User not found");

                if (option == JOptionPane.YES_OPTION) {
                    registerUser();
                } else {
                    showMessage("Closing EasyKanban. Goodbye!");
                    System.exit(0);
                }
            } else {
                String password = getInput("Enter password:");
                String[] userDetails = users.get(username);

                if (userDetails[0].equals(password)) {
                    loggedIn = true;
                    showMessage("Login successful! Welcome, " + userDetails[1] + " " + userDetails[2] + "!");
                } else {
                    showMessage("Invalid password. Please try again.");
                }
            }
        }
    }

    /**
     * Overloaded method to perform login with provided username and password.
     *
     * @param username The username to log in with.
     * @param password The password corresponding to the username.
     */
    public static void performLogin(String username, String password) {
        if (users.containsKey(username)) {
            String[] userDetails = users.get(username);
            if (userDetails[0].equals(password)) {
                loggedIn = true;
                showMessage("Login successful! Welcome, " + userDetails[1] + " " + userDetails[2] + "!");
            } else {
                showMessage("Invalid password. Please try again.");
            }
        } else {
            showMessage("Username not found. Please register first.");
        }
    }

    /**
     * Registers a new user by collecting required information.
     */
    public static void registerUser() {
        showMessage("=== Registration ===");

        String firstName = getInput("Enter first name:");
        String lastName = getInput("Enter last name:");
        String username = getInput("Enter username:");

        if (!isValidUsername(username)) {
            showMessage("Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than 5 characters in length.");
            return;
        }

        if (users.containsKey(username)) {
            showMessage("Username already exists. Please choose another one.");
            return;
        }

        String password = getInput("Enter password:");

        if (!isValidPassword(password)) {
            showMessage("Password is not correctly formatted. Please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
            return;
        }

        String[] userDetails = {password, firstName, lastName};
        users.put(username, userDetails);
        showMessage("Registration successful for username: " + username);
    }

    /**
     * Registers a new user with provided details.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param username  The username chosen by the user.
     * @param password  The password chosen by the user.
     */
    public static void registerUser(String firstName, String lastName, String username, String password) {
        if (!isValidUsername(username)) {
            throw new IllegalArgumentException("Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than 5 characters in length.");
        }

        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Username already exists. Please choose another one.");
        }

        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password is not correctly formatted. Please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
        }

        String[] userDetails = {password, firstName, lastName};
        users.put(username, userDetails);
    }

    /**
     * Checks if the provided username meets the required format.
     *
     * @param username The username to validate.
     * @return True if the username is valid, false otherwise.
     */
    public static boolean isValidUsername(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    /**
     * Checks if the provided password meets the required complexity.
     *
     * @param password The password to validate.
     * @return True if the password is valid, false otherwise.
     */
    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     * Manages the tasks in the EasyKanban application.
     */
    public static void manageTasks() {
     //Adapted from https://stackoverflow.com/questions/8059259/creating-a-console-menu-for-user-to-make-a-selection 
    int choice;
    do {
        String[] options = {
            "Add tasks", "List all Tasks", "Display Taks With longest duration",
            "Search Task By developer name","Delete Task By name ", "Quit"
        };
        choice = optionDialog("Select an option:", "EasyKanban Menu", options);

        switch (choice) {
            case 0:
                addTasks();
                break;
            case 1:
                listAllTasks();
                break;
            case 2:
                 displayLongestDurationTask();
               
                break;
            case 3:
                 searchTasksByDeveloper();
                
                break;
            case 4:
                 deleteTaskByName();
               
                break;
            case 5:
               // Quit the application
                break;
           
            default:
                showMessage("Invalid option. Please choose again.");
        }
    } while (choice != 5);

      }
    
    /**
     * Adds multiple tasks based on user input.
     */
    public static void addTasks() {
        int numTasks = Integer.parseInt(getInput("Enter number of tasks to enter:"));

        for (int i = 1; i <= numTasks; i++) {
            addTask();
        }

        showMessage("Total Hours Across All Tasks: " + Task.getTotalHours());
    }

    /**
     * Adds a single task based on user input.
     */
    public static void addTask() {
        showMessage("=== Add Task ===");

        String taskName = getInput("Enter Task Name:");
        String taskDescription = getInput("Enter Task Description:");

        Task task = new Task(taskName, taskDescription, "", "", 0, "");

        if (!task.isDescriptionValid()) {
            showMessage("Please enter a task description of less than 50 characters");
            return;
        } else {
            showMessage("Task successfully captured");
        }

        String developerFirstName = getInput("Enter Developer First Name:");
        String developerLastName = getInput("Enter Developer Last Name:");
        double taskDuration = Double.parseDouble(getInput("Enter Task Duration (in hours):"));

        task = new Task(taskName, taskDescription, developerFirstName, developerLastName, taskDuration, selectTaskStatus());
        tasks.add(task);

        showMessage(task.printTaskDetails());

        Task.incrementTaskNumber();
    }

    /**
     * Prompts user to select task status.
     *
     * @return The selected task status.
     */
    public static String selectTaskStatus() {
        String[] statusOptions = {"To Do", "Done", "Doing"};
        int statusChoice = optionDialog("Select Task Status:", "Task Status", statusOptions);
        return statusOptions[statusChoice];
    }

    /**
     * Lists all tasks currently stored in the system.
     */
    public static void listAllTasks() {
        StringBuilder allTasks = new StringBuilder("All Tasks:\n");
        for (Task task : tasks) {
            allTasks.append(task.printTaskDetails()).append("\n\n");
        }
        showMessage(allTasks.toString());
    }

    /**
     * Displays the task with the longest duration.
     */
    public static void displayLongestDurationTask() {
        if (tasks.isEmpty()) {
            showMessage("No tasks available.");
            return;
        }

        Task longestTask = tasks.stream()
                .max((t1, t2) -> Double.compare(t1.getTaskDuration(), t2.getTaskDuration()))
                .orElse(null);

        showMessage("Task with Longest Duration:\n" + longestTask.printTaskDetails());
    }

    /**
     * Searches for tasks assigned to a specific developer.
     */
    public static void searchTasksByDeveloper() {
        String developerLastName = getInput("Enter Developer Last Name to search tasks:");

        StringBuilder foundTasks = new StringBuilder("Tasks assigned to " + developerLastName + ":\n");
        boolean taskFound = false;
        for (Task task : tasks) {
            if (task.getDeveloperLastName().equalsIgnoreCase(developerLastName)) {
                foundTasks.append(task.printTaskDetails()).append("\n\n");
                taskFound = true;
            }
        }

        if (!taskFound) {
            foundTasks.append("No tasks found for this developer.");
        }

        showMessage(foundTasks.toString());
    }

    /**
     * Deletes a task based on its name.
     */
    public static void deleteTaskByName() {
        String taskName = getInput("Enter Task Name to delete:");

        Task taskToRemove = null;
        for (Task task : tasks) {
            if (task.getTaskName().equalsIgnoreCase(taskName)) {
                taskToRemove = task;
                break;
            }
        }

        if (taskToRemove != null) {
            tasks.remove(taskToRemove);
            showMessage("Task '" + taskName + "' deleted successfully.");
        } else {
            showMessage("Task '" + taskName + "' not found.");
        }
    }

    /**
     * Displays a message and prompts user for input.
     *
     * @param message The message to display.
     * @return User input as a string.
     */
    public static String getInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

    /**
     * Displays a message dialog with the specified message.
     *
     * @param message The message to display.
     */
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * Displays a confirmation dialog with the specified message and title.
     *
     * @param message The message to display in the dialog.
     * @param title   The title of the dialog.
     * @return An integer indicating the user's choice.
     */
    public static int confirmDialog(String message, String title) {
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }

    /**
     * Displays an option dialog with the specified message, title, and options.
     *
     * @param message The message to display in the dialog.
     * @param title   The title of the dialog.
     * @param options An array of options to display.
     * @return An integer indicating the user's choice.
     */
    public static int optionDialog(String message, String title, String[] options) {
        return JOptionPane.showOptionDialog(null, message, title,
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
    }

    /**
     * Getter for the users map.
     *
     * @return The map containing username to user details mappings.
     */
    public static Map<String, String[]> getUsers() {
        return users;
    }
}
