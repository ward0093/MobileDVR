package com.MSSE.MobileDVR;

public class Channel
{
	private final int number;
	private final String name;
	
	public Channel()
	{
		this(0, "");
	}
	
	public Channel(int number, String name)
	{
		this.number = number;
		this.name = name != null ? name : "";
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public String getName()
	{
		return name;
	}
	
	@Override
	public String toString()
	{
		return "" + number + ":" + name;
	}
	
	@Override
	public boolean equals(Object other)
	{
		boolean result = false;
		
		if (this == other)
			result = true;
		else if (other instanceof Channel)
		{
			Channel that = (Channel)other;
			if (that.number == this.number && this.name.equals(that.name))
				result = true;
		}
		
		return result;
	}
	
}
