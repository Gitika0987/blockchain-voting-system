package ui;

import dao.VoterDAO;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField aadhaarField;
    private JPasswordField passwordField;
    private JPasswordField pinField;
    private JCheckBox showPasswordCheckBox;
    private JCheckBox showPinCheckBox;
    private JButton loginButton, registerButton, adminButton;
    private VoterDAO voterDAO;

    public Login() {
        voterDAO = new VoterDAO();

        setTitle("Blockchain Voting System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 15));
        mainPanel.setBackground(new Color(26, 26, 26));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Welcome Back");
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Aadhaar Field
        JLabel aadhaarLabel = new JLabel("Aadhaar Number:");
        aadhaarLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        aadhaarLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(aadhaarLabel, gbc);

        aadhaarField = new JTextField(20);
        aadhaarField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        aadhaarField.setBackground(new Color(50, 50, 50));
        aadhaarField.setForeground(Color.WHITE);
        aadhaarField.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        gbc.gridy = 1;
        gbc.ipady = 8;
        formPanel.add(aadhaarField, gbc);
        gbc.ipady = 0;

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(new Color(50, 50, 50));
        passwordField.setForeground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        gbc.gridy = 3;
        gbc.ipady = 8;
        formPanel.add(passwordField, gbc);
        gbc.ipady = 0;

        // Show Password Checkbox
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showPasswordCheckBox.setOpaque(false);
        showPasswordCheckBox.setForeground(Color.WHITE);
        showPasswordCheckBox.setBackground(new Color(42, 42, 42));
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(6, 10, 12, 10);
        formPanel.add(showPasswordCheckBox, gbc);
        gbc.insets = new Insets(12, 10, 12, 10);

        // PIN Field
        JLabel pinLabel = new JLabel("4-Digit PIN (Verification):");
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pinLabel.setForeground(Color.WHITE);
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(pinLabel, gbc);

        pinField = new JPasswordField(20);
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pinField.setBackground(new Color(50, 50, 50));
        pinField.setForeground(Color.WHITE);
        pinField.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        gbc.gridy = 6;
        gbc.ipady = 8;
        gbc.gridwidth = 2;
        formPanel.add(pinField, gbc);
        gbc.ipady = 0;

        // Show PIN Checkbox
        showPinCheckBox = new JCheckBox("Show PIN");
        showPinCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showPinCheckBox.setOpaque(false);
        showPinCheckBox.setForeground(Color.WHITE);
        showPinCheckBox.setBackground(new Color(42, 42, 42));
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(6, 10, 12, 10);
        formPanel.add(showPinCheckBox, gbc);
        gbc.insets = new Insets(12, 10, 12, 10);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons Panel - with visible background
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 15));
        buttonPanel.setBackground(new Color(26, 26, 26));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
            BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        loginButton = createModernButton("Login", new Color(30, 64, 175), Color.WHITE);
        registerButton = createModernButton("Register New Voter", new Color(30, 64, 175), Color.WHITE);
        adminButton = createModernButton("Admin Panel", new Color(30, 64, 175), Color.WHITE);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(adminButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Actions
        loginButton.addActionListener(e -> attemptLogin());
        registerButton.addActionListener(e -> {
            new Register().setVisible(true);
            dispose();
        });
        adminButton.addActionListener(e -> {
            new AdminPanel().setVisible(true);
            dispose();
        });
        showPasswordCheckBox.addActionListener(e -> togglePasswordVisibility());
        showPinCheckBox.addActionListener(e -> togglePinVisibility());
    }

    private JButton createModernButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI()); // Force background color support over system UI Theme
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setOpaque(true);
        button.setContentAreaFilled(false); // Make sure native gradients don't paint over our flat color
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

    private void attemptLogin() {
        String aadhaar = aadhaarField.getText();
        String password = new String(passwordField.getPassword());
        String pin = new String(pinField.getPassword());

        if (aadhaar.isEmpty() || password.isEmpty() || pin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int voterId = voterDAO.authenticateVoter(aadhaar, password, pin);
        if (voterId != -1) {
            JOptionPane.showMessageDialog(this, "Login Successful! Biometric/PIN Verified.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            if (voterDAO.hasVoted(voterId)) {
                JOptionPane.showMessageDialog(this, "You have already cast your vote! You cannot vote again.", "Access Denied", JOptionPane.WARNING_MESSAGE);
            } else {
                new VotePage(voterId).setVisible(true);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials or PIN", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
