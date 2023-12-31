package br.com.rocketseat.todolist.task;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(length = 50)
    private String title;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private Long userId;

    public void setTitle(String title) throws Exception {
        if(title.length() > 50) {
            throw new Exception("O título não deve conter mais de 50 caracteres.");
        }
        this.title = title;
    }
}
