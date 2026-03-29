package windbuilderguidedexercise;

 // guided no.1

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/// /////////////////////////////////////////////
public class DemoSwing2guided extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField txtName;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // ERROR FIXED: Removed the duplicate "public void run(){" line
                try {
                    DemoSwing2guided window = new DemoSwing2guided();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public DemoSwing2guided() {
    	setTitle("DemoSwing2guided");
        initialize();
    }

    private void initialize() {
        
        setBounds(100, 100, 539, 309);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Enter your name:");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(152, 48, 196, 39);
        getContentPane().add(lblNewLabel);

        JButton btnTest = new JButton("TEST");

       
        btnTest.addActionListener(new ActionListener() {

            
            public void actionPerformed(ActionEvent e) {
              
                JOptionPane.showMessageDialog(null, "Hello " + txtName.getText());
            }
        });
       

        btnTest.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnTest.setBounds(96, 160, 157, 39);
        getContentPane().add(btnTest);

        JButton btnClose = new JButton("CLOSE");
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "bye " + txtName.getText());
                dispose();
            }
        });

        btnClose.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnClose.setBounds(263, 160, 157, 39);
        getContentPane().add(btnClose);

        txtName = new JTextField();
        txtName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtName.setColumns(10);
        txtName.setBounds(152, 48, 204, 39);
        getContentPane().add(txtName);
    }
}