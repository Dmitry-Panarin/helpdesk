package ru.dmitry_panarin.helpdesk.ui;

import ru.dmitry_panarin.helpdesk.model.InvalidStatusTransitionException;
import ru.dmitry_panarin.helpdesk.model.Status;
import ru.dmitry_panarin.helpdesk.model.Ticket;
import ru.dmitry_panarin.helpdesk.service.TicketNotFoundException;
import ru.dmitry_panarin.helpdesk.service.TicketService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUi {
    private final TicketService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUi(TicketService service){
        this.service = service;
    }

    public void run(){
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createTicket();
                case "2" -> showAll();
                case "3" -> showByStatus();
                case "4" -> changeStatus();
                case "5" -> showStats();
                case "0" -> running = false;
                default -> System.out.println("Неизвестный пункт меню");
            }
        }
    }

    private long readNumber() {
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Нужно ввести целое число");
        }
    }

    private Status chooseStatus() {
        Status[] statuses = Status.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);
        }
        System.out.print("Выберите номер статуса: ");
        long number = readNumber();
        if (number < 1 || number > statuses.length) {
            throw new IllegalArgumentException("Нет статуса с номером " + number);
        }
        return statuses[(int) number - 1];
    }

    private void printMenu(){
        System.out.println("""
                +-----------------------------+
                |          ГЛАВНОЕ МЕНЮ       |
                +-----------------------------+
                | 1. Добавить заявку          |
                | 2. Показать все заявки      |
                | 3. Показать по статусу      |
                | 4. Сменить статус           |
                | 5. Статистика               |
                | 0. Выход                    |
                +-----------------------------+
                """);
    }

    private void createTicket(){
        try {
            System.out.println("Введите название заявки:");
            String title = scanner.nextLine();
            System.out.println("Введите описание заявки:");
            String description = scanner.nextLine();
            Ticket ticket = service.createTicket(title, description);
            System.out.println("Заявка №" + ticket.getId() + " создана.");
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный ввод: " + e.getMessage());
        }
    }
    private void showAll(){
        List<Ticket> tickets = service.listAll();
        if (tickets.isEmpty()) { System.out.println("Заявок нет.");}
        else {tickets.forEach(System.out::println);}
    }

    private void changeStatus(){
        try {
            System.out.println("Выберите № заявки:");
            long id = readNumber();
            Status status = chooseStatus();
            Ticket ticket = service.changeStatus(id, status);
            System.out.println("Заявка №" + ticket.getId() + " сменила статус на " + ticket.getStatus() );
        } catch (InvalidStatusTransitionException | TicketNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Недопустимый ввод: " + e.getMessage());
        }
    }

    private void showByStatus(){
//        Вообще тут можно избежать дублирование кода. Но помоему нагляднее из showAll.
        try {
            Status status = chooseStatus();
            List<Ticket> tickets = service.listByStatus(status);
            if (tickets.isEmpty()) {
                System.out.println("Заявок с таким статусом нет.");
            } else {
                tickets.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e ){
            System.out.println("Ошибка ввода: " + e.getMessage());
        }

    }

    private void showStats(){
        Map<Status, Long> counts = service.countByStatus();
        System.out.println("Заявок по статусам:");
        for (Status status : Status.values()) {
            System.out.println(status + ": " + counts.getOrDefault(status, 0L));
        }
        service.findOldestOpen().ifPresentOrElse(
                ticket -> System.out.println("Самая старая незакрытая заявка: " + ticket),
                () -> System.out.println("Незакрытых заявок нет")
        );
    }

}
