package uk.co.shockwaveInteractive.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraftforge.fml.common.Loader;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.integration.tconstruct.TConConnector;
import uk.co.shockwaveInteractive.integration.tconstruct.TConstructIntegration;
import uk.co.shockwaveInteractive.integration.thermalexpansion.ThermalExpanConnector;
import uk.co.shockwaveInteractive.util.config.ShockMetalConfiguration;



public class IntegrationHandler 
{
	// Holds all current Mod modules - Use connectors to avoid minecaft crying about missing imports 
	public static HashMap<String, IntegrationModule> modules = new HashMap<String, IntegrationModule>();
	
	public static HashMap<String, Boolean>	modStatus = new HashMap<>();
	
	static
	{
		modules.put("tconstruct", new TConConnector("tconstruct", ShockMetalConfiguration.tinkersIntegration));
		modules.put("thermalexpansion", new ThermalExpanConnector("thermalexpansion", ShockMetalConfiguration.thermalExpansionIntegration));
	}
	
	
	
	public static void checkInstalled()
	{
		// Gets the Mod ID and whether it is dsiabled or not
		for (IntegrationModule m : modules.values())
		{
			modStatus.put(m.MODID, m.getDisabled());
		}
		
		// Output which mod integrations have been activated
		for (Map.Entry<String, Boolean> entry : modStatus.entrySet())
		{
			if(entry.getValue().equals(true))
			{
				ShockMetalMain.logger.info("Mod: " + entry.getKey() + " integration is active!");
			}
			else
			{
				ShockMetalMain.logger.info("Mod: " + entry.getKey() + " integration is not active :(");
			}
		}
			
	}
	
	// Run each active integrations pre-init
	public static void runPreInit()
	{
		for(Entry<String, IntegrationModule> entry : modules.entrySet())
		{
			if(modStatus.get(entry.getKey()))
			{
				entry.getValue().runPreInit();
			}
		}
		
	}
	
	// Run each active integrations init
	public static void runInit()
	{
		for(Entry<String, IntegrationModule> entry : modules.entrySet())
		{
			if(modStatus.get(entry.getKey()))
			{
				entry.getValue().runInit();
			}
		}
	}
}
