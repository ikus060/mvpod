package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog are use by the actions when the operation are too long.
 * <p>
 * This dialog are close when the operation are finish.
 * </p>
 * 
 * @author patapouf
 * 
 */
public class ProgressDialog extends Dialog {
	/**
	 * The label width.
	 */
	private static final int LABEL_WIDTH = 500;
	/**
	 * The margin height.
	 */
	private static final int MARGIN_HEIGHT = 20;

	/**
	 * Image to display or null to display the default image.
	 */
	private Image image;

	/**
	 * The thread to execute the operation.
	 */
	private Thread thread;
	/**
	 * Use to determine if the playing job are finish.
	 */
	private boolean finish = false;
	/**
	 * Operation to execute.
	 */
	private Operation operation;
	/**
	 * Progress message.
	 */
	private String progressMessage;

	/**
	 * Create a new preview dialog.
	 * 
	 * @param parentShell
	 *            the parent of this windows.
	 */
	public ProgressDialog(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * @see org.eclipse.jface.window.Window#close()
	 */
	public boolean close() {

		if (!finish) {
			operation.cancel();
			return false;
		} else {
			return super.close();
		}

	}

	/**
	 * Configures the given shell in preparation for opening this window in it.
	 * <p>
	 * This implementation set the shell title.
	 * </p>
	 * 
	 * @param newShell
	 *            the shell.
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);

		newShell.setText(Localization
				.getString(Localization.PROGRESS_DIALOG_TITLE));

	}

	/**
	 * Creates this window's widgetry in a new top-level shell.
	 * <p>
	 * This implementation start the given thread after all the component
	 * creation;
	 * </p>
	 */
	public void create() {
		super.create();

		if (operation != null) {
			thread = new Thread(new Runnable() {
				public void run() {

					// Start operation
					operation.start();

					// Finish and close the operation
					finish = true;
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							ProgressDialog.this.close();
						}
					});

				}
			});
			thread.start();
		}
	}

	/**
	 * Adds buttons to this dialog's button bar.
	 * <p>
	 * This implementation create a cancel button only.
	 * </p>
	 * 
	 * @param parent
	 *            the button bar composite
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		// create Cancel button
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	/**
	 * Creates and returns the contents of the upper part of this dialog (above
	 * the button bar).
	 * <p>
	 * This implementation create a label and a combo box to select the device.
	 * </p>
	 * 
	 * @param parent
	 *            the parent composite to contain the dialog area
	 * @return the dialog area control
	 */
	protected Control createDialogArea(Composite parent) {

		Composite comp = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = MARGIN_HEIGHT;
		comp.setLayout(layout);
		comp.setLayoutData(new GridData(LABEL_WIDTH, SWT.DEFAULT));

		Label imageLabel = new Label(comp, SWT.NONE);
		imageLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 2));
		if (image != null) {
			imageLabel.setImage(image);
		} else {
			imageLabel.setImage(Display.getCurrent().getSystemImage(
					SWT.ICON_WORKING));
		}

		Label label = new Label(comp, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		label.setText(progressMessage);

		return comp;
	}

	/**
	 * Return the operation.
	 * 
	 * @return the operation.
	 */
	public Operation getOperation() {
		return operation;
	}

	/**
	 * Return the progress message.
	 * 
	 * @return the progress message.
	 */
	public String getProgressMessage() {
		return progressMessage;
	}

	/**
	 * Opens this window, creating it first if it has not yet been created.
	 * <p>
	 * If this window has been configured to block on open (
	 * <code>setBlockOnOpen</code>), this method waits until the window is
	 * closed by the end user, and then it returns the window's return code;
	 * otherwise, this method returns immediately. A window's return codes are
	 * window-specific, although two standard return codes are predefined:
	 * <code>OK</code> and <code>CANCEL</code>.
	 * </p>
	 * 
	 * @return the return code
	 * 
	 * @see #create()
	 */
	public int open() {

		if (operation != null) {
			return super.open();
		} else {
			return CANCEL;
		}

	}

	/**
	 * Set the attached thread to execute and wait for when the dialog will
	 * open.
	 * 
	 * @param job
	 *            the playing job to handle.
	 */
	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	/**
	 * Sets the progress message of the dialog.
	 * 
	 * @param message
	 *            the new progress message.
	 */
	public void setProgressMessage(String message) {
		this.progressMessage = message;
	}

}
