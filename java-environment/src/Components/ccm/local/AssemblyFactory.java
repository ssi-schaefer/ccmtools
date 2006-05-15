package Components.ccm.local;

public interface AssemblyFactory
{
	 Assembly create()
		throws CreateFailure;
}
