package ccm.local.Components;

/***
 * The Navigation interface provides generic navigation capabilities. It is
 * inherited by all component interfaces, and may be optionally inherited by
 * any interface that is explicitly designed to be a facet interface for a
 * component.
 * CCM Specification 1-10
 * Light Weight CCM 4.1.4
 */
public interface Navigation
{
    /*
     * The provide_facet operation returns a reference to the facet
     * denoted by the name parameter. If the value of the name parameter does
     * not correspond to one of the component's facets, the InvalidName
     * exception shall be raised.
     */
    Object provide_facet(String name)
      throws InvalidName;
}
