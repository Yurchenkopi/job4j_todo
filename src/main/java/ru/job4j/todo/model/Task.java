package ru.job4j.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "title"})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    private LocalDateTime created = LocalDateTime.now();

    private boolean done;

    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + '}';
    }
}
