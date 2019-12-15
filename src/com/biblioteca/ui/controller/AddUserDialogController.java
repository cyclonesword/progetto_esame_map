package com.biblioteca.ui.controller;

import com.biblioteca.core.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddUserDialogController {

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

    public Customer getUser() {
        return new Customer(firstNameTf.getText(), lastNameTf.getText(), emailTf.getText(), fiscalCodeTf.getText(), phoneNumberTf.getText(), false);
    }

}
