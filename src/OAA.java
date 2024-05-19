import java.util.ArrayList;
import java.util.List;

public class OAA {

    public class Main {


        public static class Subjects {
            private String subjects;

            public Subjects(String subjects) {
                this.subjects = subjects;
            }

            public String getSubjects() {
                return subjects;
            }
        }

        public static class Bill {
            private int id;
            private double amount;

            public Bill(int id, double amount) {
                this.id = id;
                this.amount = amount;
            }

            public int getId() {
                return id;
            }

            public double getAmount() {
                return amount;
            }
        }

        public static class StudentInfo {
            private int id;
            private double balance;

            public StudentInfo(int id, double balance) {
                this.id = id;
                this.balance = balance;
            }

            public void updateMoney(double money) {
                this.balance += money;
            }

            public int getId() {
                return id;
            }

            public double getBalance() {
                return balance;
            }
        }

        public static class Info {
            private String info;

            public Info(String info) {
                this.info = info;
            }

            public String getInfo() {
                return info;
            }
        }


        public static class DatabaseConnection {
            private List<Bill> bills = new ArrayList<>();
            private List<StudentInfo> studentInfos = new ArrayList<>();

            public DatabaseConnection() {

                bills.add(new Bill(1, 100.0));
                studentInfos.add(new StudentInfo(1, 500.0));
            }

            public boolean pushData(Subjects subjects) {

                System.out.println("Pushing subjects data: " + subjects.getSubjects());
                return true;
            }

            public List<Bill> queryAllDataInTable() {
                return bills;
            }

            public StudentInfo findObj(int id) {
                for (StudentInfo studentInfo : studentInfos) {
                    if (studentInfo.getId() == id) {
                        return studentInfo;
                    }
                }
                return null;
            }

            public boolean pushData(StudentInfo studentInfo) {

                System.out.println("Updating student balance: " + studentInfo.getBalance());
                return true;
            }

            public boolean updateInfoInDatabase(Info info) {

                System.out.println("Updating info: " + info.getInfo());
                return true;
            }
        }


        public static class Controller {
            private DatabaseConnection dbConnection;

            public Controller(DatabaseConnection dbConnection) {
                this.dbConnection = dbConnection;
            }

            public boolean updateRegisteredSubjects(String subjects) {
                Subjects subjectObj = new Subjects(subjects);
                return dbConnection.pushData(subjectObj);
            }

            public List<Bill> listAllBillReceive() {
                return dbConnection.queryAllDataInTable();
            }

            public boolean updateMoney(int id, double money) {
                StudentInfo studentInfo = dbConnection.findObj(id);
                if (studentInfo != null) {
                    studentInfo.updateMoney(money);
                    return dbConnection.pushData(studentInfo);
                }
                return false;
            }

            public boolean updateInformation(String info) {
                Info infoObj = new Info(info);
                return dbConnection.updateInfoInDatabase(infoObj);
            }
        }


        public static class UI {
            private Controller controller;

            public UI(Controller controller) {
                this.controller = controller;
            }

            public void enterRegisteredSubjects(String subjects) {
                boolean result = controller.updateRegisteredSubjects(subjects);
                displayResult(result);
            }

            public void listAllBillNeedToProcess() {
                List<Bill> bills = controller.listAllBillReceive();
                displayData(bills);
            }

            public void billConfirm(int id, double money) {
                boolean result = controller.updateMoney(id, money);
                displayResult(result);
            }

            public void enterUpdatedInformation(String info) {
                boolean result = controller.updateInformation(info);
                displayUpdateResult(result);
            }

            private void displayResult(boolean success) {
                if (success) {
                    System.out.println("Update Successful");
                } else {
                    System.out.println("Update Failed");
                }
            }

            private void displayData(List<Bill> bills) {
                for (Bill bill : bills) {
                    System.out.println("Bill ID: " + bill.getId() + ", Amount: " + bill.getAmount());
                }
            }

            private void displayUpdateResult(boolean success) {
                if (success) {
                    System.out.println("Update Information Successful");
                } else {
                    System.out.println("Update Information Failed");
                }
            }
        }


        public static void main(String[] args) {
            DatabaseConnection dbConnection = new DatabaseConnection();
            Controller controller = new Controller(dbConnection);
            UI ui = new UI(controller);



            ui.enterRegisteredSubjects("Math, Science, English");


            ui.listAllBillNeedToProcess();
            ui.billConfirm(1, 200.0);


            ui.enterUpdatedInformation("New Course Information");
        }
    }
}
