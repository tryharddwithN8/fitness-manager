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
    public Connection getConnection() throws SQLException {
        return ConnectionDB.getConnection();    
    }

    @Override
    public Course getById(Integer id) throws SQLException {
        try {
            Connection connection = getConnection();
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return null;
            }

            String sql = "SELECT * FROM courses WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Course course = new Course();
                        course.setId(String.valueOf(resultSet.getInt("id")));
                        course.setName(resultSet.getString("course_name"));
                        course.setCoachId(String.valueOf(resultSet.getInt("coach_id")));
                        course.setSchedule(String.valueOf(resultSet.getInt("duration_weeks")));
                        course.setMaxParticipants(resultSet.getInt("participants"));
                        course.setCurrentParticipants(resultSet.getInt("participants"));
                        course.setFee(resultSet.getDouble("fee"));
                        return course;
                    } else {
                        UtilityIO.showMsg("No course was found with the specified ID.");
                        return null;
                    }
                } catch (SQLException e) {
                    UtilityIO.showMsg("Error occurred while retrieving course: " + e.getMessage());
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
        Course course = getById(id);
        if (course == null) {
            return -1;
        }else {
            try (Connection connection = getConnection()) {
                if (connection == null || connection.isClosed()) {
                    UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                    return -1;
                }

                String sql = "DELETE FROM courses WHERE id = ?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, id);
                    int result = statement.executeUpdate();
                    if (result == 1) {
                        return 1;
                    } else {
                        UtilityIO.showMsg("Failed to delete course with ID: " + id);
                        return -1;
                    }
                } catch (SQLException e) {
                    UtilityIO.showMsg("Error occurred while preparing statement: " + e.getMessage());
                    return -1;
                }
            } catch (SQLException e) {
                UtilityIO.showMsg("Error occurred while establishing connection: " + e.getMessage());
                return -1;
            }
        }
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
                        course.setCoachId(String.valueOf(resultSet.getInt("coach_id")));
                        course.setSchedule(String.valueOf(resultSet.getInt("duration_weeks")));
                        course.setMaxParticipants(resultSet.getInt("participants"));
                        course.setCurrentParticipants(resultSet.getInt("participants"));
                        course.setDescription(resultSet.getString("description"));
                        course.setFee(resultSet.getDouble("fee"));
                        course.setDiscount(resultSet.getDouble("discount"));
                        course.setCoachName(resultSet.getString("coach_name"));
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

    public List<Course> displayCourses(String col, String val) throws SQLException {
        try {
            Connection connection = getConnection();
            if (connection == null || connection.isClosed()) {
                return null;
            }

            String sql = "";
            if(col.equalsIgnoreCase("id")) {
                val = String.valueOf(Integer.parseInt(val));
                sql = "SELECT * FROM courses WHERE id = ?";
            } else if (col.equalsIgnoreCase("course_name")) {
                sql = "SELECT * FROM courses WHERE " + col + " LIKE ?";
            }

            List<Course> courseList = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (col.equalsIgnoreCase("id")) {
                    statement.setString(1, val);
                } else {
                    statement.setString(1, "%" + val + "%");
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Course course = new Course();
                        course.setId(String.valueOf(resultSet.getInt("id")));
                        course.setName(resultSet.getString("course_name"));
                        course.setCoachId(String.valueOf(resultSet.getInt("coach_id")));
                        course.setSchedule(String.valueOf(resultSet.getInt("duration_weeks")));
                        course.setMaxParticipants(resultSet.getInt("participants"));
                        course.setCurrentParticipants(resultSet.getInt("participants"));
                        course.setFee(resultSet.getDouble("fee"));
                        courseList.add(course);
                    }
                    return courseList;
                } catch (SQLException e) {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}