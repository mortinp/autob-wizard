/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.core;

import java.io.IOException;

/**
 *
 * @author Maylen
 */
public class UnixUtilStrategy extends GeneralOSUtilStrategy {
    
    @Override
    protected String getEntryScriptContent(String targetScript) {
        return "#!/bin/bash" + "\n"
             //+ "cd \"" + ResourcesManager.getPropertyValue(ResourcesManager.AUTOB_ENV_PATH) + "/resources\"" + "\n"
             + "bash \"" + targetScript + "\"";
    }
    
    @Override
    protected String getScriptsExtension() {
        return "";//none
    }

    @Override
    protected void createLink(String targetFile, String destFile) throws IOException {
        Runtime.getRuntime().exec(new String[] {"ln", "-s", targetFile, destFile});
    }
    
    @Override
    public void executeScript(String scriptFile) throws IOException {
        Runtime.getRuntime().exec("bash \"" + scriptFile + "\"");
    }
}
