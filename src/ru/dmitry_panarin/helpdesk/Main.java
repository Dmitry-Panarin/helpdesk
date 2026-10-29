package ru.dmitry_panarin.helpdesk;


import ru.dmitry_panarin.helpdesk.repository.InMemoryTicketRepository;
import ru.dmitry_panarin.helpdesk.repository.TicketRepository;
import ru.dmitry_panarin.helpdesk.service.TicketService;
import ru.dmitry_panarin.helpdesk.ui.ConsoleUi;


public class Main {
    public static void main(String[] args) {
        TicketRepository rep = new InMemoryTicketRepository();
        TicketService service = new TicketService(rep);
        new ConsoleUi(service).run();
    }
}
