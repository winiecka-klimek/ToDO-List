package pl.sda.jp.java26.springjpa;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(name = "last_name", length = 50)
    private String lastName;
    @OneToMany
//    @JoinColumn(name = "task_id")
    private Set<ToDoTask> toDoTasks = new HashSet<>();

    public Set<ToDoTask> getToDoTasks() {
        return toDoTasks;
    }
    public void addTask(ToDoTask toDoTask) {
        toDoTasks.add(toDoTask);
    }

    public void setToDoTasks(Set<ToDoTask> toDoTasks) {
        this.toDoTasks = toDoTasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
