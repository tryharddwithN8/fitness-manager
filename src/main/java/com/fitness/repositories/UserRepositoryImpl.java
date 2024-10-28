package com.fitness.repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;

import com.fitness.config.ConnectionDB;
import com.fitness.model.person.User;
import com.fitness.repositories.Interface.IRepository;
import com.fitness.utility.UtilityIO;

/**
 * UserRepository
 */
public class UserRepositoryImpl implements IRepository<User, Integer>{

    @Override
    public Connection getConnection() throws SQLException {
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
            String sql = "SELECT * FROM users WHERE role = 'user'";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setFullName(resultSet.getString("fullname"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setUsername(resultSet.getString("username"));
                        user.setEmail(resultSet.getString("email"));
                        user.setAddress(resultSet.getString("address"));
                        user.setId(resultSet.getString("id"));
                        user.setRole(resultSet.getString("role"));
                        user.setCreateDate(resultSet.getString("created_at"));
                        String dob = resultSet.getString("dob");
                        user.setIsActive(resultSet.getBoolean("is_active")); 
                        if (dob != null) {
                            user.setDob(LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        }
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
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return null;
            }

            String sql = "SELECT * FROM users WHERE id = ? AND role = 'user'";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        User user = new User();
                        user.setId(String.valueOf(resultSet.getInt("id")));
                        user.setUsername(resultSet.getString("username"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setAddress(resultSet.getString("address"));
                        return user;
                    } else {
                        UtilityIO.showMsg("No user found with ID " + id);
                        return null;
                    }
                }
            } catch (SQLException e) {
                UtilityIO.showMsg("Error occurred while fetching user: " + e.getMessage());
                return null;
            }
        } catch (SQLException e) {
            UtilityIO.showMsg("Error occurred while establishing connection: " + e.getMessage());
            return null;
        }
    }

    @Override
    public int add(User entity) throws SQLException {
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return -1;
            }

            String sql = "INSERT INTO users (username, password, email, role, address, isActive) VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, entity.getUsername());
                statement.setString(2, entity.getPassword());
                statement.setString(3, entity.getEmail());
                statement.setString(4, entity.getRole());
                statement.setString(5, entity.getAddress());
                statement.setBoolean(6, true);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    UtilityIO.showMsg("User " + entity.getUsername() + " was added successfully!");
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
        try (Connection connection = getConnection()) {
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return -1;
            }

            String sql = "UPDATE users SET phone = ?, email = ?, address = ?, fullname = ?, dob = ? WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, entity.getPhone());
                statement.setString(2, entity.getEmail());
                statement.setString(3, entity.getAddress());
                statement.setString(4, entity.getFullName());
                if (entity.getDob() != null)
                    statement.setString(5, entity.getDob().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                else
                    statement.setString(5, "");

                statement.setInt(6, Integer.parseInt(entity.getId().replaceAll("\\D+", "")));

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    UtilityIO.showMsg("User " + entity.getUsername() + " was updated successfully!");
                    return rowsUpdated;
                } else {
                    UtilityIO.showMsg("No rows were updated.");
                    return -1;
                }
            } catch (SQLException e) {
                UtilityIO.showMsg("Error occurred while updating user: " + e.getMessage()
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
    public int delete(Integer id) throws SQLException {
        try {
            Connection connection = getConnection();
            if (connection == null || connection.isClosed()) {
                UtilityIO.showMsg("Failed to establish or maintain connection to the database.");
                return -1;
            }

            String sql = "DELETE FROM users WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    UtilityIO.showMsg("User with ID " + id + " was deleted successfully!");
                    return rowsDeleted;
                } else {
                    UtilityIO.showMsg("No rows were deleted.");
                    return -1;
                }
            } catch (SQLException e) {
                UtilityIO.showMsg("Error occurred while deleting user: " + e.getMessage()
                        + " SQLState: " + e.getSQLState()
                        + " ErrorCode: " + e.getErrorCode());
                return -1;
            }
        } catch (SQLException e) {
            UtilityIO.showMsg("Error occurred while establishing connection: " + e.getMessage());
            return -1;
        }
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
                    updateIsActive(username, true);
                    if (role.equals("admin"))
                        return 3; //  admin
                    else
                        return 1; // user thường

                }
            }

            return 0; // Tài khoản không tồn tại hoặc mật khẩu sai

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return -1; //  -1 nếu có lỗi xảy ra
        }
    }
    
    public int updateIsActive(String username, boolean isActive) {
        String updateSql = "UPDATE users SET is_active = ? WHERE username = ?";
    
        try (Connection connection = getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
            updateStatement.setBoolean(1, isActive);   // Đặt giá trị isActive
            updateStatement.setString(2, username);    // Đặt điều kiện là username
            int rowsUpdated = updateStatement.executeUpdate();
    
            if (rowsUpdated > 0) {
                System.out.println("User status updated successfully!");
                return 1; 
            } else {
                System.out.println("No user found with the given username.");
                return 0; 
            }
        } catch (SQLException e) {
            System.out.println("Error updating is_active status: " + e.getMessage());
            return -1; 
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

    public int getTotalUsers(){
        int totalUser = 0;
        String sql = "SELECT COUNT(*) AS total FROM users";

        try(Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    totalUser = resultSet.getInt("total");
                }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return totalUser;
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

    public List<User> display(String col, String val) {
        try {
            Connection connection = getConnection();
            if (connection == null || connection.isClosed()) {
                return null;
            }

            String sql = "";
            if (Objects.equals(col, "id")) {
                val = String.valueOf(Integer.parseInt(val));
                sql = "SELECT * FROM users WHERE id = ? AND role = 'user'";
            } else if (Objects.equals(col, "fullname")) {
                sql = "SELECT * FROM users WHERE role = 'user' AND " + col + " LIKE ?";
            } else if (Objects.equals(col, "username")) {
                sql = "SELECT * FROM users WHERE role = 'user' AND " + col + " LIKE ?";
            }

            List<User> users = new ArrayList<>();
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (Objects.equals(col, "id")) {
                    statement.setString(1, val);
                } else {
                    statement.setString(1, "%" + val + "%");
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        User user = new User();
                        user.setId(String.valueOf(resultSet.getInt("id")));
                        user.setUsername(resultSet.getString("username"));
                        user.setFullName(resultSet.getString("fullname"));
                        user.setPassword(resultSet.getString("password"));
                        user.setEmail(resultSet.getString("email"));
                        user.setPhone(resultSet.getString("phone"));
                        user.setIsActive(resultSet.getBoolean("is_active"));
                        user.setAddress(resultSet.getString("address"));
                        users.add(user);
                    }
                    return users;
                }
            } catch (SQLException e) {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int countActiveUsers() {
        String sql = "SELECT COUNT(*) FROM users WHERE is_active = true";
        
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
    
            if (resultSet.next()) {
                return resultSet.getInt(1); 
            }
        } catch (SQLException e) {
            System.out.println("Error counting active users: " + e.getMessage());
            return -1;
        }
        return 0; 
    }
    
}   