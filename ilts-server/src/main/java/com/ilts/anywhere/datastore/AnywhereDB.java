/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: AnywhereDB.java
 */
package com.ilts.anywhere.datastore;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ilts.anywhere.AppConfig;
import com.ilts.anywhere.authentication.JdbcSessionDAO;
import com.ilts.anywhere.authentication.model.User;
import com.ilts.anywhere.betting.PurchaseWrapper;
import com.ilts.anywhere.cart.*;
import com.ilts.anywhere.payment.BetPurchase;
import com.ilts.anywhere.payment.PaypalLink;
import com.ilts.anywhere.payment.PaypalOrder;
import com.ilts.anywhere.payment.PaypalPurchase;
import com.ilts.anywhere.payment.model.DummyCardDetails;
import com.ilts.anywhere.payment.model.PaymentPurchaseOrders;
import com.ilts.anywhere.payment.model.PaymentPurchaseStatus;
import com.ilts.anywhere.payment.model.PaymentPurchases;
import com.ilts.anywhere.payment.model.StatusJson;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.hibernate.SessionFactory;

@Repository
@ComponentScan
public class AnywhereDB {

    @Autowired
    private JdbcSessionDAO sessionDAO;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    AnywhereDB() {
        AppConfig appconfig = new AppConfig();
        sessionFactory = appconfig.getSessionFactory(appconfig.getDataSource());
    }

    public boolean validateSession(String userID, String sessionToken) {
        return this.sessionDAO.validSession(sessionToken);
    }

    public void placeBet(ArrayList<Integer> orderId, String date) throws Exception {
        try {
            for (int order : orderId) {
                String sql = "INSERT INTO purchases(orderId, date) VALUES(?, ?)";
                this.jdbcTemplate.update(sql, new Object[]{order, date});

                String removeSQL = "UPDATE orders SET active = FALSE WHERE id = ?";
                this.jdbcTemplate.update(removeSQL, new Object[]{order});
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<BetPurchase> getPurchases() {
        String sql = "SELECT paypal_purchases.id AS purchaseId, "
                + "paypal_purchases.date AS purchaseDate, "
                + "paypal_purchases.cost AS purchaseCost, "
                + "paypal_purchases.userId AS userId, "
                + "users.username AS username, "
                + "paypal_purchase_status.status AS purchaseStatus "
                + "FROM paypal_purchases "
                + "JOIN paypal_purchase_status ON paypal_purchases.id = paypal_purchase_status.purchaseId "
                + "LEFT JOIN users ON paypal_purchases.userId = users.id "
                + "WHERE paypal_purchase_status.deleted = 0";
        List<BetPurchase> purchases = new ArrayList<BetPurchase>();

        final String orderSQL = "SELECT * FROM orders WHERE orders.id = ?";

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            PaypalPurchase purchase = new PaypalPurchase();
            purchase.setId(((BigInteger) (row.get("purchaseId"))).intValue());
            purchase.setDate(((java.util.Date) (row.get("purchaseDate"))).toString());
            purchase.setCost((BigDecimal) (row.get("purchaseCost")));
            purchase.setStatus((String) (row.get("purchaseStatus")));
            purchase.setOrders(this.getOrdersFromPurchase(purchase.getId()));
//	    purchase.setUserId(((Long)(row.get("userId"))).intValue());
            purchase.setUsername((String) (row.get("username")));
            purchases.add(purchase);
        }

        return purchases;
    }

    public List<PurchaseWrapper> getPurchases(String userId) {
        // retrieve purchase ID's

        String idSql = "SELECT paypal_purchases.id "
                + "FROM paypal_purchases "
                + "JOIN paypal_purchase_status "
                + "ON paypal_purchases.id = paypal_purchase_status.purchaseId "
                + "WHERE status = 'funding completed' "
                + "AND deleted = FALSE";

        String sql = "SELECT paypal_purchases.id AS purchaseId, "
                + "paypal_purchases.date AS purchaseDate, "
                + "paypal_purchases.cost AS orderCost, "
                + "paypal_purchases.paypalId "
                + "FROM paypal_purchases "
                + "JOIN paypal_purchase_status ON paypal_purchases.id = paypal_purchase_status.purchaseId "
                + "WHERE paypal_purchase_status.status = 'funding completed' "
                + "AND paypal_purchase_status.deleted = FALSE AND paypal_purchases.userId = ?";
        List<PurchaseWrapper> purchases = new ArrayList<PurchaseWrapper>();

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql, new Object[]{userId});
        for (Map row : rows) {
            PurchaseWrapper purchase = new PurchaseWrapper();
            purchase.setId(((BigInteger) (row.get("purchaseId"))).intValue());

            purchase.setDate(((java.util.Date) (row.get("purchaseDate"))).toString());

            purchase.setDrawDate((String) (row.get("drawDate")));

            purchase.setCost(((BigDecimal) (row.get("orderCost"))).intValueExact());

            purchase.setPaypalId((String) (row.get("paypalId")));

            purchases.add(purchase);
        }

        return purchases;
    }

    public int getCost(String gameId) {
        String sql = "SELECT o_game_cost FROM o_games WHERE o_game_id = ?";
        int cost = this.jdbcTemplate.queryForObject(sql, new Object[]{gameId}, Integer.class);

        return cost;
    }

    public int getSystem(String systemName) {
        String sql = "SELECT system_number FROM systems WHERE system_name = ?";
        System.out.println(systemName);

        return this.jdbcTemplate.queryForObject(sql, new Object[]{systemName}, Integer.class);
    }

    public int getOrderCost(String orderId) {
        String sql = "SELECT cost FROM orders WHERE order_id = ?";
        int cost = this.jdbcTemplate.queryForObject(sql, new Object[]{orderId}, Integer.class);

        return cost;
    }

    public int getTotalOrderCost(ArrayList<String> orderID) {
        int total = 0;
        for (String order : orderID) {
            int cost = this.getOrderCost(order);
            total = total + cost;
        }

        return total;
    }

    private String createTimeHash(String salt) {
        final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

            MessageDigest m = MessageDigest.getInstance("SHA-1");
            m.reset();
            String salted = salt + sdf.format(cal.getTime());
            m.update(salted.getBytes());
            byte[] digest = m.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            String hashtext = bigInt.toString(16);
            // Now we need to zero pad it if you actually want the full 40 chars.
            while (hashtext.length() < 40) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (Exception e) {
        }

        return "";
    }

    public PaypalPurchase createPaypalPurchase(ArrayList<String> orderId, String date, String userId) {

        // create confirm and cancel hashes
        final String confirm = this.createTimeHash("confirm");
        final String cancel = this.createTimeHash("cancel");

        // verify if hashes are used
        // get total cost
        final Integer totalPurchaseCost = this.getTotalOrderCost(orderId);
        final String purchaseDate = date;
        final String user = userId;

        final String purchaseSQL = "INSERT INTO paypal_purchases(cost, date, confirmId, cancelId, userId) VALUES(?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int row;

        row = this.jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(purchaseSQL, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, totalPurchaseCost);
                java.util.Date pDate = new java.util.Date();
                try {
                    pDate = new SimpleDateFormat("E, MMM dd, yyyy, hh:mm aa").parse(purchaseDate);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ps.setDate(2, new java.sql.Date(pDate.getTime()));
                ps.setString(3, confirm);
                ps.setString(4, cancel);
                ps.setString(5, user);

                return ps;
            }
        }, keyHolder);

        String orderSQL = "INSERT INTO paypal_purchase_orders(purchaseId, orderId) VALUES(?, ?)";
        for (String order : orderId) {
            this.jdbcTemplate.update(orderSQL, new Object[]{keyHolder.getKey(), order});

        }

        String statusSQL = "INSERT INTO paypal_purchase_status(purchaseId, status, dateCreated) VALUES(?, ?, ?)";

        java.util.Date dt = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.jdbcTemplate.update(statusSQL, new Object[]{keyHolder.getKey(), "created", sdf.format(dt)});

        final Integer purchaseId = Integer.parseInt(keyHolder.getKey().toString());

        PaypalPurchase paypalPurchase = new PaypalPurchase();
        paypalPurchase.setId(purchaseId);

        paypalPurchase.setCost(new BigDecimal(totalPurchaseCost.toString()));

        paypalPurchase.setDate(date);

        paypalPurchase.setConfirmCode(confirm);

        paypalPurchase.setCancelCode(cancel);

        return paypalPurchase;
    }

    //insert transaction and send back trans id
    //update payment purchase and payment purchase orders and payment purchase status
    public String createPaymentPurchase(ArrayList<String> orders, User userId, String date) {
        org.hibernate.Session session = sessionFactory.openSession();
        session.beginTransaction();
        Serializable paymentPurchasesUID = null;
        Serializable paymentPurchaseOrdersUID = null;
        Serializable paymentPurchaseStatusUID = null;

        final PaymentPurchases paymentPurchases = new PaymentPurchases();
        final Integer totalPurchaseCost = this.getTotalOrderCost(orders);
        System.out.print("COST :   " + totalPurchaseCost);
        paymentPurchases.setCost(totalPurchaseCost);
//        User u = new User();
//        u.setUserId(userId);
        paymentPurchases.setUserId(userId);
        paymentPurchases.setDatePayment(null);
        paymentPurchases.setPaymentId("paymentid" + "");

        paymentPurchasesUID = session.save(paymentPurchases);
        System.out.println("**** paymentPurchasesUID ****" + paymentPurchasesUID);

        for (String order : orders) {

            PaymentPurchaseOrders paymentPurchaseOrders = new PaymentPurchaseOrders();
            paymentPurchaseOrders.setPaymentpurchaseId((PaymentPurchases) session.get(PaymentPurchases.class, paymentPurchasesUID));

            Orders o = new Orders();
            o.setOrderId(order);

            paymentPurchaseOrders.setOrderId(o);

            paymentPurchaseOrders.setDeleted(Boolean.TRUE);
            paymentPurchaseOrdersUID = session.save(paymentPurchaseOrders);
            System.out.println("**** paymentPurchaseOrdersUID ****" + paymentPurchaseOrdersUID);

        }

        PaymentPurchaseStatus paymentPurchaseStatus = new PaymentPurchaseStatus();
        paymentPurchaseStatus.setDeleted(Boolean.TRUE);
        paymentPurchaseStatus.setStatus("created");
        paymentPurchaseStatus.setPaymentpurchaseId((PaymentPurchases) session.get(PaymentPurchases.class, paymentPurchasesUID));

        java.util.Date pDate = new java.util.Date();

        java.util.Date dt = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        paymentPurchaseStatus.setDatecreated(new java.sql.Date(pDate.getTime()));
        paymentPurchaseStatus.setDatemodified(new java.sql.Date(pDate.getTime()));
        paymentPurchaseStatusUID = session.save(paymentPurchaseStatus);
        System.out.println("**** paymentPurchaseStatusUID ****" + paymentPurchaseStatusUID);
        session.getTransaction().commit();
        session.close();
        return (String) paymentPurchasesUID;
    }

    public void getTotalPaypalPurchaseCost(Integer purchaseId) {
    }

    public ArrayList<PaypalOrder> getPaypalPurchases(ArrayList<String> orderId) {
        ArrayList<PaypalOrder> response = new ArrayList<PaypalOrder>();

//	MapSqlParameterSource parameters = new MapSqlParameterSource();
//	parameters.addValue("ids", orderId);
        String sql = "SELECT orders.cost AS orderCost, "
                + "draws.drawDate AS drawDate, "
                + "games.gameName AS gameName, "
                + "games.gameCost AS gameCost "
                + "FROM orders "
                + "JOIN o_draws as draws ON orders.o_draw_id = draws.o_draw_id "
                + "JOIN o_games ON draws.o_game_id = games.o_game_id WHERE orders.id = ?";

        for (String order : orderId) {
            List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql, new Object[]{order});
            for (Map row : rows) {
                PaypalOrder paypalOrder = new PaypalOrder();
                paypalOrder.setDrawDate(((java.sql.Date) (row.get("drawDate"))).toString());
                paypalOrder.setGameCost(((BigDecimal) (row.get("gameCost"))).intValueExact());
                paypalOrder.setGameName((String) (row.get("gameName")));
                paypalOrder.setOrderCost((Integer) (row.get("orderCost")));
                paypalOrder.calculateQuantity();
                response.add(paypalOrder);
            }
        }

        return response;
    }

    public void setPaypalPurchaseLinks(Integer purchaseId, ArrayList<PaypalLink> links) {
        System.out.println(">>>>>>> AFTER  "
                + "anywhereDb.setPaypalPurchaseLinks(Integer purchaseId, ArrayList<PaypalLink> links) purchaseId " + purchaseId);
        String sql = "INSERT INTO paypal_purchase_links(purchaseId, href, rel, method) VALUES(?, ?, ?, ?)";

        for (PaypalLink link : links) {
//	    System.out.println(link.getHref());
            this.jdbcTemplate.update(sql, new Object[]{purchaseId, link.getHref(), link.getRel(), link.getMethod()});
        }
    }

    public String getExecuteURI(String confirmCode) {
        String confirmCodeSql = "SELECT paypal_purchase_links.href "
                + "FROM paypal_purchases "
                + "JOIN paypal_purchase_links "
                + "ON paypal_purchases.id = paypal_purchase_links.purchaseId "
                + "WHERE paypal_purchases.confirmId = ? "
                + "AND paypal_purchase_links.rel = 'execute'";

        return this.jdbcTemplate.queryForObject(confirmCodeSql, new Object[]{confirmCode}, String.class);
    }

    public void confirmPurchase(String confirmId) {
        // find all orders
        String sql = "SELECT paypal_purchases.id, "
                + "paypal_purchase_orders.orderId, "
                + "paypal_purchases.userId AS userId "
                + "FROM paypal_purchase_orders "
                + "JOIN paypal_purchases "
                + "ON paypal_purchase_orders.purchaseId = paypal_purchases.id "
                + "WHERE paypal_purchases.confirmId = ?";

        List<Integer> orders = new ArrayList<Integer>();

        Integer purchaseId = 0;
        Integer userId = 0;
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql, new Object[]{confirmId});
        for (Map row : rows) {
            //orderId
            purchaseId = ((BigInteger) row.get("id")).intValue();
            userId = ((Long) row.get("userId")).intValue();

            Integer orderId = ((Long) row.get("orderId")).intValue();
            String removeSQL = "UPDATE orders SET active = FALSE WHERE id = ?";
            this.jdbcTemplate.update(removeSQL, new Object[]{orderId});
        }

        String updatePurchaseSQL = "UPDATE paypal_purchase_status SET deleted = TRUE WHERE deleted = FALSE AND purchaseId = ?";
        System.out.println("Deactivate " + purchaseId);
        this.jdbcTemplate.update(updatePurchaseSQL, new Object[]{purchaseId});

        java.util.Date dt = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String statusSQL = "INSERT INTO paypal_purchase_status(purchaseId, status, dateCreated) VALUES(?, ?, ?)";
        this.jdbcTemplate.update(statusSQL, new Object[]{purchaseId, "funding completed", sdf.format(dt)});

        System.out.println("Logging " + purchaseId);
        try {
            this.logPurchaseAsBet(userId, confirmId, purchaseId);
        } catch (DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void logPurchaseAsBet(Integer userId, String confirmId, Integer purchaseId) throws DataAccessException, JsonProcessingException {
        final List<Order> orders = this.getOrdersFromPurchase(purchaseId);

        String recoverySql = "INSERT INTO recovery_table(userId, transactionId, status, transactionData) VALUES(?, ?, ?, ?)";
        this.jdbcTemplate.update(recoverySql, new Object[]{
            userId, confirmId, "Bet funded", this.pojoToJson(new Object() {
                public List<Order> bets = orders;
            })
        });
    }

    private String pojoToJson(Object data) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(data);

        return json;
    }

    private List<Order> getOrdersFromPurchase(int purchaseId) {
        String ordersSQL = "SELECT orders.id AS orderID, games.gameName as gameName, "
                + "games.gameCost as gameCost, drawId, system, orders.cost AS orderCost, users.username AS username, "
                + "orders.active AS active, orders.deleted AS deleted "
                + "FROM orders "
                + "JOIN games ON orders.gameId = games.gameId "
                + "LEFT JOIN users ON orders.userId = users.id "
                + "JOIN paypal_purchase_orders ON paypal_purchase_orders.orderId = orders.id "
                + "WHERE paypal_purchase_orders.purchaseId = ?";

        String drawSQL = "SELECT * FROM o_draws WHERE id=?";
        String numbersSQL = "SELECT * FROM orders_numbers WHERE orderId=?";

        List<Order> orders = new ArrayList<Order>();

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(ordersSQL, purchaseId);
        for (Map row : rows) {
            StandardOrder order = (StandardOrder) OrderFactory.makeOrder(OrderType.STANDARD);
            order.setId(((String) (row.get("orderID"))));
            order.setGameName((String) (row.get("gameName")));
            order.setGameCost((BigDecimal) (row.get("gameCost")));
            order.setSystem((String) (row.get("system")));
            order.setOrderCost((Integer) (row.get("orderCost")));
            order.setUsername((String) (row.get("username")));
            order.setActive((Boolean) (row.get("active")));
            order.setDeleted((Boolean) (row.get("deleted")));
            Integer drawID = ((Long) (row.get("drawId"))).intValue();

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

    public List<BetPurchase> getPurchases(Boolean amountChecked,
            String amountCondition, Double amountValuesFirst,
            Double amountValuesSecond, Boolean dateChecked,
            String dateValuesEnd, String dateValuesStart,
            Boolean statusChecked, String[] statusValues, Boolean userChecked,
            Integer[] userValues) throws ParseException {
        List<String> conditions = new ArrayList<String>();
        String condition = "";

        if (statusChecked) {
            condition = "";
//	    condition = "WHERE ";
            Integer statusCounter = 0;
            for (String status : statusValues) {
                condition += "paypal_purchase_status.status = '" + status + "'";
                statusCounter++;
                if (statusCounter != statusValues.length) {
                    condition += " OR ";
                } else {
                    condition += " ";
                }
            }

            conditions.add(condition);
        }

        if (userChecked) {
            condition = "";
//	    condition = "WHERE ";
            Integer userCounter = 0;
            for (Integer user : userValues) {
                condition += "paypal_purchases.userId = '" + user + "'";
                userCounter++;

                if (userCounter != userValues.length) {
                    condition += " OR ";
                } else {
                    condition += " ";
                }
            }

            conditions.add(condition);
        }

        if (amountChecked) {
            condition = "";
            System.out.println(amountCondition);

            if (amountCondition.equals("between")) {
                Double larger = amountValuesSecond > amountValuesFirst ? amountValuesSecond : amountValuesFirst;
                Double smaller = amountValuesSecond > amountValuesFirst ? amountValuesFirst : amountValuesSecond;
                condition += "paypal_purchases.cost <= " + larger;
                condition += " AND paypal_purchases.cost >= " + smaller;
            } else {
                condition += "paypal_purchases.cost ";
                if (amountCondition.equals("lt")) {
                    condition += "<";
                } else if (amountCondition.equals("gt")) {
                    condition += ">";
                } else if (amountCondition.equals("lte")) {
                    condition += "<=";
                } else if (amountCondition.equals("gte")) {
                    condition += ">=";
                } else {
                    condition += "=";
                }
                condition += " " + amountValuesFirst;
            }

            conditions.add(condition);
        }

        if (dateChecked) {
            java.util.Date dateStart = new SimpleDateFormat("MM/dd/yyyy").parse(dateValuesStart);
            java.util.Date dateEnd = new SimpleDateFormat("MM/dd/yyyy").parse(dateValuesEnd);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dStart = df.format(dateStart) + " 00:00:00";
            String dEnd = df.format(dateEnd) + " 23:59:59";

            condition = "";
            condition += "paypal_purchases.date >= '" + dStart;
            condition += "' AND paypal_purchases.date <= '" + dEnd + "'";
            conditions.add(condition);
        }

        String conditionString = "";
        if (conditions.size() > 0) {
            Integer condStringCounter = 0;
            conditionString = "AND ";
            for (String cond : conditions) {
                conditionString += "(";
                conditionString += cond;
                conditionString += ")";
                condStringCounter++;

                if (condStringCounter != conditions.size()) {
                    conditionString += " AND ";
                }
            }
        }

        String sql = "SELECT paypal_purchases.id AS purchaseId, "
                + "paypal_purchases.date AS purchaseDate, "
                + "paypal_purchases.cost AS purchaseCost, "
                + "paypal_purchases.userId AS userId, "
                + "users.username AS username, "
                + "paypal_purchase_status.status AS purchaseStatus "
                + "FROM paypal_purchases "
                + "JOIN paypal_purchase_status ON paypal_purchases.id = paypal_purchase_status.purchaseId "
                + "LEFT JOIN users ON paypal_purchases.userId = users.id "
                + "WHERE paypal_purchase_status.deleted = 0 "
                + conditionString;
        List<BetPurchase> purchases = new ArrayList<BetPurchase>();
        System.out.println(sql);

        final String orderSQL = "SELECT * FROM orders WHERE orders.id = ?";

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            PaypalPurchase purchase = new PaypalPurchase();
            purchase.setId(((BigInteger) (row.get("purchaseId"))).intValue());
            purchase.setDate(((java.util.Date) (row.get("purchaseDate"))).toString());
            purchase.setCost((BigDecimal) (row.get("purchaseCost")));
            purchase.setStatus((String) (row.get("purchaseStatus")));
            purchase.setOrders(this.getOrdersFromPurchase(purchase.getId()));
            purchase.setUsername((String) (row.get("username")));
            purchases.add(purchase);
        }

        return purchases;
    }

    public void initiateFundingRequest(Integer purchaseId) {
        String updatePurchaseSQL = "UPDATE paypal_purchase_status SET deleted = TRUE WHERE deleted = FALSE AND purchaseId = ?";
        System.out.println("Checking funding of " + purchaseId);
        this.jdbcTemplate.update(updatePurchaseSQL, new Object[]{purchaseId});

        java.util.Date dt = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String statusSQL = "INSERT INTO paypal_purchase_status(purchaseId, status, dateCreated) VALUES(?, ?, ?)";
        this.jdbcTemplate.update(statusSQL, new Object[]{purchaseId, "checking funding", sdf.format(dt)});
    }

    public void cancelPurchase(String cancelId) {
        String sql = "SELECT paypal_purchases.id "
                + "FROM paypal_purchases "
                + "WHERE paypal_purchases.cancelId = ? "
                + "LIMIT 1";

        Integer purchaseId = this.jdbcTemplate.queryForInt(sql, new Object[]{cancelId});
        System.out.println(purchaseId);

        String updatePurchaseSQL = "UPDATE paypal_purchase_status SET deleted = TRUE WHERE deleted = FALSE AND purchaseId = ?";
        System.out.println("Cancelling " + purchaseId);
        this.jdbcTemplate.update(updatePurchaseSQL, new Object[]{purchaseId});

        java.util.Date dt = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String statusSQL = "INSERT INTO paypal_purchase_status(purchaseId, status, dateCreated) VALUES(?, ?, ?)";
        this.jdbcTemplate.update(statusSQL, new Object[]{purchaseId, "funding cancelled", sdf.format(dt)});
    }

    public List<Object> getRecoveryData() {
        String sql = "SELECT recovery_table.id, recovery_table.userId, "
                + "users.username, recovery_table.transactionId, recovery_table.transactionData, "
                + "recovery_table.status, recovery_table.created "
                + "FROM recovery_table "
                + "JOIN users ON recovery_table.userId = users.id";

        List<Object> recoveryData = new ArrayList<Object>();

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (final Map row : rows) {
            Object recoveryRow = new Object() {
                public Integer id = ((Long) (row.get("id"))).intValue();
                public Integer userId = ((Long) (row.get("userId"))).intValue();
                public String username = (String) (row.get("username"));
                public String transactionId = (String) (row.get("transactionId"));
                public String transactionData = (String) (row.get("transactionData"));
                public String status = (String) (row.get("status"));
                public java.sql.Timestamp created = (java.sql.Timestamp) (row.get("created"));
            };

            recoveryData.add(recoveryRow);
        }

        return recoveryData;
    }

    public List<Object> getRecoveryData(Boolean statusChecked, String status,
            Boolean userChecked, Integer[] userValues,
            Boolean dateChecked, String dateValuesEnd, String dateValuesStart) {
        List<String> conditions = new ArrayList<String>();
        String condition = "";

        if (statusChecked) {
            condition = "";
            condition += "recovery_table.status LIKE '%" + status + "%'";
            conditions.add(condition);
        }

        if (dateChecked) {
            java.util.Date dateStart = null;
            try {
                dateStart = new SimpleDateFormat("MM/dd/yyyy").parse(dateValuesStart);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            java.util.Date dateEnd = null;
            try {
                dateEnd = new SimpleDateFormat("MM/dd/yyyy").parse(dateValuesEnd);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String dStart = df.format(dateStart) + " 00:00:00";
            String dEnd = df.format(dateEnd) + " 23:59:59";

            condition = "";
            condition += "recovery_table.created >= '" + dStart;
            condition += "' AND recovery_table.created <= '" + dEnd + "'";
            conditions.add(condition);
        }

        if (userChecked) {
            condition = "";
//			condition = "WHERE ";
            Integer userCounter = 0;
            for (Integer user : userValues) {
                condition += "recovery_table.userId = '" + user + "'";
                userCounter++;
                if (userCounter != userValues.length) {
                    condition += " OR ";
                } else {
                    condition += " ";
                }
            }
            conditions.add(condition);
        }

        String conditionString = "";
        if (conditions.size() > 0) {
            Integer condStringCounter = 0;
            conditionString = "AND ";

            for (String cond : conditions) {
                conditionString += "(";
                conditionString += cond;
                conditionString += ")";
                condStringCounter++;

                if (condStringCounter != conditions.size()) {
                    conditionString += " AND ";
                }
            }
        }

        String sql = "SELECT recovery_table.id, recovery_table.userId, "
                + "users.username, recovery_table.transactionId, recovery_table.transactionData, "
                + "recovery_table.status, recovery_table.created "
                + "FROM recovery_table "
                + "JOIN users ON recovery_table.userId = users.id "
                + "WHERE 1=1 "
                + conditionString;

        List<Object> recoveryData = new ArrayList<Object>();
        System.out.println(sql);
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (final Map row : rows) {
            Object recoveryRow = new Object() {
                public Integer id = ((Long) (row.get("id"))).intValue();
                public Integer userId = ((Long) (row.get("userId"))).intValue();
                public String username = (String) (row.get("username"));
                public String transactionId = (String) (row.get("transactionId"));
                public String transactionData = (String) (row.get("transactionData"));
                public String status = (String) (row.get("status"));
                public java.sql.Timestamp created = (java.sql.Timestamp) (row.get("created"));
            };

            recoveryData.add(recoveryRow);
        }

        return recoveryData;
    }

    public List<Object> getTransactionLog() {
        String sql = "SELECT id, userId, data, description, created FROM transaction_log";

        List<Object> transactions = new ArrayList<Object>();
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (final Map row : rows) {
            Object transaction = new Object() {
                public Integer id = ((Long) (row.get("id"))).intValue();
                public Integer userId = ((Long) (row.get("userId"))).intValue();
                public String data = (String) (row.get("data"));
                public String description = (String) (row.get("description"));
                public java.sql.Timestamp created = (java.sql.Timestamp) (row.get("created"));
            };

            transactions.add(transaction);
        }

        return transactions;
    }

    public List<Object> getPurchaseLog() {
        String sql = "SELECT purchaseId, purchaseCost, purchaseDate, purchaseStatus, purchaseStatusActive, purchaseStatusCreated, purchaseStatusModified FROM purchase_log";

        List<Object> purchases = new ArrayList<Object>();
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (final Map row : rows) {
            Object purchase = new Object() {
                public Integer id = ((BigInteger) (row.get("purchaseId"))).intValue();
                public BigDecimal cost = ((BigDecimal) (row.get("purchaseCost")));
                public java.sql.Timestamp date = (java.sql.Timestamp) (row.get("purchaseDate"));
                public String status = (String) (row.get("purchaseStatus"));
                public String statusActive = (String) (row.get("purchaseStatusActive"));
                public java.sql.Timestamp statusCreated = (java.sql.Timestamp) (row.get("purchaseStatusCreated"));
                public java.sql.Timestamp statusModified = (java.sql.Timestamp) (row.get("purchaseStatusModified"));
            };

            purchases.add(purchase);
        }

        return purchases;
    }

    public List<Object> getActivePurchaseLog() {
        String sql = "SELECT purchaseId, purchaseCost, purchaseDate, purchaseStatus, purchaseStatusCreated, purchaseStatusModified FROM active_purchase_log";

        List<Object> purchases = new ArrayList<Object>();
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (final Map row : rows) {
            Object purchase = new Object() {
                public Integer id = ((BigInteger) (row.get("purchaseId"))).intValue();
                public BigDecimal cost = ((BigDecimal) (row.get("purchaseCost")));
                public java.sql.Timestamp date = (java.sql.Timestamp) (row.get("purchaseDate"));
                public String status = (String) (row.get("purchaseStatus"));
                public java.sql.Timestamp statusCreated = (java.sql.Timestamp) (row.get("purchaseStatusCreated"));
                public java.sql.Timestamp statusModified = (java.sql.Timestamp) (row.get("purchaseStatusModified"));
            };

            purchases.add(purchase);
        }

        return purchases;
    }

    public List<Object> getCurrentPurchaseLog() {
        String sql = "SELECT purchaseId, purchaseCost, purchaseDate, purchaseStatus, purchaseStatusCreated, purchaseStatusModified FROM purchase_current_status_log";

        List<Object> purchases = new ArrayList<Object>();
        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (final Map row : rows) {
            Object purchase = new Object() {
                public Integer id = ((BigInteger) (row.get("purchaseId"))).intValue();
                public BigDecimal cost = ((BigDecimal) (row.get("purchaseCost")));
                public java.sql.Timestamp date = (java.sql.Timestamp) (row.get("purchaseDate"));
                public String status = (String) (row.get("purchaseStatus"));
                public java.sql.Timestamp statusCreated = (java.sql.Timestamp) (row.get("purchaseStatusCreated"));
                public java.sql.Timestamp statusModified = (java.sql.Timestamp) (row.get("purchaseStatusModified"));
            };

            purchases.add(purchase);
        }

        return purchases;
    }
    
    public List<DummyCardDetails> getExistingcarddetails(String userId){
      DummyCardDetails card1 = new DummyCardDetails();
      card1.setCardNumber("1234-345-345");
      card1.setCardCvvNumber(123);
      card1.setCardPhoneNumber(989898);
    card1.setCardEmailId("test@test.com");
 
      DummyCardDetails card2 = new DummyCardDetails();   
      card2.setCardNumber("1234-345-456");
      card2.setCardCvvNumber(234);
      card2.setCardPhoneNumber(911898);
    card2.setCardEmailId("test1@test.com");
    
      DummyCardDetails card3 = new DummyCardDetails();
      card3.setCardNumber("1234-345-567");
      card3.setCardCvvNumber(345);
      card3.setCardPhoneNumber(922898);
    card3.setCardEmailId("test2@test.com");
        List<DummyCardDetails> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card3);
        cards.add(card2);
        
        
        return cards;
    }
    
    public void updateStatus(StatusJson statusJson) throws ParseException{
            org.hibernate.Session session = sessionFactory.openSession();
        session.beginTransaction();
     PaymentPurchaseStatus purchaseStatus  = new PaymentPurchaseStatus();
     PaymentPurchases transId = new PaymentPurchases();
     transId.setPaymentpurchaseId(statusJson.getTransId());
     purchaseStatus.setPaymentpurchaseId(transId);
     purchaseStatus.setStatus(statusJson.getStatus());
    
     java.util.Date pDate = new java.util.Date();
      
     purchaseStatus.setDatemodified(new java.sql.Date(pDate.getTime()));
     session.update(purchaseStatus);
     session.getTransaction().commit();
     session.close();
     
    }
}
