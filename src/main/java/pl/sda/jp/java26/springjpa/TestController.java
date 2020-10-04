package pl.sda.jp.java26.springjpa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Hello";
    }

    @PostMapping("/import")
    public String initialImport() {
        TaskDao taskDao = new TaskDao();
        taskDao.importInitialData();
        return "OK";
    }

    @PostMapping("/importWithUsers")
    public String initialImportWithUsers() {
        final TaskDao taskDao = new TaskDao();
        taskDao.importTasksWithUsers();
        return "OK";
    }

    @GetMapping("/tasks") //get nie modyfikuje danych
    public List<ToDoTask> getTasks() {
        TaskDao taskDao = new TaskDao();
       return taskDao.getAllTasks();
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        TaskDao taskDao = new TaskDao();
        return taskDao.getAllUsers();
    }

    @GetMapping("/tasks/countByUserJDBC")
    public List<TaskCountByUser> getTasksByUser() {
        TaskDao taskDao = new TaskDao();
        return taskDao.countTasksForUserJDBC();
    }

    @GetMapping("/tasks/countByUserJPQL")
    public List<TaskCountByUser> getTasksByUserJPQL() {
        TaskDao taskDao = new TaskDao();
        return taskDao.countTasksForUserJPQL();
    }

    @GetMapping("/tasks/countByUserJava")
    public List<TaskCountByUser> getTasksByUserJava() {
        TaskDao taskDao = new TaskDao();
        return taskDao.getTasksByUsersJava();
    }

    @GetMapping("/tasks/countByUserJavaOptimal")
    public List<TaskCountByUser> getTasksByUserJavaOptimal() {
        TaskDao taskDao = new TaskDao();
        return taskDao.getTasksByUsersJavaOptimal();
    }
}

