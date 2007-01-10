package Components;

/***
 * The CCMContext is an internal interface which provides a component instance
 * with access to the common container-provided runtime services.
 * It serves as a "bootstrap" to the various services the container provides
 * for the component.
 * CCM Spec. 4-22
 * Light Weight CCM 4.4.3.2
 ***/
public interface CCMContext
{
	HomeExecutorBase get_CCM_home();
}
