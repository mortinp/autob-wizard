/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.core.dirutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import nbautobuild.core.os.PlatformUtil;

/**
 *
 * @author Maylen
 */
public class PathPropertiesReaper {
    
    public static List getFilesToCopy(String filePath) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        InputStream file = new FileInputStream(filePath);
        properties.load(file);
        
        List<String> paths = new ArrayList<String>();
        Enumeration props = properties.propertyNames();
        while (props.hasMoreElements()) {
            String propName = (String) props.nextElement();
            paths.addAll(reapToFilePaths(properties.getProperty(propName)));
        }
        
        return paths;
    }
    
    private static List reapToFilePaths(String strPaths) {
        List<String> paths = new ArrayList<String>();
        if(strPaths != null && !strPaths.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(strPaths, PlatformUtil.PROPERTIES_SEPARATOR_CHAR);
            while (tokenizer.hasMoreElements()) {
                String path = (String)tokenizer.nextElement();
                File file = new File(path);
                if(file.exists() && file.isFile()) {
                    paths.add(path);
                } else {
                    System.out.println(path + " is not a file");
                }
            }
        }
        return paths;
    }
    
    /*public static void main(String[] av) {
        try {
            PathPropertiesReaper.getFilesToCopy("C:/Users/Maylen/Desktop/build.properties");
        } catch (FileNotFoundException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }*/
}
