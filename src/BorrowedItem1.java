import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BorrowedItem1 extends JDialog {
    private JFormattedTextField txtCompanyBI;
    private JFormattedTextField txtAmountBI;
    private JButton btnEnterBI;
    private JTable tableBorrowedItem;
    private JButton btnHome;
    private JLabel lblDueDateBI;
    private JLabel lblCompanyBI;
    private JLabel lblAmountBI;
    private JFormattedTextField txtDueDate;
    private JPanel panelBorrowedItem;
    private JButton btnClear;

    public BorrowedItem1() {
        setTitle("Borrowed Item Bills");
        setContentPane(panelBorrowedItem);
        setMinimumSize(new Dimension(900, 600));
        setModal(true);

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        txtDueDate.setText(String.valueOf(date));

        loadBorrowedItemTable();

        btnEnterBI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrowedItemBill();
            }
        });

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Home home = new Home();
                show();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        setVisible(true);
    }

    private void clearFields() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        txtDueDate.setText(String.valueOf(date));
        txtAmountBI.setText("");
        txtCompanyBI.setText("");
    }

    private void borrowedItemBill() {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        Date dueDate = Date.valueOf(txtDueDate.getText());
        String company = txtCompanyBI.getText();
        double amount = Double.parseDouble(txtAmountBI.getText());

        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();

        try {
            String insertQueryBorrowedItem = "INSERT INTO borrowed_item (borrowed_item_date, borrowed_item_amount, company_name, deu_date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQueryBorrowedItem);
            preparedStatement.setDate(1, date);
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, company);
            preparedStatement.setDate(4, dueDate);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Insert successful
            } else {
                // Insert failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionDB.closeConnection();
        }

        loadBorrowedItemTable();
    }

    private void loadBorrowedItemTable() {
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();

        try {
            String selectQueryBorrowedItem = "SELECT * FROM borrowed_item";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQueryBorrowedItem);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Date");
            tableModel.addColumn("Amount");
            tableModel.addColumn("Company");
            tableModel.addColumn("Due Date");

            tableBorrowedItem.setModel(tableModel);

            while (resultSet.next()) {
                int id = resultSet.getInt("borrowed_item_id");
                Date date = resultSet.getDate("borrowed_item_date");
                double amount = resultSet.getDouble("borrowed_item_amount");
                String company = resultSet.getString("company_name");
                Date dueDate = resultSet.getDate("deu_date");

                tableModel.addRow(new Object[]{id, date, amount, company, dueDate});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionDB.closeConnection();
        }
    }
}
