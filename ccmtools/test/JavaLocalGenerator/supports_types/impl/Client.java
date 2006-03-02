import world.europe.austria.ccm.local.*;
import ccm.local.Components.*;

import org.omg.CORBA.ShortHolder;
import org.omg.CORBA.IntHolder;
import org.omg.CORBA.FloatHolder;
import org.omg.CORBA.DoubleHolder;
import org.omg.CORBA.CharHolder;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ByteHolder;
import org.omg.CORBA.StringHolder;

import world.europe.austria.Color;
import world.europe.austria.ColorHolder;
import world.europe.austria.Person;
import world.europe.austria.PersonHolder;
import world.europe.austria.Address;
import world.europe.austria.AddressHolder;
import world.europe.austria.LongListHolder;
import world.europe.austria.StringListHolder;
import world.europe.austria.PersonListHolder;

import java.util.logging.*;

public class Client
{
	public static void main(String[] args)
	{
	    // Configure Logger
	    Logger logger = Logger.getLogger("ccm.local");
	    logger.setLevel(Level.FINE);	    
	    Handler handler = new ConsoleHandler();
	    handler.setLevel(Level.ALL);
	    handler.setFormatter(new ccm.local.MinimalFormatter());
	    logger.addHandler(handler);
	    ccm.local.ServiceLocator.instance().setLogger(logger);

		// Deploy local Java component
		try
		{
			TestHomeDeployment.deploy("TestHome");
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		// Use local Java component
		try
		{
			TestHome home = (TestHome) ccm.local.HomeFinder.instance().find_home_by_name("TestHome");
			Test component = home.create();

			
			/*
			 * Test VoidTypeInterface facet
			 */
			System.out.println("Supported Interface (Void Types) Test...");
			{
				component.fv1(7);
				int result = component.fv2();
				assert(result == 7);
			}
			System.out.println("OK!");


			/*
			 * Test BasicTypeInterface facet
			 */
			System.out.println("Supported Interface (Basic Types) Test...");
			{ // short
				short p1 = 7;
				ShortHolder p2 = new ShortHolder((short) 3);
				ShortHolder p3 = new ShortHolder();

				short result = component.fb1(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}

			{ // long
				int p1 = 7;
				IntHolder p2 = new IntHolder(3);
				IntHolder p3 = new IntHolder();

				int result = component.fb2(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}

			{ // unsigned short
				short p1 = 7;
				ShortHolder p2 = new ShortHolder((short) 3);
				ShortHolder p3 = new ShortHolder();

				short result = component.fb3(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}

			{ // unsigned long
				int p1 = 7;
				IntHolder p2 = new IntHolder(3);
				IntHolder p3 = new IntHolder();

				int result = component.fb4(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}

			{ // float
				float p1 = (float) 7.0;
				FloatHolder p2 = new FloatHolder((float) 3.0);
				FloatHolder p3 = new FloatHolder();

				float result = component.fb5(p1, p2, p3);

				assert(Math.abs(p2.value - 7.0) < 0.001);
				assert(Math.abs(p3.value - 3.0) < 0.001);
				assert(Math.abs(result - (3.0 + 7.0)) < 0.001);
			}

			{ // float
				float p1 = (float) 7.0;
				FloatHolder p2 = new FloatHolder((float) 3.0);
				FloatHolder p3 = new FloatHolder();

				float result = component.fb5(p1, p2, p3);

				assert(Math.abs(p2.value - 7.0) < 0.001);
				assert(Math.abs(p3.value - 3.0) < 0.001);
				assert(Math.abs(result - (3.0 + 7.0)) < 0.001);
			}

			{ // double
				double p1 = 7.0;
				DoubleHolder p2 = new DoubleHolder(3.0);
				DoubleHolder p3 = new DoubleHolder();

				double result = component.fb6(p1, p2, p3);

				assert(Math.abs(p2.value - 7.0) < 0.000001);
				assert(Math.abs(p3.value - 3.0) < 0.000001);
				assert(Math.abs(result - (3.0 + 7.0)) < 0.000001);
			}

			{ // char
				char p1 = (char) 7;
				CharHolder p2 = new CharHolder((char) 3);
				CharHolder p3 = new CharHolder();

				char result = component.fb7(p1, p2, p3);

				assert(p2.value == (char) 7);
				assert(p3.value == (char) 3);
				assert(result == (char) (3 + 7));
			}

			{
				String s1 = "sieben";
				StringHolder s2 = new StringHolder("drei");
				StringHolder s3 = new StringHolder();

				String result = component.fb8(s1, s2, s3);

				assert(s2.value.equals("sieben"));
				assert(s3.value.equals("drei"));
				assert(result.equals("dreisieben"));
			}

			{ // boolean
				boolean p1 = true;
				BooleanHolder p2 = new BooleanHolder(false);
				BooleanHolder p3 = new BooleanHolder();

				boolean result = component.fb9(p1, p2, p3);

				assert(p2.value == true);
				assert(p3.value == false);
				assert(result == false && true);
			}

			{ // octet
				byte p1 = (byte) 7;
				ByteHolder p2 = new ByteHolder((byte) 3);
				ByteHolder p3 = new ByteHolder();

				byte result = component.fb10(p1, p2, p3);

				assert(p2.value == (byte) 7);
				assert(p3.value == (byte) 3);
				assert(result == (byte) (3 + 7));
			}
			System.out.println("OK!");


			/*
			 * Test UserTypeInterface facet
			 */
			System.out.println("Supported Interface (User Types) Test...");
			{ // enum Color {red, green, blue, black, orange}
				Color p1 = Color.red;
				ColorHolder p2 = new ColorHolder(Color.blue);
				ColorHolder p3 = new ColorHolder();

				Color result = component.fu1(p1, p2, p3);

				assert(p2.value == Color.red);
				assert(p3.value == Color.blue);
				assert(result == Color.orange);
			}

			{ // struct Person { long id; string name; }
				Person p1 = new Person(3, "Egon");
				PersonHolder p2 = new PersonHolder(new Person(23, "Andrea"));
				PersonHolder p3 = new PersonHolder();

				Person result = component.fu2(p1, p2, p3);

				assert(p3.value.name.equals("Andrea"));
				assert(p3.value.id == 23);
				assert(p2.value.name.equals("Egon"));
				assert(p2.value.id == 3);
				assert(result.name.equals("EgonAndrea"));
				assert(result.id == 3 + 23);
			}

			{ // struct Address { string street; long number; Person resident;
				// }
				Person egon = new Person(3, "Egon");
				Address p1 = new Address("Waltendorf", 7, egon);

				Person andrea = new Person(23, "Andrea");
				AddressHolder p2 = new AddressHolder(new Address("Petersgasse", 17, andrea));

				AddressHolder p3 = new AddressHolder();

				Address result = component.fu3(p1, p2, p3);

				assert(p3.value.street.equals("Petersgasse"));
				assert(p3.value.number == 17);
				assert(p3.value.resident.name.equals("Andrea"));
				assert(p3.value.resident.id == 23);

				assert(p2.value.street.equals("Waltendorf"));
				assert(p2.value.number == 7);
				assert(p2.value.resident.name.equals("Egon"));
				assert(p2.value.resident.id == 3);

				assert(result.street.equals("WaltendorfPetersgasse"));
				assert(result.number == 24);
				assert(result.resident.name.equals("EgonAndrea"));
				assert(result.resident.id == 26);
			}

			{ // typedef sequence<long> LongList
				int[] p1 = new int[5];
				int[] p2Value = new int[5];
				for (int i = 0; i < 5; i++)
				{
					p1[i] = i;
					p2Value[i] = i + i;
				}

				LongListHolder p2 = new LongListHolder(p2Value);
				LongListHolder p3 = new LongListHolder();

				int[] result = component.fu4(p1, p2, p3);

				for (int i = 0; i < result.length; i++)
				{
					assert(result[i] == i);
				}
				for (int i = 0; i < p2.value.length; i++)
				{
					assert(p2.value[i] == i);
				}
				for (int i = 0; i < p3.value.length; i++)
				{
					assert(p3.value[i] == i + i);
				}
			}

			{ // typedef sequence<string> StringList
				String[] p1 = new String[5];
				String[] p2Value = new String[5];
				for (int i = 0; i < 5; i++)
				{
					p1[i] = "Egon";
					p2Value[i] = "Andrea";
				}

				StringListHolder p2 = new StringListHolder(p2Value);
				StringListHolder p3 = new StringListHolder();

				String[] result = component.fu5(p1, p2, p3);

				for (int i = 0; i < result.length; i++)
				{
					assert(result[i].equals("Test"));
				}
				for (int i = 0; i < p2.value.length; i++)
				{
					assert(p2.value[i].equals("Egon"));
				}
				for (int i = 0; i < p3.value.length; i++)
				{
					assert(p3.value[0].equals("Andrea"));
				}
			}

			{ // typedef sequence<Person> PersonList

				Person[] p1 = new Person[5];
				Person[] p2Value = new Person[5];
				for (int i = 0; i < 5; i++)
				{
					Person p = p1[i] = new Person(i, "Andrea");
					p2Value[i] = new Person(i + i, "Egon");
				}
				PersonListHolder p2 = new PersonListHolder(p2Value);
				PersonListHolder p3 = new PersonListHolder();

				Person[] result = component.fu6(p1, p2, p3);

				for (int i = 0; i < result.length; i++)
				{
					assert(result[i].name.equals("Test"));
					assert(result[i].id == i);
				}
				for (int i = 0; i < p2.value.length; i++)
				{
					assert(p2.value[i].name.equals("Andrea"));
					assert(p2.value[i].id == i);
				}
				for (int i = 0; i < p3.value.length; i++)
				{
					assert(p3.value[i].name.equals("Egon"));
					assert(p3.value[i].id == i + i);
				}
			}

			{ // typedef long time_t
				int p1 = 7;
				IntHolder p2 = new IntHolder(3);
				IntHolder p3 = new IntHolder();

				int result = component.fu7(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}
			System.out.println("OK!");
			
			component.remove();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}

		// Undeploy local Java component
		TestHomeDeployment.undeploy("TestHome");
	}
}
