import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JDialog{
    private JPanel homePanal;
    private JButton btnReciveLoan;
    private JButton btnAddLoanBorro;
    private JButton btnNewBorro;
    private JButton btnBorroItem;
    private JButton btnRepayment;
    private JButton btnViewTransaction;
    private JButton viewSearchBorrowerListButton;

    public Home(){
        setTitle("Borrowed Item Bills");
        setContentPane(homePanal);
        setMinimumSize(new Dimension(900, 600));
        setModal(true);

        btnNewBorro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                NewBorrower newBorrower = new NewBorrower();
                newBorrower.setVisible(true);
            }
        });
        viewSearchBorrowerListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewSearchBorrower viewSearchBorrower = new ViewSearchBorrower();
                viewSearchBorrower.setVisible(true);
            }
        });
        btnAddLoanBorro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoanGiven loanGiven = new LoanGiven();
                loanGiven.setVisible(true);
            }
        });
        btnReciveLoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoanRecived loanRecived = new LoanRecived();
                loanRecived.setVisible(true);
            }
        });
        btnBorroItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                BorrowedItem1 borrowedItem1 = new BorrowedItem1();
                borrowedItem1.setVisible(true);
            }
        });
        btnRepayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Repayment repayment = new Repayment();
                repayment.setVisible(true); // Set the visibility of 'repayment' instead of 'this'
            }
        });
        btnViewTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ViewTransaction viewTransaction = new ViewTransaction();
                viewTransaction.setVisible(true);
            }
        });


        setVisible(true);
    }
}
