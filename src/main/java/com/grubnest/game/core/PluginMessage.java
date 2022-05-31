package com.grubnest.game.core;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.grubnest.game.core.paper.GrubnestCorePlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.*;

/**
 * @author tamilpp25
 * @created 15/05/2022
 */
public class PluginMessage implements PluginMessageListener {

    /**
     * Plugin message receiver
     * triggers when there is a response from a subchannel
     * //todo fix forward method later
     *
     * @param channel Subchannel as given in spigot docs
     * @param player  Player that send plugin message to server
     * @param message Message
     */
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF(); // Name of server, as given in the arguments
            int playercount = in.readInt(); // list of players
        } else if (subchannel.equals("PlayerList")) {
            String server = in.readUTF(); // The name of the server you got the player list of, as given in args.
            String[] playerList = in.readUTF().split(", "); // gets all players in the server or ALL for whole proxy
        } else if (subchannel.equals("GetServers")) {
            String[] serverList = in.readUTF().split(", "); // Gets the list of servers defined
        } else if (subchannel.equals("GetServer")) {
            String servername = in.readUTF(); // get the current server name
        } else if (subchannel.equals("UUID")) {
            String uuid = in.readUTF(); // get the uuid of sent player
        } else if (subchannel.equals("UUIDOther")) {
            String playerName = in.readUTF(); // player name
            String uuid = in.readUTF(); // player uuid
        } else if (subchannel.equals("ServerIP")) {
            String serverName = in.readUTF();
            String ip = in.readUTF();
            int port = in.readUnsignedShort();
        } else if (subchannel.equals("Custom sub channel")) { // todo change later the name for custom sub channel
            short len = in.readShort();
            byte[] msgbytes = new byte[len];
            in.readFully(msgbytes);

            DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));
            try {
                String somedata = msgin.readUTF(); // Read the data in the same way you wrote it
                short somenumber = msgin.readShort();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Connects a player to said subserver.
     *
     * @param player Player to send plugin message to
     * @param server name of server to connect to, as defined in BungeeCord config.yml
     * @receiver player to be teleported
     */
    public static void connect(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
        player.sendMessage(ChatColor.DARK_GRAY + "sending you to " + server + "...");
    }

    /**
     * Connect a named player to said subserver
     *
     * @param player       Player to send plugin message to
     * @param named_player name of the player to teleport
     * @param server       name of server to connect to, as defined in BungeeCord config.yml
     * @receiver any player
     */
    public static void connectOther(Player player, String named_player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ConnectOther");
        output.writeUTF(named_player);
        output.writeUTF(server);
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Get the amount of players on a certain server, or on ALL the servers.
     *
     * @param player packet carrier
     * @param server the name of the server to get the player count of, or ALL to get the global player count
     * @receiver any player
     */
    public static void getPlayerCount(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("PlayerCount");
        output.writeUTF(server);
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Get a list of players connected on a certain server, or on ALL of the servers.
     *
     * @param player packet carrier
     * @param server the name of the server to get the list of connected players, or ALL for global online player list
     * @receiver any player
     */
    public static void getPlayerList(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("PlayerList");
        output.writeUTF(server);
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Get a list of server name strings, as defined in BungeeCord's config.yml
     *
     * @param player packet carrier
     * @receiver any player
     */
    public static void getServers(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("GetServers");
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Send a message (as in, a chat message) to the specified player.
     *
     * @param player      packet carrier
     * @param proxyPlayer the name of the player to send the chat message, or ALL to send to all players
     * @param message     the message to send to the player , supports color codes with "&" as translator
     * @receiver any player
     */
    public static void sendMessage(Player player, String proxyPlayer, String message) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Message");
        output.writeUTF(proxyPlayer);
        output.writeUTF(ChatColor.translateAlternateColorCodes('&', message));
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Send a raw message (as in, a chat message) to the specified player. The advantage of this method over Message
     * is that you can include click events and hover events.
     *
     * @param player      packet carrier
     * @param proxyPlayer the name of the player to send the chat message, or ALL to send to all players
     * @param message     the message to send to the player
     * @receiver any player
     */
    public static void sendRawMessage(Player player, String proxyPlayer, String message) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("MessageRaw");
        output.writeUTF(proxyPlayer);
        output.writeUTF(message);
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Get this server's name, as defined in BungeeCord's config.yml
     *
     * @param player packet carrier
     * @receiver any player
     */
    public static void getServer(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("GetServer");
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Request the UUID of this player
     *
     * @param player player
     * @receiver The player whose UUID you requested
     */
    public static void getUUID(Player player) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("UUID");
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Request the UUID of any player connected to the BungeeCord proxy
     *
     * @param player      packet carrier and receiver
     * @param oplayername the name of the player whose UUID you would like
     * @receiver the sender
     */
    public static void getUUIDOther(Player player, String oplayername) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("UUIDOther");
        output.writeUTF(oplayername);
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Request the IP of any server on this proxy
     *
     * @param player packet carrier
     * @param server the name of the server
     * @receiver any player
     */
    public static void getServerIP(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ServerIP");
        output.writeUTF(server);
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Kick any player on this proxy
     *
     * @param player       packet carrier
     * @param playerToKick player to be kicked
     * @param reason       reason for kick
     * @receiver any player
     */
    public static void kickPlayer(Player player, String playerToKick, String reason) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("KickPlayer");
        output.writeUTF(playerToKick);
        output.writeUTF(reason);
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", output.toByteArray());
    }

    /**
     * Send a custom plugin message to said server. This is one of the most useful channels ever.
     * Remember, the sending and receiving server(s) need to have a player online.
     *
     * @param player     packet carrier
     * @param server     server to send to, ALL to send to every server (except the one sending the plugin message),
     *                   or ONLINE to send to every server that's online (except the one sending the plugin message)
     * @param subchannel Subchannel for plugin usage.
     * @param data       message to send //todo change stuff so can send anything
     * @receiver any player
     */
    public static void forward(Player player, String server, String subchannel, String data) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward"); // So BungeeCord knows to forward it
        out.writeUTF(server); // server to send to
        out.writeUTF(subchannel); // The channel name to check if this your data

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            //THESE ARE DATA WE WANT TO SEND (example) // remove later
            msgout.writeUTF(data); // You can do anything you want with msgout
            msgout.writeShort(123); // checking needed
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", out.toByteArray());
    }

    /**
     * Send a custom plugin message to specific player.
     *
     * @param player            packet carrier
     * @param playerToForwardTo Playername to send to.
     * @param subchannel        Subchannel for plugin usage.
     * @param data              message to send. //todo change stuff so can send anything
     */
    public static void forwardToPlayer(Player player, String playerToForwardTo, String subchannel, String data) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ForwardToPlayer"); // So BungeeCord knows to forward it
        out.writeUTF(playerToForwardTo); // server to send to
        out.writeUTF(subchannel); // The channel name to check if this your data

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);
        try {
            //THESE ARE DATA WE WANT TO SEND (example) //remove later
            msgout.writeUTF(data); // You can do anything you want with msgout
            msgout.writeShort(123); // checking needed
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());
        player.sendPluginMessage(GrubnestCorePlugin.getInstance(), "BungeeCord", out.toByteArray());
    }

}