package ru.dmitry_panarin.helpdesk.service;

import ru.dmitry_panarin.helpdesk.model.InvalidStatusTransitionException;
import ru.dmitry_panarin.helpdesk.model.Status;
import ru.dmitry_panarin.helpdesk.model.Ticket;
import ru.dmitry_panarin.helpdesk.repository.TicketRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class TicketService {

    private final TicketRepository rep;

    public TicketService(TicketRepository rep) {
        this.rep = rep;
    }

    public Ticket createTicket(String title, String description) {
        Ticket ticket = new Ticket(rep.nextId(), title, description);
        return rep.save(ticket);
    }

    public Ticket getTicket(long id) {
        return rep.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public Ticket changeStatus(long id, Status status) throws InvalidStatusTransitionException {
        Ticket ticket = this.getTicket(id);
        ticket.changeStatus(status);
        return rep.save(ticket);
    }

    public List<Ticket> listByStatus(Status status) {
        return rep.findByStatus(status);
    }

    public List<Ticket> listAll(){
        return rep.findAll();
    }

    public Map<Status, Long> countByStatus(){
        return rep.findAll().stream().collect(Collectors.groupingBy(Ticket::getStatus, Collectors.counting()));
    }

    public Optional<Ticket> findOldestOpen(){
        return rep.findAll().stream().filter(ticket -> ticket.getStatus() != Status.CLOSED).min(Comparator.comparing(Ticket::getCreatedAt));
    }


}
