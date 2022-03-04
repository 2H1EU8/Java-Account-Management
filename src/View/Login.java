package View;

import Account.Account;
import Common.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JFrame {

    public Login() {
            //Panel
            JPanel panel = new JPanel();

            //Frame
            this.setLocationRelativeTo(null);
            this.setSize(new Dimension(453, 280));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Login");
            this.add(panel);
            this.setVisible(true);

            //Components
            JTextField userTextField = new JTextField();
            userTextField.setBounds(150, 70, 200, 25);
            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(150, 100, 200, 25);
            JButton loginButton = new JButton("Sign-in");
            loginButton.setBounds(185, 140, 100, 25);
            JButton registerButton = new JButton("Sign up");
            registerButton.setBounds(185, 170, 100, 25);
            loginButton.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\login.png"));
            registerButton.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\add-userC.png"));


            //Labels
            JLabel userLabel = new JLabel("Username:");
            userLabel.setBounds(80, 70, 100, 20);
            JLabel passLabel = new JLabel("Password:");
            passLabel.setBounds(80, 100, 100, 20);
            JLabel logo = new JLabel();
            logo.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\SLogo.png"));
            logo.setBounds(215, 10, 44, 44);

            panel.add(logo);
            panel.add(userLabel);
            panel.add(passLabel);
            panel.add(userTextField);
            panel.add(passwordField);
            panel.add(loginButton);
            panel.add(registerButton);
            panel.setBackground(new Color(58, 155, 220));
            //Layout
            panel.setLayout(null);
            //Events
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (userTextField.getText().isBlank() || passwordField.getText().isBlank()) {
                        JOptionPane.showMessageDialog(panel, "Username and password mustn't empty", "Error Login", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String sql = "Select * from Login where username = '" + userTextField.getText() + "' and passwords = '" + passwordField.getText() + "'";
                        Connection connection = null;
                        try {
                            connection = ConnectionDB.getConnection();
                            PreparedStatement ps = connection.prepareStatement(sql);
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {
                                JOptionPane.showMessageDialog(panel, "Login Success");
                                Account account = new Account(rs.getString("username"), rs.getString("passwords"), rs.getInt("iddata"));
                                MainView view = new MainView();
                                setVisible(false);
                            } else {
                                JOptionPane.showMessageDialog(panel, "Login don't succeed", "Message", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(panel, ex.getMessage());
                            ex.printStackTrace();
                        }

                    }
                }
            });
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    new Register();
                }
            });

        }
    }