/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: GameDAO.java
 */
package com.ilts.anywhere.games;

import java.util.List;

public interface GameDAO {
    public void insert(Game game);

    public Game getGameById(String id);

    public List<Game> getAllGames();

    public List<StandardDraw> getDraws();

    public List<Object> getDrawStatuses();
}
