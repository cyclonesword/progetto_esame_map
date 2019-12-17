package com.biblioteca.ui.controller;

import com.biblioteca.core.employee.Employee;
import com.biblioteca.core.employee.EmployeeFactory;
import com.biblioteca.datasource.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Comparator;
import java.util.List;

public class RegisterEmployeeDialogController {

    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField password;
    public TextField authCode;

    public ComboBox<Employee.Level> levelComboBox;

    private DataSource ds = DataSource.getDefault();

    public void initialize() {
        ObservableList<Employee.Level> observableList = FXCollections.observableList(List.of(Employee.Level.ADMIN, Employee.Level.MANAGER, Employee.Level.ASSISTANT));
        levelComboBox.getItems().addAll(observableList);
        levelComboBox.getSelectionModel().selectLast();
    }

    public Employee getEmployee() {

        EmployeeFactory factory = new EmployeeFactory();

        factory.setEmail(email.getText());
        factory.setPassword(password.getText());
        factory.setName(firstName.getText());
        factory.setLastName(lastName.getText());

        var lastNum = ds.getEmployees().stream()
                .map(Employee::getEmployeeNumber)
                .map(Integer::parseInt)
                .max(Comparator.naturalOrder())
                .get();

        factory.setEmployeeNumber(String.valueOf(lastNum + 1));

        var level = levelComboBox.getSelectionModel().getSelectedItem();

        return factory.getEmployee(level);
    }

}
