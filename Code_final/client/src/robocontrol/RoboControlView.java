/*
 * RoboControlView.java
 */

package robocontrol;

import java.awt.Color;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import java.util.Vector;
import javax.media.*;
import javax.media.rtp.*;
import javax.media.rtp.event.*;
import javax.media.rtp.rtcp.*;
import javax.media.protocol.*;
import javax.media.protocol.DataSource;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.Format;
import javax.media.format.FormatChangeEvent;
import javax.media.control.BufferControl;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
/**
 * The application's main frame.
 */
public class RoboControlView extends FrameView{
OurSocket os;
AVReceive2 avReceive;
boolean keyPressedFlag = false;
public static int connectionstatus=0;

    private void showConsoleMessage (String msg)
    {
        java.util.Date date = new java.util.Date();
        String dstr = "[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "]";
        jtaConsole.append("\n" + dstr + " " + msg);
        jtaConsole.setCaretPosition(jtaConsole.getDocument().getLength());
    }
    
    public RoboControlView(SingleFrameApplication app) {
        super(app);
        
        initComponents();
        
        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() 
        {
            
            public void actionPerformed(ActionEvent e) 
            {
                statusMessageLabel.setText("");                
            }            
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) 
        {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
        
         public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        this.mainPanel.setFocusable(true);
                 KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher()
                {
                    public boolean dispatchKeyEvent(KeyEvent e)
                    {
                        boolean discardEvent = false;
                        
                        if (jtHost.isFocusOwner() || jtPort.isFocusOwner() || jtRTPport.isFocusOwner())
                            return discardEvent;
                        
                        if (e.getID() == KeyEvent.KEY_PRESSED)
                        {
                           anyKeyPressed (e);
                        }
                        else if (e.getID() == KeyEvent.KEY_RELEASED)
                        {
                            anyKeyReleased(e);
                        }
                        return discardEvent;
                    }
                });
                
                 
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = RoboControlApp.getApplication().getMainFrame();
            aboutBox = new RoboControlAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        RoboControlApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaConsole = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtHost = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtPort = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtRTPport = new javax.swing.JTextField();
        jbShowVideo = new javax.swing.JButton();
        jbConnect = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jbUp = new javax.swing.JButton();
        jbDown = new javax.swing.JButton();
        jbLeft = new javax.swing.JButton();
        jbRight = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setName("jPanel3"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jtaConsole.setColumns(20);
        jtaConsole.setEditable(false);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(robocontrol.RoboControlApp.class).getContext().getResourceMap(RoboControlView.class);
        jtaConsole.setFont(resourceMap.getFont("jtaConsole.font")); // NOI18N
        jtaConsole.setLineWrap(true);
        jtaConsole.setRows(5);
        jtaConsole.setText(resourceMap.getString("jtaConsole.text")); // NOI18N
        jtaConsole.setWrapStyleWord(true);
        jtaConsole.setName("jtaConsole"); // NOI18N
        jScrollPane1.setViewportView(jtaConsole);

        jPanel2.setName("jPanel2"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jtHost.setText(resourceMap.getString("jtHost.text")); // NOI18N
        jtHost.setName("jtHost"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jtPort.setText(resourceMap.getString("jtPort.text")); // NOI18N
        jtPort.setName("jtPort"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jtRTPport.setText(resourceMap.getString("jtRTPport.text")); // NOI18N
        jtRTPport.setName("jtRTPport"); // NOI18N

        jbShowVideo.setText(resourceMap.getString("jbShowVideo.text")); // NOI18N
        jbShowVideo.setName("jbShowVideo"); // NOI18N
        jbShowVideo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbShowVideoMouseClicked(evt);
            }
        });

        jbConnect.setText(resourceMap.getString("jbConnect.text")); // NOI18N
        jbConnect.setName("jbConnect"); // NOI18N
        jbConnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbConnectMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtPort, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(jtHost, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                    .addComponent(jtRTPport, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jbShowVideo, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(jbConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtHost, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtRTPport, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbConnect)
                    .addComponent(jbShowVideo))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("jPanel1.border.title"), javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION)); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jbUp.setIcon(resourceMap.getIcon("jbUp.icon")); // NOI18N
        jbUp.setText(resourceMap.getString("jbUp.text")); // NOI18N
        jbUp.setBorder(null);
        jbUp.setBorderPainted(false);
        jbUp.setContentAreaFilled(false);
        jbUp.setName("jbUp"); // NOI18N
        jbUp.setPressedIcon(resourceMap.getIcon("jbUp.pressedIcon")); // NOI18N
        jbUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbUpMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbUpMouseReleased(evt);
            }
        });

        jbDown.setIcon(resourceMap.getIcon("jbDown.icon")); // NOI18N
        jbDown.setText(resourceMap.getString("jbDown.text")); // NOI18N
        jbDown.setBorder(null);
        jbDown.setBorderPainted(false);
        jbDown.setContentAreaFilled(false);
        jbDown.setName("jbDown"); // NOI18N
        jbDown.setPressedIcon(resourceMap.getIcon("jbDown.pressedIcon")); // NOI18N
        jbDown.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbDownMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbDownMouseReleased(evt);
            }
        });

        jbLeft.setIcon(resourceMap.getIcon("jbLeft.icon")); // NOI18N
        jbLeft.setText(resourceMap.getString("jbLeft.text")); // NOI18N
        jbLeft.setBorder(null);
        jbLeft.setBorderPainted(false);
        jbLeft.setContentAreaFilled(false);
        jbLeft.setName("jbLeft"); // NOI18N
        jbLeft.setPressedIcon(resourceMap.getIcon("jbLeft.pressedIcon")); // NOI18N
        jbLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbLeftMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbLeftMouseReleased(evt);
            }
        });

        jbRight.setIcon(resourceMap.getIcon("jbRight.icon")); // NOI18N
        jbRight.setText(resourceMap.getString("jbRight.text")); // NOI18N
        jbRight.setBorder(null);
        jbRight.setBorderPainted(false);
        jbRight.setContentAreaFilled(false);
        jbRight.setName("jbRight"); // NOI18N
        jbRight.setPressedIcon(resourceMap.getIcon("jbRight.pressedIcon")); // NOI18N
        jbRight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jbRightMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jbRightMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jbLeft)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addComponent(jbRight)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbDown)
                            .addComponent(jbUp, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbUp, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbLeft))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(jbRight)))
                .addGap(34, 34, 34)
                .addComponent(jbDown)
                .addContainerGap())
        );

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jDesktopPane1.setToolTipText(resourceMap.getString("jDesktopPane1.toolTipText")); // NOI18N
        jDesktopPane1.setName("jDesktopPane1"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(313, 313, 313)
                        .addComponent(jLabel5))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(robocontrol.RoboControlApp.class).getContext().getActionMap(RoboControlView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 503, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void sendCommand (String cmd)
    {
        if (cmd.equalsIgnoreCase("connect"))
        {
            try
                {
                    os = new OurSocket(this.jtHost.getText().trim(), Integer.parseInt(this.jtPort.getText().trim()));
                    os.println(1);
                    this.connectionstatus=1;
                }
                catch (Exception ex) 
                {
                    showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                    
                }
                  
         }
        else if(this.connectionstatus==0)
        {
            showConsoleMessage ("Connect to server first.");
            jbConnect.setLabel ("Connect");
            return;
        }
        
        if (cmd.equalsIgnoreCase("upp"))
        {
                try {
                    
                    os.println(32);
                } catch (Exception ex) {
                    showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                }            
        }
        else if (cmd.equalsIgnoreCase("downp"))
        {
                try {
                    //os = new OurSocket(this.jtHost.getText().trim(), Integer.parseInt(this.jtPort.getText().trim()));
                    os.println(34);
                } 
                catch (Exception ex) {
                    showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                }            
        }
        else if (cmd.equalsIgnoreCase("leftp"))
        {
                try {
                    //os = new OurSocket(this.jtHost.getText().trim(), Integer.parseInt(this.jtPort.getText().trim()));
                    os.println(36);
                } 
                catch (Exception ex) {
                    showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                }            
        }
        else if (cmd.equalsIgnoreCase("rightp"))
        {
                try {
                   // os = new OurSocket(this.jtHost.getText().trim(), Integer.parseInt(this.jtPort.getText().trim()));
                    os.println(38);
                } catch (Exception ex) {
                   showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                }            
        }
        
        else if (cmd.equalsIgnoreCase("upr"))
        {
                try {
                    //os = new OurSocket(this.jtHost.getText().trim(), Integer.parseInt(this.jtPort.getText().trim()));
                    os.println(33);
                } 
                catch (Exception ex) 
                {
                    showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                }            
        }
        else if (cmd.equalsIgnoreCase("downr"))
        {
                try {
                    //os = new OurSocket(this.jtHost.getText().trim(), Integer.parseInt(this.jtPort.getText().trim()));
                    os.println(35);
                } catch (Exception ex) {
                    showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                    try {
                        os.close();
                    } catch (Exception ex1) {;}
                }            
        }
        else if (cmd.equalsIgnoreCase("leftr"))
        {
                try {
                    //os = new OurSocket(this.jtHost.getText().trim(), Integer.parseInt(this.jtPort.getText().trim()));
                    os.println(37);
                } catch (Exception ex) {
                    showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                    try {
                        os.close();
                    } catch (Exception ex1) {;}
                }            
        }
        else if (cmd.equalsIgnoreCase("rightr"))
        {
                try {
                    //os = new OurSocket(this.jtHost.getText().trim(), Integer.parseInt(this.jtPort.getText().trim()));
                    os.println(39);
                } catch (Exception ex) {
                    showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                    try {
                        os.close();
                    } catch (Exception ex1) {;}
                }            
        }
        else if (cmd.equalsIgnoreCase("disconnect"))
        {
            try {
                //os = new OurSocket(this.jtHost.getText().trim(), Integer.parseInt(this.jtPort.getText().trim()));
                os.println(3);
                os.close();
            } catch (Exception ex) {
                showConsoleMessage ("Could not connect to server");
                    jbConnect.setLabel ("Connect");
                    this.connectionstatus=0;
                
            }      
        }
    }
    
    private void anyKeyPressed (java.awt.event.KeyEvent evt)
    {
        if (keyPressedFlag == true)        
            return;
        keyPressedFlag = true;
        
            if (evt.getKeyCode() == evt.VK_UP)
            {
                showConsoleMessage ("Up Arrow Key Pressed");
                this.jbUp.doClick();
                sendCommand ("upp");
            }
            else if (evt.getKeyCode() == evt.VK_DOWN)
            {
                showConsoleMessage ("Down Arrow Key Pressed");
                this.jbDown.doClick();
                sendCommand ("downp");
            }
            else if (evt.getKeyCode() == evt.VK_LEFT)
            {
                showConsoleMessage ("Left Arrow Key Pressed");
                this.jbLeft.doClick();
                sendCommand ("leftp");
            }            
            else if (evt.getKeyCode() == evt.VK_RIGHT)
            {
                showConsoleMessage ("Right Arrow Key Pressed");
                this.jbRight.doClick();
                sendCommand ("rightp");
            }
            
    }
    
    private void anyKeyReleased (java.awt.event.KeyEvent evt)
    {
        keyPressedFlag = false;
        if (evt.getKeyCode() == evt.VK_UP)
            {
                showConsoleMessage ("Up Arrow Key Released");
                this.jbUp.doClick();
                sendCommand ("upr");
            }
            else if (evt.getKeyCode() == evt.VK_DOWN)
            {
                showConsoleMessage ("Down Arrow Key Released");
                this.jbDown.doClick();
                sendCommand ("downr");
            }
            else if (evt.getKeyCode() == evt.VK_LEFT)
            {
                showConsoleMessage ("Left Arrow Key Released");
                this.jbLeft.doClick();
                sendCommand ("leftr");
            }            
            else if (evt.getKeyCode() == evt.VK_RIGHT)
            {
                showConsoleMessage ("Right Arrow Key Released");
                this.jbRight.doClick();
                sendCommand ("rightr");
            }
                    
        
    }
    
    private void anyMousePressed(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == jbUp)
        {
            showConsoleMessage ("Up Button Pressed");
            sendCommand ("upp");
        }
        else if (evt.getSource() == jbDown)
        {
            showConsoleMessage ("Down Button Pressed");
            sendCommand ("downp");
        }
        else if (evt.getSource() == jbLeft)
        {
            showConsoleMessage ("Left Button Pressed");
            sendCommand ("leftp");   
        }
        else if (evt.getSource() == jbRight)
        {
            showConsoleMessage ("Right Button Pressed");
            sendCommand ("rightp");
        }
    }
    
    private void anyMouseReleased(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == jbUp)
        {
            showConsoleMessage ("Up Button Released");
            sendCommand ("upr");
        }
        else if (evt.getSource() == jbDown)
        {
            showConsoleMessage ("Down Button Released");
            sendCommand ("downr");
        }
        else if (evt.getSource() == jbLeft)
        {
            showConsoleMessage ("Left Button Released");
            sendCommand ("leftr");   
        }
        else if (evt.getSource() == jbRight)
        {
            showConsoleMessage ("Right Button Released");
            sendCommand ("rightr");
        }
    }    
    
    private void jbShowVideoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbShowVideoMouseClicked
    if (jbShowVideo.getLabel().equals("Show Video"))
    {
    String s[] = new String [1];
    s[0] = new String(this.jtHost.getText().trim() + "/" + this.jtRTPport.getText().trim());
    
    avReceive = new AVReceive2(s);
	if (!avReceive.initialize()) {
            JOptionPane.showMessageDialog(this.mainPanel, "Failed to initialize the sessions.");
	}
        else
        {
            jbShowVideo.setLabel ("Stop Video");
        }
    }
    else
    {
        avReceive.close();
        avReceive = null;
        jbShowVideo.setLabel ("Show Video");
    }
}//GEN-LAST:event_jbShowVideoMouseClicked

private void jbLeftMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbLeftMousePressed
    anyMousePressed(evt);
}//GEN-LAST:event_jbLeftMousePressed

private void jbLeftMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbLeftMouseReleased
    anyMouseReleased(evt);
}//GEN-LAST:event_jbLeftMouseReleased

private void jbUpMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbUpMousePressed
    anyMousePressed(evt);
}//GEN-LAST:event_jbUpMousePressed

private void jbUpMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbUpMouseReleased
    anyMouseReleased(evt);
}//GEN-LAST:event_jbUpMouseReleased

private void jbDownMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDownMousePressed
    anyMousePressed(evt);
}//GEN-LAST:event_jbDownMousePressed

private void jbDownMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbDownMouseReleased
    anyMouseReleased(evt);
}//GEN-LAST:event_jbDownMouseReleased

private void jbRightMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbRightMousePressed
    anyMousePressed(evt);
}//GEN-LAST:event_jbRightMousePressed

private void jbRightMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbRightMouseReleased
    anyMouseReleased(evt);
}//GEN-LAST:event_jbRightMouseReleased

private void jbConnectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbConnectMouseClicked
if (jbConnect.getLabel().equals("Connect"))
    {
            showConsoleMessage ("Connecting to server..");
            jbConnect.setLabel ("Disconnect");
            sendCommand ("connect");            
            if(this.connectionstatus==1)
                showConsoleMessage ("Connected.");
    }
    else
    {   
            jbConnect.setLabel ("Connect");
            showConsoleMessage ("Disconnecting from server..");
            sendCommand ("disconnect");
            showConsoleMessage ("Disconnected.");
    }
}//GEN-LAST:event_jbConnectMouseClicked


    @SuppressWarnings("static-access")
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbConnect;
    private javax.swing.JButton jbDown;
    private javax.swing.JButton jbLeft;
    private javax.swing.JButton jbRight;
    private javax.swing.JButton jbShowVideo;
    private javax.swing.JButton jbUp;
    private javax.swing.JTextField jtHost;
    private javax.swing.JTextField jtPort;
    private javax.swing.JTextField jtRTPport;
    private javax.swing.JTextArea jtaConsole;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
    
    
    class AVReceive2 implements ReceiveStreamListener, SessionListener, 
	ControllerListener
{
    String sessions[] = null;
    RTPManager mgrs[] = null;
    Vector playerWindows = null;

    boolean dataReceived = false;
    Object dataSync = new Object();


    public AVReceive2(String sessions[]) {
	this.sessions = sessions;
    }

    protected boolean initialize() {

       try {
	    InetAddress ipAddr;
	    SessionAddress localAddr = new SessionAddress();
	    SessionAddress destAddr;

	    mgrs = new RTPManager[sessions.length];
	    playerWindows = new Vector();
            showConsoleMessage("Pass1");
	    SessionLabel session;

	    // Open the RTP sessions.
	    for (int i = 0; i < sessions.length; i++) {

	 	// Parse the session addresses.
		try {
		    session = new SessionLabel(sessions[i]);
		} catch (IllegalArgumentException e) {
                    
                    showConsoleMessage("Failed to parse the session address given: " + sessions[i]);
		    return false;
		}

		showConsoleMessage("Open RTP session for: addr: " + session.addr + " port: " + session.port + " ttl: " + session.ttl);

		mgrs[i] = (RTPManager) RTPManager.newInstance();
		mgrs[i].addSessionListener(this);
		mgrs[i].addReceiveStreamListener(this);
                showConsoleMessage("Pass2");
		ipAddr = InetAddress.getByName("127.0.0.1");//      getByName(session.addr);
                showConsoleMessage("Pass10");
		if( ipAddr.isMulticastAddress()) {
		    // local and remote address pairs are identical:
		    localAddr= new SessionAddress( ipAddr,
						   session.port,
						   session.ttl);
                    showConsoleMessage("Pass3");
		    destAddr = new SessionAddress( ipAddr,
						   session.port,
						   session.ttl);
                    showConsoleMessage("Pass4");
		} else {
                    showConsoleMessage("Pass11");
		    localAddr= new SessionAddress( InetAddress.getByName("127.0.0.1"),
			  		           SessionAddress.ANY_PORT);
                    showConsoleMessage("Pass5");
                    destAddr = new SessionAddress( ipAddr, session.port);
                    showConsoleMessage("Pass6");
		}
			
		mgrs[i].initialize( localAddr);
                showConsoleMessage("Pass8");

		// You can try out some other buffer size to see
		// if you can get better smoothness.
		BufferControl bc = (BufferControl)mgrs[i].getControl("javax.media.control.BufferControl");
		if (bc != null)
		    bc.setBufferLength(350);
		 showConsoleMessage("Pass12");   
    		mgrs[i].addTarget(destAddr);
                showConsoleMessage("Pass13");
	    }

        } catch (Exception e){
            showConsoleMessage("Cannot create the RTP Session1234: " + e.getMessage());
            return false;
        }

	// Wait for data to arrive before moving on.

	long then = System.currentTimeMillis();
	long waitingPeriod = 30000;  // wait for a maximum of 30 secs.

	try{
	    synchronized (dataSync) {
		while (!dataReceived && 
			System.currentTimeMillis() - then < waitingPeriod) {
		    if (!dataReceived)
			showConsoleMessage("Waiting for RTP data to arrive...");
		    dataSync.wait(1000);
		}
	    }
	} catch (Exception e) {}

	if (!dataReceived) {
	    showConsoleMessage("No RTP data was received.");
	    close();
	    return false;
	}

        return true;
    }


    public boolean isDone() {
	return playerWindows.size() == 0;
    }


    /**
     * Close the players and the session managers.
     */
    protected void close() {

	for (int i = 0; i < playerWindows.size(); i++) {
	    try {
		((PlayerWindow)playerWindows.elementAt(i)).close();
	    } catch (Exception e) {}
	}

	playerWindows.removeAllElements();

	// close the RTP session.
	for (int i = 0; i < mgrs.length; i++) {
	    if (mgrs[i] != null) {
                mgrs[i].removeTargets( "Closing session from AVReceive2");
                mgrs[i].dispose();
		mgrs[i] = null;
	    }
	}
    }


    PlayerWindow find(Player p) {
	for (int i = 0; i < playerWindows.size(); i++) {
	    PlayerWindow pw = (PlayerWindow)playerWindows.elementAt(i);
	    if (pw.player == p)
		return pw;
	}
	return null;
    }


    PlayerWindow find(ReceiveStream strm) {
	for (int i = 0; i < playerWindows.size(); i++) {
	    PlayerWindow pw = (PlayerWindow)playerWindows.elementAt(i);
	    if (pw.stream == strm)
		return pw;
	}
	return null;
    }


    /**
     * SessionListener.
     */
    public synchronized void update(SessionEvent evt) {
	if (evt instanceof NewParticipantEvent) {
	    Participant p = ((NewParticipantEvent)evt).getParticipant();
	    showConsoleMessage("A new participant had just joined: " + p.getCNAME());
	}
    }


    /**
     * ReceiveStreamListener
     */
    public synchronized void update( ReceiveStreamEvent evt) {

	RTPManager mgr = (RTPManager)evt.getSource();
	Participant participant = evt.getParticipant();	// could be null.
	ReceiveStream stream = evt.getReceiveStream();  // could be null.

	if (evt instanceof RemotePayloadChangeEvent) {
     
	    showConsoleMessage("Received an RTP PayloadChangeEvent.");
	    showConsoleMessage("Sorry, cannot handle payload change.");
	    System.exit(0);

	}
    
	else if (evt instanceof NewReceiveStreamEvent) {

	    try {
		stream = ((NewReceiveStreamEvent)evt).getReceiveStream();
		DataSource ds = stream.getDataSource();

		// Find out the formats.
		RTPControl ctl = (RTPControl)ds.getControl("javax.media.rtp.RTPControl");
		if (ctl != null){
		    showConsoleMessage("Recevied new RTP stream: " + ctl.getFormat());
		} else
		    showConsoleMessage("Recevied new RTP stream");

		if (participant == null)
		    showConsoleMessage("The sender of this stream had yet to be identified.");
		else {
		    showConsoleMessage("The stream comes from: " + participant.getCNAME()); 
		}

		// create a player by passing datasource to the Media Manager
		Player p = javax.media.Manager.createPlayer(ds);
		if (p == null)
		    return;

		p.addControllerListener(this);
		p.realize();
		PlayerWindow pw = new PlayerWindow(p, stream);
                jDesktopPane1.add(pw, javax.swing.JLayeredPane.DEFAULT_LAYER);
                ((javax.swing.plaf.basic.BasicInternalFrameUI) pw.getUI()).setNorthPane(null);
                try {
                pw.setMaximum(true);
                } catch (java.beans.PropertyVetoException e1) {
                e1.printStackTrace();
                }
                pw.setBorder(null);
                pw.setMaximizable(true);
                pw.setResizable(true);
		playerWindows.addElement(pw);

		// Notify intialize() that a new stream had arrived.
		synchronized (dataSync) {
		    dataReceived = true;
		    dataSync.notifyAll();
		}

	    } catch (Exception e) {
		showConsoleMessage("NewReceiveStreamEvent exception " + e.getMessage());
		return;
	    }
        
	}

	else if (evt instanceof StreamMappedEvent) {

	     if (stream != null && stream.getDataSource() != null) {
		DataSource ds = stream.getDataSource();
		// Find out the formats.
		RTPControl ctl = (RTPControl)ds.getControl("javax.media.rtp.RTPControl");
                String msg = "The previously unidentified stream ";
		if (ctl != null)
		    msg += ctl.getFormat().toString();
		showConsoleMessage(msg + " had now been identified as sent by: " + participant.getCNAME());
	     }
	}

	else if (evt instanceof ByeEvent) {

	     showConsoleMessage("Got \"bye\" from: " + participant.getCNAME());
	     PlayerWindow pw = find(stream);
	     if (pw != null) {
		pw.close();
		playerWindows.removeElement(pw);
	     }
	}

    }


    /**
     * ControllerListener for the Players.
     */
    public synchronized void controllerUpdate(ControllerEvent ce) {

	Player p = (Player)ce.getSourceController();

	if (p == null)
	    return;

	// Get this when the internal players are realized.
	if (ce instanceof RealizeCompleteEvent) {
	    PlayerWindow pw = find(p);
	    if (pw == null) {
		// Some strange happened.
		showConsoleMessage("error!");
		System.exit(-1);
	    }
	    pw.initialize();
	    pw.setVisible(true);
	    p.start();
	}

	if (ce instanceof ControllerErrorEvent) {
	    p.removeControllerListener(this);
	    PlayerWindow pw = find(p);
	    if (pw != null) {
		pw.close();	
		playerWindows.removeElement(pw);
	    }
	    showConsoleMessage("AVReceive2 internal error: " + ce);
	}

    }


    /**
     * A utility class to parse the session addresses.
     */
    class SessionLabel {

	public String addr = null;
	public int port;
	public int ttl = 1;

	SessionLabel(String session) throws IllegalArgumentException {
	    int off;
	    String portStr = null, ttlStr = null;

	    if (session != null && session.length() > 0) {
		while (session.length() > 1 && session.charAt(0) == '/')
		    session = session.substring(1);

		// Now see if there's a addr specified.
		off = session.indexOf('/');
		if (off == -1) {
		    if (!session.equals(""))
			addr = session;
		} else {
		    addr = session.substring(0, off);
		    session = session.substring(off + 1);
		    // Now see if there's a port specified
		    off = session.indexOf('/');
		    if (off == -1) {
			if (!session.equals(""))
			    portStr = session;
		    } else {
			portStr = session.substring(0, off);
			session = session.substring(off + 1);
			// Now see if there's a ttl specified
			off = session.indexOf('/');
			if (off == -1) {
			    if (!session.equals(""))
				ttlStr = session;
			} else {
			    ttlStr = session.substring(0, off);
			}
		    }
		}
	    }

	    if (addr == null)
		throw new IllegalArgumentException();

	    if (portStr != null) {
		try {
		    Integer integer = Integer.valueOf(portStr);
		    if (integer != null)
			port = integer.intValue();
		} catch (Throwable t) {
		    throw new IllegalArgumentException();
		}
	    } else
		throw new IllegalArgumentException();

	    if (ttlStr != null) {
		try {
		    Integer integer = Integer.valueOf(ttlStr);
		    if (integer != null)
			ttl = integer.intValue();
		} catch (Throwable t) {
		    throw new IllegalArgumentException();
		}
	    }
	}
    }


    /**
     * GUI classes for the Player.
     */
    class PlayerWindow extends JInternalFrame {

	Player player;
	ReceiveStream stream;

	PlayerWindow(Player p, ReceiveStream strm) {
	    player = p;
	    stream = strm;
	}

	public void initialize() {
	    add(new PlayerPanel(player));
	}

	public void close() {
	    player.close();
	    setVisible(false);
	    dispose();
	}

            @Override
	public void addNotify() {
	    super.addNotify();
	    pack();
	}
    }

    /**
     * GUI classes for the Player.
     */
    class PlayerPanel extends Panel {

	Component vc, cc;

	PlayerPanel(Player p) {
	    setLayout(new BorderLayout());
	    if ((vc = p.getVisualComponent()) != null)
		add("Center", vc);
	    if ((cc = p.getControlPanelComponent()) != null)
		add("South", cc);
	}

	public Dimension getPreferredSize() {
	    int w = 0, h = 0;
	    if (vc != null) {
		Dimension size = vc.getPreferredSize();
		w = size.width;
		h = size.height;
	    }
	    if (cc != null) {
		Dimension size = cc.getPreferredSize();
		if (w == 0)
		    w = size.width;
		h += size.height;
	    }
	    if (w < 160)
		w = 160;
	    return new Dimension(w, h);
	}
    }   
}// end of AVReceive2
}


