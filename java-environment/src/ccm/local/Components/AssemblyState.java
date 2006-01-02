package ccm.local.Components;

public class AssemblyState
{
	private final int state;

	private final static String[] Labels =
	{
			"", "INACTIVE", "INSERVICE"
	};

	public static final AssemblyState INACTIVE = new AssemblyState(1);

	public static final AssemblyState INSERVICE = new AssemblyState(2);

	private AssemblyState(int state)
	{
		this.state = state;
	}

	public String toString()
	{
		return Labels[state];
	}

	public final static String[] getLabels()
	{
		return Labels;
	}
}
