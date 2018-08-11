/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

/**
 *
 * @author crysi
 */
public interface Command {

    void run(MessageReceivedEvent event, boolean owner, boolean admin, boolean mod);
}
