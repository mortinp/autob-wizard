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
public interface IOSUtilStrategy {
    
    public void createShortcut(String destPath) throws IOException;
    
    public void executeAutobuild() throws IOException;
}
