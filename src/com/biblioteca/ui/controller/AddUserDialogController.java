package com.biblioteca.ui.controller;

import com.biblioteca.core.Customer;
import com.biblioteca.ui.Dialogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AddUserDialogController implements DialogController {

    @FXML
    private GridPane rootNode;

    @FXML
    private TextField fiscalCodeTf;

    @FXML
    private TextField firstNameTf;

    @FXML
    private TextField lastNameTf;

    @FXML
    private TextField emailTf;

    @FXML
    private TextField phoneNumberTf;

    private Dialog<ButtonType> dialog;

    public Customer getUser() {
        return new Customer(firstNameTf.getText(), lastNameTf.getText(), emailTf.getText(), fiscalCodeTf.getText(), phoneNumberTf.getText(), false);
    }

    @Override
    public boolean checkData() {

        if (firstNameTf.getText().isEmpty() || lastNameTf.getText().isEmpty() || emailTf.getText().isEmpty()) {
            Dialogs.showAlertDialog("Tutti i campi sono obbligatori", rootNode.getScene().getWindow());
            return false;
        } else if (phoneNumberTf.getText().isEmpty()) {
            Dialogs.showAlertDialog("Si prega di inserire un numero di telefono valido", rootNode.getScene().getWindow());
            return false;
        }

        return true;

    }

    @Override
    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
        DialogController.super.setDialog(dialog);
    }

    @Override
    public Dialog<ButtonType> getDialog() {
        return dialog;
    }
}
