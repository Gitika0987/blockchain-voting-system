package dao;

import db.DBConnection;
import model.Block;
import model.Blockchain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class VoteDAO {

    public boolean castVote(int voterId, int candidateId) {
        // Step 1: Create transaction data
        String voteData = "VoterID:" + voterId + " CandidateID:" + candidateId;

        // Step 2: Add to Blockchain
        Blockchain.addBlock(voteData);

        // Get the newly added block
        Block latestBlock = Blockchain.chain.get(Blockchain.chain.size() - 1);

        String sqlInsertVote = "INSERT INTO votes (voter_id, candidate_id, block_hash, previous_hash) VALUES (?, ?, ?, ?)";
        String sqlUpdateVoter = "UPDATE voters SET has_voted = 1 WHERE voter_id = ?";

        Connection con = null;
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false); // Start transaction

            // Step 3: Insert into votes table
            try (PreparedStatement pstInsert = con.prepareStatement(sqlInsertVote)) {
                pstInsert.setInt(1, voterId);
                pstInsert.setInt(2, candidateId);
                pstInsert.setString(3, latestBlock.hash);
                pstInsert.setString(4, latestBlock.previousHash);
                pstInsert.executeUpdate();
            }

            // Step 4: Update voter has_voted status
            try (PreparedStatement pstUpdate = con.prepareStatement(sqlUpdateVoter)) {
                pstUpdate.setInt(1, voterId);
                pstUpdate.executeUpdate();
            }

            con.commit(); // Commit transaction
            return true;
        } catch (SQLException e) {
            System.err.println("Error casting vote. Rolling back DB transaction.");
            e.printStackTrace();
            if(con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if(con != null) {
                 try {
                     con.setAutoCommit(true);
                     con.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
            }
        }
    }

    public Map<String, Integer> getResults() {
        Map<String, Integer> results = new HashMap<>();
        String sql = "SELECT c.name, COUNT(v.vote_id) as vote_count FROM candidates c LEFT JOIN votes v ON c.candidate_id = v.candidate_id GROUP BY c.name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                results.put(rs.getString("name"), rs.getInt("vote_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
