package MainBot;

import static MainBot.BotPrimary.COUNTRY;
import static MainBot.BotPrimary.PHOENIX;
import static MainBot.BotPrimary.VERSION;
import java.time.Instant;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

/**
 *
 * @author EasternGamer
 */
public class BasicCommands {

    public void info(IMessage event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.withColor(23, 214, 204);
        builder.withAuthorName("Phoenix Bot Support");
        builder.withThumbnail(PHOENIX);
        builder.appendField("Why was it made?", "This bot was created to negate some administration issues.", false);
        builder.appendField("How was it coded?", "This bot was coded in Java using the Discord4j API", false);
        builder.appendField("Wanna see the code?", "[Github](https://github.com/EasternGamer/PhoenixBot)" , false);
        builder.appendField("Need help?", "[Support Discord](https://discord.gg/RuK3Sw6)",false);
        builder.withFooterIcon(COUNTRY);
        builder.withFooterText("Info | " + VERSION);
        builder.withTimestamp(Instant.now());
        RequestBuffer.request(() -> event.getAuthor().getOrCreatePMChannel().sendMessage(builder.build()));
    }

    public void commands(IMessage event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.withColor(23, 214, 204);
        builder.withAuthorName("Phoenix Bot Support");
        builder.withThumbnail(PHOENIX);
        builder.appendField("_=_=_=_=_=_=_=_=_=_=_=_=", "_=_=_=_=_=_=_=_=_=_=_=_=", false);
        builder.appendField("!admin @role", "To allow user with the role to operate certain commands", false);
        builder.appendField("!roleadd @role 'prefix'", "To apply to the users do !updateprefix", false);
        builder.appendField("!clear number", "'Number' can be replaced with any number up to 100", false);
        builder.appendField("!poll 'option'", "Supports up to five options", false);
        builder.appendField("!info", "Bot info", false);
        builder.withFooterIcon(COUNTRY);
        builder.withFooterText("Commands | " + VERSION);
        builder.withTimestamp(Instant.now());
        RequestBuffer.request(() -> event.getAuthor().getOrCreatePMChannel().sendMessage(builder.build()));
    }

    public void ping(IMessage message) {
        Instant timeStart = Instant.now();
        message = message.getChannel().sendMessage("Ping");
        message = message.edit("Pong");
        Instant timeEnd = Instant.now();
        double time = (timeEnd.getNano() - timeStart.getNano()) / 1000000;
        if (time <= 0) {
            String timeString = time + "";
            timeString = timeString.replace("-", "");
            time = Double.parseDouble(timeString);
        }
        message.edit("Response Time: ``" + time + "ms``");
    }
}
