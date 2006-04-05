package ccm.local.Components;

public interface AssemblyFactory
{
	 Assembly create()
		throws CreateFailure;
}
