/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: JdbcGameDAO.java
 */
package com.ilts.anywhere.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.math.BigDecimal;
import java.math.BigInteger;

@Repository
public class JdbcGameDAO implements GameDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    JdbcGameDAO() {
    }

    public void insert(Game game) {
        // TODO Auto-generated method stub
    }

    public Game getGameById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Game> getAllGames() {
        String gameSQL = "SELECT * FROM games";

        String drawSQL = "select * from view_draws where o_game_id = ? ";

//               ***************** Existing codes but wrong query.. draws doesnt have vendor code where as o_draws has vendor code *********************
//                The above drawSQL is created using o_draws
//		String drawSQL = "SELECT draws.id, draws.jackpot, draws.date, draws.day, draws.vendorCode FROM draws "
//				+ "JOIN draw_status ON draws.id = draw_status.drawId "
//				+ "JOIN draw_statuses ON draw_status.statusId = draw_statuses.id "
//				+ "WHERE gameId=? AND draw_statuses.name = 'Active' AND draw_status.deleted = FALSE";
        List<Game> games = new ArrayList<Game>();

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(gameSQL);
        for (Map row : rows) {
            StandardGame game = (StandardGame) GameFactory.makeGame(GameType.STANDARD);
            game.setId(((BigInteger) (row.get("game_id"))).intValue());
            game.setName((String) (row.get("game_name")));
            game.setCost((BigDecimal) (row.get("game_cost")));
            game.setNumbers((Integer) (row.get("game_numbers")));
            game.setLogo((String) (row.get("game_logo")));

            List<Map<String, Object>> dRows = this.jdbcTemplate.queryForList(drawSQL, new Object[]{row.get("game_id")});
                List<StandardDraw> draws = new ArrayList<>();
            for (Map dRow : dRows) {
                StandardDraw draw = new StandardDraw();
                draw.setId(((BigInteger) (dRow.get("o_draws_id"))).intValue());
                draw.setJackpot((BigDecimal) (dRow.get("jackpot")));
                draw.setDate((Date) (dRow.get("date")));
                draw.setDay((String) (dRow.get("day")));
                draw.setCode((String) (dRow.get("vendorCode")));
                draws.add(draw);
            }

            game.setDraws(draws);
            games.add(game);
        }
//        for (Map row : rows) {
//            PCSOGame game = (PCSOGame) GameFactory.makeGame(GameType.PCSO);
//            game.setId(((BigInteger) (row.get("id"))).intValue());
//            game.setName((String) (row.get("name")));
//            game.setCost((BigDecimal) (row.get("cost")));
//            game.setNumbers((Integer) (row.get("numbers")));
//            game.setLogo((String) (row.get("logo")));
//
//            List<Map<String, Object>> dRows = this.jdbcTemplate.queryForList(drawSQL, new Object[]{row.get("id")});
//            List<PCSODraw> draws = new ArrayList<PCSODraw>();
//            for (Map dRow : dRows) {
//                PCSODraw draw = new PCSODraw();
//                draw.setId(((BigInteger) (dRow.get("id"))).intValue());
//                draw.setJackpot((BigDecimal) (dRow.get("jackpot")));
//                draw.setDate((Date) (dRow.get("date")));
//                draw.setDay((String) (dRow.get("day")));
//                draw.setCode((String) (dRow.get("vendorCode")));
//                draws.add(draw);
//            }
//            game.setDraws(draws);
//            games.add(game);
//        }

        return games;
    }

    public List<StandardDraw> getDraws() {
        String drawSQL = "select * from view_getDraws";
        List<StandardDraw> draws = new ArrayList<StandardDraw>();

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(drawSQL);
        for (Map dRow : rows) {
            StandardDraw draw = new StandardDraw();
            draw.setId(((BigInteger) (dRow.get("o_draws_id"))).intValue());
            draw.setJackpot((BigDecimal) (dRow.get("jackpot")));
            draw.setDate((Date) (dRow.get("date")));
            draw.setDay((String) (dRow.get("day")));
            draw.setCode((String) (dRow.get("vendorCode")));
            draw.setGameId(((BigInteger) (dRow.get("gameId"))).intValue());
            draw.setGameName((String) (dRow.get("gameName")));
            draw.setStatusId(((Long) (dRow.get("statusId"))).intValue());
            draw.setStatus((String) (dRow.get("status")));
            draws.add(draw);
        }

        return draws;
    }

    public List<Object> getDrawStatuses() {
        String sql = "SELECT * FROM draw_statuses";

        List<Object> statuses = new ArrayList<Object>();

        List<Map<String, Object>> rows = this.jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            final Map hrow = row;
            Object status = new Object() {
                public String id = ((Integer) ((Long) (hrow.get("draw_statuses_id"))).intValue()).toString();
                public String name = (String) (hrow.get("staus_name"));
            };

            statuses.add(status);
        }

        return statuses;
    }

    public void changeStatus(String drawId, String statusId) {
        final String resetSQL = "UPDATE draw_status SET deleted = 1 WHERE draw_id = ?";
        final String insertSQL = "INSERT INTO draw_status(draw_id, status_id) VALUES(?, ?)";

        try {
            this.jdbcTemplate.update(resetSQL, new Object[]{drawId});
            this.jdbcTemplate.update(insertSQL, new Object[]{drawId, statusId});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer createDraw(StandardDraw d) {
        final StandardDraw draw = d;
        final String insertSQL = "INSERT INTO o_draws(o_game_id, jackpot, date, day, vendorCode) VALUES(?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int row;

        row = this.jdbcTemplate.update(new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
                        ps.setInt(1, draw.getGameId());
                        ps.setBigDecimal(2, draw.getJackpot());
                        ps.setDate(3, new java.sql.Date(draw.getDate().getTime()));
                        ps.setString(4, "Monday");
                        ps.setString(5, draw.getCode());

                        return ps;
                    }
        }, keyHolder);

        return ((Long) keyHolder.getKey()).intValue();
    }
}