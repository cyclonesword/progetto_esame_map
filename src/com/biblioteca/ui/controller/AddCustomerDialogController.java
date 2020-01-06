package com.biblioteca.ui.controller;

import com.biblioteca.core.Customer;
import com.biblioteca.core.facade.Library;
import com.biblioteca.ui.utils.Dialogs;
import com.biblioteca.ui.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Controller class that manages the add user window dialog.
 */
public class AddCustomerDialogController implements DialogController<Customer> {

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

    // private Dialog<ButtonType> dialog;

    @Override
    public boolean checkData() {

        if (!Utils.isValidEmailAddress(emailTf.getText())){
            Dialogs.showAlertDialog("Per favore inserisci un indirizzo email valido", rootNode.getScene().getWindow());
            return false;
        } else if (firstNameTf.getText().isEmpty() || lastNameTf.getText().isEmpty()) {
            Dialogs.showAlertDialog("Tutti i campi sono obbligatori", rootNode.getScene().getWindow());
            return false;
        } else if (!Utils.isValidStringNumber(phoneNumberTf.getText())) {
            Dialogs.showAlertDialog("Si prega di inserire un numero di telefono valido (solo numeri, senza spazi, minimo 8 caratteri)", rootNode.getScene().getWindow());
            return false;
        } else if (fiscalCodeTf.getText() == null || fiscalCodeTf.getText().length() != 16) {
            Dialogs.showAlertDialog("Per favore inserisci un codice fiscale valido (16 caratteri)", rootNode.getScene().getWindow());
            return false;
        }

        return true;

    }

    public Customer confirmAndGet() {
        return Library.getInstance().newCustomer(firstNameTf.getText(), lastNameTf.getText(), emailTf.getText(), fiscalCodeTf.getText(), phoneNumberTf.getText());
    }
}
