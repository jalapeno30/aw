/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: AuthenticationService.java
 */
package com.ilts.anywhere.authentication;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.*;

import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;
import com.ilts.anywhere.utils.SendEmail;
import com.ilts.anywhere.response.Response;
import com.ilts.anywhere.response.ResponseFactory;
import com.ilts.anywhere.response.ResponseType;
import com.ilts.anywhere.response.http.HttpResponse;
import com.ilts.anywhere.response.http.HttpResponseType;
import com.ilts.anywhere.authentication.model.User;
import com.ilts.anywhere.authentication.model.Session;
import com.ilts.anywhere.authentication.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import javax.mail.MessagingException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.transaction.annotation.Transactional;

@Service
@ComponentScan
public class AuthenticationService {

    @Autowired
    private UserDAO userDAO;
//	private JdbcUserDAO userDAO;

    @Autowired
    private SessionDAO sessionDAO;
//	private JdbcSessionDAO sessionDAO;

    @Autowired
    private JdbcPermissionsDAO permissionsDAO;

    AuthenticationService() {
        this.userDAO = new UserDAOImpl();
    }

    /**
     * 
     * When the end user wants to register 
     *
     * registerUser(String registerJSON) the service method, checks and sends the response accordingly
     *
     * @param registerJSON
     * @return response
     */
    @Transactional
    public Response registerUser(String registerJSON) {
//        UserWrapper userWrap = new UserWrapper();
        User user = null;
        Role role = null;
        Response registerResponse = null;

        try {
            ObjectMapper mapperUser = new ObjectMapper();
            mapperUser.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            user = mapperUser.readValue(registerJSON, User.class);
            System.out.println();
            System.out.println("****************************JSON parse**************************************");
            System.out.println("user.getUserName(): " + user.getUserName());

            System.out.println("user.getPassword(): " + user.getPassword());
            System.out.println("user.getBirthDate(): " + user.getBirthDate());
            System.out.println("user.getFirstName(): " + user.getFirstName());
            System.out.println("user.getLastName() :" + user.getLastName());
            System.out.println("user.getGender(): " + user.getGender());
            System.out.println("user.getEmailID(): " + user.getEmailId());
            System.out.println("user.getPhoneNumber(): " + user.getPhoneNumber());
            System.out.println("user.getAddressOne(): " + user.getAddress1());

            ObjectMapper mapperRole = new ObjectMapper();
            mapperRole.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            role = mapperRole.readValue(registerJSON, Role.class);

            System.out.print("Role name : ");
            System.out.println("" + role.getRoleName());
            System.out.println("**************************Parse Ends****************************************");
        } catch (Exception e) {
            Logger.logException(CustomLogger.LogType.SEVERE, Boolean.TRUE, "JSON parse failed", e);
        }

        try {
//  --------------------Existing codes----------------
            //        User user = new User();
//        user.setUserName(userWrap.getUsername());
//        user.setPassword(userWrap.getPassword());
//        user.setFirstName(userWrap.getFirstName());
//        user.setLastName(userWrap.getLastName());
//        user.setBirthDate(userWrap.getBirthDate());
//        user.setGender(userWrap.getGender());
          
            this.userDAO.register(user);
            Logger.logMsg(CustomLogger.LogType.INFO, true, "Successfully Registered  " + user.getUserName());

            registerResponse = ResponseFactory.makeResponse(ResponseType.SUCCESSREGISTER);
            registerResponse.setMessage("Successfully registered");
        } 
        catch (SQLIntegrityConstraintViolationException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "SQLIntegrityConstraintViolationException", ex);

            registerResponse = ResponseFactory.makeResponse(ResponseType.ERROR);
            registerResponse.setMessage(String.format("Unsuccessfully registered due to - %s", ex.getMessage()));
        }
        catch (ConstraintViolationException | SQLException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "ConstraintViolationException | SQLException", ex);

            registerResponse = ResponseFactory.makeResponse(ResponseType.ERROR);
            registerResponse.setMessage(String.format("Unsuccessfully registered due to - %s", ex.getMessage()));
        } 
        catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);

            registerResponse = ResponseFactory.makeResponse(ResponseType.ERROR);
            registerResponse.setMessage(String.format("Unsuccessfully registered due to - %s", ex.getMessage()));
        }

        return registerResponse;
    }

    /**
     * When the end user tries to log in
     * loginUser(String username, String password) the service method,  checks, updates the session data  and sends the response accordingly
     * 
     * @param username
     * @param password
     * @return ResponseEntity
     */
    public ResponseEntity<Object> loginUser(String username, String password) {
        HttpResponse httpResponse = null;
        try {
            final User user;
            user = this.userDAO.login(username, password);

            if (user != null) {
                Date date = new Date();
                final Session session = new Session(user, Boolean.FALSE, date);

                /**
                 * Not Sure why disablePreviousSessions(session) this method is
                 * here. It deletes all the previous sessions that were been
                 * tagged to the user that is trying to log in It sets delete
                 * true in the database.
                 */
             // this.sessionDAO.disablePreviousSessions(session);
                this.sessionDAO.insert(session);
System.out.println("*************** after insertions *******************************");
System.out.println("*************** get token insertions *******************************"+session.getToken());
                Object data = new Object() {
                    public String status = "success";
                    public String message = "successfully logged in";
                    public String token = session.getToken();
                   
                    public String userName = user.getUserName();
                    public String role = user.getUserRolesList().listIterator().next().getUserRoleId();
                };
                 System.out.println("    ***********************   data    "+data.toString());
System.out.println("    **************use r Role********* "+  user.getUserRolesList().isEmpty()+user.getUserRolesList().listIterator().next().getRoleId());
                Logger.logMsg(CustomLogger.LogType.INFO, true, "Success Login ");

                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);
            } else {
                Object data = new Object() {
                    public String status = "error";
                    public String message = "Invalid username or password";
                };

                Logger.logMsg(CustomLogger.LogType.DEBUG, true, "Invalid username or password for " + username);

                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.FORBIDDEN,data);
            }
        } catch (SQLException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "SQLException", ex);

            httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.FORBIDDEN);
            httpResponse.setCustomMessage(String.format("Cannot Login - %s", ex.getMessage()));
        }  catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);

            httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.FORBIDDEN);
            httpResponse.setCustomMessage(String.format("Cannot Login - %s", ex.getMessage()));
        } 

        return httpResponse.getResponseEntity();
    }

    /**
     * When the end user forgets password 
     * forgotPassword(String username, String email) checks and sends the password to the email end user entered
     * and the response is send accordingly by this service
     *
     * @param username
     * @param email
     * @return response
     */
    public Response forgotPassword(String username, String email) {
        Boolean bStatus = true;
        String forgotStatus = "";
        Response forgotResponse = null;
        SendEmail sendEmail = new SendEmail();

        try {
            forgotStatus = this.userDAO.forgotPassword(username, email);

            if ((forgotStatus == null) || (forgotStatus.isEmpty() == true)) {
                bStatus = false;
            }
        } catch (Exception ex) {
            bStatus = false;

            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);

            forgotResponse = ResponseFactory.makeResponse(ResponseType.ERROR);
            forgotResponse.setMessage(String.format("Unsuccessfully sent password due to - %s", ex.getMessage()));
        }

        if (bStatus == true) {
            try {
                if (forgotStatus != null) {
                    String successFail = sendEmail.generateAndSendEmail(username, email, forgotStatus);
                    System.out.println("SuccessFail: " + successFail);

                    if (successFail.compareToIgnoreCase("success") == 0) {
                        Logger.logMsg(CustomLogger.LogType.INFO, true, "Valid " + email + " and Forgot password status is: " + forgotStatus);

                        forgotResponse = ResponseFactory.makeResponse(ResponseType.SUCCESSSENDPASSWORD);
                        forgotResponse.setMessage(String.format("Successfully sent password to - '%s'", email));
                    } else {
                        Logger.logMsg(CustomLogger.LogType.DEBUG, true, "Invalid " + email + " and Forgot password status is: " + forgotStatus);

                        forgotResponse = ResponseFactory.makeResponse(ResponseType.ERROR);
                        forgotResponse.setMessage(String.format("Unsuccessfully sent using email: '%s'", email + " and " + username));
                    }
                }
            } catch (MessagingException ex) {
                Logger.logException(CustomLogger.LogType.SEVERE, true, "MessagingException", ex);

                forgotResponse = ResponseFactory.makeResponse(ResponseType.ERROR);
                forgotResponse.setMessage(String.format("Unsuccessfully sent password due to exception"));
            }
        } else {
            Logger.logMsg(CustomLogger.LogType.DEBUG, true, "Invalid " + email);

            forgotResponse = ResponseFactory.makeResponse(ResponseType.ERROR);
            forgotResponse.setMessage(String.format("Unsuccessfully sent to '%s'", email));
        }

        return forgotResponse;
    }

    /**
     *
     * @return
     */
    public List<User> getUsers() {

        return this.userDAO.getAllUsers();

    }

    /**
     *
     * @return
     */
    public List<Object> getStatuses() {

        return this.userDAO.getStatuses();

    }

    /**
     *
     * @return
     */
    public List<Role> getRoles() {

        return this.userDAO.getRoles();

    }
//   -------------- Exisiting codes --------------------------
//    public Response changeStatus(String userId, String statusId) {
//
//        this.userDAO.changeStatus(userId, statusId);
//
//        Response response = ResponseFactory.makeResponse(ResponseType.SUCCESSCHANGESTATUS);
//        return response;
//
//    }
//
//    public Response changeRole(String userId, String roleId) {
//
//        this.userDAO.changeRole(userId, roleId);
//
//        Response response = ResponseFactory.makeResponse(ResponseType.SUCCESSCHANGEROLE);
//        return response;
//
//    }

    /**
     *
     * @param token
     * @return
     */
    public Boolean validSession(String token) {
        return this.sessionDAO.validSession(token);
    }

    /**
     *
     * @param token
     * @return
     */
    public String getUserId(String token) {
        return this.sessionDAO.getUserId(token);
    }

    /**
     *
     * @param token
     * @return
     */
    public Boolean isAdmin(String token) {
        return this.sessionDAO.isAdmin(token);
    }

    /**
     *
     * @param roleId
     * @return
     */
    public List<Permission> getRolePermissions(String roleId) {
        return this.permissionsDAO.getPermissionsByRole(roleId);
    }

//    public Object savePermissions(String jsonRequest) {
//
//        // parse post data
//        ObjectMapper mapper = new ObjectMapper();
//        List<Permission> permissions = new ArrayList<Permission>();
//
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        try {
//            permissions = mapper.readValue(jsonRequest, new TypeReference<List<Permission>>() {
//            });
//        } catch (JsonParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        this.permissionsDAO.savePermissions(permissions);
//
//        return ResponseFactory.makeResponse(ResponseType.SUCCESSDATA);
//    }
//	public Response validSession(String sessionToken) {
//		Boolean valid = this.sessionDAO.validSession(sessionToken);
//		
//		if (valid) {
//			final Response response = ResponseFactory.makeResponse(ResponseType.SUCCESSVALIDSESSION);
//		} else {
//			final Response response = ResponseFactory.makeResponse(ResponseType.ERRORINVALIDSESSION);
//		}
//		
//		return response;
//	}
    /**
     * @param userDAO the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

}
