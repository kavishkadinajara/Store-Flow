import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class NewBorrower extends JDialog{
    private JFormattedTextField txtNameBorrower;
    private JFormattedTextField txtTele;
    private JComboBox comboBoxBorrowType;
    private JButton btnSave;
    private JButton btnCancel;
    private JButton btnHome;
    private JLabel lblName;
    private JLabel lblTele;
    private JLabel lblType;
    private JPanel panalNewBorrower;
    private JLabel borrowErrorMsg;
    private JLabel borrowSuccMsg;

    public NewBorrower()
    {
        //super(parent);
        setTitle("New Borrower");
        setContentPane(panalNewBorrower);
        setMinimumSize(new Dimension(800,500));
        setModal(true);
       // setLocationRelativeTo(parent);

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Home home = new Home();
                home.show();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newBorrower();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeFormEmpty();
                borrowSuccMsg.setText(null);
                borrowErrorMsg.setText(null);
            }
        });

        setVisible(true);
    }

    private void makeFormEmpty(){
        txtNameBorrower.setText(null);
        txtTele.setText(null);
        comboBoxBorrowType.setSelectedIndex(0);
    }

    private void newBorrower() {
        String borrowerName = txtNameBorrower.getText();
        String borrowerTele = txtTele.getText();
        Object selectedBorrowType = comboBoxBorrowType.getSelectedItem();

        if (borrowerName.isEmpty() || borrowerTele.isEmpty() || selectedBorrowType == null) {
            borrowErrorMsg.setText("Please fill all fields..");
            borrowSuccMsg.setText(null);
        } else {
            borrowErrorMsg.setText(null);
            ConnectionDB connectionDB = new ConnectionDB();
            Connection connection = connectionDB.getConnection();

            try {
                // Create an SQL INSERT statement
                String insertQuery = "INSERT INTO borrower (borrower_name,borrower_tele,borrower_type) VALUES (?, ?, ?)";

                // Prepare the statement with parameters
                PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

                // Set the parameter values
                preparedStatement.setString(1, borrowerName);
                preparedStatement.setString(2, borrowerTele);
                preparedStatement.setString(3, selectedBorrowType.toString()); // Assuming selectedBorrowType is a String

                // Execute the INSERT statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Insert successful
                    makeFormEmpty();
                    borrowSuccMsg.setText("Borrower added successfully.");
                } else {
                    // Insert failed
                    borrowErrorMsg.setText("Failed to added borrower.");
                }

                // Close the prepared statement
                preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Close the database connection when done
                connectionDB.closeConnection();
            }
        }
    }


}
