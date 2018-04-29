package MainBot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;

/**
 *
 * @author EasternGamer
 */
public class Setup {

    public void ServerReg(MessageReceivedEvent event) {
        IGuild guild = event.getGuild();
        String nameRegister = guild.getStringID();
        String idConverted = nameRegister.replaceAll("0", "Zero");
        idConverted = idConverted.replaceAll("1", "One");
        idConverted = idConverted.replaceAll("2", "TWO");
        idConverted = idConverted.replaceAll("3", "THREE");
        idConverted = idConverted.replaceAll("4", "FOUR");
        idConverted = idConverted.replaceAll("5", "FIVE");
        idConverted = idConverted.replaceAll("6", "SIX");
        idConverted = idConverted.replaceAll("7", "SEVEN");
        idConverted = idConverted.replaceAll("8", "EIGHT");
        idConverted = idConverted.replaceAll("9", "NINE");
        String execute = "CREATE TABLE " + idConverted + " (ROLE VARCHAR(60), ROLEPREFIX VARCHAR(60), ADMINROLE VARCHAR(60), JOINLEAVE VARCHAR(5), PREFIX VARCHAR(10))";
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            statement.execute(execute);
            statement.setMaxRows(1000);
        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        event.getChannel().sendMessage("**TO GET STARTED**\n-Default commands can be found with ``!commands``\n -You will need to add your admin role with ``!adminrole @role``\n -To add role prefixes to roles ``!roleadd @Role 'Prefix'``\n -**TO USE !ADMINROLE YOU NEED TO BE THE CREATOR OF THE SERVER**");
    }

    public void ServerRoleAdd(IMessage message) {
        String[] prefixs;
        String prefix = "";
        try {
        prefixs = message.getContent().split("'");
        prefix = prefixs[1].replace("'", "");
        } catch (ArrayIndexOutOfBoundsException exception) {
            message.getChannel().sendMessage("**Syntax ERROR:** !roleadd @role 'Prefix'\nNote the '");
        }
        IGuild guild = message.getGuild();
        List<IRole> role = message.getRoleMentions();
        String id = guild.getStringID();
        String idConverted = id.replaceAll("0", "Zero");
        idConverted = idConverted.replaceAll("1", "One");
        idConverted = idConverted.replaceAll("2", "TWO");
        idConverted = idConverted.replaceAll("3", "THREE");
        idConverted = idConverted.replaceAll("4", "FOUR");
        idConverted = idConverted.replaceAll("5", "FIVE");
        idConverted = idConverted.replaceAll("6", "SIX");
        idConverted = idConverted.replaceAll("7", "SEVEN");
        idConverted = idConverted.replaceAll("8", "EIGHT");
        idConverted = idConverted.replaceAll("9", "NINE");
        Connection connection;
        String execute = "INSERT INTO " + idConverted + "(ROLE, ROLEPREFIX) VALUES (" + "'" + role.get(0) + "'" + ", " + "'" + prefix + "'" + ")";

        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            if (statement.execute("DELETE FROM " + idConverted + " WHERE ROLE = '" + role.get(0) + "'")) {
            }
            statement.execute(execute);
        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void adminRole(IMessage message) {
        IGuild guild = message.getGuild();
        List<IRole> role = message.getRoleMentions();
        String id = guild.getStringID();
        String idConverted = id.replaceAll("0", "Zero");
        idConverted = idConverted.replaceAll("1", "One");
        idConverted = idConverted.replaceAll("2", "TWO");
        idConverted = idConverted.replaceAll("3", "THREE");
        idConverted = idConverted.replaceAll("4", "FOUR");
        idConverted = idConverted.replaceAll("5", "FIVE");
        idConverted = idConverted.replaceAll("6", "SIX");
        idConverted = idConverted.replaceAll("7", "SEVEN");
        idConverted = idConverted.replaceAll("8", "EIGHT");
        idConverted = idConverted.replaceAll("9", "NINE");
        Connection connection;
        String execute = "INSERT INTO " + idConverted + " (ADMINROLE) VALUES (" + "'" + role.get(0) + "')";
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            if (statement.execute("DELETE FROM " + idConverted + " WHERE ADMINROLE = '" + role.get(0) + "'")) {
            }
            statement.execute(execute);
        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void prefixChange(IMessage message) {
        IGuild guild = message.getGuild();
        String[] prefix = message.toString().split(" ");
        String id = guild.getStringID();
        String idConverted = id.replaceAll("0", "Zero");
        idConverted = idConverted.replaceAll("1", "One");
        idConverted = idConverted.replaceAll("2", "TWO");
        idConverted = idConverted.replaceAll("3", "THREE");
        idConverted = idConverted.replaceAll("4", "FOUR");
        idConverted = idConverted.replaceAll("5", "FIVE");
        idConverted = idConverted.replaceAll("6", "SIX");
        idConverted = idConverted.replaceAll("7", "SEVEN");
        idConverted = idConverted.replaceAll("8", "EIGHT");
        idConverted = idConverted.replaceAll("9", "NINE");
        Connection connection;
        String execute = "INSERT INTO " + idConverted + " (PREFIX) VALUES (" + "'" + prefix[1] + "')";
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            statement.execute(execute);
        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void joinLeaveMessage(IMessage message) {
        IGuild guild = message.getGuild();
        String[] arguementArray = message.toString().split(" ");
        String arguement = arguementArray[1].toLowerCase();
        String id = guild.getStringID();
        String idConverted = id.replaceAll("0", "Zero");
        idConverted = idConverted.replaceAll("1", "One");
        idConverted = idConverted.replaceAll("2", "TWO");
        idConverted = idConverted.replaceAll("3", "THREE");
        idConverted = idConverted.replaceAll("4", "FOUR");
        idConverted = idConverted.replaceAll("5", "FIVE");
        idConverted = idConverted.replaceAll("6", "SIX");
        idConverted = idConverted.replaceAll("7", "SEVEN");
        idConverted = idConverted.replaceAll("8", "EIGHT");
        idConverted = idConverted.replaceAll("9", "NINE");
        Connection connection;
        String execute = "INSERT INTO " + idConverted + " (JOINLEAVE) VALUES (" + "'" + arguement + "')";
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM " + idConverted + " WHERE JOINLEAVE = 'false'");
            statement.execute("DELETE FROM " + idConverted + " WHERE JOINLEAVE = 'true'");
            statement.execute(execute);
        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
