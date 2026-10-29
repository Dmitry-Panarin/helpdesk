package ru.dmitry_panarin.helpdesk.repository;

import ru.dmitry_panarin.helpdesk.model.Status;
import ru.dmitry_panarin.helpdesk.model.Ticket;

import java.util.*;

public class InMemoryTicketRepository implements TicketRepository {
    private final Map<Long, Ticket>  tickets = new LinkedHashMap<>();
    private long idCounter = 1;

    @Override
    public Ticket save(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    @Override
    public Optional<Ticket> findById(long id) {
        return Optional.ofNullable(tickets.get(id));
    }

    @Override
    public List<Ticket> findAll() {
        return List.copyOf(tickets.values());
    }

    @Override
    public List<Ticket> findByStatus(Status status) {
        return tickets.values().stream()
                .filter(ticket ->  ticket.getStatus() == status)
                .toList();
    }

    @Override
    public long nextId(){
        return idCounter++;
    }


}
