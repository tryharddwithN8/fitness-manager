package com.fitness.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.fitness.config.ConnectionDB;
import com.fitness.model.fitness.Course;
import com.fitness.model.fitness.Workout;
import com.fitness.repositories.Interface.IRepository;
import com.fitness.utility.UtilityIO;


/**
 * WorkoutRepository
 */
public class WorkoutRepositoryImpl implements IRepository<Workout, Integer>{


    @Override
    public Connection getConnection(){
        return ConnectionDB.getConnection();
    }

    @Override
    public List<Workout> getAll() throws SQLException {
        List<Workout> workouts = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return null;
            }
            String sql = "SELECT * FROM workouts";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Workout workout = new Workout();
                        workout.setId(String.valueOf(resultSet.getInt("id")));
                        workout.setCourseId(String.valueOf(resultSet.getInt("course_id")));
                        workout.setdescription(resultSet.getString("description"));
                        workout.setDuration_minustes(resultSet.getInt("duration_minutes"));
                        workout.setWorkout_name(resultSet.getString("workout_name"));
                        workouts.add(workout);
                    }
                    if (workouts != null) {
                        return workouts;
                    } else {
                        UtilityIO.showMsg("No workouts were found.");
                        return null;
                    }
                } catch (SQLException e) {
                    UtilityIO.showMsg("Error occurred while retrieving workouts: " + e.getMessage()
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
    public Workout getById(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public int add(Workout entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public int update(Workout entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    
}
