package com.fitness.repositories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

import com.fitness.config.ConnectionDB;
import com.fitness.model.fitness.Course;
import com.fitness.repositories.Interface.IRepository;
import com.fitness.utility.UtilityIO;

/*
 * CourseRepository
 */
public class CourseRepositoryImpl implements IRepository<Course, Integer> {

    @Override
    public Connection getConnection() {
        return ConnectionDB.getConnection();
    }

    @Override
    public Course getById(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public int add(Course entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public int update(Course entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Course> getAll() throws SQLException {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return null;
            }

            String sql = "SELECT * FROM courses";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Course course = new Course();
                        course.setId(String.valueOf(resultSet.getInt("id")));
                        course.setName(resultSet.getString("course_name"));
                        course.setDescription(resultSet.getString("description"));
                        course.setCoachId(String.valueOf(resultSet.getInt("coach_id")));
                        course.setSchedule(String.valueOf(resultSet.getInt("duration_weeks")));
                        course.setMaxParticipants(resultSet.getInt("participants"));
                        course.setCurrentParticipants(resultSet.getInt("participants"));
                        course.setFee(resultSet.getDouble("fee"));
                        course.setDiscount(resultSet.getDouble("discount"));
                        courses.add(course);
                    }
                    if (courses != null) {
                        return courses;
                    } else {
                        UtilityIO.showMsg("No courses were found.");
                        return null;
                    }
                } catch (SQLException e) {
                    UtilityIO.showMsg("Error occurred while retrieving courses: " + e.getMessage()
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
}