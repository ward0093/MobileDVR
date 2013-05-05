package com.MSSE.MobileDVR.datamodel;

public class Channel
{
    private final long id;
	private final int number;
	private final String name;
	
	public Channel()
	{
		this(-1, 0, "");
	}
	
	public Channel(int number, String name)
	{
        this(-1, number, name);
	}

    public Channel(long id, int number, String name)
    {
        this.id = id;
        this.number = number;
        this.name = name != null ? name : "";
    }

    public long getId()
    {
        return id;
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
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + number;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Channel other = (Channel) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (number != other.number)
			return false;
		return true;
	}
	
}
