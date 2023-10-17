package br.com.rocketseat.todolist.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<TaskModel, Long> {

    @Query(value = "SELECT * FROM tb_tasks WHERE user_id = ?1", nativeQuery = true)
    List<TaskModel> findByUserId(Long idUser);
}
