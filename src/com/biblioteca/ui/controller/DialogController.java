package com.biblioteca.ui.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public interface DialogController {

    default void setDialog(Dialog<ButtonType> dialog) {
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION,
                event -> {
                    if (!checkData())
                        event.consume();
                });
    }
    default Dialog<ButtonType> getDialog() {
        throw new UnsupportedOperationException();
    }

    default boolean checkData() {
        return true;
    }
}
