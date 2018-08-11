/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.api;

import static core.PhoenixEventRunner.file;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MessageHistory;
import sx.blah.discord.util.RequestBuffer;

/**
 *
 * @author crysi
 */
public class Special {

    private static final String[] ALPHABETARRAY = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
        ".", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "%"};

    public String abbreviate(String name) {
        List<String> abbreviation = Arrays.asList(name.split(" "));
        char thing;
        StringBuilder builder = new StringBuilder();
        for (int z = 0; z < abbreviation.size(); z++) {
            thing = abbreviation.get(z).charAt(0);
            builder.append(thing);
        }
        return builder.toString();
    }

    public String getRandomKey() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int j = 0; j < 30; j++) {
            builder.append(ALPHABETARRAY[random.nextInt(46) + 0]);
        }
        return builder.toString();
    }

    public String timeNow() {
        Date dateBase = new Date();
        String[] dataDate = dateBase.toString().split(" ");
        StringBuilder time = new StringBuilder();
        time.append(dataDate[3]);
        return time.toString();
    }

    public void log(String object, boolean bool, IChannel channel) {
        if (bool) {
            System.out.println("[" + timeNow() + "][" + channel.getGuild().getName() + "][" + channel.getName() + "][INTERNAL] " + object);
            try {
                file.append("\n").append("[" + timeNow() + "][" + channel.getGuild().getName() + "][" + channel.getName() + "][INTERNAL] " + object);
            } catch (IOException ex) {
                Logger.getLogger(Special.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            RequestBuffer.request(() -> channel.sendMessage("**[ERROR] **" + object));
            System.out.println("[" + timeNow() + "][" + channel.getGuild().getName() + "][" + channel.getName() + "][EXTERNAL] " + object);
            try {
                file.append("\n").append("[" + timeNow() + "][" + channel.getGuild().getName() + "][" + channel.getName() + "][EXTERNAL] " + object);
            } catch (IOException ex) {
                Logger.getLogger(Special.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void processClear(String[] arguements, IChannel channel) {
        String[] argStep = StringUtils.split(arguements[1], "-");
        try {
            int numOne = Integer.parseInt(argStep[0]);
            int numTwo = Integer.parseInt(argStep[1]);
            MessageHistory msgHist = channel.getMessageHistory(numTwo);
            List<IMessage> msgHistP = msgHist.subList(numOne - 1, numTwo);
            RequestBuffer.request(() -> channel.bulkDelete(msgHistP));
        } catch (IllegalArgumentException e) {
            log("An error occured while decoding your numbers!", false, channel);
        }
    }

    public void updatePrefixSuffix(IUser user, IGuild guild) {
        Database db = new Database();
        IUser uUser = user;
        List<IRole> roles = user.getRolesForGuild(guild);

        List<List<String>> superRole = new ArrayList<>();
        superRole.add(new ArrayList<>());
        superRole.add(new ArrayList<>());
        superRole.add(new ArrayList<>());
        superRole.add(new ArrayList<>());
        superRole.add(new ArrayList<>());

        try (Connection connect = DriverManager.getConnection("jdbc:derby://localhost:1527/PhoenixDatabase", "pro", "profit")) {
            Statement statement = connect.createStatement();
            ResultSet rs = statement.executeQuery("select * from " + db.getTableName(guild.getStringID(), guild.getName()));
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
        builder.append(user.getName());
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
        RequestBuffer.request(() -> guild.setUserNickname(uUser, builder.toString()));
    }

    public boolean equalsAny(String searchString, List<String> listOfItems) {
        boolean done = false;
        for (int z = 0; z < listOfItems.size(); z++) {
            if (listOfItems.get(z).equals(searchString)) {
                done = true;
            }
        }
        return done;
    }

    public void sendMessage(String message, MessageReceivedEvent e) {
        Database data = new Database();

        if (data.isPMEnabled(e.getGuild().getStringID(), e.getGuild().getName())) {
            e.getAuthor().getOrCreatePMChannel().sendMessage(message);
        } else {
            e.getChannel().sendMessage(message);
        }
    }

    public void sendMessage(EmbedBuilder message, MessageReceivedEvent e) {
        Database data = new Database();

        if (data.isPMEnabled(e.getGuild().getStringID(), e.getGuild().getName())) {
            RequestBuffer.request(() -> e.getAuthor().getOrCreatePMChannel().sendMessage(message.build()));
        } else {
            RequestBuffer.request(() -> e.getChannel().sendMessage(message.build()));
        }
    }
}
