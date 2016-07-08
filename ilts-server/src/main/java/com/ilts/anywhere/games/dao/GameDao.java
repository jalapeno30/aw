/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: GameDao.java
 */
package com.ilts.anywhere.games.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ilts.anywhere.games.model.Draw;
import com.ilts.anywhere.games.model.Game;
import java.sql.SQLException;
import org.hibernate.HibernateException;

@Component
public interface GameDao {
    
    public List<Game> list() throws SQLException,HibernateException;

    public void save(Game game) throws SQLException,HibernateException;

    public void delete(String id) throws SQLException,HibernateException;

    public Game get(String id) throws SQLException,HibernateException;

    public List<Draw> getGameDraws(String id) throws SQLException,HibernateException;

    public Object availableDrawsByGame(String gameId) throws SQLException,HibernateException;
    public Object displayByGame(String gameId) throws SQLException,HibernateException;

    public Object listSystems() throws SQLException,HibernateException;

}
