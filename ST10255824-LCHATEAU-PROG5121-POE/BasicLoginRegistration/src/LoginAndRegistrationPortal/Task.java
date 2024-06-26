package LoginAndRegistrationPortal;

/**
 * Represents a task in EasyKanban with various attributes.
 */
public class Task {
    private String taskName;
    private String taskDescription;
    private String developerFirstName;
    private String developerLastName;
    private double taskDuration;
    private String taskStatus;
    private static int taskNumber = 0;
    private static int totalHours = 0;

    /**
     * Constructs a Task object with specified attributes.
     *
     * @param taskName           The name of the task.
     * @param taskDescription    The description of the task.
     * @param developerFirstName The first name of the developer assigned to the task.
     * @param developerLastName  The last name of the developer assigned to the task.
     * @param taskDuration       The duration of the task in hours.
     * @param taskStatus         The status of the task (e.g., To Do, Doing, Done).
     */
    public Task(String taskName, String taskDescription, String developerFirstName, String developerLastName, double taskDuration, String taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.developerFirstName = developerFirstName;
        this.developerLastName = developerLastName;
        this.taskDuration = taskDuration;
        this.taskStatus = taskStatus;
        totalHours += taskDuration;
    }

    /**
     * Checks if the task description is valid (length <= 50 characters).
     *
     * @return True if the task description is valid, false otherwise.
     */
    public boolean isDescriptionValid() {
        return this.taskDescription.length() <= 50;
    }

    /**
     * Generates a unique task ID based on task details.
     *
     * @return The generated task ID.
     */
    public String generateTaskID() {
        return taskName.substring(0, 2).toUpperCase() + ":" + taskNumber + ":" + developerLastName.substring(Math.max(developerLastName.length() - 3, 0)).toUpperCase();
    }

    /**
     * Retrieves a string representation of task details.
     *
     * @return String representation of task details.
     */
    public String printTaskDetails() {
        return "Task Status: " + this.taskStatus +
                "\nDeveloper Details: " + this.developerFirstName + " " + this.developerLastName +
                "\nTask Number: " + taskNumber +
                "\nTask Name: " + this.taskName +
                "\nTask Description: " + this.taskDescription +
                "\nTask ID: " + generateTaskID() +
                "\nTask Duration: " + this.taskDuration + " hours";
    }

    /**
     * Retrieves the total hours spent on tasks.
     *
     * @return The total hours spent on all tasks.
     */
    public static int getTotalHours() {
        return totalHours;
    }

    /**
     * Increments the task number for unique task identification.
     */
    public static void incrementTaskNumber() {
        taskNumber++;
    }

    /**
     * Retrieves the task name.
     *
     * @return The name of the task.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Retrieves the task duration.
     *
     * @return The duration of the task in hours.
     */
    public double getTaskDuration() {
        return taskDuration;
    }

    /**
     * Retrieves the last name of the developer assigned to the task.
     *
     * @return The last name of the developer.
     */
    public String getDeveloperLastName() {
        return developerLastName;
    }
}
