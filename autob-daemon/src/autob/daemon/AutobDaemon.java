/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package autob.daemon;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class AutobDaemon {
    
    private static String scriptPath = "";
    private static int interval = 1;//in minutes

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length != 0) scriptPath = args[0]; else return;
        if(args.length > 1) interval = Integer.parseInt(args[1]);

        Thread t = new Thread("Main Daemon Thread") {
            @Override
            public void run() {
                while(true) {
                    try {
                        //run script and sleep while it executes
                        runScript();
                        sleep(interval*60000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(AutobDaemon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        t.start();
    }
    
    public static void runScript() {
        Thread t = new Thread("Autob Task Thread") {
            @Override
            public void run() {
                try {
                    System.out.println("Starting execution: " + scriptPath + "\n");
                    
                    Process p = Runtime.getRuntime().exec(new String[] {scriptPath});
                    
                    //keep the thread alive (trick: read from its error/input streams until end)
                    StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");            
                    StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
                    errorGobbler.start();
                    outputGobbler.start();
                    
                    long startTime = System.currentTimeMillis();
                    int pResult = p.waitFor();
                    long endTime = System.currentTimeMillis();
                    int elapsedTime = (int) ((endTime - startTime)/60000);//in minutes
                    
                    System.out.println("\nExit value: " + pResult);
                    System.out.println("Autobuild will start again in approximately " + (interval - elapsedTime) + " minute(s)...");

                } catch (InterruptedException ex) {
                    Logger.getLogger(AutobDaemon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AutobDaemon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        t.start();
    }
}
