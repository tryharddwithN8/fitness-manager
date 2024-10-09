package com.fitness.repositories;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

import com.fitness.config.ConnectionDB;
import com.fitness.model.fitness.Subscription;
import com.fitness.repositories.Interface.IRepository;

/**
 * SubscriptionRepository
 */
public class SubscriptionRepositoryImpl implements IRepository<Subscription, Integer>{
    
    @Override
    public Connection getConnection(){
        return ConnectionDB.getConnection();
    }

    @Override
    public List<Subscription> getAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public Subscription getById(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public void add(Subscription entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }

    @Override
    public void update(Subscription entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }


    // implement
}