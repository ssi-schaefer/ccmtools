
/**
* BenchmarkHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../../../ccmtools/idl3/Benchmark.idl
* Tuesday, February 15, 2005 3:44:02 PM CET
*/

abstract public class BenchmarkHelper
{
  private static String  _id = "IDL:Benchmark:1.0";

  public static void insert (org.omg.CORBA.Any a, Benchmark that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Benchmark extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (BenchmarkHelper.id (), "Benchmark");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Benchmark read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_BenchmarkStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Benchmark value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static Benchmark narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof Benchmark)
      return (Benchmark)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      _BenchmarkStub stub = new _BenchmarkStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
