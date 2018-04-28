
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
        jCheckBox = new javax.swing.JCheckBox();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JRAS Bot");
        setMinimumSize(new java.awt.Dimension(544, 312));
        setName("jrasbotgui"); // NOI18N
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonUpdateAll.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 18)); // NOI18N
        jButtonUpdateAll.setText("!Updateall");
        jButtonUpdateAll.setToolTipText("Updates alll User's based on roles");
        jButtonUpdateAll.setEnabled(false);
        jButtonUpdateAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateAllActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonUpdateAll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 128, 39));

        jButtonSendMessage.setFont(new java.awt.Font("Nirmala UI Semilight", 0, 14)); // NOI18N
        jButtonSendMessage.setText("Send Message");
        jButtonSendMessage.setToolTipText("Send's a Message to the JRAS Discord");
        jButtonSendMessage.setEnabled(false);
        jButtonSendMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendMessageActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonSendMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, 128, 39));

        jButtonLogin.setText("LOGIN");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });
        getContentPane().add(jButtonLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 270, -1, -1));

        jCheckBox.setText("Logging");
        jCheckBox.setEnabled(false);
        jCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxItemStateChanged(evt);
            }
        });
        getContentPane().add(jCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, -1, -1));

        jLabel1.setFont(new java.awt.Font("Orator Std", 1, 24)); // NOI18N
        jLabel1.setText("ONLINE");
        jLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabel1.setEnabled(false);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 245, 26));

        jLabel2.setFont(new java.awt.Font("Prestige Elite Std", 0, 12)); // NOI18N
        jLabel2.setText("PASSWORD");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, 80, 20));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel3.setText("Channel ID");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 175, -1, 23));

        jLabel4.setFont(new java.awt.Font("Prestige Elite Std", 0, 12)); // NOI18N
        jLabel4.setText("USERNAME");
        jLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 10, 80, 20));
        getContentPane().add(jUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 130, -1));
        getContentPane().add(jPasswordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 130, 20));
        getContentPane().add(jTextFieldChnID, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 123, 30));

        jTextArea.setEditable(false);
        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 204, 372, -1));
        getContentPane().add(jTextFieldMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(144, 131, 147, 31));
        getContentPane().add(filler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 290, -1, 30));
        getContentPane().add(filler2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, 30, 0));
        getContentPane().add(filler3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 50, 20));

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
        IChannel channel = guilds.get(0).getChannelByID(Long.parseLong(channelID));
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
                        jCheckBox.setEnabled(true);
                        jLabel1.setEnabled(true);
                        jTextArea.append(username + ": Logged In..." + NEWLINE);
                        jUsername.setText("");
                        jPasswordField.setText("");
                    }
                    if (username.equals("Admin")) {
                        jButtonLogin.setText("LOGOUT");
                        jButtonUpdateAll.setEnabled(true);
                        jCheckBox.setEnabled(true);
                        jLabel1.setEnabled(true);
                        jTextArea.append(username + ": Logged In..." + NEWLINE);
                        jUsername.setText("");
                        jPasswordField.setText("");
                    }
                    if (username.equals("Moderator")) {
                        jButtonLogin.setText("LOGOUT");
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
                    jCheckBox.setEnabled(false);
                    jLabel1.setEnabled(false);
                }
            }
        } catch (SQLException exception) {
            Logger.getLogger(MainBot.BotPrimary.class.getName()).log(Level.SEVERE, null, exception);
        }
    }                                            

    private void jCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {                                           
        // TODO add your handling code here:
        System.out.println("State Change");
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
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonSendMessage;
    private javax.swing.JButton jButtonUpdateAll;
    private javax.swing.JCheckBox jCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea;
    private javax.swing.JTextField jTextFieldChnID;
    private javax.swing.JTextField jTextFieldMessage;
    private javax.swing.JTextField jUsername;
    // End of variables declaration                   
}
