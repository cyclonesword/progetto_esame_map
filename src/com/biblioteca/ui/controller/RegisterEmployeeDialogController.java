package com.biblioteca.ui.controller;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.core.facade.Library;
import com.biblioteca.datasource.DataSource;
import com.biblioteca.ui.utils.Dialogs;
import com.biblioteca.ui.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Comparator;

/**
 * This controller class is responsible for managing the registration of new employees. <br>
 * Refer to {@link DialogController} class for the common methods.
 */
public class RegisterEmployeeDialogController implements DialogController<Employee> {

    @FXML
    private TextField firstName;

    @FXML
    private GridPane rootNode;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private TextField authCode;

    @FXML
    private TextField empCode;

    @Override
    public boolean checkData() {

        if(!Utils.isValidEmailAddress(email.getText())) {
            Dialogs.showAlertDialog("Per favore inserisci un indirizzo email valido", rootNode.getScene().getWindow());
            return false;
        }
        else if (firstName.getText().isEmpty() || lastName.getText().isEmpty() || empCode.getText().isEmpty()) {
            Dialogs.showAlertDialog("Tutti i campi sono obbligatori!", rootNode.getScene().getWindow());
            return false;
        } else if (password.getText().isEmpty() || password.getText().length() < 5) {
            Dialogs.showAlertDialog("La password deve essere lunga almeno 5 caratteri.", rootNode.getScene().getWindow());
            return false;
        }

        // Nota per il prof Fici: ===
            // In realtà l'applicazione dovrebbe collegarsi ad un server remoto, controllare il codice
            // ed autorizzare (o non autorizzare). Dato che questo è ad di fuori dello scopo del progetto,
            // viene confrontato con un codice di esempio "123"
        // ============
        if (!authCode.getText().equals("123")) {
            Dialogs.showAlertDialog("Il Codice di autorizzazione è errato", rootNode.getScene().getWindow());
            return false;
        }

        return true;
    }

    /**
     *
     * @return The newly created Employee instance
     */
    @Override
    public Employee confirmAndGet() {
        return Library.getInstance().newEmployee(firstName.getText(), lastName.getText(), email.getText(), password.getText());
    }


    /**
     * Initialize the employee code field with an unmodifiable code.
     */
    public void initialize() {
        var lastNum = DataSource.getInstance().getEmployees().stream()
                .map(Employee::getEmployeeNumber)
                .max(Comparator.naturalOrder())
                .get();

        empCode.setText(String.valueOf(lastNum + 1));
    }
    //   ObservableList<Employee.Level> observableList = FXCollections.observableList(List.of(Employee.Level.ADMIN, Employee.Level.MANAGER, Employee.Level.ASSISTANT));
    //   levelComboBox.getItems().addAll(observableList);
    //   levelComboBox.getSelectionModel().selectLast();
}
