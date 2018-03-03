package uk.co.shockwaveInteractive.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.objects.blocks.item.ItemBlockVarients;
import uk.co.shockwaveInteractive.util.handlers.EnumHandler;
import uk.co.shockwaveInteractive.util.interfaces.IHasModel;
import uk.co.shockwaveInteractive.util.interfaces.IMetaName;

public class BlockOres extends Block implements IHasModel, IMetaName
{
	public static final PropertyEnum<EnumHandler.EnumType> VARIANT = PropertyEnum.<EnumHandler.EnumType>create("variant", EnumHandler.EnumType.class);
	
	private String name;
	private String dimension;
	
	
	public BlockOres(String name, String dimension, String effectiveTool, int miningLevel) 
	{
		super(Material.ROCK);
		setHarvestLevel(effectiveTool, miningLevel);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ShockMetalMain.shockmetaltab);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumHandler.EnumType.SHOCKMETAL));
		
		this.name = name;
		this.dimension = dimension;
		
		if (VARIANT.getName(EnumHandler.EnumType.SHOCKMETAL).equals("shockmetal"))
		{
			this.setLightLevel(3);
		}
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlockVarients(this).setRegistryName(this.getRegistryName()));
		
		
	}
	
	@Override
	public int damageDropped(IBlockState state) 
	{
		return ((EnumHandler.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return ((EnumHandler.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return this.getDefaultState().withProperty(VARIANT, EnumHandler.EnumType.byMetadtata(meta));
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) 
	{
		for(EnumHandler.EnumType varient : EnumHandler.EnumType.values())
		{
			items.add(new ItemStack(this, 1, varient.getMeta()));
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	@Override
	public String getSpecialName(ItemStack stack) 
	{
		return EnumHandler.EnumType.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	public void registerModels() {
		
		for(int i = 0; i < EnumHandler.EnumType.values().length; i++)
		{
			ShockMetalMain.proxy.registerVarientRenderer(Item.getItemFromBlock(this), i, "ore_" + this.dimension + "_" + EnumHandler.EnumType.values()[i].getName(), "inventory");
		}
		
	}

}
