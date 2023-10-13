package br.com.rocketseat.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        Object idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        LocalDateTime currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início / data fim deve ser maior que a data atual.");
        }

        TaskModel saveTask = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveTask);
    }

    @GetMapping("/")
    public List<TaskModel> listAllByIdUser(HttpServletRequest request) {
        Object idUser = request.getAttribute("idUser");
        List<TaskModel> tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }
}