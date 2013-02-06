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
public class PostgresDumpConfigDescriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "POSTGRES_DUMP_CONFIG";
    
    public PostgresDumpConfigDescriptor() {
        super(IDENTIFIER, new PostgresDumpConfig());
    }
    
    @Override
    public Object getNextPanelDescriptor() {
        return ProjectsConfigDescriptor.IDENTIFIER;
    }
    
    @Override
    public Object getBackPanelDescriptor() {
        return DeploymentConfigDescriptor.IDENTIFIER;
    }
    
    @Override
    public void displayingPanel() {
        ((PostgresDumpConfig)getPanelComponent()).aboutToDisplay();
    }
    
    @Override
    public void aboutToGoNext() {
        ((PostgresDumpConfig)getPanelComponent()).doGetData();
    }  
}
