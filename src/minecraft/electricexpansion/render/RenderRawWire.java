package electricexpansion.render;

import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

import electricexpansion.EECommonProxy;
import electricexpansion.api.CableInterfaces.IPanelElectricMachine;
import electricexpansion.cables.TileEntityRawWire;
import electricexpansion.model.ModelRawWire;


public class RenderRawWire extends TileEntitySpecialRenderer
{
	private ModelRawWire model;

	public RenderRawWire()
	{
		model = new ModelRawWire();
	}
	public void renderAModelAt(TileEntityRawWire tileEntity, double x, double y, double z, float f)
	{
		String textureToUse = null;
		int meta = tileEntity.getBlockMetadata();
		if(meta != -1)
		{
			if(meta == 0)
				textureToUse = EECommonProxy.ATEXTURES + "RawCopperWire.png";
			else if(meta == 1)
				textureToUse = EECommonProxy.ATEXTURES + "RawTinWire.png";
			else if(meta == 2)
				textureToUse = EECommonProxy.ATEXTURES + "RawSilverWire.png";
			else if(meta == 3)
				textureToUse = EECommonProxy.ATEXTURES + "RawHVWire.png";
			else if(meta == 4)
				textureToUse = EECommonProxy.ATEXTURES + "RawEndiumWire.png";
		}

		//Texture file
		bindTextureByName(textureToUse);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glScalef(1.0F, -1F, -1F);

		TileEntityRawWire TE = tileEntity;
		TileEntity[] neighbors = new TileEntity[6];
		for(int i=0; i<6; i++)
			if(TE.getConnectedBlocks()[i] != null)
				neighbors[i] = TE.getConnectedBlocks()[i];
		int[] metaConnected = new int[6];
		for(int i=0; i<6; i++)
			if(TE.getConnectedBlocks()[i] != null)
				metaConnected[i] = TE.getConnectedBlocks()[i].blockMetadata;
		
		for(int i=2; i<6; i++)
			if (TE.getConnectedBlocks()[i] != null)
				if (neighbors[i] instanceof IPanelElectricMachine)
					model.renderPanel();
		
		if (TE.getConnectedBlocks()[0] != null) {model.renderBottom();}
		if (TE.getConnectedBlocks()[1] != null) {model.renderTop();}
		if (TE.getConnectedBlocks()[2] != null) 
		{
			if (neighbors[2] instanceof IPanelElectricMachine && ((IPanelElectricMachine)TE.getConnectedBlocks()[2]).canConnectToBase(metaConnected[2], ForgeDirection.getOrientation(3)))
				model.renderPanelBack();
			else model.renderBack();
		}
		if (TE.getConnectedBlocks()[3] != null) 
		{
			if (neighbors[3] instanceof IPanelElectricMachine && ((IPanelElectricMachine)TE.getConnectedBlocks()[3]).canConnectToBase(metaConnected[3], ForgeDirection.getOrientation(2)))
					model.renderPanelFront();
			else model.renderFront();
		}
		if (TE.getConnectedBlocks()[4] != null) 
		{
			if (neighbors[4] instanceof IPanelElectricMachine && ((IPanelElectricMachine)TE.getConnectedBlocks()[4]).canConnectToBase(metaConnected[4], ForgeDirection.getOrientation(5)))
					model.renderPanelLeft();
			else model.renderLeft();
		}
		if (TE.getConnectedBlocks()[5] != null) 
		{
			if (neighbors[5] instanceof IPanelElectricMachine && ((IPanelElectricMachine)TE.getConnectedBlocks()[5]).canConnectToBase(metaConnected[5], ForgeDirection.getOrientation(4)))
					model.renderPanelRight();
			else model.renderRight();
		}

		model.renderMiddle();
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double var2, double var4, double var6, float var8)
	{
		this.renderAModelAt((TileEntityRawWire)tileEntity, var2, var4, var6, var8);
	}
}