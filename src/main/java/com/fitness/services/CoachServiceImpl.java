package com.fitness.services;

import com.fitness.model.person.Coach;
import com.fitness.services.Interface.IService;
import com.fitness.repositories.CoachRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class CoachServiceImpl implements IService<Coach, Integer> {
    private CoachRepositoryImpl coachRepoImpl = new CoachRepositoryImpl();

    @Override
    public int add(Coach entity) throws SQLException {
        return 0;
    }

    @Override
    public List<Coach> getAll() throws SQLException {
        return coachRepoImpl.getAll();
    }

    @Override
    public Coach getById(Integer integer) throws SQLException {
        return coachRepoImpl.getById(integer);
    }

    @Override
    public boolean update(Coach entity) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws SQLException {
        if (coachRepoImpl.delete(integer) == -1) {
            return false;
        }else {
            return true;
        }
    }

    public List<Coach> displayCoaches(String col, String key) throws SQLException {
        return coachRepoImpl.displayCoaches(col, key);
    }
}
