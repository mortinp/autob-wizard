/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.core;

import java.io.FileInputStream;
import nbautobuild.core.dirutils.PathPropertiesReaper;
import nbautobuild.core.dirutils.FileIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import nbautobuild.core.dirutils.DirectoriesUtility;

/**
 *
 * @author Maylen
 */
public class NBDependenciesManager {
    public static void prepareNBEnvironment(String NBInstallPath, String autobEnvPath) throws FileNotFoundException, IOException {
        //find Netbeans current user configuration
        Properties properties = new Properties();
        InputStream file = new FileInputStream(NBInstallPath + "/etc/netbeans.conf");
        properties.load(file);
        String nbUserDirectory = DirectoriesUtility.formatPath(properties.getProperty("netbeans_default_userdir"));
        nbUserDirectory = nbUserDirectory.replace("${HOME}", DirectoriesUtility.getUserDirectory());
        
        //copy Netbeans build.properties to autob environment and standardize paths
        FileIO.forceCopyFile(nbUserDirectory + "/build.properties", autobEnvPath + "/nbconfig/build.properties");        
        FileIO.replaceStringInFile(autobEnvPath + "/nbconfig/build.properties", "\\\\", "/");
        
        //copy Netbeans custom libraries to autob environment
        List paths = PathPropertiesReaper.getFilesToCopy(autobEnvPath + "/nbconfig/build.properties");
        for (Object path : paths) {
            cloneFileToSameDirectoryStructure(NBInstallPath, autobEnvPath + "/nbconfig/nb-depend", (String) path);
        }
        
        //adapt our build.properties to autob environment
        FileIO.replaceStringInFile(autobEnvPath + "/nbconfig/build.properties", NBInstallPath, autobEnvPath + "/nbconfig/nb-depend");
    }
    
    private static void cloneFileToSameDirectoryStructure(String sourceFolder, String targetFolder, String sourceFilePath) throws FileNotFoundException, IOException {
        String relSourcePath = sourceFilePath.replace(sourceFolder, "");
        FileIO.forceCopyFile(sourceFilePath, targetFolder + relSourcePath);
    }
    
    /*public static void main(String[] av) {
        try {
            NBDependenciesManager.prepareNBEnvironment("C:/Program Files/NetBeans 7.0", "D:/USUARIOS/Martin/pros/proyectos/nbautobuild-wizard");
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }*/
}
