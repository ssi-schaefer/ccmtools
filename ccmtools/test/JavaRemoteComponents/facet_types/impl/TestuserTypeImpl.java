/**
 * This file was automatically generated by CCM Tools version 0.6.3
 * <http://ccmtools.sourceforge.net>
 * 
 * CCM_UserTypeInterfaceImpl facet implementation.
 *
 * // TODO: WRITE YOUR DESCRIPTION HERE !
 * 
 * @author
 * @version
 */

package world.europe.austria.ccm.local;

import java.util.List;
import java.util.ArrayList;

import Components.ccm.local.CCMException;

import world.ccm.local.Color;
import world.ccm.local.Person;
import ccm.local.Holder;
import Components.ccm.local.CCMException;

/**
 * This class implements a component facet's methods.
 *  // TODO: WRITE YOUR DESCRIPTION HERE !
 * 
 * @author
 * @version
 */
public class TestuserTypeImpl 
	implements CCM_UserTypeInterface
{
	/** Reference to the facet's component implementation */
	private TestImpl component;

	public TestuserTypeImpl(world.europe.austria.ccm.local.TestImpl component)
	{
		this.component = component;
	}

	/** Business logic implementations */

	public Color f1(Color p1, Holder<Color> p2, Holder<Color> p3) throws CCMException
	{
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		return Color.orange;
	}

	public Person f2(Person p1, Holder<Person> p2, Holder<Person> p3) throws CCMException
	{
		Person r = new Person(p1.getId() + p2.getValue().getId(), p1.getName() + p2.getValue().getName());
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		return r;
	}

	public Address f3(Address p1, Holder<Address> p2, Holder<Address> p3) throws CCMException
	{
		Person person = new Person(p1.getResident().getId() + p2.getValue().getResident().getId(), p1.getResident()
				.getName()
				+ p2.getValue().getResident().getName());
		Address addr = new Address(p1.getStreet() + p2.getValue().getStreet(), p1.getNumber()
				+ p2.getValue().getNumber(), person);
		p3.setValue(p2.getValue());
		p2.setValue(p1);
		return addr;
	}

	public List<Integer> f4(List<Integer> p1, Holder<List<Integer>> p2, Holder<List<Integer>> p3) throws CCMException
	{
		List<Integer> result = new ArrayList<Integer>(p1.size());
		p3.setValue(new ArrayList<Integer>(p1.size()));
		for (int i = 0; i < p1.size(); i++)
		{
			result.add(i);
			p3.getValue().add(p2.getValue().get(i));
			p2.getValue().set(i, p1.get(i));
		}
		return result;
	}

	public List<String> f5(List<String> p1, Holder<List<String>> p2, Holder<List<String>> p3) throws CCMException
	{
		List<String> result = new ArrayList<String>(p1.size());
		p3.setValue(new ArrayList<String>(p1.size()));
		for (int i = 0; i < p1.size(); i++)
		{
			result.add("Test");
			p3.getValue().add(p2.getValue().get(i));
			p2.getValue().set(i, p1.get(i));
		}
		return result;
	}

	public List<Person> f6(List<Person> p1, Holder<List<Person>> p2, Holder<List<Person>> p3) throws CCMException
	{
		List<Person> result = new ArrayList<Person>(p1.size());
		p3.setValue(new ArrayList<Person>(p1.size()));
		for (int i = 0; i < p1.size(); i++)
		{
			Person person = new Person(i, "Test");
			result.add(person);
			p3.getValue().add(p2.getValue().get(i));
			p2.getValue().set(i, p1.get(i));
		}
		return result;
	}

	public int f7(int t1, Holder<Integer> t2, Holder<Integer> t3) throws CCMException
	{
		t3.setValue(t2.getValue());
		t2.setValue(t1);
		return t3.getValue() + t1;
	}

	public int[] f8(int[] p1, ccm.local.Holder<int[]> p2, ccm.local.Holder<int[]> p3) throws CCMException
	{
		int length = 10; // For this test, we assume that all arrays are of
							// the same size
		int[] result = new int[length];
		p3.setValue(new int[length]);
		for (int i = 0; i < length; i++)
		{
			result[i] = i;
			p3.getValue()[i] = p2.getValue()[i];
			p2.getValue()[i] = p1[i];
		}
		return result;
	}

	public String[] f9(String[] p1, ccm.local.Holder<String[]> p2, ccm.local.Holder<String[]> p3) throws CCMException
	{
		int length = 10; // For this test, we assume that all arrays are of
							// the same size
		String[] result = new String[length];
		p3.setValue(new String[length]);
		for (int i = 0; i < length; i++)
		{
			result[i] = "result" + i;
			p3.getValue()[i] = p2.getValue()[i];
			p2.getValue()[i] = p1[i];
		}
		return result;
	}

	public Person[] f10(Person[] p1, ccm.local.Holder<Person[]> p2, ccm.local.Holder<Person[]> p3) throws CCMException
	{
		int length = 10; // For this test, we assume that all arrays are of
							// the same size
		Person[] result = new Person[length];
		p3.setValue(new Person[length]);
		for (int i = 0; i < length; i++)
		{
			Person person = new Person(i, "result" + i);
			result[i] = person;
			p3.getValue()[i] = p2.getValue()[i];
			p2.getValue()[i] = p1[i];
		}
		return result;
	}

	public int[][] f11(int[][] p1, ccm.local.Holder<int[][]> p2, ccm.local.Holder<int[][]> p3) throws CCMException
	{
		// TODO: implement test case
		return null;
	}

	public int[][][] f12(int[][][] p1, ccm.local.Holder<int[][][]> p2, ccm.local.Holder<int[][][]> p3)
		throws CCMException
	{
		// TODO: implement test case
		return null;
	}
}
