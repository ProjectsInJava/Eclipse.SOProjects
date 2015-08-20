package System.Old;

public enum MODEL_OF_TRAIN 
{
	DECIMAL_EXPANSION;
	
	public static MODEL_OF_TRAIN getEnum(String aStringShape)
	{
	    if(DECIMAL_EXPANSION.name().equals(aStringShape))
	    {
	        return DECIMAL_EXPANSION;
	    }
	    throw new IllegalArgumentException("No Enum specified for this string");	    
	}
	
	
}
