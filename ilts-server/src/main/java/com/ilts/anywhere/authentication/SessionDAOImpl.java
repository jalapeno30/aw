package com.ilts.anywhere.authentication;

import com.ilts.anywhere.AppConfig;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class SessionDAOImpl implements SessionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionDAOImpl() {
          AppConfig appconfig = new AppConfig();
        sessionFactory = appconfig.getSessionFactory(appconfig.getDataSource());
    }

    @Override
    @Transactional
        public void insert(com.ilts.anywhere.authentication.model.Session session) {
       org.hibernate.Session sessionHibernate = sessionFactory
                .getCurrentSession();
      System.out.println("******************** insert Session *****************");
                sessionHibernate.save(session);
       
    }

    @Override
    @Transactional
    public void disablePreviousSessions(com.ilts.anywhere.authentication.model.Session session) {
        org.hibernate.Session sess = sessionFactory
                .getCurrentSession();
              System.out.println("******************** disable session Session *****************");

        List<com.ilts.anywhere.authentication.model.Session> sessions = sess
                .createCriteria(com.ilts.anywhere.authentication.model.Session.class)
                .add(Restrictions.eq("deleted", false))
                .add(Restrictions.eq("user", session.getUser()))
                .list();

        for(com.ilts.anywhere.authentication.model.Session s: sessions) {
            s.setDeleted(true);
            sess.save(s);
        }
    }

    @Override
    public void getSession(String token) {
    }

    @Override
    @Transactional
    public boolean validSession(String token) {
        org.hibernate.Session sess = sessionFactory
                .getCurrentSession();

        List<com.ilts.anywhere.authentication.model.Session> sessions = sess
                .createCriteria(com.ilts.anywhere.authentication.model.Session.class)
                .add(Restrictions.eq("deleted", false))
                .add(Restrictions.eq("token", token))
                .list();
System.out.print(">>>>> valid sessionj  --------------"+sessions.get(0));
        return !sessions.isEmpty();
    }

    @Override
    @Transactional
    public String getUserId(String token) {
        org.hibernate.Session sess = sessionFactory
                .getCurrentSession();
        com.ilts.anywhere.authentication.model.Session session = (com.ilts.anywhere.authentication.model.Session) sess
                .createCriteria(com.ilts.anywhere.authentication.model.Session.class)
                .add(Restrictions.eq("token", token))
                .add(Restrictions.eq("deleted", false))
                .list()
                .get(0);
        return session.getUser().getUserId();
    }

    @Override
    public boolean isAdmin(String token) {
        return false;
    }
}
