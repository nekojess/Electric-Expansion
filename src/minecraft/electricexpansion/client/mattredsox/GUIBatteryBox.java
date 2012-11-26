package electricexpansion.client.mattredsox;

import net.minecraft.src.GuiContainer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.StatCollector;

import org.lwjgl.opengl.GL11;

import universalelectricity.core.electricity.ElectricInfo;
import universalelectricity.core.electricity.ElectricInfo.ElectricUnit;
import electricexpansion.mattredsox.ContainerBatteryBox;
import electricexpansion.mattredsox.tileentities.TileEntityBatteryBox;

public class GUIBatteryBox extends GuiContainer
{
    private TileEntityBatteryBox tileEntity;

    private int containerWidth;
    private int containerHeight;
    
    public GUIBatteryBox(InventoryPlayer par1InventoryPlayer, TileEntityBatteryBox batteryBox)
    {
        super(new ContainerBatteryBox(par1InventoryPlayer, batteryBox));
        this.tileEntity = batteryBox;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(this.tileEntity.getInvName(), 15, 6, 4210752);
        String displayWattHours = ElectricInfo.getDisplayShort(tileEntity.getJoules(), ElectricUnit.JOULES);
        String displayMaxWattHours = ElectricInfo.getDisplay(tileEntity.getMaxJoules(), ElectricUnit.JOULES);

        if (this.tileEntity.isDisabled())
        {
            displayMaxWattHours = "Disabled";
        }

        this.fontRenderer.drawString(displayWattHours + " of", 73 - displayWattHours.length(), 30, 4210752);
        this.fontRenderer.drawString(displayMaxWattHours, 58, 40, 4210752);
        this.fontRenderer.drawString("Voltage: " + (int)this.tileEntity.getVoltage(), 65, 60, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
  //this.fontRenderer.drawString("Buildcraft Upgrade Status: "+ this.tileEntity.hasBCComp, 180, 60, 0xffffff);
  //this.fontRenderer.drawString("IC2 Upgrade Status: "+ this.tileEntity.hasIC2Comp, 180, 70, 0xffffff);
  //this.fontRenderer.drawString("T1 Upgrade Status: "+ this.tileEntity.hasT1Capacity, 180, 80, 0xffffff);
 // this.fontRenderer.drawString("T2 Upgrade Status: "+ this.tileEntity.hasT2Capacity, 180, 90, 0xffffff);
 // this.fontRenderer.drawString("T3 Upgrade Status: "+ this.tileEntity.hasT3Capacity, 180, 100, 0xffffff);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int var4 = this.mc.renderEngine.getTexture("/electricexpansion/textures/mattredsox/BatBox.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        
        containerWidth = (this.width - this.xSize) / 2;
        containerHeight = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(containerWidth, containerHeight, 0, 0, this.xSize, this.ySize);
        int scale = (int)(((double)this.tileEntity.getJoules() / this.tileEntity.getMaxJoules()) * 72);
        this.drawTexturedModalRect(containerWidth + 64, containerHeight + 51, 176, 0, scale, 20);
    }
}