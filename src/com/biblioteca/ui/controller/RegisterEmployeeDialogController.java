package com.biblioteca.ui.controller;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.core.facade.Library;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.Dialogs;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Comparator;

/**
 * This class
 */
public class RegisterEmployeeDialogController implements DialogController {

    public TextField firstName;
    public GridPane rootNode;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField authCode;

    // public ComboBox<Employee.Level> levelComboBox;

    private DataSource ds = DataSource.getDefault();
    private Dialog<ButtonType> dialog;

    public void initialize() {

    }

    @Override
    public boolean checkData() {

        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || email.getText().isEmpty()) {
            Dialogs.showAlertDialog("Tutti i campi sono obbligatori!", rootNode.getScene().getWindow());
            return false;
        } else if (password.getText().isEmpty() || password.getText().length() < 5) {
            Dialogs.showAlertDialog("La password deve essere lunga almeno 5 caratteri.", rootNode.getScene().getWindow());
            return false;
        }

        if (!authCode.getText().equals("123")) {
            Dialogs.showAlertDialog("Il Codice di autorizzazione è errato", rootNode.getScene().getWindow());
            return false;
        }

        return true;
    }

    public Employee confirmAndGet() {
        return Library.getInstance().newEmployee(firstName.getText(), lastName.getText(), email.getText(), password.getText());
    }

    @Override
    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
        dialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION,
                event -> {
                    if (!checkData())
                        event.consume();
                });
    }

    @Override
    public Dialog<ButtonType> getDialog() {
        return dialog;
    }

    //   ObservableList<Employee.Level> observableList = FXCollections.observableList(List.of(Employee.Level.ADMIN, Employee.Level.MANAGER, Employee.Level.ASSISTANT));
    //   levelComboBox.getItems().addAll(observableList);
    //   levelComboBox.getSelectionModel().selectLast();
}
