package autumnmid;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Util {
    private static final String EMPLOYEE_FILE_PATH = "employee.bin";

    public static boolean addEmployee(Employee employee) {
        File file = new File(EMPLOYEE_FILE_PATH);
        boolean append = file.exists();
        try {
            FileOutputStream fos = new FileOutputStream(file, append);
            ObjectOutputStream oos = (append ? new AppendableObjectOutputStream(fos) : new ObjectOutputStream(fos));
            oos.writeObject(employee);
            oos.close();
            fos.close();
            return true;
        } catch (IOException ignored) {

        }
        return false;
    }

    public static List<Employee> getEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(EMPLOYEE_FILE_PATH);
            ois = new ObjectInputStream(fis);
            while (true) {
                employeeList.add((Employee) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException ignored) {

        } finally {
            try {
                fis.close();
                ois.close();
            } catch (Exception ignored) {

            }
        }
        return employeeList;
    }
}