
package MainBot;

import static MainBot.JRASBotGUI.NEWLINE;
import java.util.List;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.RequestBuffer;

/**
 *
 * @author crysi
 */
public class ClearCommand {

    protected List<IMessage> bulkDeleteNum = null;

    public void clear(IMessage message) {
        String num = message.getContent().replaceAll("!clear ", "");
        int number = Integer.parseInt(num);
        IChannel channel = message.getChannel();
        RequestBuffer.request(() -> {
            message.delete();
            bulkDeleteNum = channel.getMessageHistory(number);
            channel.bulkDelete(bulkDeleteNum);
        });
    }

    public void restore(IMessage event) {

        event.getAuthor().getOrCreatePMChannel().sendMessage("=====" + NEWLINE + "**NOT IN ORDER**" + NEWLINE + "=====");
        for (IMessage eachMessage : bulkDeleteNum) {
            RequestBuffer.request(() -> {
                String eachMessageString = eachMessage.getFormattedContent();
                eachMessageString = "``" + NEWLINE + eachMessageString + NEWLINE + "``";
                event.getAuthor().getOrCreatePMChannel().sendMessage(eachMessageString);
            });
        }
        event.delete();
    }
}
