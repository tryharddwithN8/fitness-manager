package com.fitness.repositories;

import com.fitness.model.fitness.Course;
import com.fitness.model.fitness.CourseOrder;
import com.fitness.model.person.Coach;
import com.fitness.utility.UtilityIO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.fitness.config.ConnectionDB.getConnection;
import static com.fitness.controller.LoginController.nameUser;
import static com.fitness.controller.User_Ctrl_fxml.pnlOrdersController.orderList;

public class OrderRepository {
    private CoachRepositoryImpl coachRepository=new CoachRepositoryImpl();
    List<Coach> coaches=coachRepository.getListCoach();

    public OrderRepository() throws SQLException {
    }
    public void print(){
        for (Course course:orderList){
            System.out.println(course);
        }
        if (orderList.isEmpty()) System.out.println("list null");
    }
    public void saveToDB() {
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return;
            }
            String sqlInsert = "INSERT INTO listorder (nameCoach, nameCourse, fee, discount, username, courseId) VALUES (?, ?, ?, ?, ?, ?)";
            String sqlCheck = "SELECT COUNT(*) FROM listorder WHERE courseId = ? AND username = ?";

            try (PreparedStatement statementInsert = connection.prepareStatement(sqlInsert);
                 PreparedStatement statementCheck = connection.prepareStatement(sqlCheck)) {
                for (Course course : orderList) {
                    for (Coach coach : coaches) {
                        if (course.getCoachId().equals(coach.getId())) {
                            statementCheck.setString(1, course.getId());
                            statementCheck.setString(2, nameUser);
                            try (ResultSet resultSet = statementCheck.executeQuery()) {
                                if (resultSet.next() && resultSet.getInt(1) == 0) {
                                    statementInsert.setString(1, coach.getFullName());
                                    statementInsert.setString(2, course.getName());
                                    statementInsert.setString(3, String.valueOf(course.getFee()));
                                    statementInsert.setString(4, String.valueOf(course.getDiscount()));
                                    statementInsert.setString(5, nameUser);
                                    statementInsert.setString(6, course.getId());
                                    int rowsAffected = statementInsert.executeUpdate();
                                    if (rowsAffected > 0) {
                                        System.out.println("Insert successful.");
                                    } else {
                                        System.out.println("Insert failed.");
                                    }
                                } else {
                                    System.out.println("Order already exists for course: " + course.getName());
                                }
                            }
                        }
                    }
                }

            } catch (SQLException e) {
                UtilityIO.showMsg("Error occurred while adding data: " + e.getMessage()
                        + " SQLState: " + e.getSQLState()
                        + " ErrorCode: " + e.getErrorCode());
            }
        } catch (SQLException e) {
            UtilityIO.showMsg("Error occurred while establishing connection: " + e.getMessage());
        }
    }



    public List<CourseOrder> getAllOrder() throws SQLException {
        List<CourseOrder> courseOrders = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return null;
            }
            String sql = "SELECT * FROM listorder";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        CourseOrder courseOrder = new CourseOrder();
                        courseOrder.setCoachName(resultSet.getString("nameCoach"));
                        courseOrder.setName(resultSet.getString("nameCourse"));
                        courseOrder.setFee(resultSet.getDouble("fee"));
                        courseOrder.setDiscount(resultSet.getDouble("discount"));
                        courseOrder.setUsername("username");
                        courseOrders.add(courseOrder);
                    }
                } catch (SQLException e) {
                    UtilityIO.showMsg("Error occurred while retrieving users: " + e.getMessage()
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
        return courseOrders;
    }
    public void deleteOrder(){
        try (Connection connection = getConnection()){
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return;
            }
            String truncateSql = "TRUNCATE TABLE listorder";
            try (PreparedStatement truncateStatement = connection.prepareStatement(truncateSql)) {
                truncateStatement.executeUpdate();
                System.out.println("Table truncated successfully.");
            } catch (SQLException e) {
                UtilityIO.showMsg("Error occurred while truncating table: " + e.getMessage()
                        + " SQLState: " + e.getSQLState()
                        + " ErrorCode: " + e.getErrorCode());
            }

        } catch (SQLException e) {
            UtilityIO.showMsg("Error occurred while establishing connection: " + e.getMessage());
        }
    }

}
