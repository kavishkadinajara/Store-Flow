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

public class Repayment extends JDialog {
    private JPanel panelRepayment;
    private JPanel panelRecivedMoney;
    private JLabel lblBorrowdItemID;
    private JFormattedTextField txtBorrowedItemId;
    private JFormattedTextField txtRepaymentAmountRM;
    private JLabel lblTotLoanRM;
    private JLabel lblamount;
    private JButton btnOk;
    private JButton btnFinishRM;
    private JLabel lblBorrowName;
    private JButton btnHome;
    private JLabel lblDate;
    private JLabel lblWarningMsgRM;
    private JPanel panal;
    private boolean okButtonClicked = false; // Flag to track OK button click

    public Repayment() {
        setTitle("Borrowed Item Bills");
        setContentPane(panelRepayment);
        setMinimumSize(new Dimension(900, 600));
        setModal(true);

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        lblDate.setText(String.valueOf(date));

        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                okButtonClicked = true; // Set the flag when OK is clicked
                findBillId();
            }
        });

        btnFinishRM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (okButtonClicked) { // Check if OK button was clicked
                    payBill();
                } else {
                    lblWarningMsgRM.setText("Please click OK first.");
                }
            }
        });

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Home home = new Home();
                home.show();
            }
        });

        setVisible(true);

    }

    private void payBill() {
        int borrowedItemID = Integer.parseInt(txtBorrowedItemId.getText());
        double BillAmount = findBillId();
        double payAmount = Double.parseDouble(txtRepaymentAmountRM.getText());

        if (txtBorrowedItemId == null || txtRepaymentAmountRM == null) {
            lblWarningMsgRM.setText("Please enter fields");
        } else {
            if (payAmount > BillAmount || payAmount < 0) {
                lblWarningMsgRM.setText("Wrong amount! Please fix.");
            } else {
                lblWarningMsgRM.setText(null);
                BillAmount = BillAmount - payAmount;
                lblTotLoanRM.setText("New loan amount is --> Rs." + BillAmount);

                long millis = System.currentTimeMillis();
                java.sql.Date date = new java.sql.Date(millis);
                lblDate.setText(String.valueOf(date));

                ConnectionDB connectionDB = new ConnectionDB();
                Connection connection = connectionDB.getConnection();

                try {
                    // Create an SQL INSERT statement for received_loan
                    String insertQueryReceivedLoan = "INSERT INTO repayment (repayment_date, repayment_amount, borrowed_item_id) VALUES (?, ?, ?)";

                    // Prepare the statement with parameters
                    PreparedStatement preparedStatement1 = connection.prepareStatement(insertQueryReceivedLoan);
                    // Set the parameter values
                    preparedStatement1.setDate(1, date);
                    preparedStatement1.setDouble(2, payAmount);
                    preparedStatement1.setInt(3, borrowedItemID); // Assuming selectedBorrowType is a String
                    // Execute the INSERT statement
                    int rowsAffected1 = preparedStatement1.executeUpdate();

                    if (rowsAffected1 > 0) {
                        // Insert successful
                    } else {
                        // Insert failed
                    }

                    // Create an SQL DELETE statement for leading_loan
                    String deleteQueryLeadingLoan = " UPDATE borrowed_item SET borrowed_item_amount = ? WHERE borrowed_item_id = ?";

                    // Prepare the statement with parameters
                    PreparedStatement preparedStatement2 = connection.prepareStatement(deleteQueryLeadingLoan);
                    // Set the parameter values
                    preparedStatement2.setDouble(1, BillAmount);
                    preparedStatement2.setInt(2, borrowedItemID);
                    // Execute the DELETE statement
                    int rowsAffected2 = preparedStatement2.executeUpdate();

                    // Create an SQL INSERT statement for leading_loan
                    String insertQueryLeadingLoan = "INSERT INTO leading_loan (leading_date, leading_amount, borrower_id) VALUES (?, ?, ?)";

                    // Create an SQL SELECT statement for repayment
                    String selectQueryReceivedLoan = "SELECT repayment_id FROM repayment WHERE borrowed_item_id = ?";

                    PreparedStatement preparedStatement4 = connection.prepareStatement(selectQueryReceivedLoan);
                    preparedStatement4.setInt(1, borrowedItemID);
                    ResultSet receivedLoanIdResult = preparedStatement4.executeQuery();

                    java.util.List<Integer> repaymentIdsPK = new ArrayList<>(); // Create a list to store the received_loan_ids

                    while (receivedLoanIdResult.next()) {
                        int receivedLoanId = receivedLoanIdResult.getInt("repayment_id");
                        repaymentIdsPK.add(receivedLoanId);
                    }

                    // Create an SQL SELECT statement for transaction to get saved received loan id
                    String selectQueryTransaction = "SELECT repayment_id FROM transaction";

                    PreparedStatement preparedStatement6 = connection.prepareStatement(selectQueryTransaction);
                    ResultSet transactionResult = preparedStatement6.executeQuery();

                    List<Integer> receivedLoanIdFK = new ArrayList<>(); // Create a list to store the received_loan_ids from transaction table

                    while (transactionResult.next()) {
                        int repaymentId = transactionResult.getInt("repayment_id");
                        receivedLoanIdFK.add(repaymentId);
                    }

                    // Now you have all the received_loan_ids from the transaction table in the receivedLoanIdFK list.

                    // Create an SQL INSERT statement for transaction
                    String insertQueryTransaction = "INSERT INTO transaction (transaction_date, outcome, repayment_id) VALUES (?, ?, ?)";

                    for (int repaymentId : repaymentIdsPK) {
                        // Check if the receivedLoanId already exists in the receivedLoanIdFK list
                        if (!receivedLoanIdFK.contains(repaymentId)) {
                            PreparedStatement preparedStatement5 = connection.prepareStatement(insertQueryTransaction);
                            preparedStatement5.setDate(1, date);
                            preparedStatement5.setDouble(2, payAmount);
                            preparedStatement5.setInt(3, repaymentId);
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
                    preparedStatement4.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    // Close the database connection when done
                    connectionDB.closeConnection();
                }
            }

        }
    }

    private double findBillId() {
        int borrowedItemID = Integer.parseInt(txtBorrowedItemId.getText());
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();

        double totalLoanAmount = 0;
        String companyName = "";

        if (txtBorrowedItemId == null) {
            lblWarningMsgRM.setText("Please enter ID");
        } else {

            try {
                String selectLoanAmount = "SELECT borrowed_item_amount FROM borrowed_item WHERE borrowed_item_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(selectLoanAmount);
                preparedStatement2.setInt(1, borrowedItemID);
                ResultSet loanAmountResult = preparedStatement2.executeQuery();

                if (loanAmountResult.next()) {
                    totalLoanAmount = loanAmountResult.getDouble("borrowed_item_amount");
                }

                String selectNameQuery = "SELECT company_name FROM borrowed_item WHERE borrowed_item_id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(selectNameQuery);
                preparedStatement1.setInt(1, borrowedItemID);
                ResultSet nameResult = preparedStatement1.executeQuery();

                if (nameResult.next()) {
                    companyName = nameResult.getString("company_name");
                }

                lblBorrowName.setText(companyName);
                lblTotLoanRM.setText(String.valueOf("Bill amount --> Rs." + totalLoanAmount));
            } catch (SQLException e) {
                lblBorrowName.setText("User not found.");
            } finally {
                // Close the database connection when done
                connectionDB.closeConnection();
            }

        }
        return totalLoanAmount;
    }
}
