#include <iostream>
#include <wamas_system_ConsoleHome_entry.h>
#include <wamas_helpers_StdErrLoggerHome_entry.h>
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
        {
            std::cout << "# register Console" << std::endl;
            if(::deploy_wamas_system_ConsoleHome("Console")!=0)
            {
                std::cerr << "registration failed\n";
                return 1;
            }
        }
        {
            std::cout << "# creating home" << std::endl;
            ::Components::CCMHome::SmartPtr sp = create_wamas_helpers_StdErrLoggerHomeAdapter();
            StdErrLoggerHome* home = dynamic_cast<StdErrLoggerHome*>(sp.ptr());;
            std::cout << "# creating component" << std::endl;
            comp = home->create();
        }
        std::cout << "# provide logger" << std::endl;
        logger = comp->provide_logger();
        std::cout << "# configuration complete" << std::endl;
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
        std::cout << "# business logic" << std::endl;
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
        std::cout << "# tear down" << std::endl;
        comp->remove();
    }
    catch(...)
    {
        std::cerr << "\n TEAR-DOWN FAILED\n";
        return 1;
    }

    return 0;
}
