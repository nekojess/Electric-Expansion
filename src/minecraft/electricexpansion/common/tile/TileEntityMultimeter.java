package electricexpansion.common.tile;

import com.google.common.io.ByteArrayDataInput;
import electricexpansion.common.ElectricExpansion;
import java.util.EnumSet;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.electricity.ElectricityConnections;
import universalelectricity.core.electricity.ElectricityNetwork;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.implement.IConductor;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.implement.IRotatable;
import universalelectricity.prefab.network.IPacketReceiver;
import universalelectricity.prefab.network.PacketManager;
import universalelectricity.prefab.tile.TileEntityElectricityReceiver;

public class TileEntityMultimeter extends TileEntityElectricityReceiver 
	implements IPacketReceiver, IRotatable
{
	public ElectricityPack electricityReading = new ElectricityPack();
	private ElectricityPack lastReading = new ElectricityPack();

	public void initiate()
	{
		ElectricityConnections.registerConnector(this, EnumSet.of(ForgeDirection.getOrientation(getBlockMetadata() + 2)));
		this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, ElectricExpansion.blockMultimeter.blockID);
	}

	public void updateEntity()
	{
		super.updateEntity();

		if (this.ticks % 20L == 0L)
		{
			this.lastReading = this.electricityReading;

			if (!this.worldObj.isRemote)
			{
				if (!isDisabled())
				{
					ForgeDirection inputDirection = ForgeDirection.getOrientation(getBlockMetadata() + 2);
					TileEntity inputTile = Vector3.getTileEntityFromSide(this.worldObj, new Vector3(this), inputDirection);

					if (inputTile != null)
					{
						if ((inputTile instanceof IConductor))
						{
							this.electricityReading = ((IConductor)inputTile).getNetwork().getProduced(new TileEntity[0]);
							this.electricityReading.amperes *= 20.0D;
						}
						else
						{
							this.electricityReading = new ElectricityPack();
						}
					}
					else
					{
						this.electricityReading = new ElectricityPack();
					}
				}

				if (this.electricityReading.amperes != this.lastReading.amperes)
				{
					PacketManager.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 20.0D);
				}
			}
		}
	}

	public Packet getDescriptionPacket()
	{
		return PacketManager.getPacket("ElecEx", this, new Object[] { Double.valueOf(this.electricityReading.amperes), Double.valueOf(this.electricityReading.voltage) });
	}

	public void handlePacketData(INetworkManager network, int type, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream)
	{
		if (this.worldObj.isRemote)
		{
			try
			{
				this.electricityReading.amperes = dataStream.readDouble();
				this.electricityReading.voltage = dataStream.readDouble();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public String getInvName()
	{
		return "Multimeter";
	}

	public ForgeDirection getDirection()
	{
		return ForgeDirection.getOrientation(getBlockMetadata());
	}

	public void setDirection(ForgeDirection facingDirection)
	{
		this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, facingDirection.ordinal());
	}
}
