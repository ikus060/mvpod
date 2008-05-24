package net.homeip.entreprisesmd.mvconv.gui.actions;

/**
 * Class that implement this interface can be executed in a separated thread and
 * by observer by a progress dialog.
 * 
 * @author patapouf
 * 
 */
public interface Operation {
	
	/**
	 * Use to cancel the operation.
	 */
	public void cancel();
	
	/**
	 * Use to start the execute of operation.
	 */
	public void start();

}
