/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Bean.ChatOutThread;
import Lib.StringUtility;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import orm.GroupChat;
import orm.Message;
import orm.MessageData;
import orm.Users;

/**
 *
 * @author AdamKyle
 */
public class GroupCharMainFrm extends javax.swing.JFrame {

    /**
     * Creates new form GroupCharMainFrm
     */
    private Users user;
    private MessageData mess;
    private ChatOutThread cot ;
    List<GroupChat> listGroups;
    private GroupChat groupChat;
    List<Message> listMessages;
    AddGroupFrm addGroupFrm;
    
    public GroupCharMainFrm() {
        initComponents();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addGroupFrm = new AddGroupFrm();
        this.setTitle("Group Chat");
    }
    void init(){
        mess = new MessageData();
        addGroupFrm.setCot(cot);
        getListGroup();
        clickList();
    }
    private void updateListGroups(){
        if(listGroups == null || listGroups.size() <= 0) return;
        DefaultTableModel dtm = (DefaultTableModel) jTable_listGroup.getModel();
        dtm.getDataVector().removeAllElements();
        for(GroupChat group : listGroups){
            dtm.addRow(group.toObjectListGroups());
        }
    }
    private void clickList(){
        jTable_listGroup.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount() >=1){
                    try {
                        MessageData messListMess = new MessageData();
                        groupChat = listGroups.get(jTable_listGroup.getSelectedRow());
                        jLabel_group.setText(groupChat.getGroupName());
                        messListMess.setFuntion(StringUtility.FUNCTION_CLIENT_GET_MESS_BY_GROUPID);
                        messListMess.setContent(groupChat.getGroupId()+"");
                        cot.setMessage(messListMess);
                        cot.resume();
                        listMessages = (List<Message>) ois.readObject();
                        updateMessages(listMessages);
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(HomeFrm.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    void getListGroup(){
        try {
            mess.setFuntion(StringUtility.FUNCTION_CLIENT_GET_GROUP_BY_USERID);
            cot.setMessage(mess);
            cot.resume();
            listGroups = (List<GroupChat>) ois.readObject();
            updateListGroups();
        } catch (IOException ex) {
            Logger.getLogger(HomeFrm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void updateMessages(List<Message> listMessages){
        DefaultTableModel dtm = (DefaultTableModel) jTable_messages.getModel();
        dtm.getDataVector().removeAllElements();
        if(listMessages == null || listMessages.size() <= 0 || listMessages.get(0) == null){
            dtm.addRow(new Object[]{""});
            return ;
        }
        for(Message mess : listMessages){
            if(mess.getToUserId() == user.getUserId()){
                //nguio nhan = user
                dtm.addRow(new Object[]{StringUtility.RECEIVE+"("+mess.getCreatedDate()+"): "+mess.getContnet()});
            } else{
                dtm.addRow(new Object[]{StringUtility.SEND+"("+mess.getCreatedDate()+"): "+mess.getContnet()});
            }
            
        }
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel_userName = new javax.swing.JLabel();
        jLabel_group = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_listGroup = new javax.swing.JTable();
        jButton_send = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea_messageSend = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable_messages = new javax.swing.JTable();
        jButton_refresh = new javax.swing.JButton();
        jButton_chat_group = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel_userName.setBackground(new java.awt.Color(204, 204, 204));
        jLabel_userName.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        jLabel_userName.setForeground(new java.awt.Color(51, 51, 255));
        jLabel_userName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel_userName.setText("user name");

        jLabel_group.setBackground(new java.awt.Color(255, 255, 255));
        jLabel_group.setFont(new java.awt.Font("Times New Roman", 1, 17)); // NOI18N
        jLabel_group.setText("Name Group Chat");

        jTable_listGroup.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "List Groups"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable_listGroup.setRowHeight(60);
        jScrollPane1.setViewportView(jTable_listGroup);

        jButton_send.setText("Send");
        jButton_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_sendActionPerformed(evt);
            }
        });

        jTextArea_messageSend.setColumns(20);
        jTextArea_messageSend.setRows(5);
        jScrollPane3.setViewportView(jTextArea_messageSend);

        jTable_messages.setForeground(new java.awt.Color(29, 27, 27));
        jTable_messages.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Messages"
            }
        ));
        jTable_messages.setToolTipText("");
        jTable_messages.setGridColor(new java.awt.Color(0, 255, 153));
        jTable_messages.setRowHeight(60);
        jTable_messages.setSelectionBackground(new java.awt.Color(187, 191, 196));
        jTable_messages.setSelectionForeground(new java.awt.Color(53, 55, 240));
        jScrollPane4.setViewportView(jTable_messages);

        jButton_refresh.setText("Làm mới");
        jButton_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_refreshActionPerformed(evt);
            }
        });

        jButton_chat_group.setText(" Creat Group Chat");
        jButton_chat_group.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_chat_groupActionPerformed(evt);
            }
        });

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_userName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton_refresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_chat_group)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel_group, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jScrollPane3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_send, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel_group, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel_userName, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_send, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_chat_group, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_sendActionPerformed
        if(jLabel_group.getText() == null || jLabel_group.getText().compareToIgnoreCase("") == 0){
            return;
        }
        mess = new MessageData();
        mess.setContent(jTextArea_messageSend.getText());
        System.out.println(mess.getContent());
        mess.setFuntion(StringUtility.FUNCTION_CLIENT_SENT_MESS_GROUP);
        mess.setUserIdReceive(groupChat.getGroupId());
        cot.setMessage(mess);
        cot.resume();
        jTextArea_messageSend.setText("");
    }//GEN-LAST:event_jButton_sendActionPerformed

    private void jButton_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_refreshActionPerformed
        getListGroup();
    }//GEN-LAST:event_jButton_refreshActionPerformed

    private void jButton_chat_groupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_chat_groupActionPerformed
        // TODO add your handling code here:
        
        addGroupFrm.init();
        this.setVisible(false);
        addGroupFrm.setVisible(true);
    }//GEN-LAST:event_jButton_chat_groupActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GroupCharMainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GroupCharMainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GroupCharMainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GroupCharMainFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GroupCharMainFrm().setVisible(true);
            }
        });
    }
    public ChatOutThread getCot() {
        return cot;
    }

    public void setCot(ChatOutThread cot) {
        this.cot = cot;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }
    private ObjectInputStream ois;
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_chat_group;
    private javax.swing.JButton jButton_refresh;
    private javax.swing.JButton jButton_send;
    private javax.swing.JLabel jLabel_group;
    private javax.swing.JLabel jLabel_userName;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable_listGroup;
    private javax.swing.JTable jTable_messages;
    private javax.swing.JTextArea jTextArea_messageSend;
    // End of variables declaration//GEN-END:variables
}
