package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class DonationCenterRepository {
    private JdbcUtils dbUtils;

    // constructor
    public DonationCenterRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    public Vector<DonationCenter> getAllCenters() throws RepoException {
        Vector<DonationCenter> list = new Vector<>();
        String sql = "SELECT * FROM DonationCenters";
        try (Connection connection = dbUtils.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String latitude = resultSet.getString("latitude");
                String longitude = resultSet.getString("longitude");
                DonationCenter dc = new DonationCenter(id, name, latitude, longitude);
                list.add(dc);
            }
        } catch (SQLException ex) {
            throw new RepoException("DonationCenterRepository - getAllCenters - not work property");
        }
        return list;
    }

    public Vector<String> getDates(String date, String centerId) throws RepoException {
        Vector<String> list = new Vector<>();
        String sql = "SELECT dateAndTime FROM BloodDonationSchedules WHERE dateAndTime LIKE ? AND centerId = ?";
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, date + "%");
            ps.setString(2, centerId);
            try(ResultSet resultSet = ps.executeQuery()){
                while (resultSet.next()) {
                    list.add(resultSet.getString("dateAndTime"));
                }
            }
        } catch (SQLException ex){
            throw new RepoException("DonationCenterRepository - getDates - not work properly");
        }
        return list;
    }

    public void saveScheduleDate(String idUser, String date, String centerId) throws RepoException {
        String sql = "INSERT INTO BloodDonationSchedules (idUser, dateAndTime, centerId) VALUES (?, ?, ?)";
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, idUser);
            ps.setString(2, date);
            ps.setString(3, centerId);
            ps.executeUpdate();
        } catch (SQLException ex){
            throw new RepoException("DonationCenterRepository - saveScheduleDate - not work properly");
        }
    }

    public Vector<ScheduleCenter> getBookedForUser(String idUser) throws RepoException {
        Vector<ScheduleCenter> list = new Vector<>();
        String sql = "SELECT SC.dateAndTime, C.name " +
                "FROM BloodDonationSchedules SC " +
                "INNER JOIN DonationCenters C " +
                "ON SC.centerId = C.id " +
                "WHERE SC.idUser = ?";
        try(Connection conn = dbUtils.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, idUser);
            try(ResultSet resultSet = ps.executeQuery()){
                while (resultSet.next()) {
                    list.add(new ScheduleCenter(
                            resultSet.getString("dateAndTime"),
                            resultSet.getString("name")
                    ));
                }
            }
        } catch (SQLException ex){
            throw new RepoException("DonationCenterRepository - getBookedForUser - not work properly");
        }
        return list;
    }
}
