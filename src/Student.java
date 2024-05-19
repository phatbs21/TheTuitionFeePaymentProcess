
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


class Student {
    private UI ui;
    private Controller controller;

    public Student(UI ui, Controller controller) {
        this.ui = ui;
        this.controller = controller;
    }

    public void loginToWebsite(String username, String password) {
        ui.login(username, password);
    }

    public void clickViewFeeButton() {
        ui.displayTuition();
    }

    public void enterBankDetails(String bankNumber, String cardOrAccount, String otp) {
        WebsiteAppBank bank = new WebsiteAppBank();
        bank.sendPaymentRequest(bankNumber, cardOrAccount, otp);
    }
}


class UI {
    private Controller controller;

    public UI(Controller controller) {
        this.controller = controller;
    }

    public void login(String username, String password) {
        controller.findStudent(username, password);
    }

    public void displayInfo() {

    }

    public void displayTuition() {
        controller.queryTuition();
    }
}


class Controller {
    private DatabaseConnection dbConnection;

    public Controller(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void findStudent(String username, String password) {
        StudentInfo studentInfo = dbConnection.findStudent(username, password);
        if (studentInfo != null) {

        }
    }

    public void queryTuition() {
        double tuition = dbConnection.queryTuition();

    }
}


class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {

        try {
            connection = DriverManager.getConnection("jdbc:yourdatabaseurl", "username", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StudentInfo findStudent(String username, String password) {

        try {
            String query = "SELECT * FROM students WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new StudentInfo(rs.getString("name"), rs.getDouble("tuition"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public double queryTuition() {
        try {
            String query = "SELECT tuition FROM students";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("tuition");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}

class StudentInfo {
    private String name;
    private double tuition;

    public StudentInfo(String name, double tuition) {
        this.name = name;
        this.tuition = tuition;
    }

    public String getName() {
        return name;
    }

    public double getTuition() {
        return tuition;
    }
}

class WebsiteAppBank {
    public void sendPaymentRequest(String bankNumber, String cardOrAccount, String otp) {
        BIDVGate bidvGate = new BIDVGate();
        boolean authResult = bidvGate.checkAuthentication(bankNumber, cardOrAccount);
        if (authResult) {
            boolean otpResult = bidvGate.otpVerification(otp);
            if (otpResult) {
                displayResult(true);
            } else {
                displayResult(false);
            }
        } else {
            displayResult(false);
        }
    }

    public void displayResult(boolean success) {
        if (success) {
            System.out.println("Payment successful");
        } else {
            System.out.println("Payment failed");
        }
    }
}


class BIDVGate {
    public boolean checkAuthentication(String bankNumber, String cardOrAccount) {
        BIDVBank bank = new BIDVBank();
        return bank.authenticate(bankNumber, cardOrAccount);
    }

    public boolean otpVerification(String otp) {
        BIDVBank bank = new BIDVBank();
        return bank.verifyOTP(otp);
    }
}

class BIDVBank {
    public boolean authenticate(String bankNumber, String cardOrAccount) {
        return true;
    }

    public boolean verifyOTP(String otp) {
        return true;
    }
}

class MainApp {
    public static void main(String[] args) {

        DatabaseConnection dbConnection = new DatabaseConnection();
        Controller controller = new Controller(dbConnection);
        UI ui = new UI(controller);
        Student student = new Student(ui, controller);


        student.loginToWebsite("studentUsername", "studentPassword");


        student.clickViewFeeButton();


        student.enterBankDetails("12345678", "cardOrAccountNumber", "123456");
    }
}