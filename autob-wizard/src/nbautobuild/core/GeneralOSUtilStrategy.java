/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.core;

import java.io.IOException;
import nbautobuild.core.dirutils.DirectoriesUtility;
import nbautobuild.core.dirutils.FileIO;

/**
 *
 * @author martin
 */
public abstract class GeneralOSUtilStrategy implements IOSUtilStrategy {

    @Override
    public final void createShortcut(String destPath) throws IOException {
        String autobEnvPath = DirectoriesUtility.formatPath((String)ResourcesManager.getPropertyValue(ResourcesManager.AUTOB_ENV_PATH));
        String autobScriptPath = autobEnvPath + "/resources/" + getAutobScriptName() + getScriptsExtension();
        String autobScriptEntryPath = autobEnvPath + "/resources/" + toEntryScriptName(getAutobScriptName()) + getScriptsExtension();
        String launchDaemonScriptPath = autobEnvPath + "/resources/" + getLaunchDaemonScriptName() + getScriptsExtension();
        String launchDaemonScriptEntryPath = autobEnvPath + "/resources/" + toEntryScriptName(getLaunchDaemonScriptName()) + getScriptsExtension();
        
        //create autob script entry and its link
        FileIO.writeFile(autobScriptEntryPath, getEntryScriptContent(autobScriptPath));
        createLink(autobScriptEntryPath, destPath + "/autob" + getScriptsExtension());
        
        //create daemon script entry and its link
        FileIO.writeFile(launchDaemonScriptEntryPath, getEntryScriptContent(launchDaemonScriptPath));
        createLink(launchDaemonScriptEntryPath, destPath + "/daemon" + getScriptsExtension());
    }
    
    @Override
    public final void executeAutobuild() throws IOException {
        String autobEnvPath = DirectoriesUtility.formatPath((String)ResourcesManager.getPropertyValue(ResourcesManager.AUTOB_ENV_PATH));
        String autobScriptPath = autobEnvPath + "/resources/" + getAutobScriptName() + getScriptsExtension();
        executeScript(autobScriptPath);
    }
    
    private String getAutobScriptName() {
        return "launch-autob";
    }
    
    private String getLaunchDaemonScriptName() {
        return "launch-daemon";
    }
    
    private String toEntryScriptName(String targetScriptName) {
        /*if(targetScriptName.contains(".")) targetScriptName = targetScriptName.substring(0, targetScriptName.lastIndexOf(".")) + "-entry" + targetScriptName.substring(targetScriptName.lastIndexOf("."));
        else targetScriptName = targetScriptName + "-entry";*/
        targetScriptName = targetScriptName + "-entry"/* + getScriptsExtension()*/;
        return targetScriptName;
    }
    
    protected abstract void executeScript(String scriptFile) throws IOException;
    protected abstract String getEntryScriptContent(String targetScript);
    protected abstract String getScriptsExtension();
    protected abstract void createLink(String targetFile, String destFile) throws IOException;
}
