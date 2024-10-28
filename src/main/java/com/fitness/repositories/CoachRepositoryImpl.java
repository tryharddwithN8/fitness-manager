package com.fitness.repositories;

import com.fitness.config.ConnectionDB;
import com.fitness.model.person.Coach;
import com.fitness.repositories.Interface.IRepository;
import com.fitness.utility.UtilityIO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoachRepositoryImpl implements IRepository<Coach, Integer> {

    @Override
    public Connection getConnection() throws SQLException {
        return ConnectionDB.getConnection();    
    }

    public int getTotalCoachs(){
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM courses";

        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    total = resultSet.getInt("total");
                }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public List<Coach> getAll() throws SQLException {
        try {
            Connection connection = getConnection();
            if(connection == null || connection.isClosed()){
                return null;
            }

            String sql = "SELECT * FROM users WHERE role = 'coach'";

            List<Coach> coaches = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    Coach coach = new Coach();
                    coach.setId(String.valueOf(resultSet.getInt("id")));
                    coach.setFullName(resultSet.getString("fullName"));
                    coach.setUsername(resultSet.getString("username"));
                    coach.setPassword(resultSet.getString("password"));
                    coach.setEmail(resultSet.getString("email"));
                    coach.setRole(resultSet.getString("role"));
                    coach.setPhone(resultSet.getString("phone"));
                    coach.setAddress(resultSet.getString("address"));
                    coaches.add(coach);
                }

                return coaches;
            } catch (SQLException e) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Coach getById(Integer id) throws SQLException {
        try {
            Connection connection = getConnection();
            if(connection == null || connection.isClosed()){
                return null;
            }

            String sql = "SELECT * FROM users WHERE id = ? AND role = 'coach'";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Coach coach = new Coach();
                        coach.setId(String.valueOf(resultSet.getInt("id")));
                        coach.setUsername(resultSet.getString("username"));
                        coach.setPassword(resultSet.getString("password"));
                        coach.setEmail(resultSet.getString("email"));
                        coach.setRole(resultSet.getString("role"));
                        coach.setAddress(resultSet.getString("address"));
                        return coach;
                    }
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int add(Coach entity) throws SQLException {
        return 0;
    }

    @Override
    public int update(Coach entity) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Integer integer) throws SQLException {
        try {
            Connection connection = getConnection();
            if(connection == null || connection.isClosed()){
                return -1;
            }

            String sql = "DELETE FROM coaches WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, integer);
                return statement.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public List<Coach> displayCoaches(String col, String key) throws SQLException {
        try {
            Connection connection = getConnection();
            if (connection == null || connection.isClosed()) {
                return null;
            }

            String sql = "";
            if (Objects.equals(col, "id")) {
                key = String.valueOf(Integer.parseInt(key));
                sql = "SELECT * FROM users WHERE id = ? AND role = 'coach'";
            } else if (Objects.equals(col, "username")) {
                sql = "SELECT * FROM users WHERE role = 'coach' AND " + col + " LIKE ?";
            }

            List<Coach> coaches = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (Objects.equals(col, "id")) {
                    statement.setString(1, key);
                } else {
                    statement.setString(1, "%" + key + "%");
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Coach coach = new Coach();
                        coach.setId(String.valueOf(resultSet.getInt("id")));
                        coach.setFullName(resultSet.getString("fullName"));
                        coach.setUsername(resultSet.getString("username"));
                        coach.setEmail(resultSet.getString("email"));
                        coach.setPhone(resultSet.getString("phone"));
                        coach.setAddress(resultSet.getString("address"));
                        coaches.add(coach);
                    }
                    return coaches;
                }
            } catch (SQLException e) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Coach> getListCoach() throws SQLException {
        List<Coach> coaches = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return null;
            }
            String sql = "SELECT * FROM coaches";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Coach coach = new Coach();
                        coach.setLinkImg(resultSet.getString("linkImg"));
                        coach.setId(String.valueOf(resultSet.getInt("id")));
                        coach.setFullName(resultSet.getString("nameCoach"));
                        coach.setExperience(resultSet.getString("experience"));
                        coach.setBio(resultSet.getString("bio"));
                        coaches.add(coach);
                    }
                    if (coaches != null) {
                        return coaches;
                    } 
                } catch (SQLException e) {
                    UtilityIO.showMsg("Error occurred while retrieving coaches: " + e.getMessage()
                            + " SQLState: " + e.getSQLState()
                            + " ErrorCode: " + e.getErrorCode());
                    return null;
                }
            } catch (SQLException e) {
                UtilityIO.showMsg("Error occurred while preparing statement: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            UtilityIO.showMsg("Error occurred while establishing connection: " + e.getMessage());
            return null;
        }
        return coaches;
    }

    public void print() throws SQLException {
        List<Coach> newCoach=getListCoach();
        for (Coach coach:newCoach){
            System.out.println(coach);
        }
    }
}
