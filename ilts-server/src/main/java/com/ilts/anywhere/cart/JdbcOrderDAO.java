/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: JdbcOrderDAO.java
 */
package com.ilts.anywhere.cart;

import com.ilts.anywhere.AppConfig;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.PreparedStatementCreator;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.ResultSetExtractor;

@Repository
public class JdbcOrderDAO implements OrderDAO {

    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    JdbcOrderDAO() {
        AppConfig appconfig = new AppConfig();
        sessionFactory = appconfig.getSessionFactory(appconfig.getDataSource());
    }

    public void insert(Orders order) throws SQLException, Exception {
        
//          final String orderInsertSQL = "INSERT INTO orders (game_id, draw_id, user_id, system_name, cost ) VALUES(?, ?, ?, ?, ?)";
//        final String numbersInsertSQL = "INSERT INTO orders_numbers(order_id, numbers) VALUES(?, ?)";
//
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        int row;
//         final NewOrderWrapper ow = order;
//       jdbcTemplate.qu(new PreparedStatementCreator() {
//        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
// PreparedStatement ps = connection.prepareStatement(orderInsertSQL, Statement.RETURN_GENERATED_KEYS);
//                            ps.setString(1, ow.getGameID());
//                            ps.setString(2,  ow.getDrawID());
//                            ps.setString(3, ow.getUserId());
//                            ps.setString(4, ow.getSystem());
//                            ps.setInt(5, ow.getCost());
//        return ps;}
//        }, ResultSetExtractor);
//       
//jdbcTemplate.queryForRowSet("INSERT INTO TABLE(Column_Names) values (default)RETURNING ID");
//        row = this.jdbcTemplate.update(new PreparedStatementCreator() {
//                    @Override
//                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//                            PreparedStatement ps = connection.prepareStatement(orderInsertSQL, Statement.RETURN_GENERATED_KEYS);
//                            ps.setString(1, ow.getGameID());
//                            ps.setString(2,  ow.getDrawID());
//                            ps.setString(3, ow.getUserId());
//                            ps.setString(4, ow.getSystem());
//                            ps.setInt(5, ow.getCost());
//
//                            return ps;
//                    }
//        },keyHolder);
//        System.out.print("****** KEY HOLDER ***"+keyHolder);
//
//        for (ArrayList<Integer> numbers : ow.getNumbers()) {
//            String numberList = "";
//            for (Integer n : numbers) {
//                    numberList += n.toString() + ",";
//            }
//            jdbcTemplate.update(numbersInsertSQL, new Object[]{
//                    keyHolder.getKey().toString(), numberList
//            });
//        }
//        
        
//	final String gamesInsertSQL = "INSERT INTO games(name, numbers, cost, logo) VALUES(?, ?, ?, ?)";
//    	final String drawsInsertSQL = "INSERT INTO draws(gameId, jackpot, date, day, vendorCode) VALUES(?, ?, ?, ?, ?)";

//        final String orderInsertSQL = "INSERT INTO orders(game_id, draw_id, user_id, system_name, cost ) VALUES(?, ?, ?, ?, ?)";
//        final String numbersInsertSQL = "INSERT INTO orders_numbers(order_id, numbers) VALUES(?, ?)";
        Serializable oID = null;

        int row;
 org.hibernate.Session session = sessionFactory.openSession();
        session.beginTransaction();

        final Orders orderNew = order;

        orderNew.setGameId(order.getGameId());
        System.out.println(" ----  >   "+orderNew.getGameId());
        orderNew.setDrawId(order.getDrawId());
        System.out.println(" ----  >   "+orderNew.getDrawId());
        orderNew.setUserId(order.getUserId());
        System.out.println(" ----  >   "+orderNew.getUserId());
        orderNew.setSystemName(order.getSystemName());
        System.out.println(" ----  >   "+orderNew.getSystemName());
        orderNew.setActive(Boolean.TRUE);
        System.out.println(" ----  >   "+orderNew.getActive());
        orderNew.setDeleted(Boolean.FALSE);
        System.out.println(" ----  >   "+orderNew.getDeleted());
        orderNew.setCost(order.getCost()); 
        System.out.println(" ----  >   "+orderNew.getCost());
        oID = session.save(orderNew);
        System.out.println("********************* OID  ********************************" + oID);

        for (ArrayList<Integer> numbers : order.getNumbers()) {
            OrdersNumbers oN = new OrdersNumbers();
            String numberList = "";
            for (Integer n : numbers) {
                numberList += n.toString() + ",";
            }
            oN.setOrderId((Orders) session.get(Orders.class, oID));
            oN.setNumbers(numberList);
            Serializable nID = session.save(oN);
            System.out.println(nID);
        }
        session.getTransaction().commit();
        session.close();
    }

    public List<Order> getAll() throws SQLException, HibernateException, Exception {
        String ordersSQL = "SELECT orders.order_id AS orderID,  orders.cost AS orderCost,  draw_id, games.o_game_name as gameName, "
                + "games.o_game_cost as gameCost, system_name, users.user_name AS username, "
                + "orders.active AS active, orders.deleted AS deleted "
                + "FROM orders "
                + "JOIN o_games as games ON orders.game_id = games.o_game_id "
                + "LEFT JOIN users ON orders.user_id = users.user_id";

        String drawSQL = "SELECT * FROM o_draws WHERE o_draw_id=?";
        String numbersSQL = "SELECT * FROM orders_numbers WHERE order_id=?";

        List<Order> orders = new ArrayList<Order>();

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(ordersSQL);
        for (Map row : rows) {
            StandardOrder order = (StandardOrder) OrderFactory.makeOrder(OrderType.STANDARD);
            order.setId(((String) (row.get("orderID"))));
            order.setGameName((String) (row.get("gameName")));
            order.setGameCost((BigDecimal) (row.get("gameCost")));
            order.setSystem((String) (row.get("system_name")));
            order.setOrderCost((Integer) (row.get("orderCost")));
            order.setUsername((String) (row.get("username")));
            order.setActive((Boolean) (row.get("active")));
            order.setDeleted((Boolean) (row.get("deleted")));
            String drawID = ((String) (row.get("drawId")));

            DrawWrapper draw = (DrawWrapper) this.jdbcTemplate.queryForObject(drawSQL, new Object[]{drawID},
                    new RowMapper() {
                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                            DrawWrapper draw = new DrawWrapper();
                            draw.setId(rs.getString("id"));
                            draw.setCode(rs.getString("vendorCode"));
                            draw.setDate(rs.getString("date"));
                            draw.setDay(rs.getString("day"));
                            draw.setGameId(rs.getString("gameId"));
                            draw.setJackpot(rs.getBigDecimal("jackpot"));

                            return draw;
                        }
                    });

            order.setDrawCode(draw.getCode());
            order.setDrawDate(draw.getDate());
            order.setDrawDay(draw.getDay());
            order.setDrawJackpot(draw.getJackpot());

            List<Map<String, Object>> dRows = this.jdbcTemplate.queryForList(numbersSQL, new Object[]{row.get("orderID")});
            ArrayList<List<String>> numbers = new ArrayList<List<String>>();
            for (Map dRow : dRows) {
                String str = (String) (dRow.get("numbers"));
                List<String> nums = Arrays.asList(str.split("\\s*,\\s*"));
                numbers.add(nums);
            }

            order.setNumbers(numbers);
            orders.add(order);
        }

        return orders;
    }

    public List<Order> getAll(String userId) throws SQLException, HibernateException, Exception {
//	String gameSQL = "SELECT * FROM games";
//	String drawSQL = "SELECT * FROM draws WHERE gameId=?";
        String ordersSQL = "SELECT orders.order_id AS orderID, o_games.o_game_name as gameName, "
                + "o_games.o_game_cost as gameCost, draw_id, system_name, orders.cost AS orderCost FROM orders"
                + " JOIN o_games  ON orders.game_id = o_games.o_game_id WHERE deleted=FALSE AND active=TRUE AND orders.user_id = ?";
        String drawSQL = "SELECT * FROM o_draws AS draws WHERE o_draw_id=?";
        String numbersSQL = "SELECT * FROM orders_numbers WHERE order_id=?";

        List<Order> orders = new ArrayList<Order>();

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(ordersSQL, new Object[]{userId});
        for (Map row : rows) {
            StandardOrder order = (StandardOrder) OrderFactory.makeOrder(OrderType.STANDARD);
            order.setId(((String) (row.get("orderID"))));
            order.setGameName((String) (row.get("gameName")));
            order.setGameCost((BigDecimal) (row.get("gameCost")));
            order.setSystem((String) (row.get("system_name")));
            order.setOrderCost((Integer) (row.get("orderCost")));
            String drawID = ((String) (row.get("draw_id")));

            DrawWrapper draw = (DrawWrapper) this.jdbcTemplate.queryForObject(drawSQL, new Object[]{drawID},
                    new RowMapper() {
                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                            DrawWrapper draw = new DrawWrapper();
                            draw.setId(rs.getString("o_draw_id"));
                            draw.setCode(rs.getString("o_draw_vendorCode"));
                            draw.setDate(rs.getString("o_draw_date"));
                            draw.setDay(rs.getString("o_draw_day"));
                            draw.setGameId(rs.getString("o_game_id"));
                            draw.setJackpot(rs.getBigDecimal("o_draw_jackpot"));

                            return draw;
                        }
                    });

            order.setDrawCode(draw.getCode());
            order.setDrawDate(draw.getDate());
            order.setDrawDay(draw.getDay());
            order.setDrawJackpot(draw.getJackpot());

            List<Map<String, Object>> dRows = this.jdbcTemplate.queryForList(numbersSQL, new Object[]{row.get("orderID")});
//			List<PCSODraw> draws = new ArrayList<PCSODraw>();
            ArrayList<List<String>> numbers = new ArrayList<List<String>>();
            for (Map dRow : dRows) {
                String str = (String) (dRow.get("numbers"));
                List<String> nums = Arrays.asList(str.split("\\s*,\\s*"));
//				PCSODraw draw = new PCSODraw();
//				draw.setId((Integer)(dRow.get("id")));
//				draw.setJackpot((BigDecimal)(dRow.get("jackpot")));
//				draw.setDate((String)(dRow.get("date")));
//				draw.setDay((String)(dRow.get("day")));
//				draw.setCode((String)(dRow.get("vendorCode")));
//				draws.add(draw);
                numbers.add(nums);
            }
//			game.setDraws(draws);
//			games.add(game);
            order.setNumbers(numbers);
            orders.add(order);
        }
      
            return orders;
 
//	return games;
    }

    public void get() throws SQLException, HibernateException, Exception {
        // TODO Auto-generated method stub
    }

    public void delete(String id) throws SQLException, HibernateException, Exception {

        this.jdbcTemplate.update("UPDATE orders SET deleted=TRUE WHERE order_id=?", new Object[]{id});

    }
}
