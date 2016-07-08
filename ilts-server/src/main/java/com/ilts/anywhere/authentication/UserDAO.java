package com.ilts.anywhere.authentication;

import java.util.List;

//import com.ilts.anywhere.response.Response;
import com.ilts.anywhere.authentication.model.User;
import com.ilts.anywhere.authentication.model.Role;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
//import org.hibernate.exception.ConstraintViolationException;

public interface UserDAO {
	
    
//    ---------   Existing codes ------------
//	public void register(User user)  ;
//	
//	public void register(User user, String role, String status) ;
	
    /**
     *
     * @param user
     * @return boolean
     * @throws SQLException
     * @throws SQLIntegrityConstraintViolationException
     */
    public Boolean register(User user) throws SQLException,SQLIntegrityConstraintViolationException ;	
    
    /**
     *
     * @param user
     * @param role
     * @param status
     * @return
     * @throws SQLException
     * @throws SQLIntegrityConstraintViolationException
     */
    public String register(User user, String role, String status) throws SQLException, SQLIntegrityConstraintViolationException;
    
    /**
     *
     * @param id
     * @return
     */
    public User getUserById(String id);

    /**
     *
     * @param username
     * @param password
     * @return
     */
    public Boolean validUser(String username, String password);

    /**
     *
     * @param username
     * @param password
     * @return user object
     * @throws Exception
     */
    public User login(String username, String password) throws SQLException;

    /**
     *
     * @param id
     * @return
     */
    public User searchUser(String id);

    /**
     *
     * @return
     */
    public List<User> getAllUsers();

    /**
     *
     * @return
     */
    public List<Object> getStatuses();

    /**
     *
     * @return
     */
    public List<Role> getRoles();

    /**
     *
     * @param username
     * @param email
     * @return string
     * @throws Exception
     */
    public String forgotPassword(String username, String email) throws Exception;

    /**
     *
     * @param userId
     * @param statusId
     */
    public void changeStatus(String userId, String statusId);

    /**
     *
     * @param userId
     * @param roleId
     */
    public void changeRole(String userId, String roleId);
}
