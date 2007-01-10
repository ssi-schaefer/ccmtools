package Components;

public interface AssemblyFactory
{
	 Assembly create()
		throws CreateFailure;
}
