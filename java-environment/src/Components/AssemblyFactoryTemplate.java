package Components;


public class AssemblyFactoryTemplate
	implements Components.AssemblyFactory 
{
	private Class assemblyClass;

	public AssemblyFactoryTemplate(Class c) 
		throws CreateFailure
	{
		try
		{
			if(c.newInstance() instanceof Assembly)
			{
				assemblyClass = c;			
			}
			else
			{
				throw new CreateFailure();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CreateFailure();
		}
	}
	
	
    public Components.Assembly create()
		throws CreateFailure
    {
		try
		{
			return (Assembly)assemblyClass.newInstance();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new CreateFailure();
		}
    }
}
