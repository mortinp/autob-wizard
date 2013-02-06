/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import nbautobuild.core.dirutils.DirectoriesUtility;
import java.io.IOException;
import nbautobuild.core.dirutils.FileIO;
import nbautobuild.core.thread.utils.StreamGobbler;

/**
 *
 * @author Maylen
 */
public class WindowsUtilStrategy extends GeneralOSUtilStrategy {
    
    @Override
    protected String getEntryScriptContent(String targetScript) {
        return "@echo off" + "\r\n"
             + "call \"" + targetScript + "\"";
    }
    
    @Override
    protected String getScriptsExtension() {
        return ".bat";
    }

    @Override
    protected void createLink(String targetFile, String destFile) throws IOException {
        String autobEnvPath = DirectoriesUtility.formatPath((String)ResourcesManager.getPropertyValue(ResourcesManager.AUTOB_ENV_PATH));
        
        //it looks like in Windows we can't execute commands directly through Runtime.getRuntime().exec(), so we need to create a batch file to do it
        String targetLnFile = autobEnvPath + "/resources/wlnk.bat";
        FileIO.writeTempFile(targetLnFile, 
                            "@echo off" + "\r\n"
                          + "set XXMKLINK_PATH=" + autobEnvPath + "/resources/3p/xxmklink" + "\r\n"
                          + "set PATH=%PATH%;%XXMKLINK_PATH%" + "\r\n"
                          + "xxmklink \""+ destFile + "\" \"" + targetFile + "\" "
                          + "\"\" \"\" \"\" 1 \"" + autobEnvPath + "/resources/autob.ico\"");
        Process p = Runtime.getRuntime().exec(autobEnvPath + "/resources/wlnk.bat");
        StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");            
        StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
        errorGobbler.start();
        outputGobbler.start();
        try {
            int pResult = p.waitFor();
        } catch (InterruptedException ex) {
            Logger.getLogger(WindowsUtilStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void executeScript(String scriptFile) throws IOException {
        Runtime.getRuntime().exec("\"" + scriptFile + "\"");
    }
}
