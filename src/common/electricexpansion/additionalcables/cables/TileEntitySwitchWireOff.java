package additionalcables.cables;

import additionalcables.AdditionalCables;
import net.minecraft.src.TileEntity;

public class TileEntitySwitchWireOff extends TileEntity 
{
@Override
public boolean canUpdate()
{
	return true;
}
@Override
public void updateEntity()
{
	if(this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord))
		this.worldObj.setBlockAndMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, AdditionalCables.onSwitchWire, this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
}
}