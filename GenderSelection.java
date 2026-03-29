package windbuilderguidedexercise;

 // guided no. 3

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GenderSelection {
    private JFrame frmJradioButtonExample;

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->{
            try {
                GenderSelection window = new GenderSelection();
                window.frmJradioButtonExample.setVisible(true);
            }catch (Exception e) {
                e.printStackTrace();
            }
        });




    }
public GenderSelection() {
        initialize();
}
private void initialize() {
        frmJradioButtonExample = new JFrame();
        frmJradioButtonExample.setTitle("GenderSelectionJRadioButton Example");
        frmJradioButtonExample.setBounds(100, 100,492,350);
        frmJradioButtonExample.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmJradioButtonExample.getContentPane().setLayout(null);

        JRadioButton rdoMale = new JRadioButton("Male");
        rdoMale.setFont(new Font("Tahoma", Font.PLAIN,12));
        rdoMale.setBounds(124,48,100,30);
        frmJradioButtonExample.getContentPane().add(rdoMale);


        JRadioButton rdoFemale = new JRadioButton("Female");
        rdoFemale.setFont(new Font("Tahoma", Font.PLAIN,12));
        rdoFemale.setBounds(344,48,100,30);
        frmJradioButtonExample.getContentPane().add(rdoFemale);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(rdoMale);
        genderGroup.add(rdoFemale);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setFont(new Font("Tahoma", Font.PLAIN,12));
        btnSubmit.setBounds(195,120,133,52);
        frmJradioButtonExample.getContentPane().add(btnSubmit);

        JLabel lblResult = new JLabel("<output here>");
        lblResult.setFont(new Font("Tahoma", Font.PLAIN,12));
        lblResult.setBounds(29,192,240,52);
        frmJradioButtonExample.getContentPane().add(lblResult);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public  void actionPerformed(ActionEvent e){
                if (rdoMale.isSelected()){
                    lblResult.setText("Selected Gender: Male");
                }else if (rdoFemale.isSelected()){
                    lblResult.setText("Selected Gender: Female");
                }else {
                    lblResult.setText("No Gender Selected");
                }
            }
        });



}

}
