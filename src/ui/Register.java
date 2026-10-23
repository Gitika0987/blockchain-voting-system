package ui;

import dao.VoterDAO;
import java.awt.*;
import javax.swing.*;

public class Register extends JFrame {
    private JTextField nameField, ageField, aadhaarField;
    private JPasswordField passwordField, pinField;
    private JCheckBox showPasswordCheckBox;
    private JCheckBox showPinCheckBox;
    private JButton registerButton, backButton;
    private VoterDAO voterDAO;

    public Register() {
        voterDAO = new VoterDAO();

        setTitle("Blockchain Voting System - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 900);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBackground(new Color(26, 26, 26));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Create Your Voter Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(42, 42, 42));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        // Create fields upfront
        nameField = new JTextField(20);
        ageField = new JTextField(20);
        aadhaarField = new JTextField(20);
        passwordField = new JPasswordField(20);
        pinField = new JPasswordField(20);

        addFormField(formPanel, "Full Name:", nameField, 0);
        addFormField(formPanel, "Age:", ageField, 2);
        addFormField(formPanel, "Aadhaar Number:", aadhaarField, 4);
        addFormField(formPanel, "Password:", passwordField, 6);

        // Show Password Checkbox
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showPasswordCheckBox.setOpaque(false);
        showPasswordCheckBox.setForeground(Color.WHITE);
        showPasswordCheckBox.setBackground(new Color(42, 42, 42));
        GridBagConstraints passwordCheckGbc = new GridBagConstraints();
        passwordCheckGbc.gridx = 0;
        passwordCheckGbc.gridy = 8;
        passwordCheckGbc.gridwidth = 2;
        passwordCheckGbc.fill = GridBagConstraints.HORIZONTAL;
        passwordCheckGbc.insets = new Insets(6, 10, 12, 10);
        formPanel.add(showPasswordCheckBox, passwordCheckGbc);

        addFormField(formPanel, "Create 4-Digit PIN:", pinField, 10);

        // Show PIN Checkbox
        showPinCheckBox = new JCheckBox("Show PIN");
        showPinCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showPinCheckBox.setOpaque(false);
        showPinCheckBox.setForeground(Color.WHITE);
        showPinCheckBox.setBackground(new Color(42, 42, 42));
        GridBagConstraints pinCheckGbc = new GridBagConstraints();
        pinCheckGbc.gridx = 0;
        pinCheckGbc.gridy = 12;
        pinCheckGbc.gridwidth = 2;
        pinCheckGbc.fill = GridBagConstraints.HORIZONTAL;
        pinCheckGbc.insets = new Insets(6, 10, 12, 10);
        formPanel.add(showPinCheckBox, pinCheckGbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel - with visible background
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 15));
        buttonPanel.setBackground(new Color(26, 26, 26));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
            BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        registerButton = createModernButton("Register", new Color(30, 64, 175), Color.WHITE);
        backButton = createModernButton("Back to Login", new Color(30, 64, 175), Color.WHITE);

        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Actions
        registerButton.addActionListener(e -> registerUser());
        backButton.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });
        showPasswordCheckBox.addActionListener(e -> togglePasswordVisibility());
        showPinCheckBox.addActionListener(e -> togglePinVisibility());
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(Color.WHITE);

        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridx = 0;
        labelGbc.gridy = row;
        labelGbc.gridwidth = 2;
        labelGbc.fill = GridBagConstraints.HORIZONTAL;
        labelGbc.weightx = 1.0;
        labelGbc.insets = new Insets(12, 10, 8, 10);
        panel.add(label, labelGbc);

        if (field instanceof JTextField) {
            JTextField tf = (JTextField) field;
            tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            tf.setBackground(new Color(50, 50, 50));
            tf.setForeground(Color.WHITE);
            tf.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        } else if (field instanceof JPasswordField) {
            JPasswordField pf = (JPasswordField) field;
            pf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            pf.setBackground(new Color(50, 50, 50));
            pf.setForeground(Color.WHITE);
            pf.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        }

        GridBagConstraints fieldGbc = new GridBagConstraints();
        fieldGbc.gridx = 0;
        fieldGbc.gridy = row + 1;
        fieldGbc.gridwidth = 2;
        fieldGbc.fill = GridBagConstraints.HORIZONTAL;
        fieldGbc.weightx = 1.0;
        fieldGbc.ipady = 8;
        fieldGbc.insets = new Insets(0, 10, 12, 10);
        panel.add(field, fieldGbc);
    }

    private JButton createModernButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        return button;
    }

    private void togglePasswordVisibility() {
        if (showPasswordCheckBox.isSelected()) {
            // Show password as plain text
            passwordField.setEchoChar((char) 0);
        } else {
            // Hide password with dots
            passwordField.setEchoChar('●');
        }
    }

    private void togglePinVisibility() {
        if (showPinCheckBox.isSelected()) {
            // Show PIN as plain text
            pinField.setEchoChar((char) 0);
        } else {
            // Hide PIN with dots
            pinField.setEchoChar('●');
        }
    }

    private void registerUser() {
        String name = nameField.getText();
        String ageStr = ageField.getText();
        String aadhaar = aadhaarField.getText();
        String password = new String(passwordField.getPassword());
        String pin = new String(pinField.getPassword());

        if (name.isEmpty() || ageStr.isEmpty() || aadhaar.isEmpty() || password.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            if(age < 18) {
                JOptionPane.showMessageDialog(this, "You must be 18 or older to vote.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = voterDAO.registerVoter(name, age, aadhaar, password, pin);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                new Login().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Aadhaar might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
