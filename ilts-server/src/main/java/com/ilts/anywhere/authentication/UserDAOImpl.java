/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: UserDAOImpl.java
 */
package com.ilts.anywhere.authentication;

import com.ilts.anywhere.AppConfig;

import org.hibernate.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ilts.anywhere.authentication.model.User;
import com.ilts.anywhere.authentication.model.Role;
import com.ilts.anywhere.authentication.model.Status;
import com.ilts.anywhere.authentication.model.UserRoles;
import com.ilts.anywhere.authentication.model.UserStatus;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;

@Component
@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    /**
     *
     */
    public UserDAOImpl() {
        AppConfig appconfig = new AppConfig();
        sessionFactory = appconfig.getSessionFactory(appconfig.getDataSource());
    }

//    @Override
//    @Transactional
//    public void register(User user) throws SQLException,ConstraintViolationException  {
//        System.out.println("**** In register(user)...");
//        System.out.println(user.getUserName() + user.getPassword());
//        try {
//            this.register(user, "User", "Active");
//        } catch (ConstraintViolationException ex) {
//            throw ex;
//         
//        } 
//    }
    /**
     * Service calls register(User user) which in turn calls register(User user,
     * String role, String status) validates and throws exception accordingly
     *
     * @param user
     * @return boolean value
     * @throws java.sql.SQLIntegrityConstraintViolationException
     * @throws SQLException
     * @throws ConstraintViolationException
     */
    @Override
    @Transactional
    public Boolean register(User user) throws SQLIntegrityConstraintViolationException, SQLException {
        System.out.println("**** In register(user)...");
        System.out.println(user.getUserName() + user.getPassword());
    
        Boolean resultReg = false;

        if (this.register(user, "User", "Active").equalsIgnoreCase("success")) {
            resultReg = true;
        } else if (this.register(user, "User", "Active").equalsIgnoreCase("username")) {
            resultReg = false;
            throw new SQLIntegrityConstraintViolationException("Duplicate entry:user_name");
        } else if (this.register(user, "User", "Active").equalsIgnoreCase("email")) {
            resultReg = false;
            throw new SQLIntegrityConstraintViolationException("Duplicate entry:email");
        }

        return resultReg;
    }

    /**
     *
     * register(User user, String role, String status) calls
     * registerUserLogic(User user, String role, String status, Session session)
     * where it created session for hibernate, validates and checks matching
     * values in the database and returns message accordingly
     *
     * @param user
     * @param role
     * @param status
     * @return string
     * @throws SQLException
     * @throws SQLIntegrityConstraintViolationException
     */
    @Override
    @Transactional
    public String register(User user, String role, String status) throws SQLException, SQLIntegrityConstraintViolationException {
        String resultReg = "";
        Session session = sessionFactory.openSession();

        String hqlUser = "from User where user_name = :username ";
        String hqlEmail = "from User where email_id = :email";

        Query queryUser = (Query) session
                .createQuery(hqlUser);
        queryUser.setParameter("username", user.getUserName());
        Query queryEmail = (Query) session
                .createQuery(hqlEmail);
        queryEmail.setParameter("email", user.getEmailId());

        List<User> usersUser = queryUser.list();
        List<User> usersEmail = queryEmail.list();

        if (!usersUser.isEmpty()) {
            session.close();
            resultReg = "username";
        } else if (!usersEmail.isEmpty()) {
            session.close();
            resultReg = "email";
        } else {
            this.registerUserLogic(user, role, status, session);
            session.close();
            resultReg = "success";
        }

        return resultReg;
    }

    /**
     * registerUserLogic(User user, String role, String status, Session session)
     * validates the values passed with the database and returns the message
     * accordingly
     *
     * @param user
     * @param role
     * @param status
     * @param session
     */
    public void registerUserLogic(User user, String role, String status, Session session) {
 session.beginTransaction();

        User userNew = new User();
        Serializable uID;
        String roleHQL ="  from Role where roleName = :rname";
        String statusHQL ="  from Status where userStatusesName = :sname";
        System.out.println("**** In register(user, role, status)...");
        userNew.setUserName(user.getUserName());
        userNew.setPassword(user.getPassword());
        userNew.setFirstName(user.getFirstName());
        userNew.setLastName(user.getLastName());
        userNew.setBirthDate(user.getBirthDate());
        userNew.setGender(user.getGender().substring(0, 1));
        userNew.setEmailId(user.getEmailId());
        userNew.setPhoneNumber(user.getPhoneNumber());
        userNew.setAccountNumber(user.getAccountNumber());
        userNew.setAddress1(user.getAddress1());
        userNew.setAddress2(user.getAddress2());
        userNew.setCity(user.getCity());
        userNew.setState(user.getState());
        userNew.setZipcode(user.getZipcode());
        userNew.setCountry(user.getCountry());
        System.out.print("**********************|||||||**********************");
        uID = session.save(userNew);

        System.out.println("********    User ID ************* : " + uID);
        UserStatus userstaus = new UserStatus();
        Query queryStatus = (Query) session
                .createQuery(statusHQL);
         queryStatus.setParameter("sname", status);
          List<Status> statusList = queryStatus.list();
        int statusNum =0;
//        if(status.equalsIgnoreCase("active")){
//            statusNum=1;
//        }else if(status.equalsIgnoreCase("locked")){
//            statusNum=2;
//        }else if(status.equalsIgnoreCase("deleted")){
//            statusNum=3;
//        }
        userstaus.setUserStatusesId((Status) session.get(Status.class, statusList.get(0).getUserStatusesId()));
        userstaus.setUserId((User) session.get(User.class, uID));
        Serializable statusid =  session.save(userstaus);
       
        userstaus = (UserStatus) session.get(UserStatus.class, statusid);
        System.out.println("User Status Id 1st: " + userstaus.getUserStatusId());
        UserRoles ur = new UserRoles();
          Query queryRole = (Query) session
                .createQuery(roleHQL);
         queryRole.setParameter("rname", role);
           List<Role> roleList = queryRole.list();
        int roleNum =0;
//        if(role.equalsIgnoreCase("administrator")){
//            roleNum=1;
//        }else if(role.equalsIgnoreCase("user")){
//            roleNum =2;
//        }else if(role.equalsIgnoreCase("csp")){
//            roleNum =3;
//        }
        ur.setRoleId((Role) session.get(Role.class, roleList.get(0).getRoleId()));
        ur.setUserId(userNew);
        Serializable roleid =  session.save(ur);
        ur = (UserRoles) session.get(UserRoles.class, roleid);
        System.out.println("User role Id 1st: " + ur.getUserRoleId());

        session.getTransaction().commit();
    }

//    ---------- Existing codes -------------------
//    @Override
//    @Transactional
//    public void register(User user, String role, String status) throws SQLException,ConstraintViolationException {
//     
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        
//        
//        String hql = "from User where user_name = :username ";
//        Query query = (Query)session
//                .createQuery(hql);
//        query.setParameter("username", user.getUserName());
//        
//        List<User> users = query.list();
//        for(User u : users){
//         if(u.getUserName().equals(user.getUserName()) ){
//            throw new  ConstraintViolationException("Duplicate Entry for : ", new SQLException() ,user.getUserName());}
//         
//        }   
//        
//        
//        
//         User userNew = new User();Serializable uID;
//        System.out.println("**** In register(user, role, status)...");
//       //
//        //try{
//       
//        userNew.setUserName(user.getUserName());
//        userNew.setLastName(user.getLastName());
//        userNew.setGender(user.getGender());
//        userNew.setFirstName(user.getFirstName().substring(0, 1));
//        userNew.setPassword(user.getPassword());
//        userNew.setEmailId(user.getEmailId());
//        userNew.setBirthDate(user.getBirthDate());
//        userNew.setPhoneNumber(user.getPhoneNumber());
//        userNew.setAccountNumber(user.getAccountNumber());
//         uID = session.save(userNew);
////         }
////   catch (ConstraintViolationException  e) {
////      System.out.println("Exceptionllllllllllllllllllllllllllllll");
////       throw e;
////       
////      }finally{
////            session.close();;
////       }
//        System.out.println("User ID : " + uID);
//        UserStatus userstaus = new UserStatus();
//        userstaus.setUserStatusesId((Status) session.get(Status.class, 1));
//        userstaus.setUserId((User) session.get(User.class, uID));
//        Long statusid = (Long) session.save(userstaus);
//        userstaus = (UserStatus) session.get(UserStatus.class, statusid);
//        System.out.println("User Status Id 1st: " + userstaus.getUserStatusId());
//        UserRoles ur = new UserRoles();
//        ur.setRoleId((Role) session.get(Role.class, 2));
//        ur.setUserId(userNew);
//        Long roleid = (Long) session.save(ur);
//        ur = (UserRoles) session.get(UserRoles.class, roleid);
//        System.out.println("User role Id 1st: " + ur.getUserRoleId());
//        session.getTransaction().commit();
//      }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public User getUserById(String id) {
        return null;
    }

    /**
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Boolean validUser(String username, String password) {
        return null;
    }

    /**
     * login(String username, String password) validates log in details with
     * database and sends the response accordingly
     *
     * @param username
     * @param password
     * @return user object
     * @throws SQLException
     * @throws ConstraintViolationException
     */
    @Override
    @Transactional
    public User login(String username, String password) throws SQLException {
        String hql = " from User where user_name = :username and password = :password";
        Query query = (Query) sessionFactory
                .getCurrentSession()
                .createQuery(hql);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> users = query.list();
        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Object> getStatuses() {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Role> getRoles() {
        return null;
    }

    /**
     *
     * @param userId
     * @param statusId
     */
    @Override
    public void changeStatus(String userId, String statusId) {

    }

    /**
     *
     * @param userId
     * @param roleId
     */
    @Override
    public void changeRole(String userId, String roleId) {

    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public User searchUser(String id) {
        String hql = "from User where user_id = :id ";

        Query query = (Query) sessionFactory
                .getCurrentSession()
                .createQuery(hql);
        query.setParameter("id", id);
        List<User> users = query.list();

        return users.isEmpty() ? null : users.get(0);
    }

    /**
     *
     * @param userName
     * @param emailID
     * @return
     */
    @Transactional
    public User searchUser(String userName, String emailID) {
        String hql = "from User where user_name = :userName and emailId = :email ";

        Query query = (Query) sessionFactory
                .getCurrentSession()
                .createQuery(hql);
        query.setParameter("userName", userName);
        query.setParameter("email", emailID);
        List<User> users = query.list();

        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * forgotPassword(String username, String email) checks and get the values
     * from the database
     *
     * @param username
     * @param email
     * @return string
     * @throws Exception
     */
    @Override
    @Transactional
    public String forgotPassword(String username, String email) throws Exception {
        String hql = "select password from User where userName = :userName and emailId = :email ";

        Query query = (Query) sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("userName", username);
        query.setParameter("email", email);

        List<String> passwords = query.list();
        System.out.println("***********passwords list  impl*************" + passwords);

        return passwords.isEmpty() ? null : passwords.get(0);
    }
}
