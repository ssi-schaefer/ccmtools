package ccmtools.CppGenerator.plugin;

public class PdlType
{
	private String pdlName;
	private String cName;
	private static final String DELIMITER_REGEX = ":"; 
	
	public PdlType(String line)
	{
		String[] names = line.split(DELIMITER_REGEX);
		if(names.length == 2)
		{
			pdlName = names[0].trim();
			cName = names[1].trim();
		}
		else
		{
			pdlName = cName = "";
		}
	}

	public String getCName()
	{
		return cName;
	}

	public String getPdlName()
	{
		return pdlName;
	}
}
