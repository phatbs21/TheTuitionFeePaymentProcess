// Import necessary packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Student class
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

// UI class
class UI {
    private Controller controller;

    public UI(Controller controller) {
        this.controller = controller;
    }

    public void login(String username, String password) {
        controller.findStudent(username, password);
    }

    public void displayInfo() {
        // Display information
    }

    public void displayTuition() {
        controller.queryTuition();
    }
}

// Controller class
class Controller {
    private DatabaseConnection dbConnection;

    public Controller(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void findStudent(String username, String password) {
        StudentInfo studentInfo = dbConnection.findStudent(username, password);
        if (studentInfo != null) {
            // Return result to UI
        }
    }

    public void queryTuition() {
        double tuition = dbConnection.queryTuition();
        // Return tuition result to UI
    }
}

// DatabaseConnection class
class DatabaseConnection {
    private Connection connection;

    public DatabaseConnection() {
        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:yourdatabaseurl", "username", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public StudentInfo findStudent(String username, String password) {
        // Query database to find student info
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
        // Query database to get tuition
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

// StudentInfo class
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

// WebsiteAppBank class
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

// BIDVGate class
class BIDVGate {
    public boolean checkAuthentication(String bankNumber, String cardOrAccount) {
        // Simulate authentication check with the bank
        BIDVBank bank = new BIDVBank();
        return bank.authenticate(bankNumber, cardOrAccount);
    }

    public boolean otpVerification(String otp) {
        // Simulate OTP verification
        BIDVBank bank = new BIDVBank();
        return bank.verifyOTP(otp);
    }
}

// BIDVBank class
class BIDVBank {
    public boolean authenticate(String bankNumber, String cardOrAccount) {
        // Simulate successful authentication
        return true;
    }

    public boolean verifyOTP(String otp) {
        // Simulate successful OTP verification
        return true;
    }
}

// Main application class to demonstrate the sequence
class MainApp {
    public static void main(String[] args) {
        // Initialize components
        DatabaseConnection dbConnection = new DatabaseConnection();
        Controller controller = new Controller(dbConnection);
        UI ui = new UI(controller);
        Student student = new Student(ui, controller);

        // Student logs into the website
        student.loginToWebsite("studentUsername", "studentPassword");

        // Student clicks the button to view tuition fee
        student.clickViewFeeButton();

        // Student enters bank details to pay tuition fee
        student.enterBankDetails("12345678", "cardOrAccountNumber", "123456");
    }
}