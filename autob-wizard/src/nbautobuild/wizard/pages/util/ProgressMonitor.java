/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.wizard.pages.util;

import nbautobuild.wizard.pages.FinishScreen;

/**
 *
 * @author mproenza
 */
public class ProgressMonitor {
    private static ProgressMonitor singleInstance = null;
    
    public static ProgressMonitor instance() {
        if(singleInstance == null) {
            singleInstance = new ProgressMonitor();
        }
        return singleInstance;
    }
    
    public void setCurrentTask(String taskName) {
        FinishScreen.setCurrentTask(taskName);
    }
    public void addProgress(int ammount) {
        FinishScreen.addProgress(ammount);
    }
}
