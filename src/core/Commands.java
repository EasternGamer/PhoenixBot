/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import core.api.Database;
import core.api.FileIO;
import core.api.Special;
import static core.PhoenixEventRunner.STARTTIME;
import static core.PhoenixEventRunner.VERSION;
import static core.PhoenixEventRunner.messagesProcessed;
import static core.PhoenixEventRunner.prefix;
import static ui.BotCommandUI.bot;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageHistory;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;
import sx.blah.discord.util.RequestBuilder;

/**
 *
 * @author crysi
 */
@SuppressWarnings("StaticNonFinalUsedInInitialization")
public class Commands {

    public static final Map<String, Command> COMMANDS = new HashMap();
    public static Boolean pictures = false;
    public static Boolean poll = false;
    public static List<Boolean> options = new ArrayList<>();
    public static List<String> emoteArray = new ArrayList<>();
    public static String[] arguements;
    public static String title;
    public static String pictureString;
    public static IMessage finalMessage;

    private static List<IRole> finalRoles = new ArrayList<>();
    private static int i;
    private static int z;
    private static Special special = new Special();

    static {
        COMMANDS.put("suggest", (event, owner, admin, mod) -> {
            EmbedBuilder builder = new EmbedBuilder();
            builder.withAuthorName(event.getAuthor().getName());
            builder.withAuthorIcon(event.getAuthor().getAvatarURL());
            builder.withTitle(event.getGuild().getName());
            builder.withThumbnail(event.getGuild().getIconURL());
            builder.appendDesc(event.getMessage().getContent().replace(prefix + "suggest", ""));
            builder.withColor(16729620);
            RequestBuffer.request(() -> bot.getApplicationOwner().getOrCreatePMChannel().sendMessage(builder.build()));

            EmbedBuilder embed = new EmbedBuilder();
            embed.withColor(16729620);
            embed.appendDesc("Feedback successfully sent!");
            special.sendMessage(embed, event);
        });
        COMMANDS.put("set admin", (event, owner, admin, mod) -> {
            Database database = new Database();

            if (owner == true) {
                try {
                    database.setAdmin(event);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.appendDesc("Administrator Role is now: " + event.getMessage().getRoleMentions().get(0).mention());
                    builder.withColor(16729620);

                    RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                } catch (SQLException ex) {
                }
            } else {
                special.log("Requires Owner to change!", false, event.getChannel());
            }
        });
        COMMANDS.put("set mod", (event, owner, admin, mod) -> {
            Database database = new Database();

            if (owner == true || admin == true) {
                try {
                    database.setMod(event);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.appendDesc("Moderator Role is now: " + event.getMessage().getRoleMentions().get(0).mention());
                    builder.withColor(16729620);

                    RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                } catch (SQLException ex) {
                }
            } else {
                special.log("Requires Admin Role and higher to change!", false, event.getChannel());
            }
        });
        COMMANDS.put("set cprefix", (event, owner, admin, mod) -> {
            Database database = new Database();

            if (owner == true || admin == true) {
                try {
                    database.setPrefix(event);
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.appendDesc("__**All**__ commands use the following prefix: **" + event.getMessage().getContent().split(" ")[2] + "**");
                    builder.withColor(16729620);

                    RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                } catch (SQLException e) {
                }
            } else {
                special.log("Requires Admin Role and higher to change!", false, event.getChannel());
            }
        });
        COMMANDS.put("set private", (event, owner, admin, mod) -> {
            if (owner == true || admin == true) {
                try {
                    Database data = new Database();
                    data.setPM(event);
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.appendDesc("Private messages are now: **" + data.isPMEnabled(event.getGuild().getStringID(), event.getGuild().getName()) + "**");
                    builder.withColor(16729620);

                    RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                } catch (SQLException ex) {
                    Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        COMMANDS.put("set welcomeleave", (event, owner, admin, mod) -> {
            if (owner == true || admin == true) {
                try {
                    Database data = new Database();
                    data.setJoinLeave(event);
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.appendDesc("Welcome and Leave messages is now: **" + data.isWLEnabled(event.getGuild().getStringID(), event.getGuild().getName()) + "**");
                    builder.withColor(16729620);

                    RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                } catch (SQLException ex) {
                    Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        COMMANDS.put("set leave", (event, owner, admin, mod) -> {
            if (owner == true || admin == true) {
                try {
                    Database data = new Database();
                    data.setLeaveMessage(event);

                    String message = data.getLeaveMessage(event.getGuild().getStringID(), event.getGuild().getName());
                    message = StringUtils.replace(message, "#mention#", "");
                    message = StringUtils.replace(message, "#user#", event.getAuthor().getName());

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.withTitle("Leave Message");
                    builder.appendDesc(message);
                    builder.withColor(16729620);

                    RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                } catch (SQLException ex) {
                    Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        COMMANDS.put("set welcome", (event, owner, admin, mod) -> {
            if (owner == true || admin == true) {
                try {
                    Database data = new Database();
                    data.setJoinMessage(event);

                    String message = data.getWelcomeMessage(event.getGuild().getStringID(), event.getGuild().getName());
                    message = StringUtils.replace(message, "#mention#", event.getAuthor().mention());
                    message = StringUtils.replace(message, "#user#", event.getAuthor().getName());

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.withTitle("Welcome Message");
                    builder.appendDesc(message);
                    builder.withColor(16729620);

                    RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                } catch (SQLException ex) {
                    Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        COMMANDS.put("prefix", (event, owner, admin, mod) -> {
            Database database = new Database();

            if (owner == true || admin == true || mod == true) {
                try {
                    database.rolePrefixAdd(event);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.appendDesc("**__Prefix Addition for__ " + event.getMessage().getRoleMentions().get(0).mention() + "**\n\nExample: **" + event.getMessage().getContent().split("<")[2].replace(">", "") + "**EasternGamer");
                    builder.withColor(16729620);

                    RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                } catch (SQLException ex) {
                    special.log("SQL Error please report to EasternGamer with time and date!", false, event.getChannel());
                }
                event.getGuild().getUsersByRole(event.getMessage().getRoleMentions().get(0)).forEach((user) -> {
                    special.updatePrefixSuffix(user, event.getGuild());
                });
            } else {
                special.log("You lack the required permissions", false, event.getChannel());
            }
        });
        COMMANDS.put("suffix", (event, owner, admin, mod) -> {
            Database database = new Database();

            if (owner == true || admin == true || mod == true) {
                try {
                    database.roleSuffixAdd(event);

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.appendDesc("**__Suffix Addition for__ " + event.getMessage().getRoleMentions().get(0).mention() + "**\n\nExample: " + "EasternGamer**" + event.getMessage().getContent().split("<")[2].replace(">", "") + "**");
                    builder.withColor(16729620);

                    RequestBuffer.request(() -> event.getChannel().sendMessage(builder.build()));
                } catch (SQLException ex) {
                    special.log("SQL Error please report to EasternGamer with time and date!", false, event.getChannel());
                }
                event.getGuild().getUsersByRole(event.getMessage().getRoleMentions().get(0)).forEach((user) -> {
                    special.updatePrefixSuffix(user, event.getGuild());
                });
            } else {
                special.log("You lack the required permissions", false, event.getChannel());
            }
        });
        COMMANDS.put("clear", (event, owner, admin, mod) -> {

            if (owner == true || admin == true || event.getAuthor().getPermissionsForGuild(event.getGuild()).contains(Permissions.MANAGE_MESSAGES)) {
                RequestBuffer.request(() -> event.getMessage().delete());
                List<String> builder = new ArrayList<>();
                IChannel channel = event.getChannel();
                String arguement[] = StringUtils.split(event.getMessage().getContent(), " ");
                if (arguement[1].contains("-")) {
                    special.processClear(arguement, channel);
                } else {
                    try {
                        int numOne = Integer.parseInt(arguement[1]);
                        if (numOne > 100) {
                            special.log("Number too large! Max value is 100.", false, channel);
                        } else {
                            RequestBuffer.request(() -> {
                                try {
                                    channel.bulkDelete(channel.getMessageHistory(numOne + 1));

                                } catch (DiscordException e) {
                                    special.log("Messages are likely older than 2 weeks. Unable to be deleted.", false, event.getChannel());
                                }
                            });
                            MessageHistory mh = channel.getMessageHistory(numOne + 1);
                            for (int j = 0; j < numOne; j++) {
                                try {
                                    builder.add("[" + mh.get(j).getCreationDate().toString() + "]" + mh.get(j).getAuthor().getName() + ": " + mh.get(j).getFormattedContent());
                                } catch (NullPointerException e) {
                                }
                            }
                            FileIO fileIO = new FileIO();
                            fileIO.createFile(new Database().getTableName(event.getGuild().getStringID(), event.getGuild().getName()), builder);
                        }
                    } catch (NumberFormatException e) {
                        special.log("An error occured while decoding your numbers!", false, event.getChannel());
                    } catch (IOException ex) {
                        Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                special.log("You lack the required permissions", false, event.getChannel());
            }
        });
        COMMANDS.put("restore", (event, owner, admin, mod) -> {
            if (owner == true || admin == true) {
                RequestBuffer.request(() -> {
                    try {
                        event.getAuthor().getOrCreatePMChannel().sendFile("File contains all saved messages", new FileIO().getFileContent(new Database().getTableName(event.getGuild().getStringID(), event.getGuild().getName())));
                    } catch (IOException ex) {
                        Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else {
                special.log("You lack the required permissions", false, event.getChannel());
            }
        });
        COMMANDS.put("poll", (event, owner, admin, mod) -> {

            if (owner == true || admin == true || mod == true) {
                String option = event.getMessage().getContent();
                String[] emote = {"", "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
                String pollOptions = "";
                if (option.contains("<")) {
                    arguements = StringUtils.split(option, "<");
                    for (int num = 2; num < arguements.length; num++) {
                        try {
                            arguements[num] = StringUtils.replace(arguements[num], ">", "");
                            pollOptions = pollOptions + ":" + emote[num] + ":" + arguements[num] + "\n";
                            options.add(true);
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                    }
                    try {
                        String[] picture = StringUtils.split(option, "(");
                        pictureString = picture[1].replace(")", "").replace("(", "");
                        pictures = true;
                    } catch (Exception e) {
                        pictures = false;
                    }
                    title = StringUtils.replace(arguements[1], ">", "");
                    EmbedBuilder builder = new EmbedBuilder();
                    if (pictures == true) {
                        builder.withImage(pictureString);
                    }
                    builder.withColor(16729620);
                    builder.withTitle("**" + title + "**");
                    builder.withDescription(pollOptions);
                    event.getMessage().delete();
                    builder.withFooterIcon("https://i.imgur.com/ghrEWh8.png");
                    builder.withFooterText("Poll | " + VERSION);
                    builder.withTimestamp(Instant.now());

                    emoteArray.add("\u0031\u20E3");
                    emoteArray.add("\u0032\u20E3");
                    emoteArray.add("\u0033\u20E3");
                    emoteArray.add("\u0034\u20E3");
                    emoteArray.add("\u0035\u20E3");
                    emoteArray.add("\u0036\u20E3");
                    emoteArray.add("\u0037\u20E3");
                    emoteArray.add("\u0038\u20E3");
                    emoteArray.add("\u0039\u20E3");
                    RequestBuffer.request(() -> {
                        finalMessage = event.getMessage().getChannel().sendMessage(builder.build());
                        for (int pollNumber = 0; pollNumber < options.size(); pollNumber++) {
                            finalMessage.addReaction(ReactionEmoji.of(emoteArray.get(pollNumber)));
                        }

                        poll = false;
                        emoteArray.removeAll(emoteArray);
                    });
                } else {
                    special.log("!poll <Title> <Option 1> <Option 2>\nNote the **<>** as the seperator", false, event.getChannel());
                }
            } else {
                special.log("You lack the required permissions", false, event.getChannel());
            }
        });
        COMMANDS.put("role assign", (event, owner, admin, mod) -> {

            if (owner == true || admin == true) {
                RequestBuilder request = new RequestBuilder(bot);
                request.shouldBufferRequests(true);
                request.setAsync(true);

                List<IRole> roles = event.getMessage().getRoleMentions();
                List<IUser> users = event.getMessage().getMentions();
                request.doAction(() -> {
                    for (int j = i; j < roles.size(); j++) {
                        for (int s = z; s < users.size(); s++) {
                            try {
                                users.get(s).addRole(roles.get(j));
                            } catch (MissingPermissionsException e) {
                                special.log(e.getErrorMessage(), false, event.getChannel());
                            }
                            z++;
                        }
                        z = 0;
                        i++;
                    }
                    i = 0;
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.withColor(16729620);
                    embed.withTitle("Successfully Applied All Roles!");
                    RequestBuffer.request(() -> event.getChannel().sendMessage(embed.build()));
                    return true;
                }).execute();
            } else {
                special.log("You lack the required permissions", false, event.getChannel());
            }
        });
        COMMANDS.put("role remove", (event, owner, admin, mod) -> {

            if (owner == true || admin == true) {
                RequestBuilder request = new RequestBuilder(bot);
                request.shouldBufferRequests(true);
                request.setAsync(true);

                List<IRole> roles = event.getMessage().getRoleMentions();
                List<IUser> users = event.getMessage().getMentions();
                request.doAction(() -> {
                    for (int j = i; j < roles.size(); j++) {
                        for (int s = z; s < users.size(); s++) {
                            try {
                                users.get(s).removeRole(roles.get(j));
                            } catch (MissingPermissionsException e) {
                                special.log(e.getErrorMessage(), false, event.getChannel());
                            }
                            z++;
                        }
                        z = 0;
                        i++;
                    }
                    i = 0;
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.withTitle("Successfully Removed All Roles!");
                    embed.withColor(16729620);
                    RequestBuffer.request(() -> event.getChannel().sendMessage(embed.build()));
                    return true;
                }).execute();
            } else {
                special.log("You lack the required permissions", false, event.getChannel());
            }
        });
        COMMANDS.put("list role", (event, owner, admin, mod) -> {
            Database db = new Database();

            List<IRole> roles = event.getGuild().getRoles();

            List<List<String>> superRole = new ArrayList<>();
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
                roles.parallelStream()
                        .filter(role -> special.equalsAny(role.getStringID(), superRole.get(0)))
                        .forEach(role -> finalRoles.add(role));
                EmbedBuilder build = new EmbedBuilder();
                for (int l = 0; l < finalRoles.size(); l++) {
                    StringBuilder name = new StringBuilder();
                    for (int k = 0; k < finalRoles.size() + 1; k++) {
                        if (superRole.get(0).get(k).equals(finalRoles.get(l).getStringID())) {
                            try {
                                if (!superRole.get(1).get(k).contains("null")) {
                                    name.append(superRole.get(1).get(k));
                                }
                            } catch (NullPointerException e) {
                            }
                            name.append("EasternGamer");
                            try {
                                if (!superRole.get(2).get(k).contains("null")) {
                                    name.append(superRole.get(2).get(k));
                                }
                            } catch (NullPointerException e) {
                            }
                        }
                    }
                    try {
                        build.appendField(name.toString(), finalRoles.get(l).mention(), true);
                    } catch (IllegalArgumentException e) {

                    }
                }
                build.withTitle("**Prefixes and Suffixes**");
                build.withColor(16729620);
                special.sendMessage(build, event);
                finalRoles.clear();
            } catch (SQLException e) {
            }

        });
        COMMANDS.put("list settings", (event, owner, admin, mod) -> {
            try {
                Database database = new Database();
                String id = event.getGuild().getStringID();
                String name = event.getGuild().getName();
                String prefix = database.getPrefix(id, name, event.getGuild());

                String adminId = database.getAdminRole(id, name);
                String modId = database.getModRole(id, name);

                String welcomeMessage = database.getWelcomeMessage(id, name);
                String leaveMessage = database.getLeaveMessage(id, name);
                boolean enabled = database.isWLEnabled(id, name);
                boolean pm = database.isPMEnabled(id, name);
                String adminS;
                try {
                    adminS = event.getGuild().getRoleByID(Long.parseLong(adminId)).mention();
                } catch (NumberFormatException e) {
                    adminS = "Not Set";
                }

                String modS;
                try {
                    modS = event.getGuild().getRoleByID(Long.parseLong(modId)).mention();
                } catch (NumberFormatException e) {
                    modS = "Not Set";
                }

                StringBuilder build = new StringBuilder();

                build.append("__**Administration Settings**__\n");
                build.append("--> Admin Role -> ").append(adminS).append("\n");
                build.append("--> Mod Role -> ").append(modS).append("\n");
                build.append("\n");
                build.append("__**General Settings**__\n");
                build.append("--> Command Prefix -> ``").append(prefix).append("``\n");
                build.append("--> Welcome & Leave -> ``").append(enabled).append("``\n");
                build.append("--> PM's Enabled -> ``").append(pm).append("``\n");
                build.append("\n");
                build.append("----> Welcome Message -> ``").append(welcomeMessage).append("``\n");
                build.append("----> Leave Message -> ``").append(leaveMessage).append("``\n");

                EmbedBuilder builder = new EmbedBuilder();
                builder.withColor(16729620);
                builder.appendDescription(build.toString());

                special.sendMessage(builder, event);
            } catch (SQLException ex) {
                Logger.getLogger(Commands.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        COMMANDS.put("commands", (event, owner, admin, mod) -> {
            EmbedBuilder build = new EmbedBuilder();
            StringBuilder builder = new StringBuilder();

            if (owner == true) {
                builder.append("**====Owner====**\n");
                builder.append(" - ").append(prefix).append("set admin @role\n");
                builder.append("**====Administrator====**\n");
                builder.append(" - ").append(prefix).append("set cprefix $$\n");
                builder.append(" - ").append(prefix).append("set mod @role\n");
                builder.append(" - ").append(prefix).append("set welcome <#mention# #user# is cool>\n");
                builder.append(" - ").append(prefix).append("set leave <#user# is leaving>\n");
                builder.append(" - ").append(prefix).append("set welcomeleave true/false\n");
                builder.append(" - ").append(prefix).append("clear 1..100\n");
                builder.append(" - ").append(prefix).append("restore\n");
                builder.append(" - ").append(prefix).append("role assign @user @user @role @role\n");
                builder.append(" - ").append(prefix).append("role remove @user @user @role @role\n");
                builder.append("**====Moderator====**\n");
                builder.append(" - ").append(prefix).append("set private true/false\n");
                builder.append(" - ").append(prefix).append("poll <Title> <Option 1>...<Option 9>\n");
                builder.append(" - ").append(prefix).append("prefix <content> @role\n");
                builder.append(" - ").append(prefix).append("suffix <content> @role\n");
                builder.append("**====General====**\n");
                builder.append(" - ").append(prefix).append("list settings\n");
                builder.append(" - ").append(prefix).append("list role\n");
                builder.append(" - ").append(prefix).append("stats server\n");
                builder.append(" - ").append(prefix).append("stats user\n");
                builder.append(" - ").append(prefix).append("stats\n");
                builder.append(" - ").append(prefix).append("latency\n");
                builder.append(" - ").append(prefix).append("suggest\n");
            } else if (admin == true) {
                builder.append("**====Administrator====**\n");
                builder.append(" - ").append(prefix).append("set cprefix $$\n");
                builder.append(" - ").append(prefix).append("set mod @role\n");
                builder.append(" - ").append(prefix).append("set welcome <#mention# #user# is cool>\n");
                builder.append(" - ").append(prefix).append("set leave <#user# is leaving>\n");
                builder.append(" - ").append(prefix).append("set welcomeleave true/false\n");
                builder.append(" - ").append(prefix).append("clear 1..100\n");
                builder.append(" - ").append(prefix).append("restore\n");
                builder.append(" - ").append(prefix).append("role assign @user @user @role @role\n");
                builder.append(" - ").append(prefix).append("role remove @user @user @role @role\n");
                builder.append("**====Moderator====**\n");
                builder.append(" - ").append(prefix).append("set private true/false\n");
                builder.append(" - ").append(prefix).append("poll <Title> <Option 1>...<Option 9>\n");
                builder.append(" - ").append(prefix).append("prefix <content> @role\n");
                builder.append(" - ").append(prefix).append("suffix <content> @role\n");
                builder.append("**====General====**\n");
                builder.append(" - ").append(prefix).append("list settings\n");
                builder.append(" - ").append(prefix).append("list role\n");
                builder.append(" - ").append(prefix).append("stats server\n");
                builder.append(" - ").append(prefix).append("stats user\n");
                builder.append(" - ").append(prefix).append("stats\n");
                builder.append(" - ").append(prefix).append("latency\n");
                builder.append(" - ").append(prefix).append("suggest\n");
            } else if (mod == true) {
                builder.append("**====Moderator====**\n");
                builder.append(" - ").append(prefix).append("set private true/false\n");
                builder.append(" - ").append(prefix).append("poll <Title> <Option 1>...<Option 9>\n");
                builder.append(" - ").append(prefix).append("prefix <content> @role\n");
                builder.append(" - ").append(prefix).append("suffix <content> @role\n");
                builder.append("**====General====**\n");
                builder.append(" - ").append(prefix).append("list settings\n");
                builder.append(" - ").append(prefix).append("list role\n");
                builder.append(" - ").append(prefix).append("stats server\n");
                builder.append(" - ").append(prefix).append("stats user\n");
                builder.append(" - ").append(prefix).append("stats\n");
                builder.append(" - ").append(prefix).append("latency\n");
                builder.append(" - ").append(prefix).append("suggest\n");
            } else {
                builder.append("**====General====**\n");
                builder.append(" - ").append(prefix).append("list settings\n");
                builder.append(" - ").append(prefix).append("list role\n");
                builder.append(" - ").append(prefix).append("stats server\n");
                builder.append(" - ").append(prefix).append("stats user\n");
                builder.append(" - ").append(prefix).append("stats\n");
                builder.append(" - ").append(prefix).append("latency\n");
                builder.append(" - ").append(prefix).append("suggest\n");
            }
            build.withColor(16729620);
            build.withTitle("Command List");
            build.appendDesc(builder.toString());
            special.sendMessage(build, event);
        });
        COMMANDS.put("latency", (event, owner, admin, mod) -> {
            RequestBuffer.request(() -> {

                long timeStart = System.nanoTime();

                IChannel channel = event.getChannel();
                IMessage message = channel.sendMessage("Ping");
                message.edit("Pong!");

                double timeEnd = (System.nanoTime() - timeStart) / 2000000.0;
                timeEnd = Math.round(timeEnd * 100) / 100.0;

                message.edit(":alarm_clock:**Response Time:** ``" + timeEnd + "ms``");
            });
        });
        COMMANDS.put("stats server", (event, owner, admin, mod) -> {

            String guildName = event.getGuild().getOwner().getName();
            String guildID = event.getGuild().getStringID();
            String name = event.getGuild().getName();
            String iconURL = event.getGuild().getIconURL();

            int memberCount = event.getGuild().getTotalMemberCount();

            long creationDate = event.getGuild().getCreationDate().getEpochSecond();
            long now = Instant.now().getEpochSecond();

            double timeFinal = Math.round(((now - creationDate) / 60 / 60 / 24.0) / 100.0) * 100.0;

            EmbedBuilder embed = new EmbedBuilder();
            embed.withColor(16729620);
            embed.withTitle(name);
            embed.withThumbnail(iconURL);
            embed.appendDesc(":pager: **Guild ID:** " + guildID + "\n:mens: **Member Count:** " + memberCount + "\n:calendar: **Creation Date:** " + StringUtils.remove(event.getGuild().getCreationDate().truncatedTo(ChronoUnit.DAYS).toString(), "T00:00:00Z") + "\n:floppy_disk: **Guild Owner: **" + guildName);
            special.sendMessage(embed, event);
        });
        COMMANDS.put("stats user", (event, owner, admin, mod) -> {

            EmbedBuilder embed = new EmbedBuilder();
            StringBuilder builder = new StringBuilder();
            if (!event.getMessage().getMentions().isEmpty()) {
                embed.withColor(16729620);
                IUser user = event.getMessage().getMentions().get(0);
                embed.withTitle(user.getDisplayName(event.getGuild()));
                builder.append(":pager: **User ID:** ").append(user.getStringID()).append("\n");
                builder.append(":computer: **Status:** ").append(user.getPresence().getStatus()).append("\n");
                builder.append(":calendar: **Creation Date:** ").append(StringUtils.remove(user.getCreationDate().truncatedTo(ChronoUnit.DAYS).toString(), "T00:00:00Z")).append("\n");

                embed.appendDesc(builder.toString());
                special.sendMessage(embed, event);
            } else {
                embed.withColor(16729620);
                IUser user = event.getAuthor();
                embed.withTitle(user.getDisplayName(event.getGuild()));
                builder.append(":pager: **User ID:** ").append(user.getStringID()).append("\n");
                builder.append(":computer: **Status:** ").append(user.getPresence().getStatus()).append("\n");
                builder.append(":calendar: **Creation Date:** ").append(StringUtils.remove(user.getCreationDate().truncatedTo(ChronoUnit.DAYS).toString(), "T00:00:00Z")).append("\n");
                embed.appendDesc(builder.toString());
                special.sendMessage(embed, event);
            }
        });
        COMMANDS.put("stats", (event, owner, admin, mod) -> {
            Runtime runtime = Runtime.getRuntime();

            double memoryTotal = runtime.totalMemory() / 1024 / 1024;
            double memoryFree = runtime.freeMemory() / 1024 / 1024;
            double memory = memoryTotal - memoryFree;

            long time = STARTTIME;
            long timeEnd = System.currentTimeMillis() / 1000;

            int days = (int) ((timeEnd - time) / 60 / 60 / 24);
            int hours = (int) ((timeEnd - time) / 60 / 60) - (days * 24);
            int minutes = (int) (((timeEnd - time) / 60) - (hours * 60) - (days * 24 * 60));
            int seconds = (int) ((timeEnd - time) - (minutes * 60) - (hours * 60 * 60) - (days * 24 * 60 * 60));

            String collection = days + " day(s), " + hours + " hour(s), " + minutes + "minute(s) and " + seconds + " second(s).";

            EmbedBuilder embed = new EmbedBuilder();
            embed.withColor(16729620);
            embed.appendDesc(":computer: **Version:** " + VERSION + "\n:floppy_disk: **Memory Usage:** " + memory + " MB" + "\n:stopwatch: **Uptime:** " + collection + "\n:file_cabinet: **Messages Proccessed: **" + messagesProcessed);
            special.sendMessage(embed, event);
        });
        COMMANDS.put("help", (event, owner, admin, mod) -> {
            EmbedBuilder embed = new EmbedBuilder();
            embed.withColor(16729620);
            embed.appendDesc("Tutorial Video: https://youtu.be/5mZIA1zqxuo");
            special.sendMessage(embed, event);
        });
    }

}
