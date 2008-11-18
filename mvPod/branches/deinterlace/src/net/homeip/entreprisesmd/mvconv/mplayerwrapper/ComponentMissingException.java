package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

/**
 * This exception are throw by Hard Coded profile when it's impossible to create
 * the encoding options.
 * 
 * @author patapouf
 * 
 */
public class ComponentMissingException extends MPlayerException {

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 4516958218458281783L;
	/**
	 * The component name
	 */
	private String componentName;

	/**
	 * Create a new component missing exception.
	 * 
	 * @param name
	 *            the name of the missing component.
	 */
	public ComponentMissingException(String name) {
		super("Component missing " + name); //$NON-NLS-1$
		this.componentName = name;
	}
	
	/**
	 * Create a new component missing exception.
	 * 
	 * @param name
	 *            the name of the missing component.
	 * @param cause
	 *            the exception that cause this error.
	 */
	public ComponentMissingException(String name, Exception cause) {
		super("Component missing " + name, cause); //$NON-NLS-1$
	}

	/**
	 * Return the missing component name.
	 * 
	 * @return the component name.
	 */
	public String getComponentName() {
		return this.componentName;
	}
}
