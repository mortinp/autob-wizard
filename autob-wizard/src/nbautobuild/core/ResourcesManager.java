/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.core;

import nbautobuild.core.dirutils.DirectoriesUtility;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import nbautobuild.core.dirutils.FileIO;
import nbautobuild.core.os.PlatformUtil;

/**
 *
 * @author Martin
 */
public class ResourcesManager {
    
    private static Map propertiesMap = new HashMap();    
    private static Map projectsMap = new HashMap();
    
    //PROPERTIES NAMES
    public static final String SVN_URL = "svn.url";
    public static final String SVN_USER = "svn.username";
    public static final String SVN_PASS = "svn.password";
    public static final String SVN_ESPORT_PATH = "svn.export.path";
    
    public static final String FTP_DIR = "ftpserver.dir";
    public static final String FTP_USER = "ftpserver.user";
    public static final String FTP_PASS = "ftpserver.pass";
    public static final String FTP_EXPORT_FOLDER = "ftp.export.folder";
    public static final String FTP_EXPORT_VERSION = "ftp.export.version";
    
    public static final String PG_PGDUMP_PATH = "postgres.pgdump.exe";
    public static final String PG_SERVER_DIR = "db.server.ip";
    public static final String PG_DB_NAME = "db.name";
    public static final String PG_USER = "db.user";
    public static final String PG_PASS = "db.pass";
    
    public static final String PROJECTS_NAMES_LIST = "projects.names";
    public static final String PROJECTS_ALIAS_LIST = "projects.alias";
    public static final String PROJECT_ALIAS = "project.alias";
    public static final String TIME_STAMP = "time.stamp";

   
    //public static final String OLD_AUTOB_ENV_PATH = "old.autobuild.env.path";
    public static final String AUTOB_ENV_PATH = "autobuild.env.path";
    public static final String SYSTEM_JAVA_HOME = "system.java.home";
    public static final String BUILD_INTERVAL = "build.interval";
    public static final String USER_PROPERTIES_PATH = "user.properties.file";//do not modify (private of netbeans)
    
    
    public static final String LOCAL_EXPORT_FOLDER = "local.export.folder";
    public static final String LOCAL_EXPORT_VERSION = "local.export.version";
    
    
    //OTHER KEYS NAMES
    public static final String COPY_LINK_PATH = "copy-link-path";
    
    //other configurations
    public static String copyLinkPath = null;
    public static boolean netbeansIsInstalled = false;
    public static String netbeansInstallationPath = null;
    
    private static Properties svnProperties = new Properties();
    private static Properties nbbuildProperties = new Properties();
    private static Properties buildexportProperties = new Properties();
    private static Properties pgdumpProperties = new Properties();
    private static Properties autobconfigProperties = new Properties();
    private static Properties envconfigProperties = new Properties();
    
    private static ResourcesManager singleInstance = null;
    private IOSUtilStrategy scriptingStrategy;
    
    private ResourcesManager() {
        scriptingStrategy = PlatformUtil.getOSUtilStrategy();
    }
    
    public static ResourcesManager instance() {
        if(singleInstance == null) {
            singleInstance = new ResourcesManager();
        }
        return singleInstance;
    }
    
    public void setProperty(String propertyName, Object value) {
        propertiesMap.put(propertyName, value);
    }
    
    public void addProject(String alias, String name) {
        projectsMap.put(alias, name);
    }
    
    public Map getPropertiesMap() {
        return propertiesMap;
    }
    
    public static Object getPropertyValue(String propertyName) {
        return propertiesMap.get(propertyName);
    }
    
    public void setNBInstalled(boolean isIt) {
        netbeansIsInstalled = isIt;
    }
    
    public void setNBInstallationPath(String path) {
        netbeansInstallationPath = path;
    }
    
    public void expandResources() throws IOException {
        //ProgressMonitor.instance().setCurrentTask("Updating Autobuild Environment");
        updateAutobEnv();
        
        //shortcut variable
        String autoBuildEnvPath = (String) getPropertyValue(AUTOB_ENV_PATH);
        
        //ProgressMonitor.instance().setCurrentTask("Configuring Autobuild Environment");
        
        envconfigProperties = new Properties();
        envconfigProperties.setProperty(AUTOB_ENV_PATH, (String) getPropertyValue(AUTOB_ENV_PATH));
        envconfigProperties.setProperty(SYSTEM_JAVA_HOME, (String)getPropertyValue(SYSTEM_JAVA_HOME));
        envconfigProperties.setProperty(BUILD_INTERVAL, (String)getPropertyValue(BUILD_INTERVAL));

        svnProperties = new Properties();
        svnProperties.setProperty(SVN_URL, (String)getPropertyValue(SVN_URL));
        svnProperties.setProperty(SVN_USER, (String)getPropertyValue(SVN_USER));
        svnProperties.setProperty(SVN_PASS, (String)getPropertyValue(SVN_PASS));
        svnProperties.setProperty(SVN_ESPORT_PATH, (String)getPropertyValue(SVN_ESPORT_PATH));
        
        pgdumpProperties = new Properties();
        pgdumpProperties.setProperty(PG_PGDUMP_PATH, (String)getPropertyValue(PG_PGDUMP_PATH));
        pgdumpProperties.setProperty(PG_SERVER_DIR, (String)getPropertyValue(PG_SERVER_DIR));
        pgdumpProperties.setProperty(PG_DB_NAME, (String)getPropertyValue(PG_DB_NAME));
        pgdumpProperties.setProperty(PG_USER, (String)getPropertyValue(PG_USER));
        pgdumpProperties.setProperty(PG_PASS, (String)getPropertyValue(PG_PASS));

        nbbuildProperties = new Properties();
        nbbuildProperties.setProperty(AUTOB_ENV_PATH, (String)getPropertyValue(AUTOB_ENV_PATH));
        nbbuildProperties.setProperty(USER_PROPERTIES_PATH, "${autobuild.env.path}/nbconfig/build.properties");

        buildexportProperties = new Properties();
        buildexportProperties.setProperty(TIME_STAMP, (String)getPropertyValue(TIME_STAMP));
        buildexportProperties.setProperty(LOCAL_EXPORT_FOLDER, (String)getPropertyValue(LOCAL_EXPORT_FOLDER));
        buildexportProperties.setProperty(LOCAL_EXPORT_VERSION, "${" + LOCAL_EXPORT_FOLDER +"}/${" + PROJECT_ALIAS + "}/${" + PROJECT_ALIAS + "}(${" + TIME_STAMP + "})");
        buildexportProperties.setProperty(FTP_DIR, (String)getPropertyValue(FTP_DIR));
        buildexportProperties.setProperty(FTP_USER, (String)getPropertyValue(FTP_USER));
        buildexportProperties.setProperty(FTP_PASS, (String)getPropertyValue(FTP_PASS));
        buildexportProperties.setProperty(FTP_EXPORT_FOLDER, (String)getPropertyValue(FTP_EXPORT_FOLDER));
        buildexportProperties.setProperty(FTP_EXPORT_VERSION, "${" + FTP_EXPORT_FOLDER + "}/${" + PROJECT_ALIAS + "}/${" + PROJECT_ALIAS + "}(${" + TIME_STAMP + "})");
        
        String strProjectsNames = "";
        String strProjectsAlias = "";
        String separator = "";
        for (Object key : projectsMap.keySet()) {
            buildexportProperties.setProperty(PROJECT_ALIAS, (String)key);
            strProjectsNames += separator + projectsMap.get(key);
            strProjectsAlias += separator + (String)key;
            separator = ";";

            File buildexportFile = new File(autoBuildEnvPath + "/resources/custom/" + (String) key + "-build-export.properties");
            OutputStream buildexportFileOut = new FileOutputStream(buildexportFile);
            buildexportProperties.store(buildexportFileOut, null);
            buildexportFileOut.close();
        }
        autobconfigProperties = new Properties();
        autobconfigProperties.setProperty(PROJECTS_NAMES_LIST, strProjectsNames);
        autobconfigProperties.setProperty(PROJECTS_ALIAS_LIST, strProjectsAlias);
        
        File nbbuildFile = new File(autoBuildEnvPath + "/resources/custom/nbbuild.properties");
        OutputStream nbbuildFileOut = new FileOutputStream(nbbuildFile);
        nbbuildProperties.store(nbbuildFileOut, null);
        nbbuildFileOut.close();

        File svnFile = new File(autoBuildEnvPath + "/resources/svn/svn.properties");
        OutputStream svnFileOut = new FileOutputStream(svnFile);
        svnProperties.store(svnFileOut, null);
        svnFileOut.close();
        
        File pgdumpFile = new File(autoBuildEnvPath + "/resources/custom/postgres.properties");
        OutputStream pgdumpFileOut = new FileOutputStream(pgdumpFile);
        pgdumpProperties.store(pgdumpFileOut, null);
        pgdumpFileOut.close();

        File autobconfigFile = new File(autoBuildEnvPath + "/resources/autob-config.properties");
        OutputStream autobconfigFileOut = new FileOutputStream(autobconfigFile);
        autobconfigProperties.store(autobconfigFileOut, null);
        autobconfigFileOut.close();
        
        File envconfigFile = new File(autoBuildEnvPath + "/resources/conf/env-config.properties");
        OutputStream envconfigFileOut = new FileOutputStream(envconfigFile);
        envconfigProperties.store(envconfigFileOut, null);
        envconfigFileOut.close();
        
        //ProgressMonitor.instance().setCurrentTask("Creating Shortcuts");
        createShortcut((String)getPropertyValue(COPY_LINK_PATH));
    }
    
    private void updateAutobEnv() throws IOException {
        String autoBuildEnvPath = DirectoriesUtility.getJarFolderPath();
        String javaHomePath = DirectoriesUtility.getJavaHomePath();
            
        setProperty(AUTOB_ENV_PATH, autoBuildEnvPath);
        setProperty(SYSTEM_JAVA_HOME, javaHomePath);
        
        File envconfigFile = new File(autoBuildEnvPath + "/resources/conf/env-config.properties");
        InputStream envconfigFileIn = new FileInputStream(envconfigFile);
        envconfigProperties.load(envconfigFileIn);
        envconfigFileIn.close();
        
        if(netbeansIsInstalled) {
            NBDependenciesManager.prepareNBEnvironment(netbeansInstallationPath, autoBuildEnvPath);
        } else {
            String oldAutoBuildEnvPath = envconfigProperties.getProperty(AUTOB_ENV_PATH);
            FileIO.replaceStringInFile(autoBuildEnvPath + "/nbconfig/build.properties", oldAutoBuildEnvPath, autoBuildEnvPath);
        }   
    }
    
    private void createShortcut(String destPath) throws IOException {
        scriptingStrategy.createShortcut(destPath);
    }
    
    public void executeAutobuild() throws IOException {
        scriptingStrategy.executeAutobuild();
    }
}
