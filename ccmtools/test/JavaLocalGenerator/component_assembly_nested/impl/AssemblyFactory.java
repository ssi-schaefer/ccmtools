package world;

import ccm.local.Components.CreateFailure;


public class AssemblyFactory
    implements ccm.local.Components.AssemblyFactory 
{
    public ccm.local.Components.Assembly create()
	throws CreateFailure
    {
	System.out.println(" AssemblyFactory.create()");
	return new world.Assembly();
    }
}
