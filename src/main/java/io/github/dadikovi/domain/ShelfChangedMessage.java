package io.github.dadikovi.domain;

import io.github.dadikovi.domain.enumeration.ChangeType;

public class ShelfChangedMessage {

    private ChangeType changeType;
    private Book changedBook;

    public ShelfChangedMessage() {

    }

    public ShelfChangedMessage( ChangeType changeType, Book changedBook ) {
        this.changeType = changeType;
        this.changedBook = changedBook;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType( ChangeType changeType ) {
        this.changeType = changeType;
    }

    public Book getChangedBook() {
        return changedBook;
    }

    public void setChangedBook( Book changedBook ) {
        this.changedBook = changedBook;
    }
}
