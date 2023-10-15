package tech.davsilvam.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDTO(UUID id, String username, String name, LocalDateTime createdAt) {

  public UserResponseDTO(User user) {
    this(user.getId(), user.getUsername(), user.getName(), user.getCreatedAt());
  }
  
}
