/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: GamesController.java
 */
package com.ilts.anywhere.games.controller;

//import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ilts.anywhere.authentication.AuthenticationService;
import com.ilts.anywhere.games.Game;
import com.ilts.anywhere.games.service.GameService;
import com.ilts.anywhere.response.Response;


@Controller
public class GamesController {
//	private GameService gameService = new GameService();
    @Autowired
    private GameService gameService;

    @Autowired
    private AuthenticationService authService;

    /**
    * Retrieve a list of all available games provided through this service provider. 
    * Assumption is that using a service provider they will be able to receive their winnings through a 
    * physical location or that using a service provider is somewhat beneficial to them.
    **/
    @RequestMapping("/games/availableGamesByServiceProvider")
    public @ResponseBody List<Game> games(HttpServletResponse response) {
        return gameService.retrieveGames();
    }

    /**
    * To retrieve the draw details for a particular game
    **/
    @RequestMapping("/games/retrieveDrawStatus")
    public @ResponseBody Object drawStatus(HttpServletResponse response) {
        return new Object() {};
    }

    /**
    * When a user logs into the My Account/My games page, a list will be shown that will display
    * all available games for the user, a section for results (ended games) and the current games/bets 
    * that are in progress.
    **/
    @RequestMapping("/games/retrieveGameStatus")
    public @ResponseBody Object gameStatus(HttpServletResponse response) {
        return new Object() {};
    }

    @RequestMapping("/games/getDraws")
    public @ResponseBody Response getDraws(@RequestParam(value="gameId", required=false) String gameId, HttpServletResponse response) {
        return this.gameService.getDraws();
    }

    /**
     * return list of draws statuses
     */
    @RequestMapping("/games/drawsStatusesList")
    public @ResponseBody ResponseEntity<List<Object>> getDrawStatuses(HttpServletResponse response) {
        return new ResponseEntity<List<Object>>(this.gameService.getDrawStatuses(), HttpStatus.OK);
    }

    /**
     * 
     * @param userId
     * @param statusId
     * @param response
     * @return ResponseEntity
     */
    @RequestMapping("/games/changeDrawStatus")
    public @ResponseBody ResponseEntity<Response> changeDrawStatus(@RequestParam(value="drawId", required=true) String drawId,
                                                                   @RequestParam(value="statusId", required=true) String statusId,
                                                                   HttpServletResponse response) {
        Response status = this.gameService.changeStatus(drawId, statusId);
        return new ResponseEntity<Response>(status, HttpStatus.OK);
    }

    @RequestMapping("/games/createDraw")
    public @ResponseBody ResponseEntity<Response> createDraw(@RequestParam(value="token", required = true) String token,
                                                             @RequestBody String jsonRequest,
                                                             HttpServletResponse response) {
        if (!authService.validSession(token)) {
            return new ResponseEntity<Response>(HttpStatus.UNAUTHORIZED);
        } else {
            if (!authService.isAdmin(token)) {
                return new ResponseEntity<Response>(HttpStatus.FORBIDDEN);
            } else {
                String userId = authService.getUserId(token);
                Response resp = gameService.createDraw(jsonRequest);

                return new ResponseEntity<Response>(resp, HttpStatus.OK);
            }
        }
    }

    @RequestMapping("/games")
    public @ResponseBody ResponseEntity<Object> availableGamesByServiceProvider(HttpServletResponse response) {
        return gameService.availableGamesByServiceProvider();
    }

    @RequestMapping("/games/{gameId}/draws/available")
    public @ResponseBody ResponseEntity<Object> availableDrawsByGame(@PathVariable("gameId") String gameId,
                                                                     HttpServletResponse response) {
        return gameService.availableDrawsByGame(gameId);
    }
  @RequestMapping("/games/{gameId}/getDisplayNumbers")
    public @ResponseBody ResponseEntity<Object> displayByGame(@PathVariable("gameId") String gameId,
                                                                     HttpServletResponse response) {
        return gameService.displayByGame(gameId);
    }
    @RequestMapping("/games/systems")
    public @ResponseBody ResponseEntity<Object> availableDrawsByGame(HttpServletResponse response) {
        return gameService.availableGameSystems();
    }
}