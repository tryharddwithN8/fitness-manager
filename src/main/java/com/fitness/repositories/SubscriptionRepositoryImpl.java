package com.fitness.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.fitness.config.ConnectionDB;
import com.fitness.model.fitness.Course;
import com.fitness.model.fitness.Subscription;
import com.fitness.repositories.Interface.IRepository;
import com.fitness.utility.UtilityIO;

import static com.fitness.controller.LoginController.nameUser;
import static com.fitness.controller.User_Ctrl_fxml.pnlOrdersController.subcriptions;

/**
 * SubscriptionRepository
 */
public class SubscriptionRepositoryImpl implements IRepository<Subscription, Integer>{
    
    @Override
    public Connection getConnection() throws SQLException {
        return ConnectionDB.getConnection();    
    }

    @Override
    public List<Subscription> getAll() throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return new ArrayList<>();
            }
            String sql = "SELECT * FROM subscriptions";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Subscription subscription = new Subscription();
                        subscription.setId(String.valueOf(resultSet.getInt("id")));
                        subscription.setMemberId(resultSet.getString("user_id"));
                        subscription.setMembername(resultSet.getString("username"));
                        subscription.setCourseId(resultSet.getString("course_id"));
                        subscription.setStartDate(resultSet.getTimestamp("subscribed_at").toString());
                        subscriptions.add(subscription);
                    }
                    if (subscriptions != null) {
                        return subscriptions;
                    } else {
                        UtilityIO.showMsg("No subcription were found.");
                        return new ArrayList<>();
                    }
                } catch (SQLException e) {
                    UtilityIO.showMsg("Error occurred while retrieving subcription: " + e.getMessage()
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


    @Override
    public Subscription getById(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public int add(Subscription entity) throws SQLException {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int update(Subscription entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    public void saveSubDB() {
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return;
            }
            String checkSql = "SELECT COUNT(*) FROM subscriptions WHERE course_id = ? AND username = ?";
            String insertSql = "INSERT INTO subscriptions (course_id, username) VALUES (?, ?)";
            for (Course course : subcriptions) {
                String courseId = course.getId();
                if (courseId == null || courseId.isEmpty()) {
                    UtilityIO.showMsg("Invalid course ID for one of the courses.");
                    continue;
                }
                try (PreparedStatement checkStatement = connection.prepareStatement(checkSql)) {
                    checkStatement.setString(1, courseId);
                    checkStatement.setString(2, nameUser);
                    ResultSet rs = checkStatement.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);
                    if (count > 0) {
                        UtilityIO.showMsg("Subscription already exists for course: " + courseId);
                        continue;
                    }
                    try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                        insertStatement.setString(1, courseId);
                        insertStatement.setString(2, nameUser);
                        insertStatement.executeUpdate();
                    }
                } catch (SQLException e) {
                    UtilityIO.showMsg("Error occurred while adding user: " +
                            e.getMessage() + " SQLState: " + e.getSQLState() + " ErrorCode: " + e.getErrorCode());
                }
            }
        } catch (SQLException e) {
            UtilityIO.showMsg("Error occurred while establishing connection: " + e.getMessage());
        }
    }



}