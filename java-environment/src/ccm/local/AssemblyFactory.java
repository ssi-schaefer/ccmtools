package ccm.local;

import ccm.local.Components.Assembly;
import ccm.local.Components.CreateFailure;

public class AssemblyFactory
	implements ccm.local.Components.AssemblyFactory 
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
	
	
    public ccm.local.Components.Assembly create()
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
