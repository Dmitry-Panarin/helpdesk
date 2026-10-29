package ru.dmitry_panarin.helpdesk.model;

public enum Status {
    NEW,
    IN_PROGRESS,
    CLOSED;

    public boolean canTransitionTo(Status next) {
        return switch (this) {
            case NEW -> next == IN_PROGRESS || next == CLOSED;
            case IN_PROGRESS -> next == CLOSED;
            case CLOSED -> false;
        };
    }
}