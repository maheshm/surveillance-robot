package robocontrol.server;

import java.awt.Dimension;
import java.io.IOException;
import java.io.File;
//import java.net.InetAddress;
//import java.net.ServerSocket;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
//import java.text.Format;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.ButtonGroup;
//import java.util.ResourceBundle.Control;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.Codec;
import javax.media.Controller;
import javax.media.ControllerClosedEvent;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.DataSink;
import javax.media.Manager;
import javax.media.MediaException;
import javax.media.MediaLocator;
import javax.media.NoProcessorException;
import javax.media.Owned;
import javax.media.Player;
import javax.media.Processor;
import javax.media.control.QualityControl;
import javax.media.control.TrackControl;
import javax.media.format.VideoFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
//import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.*;
import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.*;

/*
 * ServerApp.java
 *
 * 
 */
import javax.swing.UnsupportedLookAndFeelException;

public class ServerApp extends javax.swing.JFrame {

    VideoTransmit vserver = null;
    RoboControlServer server = null;
    OurSocket ls = null;
    private boolean sendSSData = false;
    private boolean videoServerStarted = false;
    private boolean roboControlServerStarted = false;
    private int roboControlListenerPort = -1;
    File mediaFile = new File("/home/atwork/project_robot/smartcam/src/app/file");

    /** Creates new form ServerApp */
    public ServerApp() 
    {
                initComponents();
    }

    public int getRoboControlServerPort() 
    {
            return Integer.parseInt(jtRoboControlPort.getText().trim());
    }

    public void connectToRoboControlListenerServer(int port) 
    {
        roboControlListenerPort = port;
        try
        {
            showConsoleMessage("Connecting to RoboControl Client Listener Server at " + server.clientAddr.getHostAddress() + ":" + roboControlListenerPort);
            ls = new OurSocket(server.clientAddr.getHostAddress(), port);
        }
        catch (UnknownHostException ex) 
        {
            showConsoleMessage("connectToRoboControlListenerServer Error: " + ex.getMessage());
        }
        catch (IOException ex) 
        {
            showConsoleMessage("connectToRoboControlListenerServer Error: " + ex.getMessage());
        }
    }

    public void UpdateRoboControlServerConnStatus(boolean connected) 
    {
        if (connected) 
        {
            jlServerConnStatus.setText("Connected to " + server.clientAddr.getHostAddress() + ":" + String.valueOf(server.clientPort));
        }
        
        else 
        {
            try 
            {
                jlServerConnStatus.setText("Not Connected");
                if (ls != null) {
                    ls.close();
                }
            }
            catch (IOException ex) 
            {
                showConsoleMessage("UpdateRoboControlServerConnStatus Error: " + ex.getMessage());
            }
        }
    }

    public void showConsoleMessage(String msg) 
    {
        java.util.Date date = new java.util.Date();
        String dstr = "[" + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + "]";
        jtaConsole.append("\n" + dstr + " " + msg);
        jtaConsole.setCaretPosition(jtaConsole.getDocument().getLength());
    }

      /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaConsole = new javax.swing.JTextArea();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jbStartRoboControlServer = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jlServerConnStatus = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jtRoboControlPort = new javax.swing.JTextField();
        jbStartVideoServer = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jcbCaptureDevice = new javax.swing.JComboBox();
        jLabel29 = new javax.swing.JLabel();
        jtVideoPort = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RoboControl Server");

        jLabel1.setFont(new java.awt.Font("Tahoma 18 14", 1, 12));
        jLabel1.setText("RoboControl (Server)");

        jtaConsole.setColumns(20);
        jtaConsole.setEditable(false);
        jtaConsole.setFont(new java.awt.Font("Tahoma 10 12", 0, 10));
        jtaConsole.setLineWrap(true);
        jtaConsole.setRows(5);
        jtaConsole.setText("Welcome to RoboControl Server..");
        jtaConsole.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jtaConsole);

        jbStartRoboControlServer.setText("Start RoboControl Server");
        jbStartRoboControlServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStartRoboControlServerActionPerformed(evt);
            }
        });

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("RoboControl Server"));

        jLabel27.setText("Connection Status:");

        jlServerConnStatus.setForeground(new java.awt.Color(153, 0, 0));
        jlServerConnStatus.setText("Not Connected");

        jLabel23.setText("RoboControl Server Port:");

        jtRoboControlPort.setText("1080");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtRoboControlPort, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlServerConnStatus))
                .addContainerGap(304, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtRoboControlPort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlServerConnStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)))
        );

        jbStartVideoServer.setText("Start Video Streaming Server");
        jbStartVideoServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbStartVideoServerActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Video Streaming Server"));

        jLabel24.setText("Capture Device:");

        jcbCaptureDevice.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "vfw://0", "vfw://1", "vfw://2", "vfw://3", "file://dev/video1" }));
        jcbCaptureDevice.setPreferredSize(new java.awt.Dimension(100, 22));

        jLabel29.setText("Video Server Port:");

        jtVideoPort.setText("42050");
        jtVideoPort.setPreferredSize(new java.awt.Dimension(50, 17));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(25, 25, 25)
                        .addComponent(jcbCaptureDevice, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtVideoPort, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)))
                .addContainerGap(313, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbCaptureDevice, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtVideoPort, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jbStartRoboControlServer)
                        .addGap(38, 38, 38)
                        .addComponent(jbStartVideoServer)
                        .addGap(61, 61, 61))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbStartVideoServer)
                    .addComponent(jbStartRoboControlServer))
                .addContainerGap())
        );

        jTabbedPane1.addTab("RoboControl Server", jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(180, 180, 180))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbStartVideoServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStartVideoServerActionPerformed

        if (!videoServerStarted) 
        {
            try
            {
                vserver = new VideoTransmit(new MediaLocator(mediaFile.toURL()), "255.255.255.255", this.jtVideoPort.getText());
            }catch(MalformedURLException e ){}
            videoServerStarted = true;
            showConsoleMessage("Video Server Started..");
        }
        else
        {
            if (vserver != null) 
            {
                vserver.stop();
                vserver.t.interrupt();
                showConsoleMessage("Video Server Stopped..");
                videoServerStarted = false;
            }
        }

        jbStartVideoServer.setText(videoServerStarted ? "Stop Video Streaming Server" : "Start Video Streaming Server");
    }//GEN-LAST:event_jbStartVideoServerActionPerformed

    private void jbStartRoboControlServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbStartRoboControlServerActionPerformed
        
        roboControlServerStarted = !roboControlServerStarted;

        if (roboControlServerStarted) 
        {
            server = new RoboControlServer(this);
        }
        else 
        {
            server.stop();
            showConsoleMessage("RoboControl Server Stopped");
        }

        jbStartRoboControlServer.setText(roboControlServerStarted ? "Stop RoboControl Server" : "Start RoboControl Server");
    }//GEN-LAST:event_jbStartRoboControlServerActionPerformed

    
    public static void main(String args[]) 
    {
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            java.awt.EventQueue.invokeLater(new Runnable() 
            {
                public void run() 
                {
                    new ServerApp().setVisible(true);
                }
            }
            );
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) 
        {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) 
        {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) 
        {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class VideoTransmit implements Runnable 
    {
        // Input MediaLocator
        // Can be a file or http or capture source
        private MediaLocator locator;
        private String ipAddress;
        private String port;
        private Processor processor = null;
        private DataSink rtptransmitter = null;
        private DataSource dataOutput = null;
        Thread t;

        public VideoTransmit(MediaLocator locator, String ipAddress, String port) 
        {
            this.locator = locator;
            this.ipAddress = ipAddress;
            this.port = port;

            showConsoleMessage(",ip=" + ipAddress + ",port=" + port.toString());
            t = new Thread(this, "Video Transmission Server");
            t.start();
        }

        /**
         * Starts the transmission. Returns null if transmission started ok.
         * Otherwise it returns a string with the reason why the setup failed.
         */
        
        public synchronized String start() 
        {
            String result;

            // Create a processor for the specified media locator
            // and program it to output JPEG/RTP
            result = createProcessor();
            if (result != null) 
            {
                return result;
            }

            // Create an RTP session to transmit the output of the
            // processor to the specified IP address and port no.
            result = createTransmitter();
            if (result != null) 
            {
                processor.close();
                processor = null;
                return result;
            }

            // Start the transmission
            processor.start();

            return null;
        }

        /**
         * Stops the transmission if already started
         */
        public void stop() 
        {
            synchronized (this) 
            {
                if (processor != null) 
                {
                    processor.stop();
                    if (processor != null) 
                    {
                        processor.close();
                    }
                    processor = null;
                    if (rtptransmitter != null) 
                    {
                        rtptransmitter.close();
                    }
                    rtptransmitter = null;
                }
            }
        }

        private String createProcessor() 
        {
            if (locator == null) 
            {
                return "Locator is null";
            }

            DataSource ds;
            DataSource clone;

            try 
            {
                 ds = Manager.createDataSource(locator);
            }
            catch (Exception e) 
            {
                return "Couldn't create DataSource";
            }

            // Try to create a processor to handle the input media locator
            try 
            {
                
                processor = Manager.createProcessor(ds);
            }
            catch (NoProcessorException npe) 
            {
                return "Couldn't create processor";
            }
            catch (IOException ioe) 
            {
                return "IOException creating processor";
            }

            // Wait for it to configure
            boolean result = waitForState(processor, Processor.Configured);
            if (result == false) 
            {
                return "Couldn't configure processor";
            }

            // Get the tracks from the processor
            TrackControl[] tracks = processor.getTrackControls();

            // Do we have atleast one track?
            if (tracks == null || tracks.length < 1) 
            {
                return "Couldn't find tracks in processor";
            }

            boolean programmed = false;
            // Search through the tracks for a video track
            for (int i = 0; i < tracks.length; i++) 
            {
                javax.media.Format format = tracks[i].getFormat();
                if (tracks[i].isEnabled() && format instanceof VideoFormat && !programmed) 
                {

                    // Found a video track. Try to program it to output JPEG/RTP
                    // Make sure the sizes are multiple of 8's.
                    Dimension size = ((VideoFormat) format).getSize();
                    float frameRate = ((VideoFormat) format).getFrameRate();
                    int w = (size.width % 8 == 0 ? size.width : (int) (size.width / 8) * 8);
                    int h = (size.height % 8 == 0 ? size.height : (int) (size.height / 8) * 8);
                    @SuppressWarnings("static-access")
                    VideoFormat jpegFormat = new VideoFormat(VideoFormat.JPEG_RTP,
                            new Dimension(w, h),
                            format.NOT_SPECIFIED,
                            format.byteArray,
                            frameRate);
                    tracks[i].setFormat(jpegFormat);
                    showConsoleMessage("Video transmitted as:  " + jpegFormat);
                    // Assume succesful
                    programmed = true;
                } else {
                    tracks[i].setEnabled(false);
                }
            }

            if (!programmed) {
                return "Couldn't find video track";
            }

            // Set the output content descriptor to RAW_RTP
            ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.RAW_RTP);
            processor.setContentDescriptor(cd);

            // Realize the processor. This will internally create a flow
            // graph and attempt to create an output datasource for JPEG/RTP
            // video frames.
            result = waitForState(processor, Controller.Realized);
            if (result == false) {
                return "Couldn't realize processor";
            }

            // Set the JPEG quality to .5.
            setJPEGQuality(processor, 0.5f);

            // Get the output data source of the processor
            dataOutput = processor.getDataOutput();
            return null;
        }

        // Creates an RTP transmit data sink. This is the easiest way to create
        // an RTP transmitter. The other way is to use the RTPSessionManager API.
        // Using an RTP session manager gives you more control if you wish to
        // fine tune your transmission and set other parameters.
        private String createTransmitter() {
            // Create a media locator for the RTP data sink.
            // For example:
            //    rtp://129.130.131.132:42050/video
            String rtpURL = "rtp://" + ipAddress + ":" + port + "/video";
            showConsoleMessage(rtpURL);
            MediaLocator outputLocator = new MediaLocator(rtpURL);

            // Create a data sink, open it and start transmission. It will wait
            // for the processor to start sending data. So we need to start the
            // output data source of the processor. We also need to start the
            // processor itself, which is done after this method returns.
            try {
                rtptransmitter = Manager.createDataSink(dataOutput, outputLocator);
                rtptransmitter.open();
                rtptransmitter.start();
                dataOutput.start();
            } catch (MediaException me) {
                return "Couldn't create RTP data sink";
            } catch (IOException ioe) {
                return "Couldn't create RTP data sink";
            }

            return null;
        }

        /**
         * Setting the encoding quality to the specified value on the JPEG encoder.
         * 0.5 is a good default.
         */
        void setJPEGQuality(Player p, float val) {

            javax.media.Control cs[] = (javax.media.Control[]) p.getControls();
            QualityControl qc = null;
            VideoFormat jpegFmt = new VideoFormat(VideoFormat.JPEG);

            // Loop through the controls to find the Quality control for
            // the JPEG encoder.
            for (int i = 0; i < cs.length; i++) {

                if (cs[i] instanceof QualityControl &&
                        cs[i] instanceof Owned) {
                    Object owner = ((Owned) cs[i]).getOwner();

                    // Check to see if the owner is a Codec.
                    // Then check for the output format.
                    if (owner instanceof Codec) {
                        javax.media.Format fmts[] = (javax.media.Format[]) ((Codec) owner).getSupportedOutputFormats(null);
                        for (int j = 0; j < fmts.length; j++) {
                            if (fmts[j].matches(jpegFmt)) {
                                qc = (QualityControl) cs[i];
                                qc.setQuality(val);
                                showConsoleMessage("Setting quality to " +
                                        val + " on " + qc);
                                break;
                            }
                        }
                    }
                    if (qc != null) {
                        break;
                    }
                }
            }
        }
        /****************************************************************
         * Convenience methods to handle processor's state changes.
         ****************************************************************/
        private Integer stateLock = new Integer(0);
        private boolean failed = false;

        Integer getStateLock() {
            return stateLock;
        }

        void setFailed() {
            failed = true;
        }

        private synchronized boolean waitForState(
                Processor p,
                int state) {
            p.addControllerListener(new StateListener());
            failed = false;

            // Call the required method on the processor
            if (state == Processor.Configured) {
                p.configure();
            } else if (state == Processor.Realized) {
                p.realize();
            }

            // Wait until we get an event that confirms the
            // success of the method, or a failure event.
            // See StateListener inner class
            while (p.getState() < state && !failed) {
                synchronized (getStateLock()) {
                    try {
                        getStateLock().wait();
                    } catch (InterruptedException ie) {
                        return false;
                    }
                }
            }

            if (failed) {
                return false;
            } else {
                return true;
            }
        }

        /****************************************************************
         * Inner Classes
         ****************************************************************/
        class StateListener implements ControllerListener {

            public void controllerUpdate(ControllerEvent ce) {

                // If there was an error during configure or
                // realize, the processor will be closed
                if (ce instanceof ControllerClosedEvent) {
                    setFailed();
                }

                // All controller events, send a notification
                // to the waiting thread in waitForState method.
                if (ce instanceof ControllerEvent) {
                    synchronized (getStateLock()) {
                        getStateLock().notifyAll();
                    }
                }
            }
        }

        public void run() {
            //Start the transmission
            String result = this.start();

            // result will be non-null if there was an error. The return
            // value is a String describing the possible error. Print it.
            if (result != null) {
                showConsoleMessage("Run Error : " + result);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton jbStartRoboControlServer;
    private javax.swing.JButton jbStartVideoServer;
    private javax.swing.JComboBox jcbCaptureDevice;
    private javax.swing.JLabel jlServerConnStatus;
    private javax.swing.JTextField jtRoboControlPort;
    private javax.swing.JTextField jtVideoPort;
    public static javax.swing.JTextArea jtaConsole;
    // End of variables declaration//GEN-END:variables
}

