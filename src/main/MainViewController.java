package main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField salaryLowerBoundTextField;

    @FXML
    private RadioButton maleRadio;

    @FXML
    private RadioButton femaleRadio;

    @FXML
    private ChoiceBox<Designation> designationChoiceBox;

    @FXML
    private ChoiceBox<Department> departmentChoiceBox;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private DatePicker joiningDatePicker;

    @FXML
    private Text updateText;

    @FXML
    public TextArea employeeListTextArea;

    @FXML
    private ChoiceBox<Department> searchDepartmentChoiceBox;

    @FXML
    private TextField salaryUpperBoundTextField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private TextArea searchResultTextArea;

    private final Alert alert = new Alert(Alert.AlertType.WARNING);
    private final ToggleGroup genderToggleGroup = new ToggleGroup();

    private final List<Employee> employees = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureRadioButtons();
        configureAlert();
        departmentChoiceBox.getItems().addAll(Department.values());
        designationChoiceBox.getItems().addAll(Designation.values());
        searchDepartmentChoiceBox.getItems().addAll(Department.values());
    }

    private void configureAlert() {
        alert.initModality(Modality.WINDOW_MODAL);
        Platform.runLater(() -> alert.initOwner(idTextField.getScene().getWindow()));
    }

    @FXML
    void onAddNewEmployee() {
        // na korle o hobe
        if (!validateNewEmployeeInputs()) {
            showAlert("Invalid Input!");
            return;
        }
        Employee employee = new Employee(
                Integer.parseInt(idTextField.getText()),
                nameTextField.getText(),
                Integer.parseInt(salaryTextField.getText()),
                genderToggleGroup.getSelectedToggle() == maleRadio ? "Male" : "Female",
                designationChoiceBox.getValue(),
                departmentChoiceBox.getValue(),
                dateOfBirthPicker.getValue(),
                joiningDatePicker.getValue()
        );
//same id r 2 jon k rakhbo nah...jar akti matro interface e abstract
        if (employees.stream().anyMatch(emp -> emp.compareTo(employee) == 0)) {
            showAlert("Employee with the id " + employee.getId() + " already exits!");
            return;
        }

        employees.add(employee);
        
        //just table ar counter update korbe
        updateText.setText(
                "After addition, there are Total "
                        + employees.size()
                        + " employees, as shown below:"
        );
        updateEmployeeListTextArea();
    }

    private void updateEmployeeListTextArea() {
        StringBuilder builder = new StringBuilder();
        for (Employee employee : employees) {
            builder.append(employee).append('\n');
        }
        employeeListTextArea.setText(builder.toString());
    }

    @FXML
    void onShowSearchResult() {
        if (!validateFilterInputs()) {
            showAlert("Invalid Input!");
            return;
        }

        Department serachDepartment = searchDepartmentChoiceBox.getValue();
        int searchUpperBoundSalary = Integer.parseInt(salaryUpperBoundTextField.getText());
        int searchLowerBoundSalary = Integer.parseInt(salaryLowerBoundTextField.getText());
        int count = 0;
        int totalSalary = 0;

        for (Employee employee : employees) {
            if (employee.getDepartment() == serachDepartment && employee.getSalary() >= searchLowerBoundSalary && employee.getSalary() <= searchUpperBoundSalary) {
                count++;
                totalSalary += employee.getSalary();
            }
        }

        searchResultTextArea.setText(
                "Average salary of "
                        + serachDepartment
                        + " department having salary within "
                        + searchLowerBoundSalary
                        + " and "
                        + searchUpperBoundSalary
                        //? ar ager gula condition and ? ar porerta result
                        + " taka is : " + (count == 0 ? 0 : totalSalary / count)
        );
    }

    private void configureRadioButtons() {
        maleRadio.setToggleGroup(genderToggleGroup);
        femaleRadio.setToggleGroup(genderToggleGroup);
    }

    private boolean validateNewEmployeeInputs() {
        try {
            LocalDate dob = dateOfBirthPicker.getValue();
            LocalDate joiningDate = joiningDatePicker.getValue();
            return Integer.parseInt(idTextField.getText()) > 0
                    && !nameTextField.getText().trim().isEmpty()
                    && Integer.parseInt(salaryTextField.getText()) > 0
                    && (maleRadio.isSelected() || femaleRadio.isSelected())
                    && designationChoiceBox.getValue() != null
                    && designationChoiceBox.getValue() != null
                    && (dob) != null
                    && (joiningDate) != null
                    && dob.isBefore(joiningDate);
        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean validateFilterInputs() {
        try {
            int uSalary, lSalary;
            return searchDepartmentChoiceBox.getValue() != null
                    && (lSalary = Integer.parseInt(salaryLowerBoundTextField.getText())) > 0
                    && (uSalary = Integer.parseInt(salaryUpperBoundTextField.getText())) > 0
                    && uSalary >= lSalary;
        } catch (Exception ignored) {
            return false;
        }
    }

    private void showAlert(String message) {
        alert.setContentText(message);
        alert.showAndWait();
    }
}