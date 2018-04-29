package MainBot;

import static MainBot.BotPrimary.BOT;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

/**
 *
 * @author EasternGamer
 */
public class JRASBotGUI extends javax.swing.JFrame {

    Connection connect = null;
    ResultSet rs = null;
    Statement st;
    static final String NEWLINE = "\n";

    /**
     * Creates new form JRASBotGUI
     */
    public JRASBotGUI() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jButtonUpdateAll = new javax.swing.JButton();
        jButtonSendMessage = new javax.swing.JButton();
        jButtonLogin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jUsername = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();
        jTextFieldChnID = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jTextFieldMessage = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));
        jTextFieldGuildNumber = new javax.swing.JTextField();
        jButtonGuildName = new javax.swing.JButton();
        jButtonUsers = new javax.swing.JButton();
        jButtonClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JRAS Bot");
        setMinimumSize(new java.awt.Dimension(544, 312));
        setName("jrasbotgui"); // NOI18N

        jButtonUpdateAll.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 18)); // NOI18N
        jButtonUpdateAll.setText("!Updateall");
        jButtonUpdateAll.setToolTipText("Updates alll User's based on roles");
        jButtonUpdateAll.setEnabled(false);
        jButtonUpdateAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateAllActionPerformed(evt);
            }
        });

        jButtonSendMessage.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        jButtonSendMessage.setText("Send Message");
        jButtonSendMessage.setToolTipText("Send's a Message to the JRAS Discord");
        jButtonSendMessage.setEnabled(false);
        jButtonSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendMessageActionPerformed(evt);
            }
        });

        jButtonLogin.setText("LOGIN");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Orator Std", 1, 24)); // NOI18N
        jLabel1.setText("ONLINE");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setEnabled(false);

        jLabel2.setFont(new java.awt.Font("Prestige Elite Std", 0, 12)); // NOI18N
        jLabel2.setText("PASSWORD");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Channel ID");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Prestige Elite Std", 0, 12)); // NOI18N
        jLabel4.setText("USERNAME");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextArea.setEditable(false);
        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        jButtonGuildName.setText("Guild Name");
        jButtonGuildName.setEnabled(false);
        jButtonGuildName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuildNameActionPerformed(evt);
            }
        });

        jButtonUsers.setText("Users");
        jButtonUsers.setEnabled(false);
        jButtonUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUsersActionPerformed(evt);
            }
        });

        jButtonClear.setText("Clear");
        jButtonClear.setEnabled(false);
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonUpdateAll, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonSendMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldChnID, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldGuildNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonGuildName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonClear))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(39, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonLogin)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPasswordField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(488, 488, 488)
                        .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jUsername, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(22, 22, 22)
                .addComponent(filler3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(65, 65, 65)
                                .addComponent(filler2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonLogin))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonUpdateAll, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonSendMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldChnID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldGuildNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonGuildName)
                            .addComponent(jButtonUsers)
                            .addComponent(jButtonClear)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void jButtonUpdateAllActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    private void jButtonSendMessageActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        // TODO add your handling code here:
        String channelID = jTextFieldChnID.getText();
        String message = jTextFieldMessage.getText();
        jTextArea.append("DEVELOPER: " + message + NEWLINE);
        String fullMessage = "**DEV**: " + message;
        List<IGuild> guilds = BOT.getGuilds();
        IChannel channel = guilds.get(Integer.parseInt(jTextFieldGuildNumber.getText())).getChannelByID(Long.parseLong(channelID));
        channel.sendMessage(fullMessage);
    }                                                  

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {                                             
        String username = jUsername.getText();
        String passwordEntered = Arrays.toString(jPasswordField.getPassword());
        passwordEntered = passwordEntered.replaceAll(",", "");
        passwordEntered = passwordEntered.replace("[", "");
        passwordEntered = passwordEntered.replace("]", "");
        passwordEntered = passwordEntered.replaceAll(" ", "");
        boolean logged = false;
        try {
            connect = DriverManager.getConnection("jdbc:derby://localhost:1527/PrimaryDatabase", "Database", "data");
            st = (Statement) connect.createStatement();
            rs = st.executeQuery("select * from USERTABLE");
            while (rs.next()) {

                System.out.println("While loop start");
                if (rs.getString(1).equals(username) && rs.getString(2).equals(passwordEntered)) {
                    if (username.equals("Developer")) {
                        jButtonLogin.setText("LOGOUT");
                        jButtonSendMessage.setEnabled(true);
                        jButtonUpdateAll.setEnabled(true);
                        jButtonClear.setEnabled(true);
                        jButtonGuildName.setEnabled(true);
                        jButtonUsers.setEnabled(true);
                        jLabel1.setEnabled(true);
                        jTextArea.append(username + ": Logged In..." + NEWLINE);
                        jUsername.setText("");
                        jPasswordField.setText("");
                    }
                    if (username.equals("Admin")) {
                        jButtonLogin.setText("LOGOUT");
                        jButtonClear.setEnabled(true);
                        jButtonUpdateAll.setEnabled(true);
                        jLabel1.setEnabled(true);
                        jTextArea.append(username + ": Logged In..." + NEWLINE);
                        jUsername.setText("");
                        jPasswordField.setText("");
                    }
                    if (username.equals("Moderator")) {
                        jButtonLogin.setText("LOGOUT");
                        jButtonClear.setEnabled(true);
                        jLabel1.setEnabled(true);
                        jButtonUpdateAll.setEnabled(true);
                        jTextArea.append(username + ": Logged In..." + NEWLINE);
                        jUsername.setText("");
                        jPasswordField.setText("");
                    }
                    logged = true;
                } else if (logged == false) {
                    jButtonLogin.setText("LOGIN");
                    jButtonSendMessage.setEnabled(false);
                    jButtonUpdateAll.setEnabled(false);
                    jLabel1.setEnabled(false);
                    jButtonClear.setEnabled(true);
                    jButtonGuildName.setEnabled(true);
                    jButtonUsers.setEnabled(true);
                }
            }
        } catch (SQLException exception) {
            Logger.getLogger(MainBot.BotPrimary.class.getName()).log(Level.SEVERE, null, exception);
        }
    }                                            

    private void jButtonGuildNameActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
        IGuild guild = BOT.getGuilds().get(Integer.parseInt(jTextFieldGuildNumber.getText()));
        String guildName = guild.getName();
        jTextArea.append(guildName + NEWLINE);
    }                                                

    private void jButtonUsersActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        IGuild guild = BOT.getGuilds().get(Integer.parseInt(jTextFieldGuildNumber.getText()));
        List<IUser> users = guild.getUsers();
        IUser owner = guild.getOwner();
        jTextArea.append(owner.getName() + NEWLINE + NEWLINE);
        for (IUser user : users) {
        jTextArea.append(user.getDisplayName(guild) + NEWLINE);
        }
    }                                            

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {                                             

        jTextArea.setText("");
    }                                            

    /**
     * @param args the command line arguments
     */
    public void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("System".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BotPrimary.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        EventDispatcher dis = BOT.getDispatcher();
        dis.registerListener(new JRASBotGUI());

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new JRASBotGUI().setVisible(true);
        });
    }
    // Variables declaration - do not modify                     
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonGuildName;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonSendMessage;
    private javax.swing.JButton jButtonUpdateAll;
    private javax.swing.JButton jButtonUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea;
    private javax.swing.JTextField jTextFieldChnID;
    private javax.swing.JTextField jTextFieldGuildNumber;
    private javax.swing.JTextField jTextFieldMessage;
    private javax.swing.JTextField jUsername;
    // End of variables declaration                   
}
