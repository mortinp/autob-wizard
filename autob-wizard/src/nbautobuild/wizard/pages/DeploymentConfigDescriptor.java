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
public class DeploymentConfigDescriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "DEPLOYMENT_CONFIG";
    
    public DeploymentConfigDescriptor() {
        super(IDENTIFIER, new DeploymentConfig());
    }
    
    @Override
    public Object getNextPanelDescriptor() {
        return PostgresDumpConfigDescriptor.IDENTIFIER;
    }
    
    @Override
    public Object getBackPanelDescriptor() {
        return SVNRepoConfigDescriptor.IDENTIFIER;
    }
    
    @Override
    public void displayingPanel() {
        ((DeploymentConfig)getPanelComponent()).aboutToDisplay();
    }
    
    @Override
    public void aboutToGoNext() {
        ((DeploymentConfig)getPanelComponent()).doGetData();
    }
}
