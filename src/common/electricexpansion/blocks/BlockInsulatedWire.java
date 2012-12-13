package electricexpansion.blocks;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import universalelectricity.prefab.BlockConductor;
import universalelectricity.prefab.UETab;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import electricexpansion.EECommonProxy;
import electricexpansion.cables.TileEntityInsulatedWire;

public class BlockInsulatedWire extends BlockConductor 
{
	public BlockInsulatedWire(int id, int meta) 
	{
		super(id, Material.cloth);
		this.setBlockName("InsulatedWire");
		this.setStepSound(soundClothFootstep);
		this.setResistance(0.2F);
		this.setBlockBounds(0.30F, 0.30F, 0.30F, 0.70F, 0.70F, 0.70F);
		this.setRequiresSelfNotify();
		this.setCreativeTab(UETab.INSTANCE);
	}

	@Override
	public boolean isOpaqueCube()
	{return false;}

	@Override
	public boolean renderAsNormalBlock()
	{return false;}

	@Override
	public int damageDropped(int i)
	{return i;}

	@Override
	public int getRenderType()
	{return -1;}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{return new TileEntityInsulatedWire();}

	@Override
	public String getTextureFile()
	{return EECommonProxy.AITEMS;}

	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int var4 = 0; var4 < 5; ++var4)
			par3List.add(new ItemStack(par1, 1, var4));
	}
}