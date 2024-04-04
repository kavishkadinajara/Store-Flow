import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewSearchBorrower extends JDialog {
    private JPanel panelSearchBorrower;
    private JFormattedTextField txtSearchBar;
    private JButton btnSearch;
    private JTable tableBorrowers;
    private JButton btnHome;
    private JTable tableBorrowerList;

    public ViewSearchBorrower() {
        setTitle("Search & View Borrower");
        setContentPane(panelSearchBorrower);
        setMinimumSize(new Dimension(900, 600));
        setModal(true);

        loadBorrowerList();

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBorrower();
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

    private void searchBorrower() {
        String searchName = txtSearchBar.getText().trim(); // Trim leading and trailing spaces

        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();

        try {
            // Create an SQL SELECT statement for borrowers
            String selectQueryBorrower;

            if (searchName.isEmpty()) {
                // When search bar is empty, select all rows
                selectQueryBorrower = "SELECT * FROM borrower";
            } else {
                // When search bar is not empty, search by name
                selectQueryBorrower = "SELECT * FROM borrower WHERE borrower_name LIKE ?";
            }

            PreparedStatement preparedStatement = connection.prepareStatement(selectQueryBorrower);

            if (!searchName.isEmpty()) {
                preparedStatement.setString(1, "%" + searchName + "%"); // Use wildcard for partial matches
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Telephone No.");
            tableModel.addColumn("Type");

            tableBorrowerList.setModel(tableModel);

            while (resultSet.next()) {
                int id = resultSet.getInt("borrower_id");
                String name = resultSet.getString("borrower_name");
                String telephone = resultSet.getString("borrower_tele");
                String type = resultSet.getString("borrower_type");

                tableModel.addRow(new Object[]{id, name, telephone, type});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionDB.closeConnection();
        }
    }

    private void loadBorrowerList() {
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();

        try {
            // Create an SQL SELECT statement for borrowers
            String selectQueryBorrower = "SELECT * FROM borrower";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQueryBorrower);
            ResultSet resultSet = preparedStatement.executeQuery();

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Telephone No.");
            tableModel.addColumn("Type");

            tableBorrowerList.setModel(tableModel);

            while (resultSet.next()) {
                int id = resultSet.getInt("borrower_id");
                String name = resultSet.getString("borrower_name");
                String telephone = resultSet.getString("borrower_tele");
                String type = resultSet.getString("borrower_type");

                tableModel.addRow(new Object[]{id, name, telephone, type});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionDB.closeConnection();
        }
    }
}
