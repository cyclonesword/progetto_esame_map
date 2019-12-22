package com.biblioteca.ui.controller;

import com.biblioteca.core.Customer;
import com.biblioteca.core.facade.Library;
import com.biblioteca.ui.utils.Dialogs;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controller class that manage the add user window dialog.
 */
public class AddUserDialogController implements DialogController<Customer> {

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

    public Customer confirmAndGet() {
        return Library.getInstance().newCustomer(firstNameTf.getText(), lastNameTf.getText(), emailTf.getText(), fiscalCodeTf.getText(), phoneNumberTf.getText());
    }
}
