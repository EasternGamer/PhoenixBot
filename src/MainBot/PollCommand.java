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
import java.time.Instant;
import java.util.List;
import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

/**
 *
 * @author crysi
 */
public class PollCommand {

    Boolean poll = false;
    Boolean option1B = false;
    Boolean option2B = false;
    Boolean option3B = false;
    Boolean option4B = false;
    Boolean option5B = false;
    Boolean option6B = false;
    Boolean option7B = false;
    Boolean option8B = false;
    Boolean option9B = false;

    String[] arguements;
    String optionReplace = "";
    String title;
    String titleFinal;
    String option1;
    String option1Final;
    String option2;
    String option2Final;
    String option3;
    String option3Final;
    String option4;
    String option4Final;
    String option5;
    String option5Final;
    String option6;
    String option6Final;
    String option7;
    String option7Final;
    String option8;
    String option8Final;
    String option9;
    String option9Final;

    public void pollProcess(IMessage message) {
        String option = message.getContent();
        optionReplace = option.replace("!poll ", "");
        arguements = optionReplace.split("' '");

        try {
            title = arguements[0];
            titleFinal = title.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            titleFinal = null;
        }
        try {
            option1 = arguements[1];
            option1Final = option1.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            option1 = null;
            option1Final = null;
        }
        try {
            option2 = arguements[2];
            option2Final = option2.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            option2 = null;
            option2Final = null;
        }
        try {
            option3 = arguements[3];
            option3Final = option3.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            option3 = null;
            option3Final = null;
        }
        try {
            option4 = arguements[4];
            option4Final = option4.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            option4 = null;
            option4Final = null;
        }
        try {
            option5 = arguements[5];
            option5Final = option5.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            option5 = null;
            option5Final = null;
        }
        try {
            option6 = arguements[6];
            option6Final = option6.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            option6 = null;
            option6Final = null;
        }
        try {
            option7 = arguements[7];
            option7Final = option7.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            option7 = null;
            option7Final = null;
        }
        try {
            option8 = arguements[8];
            option8Final = option8.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            option8 = null;
            option8Final = null;
        }
        try {
            option9 = arguements[9];
            option9Final = option9.replaceAll("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            option9 = null;
            option9Final = null;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.withColor(23, 214, 204);
        builder.withTitle("**" + titleFinal + "**");

        if (option1 != null) {

            builder.withDescription(":one: " + option1Final);
            option1B = true;
        }
        if (option2 != null) {
            builder.withDescription(":one: " + option1Final + NEWLINE + ":two: " + option2Final);
            option2B = true;
        }
        if (option3 != null) {
            builder.withDescription(":one: " + option1Final + NEWLINE + ":two: " + option2Final + NEWLINE + ":three: " + option3Final);
            option3B = true;
        }
        if (option4 != null) {
            builder.withDescription(":one: " + option1Final + NEWLINE + ":two: " + option2Final + NEWLINE + ":three: " + option3Final + NEWLINE + ":four: " + option4Final);
            option4B = true;
        }
        if (option5 != null) {
            builder.withDescription(":one: " + option1Final + NEWLINE + ":two: " + option2Final + NEWLINE + ":three: " + option3Final + NEWLINE + ":four: " + option4Final + NEWLINE + ":five: " + option5Final);
            option5B = true;
        }
        if (option6 != null) {
            builder.withDescription(":one: " + option1Final + NEWLINE + ":two: " + option2Final + NEWLINE + ":three: " + option3Final + NEWLINE + ":four: " + option4Final + NEWLINE + ":five: " + option5Final + NEWLINE + ":six: " + option6Final);
            option6B = true;
        }
        if (option7 != null) {
            builder.withDescription(":one: " + option1Final + NEWLINE + ":two: " + option2Final + NEWLINE + ":three: " + option3Final + NEWLINE + ":four: " + option4Final + NEWLINE + ":five: " + option5Final + NEWLINE + ":six: " + option6Final + NEWLINE + ":seven: " + option7Final);
            option7B = true;
        }
        if (option8 != null) {
            builder.withDescription(":one: " + option1Final + NEWLINE + ":two: " + option2Final + NEWLINE + ":three: " + option3Final + NEWLINE + ":four: " + option4Final + NEWLINE + ":five: " + option5Final + NEWLINE + ":six: " + option6Final + NEWLINE + ":seven: " + option7Final + NEWLINE + ":eight: " + option8Final);
            option8B = true;
        }
        if (option9 != null) {
            builder.withDescription(":one: " + option1Final + NEWLINE + ":two: " + option2Final + NEWLINE + ":three: " + option3Final + NEWLINE + ":four: " + option4Final + NEWLINE + ":five: " + option5Final + NEWLINE + ":six: " + option6Final + NEWLINE + ":seven: " + option7Final + NEWLINE + ":eight: " + option8Final + NEWLINE + ":nine: " + option9Final);
            option9B = true;
        }

        message.delete();
        builder.withFooterIcon("https://i.imgur.com/a3mOAbZ.png");
        builder.withFooterText("Poll | v1.0");
        builder.withTimestamp(Instant.now());
        message.getChannel().sendMessage(builder.build());

        poll = true;
        if (poll == true) {

            List<IMessage> latestMessage = message.getChannel().getMessageHistory();
            IMessage latest = latestMessage.get(0);
            String one = "\u0031\u20E3";
            String two = "\u0032\u20E3";
            String three = "\u0033\u20E3";
            String four = "\u0034\u20E3";
            String five = "\u0035\u20E3";
            String six = "\u0036\u20E3";
            String seven = "\u0037\u20E3";
            String eight = "\u0038\u20E3";
            String nine = "\u0039\u20E3";
            if (option1B == true) {
                latest.addReaction(ReactionEmoji.of(one));
            }
            if (option2B == true) {
                latest.addReaction(ReactionEmoji.of(two));
            }
            if (option3B == true) {
                latest.addReaction(ReactionEmoji.of(three));
            }
            if (option4B == true) {
                latest.addReaction(ReactionEmoji.of(four));
            }
            if (option5B == true) {
                latest.addReaction(ReactionEmoji.of(five));
            }
            if (option6B == true) {
                latest.addReaction(ReactionEmoji.of(six));
            }
            if (option7B == true) {
                latest.addReaction(ReactionEmoji.of(seven));
            }
            if (option8B == true) {
                latest.addReaction(ReactionEmoji.of(eight));
            }
            if (option9B == true) {
                latest.addReaction(ReactionEmoji.of(nine));
            }
            poll = false;
            option1B = false;
            option2B = false;
            option3B = false;
            option4B = false;
            option5B = false;
            option6B = false;
            option7B = false;
            option8B = false;
            option9B = false;
        }
    }

}
