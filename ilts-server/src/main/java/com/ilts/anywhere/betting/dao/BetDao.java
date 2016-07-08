package com.ilts.anywhere.betting.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ilts.anywhere.betting.model.Bet;

@Component
public interface BetDao {
	public List<Bet> list();
	
	public Bet get(BigInteger id);
	
	public void update(Bet bet);
	
	public void save(Bet bet);
	
	public void delete(BigInteger id);
	
	public List<Bet> listByUser(BigInteger userId);

	public List<Bet> listPendingByUser(String userId);
}
