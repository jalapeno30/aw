/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: GameDaoImpl.java
 */
package com.ilts.anywhere.games.dao;

import com.ilts.anywhere.AppConfig;

import java.util.List;

import com.ilts.anywhere.games.model.LottoSystem;
import com.ilts.anywhere.games.model.GamesVendorReqs;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ilts.anywhere.games.model.Draw;
import com.ilts.anywhere.games.model.Game;
import java.sql.SQLException;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

public class GameDaoImpl implements GameDao {

    @Autowired
    private SessionFactory sessionFactory;

    public GameDaoImpl() {
        AppConfig appconfig = new AppConfig();
        sessionFactory = appconfig.getSessionFactory(appconfig.getDataSource());
    }

    public GameDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Game> list() throws SQLException, HibernateException {
        List<Game> listGame = (List<Game>) sessionFactory.getCurrentSession()
                .createCriteria(Game.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
        if (listGame.isEmpty()) {
            return null;
        } else {
            return listGame;
        }
    }

    @Override
    public List<Draw> availableDrawsByGame(String gid) throws SQLException, HibernateException {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Draw draw where draw.game.gameId = :game";
        Query q = session.createQuery(hql);
        q.setParameter("game", gid);
        List<Draw> g = (List<Draw>) q.list();
        if (q.list().isEmpty()) {
            return null;
        } else {
            return q.list();
        }
    }

    @Override
    public void save(Game game) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
    }

    @Override
    public Game get(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Draw> getGameDraws(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LottoSystem> listSystems() throws SQLException, HibernateException {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from LottoSystem system order by system.systemNumber";
        Query q = session.createQuery(hql);

        if (q.list().isEmpty()) {
            return null;
        } else {
            return q.list();
        }

    }

    @Override
    public Object displayByGame(String gameId) throws SQLException, HibernateException {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from GamesVendorReqs gamereq where gamereq.gameId.gameId = :game";
        Query q = session.createQuery(hql);
        q.setParameter("game", gameId);
        List<GamesVendorReqs> g = (List<GamesVendorReqs>) q.list();
        for (GamesVendorReqs gm : g) {
            System.out.println("  OOOOOOOOOOOOOOOOOOOOOOOOO " + gm + "    ----------------- " + gm.getGameNumbersDisplay() + gm.getGameId()+" \n"+gm.getGameColor());
        }

        if (q.list().isEmpty()) {
            return null;
        } else {
            return q.list();
        }
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
