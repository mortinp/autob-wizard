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
public class FinishScreenDescriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "FINISH_SCREEN";
    
    public FinishScreenDescriptor() {
        super(IDENTIFIER, new FinishScreen());
    }
    
    @Override
    public Object getNextPanelDescriptor() {
        return FINISH;
    }
    
    @Override
    public Object getBackPanelDescriptor() {
        return LocalResourcesConfigDescriptor.IDENTIFIER;
    }
    
    @Override
    public void onFinish() {
        FinishScreen.applyConfiguration();
    }
}
