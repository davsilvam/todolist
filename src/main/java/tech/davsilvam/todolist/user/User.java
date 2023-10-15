package tech.davsilvam.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(unique = true)
  private String username;
  private String name;

  @Column(name = "password_hash")
  private String password;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public User(UserRequestDTO data) {
    this.setUsername(data.username());
    this.setName(data.name());
    this.setPassword(data.password());
  }

}
