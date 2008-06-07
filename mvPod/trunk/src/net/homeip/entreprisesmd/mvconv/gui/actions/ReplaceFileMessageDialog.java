package net.homeip.entreprisesmd.mvconv.gui.actions;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.icons.IconLoader;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * This dialog are use to ask the user to replace a file.
 * 
 * @author patapouf
 * 
 */
public class ReplaceFileMessageDialog extends MessageDialogWithToggle {

	/**
	 * Cancel button index.
	 */
	public static final int CANCEL_ID = 0;
	/**
	 * Replace button index.
	 */
	public static final int REPLACE_ID = 1;

	/**
	 * Return button labels.
	 * 
	 * @return button labels.
	 */
	private static String[] dialogButtonLabels() {
		String cancel = Localization
				.getString(Localization.REPLACE_FILE_DIALOG_CANCEL);
		String replace = Localization
				.getString(Localization.REPLACE_FILE_DIALOG_REPLACE);
		String[] labels = new String[2];
		labels[CANCEL_ID] = cancel;
		labels[REPLACE_ID] = replace;
		return labels;
	}

	/**
	 * Return dialog message.
	 * 
	 * @param filename
	 *            the file name to replace
	 * @return the message.
	 */
	private static String dialogMessage(String filename) {
		return Localization.getString(Localization.REPLACE_FILE_DIALOG_MESSAGE,
				filename);
	}

	/**
	 * Return dialog title.
	 * 
	 * @return dialog title.
	 */
	private static String dialogTitle() {
		return Localization.getString(Localization.APPLICATION_NAME);
	}

	/**
	 * Return the dialog icon.
	 * 
	 * @return the icon.
	 */
	private static Image dialogTitleImage() {
		return IconLoader.loadIcon(IconLoader.ICON_APP_16).createImage();
	}

	/**
	 * Return the toggle message.
	 * 
	 * @return the toggle message.
	 */
	private static String toggleMessage() { 
		return Localization.getString(Localization.REPLACE_FILE_DIALOG_ALWAYS);
	}

	/**
	 * Create a new message dialog.
	 * 
	 * @param parentShell
	 *            the parent of this dialog.
	 * @param filename
	 *            the file name of file to replace.
	 */
	public ReplaceFileMessageDialog(Shell parentShell, String filename,
			boolean toggleState) {

		super(parentShell, dialogTitle(), dialogTitleImage(),
				dialogMessage(filename), MessageDialog.WARNING,
				dialogButtonLabels(), CANCEL_ID, toggleMessage(), toggleState);

	}

    /**
     * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
     */
    protected void buttonPressed(int buttonId) {
        setReturnCode(buttonId);
        close();
        
        if ( getToggleState() && getPrefStore() != null && getPrefKey() != null) {
            switch (buttonId) {
            case REPLACE_ID:
            	getPrefStore().setValue(getPrefKey(), ALWAYS);
                break;
            }
        }
        
    }
	
	/**
	 * This implementation try to by pass implementation of
	 * MessageDialogWithToggle.
	 * 
	 * @see org.eclipse.jface.dialogs.MessageDialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		Button[] buttons = new Button[getButtonLabels().length];
		for (int i = 0; i < getButtonLabels().length; i++) {
			String label = getButtonLabels()[i];
			Button button = createButton(parent, i, label,
					getDefaultButtonIndex() == i);
			buttons[i] = button;
		}
		setButtons(buttons);
	}

}
