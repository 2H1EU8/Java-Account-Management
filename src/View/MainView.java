package View;

import Account.Account;
import Account.AccountData;
import Common.ConnectionDB;
import Common.DAO;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainView extends JFrame {
    public static DefaultTableModel dtm;
    public static JTable dataTable = new JTable();

    private void initTable(){
        dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(new String[]{"Username","Password","Type","Modified","Link"});
        dataTable.setModel(dtm);
    }

    private  void loadData(){
        try{
            String sql = "Select * from Data where idaccess ="+Account.getIdAccess()+" order by modified desc";
            Connection connection = ConnectionDB.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            dtm.setRowCount(0);
            while (rs.next()){
                String[] dataRow = new String[]{
                        rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Type"), rs.getString("Modified"),rs.getString("Link")};
                dtm.addRow(dataRow);
            }
            dtm.fireTableDataChanged();
            rs.close();
            ps.close();
            connection.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
            e.printStackTrace();
        }
    }

    private  void loadFindData(String keyword){
        try{
            String sql = "Select * from Data where idaccess ="+Account.getIdAccess()+" and username like '%"+ keyword +"%'";
            Connection connection = ConnectionDB.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            dtm.setRowCount(0);
            while (rs.next()){
                String[] dataRow = new String[]{
                        rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Type"), rs.getString("Modified"),rs.getString("link")};
                dtm.addRow(dataRow);
            }
            dtm.fireTableDataChanged();
            rs.close();
            ps.close();
            connection.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
            e.printStackTrace();
        }
    }

    private  void loadSortData(String key){
        try{
            String sql = "Select * from Data where idaccess ="+Account.getIdAccess()+" ORDER BY "+key+" ASC;";
            Connection connection = ConnectionDB.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            dtm.setRowCount(0);
            while (rs.next()){
                String[] dataRow = new String[]{
                        rs.getString("Username"), rs.getString("Password"),
                        rs.getString("Type"), rs.getString("Modified"),rs.getString("link")};
                dtm.addRow(dataRow);
            }
            dtm.fireTableDataChanged();
            rs.close();
            ps.close();
            connection.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }

    private  void checkPass(){
        try{
            String sql = "Select * from Data where idaccess ="+Account.getIdAccess()+" order by modified desc";
            Connection connection = ConnectionDB.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            dtm.setRowCount(0);
            String passReg = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            while (rs.next()){
                if (!rs.getString("Password").trim().matches(passReg)) {
                    String[] dataRow = new String[]{
                            rs.getString("Username"), rs.getString("Password"),
                            rs.getString("Type"), rs.getString("Modified"),rs.getString("Link")};
                    dtm.addRow(dataRow);
                }
            }
            dtm.fireTableDataChanged();
            rs.close();
            ps.close();
            connection.close();

        }catch (Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
            e.printStackTrace();
        }
    }

    public MainView() throws IOException {
        //MainContainer
        JFrame frame = new JFrame("Account Management");
        frame.setSize(890, 550);
        frame.setResizable(true);
        GridLayout gl = new GridLayout();
        frame.setLayout(gl);
        frame.setLocationRelativeTo(null);

//Components
        //Panel
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel formPanel = new JPanel();
        JPanel endPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel fsPanel = new JPanel();
        panel1.setBackground(new Color(58, 155, 220));
        panel2.setBackground(new Color(58, 155, 220));
        panel1.setBorder(new CompoundBorder(new TitledBorder(""), new SoftBevelBorder(BevelBorder.LOWERED)));
        panel2.setBorder(new CompoundBorder(new TitledBorder("Account"), new EmptyBorder(4, 4, 4, 4)));
        frame.add(panel1);
        frame.add(panel2);
        //Label
        JLabel userlb = new JLabel("Username:");
        JLabel passwordlb = new JLabel("Password:");
        JLabel retype = new JLabel("Re-type:");
        JLabel linklb = new JLabel("Link/Note:");

        //TextField
        JTextField usertf = new JTextField(25);
        JPasswordField passwordtf = new JPasswordField(25);
        JPasswordField retypetf = new JPasswordField(25);
        JTextField findtf = new JTextField("Enter_keyword", 12);
        JTextField typetf = new JTextField("Type", 10);
        JTextField linktf = new JTextField(25);

        //ComboBox
        JComboBox sCombo = new JComboBox();
        sCombo.addItem("Type");
        sCombo.addItem("Modified");
        sCombo.addItem("Username");
        //Table
        dataTable.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                int row = dataTable.getSelectedRow();
                if (row >= 0) {
                    usertf.setText(dataTable.getValueAt(row, 0).toString().trim());
                    passwordtf.setText(dataTable.getValueAt(row, 1).toString().trim());
                    retypetf.setText(dataTable.getValueAt(row, 1).toString().trim());
                    typetf.setText(dataTable.getValueAt(row, 2).toString());
                    linktf.setText("");
                    linktf.setText(dataTable.getValueAt(row,4).toString().trim());
                }
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        initTable();
        loadData();
        JScrollPane sp = new JScrollPane(dataTable);

        JButton printButton = new JButton("In");
        panel2.add(sp);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));

        //Button
        JButton addButton = new JButton(" Add  ");
        addButton.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\new.png"));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                StringBuilder sb = new StringBuilder();
                if (usertf.getText().isBlank()) {
                    sb.append("Enter username");
                    usertf.setBackground(Color.red);
                } else {
                    usertf.setBackground(Color.white);
                }
                if (sb.length() > 0) {
                    JOptionPane.showMessageDialog(frame, sb);
                    return;
                }

                if (passwordtf.getText().isBlank()) {
                    sb.append("Enter password");
                    passwordtf.setBackground(Color.red);
                } else {
                    passwordtf.setBackground(Color.white);
                }
                if (sb.length() > 0) {
                    JOptionPane.showMessageDialog(frame, sb);
                    return;
                }

                if (retypetf.getText().isBlank()) {
                    sb.append("Retype password");
                    usertf.setBackground(Color.red);
                }
                if (sb.length() > 0) {
                    JOptionPane.showMessageDialog(frame, sb);
                    return;
                }
                if (!passwordtf.getText().equals(retypetf.getText())) {
                    sb.append("Password don't match");
                }
                if (sb.length() > 0) {
                    JOptionPane.showMessageDialog(frame, sb);
                    return;
                }
                try {
                    AccountData acc = new AccountData();
                    acc.setUserName(usertf.getText());
                    acc.setPassword(passwordtf.getText());
                    acc.setTypeAcc(typetf.getText());
                    acc.setIdAccess(Account.getIdAccess());
                    acc.setAddress(linktf.getText());
                    DAO.addAcc(acc);
                    JOptionPane.showMessageDialog(frame, "Add Success");
                    dtm.setRowCount(0);
                    loadData();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Account has existed");
                }
            }
        });
        JButton deleteButton = new JButton("Delete");
        deleteButton.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\close.png"));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    DAO.deleteAcc(usertf.getText(),typetf.getText(),Account.getIdAccess());
                    JOptionPane.showMessageDialog(frame, "Delete success");
                    usertf.setText("");
                    passwordtf.setText("");
                    retypetf.setText("");
                    typetf.setText("");
                    linktf.setText("");
                    dtm.setRowCount(0);
                    loadData();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error" + ex.getMessage());
                    ex.printStackTrace();
                }
            }

        });
        JButton updateButton = new JButton("Update");
        updateButton.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\edit.png"));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                StringBuilder sb = new StringBuilder();
                if (usertf.getText().isBlank()) {
                    sb.append("Enter username");
                    usertf.setBackground(Color.red);
                } else {
                    usertf.setBackground(Color.white);
                }
                if (sb.length() > 0) {
                    JOptionPane.showMessageDialog(frame, sb);
                    return;
                }

                if (passwordtf.getText().isBlank()) {
                    sb.append("Enter password");
                    passwordtf.setBackground(Color.red);
                } else {
                    passwordtf.setBackground(Color.white);
                }
                if (sb.length() > 0) {
                    JOptionPane.showMessageDialog(frame, sb);
                    return;
                }

                if (retypetf.getText().isBlank()) {
                    sb.append("Retype password");
                    usertf.setBackground(Color.red);
                }
                if (sb.length() > 0) {
                    JOptionPane.showMessageDialog(frame, sb);
                    return;
                }
                if (!passwordtf.getText().equals(retypetf.getText())) {
                    sb.append("Password don't match");
                }
                if (sb.length() > 0) {
                    JOptionPane.showMessageDialog(frame, sb);
                    return;
                }
                try {
                    AccountData acc = new AccountData();
                    acc.setUserName(usertf.getText().trim());
                    acc.setPassword(passwordtf.getText());
                    acc.setTypeAcc(typetf.getText());
                    acc.setIdAccess(Account.getIdAccess());
                    acc.setAddress(linktf.getText());
                    DAO.updateAcc(acc);
                    JOptionPane.showMessageDialog(frame, "Update Success");
                    if(dataTable.getBackground()==Color.red){
                        dtm.setRowCount(0);
                        checkPass();
                    }
                    else{
                        dtm.setRowCount(0);
                        loadData();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage());
                }
            }
        });
        JButton reloadButton = new JButton("Reload");
        reloadButton.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\reload.png"));
        reloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                usertf.setText("");
                passwordtf.setText("");
                retypetf.setText("");
                typetf.setText("Type");
                linktf.setText("");
                findtf.setText("Enter_keyword");
                loadData();
                dataTable.setBackground(Color.white);
            }
        });
        JButton sortButton = new JButton("Sort");
        sortButton.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\sort.png"));
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSortData(sCombo.getSelectedItem().toString());
            }
        });
        JButton findButton = new JButton("Find");
        findButton.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\search.png"));
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFindData(findtf.getText());
            }
        });

        JCheckBox chkBox = new JCheckBox("Hide password");
        chkBox.setSelected(true);
        chkBox.setBackground(new Color(58, 155, 220));
        chkBox.setForeground(Color.BLACK);
        chkBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordtf.setEchoChar('•');
                    retypetf.setEchoChar('•');
                } else {
                    passwordtf.setEchoChar((char) 0);
                    retypetf.setEchoChar((char) 0);
                }
            }
        });

        endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.CENTER);
        fl.setHgap(10);
        fl.setVgap(20);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(reloadButton);
        buttonPanel.setLayout(fl);

//CRUD Container
        fsPanel.add(sCombo);
        fsPanel.add(sortButton);
        fsPanel.add(findtf);
        fsPanel.add(findButton);

        formPanel.setBorder(new CompoundBorder(new TitledBorder("Form"), new EmptyBorder(4, 4, 4, 4)));
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weighty = 0.25;
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(userlb, gbc);
        gbc.gridx++;
        formPanel.add(usertf, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordlb, gbc);
        gbc.gridx++;
        formPanel.add(passwordtf, gbc);
        gbc.gridx++;
        formPanel.add(chkBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(retype, gbc);
        gbc.gridx++;
        formPanel.add(retypetf, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0;
        formPanel.add(typetf, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(linklb, gbc);
        gbc.gridx++;
        formPanel.add(linktf,gbc);

        BufferedImage icon = ImageIO.read(new File("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\LLogo.png"));
        JLabel logo = new JLabel(new ImageIcon(icon));
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        JButton thongkeB = new JButton("Statistics");
        thongkeB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new Chart();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        JLabel namelb = new JLabel("Account Management");
        JLabel nameuserlb = new JLabel("Hi,"+Account.getUserName().trim()+"!");
        JLabel userIcon = new JLabel();
        userIcon.setIcon(new ImageIcon("C:\\Users\\ADMIN\\IdeaProjects\\AccountManagement\\src\\Logo\\user.png"));
        JButton logoutB = new JButton("Log out");
        logoutB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new Login();
            }
        });
        JButton checkB = new JButton("Password Security");
        checkB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"•Must contain at least one number\n" +
                        "\n" +
                        "•Must contain at least one lowercase letter\n" +
                        "\n" +
                        "•Must contain at least one uppercase letter\n" +
                        "\n" +
                        "•Must contain at least one special character (@#$%^&+=)\n" +
                        "\n" +
                        "•Length must be at least 8 characters (no space)","Requirement of a strong password",JOptionPane.WARNING_MESSAGE);
                dataTable.setBackground(Color.red);
                checkPass();
            }
        });
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.weightx = 1;
        gbc2.weighty = 1;
        logoPanel.add(logo,gbc2);
        gbc2.gridy = 1;
        logoPanel.add(namelb,gbc2);
        gbc2.gridy ++;
        logoPanel.add(thongkeB,gbc2);
        gbc2.gridx ++;
        gbc2.gridy =2;
        logoPanel.add(checkB,gbc2);
        gbc2.gridx ++;
        gbc2.gridy = 0;
        logoPanel.add(userIcon,gbc2);
        gbc2.gridy ++;
        logoPanel.add(nameuserlb,gbc2);
        gbc2.gridy ++;
        logoPanel.add(logoutB,gbc2);

        logoPanel.setBackground(new Color(41, 197, 246));
        sp.setBackground(new Color(41, 197, 246));
        formPanel.setBackground(new Color(41, 197, 246));
        fsPanel.setBackground(new Color(41, 197, 246));
        buttonPanel.setBackground(new Color(41, 197, 246));
        endPanel.add(buttonPanel);
        endPanel.add(fsPanel);

        panel1.add(logoPanel);
        panel1.add(formPanel);
        panel1.add(endPanel);
        panel1.setLayout(new GridLayout(3, 1));

        pack();
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
