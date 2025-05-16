package autumnmid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MidExamSceneController implements Initializable {
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private ComboBox<String> desigComboBox;
    @FXML
    private ComboBox<String> deptComboBox;
    @FXML
    private DatePicker dobDatePicker;
    @FXML
    private DatePicker dojDatePicker;
    @FXML
    private Text countLabel;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer> idColumn;
    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, Float> salaryColumn;
    @FXML
    private TableColumn<Employee, String> genderColumn;
    @FXML
    public TableColumn<Employee, String> designationColumn;
    @FXML
    public TableColumn<Employee, String> departmentColumn;
    @FXML
    public TableColumn<Employee, String> dobColumn;
    @FXML
    public TableColumn<Employee, String> dojColumn;
    private final ToggleGroup toggleGroup = new ToggleGroup();

    private final List<String> designations = Arrays.asList("Executive", "Junior Officer", "Senior Officer", "Accountant", "HR Manager", "Director", "Engineer");
    private final List<String> departments = Arrays.asList("Accounts", "HR", "Admin", "Sales", "Production");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dobDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(empty || item.isBefore(LocalDate.now().minusDays(1)) || item.isAfter(LocalDate.now().plusDays(2)));
            }
        });
        maleRadioButton.setToggleGroup(toggleGroup);
        femaleRadioButton.setToggleGroup(toggleGroup);
        desigComboBox.getItems().addAll(designations);
        deptComboBox.getItems().addAll(departments);
        configureTableColumns();
        loadFromBinFile();
    }

    public void setInitData(List<Employee> employees) {
        employeeTable.getItems().addAll(employees);
    }

    @FXML
    private void addNewEmpToListAndShowThemAllButtonOnClick(ActionEvent event) {
        int id = Integer.parseInt(idTextField.getText());
        if (employeeTable.getItems().stream().anyMatch(employee -> employee.getId() == id)) {
            showAlert(id);
            return;
        }

        Employee newEmployee = new Employee(
                id,
                nameTextField.getText(),
                Float.parseFloat(salaryTextField.getText()),
                maleRadioButton.isSelected() ? "Male" : "Female",
                desigComboBox.getValue(),
                deptComboBox.getValue(),
                dobDatePicker.getValue(),
                dojDatePicker.getValue()
        );
        employeeTable.getItems().add(newEmployee);
        Util.addEmployee(newEmployee);
        countLabel.setText("After addition, there are TOTAL " +
                employeeTable.getItems().size() +
                " employees, as show below:");
    }

    @FXML
    private void onSwitchFilterScene(ActionEvent event) throws Exception {
        Stage stage = (Stage) idTextField.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FilterScene.fxml"));
        Scene scene = new Scene(loader.load());
        FilterSceneController controller = loader.getController();
        controller.setInitData(departments, employeeTable.getItems());
        stage.setScene(scene);
        stage.show();
    }

    private void configureTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("desig"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));
        dojColumn.setCellValueFactory(new PropertyValueFactory<>("doj"));
    }

    private void loadFromBinFile() {
        employeeTable.getItems().addAll(
                Util.getEmployees()
        );
        countLabel.setText("After addition, there are TOTAL " +
                employeeTable.getItems().size() +
                " employees, as show below:");
    }

    private void showAlert(int id) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(idTextField.getScene().getWindow());
        alert.initModality(Modality.NONE);
        alert.setContentText("Employee with id " + id + " already exists!");
        alert.showAndWait();
    }
}