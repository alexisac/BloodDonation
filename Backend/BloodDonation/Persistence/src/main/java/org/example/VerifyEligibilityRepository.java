package org.example;

import org.example.Enums.DonationType;
import org.example.Enums.EligibilityType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class VerifyEligibilityRepository {
    private JdbcUtils dbUtils;

    public VerifyEligibilityRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    public int getEligibilityTypeForUser(String idUser) throws RepoException {
        String sql = "SELECT * FROM EligibilityType WHERE idUser = ? LIMIT 1";
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, idUser);

            try(ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("typeValue");
                } else {
                    return EligibilityType.UNDEFINED.getIntValue();
                }
            }
        } catch (SQLException ex) {
            throw new RepoException("VerifyEligibilityRepository - getEligibilityTypeForUser - not work properly");
        }
    }

    public void saveEligibilityType(String idUser, int typeValue) throws RepoException {
        String selectSql = "SELECT typeValue FROM EligibilityType WHERE idUser = ?";
        String updateSql = "UPDATE EligibilityType SET typeValue = ? WHERE idUser = ?";
        String insertSql = "INSERT INTO EligibilityType (idUser, typeValue) VALUES (?, ?)";

        try (Connection conn = dbUtils.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            selectStmt.setString(1, idUser);
            ResultSet resultSet = selectStmt.executeQuery();

            if (resultSet.next()) {
                // Entry exists, perform update
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, typeValue);
                    updateStmt.setString(2, idUser);
                    updateStmt.executeUpdate();
                }
            } else {
                // Entry does not exist, perform insert
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                    insertStmt.setString(1, idUser);
                    insertStmt.setInt(2, typeValue);
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new RepoException("VerifyEligibilityRepository - saveEligibilityType - not work properly");
        }
    }

}
