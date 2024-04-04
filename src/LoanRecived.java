import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanRecived extends JDialog {
    private JFormattedTextField txtBorrowIdRM;
    private JButton btnOk;
    private JFormattedTextField txtRecivedAmountRM;
    private JButton btnFinishRM;
    private JLabel lblTotLoanRM;
    private JLabel lblBorrowName;
    private JLabel lblid;
    private JLabel lblamount;
    private JButton btnHome;
    private JPanel panelRecivedMoney;
    private JLabel lblDateRM;
    private JLabel lblWarningMsgRM;

    public LoanRecived() {
        setTitle("Lone Add & View");
        setContentPane(panelRecivedMoney);
        setMinimumSize(new Dimension(900, 600));
        setModal(true);

        long millis = System.currentTimeMillis();
        // creating a new object of the class Date
        java.sql.Date date = new java.sql.Date(millis);
        lblDateRM.setText(String.valueOf(date));

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Home home = new Home();
                home.show();
            }
        });

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findBorrowerLoanAmount();
            }
        });

        btnFinishRM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                balanceLoan();
            }
        });

        setVisible(true);
    }

    private void balanceLoan() {
        int borrowID = Integer.parseInt(txtBorrowIdRM.getText());
        double totalLoanAmount = findBorrowerLoanAmount();
        double receivedLoanAmount = Double.parseDouble(txtRecivedAmountRM.getText());

        if (borrowID <= 0 || txtRecivedAmountRM.getText() == null) {
            lblWarningMsgRM.setText("Please enter fields");
        } else {
            if (receivedLoanAmount > totalLoanAmount || receivedLoanAmount < 0) {
                lblWarningMsgRM.setText("Wrong amount! Please fix.");
            } else {
                lblWarningMsgRM.setText(null);
                totalLoanAmount = totalLoanAmount - receivedLoanAmount;
                lblTotLoanRM.setText("New loan amount is --> Rs." + totalLoanAmount);

                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                lblDateRM.setText(String.valueOf(date));

                ConnectionDB connectionDB = new ConnectionDB();
                Connection connection = connectionDB.getConnection();

                try {
                    // Create an SQL INSERT statement for received_loan
                    String insertQueryReceivedLoan = "INSERT INTO recived_loan (recived_loan_date, recived_loan_amount, borrower_id) VALUES (?, ?, ?)";

                    // Prepare the statement with parameters
                    PreparedStatement preparedStatement1 = connection.prepareStatement(insertQueryReceivedLoan);
                    // Set the parameter values
                    preparedStatement1.setDate(1, date);
                    preparedStatement1.setDouble(2, receivedLoanAmount);
                    preparedStatement1.setInt(3, borrowID); // Assuming selectedBorrowType is a String
                    // Execute the INSERT statement
                    int rowsAffected1 = preparedStatement1.executeUpdate();

                    if (rowsAffected1 > 0) {
                        // Insert successful
                    } else {
                        // Insert failed
                    }

                    // Create an SQL DELETE statement for leading_loan
                    String deleteQueryLeadingLoan = " DELETE FROM leading_loan WHERE borrower_id = ?";

                    // Prepare the statement with parameters
                    PreparedStatement preparedStatement2 = connection.prepareStatement(deleteQueryLeadingLoan);
                    // Set the parameter values
                    preparedStatement2.setInt(1, borrowID);
                    // Execute the DELETE statement
                    int rowsAffected2 = preparedStatement2.executeUpdate();

                    // Create an SQL INSERT statement for leading_loan
                    String insertQueryLeadingLoan = "INSERT INTO leading_loan (leading_date, leading_amount, borrower_id) VALUES (?, ?, ?)";

                    PreparedStatement preparedStatement3 = connection.prepareStatement(insertQueryLeadingLoan);
                    preparedStatement3.setDate(1, date);
                    preparedStatement3.setDouble(2, totalLoanAmount);
                    preparedStatement3.setInt(3, borrowID);
                    int rowsAffected3 = preparedStatement3.executeUpdate();

                    if (rowsAffected3 > 0) {
                        // Insert successful
                    } else {
                        // Insert failed
                    }

                    // Create an SQL SELECT statement for received_loan
                    String selectQueryReceivedLoan = "SELECT recived_loan_id FROM recived_loan WHERE borrower_id = ?";

                    PreparedStatement preparedStatement4 = connection.prepareStatement(selectQueryReceivedLoan);
                    preparedStatement4.setInt(1, borrowID);
                    ResultSet receivedLoanIdResult = preparedStatement4.executeQuery();

                    List<Integer> receivedLoanIdsPK = new ArrayList<>(); // Create a list to store the received_loan_ids

                    while (receivedLoanIdResult.next()) {
                        int receivedLoanId = receivedLoanIdResult.getInt("recived_loan_id");
                        receivedLoanIdsPK.add(receivedLoanId);
                    }

                    // Create an SQL SELECT statement for transaction to get saved received loan id
                    String selectQueryTransaction = "SELECT recived_loan_id FROM transaction";

                    PreparedStatement preparedStatement6 = connection.prepareStatement(selectQueryTransaction);
                    ResultSet transactionResult = preparedStatement6.executeQuery();

                    List<Integer> receivedLoanIdFK = new ArrayList<>(); // Create a list to store the received_loan_ids from the transaction table

                    while (transactionResult.next()) {
                        int receivedLoanId = transactionResult.getInt("recived_loan_id");
                        receivedLoanIdFK.add(receivedLoanId);
                    }

                    // Now you have all the received_loan_ids from the transaction table in the receivedLoanIdFK list.

                    // Create an SQL INSERT statement for transaction
                    String insertQueryTransaction = "INSERT INTO transaction (transaction_date, income, recived_loan_id) VALUES (?, ?, ?)";

                    for (int receivedLoanId : receivedLoanIdsPK) {
                        // Check if the receivedLoanId already exists in the receivedLoanIdFK list
                        if (!receivedLoanIdFK.contains(receivedLoanId)) {
                            PreparedStatement preparedStatement5 = connection.prepareStatement(insertQueryTransaction);
                            preparedStatement5.setDate(1, date);
                            preparedStatement5.setDouble(2, receivedLoanAmount);
                            preparedStatement5.setInt(3, receivedLoanId);
                            int rowsAffected5 = preparedStatement5.executeUpdate();

                            if (rowsAffected5 > 0) {
                                // Insert successful
                            } else {
                                // Insert failed
                            }

                            preparedStatement5.close();
                        }
                    }
                    // Close the prepared statements
                    preparedStatement1.close();
                    preparedStatement2.close();
                    preparedStatement3.close();
                    preparedStatement4.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                    lblWarningMsgRM.setText("Error: Database operation failed.");
                } finally {
                    // Close the database connection when done
                    connectionDB.closeConnection();
                }
            }
        }
    }

    private double findBorrowerLoanAmount() {
        int borrowID = Integer.parseInt(txtBorrowIdRM.getText());
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();

        double totalLoanAmount = 0;
        String borrowerName = "";

        if (txtBorrowIdRM == null) {
            lblWarningMsgRM.setText("Please enter ID");
        } else {

            try {
                // Create an SQL SELECT statement for loan amount
                String selectLoanAmount = "SELECT SUM(leading_amount) AS total_amount FROM leading_loan WHERE borrower_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(selectLoanAmount);
                preparedStatement2.setInt(1, borrowID);
                ResultSet loanAmountResult = preparedStatement2.executeQuery();

                if (loanAmountResult.next()) {
                    totalLoanAmount = loanAmountResult.getDouble("total_amount");
                }

                // Create an SQL SELECT statement for borrower name
                String selectNameQuery = "SELECT borrower_name FROM borrower WHERE borrower_id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(selectNameQuery);
                preparedStatement1.setInt(1, borrowID);
                ResultSet nameResult = preparedStatement1.executeQuery();

                if (nameResult.next()) {
                    borrowerName = nameResult.getString("borrower_name");
                }

                lblBorrowName.setText(borrowerName);
                lblTotLoanRM.setText(String.valueOf("Total loan amount --> Rs." + totalLoanAmount));
            } catch (SQLException e) {
                e.printStackTrace();
                lblWarningMsgRM.setText("Error: Database operation failed.");
            } finally {
                // Close the database connection when done
                connectionDB.closeConnection();
            }

        }
        return totalLoanAmount;
    }

}
