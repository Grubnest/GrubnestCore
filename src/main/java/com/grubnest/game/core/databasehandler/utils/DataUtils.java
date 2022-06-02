package com.grubnest.game.core.databasehandler.utils;

import com.grubnest.game.core.databasehandler.MySQL;

import java.util.UUID;

public class DataUtils {

    /**
     * Updates a players username stored in the database
     *
     * @param id       the player's uuid
     * @param username the player's username
     */
    public static void updatePlayerUsername(MySQL mysql, UUID id, String username) {
        mysql.updatePlayerUsername(id, username);
    }

}
