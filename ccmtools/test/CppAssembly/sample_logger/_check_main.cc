#include <iostream>
#include <wamas/helpers/StdErrLoggerHome_gen.h>

using namespace wamas::helpers;

int main()
{
    // objects
    StdErrLogger::SmartPtr comp;
    ::wamas::io::LoggerItf::SmartPtr logger;

    // setup
    try
    {
        StdErrLoggerHome home;
        comp = home.create();
        logger = comp->provide_logger();
        comp->configuration_complete();
    }
    catch(...)
    {
        std::cerr << "\n SETUP FAILED\n";
        return 1;
    }

    // business logic
    try
    {
        logger->print("Hello World!");
    }
    catch(...)
    {
        std::cerr << "\n B.L. FAILED\n";
        return 1;
    }

    // tear down
    try
    {
        comp->remove();
    }
    catch(...)
    {
        std::cerr << "\n TEAR-DOWN FAILED\n";
        return 1;
    }

    return 0;
}
