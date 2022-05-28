package com.grubnest.game.core.util;

import com.grubnest.game.core.databasehandler.MySQL;

import java.util.UUID;

public class Util {

    public static void updateUsername(MySQL mysql, UUID id, String username) {
        mysql.updatePlayerUsername(id, username);
    }

}
