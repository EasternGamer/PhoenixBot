package MainBot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.RequestBuffer;

/**
 *
 * @author EasternGamer
 */
public class UpdateCommand {

    public void updatePrefix(IGuild guild) {
        String idConverted = guild.getStringID();
        List<IRole> roles = guild.getRoles();
        List<IUser> players = guild.getUsers();
        List<String> prefixes = new ArrayList<>();
        List<String> ranksSaved = new ArrayList<>();

        try {
            idConverted = idConverted.replaceAll("0", "Zero");
            idConverted = idConverted.replaceAll("1", "One");
            idConverted = idConverted.replaceAll("2", "TWO");
            idConverted = idConverted.replaceAll("3", "THREE");
            idConverted = idConverted.replaceAll("4", "FOUR");
            idConverted = idConverted.replaceAll("5", "FIVE");
            idConverted = idConverted.replaceAll("6", "SIX");
            idConverted = idConverted.replaceAll("7", "SEVEN");
            idConverted = idConverted.replaceAll("8", "EIGHT");
            idConverted = idConverted.replaceAll("9", "NINE");
            Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            connection.setAutoCommit(false);
            Statement statement1 = connection.createStatement();
            Statement statement2 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT ROLEPREFIX FROM " + idConverted);
            ResultSet resultSet2 = statement2.executeQuery("SELECT ROLE FROM " + idConverted);
            while (resultSet1.next()) {
                prefixes.add(resultSet1.getString(1));
            }

            while (resultSet2.next()) {
                ranksSaved.add(resultSet2.getString(1));
            }
        } catch (SQLException exception) {
            Logger.getLogger(BotPrimary.class.getName()).log(Level.SEVERE, null, exception);
        }
        int number = -1;
        for (String rank : prefixes) {
            number++;
            for (IRole r : roles) {
                String serverRole = r.toString();
                serverRole = serverRole.replace("[", "").replace("]", "");
                String sqlRole = ranksSaved.get(number);
                if (sqlRole != null) {
                    sqlRole = sqlRole.replace("[", "").replace("]", "");
                    if (sqlRole.equals(serverRole)) {
                        for (IUser player : players) {
                            if (!player.getDisplayName(guild).equals(rank + player.getName())) {
                                if (player.hasRole(r)) {
                                    RequestBuffer.request(() -> {
                                        guild.setUserNickname(player, rank + player.getName());
                                    });
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

