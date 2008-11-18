package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * A DVDDeviceDialog is a specialized Dialog that are use to select a dvd
 * device.
 * 
 * @author patapouf
 * 
 */
public class DVDDeviceDialog extends Dialog {

	/**
	 * Default label width.
	 */
	private static final int LABEL_WIDTH = 500;
	/**
	 * Margin height of the label.
	 */
	private static final int MARGIN_HEIGHT = 20;

	/**
	 * The combo box.
	 */
	Combo deviceCombo;

	/**
	 * The selected dvd device.
	 */
	String device;

	/**
	 * Available devices list.
	 */
	String[] devices;

	/**
	 * The image.
	 */
	Image image;

	/**
	 * Create a new dialog to select the dvd device.
	 * 
	 * @param parentShell
	 *            the parent of this dialog.
	 */
	public DVDDeviceDialog(Shell parentShell) {
		super(parentShell);
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

		newShell.setText(Localization.getString(Localization.DVD_DIALOG_TITLE));

	}

	/**
	 * Creates and returns the contents of the upper part of this dialog (above
	 * the button bar).
	 * <p>
	 * This implementation create a label and a combo box to select the device.
	 * </p>
	 * 
	 * @param parent
	 *            the parent composite to contain the dialog area.
	 * @return the dialog area control.
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
		if (this.image != null) {
			imageLabel.setImage(this.image);
		} else {
			imageLabel.setImage(Display.getCurrent().getSystemImage(
					SWT.ICON_QUESTION));
		}

		Label label = new Label(comp, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));
		label.setText(Localization.getString(Localization.DVD_DIALOG_LABEL));

		this.deviceCombo = new Combo(comp, SWT.NONE);
		this.deviceCombo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		for (int i = 0; i < this.devices.length; i++) {
			this.deviceCombo.add(this.devices[i], i);
		}

		this.deviceCombo.select(0);

		return comp;
	}

	/**
	 * Return the select device in the list or null if the user pressed the
	 * cancel button.
	 * 
	 * @return the select device in the list.
	 */
	public String getDevice() {
		return this.device;
	}

	/**
	 * Return the image.
	 * 
	 * @return the image or null.
	 */
	public Image getImage() {
		return this.image;
	}

	/**
	 * Notifies that the ok button of this dialog has been pressed. This
	 * implementation get the currently selected device in the list.
	 */
	protected void okPressed() {
		int index = this.deviceCombo.getSelectionIndex();
		if (index != -1) {
			this.device = this.deviceCombo.getItem(index);
		}

		super.okPressed();
	}

	/**
	 * Use to set the devices list of the dialog.
	 * 
	 * @param devices
	 *            the list of DVD devices.
	 */
	public void setDeviceList(String[] devices) {
		this.devices = new String[devices.length];
		System.arraycopy(devices, 0, this.devices, 0, devices.length);
	}

	/**
	 * Set the image that will be shown in the dialog.
	 * 
	 * @param image
	 *            the image to show in this dialog or null to display a default
	 *            image.
	 */
	public void setImage(Image image) {
		this.image = image;
	}

}
