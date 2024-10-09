package com.fitness.repositories.Interface;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

/**
 * InnerIRepository
 */
public interface IRepository<T, ID> {
    
    Connection getConnection();   // get connection from db

    // Lấy tất cả các bản ghi
    List<T> getAll() throws SQLException;

    // Lấy bản ghi theo ID
    T getById(ID id) throws SQLException;

    // Thêm bản ghi mới
    void add(T entity) throws SQLException;

    // Cập nhật bản ghi
    void update(T entity) throws SQLException;

    // Xóa bản ghi theo ID
    void delete(ID id) throws SQLException;
}