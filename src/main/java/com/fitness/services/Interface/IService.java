package com.fitness.services.Interface;

import java.sql.SQLException;
import java.util.List;
/**
 * IService
 */
public interface IService<T, ID> {
    // Thêm một đối tượng mới
    int add(T entity) throws SQLException;

    // Lấy danh sách tất cả đối tượng
    List<T> getAll() throws SQLException;

    T getById(ID id) throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean delete(ID id) throws SQLException;
}