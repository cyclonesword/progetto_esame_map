package com.biblioteca.ui.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * This interface has been created to manage all the functionality common to all Controller classes.
 * @param <T>
 */
public interface DialogController<T> {

    default void setOnConfirmClickedEventListener(Dialog<ButtonType> dialog) {
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION,
                event -> {
                    if (!checkData())
                        event.consume();
                });
    }

    /**
     * This method is automatically called when the user press the "Ok" button on the window dialog.
     * @return true if the data validation was successful, false otherwise. By default returns true.
     */
    default boolean checkData() {
        return true;
    }

    /**
     * Close the dialog and returns the object that this dialog is handling.
     * @return The data that this dialog is handling
     */
    T confirmAndGet();

}
