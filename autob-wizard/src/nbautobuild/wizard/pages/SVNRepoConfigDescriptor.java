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
public class SVNRepoConfigDescriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "SVN_REPO_CONFIG";
    
    public SVNRepoConfigDescriptor() {
        super(IDENTIFIER, new SVNRepoConfig());
    }
    
    @Override
    public Object getNextPanelDescriptor() {
        return DeploymentConfigDescriptor.IDENTIFIER;
    }
    
    @Override
    public void displayingPanel() {
        ((SVNRepoConfig)getPanelComponent()).aboutToDisplay();
    }
    
    @Override
    public void aboutToGoNext() {
        ((SVNRepoConfig)getPanelComponent()).doGetData();
    }  
}
