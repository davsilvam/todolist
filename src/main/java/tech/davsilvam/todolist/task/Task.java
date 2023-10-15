package tech.davsilvam.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tb_tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(length = 50)
  private String title;
  private String description;
  private Boolean isCompleted;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private Number priority;

  private UUID userId;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public Task(TaskRequestDTO data) throws Exception {
    this.setTitle(data.title());
    this.setDescription(data.description());
    this.setIsCompleted(data.isCompleted());
    this.setStartAt(data.startAt());
    this.setEndAt(data.endAt());
    this.setPriority(data.priority());
  }

  public void setTitle(String title) throws Exception {
    if (title.length() > 50) {
      throw new Exception("Title must be less than 50 characters.");
    }

    this.title = title;
  }

  public void setStartAt(LocalDateTime startAt) throws Exception {
    var currentDate = LocalDateTime.now();

    if (startAt.isBefore(currentDate)) {
      throw new Exception("Start date must be after current date.");
    }

    this.startAt = startAt;
  }

  public void setEndAt(LocalDateTime endAt) throws Exception {
    if (endAt.isBefore(this.startAt)) {
      throw new Exception("End date must be after start date.");
    }

    this.endAt = endAt;
  }

}
