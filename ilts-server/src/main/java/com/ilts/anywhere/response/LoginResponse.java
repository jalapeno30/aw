/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: LoginResponse.java
 */
package com.ilts.anywhere.response;

import com.ilts.anywhere.authentication.model.Session;

import java.util.Date;

public class LoginResponse extends Response {
    public String token;
    public long expires;
    public String role;
    public String username;

    LoginResponse() {
        super(ResponseType.SUCCESSLOGIN);
        construct();
    }

    @Override
    protected void construct() {
        System.out.println("User has successfully logged in");
        this.setMessage("Successfully logged in"  );
        setStatus("success");
    }

    public void setSession(Session session) {
        this.token = session.getToken();
        Date expiration = session.getExpires();
        this.expires = expiration.getTime();
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
