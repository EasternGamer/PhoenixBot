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
 * @author crysi
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
        String execute = "CREATE TABLE " + idConverted + " (ROLE VARCHAR(60), ROLEPREFIX VARCHAR(60), ADMINROLE VARCHAR(60))";
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            statement.execute(execute);
        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
        event.getChannel().sendMessage("**TO GET STARTED**\n-Default commands can be found with ``!commands``\n -You will need to add your admin role with ``!adminrole @role``\n -To add role prefixes to roles ``!roleadd @Role 'Prefix'``\n -");
    }

    public void ServerRoleAdd(IMessage message) {
        String prefixs[] = message.getContent().split("'");
        String prefix = prefixs[1].replace("'", "");
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
        String execute = "INSERT INTO " + idConverted + "(ROLE, ROLEPREFIX) VALUES (" + "'" + role + "'" + ", " + "'" + prefix + "'" + ")";

        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            Statement statement = connection.createStatement();
            if (statement.execute("DELETE FROM " + idConverted + " WHERE ROLE = '" + role + "'")) {
            }
            statement.execute(execute);
        } catch (SQLException ex) {
            Logger.getLogger(Setup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void adminRole() {

    }
}
