package org.example;
import org.example.Enums.BloodType;
import org.example.Enums.DonationType;
import org.example.Enums.Sex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

public class UserRepository {
    private JdbcUtils dbUtils;

    // constructor
    public UserRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    public String login(String email, String password) throws RepoException{
        String sql = "SELECT id FROM UserTable WHERE email = ? AND password = ?";
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, email);
            ps.setString(2, password);

            try(ResultSet resultSet = ps.executeQuery()){
                if(resultSet.next()) {
                    return resultSet.getString("id");
                }
            }
        } catch (SQLException ex){
            throw new RepoException("UserRepository - login - not work properly");
        }

        return null;
    }

    public String searchAccount(String email, String CNP) throws RepoException{
        String sql = "SELECT id FROM UserTable WHERE email = ? AND CNP = ?";
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, email);
            ps.setString(2, CNP);

            try(ResultSet resultSet = ps.executeQuery()){
                if(resultSet.next()) {
                    return resultSet.getString("id");
                }
            }
        } catch (SQLException ex){
            throw new RepoException("UserRepository - searchAccount - not work properly");
        }

        return null;
    }

    public String register(String id, String email, String CNP, String password) throws RepoException{
        String sql = "INSERT INTO UserTable (id, email, CNP, password) VALUES (?, ?, ?, ?)";
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, String.valueOf(id));
            ps.setString(2, String.valueOf(email));
            ps.setString(3, String.valueOf(CNP));
            ps.setString(4, String.valueOf(password));
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RepoException("UserRepository - register - not work properly");
        }
        return login(email, password);
    }

    public Boolean resetPassword(String id, String email, String CNP, String password) throws RepoException{
        String sql = "UPDATE UserTable SET password = ? WHERE id = ? AND email = ? AND CNP = ?";
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, String.valueOf(password));
            ps.setString(2, String.valueOf(id));
            ps.setString(3, String.valueOf(email));
            ps.setString(4, String.valueOf(CNP));
            int modifiedRows = ps.executeUpdate();
            return modifiedRows == 1;
        }catch (SQLException e){
            throw new RepoException("UserRepository - resetPassword - not work properly");
        }
    }

    public void deleteRegister(String id) throws RepoException{
        String sql = "DELETE FROM UserTable WHERE id = ?";
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, String.valueOf(id));
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RepoException("UserRepository - deleteRegister - not work properly");
        }
    }

    public void saveGeneralInformation(String idUser, String firstName, String lastName, String birthDate,
                                          int sex, int bloodType, int points) throws RepoException {
        String sql = "INSERT INTO UserGeneralInformationTable (idUser, firstName, lastName, birthDate, sex, " +
                "bloodType, points) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, String.valueOf(idUser));
            ps.setString(2, String.valueOf(firstName));
            ps.setString(3, String.valueOf(lastName));
            ps.setString(4, String.valueOf(birthDate));
            ps.setString(5, String.valueOf(sex));
            ps.setString(6, String.valueOf(bloodType));
            ps.setString(7, String.valueOf(points));
            ps.executeUpdate();
        }catch (SQLException e){
            throw new RepoException("UserRepository - saveGeneralInformation - not work properly");
        }
    }

    public void deleteSaveGeneralInformation(String id) throws RepoException{
        String sql = "DELETE FROM UserGeneralInformationTable WHERE idUser = ?";
        try(Connection connection = dbUtils.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, String.valueOf(id));
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RepoException("UserRepository - deleteSaveGeneralInformation - not work properly");
        }
    }

    public UserInfo getGeneralInformation(String idUser) throws RepoException {
        String sql = "SELECT * FROM UserGeneralInformationTable WHERE idUser = ?";
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, idUser);

            try(ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    String id = resultSet.getString("idUser");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String birthDate = resultSet.getString("birthDate");
                    Sex sex = Sex.getObjectByValue(resultSet.getInt("sex"));
                    BloodType bloodType = BloodType.getObjectByValue(resultSet.getInt("bloodType"));
                    int points = resultSet.getInt("points");
                    return new UserInfo(id, firstName, lastName, birthDate, sex, bloodType, points);
                }
            }
        } catch (SQLException ex) {
            throw new RepoException("UserRepository - getGeneralInformation - not work properly");
        }
        return null;
    }

    public Vector<SimpleUserInfo> getAllDonatorsOnDate(String date, String centerId) throws RepoException {
        Vector<SimpleUserInfo> list = new Vector<>();
        String sql = "SELECT GI.idUser, GI.firstName, GI.lastName " +
                "FROM UserGeneralInformationTable GI " +
                "INNER JOIN BloodDonationSchedules SC " +
                "ON GI.idUser = SC.idUser " +
                "WHERE SC.dateAndTime LIKE ? " +
                "AND SC.centerId = ?";
        try(Connection conn = dbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, date + "%");
            ps.setString(2, centerId);
            try(ResultSet resultSet = ps.executeQuery()){
                while (resultSet.next()) {
                    list.add(new SimpleUserInfo(
                            resultSet.getString("idUser"),
                            resultSet.getString("firstName"),
                            resultSet.getString("lastName")
                    ));
                }
            }
        } catch (SQLException ex){
            throw new RepoException("UserRepository - getAllDonatorsOnDate - not work properly");
        }
        return list;
    }
}
