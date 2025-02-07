package org.example;

import org.example.Enums.DonationType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class DonationHistoryRepository {
    private JdbcUtils dbUtils;

    //constructor
    public DonationHistoryRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    public Vector<DonationHistoryEntry> getAllDonationHistory(String idUser) throws RepoException {
        Vector<DonationHistoryEntry> list = new Vector<>();
        String sql = "SELECT * FROM DonationHistory WHERE idUser = ?";
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, idUser);

            try(ResultSet resultSet = ps.executeQuery()) {
                while(resultSet.next()) {
                    String id = resultSet.getString("idUser");
                    String date = resultSet.getString("date");
                    DonationType type = DonationType.getObjectByValue(resultSet.getInt("type"));
                    DonationHistoryEntry dhe = new DonationHistoryEntry(id, date, type);
                    list.add(dhe);
                }
            }
        } catch (SQLException ex) {
            throw new RepoException("DonationHistoryRepository - getAllDonationHistory - not work properly");
        }
        return list;
    }
}
