package tech.davsilvam.todolist.task;

import java.util.UUID;

public record TaskResponseDTO(UUID id, String title, String description, Boolean isCompleted, Number priority, UUID userId) {

  public TaskResponseDTO(Task task) {
    this(task.getId(), task.getTitle(), task.getDescription(), task.getIsCompleted(), task.getPriority(), task.getUserId());
  }

}
