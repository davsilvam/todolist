package tech.davsilvam.todolist.task;

import java.time.LocalDateTime;

public record TaskRequestDTO(String title, String description, Boolean isCompleted, LocalDateTime startAt, LocalDateTime endAt, Number priority) {
  
}
