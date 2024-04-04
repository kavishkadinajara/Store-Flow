import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewTransaction extends JDialog{
    private JPanel panelTransaction;
    private JButton btnAllTransaction;
    private JButton btnRecivedLoan;
    private JButton btnPurchesItem;
    private JButton btnRepayment;
    private JTable tableTransaction;
    private JButton btnHome;
    private JScrollPane scrollVT;

    public ViewTransaction(){
        setTitle("Lone Add & View");
        setContentPane(panelTransaction);
        setMinimumSize(new Dimension(900,600));
        setModal(true);


        btnAllTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allTransaction();
            }
        });
        btnRecivedLoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receivedLoan();
            }
        });
        btnPurchesItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PurchesItem();
            }
        });
        btnRepayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repayment();
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

    private void repayment() {
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Create an SQL SELECT statement for transactions
            String selectQueryTransaction = "SELECT * FROM repayment";
            preparedStatement = connection.prepareStatement(selectQueryTransaction);
            resultSet = preparedStatement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Date");
            tableModel.addColumn("Amount");
            tableModel.addColumn("Borrowed Bill ID");

            tableTransaction.setModel(tableModel);

            while (resultSet.next()) {
                int id = resultSet.getInt("repayment_id");
                Date date = resultSet.getDate("repayment_date");
                double amount = resultSet.getDouble("repayment_amount");
                int borrowedId = resultSet.getInt("borrowed_item_id");

                tableModel.addRow(new Object[]{id, date, amount, borrowedId});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close ResultSet, PreparedStatement, and Connection in a finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            connectionDB.closeConnection();
        }
    }

    private void PurchesItem() {
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Create an SQL SELECT statement for transactions
            String selectQueryTransaction = "SELECT * FROM purchesed_item";
            preparedStatement = connection.prepareStatement(selectQueryTransaction);
            resultSet = preparedStatement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Date");
            tableModel.addColumn("Company Name");
            tableModel.addColumn("Amount");

            tableTransaction.setModel(tableModel);

            while (resultSet.next()) {
                int id = resultSet.getInt("purchesed_item_id");
                Date date = resultSet.getDate("purchesed_item_date");
                String companyName = resultSet.getString("company_name");
                double amount = resultSet.getDouble("purchesed_item_amount");

                tableModel.addRow(new Object[]{id, date, companyName, amount});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close ResultSet, PreparedStatement, and Connection in a finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            connectionDB.closeConnection();
        }
    }

    private void receivedLoan() {
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Create an SQL SELECT statement for transactions
            String selectQueryTransaction = "SELECT * FROM recived_loan";
            preparedStatement = connection.prepareStatement(selectQueryTransaction);
            resultSet = preparedStatement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Date");
            tableModel.addColumn("Amount");
            tableModel.addColumn("Borrower ID");

            tableTransaction.setModel(tableModel);

            while (resultSet.next()) {
                int id = resultSet.getInt("recived_loan_id");
                Date date = resultSet.getDate("recived_loan_date");
                double amount = resultSet.getDouble("recived_loan_amount");
                int borrowId = resultSet.getInt("borrower_id");

                tableModel.addRow(new Object[]{id, date, amount, borrowId});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close ResultSet, PreparedStatement, and Connection in a finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            connectionDB.closeConnection();
        }
    }

    private void allTransaction() {
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Create an SQL SELECT statement for transactions
            String selectQueryTransaction = "SELECT * FROM transaction";
            preparedStatement = connection.prepareStatement(selectQueryTransaction);
            resultSet = preparedStatement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Date");
            tableModel.addColumn("Income");
            tableModel.addColumn("Outcome");
            tableModel.addColumn("Repayment ID");
            tableModel.addColumn("Received Loan ID");
            tableModel.addColumn("Purchased ID");

            tableTransaction.setModel(tableModel);

            while (resultSet.next()) {
                int id = resultSet.getInt("transaction_id");
                Date date = resultSet.getDate("transaction_date");
                double income = resultSet.getDouble("income");
                double outcome = resultSet.getDouble("outcome");
                int RepaymentId = resultSet.getInt("repayment_id");
                int ReceivedId = resultSet.getInt("recived_loan_id");
                int purchasedId = resultSet.getInt("purchesed_item_id");

                tableModel.addRow(new Object[]{id, date, income, outcome, RepaymentId, ReceivedId, purchasedId});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Close ResultSet, PreparedStatement, and Connection in a finally block
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            connectionDB.closeConnection();
        }
    }

}
