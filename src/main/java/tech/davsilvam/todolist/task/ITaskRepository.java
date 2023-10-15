package tech.davsilvam.todolist.task;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, UUID> {
  
  List<Task> findByUserId(UUID userId);

}
