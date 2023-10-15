package tech.davsilvam.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @GetMapping
  public ResponseEntity<List<TaskResponseDTO>> getAll(HttpServletRequest request) {
    String userIdAttribute = request.getAttribute("userId").toString();
    UUID userId = UUID.fromString(userIdAttribute);

    List<TaskResponseDTO> tasks = taskRepository.findByUserId((UUID) userId).stream().map(TaskResponseDTO::new)
        .toList();

    return ResponseEntity.status(HttpStatus.OK).body(tasks);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping
  public ResponseEntity<Task> save(@RequestBody TaskRequestDTO data, HttpServletRequest request) throws Exception {
    Task newTask = new Task(data);

    String userIdAttribute = request.getAttribute("userId").toString();
    UUID userId = UUID.fromString(userIdAttribute);
    newTask.setUserId((UUID) userId);

    Task createdTask = taskRepository.save(newTask);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PutMapping("/{id}")
  public ResponseEntity<Task> update(@PathVariable UUID id, @RequestBody TaskRequestDTO data, HttpServletRequest request) throws Exception {
    var oldTask = taskRepository.findById(id).orElse(null);

    if (oldTask == null) {
      ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      throw new Exception("Task not found.");
    }

    Task newTaskData = new Task(data);

    String userIdAttribute = request.getAttribute("userId").toString();
    UUID userId = UUID.fromString(userIdAttribute);

    if (!oldTask.getUserId().equals(userId)) {
      ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      throw new Exception("User not authorized to update this task.");
    }

    Utils.copyNonNullProperties(newTaskData, oldTask);

    Task updatedTask = taskRepository.save(oldTask);

    return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
  }

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable UUID id, HttpServletRequest request) throws Exception {
    String userIdAttribute = request.getAttribute("userId").toString();
    UUID userId = UUID.fromString(userIdAttribute);

    Task task = taskRepository.findById(id).orElse(null);

    if (task == null) {
      ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      throw new Exception("Task not found.");
    }

    if (!task.getUserId().equals(userId)) {
      ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      throw new Exception("User not authorized to delete this task.");
    }

    taskRepository.delete(task);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
