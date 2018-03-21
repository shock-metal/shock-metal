package uk.co.shockwaveInteractive.integration.thermalexpansion;

import uk.co.shockwaveInteractive.integration.IntegrationModule;

public class ThermalExpanConnector extends IntegrationModule {

	public ThermalExpanConnector(String MODID, boolean configStatus) {
		super(MODID, configStatus);
	}


	@Override
	public void runPreInit() 
	{
		
	}

	@Override
	public void runInit() 
	{
		ThermalExpansionIntegration.runInit();
	}

}
