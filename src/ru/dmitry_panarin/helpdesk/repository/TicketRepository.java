package ru.dmitry_panarin.helpdesk.repository;

import ru.dmitry_panarin.helpdesk.model.Status;
import ru.dmitry_panarin.helpdesk.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketRepository {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(long id);
    List<Ticket> findAll();
    List<Ticket> findByStatus(Status status);
    long nextId();
}
