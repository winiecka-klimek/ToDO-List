package pl.sda.jp.java26.springjpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.html.parser.Entity;

public class TaskDao {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY =
            Persistence.createEntityManagerFactory("mySql");

    public void importInitialData() {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        ToDoTask toDoTask = new ToDoTask();
        toDoTask.setTaskName("Moje pierwsze zadanie");
        entityManager.getTransaction().begin();
        entityManager.persist(toDoTask);
        entityManager.getTransaction().commit();

        entityManager.close();
    }

}
