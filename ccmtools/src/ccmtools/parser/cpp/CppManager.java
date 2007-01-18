package ccmtools.parser.cpp;

import ccmtools.utils.ConfigurationLocator;

public class CppManager
{
    public static PreProcessor createCpp()
    {
        if (ConfigurationLocator.getInstance().get("ccmtools.cpp").length() != 0)
        {
            return new ExternalCpp();
        }
        else
        {
            return new InternalCpp();
        }
    }    
}
