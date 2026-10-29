package ru.dmitry_panarin.helpdesk.model;

public class InvalidStatusTransitionException extends Exception{
    public InvalidStatusTransitionException(long id, Status statusFrom, Status statusTo){
        super("Нельзя перевести заявку №" + id + " ИЗ " + statusFrom + " В " + statusTo);
    }
}
