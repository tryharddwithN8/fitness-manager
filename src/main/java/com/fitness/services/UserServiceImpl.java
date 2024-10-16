package com.fitness.services;

import java.sql.SQLException;
import java.util.List;


import com.fitness.model.person.User;
import com.fitness.repositories.UserRepositoryImpl;
import com.fitness.services.Interface.IService;

/**
 * UserService
 */
public class UserServiceImpl implements IService<User, Integer> {

    private UserRepositoryImpl userRepoImpl = new UserRepositoryImpl();

    @Override
    public int add(User entity) throws SQLException {
        int checkAccount = userRepoImpl.checkExitsAccount(entity.getUsername(), entity.getEmail());
        if(checkAccount == 0)   return 0;   // account exits
        
        int result = userRepoImpl.add(entity);
        if(result != -1) return 1;  // add oke
        return -1;  // add failed
    }
    
    public int loginAuth(String username, String hashPass){
        int checkAccount = userRepoImpl.loginAuth(username, hashPass);
        if(checkAccount == 1)
            return 1;   // user
        if(checkAccount == 3)
            return 3;   //admin
        else
            return -1;
    }

    public int getEmailAccount(String email)
    {
        int check = userRepoImpl.getByEmailExist(email);
        if(check == 1)
            return 1;    // is exist
        return -1;
    }

    public int updatePasswd(String email, String pass){
        int check = userRepoImpl.updatePasswd(email, pass);
        if(check == 1)
            return 1;
        return -1;
    }

    @Override
    public List<User> getAll() throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public User getById(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public boolean update(User entity) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }



    // implement
}