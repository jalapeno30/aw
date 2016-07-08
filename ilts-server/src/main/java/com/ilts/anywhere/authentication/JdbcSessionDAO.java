/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: JdbcSessionDAO.java
 */
package com.ilts.anywhere.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import com.ilts.anywhere.authentication.model.User;
import javax.sql.DataSource;

@Repository
public class JdbcSessionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    JdbcSessionDAO() {
    }

    public void insert(Session session) {
        // TODO Auto-generated method stub
        try {

        this.jdbcTemplate.update("INSERT INTO sessions (userId, token, "
                        + "expires) VALUES (?, ?, ?)", new Object[] {
                        session.getUserId(), session.getToken(), session.getExpiration()});

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void disablePreviousSessions(String userId) {
        try {
            String sql = "UPDATE sessions SET deleted = 1 WHERE userId = ?";
            this.jdbcTemplate.update(sql, new Object[]{userId});
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void getSession(String token) {
        // TODO Auto-generated method stub
    }

    public boolean validSession(String token) {
        String sql = "SELECT COUNT(*) FROM sessions WHERE token = ? AND deleted = 0";
        int rowCount = this.jdbcTemplate.queryForObject(sql, new Object[]{token}, Integer.class);
       System.out.println(" **** IS ROW COUNT  *******"+rowCount);

        return (rowCount > 0);
    }

    public User getUserId(String token) {
        String sql = "SELECT user_id FROM sessions WHERE token=? AND deleted = 0";
        com.ilts.anywhere.authentication.model.User u= new User();
        String userId = this.jdbcTemplate.queryForObject(sql, new Object[]{token}, String.class);

       u.setUserId(userId);
       
       System.out.println(" **** IS valid session *******"+userId);
         return u;
    }

    public boolean isAdmin(String token) {
        String sql = "SELECT COUNT(*) FROM sessions "
                        + "JOIN user_roles ON user_roles.user_id = sessions.user_id "
                        + "WHERE token = ? "
                        + "AND sessions.deleted = 0 "
                        + "AND role_id = 1";

        int rowCount = this.jdbcTemplate.queryForObject(sql, new Object[]{token}, Integer.class);

        return (rowCount > 0);
    }
}
