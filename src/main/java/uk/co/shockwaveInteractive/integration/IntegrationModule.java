//package uk.co.shockwaveinteractive.integration;
//
//import java.util.LinkedHashSet;
//import java.util.Set;
//
//import net.minecraftforge.fml.common.Loader;
//
//public abstract class IntegrationModule
//{
//	public String MODID;
//	private boolean configStatus;
//	private boolean disabled;
//
//	public IntegrationModule(String MODID)
//	{
//		this.MODID = MODID;
//		disabled = checkIfEnabled();
//	}
//
//	public IntegrationModule(String MODID, boolean configStatus)
//	{
//		this.MODID = MODID;
//		this.configStatus = configStatus;
//		disabled = checkIfEnabled();
//	}
//
//	private boolean checkIfEnabled()
//	{
//		if(!Loader.isModLoaded(MODID) || !configStatus)
//		{
//			return false;
//		}
//
//		return true;
//	}
//
//	public boolean getDisabled()
//	{
//		return disabled;
//	}
//
//	public abstract void runPreInit();
//
//	public abstract void runInit();
//
//}
