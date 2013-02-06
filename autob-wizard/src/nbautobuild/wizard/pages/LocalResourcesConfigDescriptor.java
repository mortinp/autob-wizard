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
public class LocalResourcesConfigDescriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "LOCAL_RESOURCES_CONFIG";
    
    public LocalResourcesConfigDescriptor() {
        super(IDENTIFIER, new LocalResourcesConfig());
    }
    
    @Override
    public Object getNextPanelDescriptor() {
        return FinishScreenDescriptor.IDENTIFIER;
    }
    
    @Override
    public Object getBackPanelDescriptor() {
        return ProjectsConfigDescriptor.IDENTIFIER;
    }
    
    @Override
    public void displayingPanel() {
        ((LocalResourcesConfig)getPanelComponent()).aboutToDisplay();
    }
    
    @Override
    public void aboutToGoNext() {
         ((LocalResourcesConfig)getPanelComponent()).doGetData();
    }
}
