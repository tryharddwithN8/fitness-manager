package com.fitness.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.fitness.config.ConnectionDB;
import com.fitness.model.fitness.Course;
import com.fitness.model.fitness.Progress;
import com.fitness.model.fitness.Subscription;
import com.fitness.repositories.Interface.IRepository;
import com.fitness.utility.UtilityIO;

/**
 * SubscriptionRepository
 */
public class SubscriptionRepositoryImpl implements IRepository<Subscription, Integer>{
    
    @Override
    public Connection getConnection(){
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
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


    // implement
}