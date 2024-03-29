package nbautobuild.wizard;

import com.nexes.wizard.*;

public class TestPanel3Descriptor extends WizardPanelDescriptor {
    
    public static final String IDENTIFIER = "SERVER_CONNECT_PANEL";
    
    TestPanel3 panel3;
    
    public TestPanel3Descriptor() {
        
        panel3 = new TestPanel3();
        setPanelDescriptorIdentifier(IDENTIFIER);
        setPanelComponent(panel3);
        
    }

    @Override
    public Object getNextPanelDescriptor() {
        return FINISH;
    }
    
    @Override
    public Object getBackPanelDescriptor() {
        return TestPanel2Descriptor.IDENTIFIER;
    }
    
    
    @Override
    public void aboutToDisplayPanel() {
        
        panel3.setProgressValue(0);
        panel3.setProgressText("Connecting to Server...");

        getWizard().setNextFinishButtonEnabled(false);
        getWizard().setBackButtonEnabled(false);
        
    }
    
    @Override
    public void displayingPanel() {

        Thread t = new Thread() {

            @Override
            public void run() {

                try {
                    Thread.sleep(2000);
                    panel3.setProgressValue(25);
                    panel3.setProgressText("Server Connection Established");
                    Thread.sleep(500);
                    panel3.setProgressValue(50);
                    panel3.setProgressText("Transmitting Data...");
                    Thread.sleep(3000);
                    panel3.setProgressValue(75);
                    panel3.setProgressText("Receiving Acknowledgement...");
                    Thread.sleep(1000);
                    panel3.setProgressValue(100);
                    panel3.setProgressText("Data Successfully Transmitted");

                    getWizard().setNextFinishButtonEnabled(true);
                    getWizard().setBackButtonEnabled(true);

                } catch (InterruptedException e) {
                    
                    panel3.setProgressValue(0);
                    panel3.setProgressText("An Error Has Occurred");
                    
                    getWizard().setBackButtonEnabled(true);
                }

            }
        };

        t.start();
    }
    
    @Override
    public void aboutToHidePanel() {
        //  Can do something here, but we've chosen not not.
    }    
            
}
