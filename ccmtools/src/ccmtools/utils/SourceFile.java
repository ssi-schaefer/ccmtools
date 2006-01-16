package ccmtools.utils;


public class SourceFile
{
	private final String code;
	private final String packageName;
	private final String className;
	
	public SourceFile(String packageName, String fileName, String code)
	{
		this.packageName = packageName;
		this.className = fileName;
		this.code = code;
	}

	public String getPackageName()
	{
		return packageName;
	}
	
	public String getClassName()
	{
		return className;
	}

	public String getCode()
	{
		return code;
	}
}
