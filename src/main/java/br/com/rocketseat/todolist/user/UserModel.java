package br.com.rocketseat.todolist.user;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity(name = "tb_users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
