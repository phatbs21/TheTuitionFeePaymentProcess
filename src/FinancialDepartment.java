public class FinancialDepartment {

    public static void main(String[] args) {
        FinancialDepartment department = new FinancialDepartment();
        department.enterBill(12345, 1000.0);
        department.updateTuition(67890, 500.0);
    }

    public void enterBill(int studentId, double amount) {
        UI ui = new UI();
        ui.enterBill(studentId, amount);
    }

    public void updateTuition(int subjectId, double amount) {
        UI ui = new UI();
        ui.enterTuition(subjectId, amount);
    }

    class UI {

        void enterBill(int studentId, double amount) {
            Controller controller = new Controller();
            controller.createBill(studentId, amount);
        }

        void enterTuition(int subjectId, double amount) {
            Controller controller = new Controller();
            controller.updateTuition(subjectId, amount);
        }

        void displayResult(String result) {
            System.out.println(result);
        }
    }

    class Controller {

        void createBill(int studentId, double amount) {
            BillFinance billFinance = new BillFinance();
            boolean success = billFinance.pushBillInfo(studentId, amount);
            if (success) {
                sendBillToOAA(studentId, amount);
                new UI().displayResult("Bill creation successful.");
            }
        }

        void updateTuition(int subjectId, double amount) {
            Subject subject = new Subject();
            boolean success = subject.updateMoney(subjectId, amount);
            if (success) {
                calculateTotalTuition(subjectId);
                new UI().displayResult("Tuition update successful.");
            }
        }

        void sendBillToOAA(int studentId, double amount) {
            // Logic to send bill to OAA
        }

        void calculateTotalTuition(int subjectId) {
            // Logic to calculate total tuition
        }
    }

    class BillFinance {

        boolean pushBillInfo(int studentId, double amount) {
            DatabaseConnector dbConnector = new DatabaseConnector();
            return dbConnector.pushData("INSERT INTO Bills (StudentID, Amount) VALUES (" + studentId + ", " + amount + ")");
        }
    }

    class Subject {

        boolean updateMoney(int subjectId, double amount) {
            DatabaseConnector dbConnector = new DatabaseConnector();
            return dbConnector.pushData("UPDATE Subjects SET Amount = " + amount + " WHERE SubjectID = " + subjectId);
        }
    }

    class DatabaseConnector {

        boolean pushData(String sql) {
            // Logic to push data to the database
            // Simulating database operation success
            return true;
        }
    }
}