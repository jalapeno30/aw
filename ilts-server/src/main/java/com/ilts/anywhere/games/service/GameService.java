/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: GameService.java
 */
package com.ilts.anywhere.games.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ilts.anywhere.games.Game;
import com.ilts.anywhere.games.JdbcGameDAO;
import com.ilts.anywhere.games.StandardDraw;
import com.ilts.anywhere.games.dao.GameDao;

import com.ilts.anywhere.response.DataResponse;
import com.ilts.anywhere.response.Response;
import com.ilts.anywhere.response.ResponseFactory;
import com.ilts.anywhere.response.ResponseType;
import com.ilts.anywhere.response.http.HttpResponse;
import com.ilts.anywhere.response.http.HttpResponseType;
import java.sql.SQLException;
import com.ilts.anywhere.logging.log.CustomLogger;
import com.ilts.anywhere.utils.Logger;

import org.hibernate.HibernateException;

@Service
@ComponentScan
public class GameService {

    @Autowired
    private JdbcGameDAO jdbcGameDAO;

    @Autowired
    private GameDao gameDao;

    public List<Game> retrieveGames() {
        return this.jdbcGameDAO.getAllGames();
    }

    public Response getDraws() {
        // validate token

        // get draws
        final List<StandardDraw> allDraws = this.jdbcGameDAO.getDraws();
        Object data = new Object() {
            public List<StandardDraw> draws = allDraws;
        };
        DataResponse response = (DataResponse) ResponseFactory.makeResponse(ResponseType.SUCCESSDATA);
        response.setData(data);

        return response;
        // return null;
    }

    public List<Object> getDrawStatuses() {
        return this.jdbcGameDAO.getDrawStatuses();
    }

    public Response changeStatus(String drawId, String statusId) {
        this.jdbcGameDAO.changeStatus(drawId, statusId);

        Response response = ResponseFactory.makeResponse(ResponseType.SUCCESSCHANGESTATUS);
        return response;
//        return null;
    }

    public Response createDraw(String jsonRequest) {
        StandardDraw draw = new StandardDraw();

        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            draw = mapper.readValue(jsonRequest, StandardDraw.class);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Integer drawId = this.jdbcGameDAO.createDraw(draw);
        this.changeStatus(drawId.toString(), "3");

        Response response = ResponseFactory.makeResponse(ResponseType.SUCCESSDATA);

        return response;
//        return null;
    }

    @Transactional
    public ResponseEntity<Object> testList() {
        Object data;
        HttpResponse httpResponse = null;
        try {

            data = gameDao.list();

            if (data != null) {
                Logger.logMsg(CustomLogger.LogType.INFO, true, "Succes in capturing games list ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);

            } else {
                Object data1 = new Object() {
                    public String status = "error";
                    public String message = "No Games available";
                };
                Logger.logMsg(CustomLogger.LogType.DEBUG, true, "No such Game provided ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.NOTFOUND, data1);
            }
        } catch (SQLException | HibernateException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "SQLException", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch Games for SQL/Hibernate Exception - %s", ex.getMessage()));
        } catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch Games for  Exception - %s", ex.getMessage()));
        }
        return httpResponse.getResponseEntity();
    }

    @Transactional
    public ResponseEntity<Object> availableGamesByServiceProvider() {
        Object data;
        HttpResponse httpResponse = null;
        try {
            data = gameDao.list();
            if (data != null) {
                Logger.logMsg(CustomLogger.LogType.INFO, true, "Succes in fetching Games ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);

            } else {
                Object data1 = new Object() {
                    public String status = "error";
                    public String message = "No Games available";
                };
                Logger.logMsg(CustomLogger.LogType.DEBUG, true, "No such Game provided ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.NOTFOUND, data1);
            }
        } catch (SQLException | HibernateException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "SQLException", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch Games for SQL/Hibernate Exception - %s", ex.getMessage()));
        } catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch Games for Exception - %s", ex.getMessage()));
        }

        return httpResponse.getResponseEntity();
    }

    @Transactional
    public ResponseEntity<Object> availableDrawsByGame(String gameId) {
        Object data;
        HttpResponse httpResponse = null;
        try {
            data = gameDao.availableDrawsByGame(gameId);
            if (data != null) {
                Logger.logMsg(CustomLogger.LogType.INFO, true, "Succes in fetching available Draws by Game ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);

            } else {
                Object data1 = new Object() {
                    public String status = "error";
                    public String message = "No Draws available";
                };
                Logger.logMsg(CustomLogger.LogType.DEBUG, true, "No Draws are available for the Game provided ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.NOTFOUND, data1);
            }
        } catch (SQLException | HibernateException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "SQLException", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch available Draws by Game for SQL/Hibernate Exception - %s", ex.getMessage()));
        } catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch available Draws by Game for Exception - %s", ex.getMessage()));
        }

        return httpResponse.getResponseEntity();
    }

    @Transactional
    public ResponseEntity<Object> displayByGame(String gameId) {
        Object data;
        HttpResponse httpResponse = null;
        try {
            data = gameDao.displayByGame(gameId);
            if (data != null) {

                Logger.logMsg(CustomLogger.LogType.INFO, true, "Succes in display Numbers and requirements for Game ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);

            } else {
                Object data1 = new Object() {
                    String status = "error";
                    String message = "No requirements available";
                };
                Logger.logMsg(CustomLogger.LogType.DEBUG, true, "No requirements available for the Game provided ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.NOTFOUND, data1);
            }
        } catch (SQLException | HibernateException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "SQLException", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch display Numbers and requirements by Game for SQL/Hibernate Exception - %s", ex.getMessage()));
        } catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch display Numbers and requirements by Game for Exception - %s", ex.getMessage()));
        }

        return httpResponse.getResponseEntity();
    }

    @Transactional
    public ResponseEntity<Object> availableGameSystems() {
        Object data;
        HttpResponse httpResponse = null;
        try {
            data = gameDao.listSystems();
            if (data != null) {

                Logger.logMsg(CustomLogger.LogType.INFO, true, "Succes in display Numbers and requirements for Game ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.ACCEPTED, data);

            } else {
                Object data1 = new Object() {
                    String status = "error";
                    String message = "No requirements available";
                };
                Logger.logMsg(CustomLogger.LogType.DEBUG, true, "No requirements available for the Game provided ");
                httpResponse = ResponseFactory.makeHttpResponse(HttpResponseType.NOTFOUND, data1);
            }
        } catch (SQLException | HibernateException ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "SQLException", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch display Numbers and requirements by Game for SQL/Hibernate Exception - %s", ex.getMessage()));
        } catch (Exception ex) {
            Logger.logException(CustomLogger.LogType.SEVERE, true, "Exception", ex);
            httpResponse.setCustomMessage(String.format("Cannot fetch display Numbers and requirements by Game for Exception - %s", ex.getMessage()));
        }

        return httpResponse.getResponseEntity();
    }
}
