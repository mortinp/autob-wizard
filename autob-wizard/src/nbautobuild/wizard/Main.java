package nbautobuild.wizard;

import com.nexes.wizard.*;
import nbautobuild.wizard.pages.DeploymentConfigDescriptor;
import nbautobuild.wizard.pages.FinishScreenDescriptor;
import nbautobuild.wizard.pages.LocalResourcesConfigDescriptor;
import nbautobuild.wizard.pages.PostgresDumpConfigDescriptor;
import nbautobuild.wizard.pages.ProjectsConfigDescriptor;
import nbautobuild.wizard.pages.SVNRepoConfigDescriptor;
import nbautobuild.wizard.pages.WelcomeScreenDescriptor;

public class Main {
    
    public static void main(String[] args) {
        
        Wizard wizard = new Wizard();
        wizard.getDialog().setTitle("Netbeans Project Autobuild Configuration");
        
        WizardPanelDescriptor pageWelcomScreen = new WelcomeScreenDescriptor();
        wizard.registerWizardPanel(WelcomeScreenDescriptor.IDENTIFIER, pageWelcomScreen);
        
        WizardPanelDescriptor pageSVNRepoConfig = new SVNRepoConfigDescriptor();
        wizard.registerWizardPanel(SVNRepoConfigDescriptor.IDENTIFIER, pageSVNRepoConfig);

        WizardPanelDescriptor pageDeploymentConfig = new DeploymentConfigDescriptor();
        wizard.registerWizardPanel(DeploymentConfigDescriptor.IDENTIFIER, pageDeploymentConfig);
        
        WizardPanelDescriptor pgdumpDeploymentConfig = new PostgresDumpConfigDescriptor();
        wizard.registerWizardPanel(PostgresDumpConfigDescriptor.IDENTIFIER, pgdumpDeploymentConfig);
        
        WizardPanelDescriptor pageProjectsConfig = new ProjectsConfigDescriptor();
        wizard.registerWizardPanel(ProjectsConfigDescriptor.IDENTIFIER, pageProjectsConfig);
        
        WizardPanelDescriptor pageLocalResConfig = new LocalResourcesConfigDescriptor();
        wizard.registerWizardPanel(LocalResourcesConfigDescriptor.IDENTIFIER, pageLocalResConfig);
        
        WizardPanelDescriptor pageFinishConfig = new FinishScreenDescriptor();
        wizard.registerWizardPanel(FinishScreenDescriptor.IDENTIFIER, pageFinishConfig);
        
        wizard.setCurrentPanel(WelcomeScreenDescriptor.IDENTIFIER);
        
        int ret = wizard.showModalDialog();
        
        System.exit(ret);
    }
    
}
