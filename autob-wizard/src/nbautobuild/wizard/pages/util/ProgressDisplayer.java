/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.wizard.pages.util;

/**
 *
 * @author mproenza
 */
public interface ProgressDisplayer {
    public void setCurrentTask(String taskName);
    public void addProgress(int ammount);
}
