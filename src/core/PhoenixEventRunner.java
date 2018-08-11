/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import core.api.Database;
import core.api.Special;
import static core.Commands.COMMANDS;
import static ui.BotCommandUI.bot;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildUpdateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserLeaveEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserRoleUpdateEvent;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

/**
 *
 * @author crysi
 */
public class PhoenixEventRunner {

    public static final long STARTTIME = System.currentTimeMillis() / 1000;
    public static FileWriter file = null;
    public static final String VERSION = "2.01b";

    protected static int serverCount = 0;
    protected static int messagesProcessed = 0;
    protected static String prefix = "!";
    protected static Special special = new Special();
    
    private static final String[] COMMANDLIST = {"suggest", "set admin", "set mod", "set cprefix", "set private", "set welcomeleave", "set welcome", "set leave", "prefix", "suffix", "clear", "restore", "poll", "role assign", "role remove", "list role", "list settings", "stats server", "stats user", "stats", "commands", "latency", "help"};
    private static boolean guildOwner = false;
    private static boolean guildAdmin = false;
    private static boolean guildModerator = false;

    @EventSubscriber
    public void onMessageEvent(MessageReceivedEvent event) throws SQLException {
        long timeStart = System.nanoTime();
        messagesProcessed++;
        if (event.getClient().getGuilds().size() > serverCount) {
            serverCount = event.getClient().getGuilds().size();
            event.getClient().changePresence(StatusType.ONLINE, ActivityType.LISTENING, serverCount + " servers");
        }

        if (StringUtils.containsAny(event.getMessage().getContent(), COMMANDLIST) && !event.getChannel().isPrivate()) {
            if (!event.getAuthor().isBot()) {
                String id = event.getGuild().getStringID();
                String name = event.getGuild().getName();

                Database database = new Database();
                prefix = database.getPrefix(id, name, event.getGuild());
                String messageUP = event.getMessage().getContent();
                if (StringUtils.startsWith(messageUP, prefix)) {
                    boolean check = false;

                    IUser messanger = event.getAuthor();
                    IUser owner = event.getGuild().getOwner();
                    String admin = database.getAdminRole(id, name);
                    String moderator = database.getModRole(id, name);
                    guildOwner = messanger.equals(owner) || messanger.equals(bot.getApplicationOwner());
                    guildAdmin = messanger.getRolesForGuild(event.getGuild()).stream()
                            .parallel()
                            .anyMatch((role) -> role.getStringID().equals(admin));
                    guildModerator = messanger.getRolesForGuild(event.getGuild()).stream()
                            .parallel()
                            .anyMatch((role) -> role.getStringID().equals(moderator));

                    String messagePP = StringUtils.remove(messageUP.toLowerCase(), prefix);
                    for (int z = 0; z < COMMANDLIST.length; z++) {
                        if (StringUtils.startsWith(messagePP, COMMANDLIST[z]) && !check) {
                            messagePP = COMMANDLIST[z];
                            check = true;
                        }
                    }
                    if (COMMANDS.containsKey(messagePP)) {
                        COMMANDS.get(messagePP).run(event, guildOwner, guildAdmin, guildModerator);
                    }
                    guildOwner = false;
                    guildAdmin = false;
                    guildModerator = false;
                    System.out.println("Finish Time: " + (System.nanoTime() - timeStart) / 1000000.0);
                }
            }
        }
    }

    @EventSubscriber
    public void onBotJoin(ReadyEvent e) {
        e.getClient().getGuilds().forEach(guild -> {
            Database database = new Database();
            database.createDatabase(guild, true);
        });
        serverCount = e.getClient().getGuilds().size();
        e.getClient().changePresence(StatusType.ONLINE, ActivityType.LISTENING, serverCount + " servers");
    }

    @EventSubscriber
    public void onUserJoin(UserJoinEvent event) {
        Database database = new Database();
        if (database.isWLEnabled(event.getGuild().getStringID(), event.getGuild().getName())) {
            RequestBuffer.request(() -> {
                String message = database.getWelcomeMessage(event.getGuild().getStringID(), event.getGuild().getName());
                message = StringUtils.replace(message, "#mention#", event.getUser().mention());
                message = StringUtils.replace(message, "#user#", event.getUser().getName());
                try {
                    event.getGuild().getDefaultChannel().sendMessage(message);
                } catch (MissingPermissionsException e) {
                    special.log(e.getErrorMessage(), true, event.getGuild().getDefaultChannel());
                }
            });
        }
    }

    @EventSubscriber
    public void onUserLeave(UserLeaveEvent event) {
        Database database = new Database();
        if (database.isWLEnabled(event.getGuild().getStringID(), event.getGuild().getName())) {
            RequestBuffer.request(() -> {
                String message = database.getLeaveMessage(event.getGuild().getStringID(), event.getGuild().getName());
                message = StringUtils.replace(message, "#mention#", "");
                message = StringUtils.replace(message, "#user#", event.getUser().getName());
                try {
                    event.getGuild().getDefaultChannel().sendMessage(message);
                } catch (MissingPermissionsException e) {
                    special.log(e.getErrorMessage(), true, event.getGuild().getDefaultChannel());
                }
            });
        }
    }

    @EventSubscriber
    public void onGuildNameChange(GuildUpdateEvent event) {
        if (!event.getNewGuild().getName().equals(event.getOldGuild().getName())) {
            new Database().recreateTable(event.getNewGuild(), event.getOldGuild());
        }
    }

    @EventSubscriber
    public void onRoleUpdate(UserRoleUpdateEvent event) {
        Database db = new Database();

        List<IRole> roles = event.getNewRoles();

        List<List<String>> superRole = new ArrayList<>();
        superRole.add(new ArrayList<>());
        superRole.add(new ArrayList<>());
        superRole.add(new ArrayList<>());
        superRole.add(new ArrayList<>());
        superRole.add(new ArrayList<>());

        try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/PhoenixDatabase", "pro", "profit")) {
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("select * from " + db.getTableName(event.getGuild().getStringID(), event.getGuild().getName()));
            while (rs.next()) {
                superRole.get(0).add(rs.getString(1));
                superRole.get(1).add(rs.getString(2));
                superRole.get(2).add(rs.getString(3));
            }

        } catch (SQLException e) {
        }
        for (int i = 0; i < superRole.get(0).size(); i++) {
            for (int z = 0; z < roles.size(); z++) {
                if (superRole.get(0).get(i).equals(roles.get(z).getStringID())) {
                    superRole.get(3).add(superRole.get(1).get(i));
                    superRole.get(4).add(superRole.get(2).get(i));
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        try {
            if (superRole.get(3).size() > 0) {
                for (int j = 0; j < superRole.get(3).size(); j++) {
                    if (!superRole.get(3).get(j).equals("null")) {
                        builder.append(superRole.get(3).get(j));
                    }
                }
            }
        } catch (NullPointerException e) {
        }
        builder.append(event.getUser().getName());
        try {
            if (superRole.get(4).size() > 0) {
                for (int j = 0; j < superRole.get(4).size(); j++) {
                    if (!superRole.get(4).get(j).equals("null")) {
                        builder.append(superRole.get(4).get(j));
                    }
                }
            }
        } catch (NullPointerException e) {
        }
        RequestBuffer.request(() -> {
            try {
                event.getGuild().setUserNickname(event.getUser(), builder.toString());
            } catch (DiscordException | MissingPermissionsException e) {
                special.log(e.getMessage(), true, event.getGuild().getDefaultChannel());
            }
        });

    }

    public static void main(String args[]) throws IOException {
        file = new FileWriter("Log - " + System.currentTimeMillis());
        java.awt.EventQueue.invokeLater(() -> new ui.BotCommandUI().setVisible(true));

    }
}
