package Components;

/***
 * A configurator is an object that encapsulates a specific attribute
 * configuration that can be reproduced an many instances of a component type.
 * A configurator is intended to invoke attribute set operations on the target
 * component.
 * CCM Specification 1-47
 */
public interface Configurator
{
    /**
     * The configure (  ) operation establishes its encapsulated configuration
     * on the target component. If the target component is not of the type
     * expected by the configurator, the operation shall raise the
     * WrongComponentType exception.
     */
    void configure(CCMObject comp)
      	throws WrongComponentType;
}
