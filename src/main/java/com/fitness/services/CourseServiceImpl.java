package com.fitness.services;

import java.sql.SQLException;
import java.util.List;

import com.fitness.model.fitness.Course;
import com.fitness.repositories.CourseRepositoryImpl;
import com.fitness.services.Interface.IService;

/**
 * CourseService
 */
public class CourseServiceImpl implements IService<Course, Integer>{

    private CourseRepositoryImpl courseRepoImpl = new CourseRepositoryImpl();

    @Override
    public int add(Course entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public List<Course> getAll() throws SQLException {
        return courseRepoImpl.getAll();
    }

    @Override
    public Course getById(Integer id) throws SQLException {
        return courseRepoImpl.getById(id);
    }

    @Override
    public boolean update(Course entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        if (courseRepoImpl.delete(id) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public List<Course> displayCourses(String col, String val) throws SQLException {
        return courseRepoImpl.displayCourses(col, val);
    }

}