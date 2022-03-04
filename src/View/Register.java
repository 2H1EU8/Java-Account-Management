package View;

import Account.Account;
import Common.DAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class Register extends JFrame {

    public Register() {
        //Panel
        JPanel panel = new JPanel();

        //Frame
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(453, 280));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Register");
        this.add(panel);
        this.setVisible(true);

        //Components
        JTextField userTextField = new JTextField();
        userTextField.setBounds(150, 60, 200, 25);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 90, 200, 25);
        JPasswordField passwordField1 = new JPasswordField();
        passwordField1.setBounds(150, 120, 200, 25);
        JButton signupButton = new JButton("Sign up");
        signupButton.setBounds(185, 180, 100, 25);
        signupButton.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\add-user.png"));
        JButton backButton = new JButton("Back");
        backButton.setBounds(185,210,100,25);
        JCheckBox chkBox = new JCheckBox("Hide password");
        chkBox.setSelected(true);
        chkBox.setForeground(Color.BLACK);
        chkBox.setBackground(new Color(58, 155, 220));
        chkBox.setBounds(180, 150, 150, 20);
        chkBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordField.setEchoChar('•');
                    passwordField1.setEchoChar('•');
                } else {
                    passwordField.setEchoChar((char) 0);
                    passwordField1.setEchoChar((char) 0);
                }
            }
        });

        //Labels
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(80, 60, 100, 20);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(80, 90, 100, 20);
        JLabel retypeLabel = new JLabel("Re-type:");
        retypeLabel.setBounds(80, 120, 100, 20);
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\SLogo.png"));
        logo.setBounds(215,10,44,44);


        panel.add(logo);
        panel.add(userLabel);
        panel.add(passLabel);
        panel.add(retypeLabel);
        panel.add(userTextField);
        panel.add(passwordField);
        panel.add(passwordField1);
        panel.add(signupButton);
        panel.add(chkBox);
        panel.add(backButton);
        panel.setBackground(new Color(58, 155, 220));
        //Layout
        panel.setLayout(null);
        //Event
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userTextField.getText().isBlank())
                {
                    JOptionPane.showMessageDialog(panel,"User name mustn't empty!","Alter",JOptionPane.WARNING_MESSAGE);
                    userTextField.setBackground(Color.red);
                }
                else if (!passwordField.getText().equals(passwordField1.getText())) {
                    JOptionPane.showMessageDialog(panel, "Password not match!","Alter",JOptionPane.WARNING_MESSAGE);
                    passwordField.setBackground(Color.red);
                    passwordField1.setBackground(Color.red);
                }else{
                    userTextField.setBackground(Color.white);
                    passwordField.setBackground(Color.white);
                    passwordField1.setBackground(Color.white);
                    Account acc = new Account(userTextField.getText(),passwordField.getText(),0);
                    try {
                        DAO.signUp(acc);
                        JOptionPane.showMessageDialog(panel,"Sign-up success!","Success",JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        new Login();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel,"User was in existence.","Error",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new Login();
            }
        });
    }
}
