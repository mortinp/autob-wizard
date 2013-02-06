/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nbautobuild.wizard.pages;

import com.nexes.wizard.WizardPanelDescriptor;

/**
 *
 * @author Martin
 */
public class ProjectsConfigDescriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "PROJECTS_CONFIG";
    
    public ProjectsConfigDescriptor() {
        super(IDENTIFIER, new ProjectsConfig());
    }
    
    @Override
    public Object getNextPanelDescriptor() {
        return LocalResourcesConfigDescriptor.IDENTIFIER;
    }
    
    @Override
    public Object getBackPanelDescriptor() {
        return PostgresDumpConfigDescriptor.IDENTIFIER;
    }
    
    @Override
    public void displayingPanel() {
        ((ProjectsConfig)getPanelComponent()).aboutToDisplay();
    }
    
    @Override
    public void aboutToGoNext() {
        ((ProjectsConfig)getPanelComponent()).doGetData();
    }
}
