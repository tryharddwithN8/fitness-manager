package com.fitness.repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.fitness.config.ConnectionDB;
import com.fitness.model.person.User;
import com.fitness.repositories.Interface.IRepository;
import com.fitness.utility.UtilityIO;

/**
 * UserRepository
 */
public class UserRepositoryImpl implements IRepository<User, Integer>{

    @Override
    public Connection getConnection(){
        return ConnectionDB.getConnection();
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return null;
            }
            String sql = "SELECT * FROM users";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setUsername(resultSet.getString("username"));
                        user.setEmail(resultSet.getString("email"));
                        user.setAddress(resultSet.getString("address"));
                        users.add(user);
                    }
                    if (users != null) {
                        return users;
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
        return users;
    }


    @Override
    public User getById(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public int add(User entity) throws SQLException {
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return -1;
            }

            String sql = "INSERT INTO users (username, password, email, role, address) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, entity.getUsername());
                statement.setString(2, entity.getPassword());
                statement.setString(3, entity.getEmail());
                statement.setString(4, entity.getRole());
                statement.setString(5, entity.getAddress());


                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    UtilityIO.showMsg("user "+entity.getUsername()+" was added successfully!");
                    return rowsInserted;
                } else {
                    UtilityIO.showMsg("No rows were inserted.");
                    return -1;
                }
            } catch (SQLException e) {
                UtilityIO.showMsg("Error occurred while adding user: " + e.getMessage()
                        + " SQLState: " + e.getSQLState()
                        + " ErrorCode: " + e.getErrorCode());
                return -1;
            }
        } catch (SQLException e) {
            UtilityIO.showMsg("Error occurred while establishing connection: " + e.getMessage());
            return -1;
        }
    }


    @Override
    public int update(User entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    public int getInfoByUsername(String user){
        /**
         * input: username
         * output: List(all info from user)
         */
        return 3233;
    }

    public int loginAuth(String username, String password) {
        String sql = "SELECT password, role FROM users WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                String role = resultSet.getString("role");

                if (password.equals(hashedPassword)) {
                    if (role.equals("admin"))
                        return 3; //  admin
                    else
                        return 1; // user thường

                }
            }

            return 0; 

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return -1; //  -1 nếu có lỗi xảy ra
        }
    }

    public int getByEmailExist(String email) {
        int exists = 0;

        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0)
                        exists = 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }


    public int checkExitsAccount(String username, String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0)
                    return 0;
            }

            return 1;

        } catch (SQLException e) {
            UtilityIO.showMsg("Error occurred while checking account: " + e.getMessage());
            return -1;
        }
    }

    public int updatePasswd(String email, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int result = -1;

        try {
            conn = getConnection();

            String sql = "UPDATE users SET password = ? WHERE email = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, password);
            pstmt.setString(2, email);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                result = 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

}