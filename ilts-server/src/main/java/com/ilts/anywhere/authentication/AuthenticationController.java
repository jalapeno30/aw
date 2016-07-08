/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: AuthenticationController.java
 */
package com.ilts.anywhere.authentication;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilts.anywhere.response.Response;

import java.util.List;

//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ilts.anywhere.authentication.model.Role;
import com.ilts.anywhere.authentication.model.User;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author ssanapureddy
 */
@Controller
public class AuthenticationController {
    // private static final ObjectMapper mapper = new ObjectMapper();
    private ValidUsers validUsers;
    private User authUser;

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * @param authenticationService the authenticationService to set
     */
    @Autowired(required = true)
    @Qualifier(value = "authenticationService")
    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

//    ---------   Existing codes ------------
//	private Boolean authenticateUser(String username, String password) {
//		
//		AuthenticationService authenticationService = new AuthenticationService();
//		User user = authenticationService.loginUser(username, password);
////		Boolean ret = authenticationService.loginUser(username, password);
//		if (user == null) {
//			return false;
//		} else {
//			return true;
//		}
////		return ret;
//	}

//	private void updateSessionStore(Session session) {
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//
//		Sessions sess = new Sessions();
//	}

    /**
     *
     */
    public class Sessions {
        /**
         *
         */
        public List<Session> sessions = new ArrayList<>();
    }

    /**
     *
     * @param username
     * @param password
     * @param response
     * @return response
     * 
     * authenticates the log in credentials of the end user
     * 
     */
    @RequestMapping(value = "/authentication/userLogin")
    public @ResponseBody
    ResponseEntity<Object> user(@RequestParam(value="username", required=true) String username,
                                @RequestParam(value="password", required=true) String password,
                                HttpServletResponse response) {
        return this.authenticationService.loginUser(username, password);
    }

    /**
     *
     * @param userId
     * @param token
     * @param response
     * @return response
     */
    @RequestMapping(value = "/authentication/isValidSession")
    public @ResponseBody
    Object validSession(@RequestParam(value="userId", required=true) String userId,
                        @RequestParam(value="token", required=true) String token,
                        HttpServletResponse response) {
        return new Object() {
            public Boolean status = true;
        };
    }

    /**
     *
     * @param userName
     * @param response
     * @return response
     * 
     * log outs the end user
     */
    @RequestMapping(value = "/authentication/userLogout")
    public @ResponseBody
    Object logout(@RequestParam(value="userId", required=true) String userName,
                  HttpServletResponse response) {
        return new Object() {
            public Boolean status = true;
            public String message = "Logged out.";
        };
    }

    /**
     *
     * @param username
     * @param email
     * @param response
     * @return response
     * 
     * authenticates the username and email to send an email containing password
     * to the end user
     */
    @RequestMapping(value = "/authentication/forgotPassword")
    public @ResponseBody
    Object forgotPassword(@RequestParam(value="username", required=true) String username,
                          @RequestParam(value="email", required=true) String email,
                          HttpServletResponse response) {
        return this.authenticationService.forgotPassword(username, email);
    }

    /**
     *
     * @param jsonRequest
     * @param response
     * @return response
     * 
     * authenticates the registration of the end user
     * 
     */
    @RequestMapping(value = "/authentication/register")
    public @ResponseBody
    Response register(@RequestBody String jsonRequest,
                      HttpServletResponse response) {
        AuthenticationService authenticationService = new AuthenticationService();
        Response registerResponse = authenticationService.registerUser(jsonRequest);

        return registerResponse;
    }

    /**
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/authentication/usersList")
    public @ResponseBody
    List<User> users(HttpServletResponse response) {
        return this.authenticationService.getUsers();
    }

    /**
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/authentication/statusList")
    public @ResponseBody
    List<Object> statuses(HttpServletResponse response) {
        return this.authenticationService.getStatuses();
    }

    /**
     *
     * @param response
     * @return
     */
    @RequestMapping("/authentication/rolesList")
    public @ResponseBody
    List<Role> roles(HttpServletResponse response) {
        return this.authenticationService.getRoles();
    }
	
//    ---------   Existing codes ------------
//	@RequestMapping(value = "/authentication/changeStatus")
//	public @ResponseBody Response changeStatus(
//			@RequestParam(value="userId", required=true) String userId,
//			@RequestParam(value="statusId", required=true) String statusId,
//			HttpServletResponse response) {
//		
//		return this.authenticationService.changeStatus(userId, statusId);
//	}
//	
//	@RequestMapping(value = "/authentication/changeRole")
//	public @ResponseBody Response changeRole(
//			@RequestParam(value="userId", required=true) String userId,
//			@RequestParam(value="roleId", required=true) String roleId,
//			HttpServletResponse response) {
//		
//		return this.authenticationService.changeRole(userId, roleId);
//	}
	
//	@RequestMapping("/authentication/validSession")
//	public @ResponseBody Response validSession(
//			@RequestParam(value="sessionToken", required=true) String sessionToken,
//			HttpServletResponse response) {
//		
//		return this.authenticationService.validSession(sessionToken);
//	}
	
    /**
     *
     * @param roleId
     * @param response
     * @return
     */
    @RequestMapping("/authentication/getRolePermissions")
    public @ResponseBody
    List<Permission> getRolePermissions(@RequestParam(value="roleId", required = true) String roleId,
                                        HttpServletResponse response) {
        return this.authenticationService.getRolePermissions(roleId);
    }

//    ---------   Existing codes ------------
//	@RequestMapping(value="/authentication/savePermissions", method=RequestMethod.POST)
//	public @ResponseBody ResponseEntity<Object> savePermissions(
//			@RequestParam(value="token", required = true) String token,
//			@RequestBody String jsonRequest,
//			HttpServletResponse response) {
//
//		if (!this.authenticationService.validSession(token)) {
//			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
//		} else if (!this.authenticationService.isAdmin(token)) {
//			return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
//		} else {
//			return new ResponseEntity<Object>(this.authenticationService.savePermissions(jsonRequest), HttpStatus.OK);
//		}
//
//	}
}