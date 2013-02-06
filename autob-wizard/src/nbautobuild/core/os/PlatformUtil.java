/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.core.os;

import nbautobuild.core.IOSUtilStrategy;
import nbautobuild.core.UnixUtilStrategy;
import nbautobuild.core.WindowsUtilStrategy;

/**
 *
 * @author Maylen
 */
public class PlatformUtil {
    
    public static String PROPERTIES_SEPARATOR_CHAR = null;
    public static Platform platform = Platform.UNKNOWN;
    
    public static enum Platform {
        UNIX,
        WINDOWS,
        UNKNOWN
    }
    
    static {
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0) {
            platform = Platform.UNIX;
            PROPERTIES_SEPARATOR_CHAR = ":";
        } else if(osName.indexOf("win") >= 0) {
            platform = Platform.WINDOWS;
            PROPERTIES_SEPARATOR_CHAR = ";";
        }   
    }
    
    public static IOSUtilStrategy getOSUtilStrategy() {
        if(platform == Platform.UNIX)
            return new UnixUtilStrategy();
        else if(platform == Platform.WINDOWS)
            return new WindowsUtilStrategy();
        return null;
    }
    
}
