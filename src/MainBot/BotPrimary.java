package MainBot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserJoinEvent;
import sx.blah.discord.handle.impl.events.guild.member.UserLeaveEvent;
import sx.blah.discord.handle.obj.ActivityType;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.StatusType;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RequestBuffer;

/**
 *
 * @author EasternGamer
 */
public final class BotPrimary {

    public static final String PHOENIX = "https://i.imgur.com/ghrEWh8.png";
    public static final String COUNTRY = "https://i.imgur.com/a3mOAbZ.png";
    public static final String DISCORD = "https://discord.gg/RuK3Sw6";
    public static final String GITHUB = "https://github.com/EasternGamer/PhoenixBot";
    public static final String VERSION = "v1.2";
    public static final IDiscordClient BOT = createClient("NDM5NDU0ODQyMDcxNTQ3OTA1.DcZsnw.QkEFw-2Xn0pOfxp90bPCDhj0rp0", true);

    public static IDiscordClient createClient(String token, boolean login) {

        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);

        try {
            if (login) {
                return clientBuilder.login();
            } else {
                return clientBuilder.build();
            }
        } catch (DiscordException e) {
            return null;
        }

    }
    public int num = 0;
    public String prefix = "!";

    @EventSubscriber
    @SuppressWarnings({"empty-statement"})
    public void onMessageEvent(MessageReceivedEvent event) {

        ///////////////////////////////////////////
        //Inilization//
        ///////////////////////////////////////////
        IDiscordClient client = event.getClient();
        client.changeUsername("Phoenix Bot");
        while (client.getGuilds().size() > num) {
            num++;
        }
        client.changePresence(StatusType.ONLINE, ActivityType.LISTENING, num + " servers");

        IMessage message = event.getMessage();
        IUser sender = message.getAuthor();
        String command = message.getContent().toLowerCase();
        IGuild guild = message.getGuild();
        List<IRole> roles = guild.getRoles();
        IRole adminRank = null;
        String idConverted = guild.getStringID().replaceAll("0", "Zero");
        idConverted = idConverted.replaceAll("1", "One");
        idConverted = idConverted.replaceAll("2", "TWO");
        idConverted = idConverted.replaceAll("3", "THREE");
        idConverted = idConverted.replaceAll("4", "FOUR");
        idConverted = idConverted.replaceAll("5", "FIVE");
        idConverted = idConverted.replaceAll("6", "SIX");
        idConverted = idConverted.replaceAll("7", "SEVEN");
        idConverted = idConverted.replaceAll("8", "EIGHT");
        idConverted = idConverted.replaceAll("9", "NINE");
        String execute = "select * from " + idConverted;
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(execute);
            while (rs.next()) {
                if (rs.getString(5) != null) {
                    prefix = rs.getString(5);
                }
            }
        } catch (SQLException ex) {
            message.getChannel().sendMessage("Type '!register' to register the server for further command use...");
            System.out.println("Register Sent to " + message.getAuthor().getName());
        }

        //////////////////////////////////////////
        //Commands//
        //////////////////////////////////////////
        ///Register Commands///
        if (command.startsWith(prefix)) {
            Connection connection;
            try {
                connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(execute);
                while (rs.next()) {
                    if (rs.getString(3) != null) {
                        for (IRole role : roles) {
                            String aRole = rs.getString(3);
                            if (aRole.equals(role.toString())) {
                                adminRank = role;
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error");
            }
        }
        if (command.startsWith(prefix + "register")) {
            Setup setup = new Setup();
            setup.ServerReg(event);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }
        if (command.startsWith(prefix + "admin") && guild.getOwner().equals(sender)) {
            Setup setupAdmin = new Setup();
            setupAdmin.adminRole(message);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }
        if (command.startsWith(prefix + "roleadd") && sender.hasRole(adminRank)) {
            Setup setup = new Setup();
            setup.ServerRoleAdd(message);
            System.out.println(event.getChannel().getLongID() + sender.getName() + ": " + command);
        }
        if (command.equals(prefix + "updateprefix") && sender.hasRole(adminRank)) {
            UpdateCommand updateCommand = new UpdateCommand();
            updateCommand.updatePrefix(guild);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }
        if (command.startsWith("!prefix") && sender.hasRole(adminRank)) {
            Setup setup = new Setup();
            setup.prefixChange(message);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }
        if (command.startsWith(prefix + "joinleave") && sender.hasRole(adminRank)) {
            Setup setup = new Setup();
            setup.joinLeaveMessage(message);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }

        ///Standard Commands///
        if (command.equals(prefix + "commands")) {
            BasicCommands basicCommands = new BasicCommands();
            basicCommands.commands(message);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }
        if (command.equals(prefix + "info")) {
            BasicCommands basicCommands = new BasicCommands();
            basicCommands.info(message);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }
        if (command.equals(prefix + "latency")) {
            BasicCommands basicCommands = new BasicCommands();
            basicCommands.ping(message);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }
        if (command.startsWith(prefix + "clear") && sender.hasRole(adminRank)) {
            ClearCommand clear = new ClearCommand();
            clear.clear(message);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }
        if (command.equals(prefix + "restore") && sender.hasRole(adminRank)) {
            ClearCommand restore = new ClearCommand();
            restore.restore(message);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);
        }
        if (command.startsWith(prefix + "poll ") && sender.hasRole(adminRank)) {
            PollCommand pollCommand = new PollCommand();
            pollCommand.pollProcess(message);
            System.out.println(event.getChannel().getLongID() + " " + sender.getName() + ": " + command);

        }
    }

    @EventSubscriber
    public void onJoin(UserJoinEvent event
    ) {
        Boolean join = true;
        IChannel channel = event.getGuild().getDefaultChannel();
        IUser user = event.getUser();
        String idConverted = event.getGuild().getStringID().replaceAll("0", "Zero");
        idConverted = idConverted.replaceAll("1", "One");
        idConverted = idConverted.replaceAll("2", "TWO");
        idConverted = idConverted.replaceAll("3", "THREE");
        idConverted = idConverted.replaceAll("4", "FOUR");
        idConverted = idConverted.replaceAll("5", "FIVE");
        idConverted = idConverted.replaceAll("6", "SIX");
        idConverted = idConverted.replaceAll("7", "SEVEN");
        idConverted = idConverted.replaceAll("8", "EIGHT");
        idConverted = idConverted.replaceAll("9", "NINE");
        String execute = "select * from " + idConverted;
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(execute);
            while (rs.next()) {
                if (rs.getString(4) != null) {
                    if (rs.getString(4).equals("true")) {
                        join = true;
                    } else if (rs.getString(4).equals("false")) {
                        join = false;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        String joinMessage = "**Welcome to " + event.getGuild().getName() + "** " + user.mention() + "!";
        if (join == true) {
            try {
                RequestBuffer.request(() -> {
                    try {
                        channel.sendMessage(joinMessage);
                        System.out.println("**Welcome to **" + event.getGuild().getName() + " " + user.getName() + "!");
                    } catch (MissingPermissionsException exception) {
                        System.out.println("**Welcome to **" + event.getGuild().getName() + " " + user.getName() + "!");
                    }
                });
            } catch (NullPointerException e) {
                event.getGuild().getChannels().get(0).sendMessage(joinMessage);
            }
        }
    }

    @EventSubscriber
    public void onLeave(UserLeaveEvent event
    ) {
        Boolean join = true;
        IChannel channel = event.getGuild().getDefaultChannel();
        IUser user = event.getUser();
        String idConverted = event.getGuild().getStringID().replaceAll("0", "Zero");
        idConverted = idConverted.replaceAll("1", "One");
        idConverted = idConverted.replaceAll("2", "TWO");
        idConverted = idConverted.replaceAll("3", "THREE");
        idConverted = idConverted.replaceAll("4", "FOUR");
        idConverted = idConverted.replaceAll("5", "FIVE");
        idConverted = idConverted.replaceAll("6", "SIX");
        idConverted = idConverted.replaceAll("7", "SEVEN");
        idConverted = idConverted.replaceAll("8", "EIGHT");
        idConverted = idConverted.replaceAll("9", "NINE");
        String execute = "select * from " + idConverted;
        try {
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(execute);
            while (rs.next()) {
                if (rs.getString(4) != null) {
                    if (rs.getString(4).equals("true")) {
                        join = true;
                    } else if (rs.getString(4).equals("false")) {
                        join = false;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        String leaveMessage = "**Hope to see you again,** " + user.getName() + "!";
        if (join == true) {
            try {
                RequestBuffer.request(() -> {
                    try {
                        channel.sendMessage(leaveMessage);
                        System.out.println(leaveMessage);
                    } catch (MissingPermissionsException exception) {
                        System.out.println(leaveMessage);
                    }
                });
            } catch (NullPointerException e) {
                event.getGuild().getChannels().get(0).sendMessage(leaveMessage);
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        EventDispatcher dis = BOT.getDispatcher();
        dis.registerListener(new BotPrimary());

        java.awt.EventQueue.invokeLater(() -> {
            new JRASBotGUI().setVisible(true);
        });
    }
}
