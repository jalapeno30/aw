package com.ilts.anywhere.betting.dao;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ilts.anywhere.betting.model.Bet;
import org.springframework.stereotype.Repository;

@Repository
public class BetDaoImpl implements BetDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public BetDaoImpl() {
		
	}
	
//	public BetDaoImpl(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
	
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bet> list() {
		List<Bet> listBet = (List<Bet>) sessionFactory.getCurrentSession()
				.createCriteria(Bet.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
				.list();
		return listBet;
	}

	@Override
	public Bet get(BigInteger id) {
		return (Bet) sessionFactory.getCurrentSession().get(Bet.class, id);
	}

	@Override
	public void update(Bet bet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Bet bet) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(bet);
	}

	@Override
	public void delete(BigInteger id) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(this.get(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bet> listByUser(BigInteger userId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Bet bet where bet.user.id = :userId";
		Query q = session.createQuery(hql);
		q.setParameter("userId", userId);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bet> listPendingByUser(String userId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from Bet bet where bet.user.id = :userId and bet.deleted = false";
		Query q = session.createQuery(hql);
		q.setParameter("userId", new BigInteger(userId.toString()));
		return q.list();
	}
	
}
