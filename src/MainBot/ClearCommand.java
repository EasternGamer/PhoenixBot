/*
 * Copyright (C) 2018 crysi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
