import java.util.*;

// Enum for Task Status
enum TaskStatus {
    TODO, IN_PROGRESS, DONE
}

// Class representing a task
class Task {
    private int taskId;
    private String description;
    private int estimatedHours; // Effort in hours
    private TaskStatus status;
    private String assignedTo;

    public Task(int taskId, String description, int estimatedHours) {
        this.taskId = taskId;
        this.description = description;
        this.estimatedHours = estimatedHours;
        this.status = TaskStatus.TODO;
        this.assignedTo = null;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void assignTo(String developer) {
        this.assignedTo = developer;
    }

    public String display() {
        return "Task ID: " + taskId + ", Description: " + description + ", Estimated Hours: " + estimatedHours
                + ", Status: " + status + ", Assigned To: " + (assignedTo == null ? "None" : assignedTo);
    }
}

// Class representing a sprint
class Sprint {
    private int sprintId;
    private String sprintName;
    private int durationInDays; // Duration of the sprint
    private List<Task> tasks;

    public Sprint(int sprintId, String sprintName, int durationInDays) {
        this.sprintId = sprintId;
        this.sprintName = sprintName;
        this.durationInDays = durationInDays;
        this.tasks = new ArrayList<>();
    }

    public int getSprintId() {
        return sprintId;
    }

    public String getSprintName() {
        return sprintName;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(int taskId) {
        tasks.removeIf(task -> task.getTaskId() == taskId);
    }

    public int calculateRemainingEffort() {
        List<Task> pendingTasks = tasks.stream()
                                   .filter(task -> task.getStatus() != TaskStatus.DONE)
                                   .toList();
    
    // Calculate the total effort for pending tasks
        int totalEffort = pendingTasks.stream()
                                  .mapToInt(Task::getEstimatedHours)
                                  .sum();
        return totalEffort;
    }

    public String display() {
        StringBuilder builder = new StringBuilder();
        builder.append("Sprint ID: ").append(sprintId)
                .append(", Name: ").append(sprintName)
                .append(", Duration: ").append(durationInDays).append(" days\n");

        for (Task task : tasks) {
            builder.append(task.display()).append("\n");
        }
        return builder.toString();
    }
}

// Sprint Planner class
class SprintPlanner {
    public Map<Integer, Sprint> sprints;
    private int nextTaskId;

    public SprintPlanner() {
        this.sprints = new HashMap<>();
        this.nextTaskId = 1;
    }

    public void createSprint(int sprintId, String sprintName, int durationInDays) {
        if (sprints.containsKey(sprintId)) {
            System.out.println("Sprint with ID " + sprintId + " already exists.");
            return;
        }
        Sprint sprint = new Sprint(sprintId, sprintName, durationInDays);
        sprints.put(sprintId, sprint);
        System.out.println("Sprint created:\n" + sprint.display());
    }

    public void addTaskToSprint(int sprintId, String description, int estimatedHours) {
        Sprint sprint = sprints.get(sprintId);
        if (sprint == null) {
            System.out.println("Sprint with ID " + sprintId + " not found.");
            return;
        }
        Task task = new Task(nextTaskId++, description, estimatedHours);
        sprint.addTask(task);
        System.out.println("Task added to sprint:\n" + task.display());
    }

    public void assignTask(int sprintId, int taskId, String developer) {
        Sprint sprint = sprints.get(sprintId);
        if (sprint == null) {
            System.out.println("Sprint with ID " + sprintId + " not found.");
            return;
        }
        for (Task task : sprint.getTasks()) {
            if (task.getTaskId() == taskId) {
                task.assignTo(developer);
                System.out.println("Task assigned:\n" + task.display());
                return;
            }
        }
        System.out.println("Task with ID " + taskId + " not found in sprint.");
    }

    public void updateTaskStatus(int sprintId, int taskId, TaskStatus status) {
        Sprint sprint = sprints.get(sprintId);
        if (sprint == null) {
            System.out.println("Sprint with ID " + sprintId + " not found.");
            return;
        }
        for (Task task : sprint.getTasks()) {
            if (task.getTaskId() == taskId) {
                task.setStatus(status);
                System.out.println("Task status updated:\n" + task.display());
                return;
            }
        }
        System.out.println("Task with ID " + taskId + " not found in sprint.");
    }

    public void viewSprintDetails(int sprintId) {
        Sprint sprint = sprints.get(sprintId);
        if (sprint == null) {
            System.out.println("Sprint with ID " + sprintId + " not found.");
            return;
        }
        System.out.println(sprint.display());
    }
}

// Main class
public class SprintPlannerSystem {
    public static void main(String[] args) {
        SprintPlanner planner = new SprintPlanner();

        planner.createSprint(1, "Sprint 1", 14);

        planner.addTaskToSprint(1, "Design the database schema", 8);
        planner.addTaskToSprint(1, "Implement user authentication", 12);

        planner.viewSprintDetails(1);

        planner.assignTask(1, 1, "Alice");
        planner.updateTaskStatus(1, 1, TaskStatus.IN_PROGRESS);

        planner.viewSprintDetails(1);

        System.out.println("Remaining effort: " + planner.sprints.get(1).calculateRemainingEffort() + " hours");
    }
}


// import java.util.*;

// // Enum for Task Status
// enum TaskStatus {
//     TODO, IN_PROGRESS, DONE
// }

// // Class representing a task
// class Task {
//     private int taskId;
//     private String description;
//     private int estimatedHours; // Effort in hours
//     private TaskStatus status;
//     private String assignedTo;

//     public Task(int taskId, String description, int estimatedHours) {
//         this.taskId = taskId;
//         this.description = description;
//         this.estimatedHours = estimatedHours;
//         this.status = TaskStatus.TODO;
//         this.assignedTo = null;
//     }

//     public int getTaskId() {
//         return taskId;
//     }

//     public String getDescription() {
//         return description;
//     }

//     public int getEstimatedHours() {
//         return estimatedHours;
//     }

//     public TaskStatus getStatus() {
//         return status;
//     }

//     public void setStatus(TaskStatus status) {
//         this.status = status;
//     }

//     public String getAssignedTo() {
//         return assignedTo;
//     }

//     public void assignTo(String developer) {
//         this.assignedTo = developer;
//     }

//     @Override
//     public String toString() {
//         return "Task ID: " + taskId + ", Description: " + description + ", Estimated Hours: " + estimatedHours
//                 + ", Status: " + status + ", Assigned To: " + (assignedTo == null ? "None" : assignedTo);
//     }
// }

// // Class representing a sprint
// class Sprint {
//     private int sprintId;
//     private String sprintName;
//     private int durationInDays; // Duration of the sprint
//     private List<Task> tasks;

//     public Sprint(int sprintId, String sprintName, int durationInDays) {
//         this.sprintId = sprintId;
//         this.sprintName = sprintName;
//         this.durationInDays = durationInDays;
//         this.tasks = new ArrayList<>();
//     }

//     public int getSprintId() {
//         return sprintId;
//     }

//     public String getSprintName() {
//         return sprintName;
//     }

//     public int getDurationInDays() {
//         return durationInDays;
//     }

//     public List<Task> getTasks() {
//         return tasks;
//     }

//     public void addTask(Task task) {
//         tasks.add(task);
//     }

//     public void removeTask(int taskId) {
//         tasks.removeIf(task -> task.getTaskId() == taskId);
//     }

//     public int calculateRemainingEffort() {
//         return tasks.stream()
//                 .filter(task -> task.getStatus() != TaskStatus.DONE)
//                 .mapToInt(Task::getEstimatedHours)
//                 .sum();
//     }

//     @Override
//     public String toString() {
//         return "Sprint ID: " + sprintId + ", Name: " + sprintName + ", Duration: " + durationInDays + " days";
//     }
// }

// // Sprint Planner class
// class SprintPlanner {
//     public Map<Integer, Sprint> sprints;
//     private int nextTaskId;

//     public SprintPlanner() {
//         this.sprints = new HashMap<>();
//         this.nextTaskId = 1;
//     }

//     public void createSprint(int sprintId, String sprintName, int durationInDays) {
//         if (sprints.containsKey(sprintId)) {
//             System.out.println("Sprint with ID " + sprintId + " already exists.");
//             return;
//         }
//         Sprint sprint = new Sprint(sprintId, sprintName, durationInDays);
//         sprints.put(sprintId, sprint);
//         System.out.println("Sprint created: " + sprint);
//     }

//     public void addTaskToSprint(int sprintId, String description, int estimatedHours) {
//         Sprint sprint = sprints.get(sprintId);
//         if (sprint == null) {
//             System.out.println("Sprint with ID " + sprintId + " not found.");
//             return;
//         }
//         Task task = new Task(nextTaskId++, description, estimatedHours);
//         sprint.addTask(task);
//         System.out.println("Task added to sprint: " + task);
//     }

//     public void assignTask(int sprintId, int taskId, String developer) {
//         Sprint sprint = sprints.get(sprintId);
//         if (sprint == null) {
//             System.out.println("Sprint with ID " + sprintId + " not found.");
//             return;
//         }
//         for (Task task : sprint.getTasks()) {
//             if (task.getTaskId() == taskId) {
//                 task.assignTo(developer);
//                 System.out.println("Task assigned: " + task);
//                 return;
//             }
//         }
//         System.out.println("Task with ID " + taskId + " not found in sprint.");
//     }

//     public void updateTaskStatus(int sprintId, int taskId, TaskStatus status) {
//         Sprint sprint = sprints.get(sprintId);
//         if (sprint == null) {
//             System.out.println("Sprint with ID " + sprintId + " not found.");
//             return;
//         }
//         for (Task task : sprint.getTasks()) {
//             if (task.getTaskId() == taskId) {
//                 task.setStatus(status);
//                 System.out.println("Task status updated: " + task);
//                 return;
//             }
//         }
//         System.out.println("Task with ID " + taskId + " not found in sprint.");
//     }

//     public void viewSprintDetails(int sprintId) {
//         Sprint sprint = sprints.get(sprintId);
//         if (sprint == null) {
//             System.out.println("Sprint with ID " + sprintId + " not found.");
//             return;
//         }
//         System.out.println(sprint);
//         for (Task task : sprint.getTasks()) {
//             System.out.println(task);
//         }
//     }
// }

// // Main class
// public class SprintPlannerSystem {
//     public static void main(String[] args) {
//         SprintPlanner planner = new SprintPlanner();

//         planner.createSprint(1, "Sprint 1", 14);

//         planner.addTaskToSprint(1, "Design the database schema", 8);
//         planner.addTaskToSprint(1, "Implement user authentication", 12);

//         planner.viewSprintDetails(1);

//         planner.assignTask(1, 1, "Alice");
//         planner.updateTaskStatus(1, 1, TaskStatus.IN_PROGRESS);

//         planner.viewSprintDetails(1);

//         System.out.println("Remaining effort: " + planner.sprints.get(1).calculateRemainingEffort() + " hours");
//     }
// }
