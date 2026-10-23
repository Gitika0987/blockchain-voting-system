package ui;

import dao.CandidateDAO;
import dao.VoteDAO;
import model.Blockchain;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class AdminPanel extends JFrame {
    private CandidateDAO candidateDAO;
    private VoteDAO voteDAO;

    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private JButton validateChainBtn, addCandidateBtn, refreshBtn, exitBtn;

    public AdminPanel() {
        candidateDAO = new CandidateDAO();
        voteDAO = new VoteDAO();

        setTitle("Blockchain Voting System - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(18, 18));
        mainPanel.setBackground(new Color(26, 26, 26));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        JLabel subtitleLabel = new JLabel("Monitor votes, manage candidates, and verify blockchain integrity.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel topPanel = new JPanel(new BorderLayout(12, 12));
        topPanel.setBackground(new Color(42, 42, 42));
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel infoLabel = new JLabel("Use the controls to refresh results, add candidates, or validate the vote ledger.");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(Color.WHITE);
        topPanel.add(infoLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBackground(new Color(42, 42, 42));

        addCandidateBtn = createModernButton("Add Candidate", new Color(30, 64, 175), Color.WHITE);
        refreshBtn = createModernButton("Refresh Results", new Color(30, 64, 175), Color.WHITE);
        validateChainBtn = createModernButton("Validate Blockchain", new Color(30, 64, 175), Color.WHITE);
        exitBtn = createModernButton("Log Out", new Color(220, 38, 38), Color.WHITE);

        buttonPanel.add(addCandidateBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(validateChainBtn);
        buttonPanel.add(exitBtn);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Candidate Name", "Total Votes"}, 0);
        resultsTable = new JTable(tableModel);
        resultsTable.setRowHeight(26);
        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultsTable.setBackground(new Color(50, 50, 50));
        resultsTable.setForeground(Color.WHITE);
        resultsTable.setGridColor(new Color(80, 80, 80));
        resultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultsTable.getTableHeader().setBackground(new Color(42, 42, 42));
        resultsTable.getTableHeader().setForeground(Color.WHITE);
        resultsTable.setFillsViewportHeight(true);

        JScrollPane tableScroll = new JScrollPane(resultsTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        tableScroll.getViewport().setBackground(new Color(50, 50, 50));
        mainPanel.add(tableScroll, BorderLayout.CENTER);

        // Bottom action buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 15));
        bottomPanel.setBackground(new Color(26, 26, 26));
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
            BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));
        JButton exportBtn = createModernButton("Export Results", new Color(30, 64, 175), Color.WHITE);
        JButton logoutBtn = createModernButton("Log Out", new Color(220, 38, 38), Color.WHITE);

        exportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Export feature coming soon!"));
        logoutBtn.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        bottomPanel.add(exportBtn);
        bottomPanel.add(logoutBtn);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        loadResults();

        addCandidateBtn.addActionListener(e -> openAddCandidateDialog());
        refreshBtn.addActionListener(e -> loadResults());
        validateChainBtn.addActionListener(e -> validateBlockchain());
        exitBtn.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });
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

    private void loadResults() {
        tableModel.setRowCount(0);
        Map<String, Integer> results = voteDAO.getResults();
        for (Map.Entry<String, Integer> entry : results.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
        }
    }

    private void openAddCandidateDialog() {
        JTextField nameField = new JTextField();
        JTextField partyField = new JTextField();
        Object[] message = {
            "Candidate Name:", nameField,
            "Party:", partyField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Candidate", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String party = partyField.getText();
            if (!name.isEmpty() && !party.isEmpty()) {
                candidateDAO.addCandidate(name, party);
                loadResults();
                JOptionPane.showMessageDialog(this, "Candidate added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void validateBlockchain() {
        if (Blockchain.chain.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Blockchain is currently empty. No votes cast yet.", "Blockchain Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        boolean isValid = Blockchain.isChainValid();
        if (isValid) {
            JOptionPane.showMessageDialog(this, "Blockchain is SECURE and VALID. No tampering detected.", "Validation Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "CRITICAL ALERT: Blockchain tampering detected!", "Validation Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
