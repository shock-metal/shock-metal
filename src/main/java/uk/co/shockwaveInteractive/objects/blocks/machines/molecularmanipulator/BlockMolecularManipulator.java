package uk.co.shockwaveInteractive.objects.blocks.machines.molecularmanipulator;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import uk.co.shockwaveInteractive.ShockMetalMain;
import uk.co.shockwaveInteractive.init.BlockInit;
import uk.co.shockwaveInteractive.objects.blocks.BlockBase;
import uk.co.shockwaveInteractive.util.Reference;

public class BlockMolecularManipulator extends BlockBase implements ITileEntityProvider {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockMolecularManipulator(String name, Material material, String effectiveTool)
	{
		super(name, material, effectiveTool, 3, 10);
		setSoundType(SoundType.METAL);
		setResistance(10.0f);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add("Atoms and molecules now answer to you!, Allows for various transformations of materials");
		super.addInformation(stack, player, tooltip, advanced);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		return Item.getItemFromBlock(BlockInit.MOLECULAR_MANIPULATOR);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		
		return new ItemStack(BlockInit.MOLECULAR_MANIPULATOR);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(!worldIn.isRemote)
		{
			playerIn.openGui(ShockMetalMain.instance, Reference.GUI_MOLECULAR_MANIPULATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) 
	{
		if (!worldIn.isRemote) 
        {
            IBlockState north = worldIn.getBlockState(pos.north());
            IBlockState south = worldIn.getBlockState(pos.south());
            IBlockState west = worldIn.getBlockState(pos.west());
            IBlockState east = worldIn.getBlockState(pos.east());
            EnumFacing face = (EnumFacing)state.getValue(FACING);

            if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
            else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.NORTH;
            else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.EAST;
            else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.WEST;
            worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
        }
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active)
		{
			worldIn.setBlockState(pos, BlockInit.MOLECULAR_MANIPULATOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, true), 3);
		}
		else 
		{
			worldIn.setBlockState(pos, BlockInit.MOLECULAR_MANIPULATOR.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, false), 3);
		}
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) 
	{
		return new TileEntityMolecularManipulator();
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{	
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
	{
		TileEntityMolecularManipulator tileentity = (TileEntityMolecularManipulator)worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, tileentity);
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) 
	{
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) 
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {ACTIVE, FACING});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		EnumFacing facing = EnumFacing.getFront(meta);
		
		if(facing.getAxis() == EnumFacing.Axis.Y)
		{
			facing = EnumFacing.NORTH;
		}
		
		return this.getDefaultState().withProperty(FACING, facing);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
}
