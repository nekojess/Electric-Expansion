package electricexpansion.api;

public interface WirelessPowerMachine 
{
	public short getFrequency(); 

	public void setFrequency(short newFrequency);
	
	public double getJoules(Object... data);
	
	public double getMaxJoules(Object... data);
}
