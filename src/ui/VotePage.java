package ui;

import dao.CandidateDAO;
import dao.VoteDAO;
import model.Candidate;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VotePage extends JFrame {
    private int voterId;
    private CandidateDAO candidateDAO;
    private VoteDAO voteDAO;
    private ButtonGroup candidateGroup;
    private JButton submitVoteButton;

    public VotePage(int voterId) {
        this.voterId = voterId;
        this.candidateDAO = new CandidateDAO();
        this.voteDAO = new VoteDAO();

        setTitle("Blockchain Voting System - Cast Your Vote");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 600);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 18));
        mainPanel.setBackground(new Color(26, 26, 26));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Cast Your Vote");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        JLabel subtitleLabel = new JLabel("Choose a candidate carefully. Voting is secure and permanent.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 16));
        centerPanel.setBackground(new Color(42, 42, 42));
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
            BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        JLabel infoLabel = new JLabel("Select one candidate below and submit your vote.");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        centerPanel.add(infoLabel, BorderLayout.NORTH);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.setOpaque(false);
        radioPanel.setBackground(new Color(42, 42, 42));
        radioPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        candidateGroup = new ButtonGroup();

        List<Candidate> candidates = candidateDAO.getAllCandidates();
        if (candidates.isEmpty()) {
            JLabel emptyLabel = new JLabel("No candidates are available at the moment.");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            emptyLabel.setForeground(Color.WHITE);
            radioPanel.add(emptyLabel);
        } else {
            for (Candidate c : candidates) {
                JRadioButton rb = new JRadioButton(c.getName() + " (" + c.getParty() + ")");
                rb.setActionCommand(String.valueOf(c.getCandidateId()));
                rb.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                rb.setForeground(Color.WHITE);
                rb.setBackground(new Color(42, 42, 42));
                rb.setOpaque(true);
                rb.setAlignmentX(Component.LEFT_ALIGNMENT);
                candidateGroup.add(rb);
                radioPanel.add(rb);
                radioPanel.add(Box.createRigidArea(new Dimension(0, 6)));
            }
        }

        JScrollPane scrollPane = new JScrollPane(radioPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        scrollPane.setPreferredSize(new Dimension(480, 260));
        scrollPane.getViewport().setBackground(new Color(50, 50, 50));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 15));
        actionPanel.setBackground(new Color(26, 26, 26));
        actionPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80), 1),
            BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));

        submitVoteButton = createModernButton("Submit Secure Vote", new Color(30, 64, 175), Color.WHITE);
        JButton backButton = createModernButton("Back to Login", new Color(30, 64, 175), Color.WHITE);
        backButton.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        actionPanel.add(backButton);
        actionPanel.add(submitVoteButton);
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        submitVoteButton.addActionListener(e -> castVote());
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

    private void castVote() {
        ButtonModel selection = candidateGroup.getSelection();
        if (selection == null) {
            JOptionPane.showMessageDialog(this, "Please select a candidate.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int candidateId = Integer.parseInt(selection.getActionCommand());
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to cast this vote? It will be permanently recorded on the blockchain.", "Confirm Vote", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = voteDAO.castVote(voterId, candidateId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Vote successfully cast and secured in the Blockchain!", "Success", JOptionPane.INFORMATION_MESSAGE);
                new Login().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cast vote. System Error.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
