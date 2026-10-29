package ru.dmitry_panarin.helpdesk.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Ticket {
    private final long id;
    private String title;
    private String description;
    private Status status;
    private final LocalDateTime createdAt;

    public Ticket(long id, String title, String description){

        status = Status.NEW;
        createdAt = LocalDateTime.now();
        this.id = id;
        this.title = requireTitle(title);
        this.description = description;
    }

    private static String requireTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Заголовок заявки не может быть пустым");
        }
        return title;
    }

    @Override
    public String toString(){
        return "Ticket{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                " createdAt=" + createdAt + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void changeStatus(Status newStatus) throws InvalidStatusTransitionException {
        if (!status.canTransitionTo(newStatus)) {
            throw new InvalidStatusTransitionException(id, status, newStatus);
        }
        this.status = newStatus;
    }

    /* ГЕТТЕРЫ */

    public long getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public Status getStatus(){
        return status;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    /* СЕТТЕРЫ */

    public void setTitle(String title){
        this.title = requireTitle(title);
    }

    public void setDescription(String description){
        this.description = description;
    }

}
