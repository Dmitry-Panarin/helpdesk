package ru.dmitry_panarin.helpdesk.service;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(long id) {
        super("Заявка №" + id + " не найдена.");
    }
}
