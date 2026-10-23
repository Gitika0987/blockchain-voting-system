package dao;

import db.DBConnection;
import model.Block;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoterDAO {

    public boolean registerVoter(String name, int age, String aadhaar, String password, String pin) {
        String sql = "INSERT INTO voters (name, age, aadhaar, password, has_voted, pin) VALUES (?, ?, ?, ?, 0, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, aadhaar);
            pst.setString(4, Block.applySHA256(password)); // Hash password for security
            pst.setString(5, pin);

            int affected = pst.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int authenticateVoter(String aadhaar, String password, String pin) {
        String sql = "SELECT voter_id FROM voters WHERE aadhaar = ? AND password = ? AND pin = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, aadhaar);
            pst.setString(2, Block.applySHA256(password));
            pst.setString(3, pin);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("voter_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // -1 indicates failed login
    }

    public boolean hasVoted(int voterId) {
        String sql = "SELECT has_voted FROM voters WHERE voter_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setInt(1, voterId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("has_voted") == 1; // 1 means has voted, 0 means hasn't
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
