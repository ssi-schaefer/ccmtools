import org.omg.CORBA.ShortHolder;
import org.omg.CORBA.IntHolder;
import org.omg.CORBA.FloatHolder;
import org.omg.CORBA.DoubleHolder;
import org.omg.CORBA.CharHolder;
import org.omg.CORBA.BooleanHolder;
import org.omg.CORBA.ByteHolder;

import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;

import world.europe.austria.ccm.local.*;
import ccm.local.ServiceLocator;


public class Client
{
    public static void main(String[] args)
    {
        ORB orb = ORB.init(args, null);
        try {
	    // Set up the ServiceLocator singleton
            ServiceLocator.instance().setCorbaOrb(orb);
	    
	    // Deploy ClientLib component
	    TestHomeClientLibDeployment.deploy("myTestHome");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	
	try
	{
	    System.out.println("Client");
	    
	    ccm.local.Components.HomeFinder homeFinder = 
		ccm.local.HomeFinder.instance();
	    TestHome home = 
		(TestHome) homeFinder.find_home_by_name("myTestHome");

	    Test component = home.create();	    
	    component.configuration_complete();
	    
	    /*
		 * Test VoidTypeInterface facet
		 */
		VoidTypeInterface voidType = component.provide_voidType();
			
		{
			voidType.f1(7);
		}

		{
			int result = voidType.f2();
			assert(result == 7);
		}
		
		/*
		 * Test BasicTypeInterface facet
		 */
		BasicTypeInterface basicType = component.provide_basicType();

			{ // short
				short p1 = 7;
				ShortHolder p2 = new ShortHolder((short) 3);
				ShortHolder p3 = new ShortHolder();

				short result = basicType.f1(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}

			{ // long
				int p1 = 7;
				IntHolder p2 = new IntHolder(3);
				IntHolder p3 = new IntHolder();

				int result = basicType.f2(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}

			{ // unsigned short
				short p1 = 7;
				ShortHolder p2 = new ShortHolder((short) 3);
				ShortHolder p3 = new ShortHolder();

				short result = basicType.f3(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}

			{ // unsigned long
				int p1 = 7;
				IntHolder p2 = new IntHolder(3);
				IntHolder p3 = new IntHolder();

				int result = basicType.f4(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}

			{ // float
				float p1 = (float) 7.0;
				FloatHolder p2 = new FloatHolder((float) 3.0);
				FloatHolder p3 = new FloatHolder();

				float result = basicType.f5(p1, p2, p3);

				assert(Math.abs(p2.value - 7.0) < 0.001);
				assert(Math.abs(p3.value - 3.0) < 0.001);
				assert(Math.abs(result - (3.0 + 7.0)) < 0.001);
			}

			{ // float
				float p1 = (float) 7.0;
				FloatHolder p2 = new FloatHolder((float) 3.0);
				FloatHolder p3 = new FloatHolder();

				float result = basicType.f5(p1, p2, p3);

				assert(Math.abs(p2.value - 7.0) < 0.001);
				assert(Math.abs(p3.value - 3.0) < 0.001);
				assert(Math.abs(result - (3.0 + 7.0)) < 0.001);
			}

			{ // double
				double p1 = 7.0;
				DoubleHolder p2 = new DoubleHolder(3.0);
				DoubleHolder p3 = new DoubleHolder();

				double result = basicType.f6(p1, p2, p3);

				assert(Math.abs(p2.value - 7.0) < 0.000001);
				assert(Math.abs(p3.value - 3.0) < 0.000001);
				assert(Math.abs(result - (3.0 + 7.0)) < 0.000001);
			}

			{ // char
				char p1 = (char) 7;
				CharHolder p2 = new CharHolder((char) 3);
				CharHolder p3 = new CharHolder();

				char result = basicType.f7(p1, p2, p3);

				assert(p2.value == (char) 7);
				assert(p3.value == (char) 3);
				assert(result == (char) (3 + 7));
			}

			{
				String s1 = "sieben";
				StringHolder s2 = new StringHolder("drei");
				StringHolder s3 = new StringHolder();

				String result = basicType.f8(s1, s2, s3);

				assert(s2.value.equals("sieben"));
				assert(s3.value.equals("drei"));
				assert(result.equals("dreisieben"));
			}

			{ // boolean
				boolean p1 = true;
				BooleanHolder p2 = new BooleanHolder(false);
				BooleanHolder p3 = new BooleanHolder();

				boolean result = basicType.f9(p1, p2, p3);

				assert(p2.value == true);
				assert(p3.value == false);
				assert(result == false && true);
			}

			{ // octet
				byte p1 = (byte) 7;
				ByteHolder p2 = new ByteHolder((byte) 3);
				ByteHolder p3 = new ByteHolder();

				byte result = basicType.f10(p1, p2, p3);

				assert(p2.value == (byte) 7);
				assert(p3.value == (byte) 3);
				assert(result == (byte) (3 + 7));
			}


		/*
		 * Test UserTypeInterface facet
		 */
		UserTypeInterface userType = component.provide_userType();

			{ // enum Color {red, green, blue, black, orange}
				world.europe.austria.Color p1 = world.europe.austria.Color.red;
				world.europe.austria.ColorHolder p2 = new world.europe.austria.ColorHolder(
						world.europe.austria.Color.blue);
				world.europe.austria.ColorHolder p3 = new world.europe.austria.ColorHolder();

				world.europe.austria.Color result = userType.f1(p1, p2, p3);

				assert(p2.value == world.europe.austria.Color.red);
				assert(p3.value == world.europe.austria.Color.blue);
				assert(result == world.europe.austria.Color.orange);
			}

			{ // struct Person { long id; string name; }
				world.europe.austria.Person p1 = new world.europe.austria.Person(3, "Egon");
				world.europe.austria.PersonHolder p2 = new world.europe.austria.PersonHolder(
						new world.europe.austria.Person(23, "Andrea"));
				world.europe.austria.PersonHolder p3 = new world.europe.austria.PersonHolder();

				world.europe.austria.Person result = userType.f2(p1, p2, p3);

				assert(p3.value.name.equals("Andrea"));
				assert(p3.value.id == 23);
				assert(p2.value.name.equals("Egon"));
				assert(p2.value.id == 3);
				assert(result.name.equals("EgonAndrea"));
				assert(result.id == 3 + 23);
			}

			{ // struct Address { string street; long number; Person resident; }
				world.europe.austria.Person egon = new world.europe.austria.Person(3, "Egon");
				world.europe.austria.Address p1 = new world.europe.austria.Address("Waltendorf", 7, egon);

				world.europe.austria.Person andrea = new world.europe.austria.Person(23, "Andrea");
				world.europe.austria.AddressHolder p2 = new world.europe.austria.AddressHolder(
						new world.europe.austria.Address("Petersgasse", 17, andrea));

				world.europe.austria.AddressHolder p3 = new world.europe.austria.AddressHolder();

				world.europe.austria.Address result = userType.f3(p1, p2, p3);

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

				world.europe.austria.LongListHolder p2 = new world.europe.austria.LongListHolder(p2Value);
				world.europe.austria.LongListHolder p3 = new world.europe.austria.LongListHolder();

				int[] result = userType.f4(p1, p2, p3);

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

				world.europe.austria.StringListHolder p2 = new world.europe.austria.StringListHolder(p2Value);
				world.europe.austria.StringListHolder p3 = new world.europe.austria.StringListHolder();

				String[] result = userType.f5(p1, p2, p3);

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

				world.europe.austria.Person[] p1 = new world.europe.austria.Person[5];
				world.europe.austria.Person[] p2Value = new world.europe.austria.Person[5];
				for (int i = 0; i < 5; i++)
				{
					world.europe.austria.Person p = p1[i] = new world.europe.austria.Person(i, "Andrea");
					p2Value[i] = new world.europe.austria.Person(i + i, "Egon");
				}
				world.europe.austria.PersonListHolder p2 = new world.europe.austria.PersonListHolder(p2Value);
				world.europe.austria.PersonListHolder p3 = new world.europe.austria.PersonListHolder();

				world.europe.austria.Person[] result = userType.f6(p1, p2, p3);

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

				int result = userType.f7(p1, p2, p3);

				assert(p2.value == 7);
				assert(p3.value == 3);
				assert(result == 3 + 7);
			}

			component.remove();
			System.out.println("OK!");
		}
	        catch (Exception e)
	        {
		    e.printStackTrace();
		}
		finally
		{
		    // Undeploy ClientLib component
		    TestHomeClientLibDeployment.undeploy("myTestHome");

		    // Tear down the ServiceLocator singleton
		    ServiceLocator.instance().destroy();	
		}
    }
}
