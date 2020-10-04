package pl.sda.jp.java26.springjpa;

public class TaskCountByUser {

    String lastName;
    long taskCount;

    public TaskCountByUser(String lastName, long taskCount) {
        this.lastName = lastName;
        this.taskCount = taskCount;
    }

    public long getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
