import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoanGiven extends JDialog {
    private JLabel lblDateMarkLoan;
    private JFormattedTextField txtBorrowIDML;
    private JFormattedTextField txtAmountLM;
    private JButton btnAddML;
    private JButton btnViweAllML;
    private JTable tableLoanList;
    private JLabel lblAmount;
    private JLabel lblBID;
    private JPanel panalMarkLoan;
    private JLabel lblBorrowNameML;
    private JLabel lblLoanTot;
    private JLabel lblTotAmountML;
    private JButton btnHomeML;

    public LoanGiven() {
        setTitle("Loan Add & View");
        setContentPane(panalMarkLoan);
        setMinimumSize(new Dimension(900, 600));
        setModal(true);

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        lblDateMarkLoan.setText(String.valueOf(date));

        btnAddML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblBorrowNameML.setText("");
                addBorrowLoan();
            }
        });

        btnViweAllML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBorrowDetailsTable();
            }
        });

        btnHomeML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Home home = new Home();
                home.setVisible(true);
            }
        });

        setVisible(true);
    }

    private void addBorrowLoan() {
        try {
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
            double loanAmount = Double.parseDouble(txtAmountLM.getText());
            int borrowID = Integer.parseInt(txtBorrowIDML.getText());

            ConnectionDB connectionDB = new ConnectionDB();
            Connection connection = connectionDB.getConnection();

            try {
                // Step 2: Fetch borrower's name based on borrower ID
                String selectNameQuery = "SELECT borrower_name FROM borrower WHERE borrower_id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(selectNameQuery);
                preparedStatement1.setInt(1, borrowID);
                ResultSet nameResult = preparedStatement1.executeQuery();
                String borrowerName = "";

                if (nameResult.next()) {
                    borrowerName = nameResult.getString("borrower_name");
                }

                // Step 3: Fetch the total loan amount for the borrower
                String selectLoanAmount = "SELECT SUM(leading_amount) AS total_amount FROM leading_loan WHERE borrower_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(selectLoanAmount);
                preparedStatement2.setInt(1, borrowID);
                ResultSet loanAmountResult = preparedStatement2.executeQuery();
                double totalLoanAmount = 0;

                if (loanAmountResult.next()) {
                    totalLoanAmount = loanAmountResult.getDouble("total_amount");
                }

                // Step 1: Insert the new loan record
                String insertQuery = "INSERT INTO leading_loan (leading_date, leading_amount, borrower_id) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setDate(1, date);
                preparedStatement.setDouble(2, loanAmount);
                preparedStatement.setInt(3, borrowID);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Insert successful
                    lblBorrowNameML.setText("Borrower " + borrowerName + " loan amount added successfully.");
                    lblTotAmountML.setText("Rs." + (totalLoanAmount + loanAmount));
                } else {
                    // Insert failed
                    lblBorrowNameML.setText("Failed to add borrower.");
                }

                displayBorrowDetailsTable();

                // Close the prepared statements
                preparedStatement.close();
                preparedStatement1.close();
                preparedStatement2.close();
            } catch (SQLException e) {
                // Handle the exception, show an error message, or log it
                e.printStackTrace();
            } finally {
                // Close the database connection when done
                connectionDB.closeConnection();
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format (e.g., loanAmount or borrowID)
            lblBorrowNameML.setText("Invalid loan amount or borrower ID.");
        }
    }

    private void displayBorrowDetailsTable() {
        try {
            int borrowID = Integer.parseInt(txtBorrowIDML.getText());

            ConnectionDB connectionDB = new ConnectionDB();
            Connection connection = connectionDB.getConnection();

            try {
                borrowerDetails();

                String displayToTable = "SELECT leading_date, leading_amount FROM leading_loan WHERE borrower_id = ?";
                PreparedStatement preparedStatement3 = connection.prepareStatement(displayToTable);
                preparedStatement3.setInt(1, borrowID);
                ResultSet displayToTableResult = preparedStatement3.executeQuery();

                DefaultTableModel tableModel = new DefaultTableModel();
                tableModel.addColumn("Date");
                tableModel.addColumn("Amount");

                // Set the table model for your tableLoanList
                tableLoanList.setModel(tableModel);

                // Iterate through the result set and add rows to the table model
                while (displayToTableResult.next()) {
                    java.sql.Date date1 = displayToTableResult.getDate("leading_date");
                    double amount = displayToTableResult.getDouble("leading_amount");

                    // Add a new row to the table model
                    tableModel.addRow(new Object[]{date1, amount});
                }

                // Close the prepared statement and result set
                preparedStatement3.close();
                displayToTableResult.close();
            } catch (SQLException e) {
                // Handle the exception, show an error message, or log it
                e.printStackTrace();
                lblBorrowNameML.setText("Error displaying details.");
            } catch (Exception e) {
                // Handle the exception, show an error message, or log it
                e.printStackTrace();
                lblBorrowNameML.setText("Error displaying details.");
            } finally {
                // Close the database connection when done
                connectionDB.closeConnection();
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format for borrowID
            lblBorrowNameML.setText("Invalid borrower ID.");
        }
    }

    private void borrowerDetails() {
        try {
            int borrowID = Integer.parseInt(txtBorrowIDML.getText());

            ConnectionDB connectionDB = new ConnectionDB();
            Connection connection = connectionDB.getConnection();

            try {
                // Step 2: Fetch borrower's name based on borrower ID
                String selectNameQuery = "SELECT borrower_name FROM borrower WHERE borrower_id = ?";
                PreparedStatement preparedStatement1 = connection.prepareStatement(selectNameQuery);
                preparedStatement1.setInt(1, borrowID);
                ResultSet nameResult = preparedStatement1.executeQuery();
                String borrowerName = "";

                if (nameResult.next()) {
                    borrowerName = nameResult.getString("borrower_name");
                }

                // Step 3: Fetch the total loan amount for the borrower
                String selectLoanAmount = "SELECT SUM(leading_amount) AS total_amount FROM leading_loan WHERE borrower_id = ?";
                PreparedStatement preparedStatement2 = connection.prepareStatement(selectLoanAmount);
                preparedStatement2.setInt(1, borrowID);
                ResultSet loanAmountResult = preparedStatement2.executeQuery();
                double totalLoanAmount = 0;

                if (loanAmountResult.next()) {
                    totalLoanAmount = loanAmountResult.getDouble("total_amount");
                }

                lblBorrowNameML.setText(borrowerName);
                lblTotAmountML.setText("Rs." + totalLoanAmount);

            } catch (SQLException e) {
                // Handle the exception, show an error message, or log it
                e.printStackTrace();
                lblBorrowNameML.setText("User not found.");
            } finally {
                // Close the database connection when done
                connectionDB.closeConnection();
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format for borrowID
            lblBorrowNameML.setText("Invalid borrower ID.");
        }
    }
}
