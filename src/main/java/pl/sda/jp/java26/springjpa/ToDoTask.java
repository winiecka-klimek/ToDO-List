package pl.sda.jp.java26.springjpa;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
public class ToDoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Column(name = "task_name", length = 100, nullable = false)
    private String taskName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
