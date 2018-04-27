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

import java.time.Instant;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

/**
 *
 * @author crysi
 */
public class BasicCommands {
    public void info(IMessage event) {
        EmbedBuilder builder = new EmbedBuilder();
           builder.withColor(23, 214, 204);
           builder.withAuthorIcon("https://i.imgur.com/Gd2Eqtk.png");
           builder.withAuthorName("JRAS Support");
           builder.withTitle("Bot Info");
           builder.withThumbnail("https://i.imgur.com/Gd2Eqtk.png");
           builder.appendField("Why was it made?", "This bot was created to negate some administration issues.", false);
           builder.appendField("How was it coded?", "This bot was coded in Java using the Discord4j API", false);
           builder.appendField("Need to know more?", "PM EasternGamer for more info.", false);
           builder.withFooterIcon("https://i.imgur.com/a3mOAbZ.png");
           builder.withFooterText("EasternGamer");
           builder.withTimestamp(Instant.now());
           RequestBuffer.request(() -> event.getAuthor().getOrCreatePMChannel().sendMessage(builder.build()));
           System.out.println(event.getAuthor().getName() + ": !botinfo");
    }
    public void commands(IMessage event) {
        EmbedBuilder builder = new EmbedBuilder();
           builder.withColor(23, 214, 204);
           builder.withAuthorIcon("https://i.imgur.com/Gd2Eqtk.png");
           builder.withAuthorName("Phoenix Support");
           builder.withTitle("Phoenix Commands");
           builder.withThumbnail("https://i.imgur.com/Gd2Eqtk.png");
           builder.appendField("_=_=_=_=_=_=_=_=_=_=_=_=", "_=_=_=_=_=_=_=_=_=_=_=_=", false);
           builder.appendField("!roleadd @role 'prefix'", "To apply to the users do !updateprefix", false);
           builder.appendField("!clear number", "'Number' can be replaced with any number up to 100", false);
           builder.appendField("!poll 'option'", "Supports up to five options", false);
           builder.appendField("!info", "Bot info", false);
           builder.appendField("!server", "(Not Operational)", false);
           builder.withFooterIcon("https://i.imgur.com/a3mOAbZ.png");
           builder.withFooterText("EasternGamer");
           builder.withTimestamp(Instant.now());
           RequestBuffer.request(() -> event.getAuthor().getOrCreatePMChannel().sendMessage(builder.build()));
           System.out.println(event.getAuthor().getName() + ": !commands");
    }
}
