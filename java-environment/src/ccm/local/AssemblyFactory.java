package ccm.local;

import Components.ccm.local.Assembly;
import Components.ccm.local.CreateFailure;

public class AssemblyFactory
	implements Components.ccm.local.AssemblyFactory 
{
	private Class assemblyClass;

	public AssemblyFactory(Class c) 
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
	
	
    public Components.ccm.local.Assembly create()
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
