package autumnmid;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilterSceneController implements Initializable {
    @FXML
    private TextField lowerBoundTextField;

    @FXML
    private TextField upperBoundTextField;

    @FXML
    private ComboBox<String> searchDeptComboBox;

    @FXML
    private Label searchResultLabel;

    private List<Employee> employeeList;


    public void setInitData(List<String> departments, List<Employee> employeeList) {
        searchDeptComboBox.getItems().addAll(departments);
        this.employeeList = employeeList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onSwitchScene(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MidExamScene.fxml"));
        Scene scene = new Scene(loader.load());
        MidExamSceneController controller = loader.getController();
        controller.setInitData(employeeList);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void showAvgSalaryButtonOnClicked(ActionEvent event) {
        String dept = searchDeptComboBox.getValue();
        float upper = Float.parseFloat(upperBoundTextField.getText());
        float lower = Float.parseFloat(lowerBoundTextField.getText());

        int count = 0;
        float totalSalary = 0;
        for (Employee employee : employeeList) {
            if (employee.getDept().equals(dept) && employee.getSalary() >= lower && employee.getSalary() <= upper) {
                count++;
                totalSalary += employee.getSalary();
            }
        }

        searchResultLabel.setText(
                "Average Salary of "
                        + dept
                        + " department having salary within "
                        + upper + " and " + lower
                        + " taka is: " + (count == 0 ? 0 : (totalSalary / count))
        );
    }
}