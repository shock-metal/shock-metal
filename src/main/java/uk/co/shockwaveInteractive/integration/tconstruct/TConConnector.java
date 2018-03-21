package uk.co.shockwaveInteractive.integration.tconstruct;

import uk.co.shockwaveInteractive.integration.IntegrationModule;

public class TConConnector extends IntegrationModule
{

	public TConConnector(String MODID, boolean configStatus) {
		super(MODID, configStatus);
	}

	@Override
	public void runPreInit() 
	{
		TConstructIntegration.runPreInit();
	}

	@Override
	public void runInit() 
	{
		TConstructIntegration.runInit();
	}

}
