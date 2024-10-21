package com.fitness.repositories;

import com.fitness.config.ConnectionDB;
import com.fitness.model.fitness.Course;
import com.fitness.model.person.Coach;
import com.fitness.model.person.User;
import com.fitness.repositories.Interface.IRepository;
import com.fitness.utility.UtilityIO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoachRepositoryImpl implements IRepository<Coach, Integer> {
    @Override
    public Connection getConnection() {
        return ConnectionDB.getConnection();
    }

    @Override
    public List<Coach> getAll() throws SQLException {
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
                        coach.setName(resultSet.getString("nameCoach"));
                        coach.setExperience(resultSet.getString("experience"));
                        coach.setBio(resultSet.getString("bio"));
                        coaches.add(coach);
                    }
                    if (coaches != null) {
                        return coaches;
                    } else {
                        UtilityIO.showMsg("No coach were found");
                        return null;
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
    }
    public void printCoaches() {
        try {
            List<Coach> coaches = getAll();
            if (coaches == null || coaches.isEmpty()) {
                System.out.println("No coaches found.");
            } else {
                for (Coach coach : coaches) {
                    System.out.println("Coach ID: " + coach.getId());
                    System.out.println("Name: " + coach.getName());
                    System.out.println("Experience: " + coach.getExperience());
                    System.out.println("Bio: " + coach.getBio());
                    System.out.println("Image Link: " + coach.getLinkImg());
                    System.out.println("-----------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error printing coaches: " + e.getMessage());
        }
    }
    @Override
    public Coach getById(Integer integer) throws SQLException {
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
        return 0;
    }
}
