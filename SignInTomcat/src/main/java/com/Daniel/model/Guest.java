package com.Daniel.model;

import java.time.LocalDate;

/**
 * Created by Daniel on 12/5/16.
 */
public class Guest {
    int guestId;
    LocalDate inDate;
    boolean signedOut;
    String firstName;
    String lastName;
    String comment;
    LocalDate outDate;


    public Guest(int guestId, LocalDate inDate, String firstName, String lastName, String comment) {
        this.guestId = guestId;
        this.inDate = inDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.comment = comment;
    }

    public Guest(int guestId, LocalDate inDate, boolean signedOut, String firstName, String lastName, String comment, LocalDate outDate) {
        this.guestId = guestId;
        this.inDate = inDate;
        this.signedOut = signedOut;
        this.firstName = firstName;
        this.lastName = lastName;
        this.comment = comment;
        this.outDate = outDate;
    }

    public Guest(){

    }

    public LocalDate getInDate() {
        return inDate;
    }

    public boolean isSignedOut() {
        return signedOut;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getComment() {
        return comment;
    }

    public void setSignedOut(boolean signedOut) {
        this.signedOut = signedOut;
    }

    public int getGuestId() {
        return guestId;
    }

    public LocalDate getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDate outDate) {
        this.outDate = outDate;
    }
}
