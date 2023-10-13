package tech.davsilvam.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import tech.davsilvam.todolist.utils.Utils;

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @GetMapping("/")
  public ResponseEntity<Object> fetch(HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    var tasks = this.taskRepository.findByUserId((UUID) userId);

    return ResponseEntity.status(HttpStatus.OK).body(tasks);
  }

  @PostMapping("/")
  public ResponseEntity<Object> create(@RequestBody TaskModel task, HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    task.setUserId((UUID) userId);

    var currentDate = LocalDateTime.now();

    if (currentDate.isAfter(task.getStartAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date must be after current date.");
    }

    if (task.getEndAt().isBefore(task.getStartAt())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("End date must be after start date.");
    }

    var createdTask = this.taskRepository.save(task);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> update(@PathVariable UUID id, @RequestBody TaskModel task, HttpServletRequest request) {
    var oldTask = this.taskRepository.findById(id).orElse(null);

    if (oldTask == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
    }

    var userId = request.getAttribute("userId");

    if (!oldTask.getUserId().equals(userId)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized to update this task.");
    }

    Utils.copyNonNullProperties(task, oldTask);

    var updatedTask = this.taskRepository.save(oldTask);

    return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
  }

}
