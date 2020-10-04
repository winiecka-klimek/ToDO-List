package pl.sda.jp.java26.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.*;
import java.util.*;

public class TaskDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("mySql");

    private final static String DB_CON =
            "jdbc:mysql://localhost:3306/jdbcjpa_projekt?serverTimezone=UTC";
    private Connection connection;

    public void importInitialData() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        final ToDoTask todoTask = new ToDoTask();
        todoTask.setTaskName("Moje pierwsze zadanie");

        entityManager.getTransaction().begin();
        entityManager.persist(todoTask);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public void importTasksWithUsers() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

       final User u1 = new User();
       u1.setFirstName("Jan");
       u1.setLastName("Kowalski");

       final User u2 = new User();
       u2.setFirstName("Anna");
       u2.setLastName("Nowak");

       final ToDoTask toDoTask1 = new ToDoTask();
        toDoTask1.setTaskName("Moje pierwsze zadanie");
        toDoTask1.setUser(u1);

        final ToDoTask toDoTask2 = new ToDoTask();
        toDoTask2.setTaskName("Moje drugie zadanie");
        toDoTask2.setUser(u2);

        ToDoTask toDoTask3 = new ToDoTask();
        toDoTask3.setTaskName("Moje  trzecie zadanie");
        toDoTask3.setUser(u2);

        entityManager.getTransaction().begin();
        entityManager.persist(u1);
        entityManager.persist(u2);
        entityManager.persist(toDoTask1);
        entityManager.persist(toDoTask2);
        entityManager.persist(toDoTask3);

        entityManager.getTransaction().commit();

        entityManager.close();
    }

    public List<ToDoTask> getAllTasks() {
       final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

       String  jpqlQuery = "SELECT t FROM ToDoTask t";
        List<ToDoTask> tasks = entityManager
                .createQuery(jpqlQuery, ToDoTask.class)
                .getResultList();
        return tasks;
    }

    public List<User> getAllUsers() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        String  jpqlQuery = "SELECT u FROM User u";
        List<User> users = entityManager
                .createQuery(jpqlQuery, User.class)
                .getResultList();
        return users;
    }

    private void createJDBCConnection() {
        try {
            connection = DriverManager.getConnection(DB_CON, "marta", "tajnehaslo");
        } catch (SQLException e) {
//            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    private void closeJDBCConnection() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            System.out.println("not opened");
        }
    }

    public List<TaskCountByUser> countTasksForUserJDBC() {
        String queryJDBC = "SELECT u.last_name, count(t.task_name) task_count FROM tasks t " +
                "LEFT JOIN users u ON u.id = t.user_id " +
                "GROUP BY u.id";

        createJDBCConnection();
        List<TaskCountByUser> tasksCountByUsers = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(queryJDBC);

            while (resultSet.next()) {
                String lastName = resultSet.getString("last_name");
                long taskCount = resultSet.getLong("task_count");
                TaskCountByUser taskCountByUser = new TaskCountByUser(lastName, taskCount);
                tasksCountByUsers.add(taskCountByUser);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        closeJDBCConnection();
        return tasksCountByUsers;
    }

    public List<TaskCountByUser> countTasksForUserJPQL()  {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

       return entityManager
                .createQuery("SELECT new pl.sda.jp.java26.springjpa.TaskCountByUser(t.user.lastName, count(t))  " +
                                "FROM ToDoTask t GROUP BY t.user",
                        TaskCountByUser.class)
                .getResultList();

    }

    public List<TaskCountByUser> getTasksByUsersJava() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        String  jpqlQuery = "SELECT t FROM ToDoTask t WHERE t.user is not null";
        List<ToDoTask> tasks = entityManager
                .createQuery(jpqlQuery, ToDoTask.class)
                .getResultList();

        List<User> users = getAllUsers();
        List<TaskCountByUser> taskCountByUserList = new ArrayList<>();

        Map<User, Integer> userTasksCountMap = new HashMap<>();

        for (User user : users) {
            List<ToDoTask> currentUserTasks = new ArrayList<>();
                for (ToDoTask task : tasks) {
                    if (user.getId().equals(task.getUser().getId())) {
                        currentUserTasks.add(task);
                    }
                }
                userTasksCountMap.put(user, currentUserTasks.size());
            }

        for (User user : userTasksCountMap.keySet()) {
            int values = userTasksCountMap.get(user);
            taskCountByUserList.add(new TaskCountByUser(user.getLastName(), values));
        }
        return taskCountByUserList;
    }

    public List<TaskCountByUser> getTasksByUsersJavaOptimal() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

       final List<ToDoTask> tasks = entityManager
                .createQuery("SELECT t FROM ToDoTask t WHERE t.user is not null", ToDoTask.class)
                .getResultList();

        final List<User> users = entityManager
                .createQuery("SELECT u FROM User u", User.class)
                .getResultList();

        List<TaskCountByUser> taskCountByUserList = new ArrayList<>();
        for (User user : users) {
            int taskPerUserCounter = 0;
            for (ToDoTask task : tasks) {
                if (user.getId().equals(task.getUser().getId())) {
                    taskPerUserCounter += 1;
                }
            } taskCountByUserList.add(new TaskCountByUser(user.getLastName(), taskPerUserCounter));
        }

        return taskCountByUserList;
    }

}
