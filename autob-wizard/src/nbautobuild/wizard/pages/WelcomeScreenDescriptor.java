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
public class WelcomeScreenDescriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "WELCOME_SCREEN";
    
    public WelcomeScreenDescriptor() {
        super(IDENTIFIER, new WelcomeScreen());
    }
    
    @Override
    public Object getNextPanelDescriptor() {
        return SVNRepoConfigDescriptor.IDENTIFIER;
    }
}
