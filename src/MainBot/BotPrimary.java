
package MainBot;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;
import java.util.List;
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

/**
 *
 * @author EasternGamer
 */
public final class BotPrimary {

    public static final IDiscordClient BOT = createClient("", true);


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

    @EventSubscriber
    @SuppressWarnings({"empty-statement"})
    public void onMessageEvent(MessageReceivedEvent event) {
        ///////////////////////////////////////////
        //Inilization//
        ///////////////////////////////////////////
        IDiscordClient client = event.getClient();
        client.changeUsername("Phoenix");
        client.changePresence(StatusType.ONLINE, ActivityType.LISTENING, "you...");
        IMessage message = event.getMessage();
        IUser sender = message.getAuthor();
        String command = message.getContent().toLowerCase();
        IGuild guild = message.getGuild();
        List<IRole> roles = guild.getRoles();
        IRole adminRank = roles.get(0);
        String num = "0";
        //////////////////////////////////////////
        //Commands//
        //////////////////////////////////////////
        if (command.equals("!updateprefix") && sender.hasRole(adminRank)) {
            UpdateCommand updateCommand = new UpdateCommand();
            updateCommand.updatePrefix(guild);
        }
        if (command.startsWith("!admin") && guild.getOwner().equals(sender)) {

        }
        if (command.equals("!commands")) {
            BasicCommands basicCommands = new BasicCommands();
            basicCommands.commands(message);
        }
        if (command.equals("!botinfo")) {
            BasicCommands basicCommands = new BasicCommands();
            basicCommands.info(message);
        }
        if (command.equals("!clear " + num) && sender.hasRole(adminRank)) {
            ClearCommand clear = new ClearCommand();
            clear.clear(message);
        }
        if (command.equals("!restore") && sender.hasRole(adminRank)) {

        }
        if (command.startsWith("!poll ") && sender.hasRole(adminRank)) {
            PollCommand pollCommand = new PollCommand();
            pollCommand.pollProcess(message);

        }
        if (command.startsWith("!register")) {
            Setup setup = new Setup();
            setup.ServerReg(event);
        }
        if (command.startsWith("!roleadd")) {
            Setup setup = new Setup();
            setup.ServerRoleAdd(message);
        }
    }

    @EventSubscriber
    public void onJoin(UserJoinEvent event
    ) {
        IChannel channel = event.getGuild().getDefaultChannel();
        IUser user = event.getUser();
        String joinMessage = "**Welcome to **" + event.getGuild().getName() + " " + user.mention() + "!";
        try {
            channel.sendMessage(joinMessage);
        } catch (NullPointerException e) {
            event.getGuild().getChannels().get(0).sendMessage(joinMessage);
        }
    }

    @EventSubscriber
    public void onLeave(UserLeaveEvent event
    ) {
        IChannel channel = event.getGuild().getDefaultChannel();
        IUser user = event.getUser();
        String name = user.getName();
        String leaveMessage = "**Hope to see you again,** " + name + "!";
        try {
            channel.sendMessage(leaveMessage);
        } catch (NullPointerException e) {
            event.getGuild().getChannels().get(0).sendMessage(leaveMessage);
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
