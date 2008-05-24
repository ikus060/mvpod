package net.homeip.entreprisesmd.mvconv.gui;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.ComponentMissingException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.DVDNotAvailableException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.GrabXvPortException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerException;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.PaletteException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * This class are use to display an error message (or warning message) to the
 * user. It's use the SWT framework to show this error and the localization
 * module to diplay the message in the right language.
 * 
 * @author patapouf
 * 
 */
public final class ErrorMessage {

	/**
	 * Private constructor.
	 */
	private ErrorMessage() {

	}

	/**
	 * Open a new message box to the user that display an error message.
	 * 
	 * @param shell
	 *            the parent of this message box.
	 * @param message
	 *            the error message.
	 */
	public static void showError(Shell shell, String message){
		show(shell, message, SWT.ICON_ERROR);
	}
	
	/**
	 * Open a new message box to the user that display the error message.
	 * 
	 * @param shell
	 *            the parent of this message box.
	 * @param stringID
	 *            the string identifier that correspond to the message. This id
	 *            must be a constant in Localization class.
	 */
	public static void showLocalizedError(Shell shell, String stringID) {

		String message = Localization.getString(stringID);
		showError(shell, message);

	}

	/**
	 * Open a new message box to the user that display the error message.
	 * 
	 * @param shell
	 *            the parent of this message box.
	 * @param stringID
	 *            the string identifier that correspond to the message. This id
	 *            must be a constant in Localization class.
	 * @param args
	 *            argument list to localized this message.
	 */
	public static void showLocalizedError(Shell shell, String stringID,
			Object... args) {

		String message = Localization.getString(stringID, args);
		showError(shell, message);

	}

	/**
	 * Open a new message box to the user that display the exception message
	 * corresponding to the type of exception.
	 * 
	 * @param shell
	 *            the parent of this message box.
	 * @param exception
	 *            the mplayer exception
	 * @param defaultID
	 *            the string identifier that correspond to the default message
	 *            in case the exception are not typed.
	 */
	public static void showMPlayerException(Shell shell,
			MPlayerException exception, String defaultID) {

		if (exception instanceof GrabXvPortException) {
			ErrorMessage
					.showLocalizedError(shell, Localization.MPLAYER_GRAP_XV_PORT);
		} else if (exception instanceof DVDNotAvailableException) {
			ErrorMessage.showLocalizedError(shell,
					Localization.MPLAYER_DVDDEVICE_NOT_AVAILABLE,
					((DVDNotAvailableException) exception).getDevice());
		} else if (exception instanceof PaletteException) {
			ErrorMessage.showLocalizedError(shell,
					Localization.MPLAYER_PALETTE_ERROR);
		} else if (exception instanceof ComponentMissingException) {
			ErrorMessage.showLocalizedError(shell,
					Localization.MPLAYER_COMPONENT_MISSING,
					((ComponentMissingException) exception).getComponentName());
		} else {
			ErrorMessage.showLocalizedError(shell, defaultID);
		}
	}

	/**
	 * Open a new message box to the user that display a message.
	 * 
	 * @param shell
	 *            the parent of this message box.
	 * @param message
	 *            the message.
	 * @param style
	 *            the style of the message.
	 * @return the return id.
	 */
	public static int show(Shell shell, String message, int style) {

		if (shell != null) {
			String title = Localization
					.getString(Localization.APPLICATION_NAME);

			MessageBox msg = new MessageBox(shell, style);
			msg.setText(title);
			msg.setMessage(message);
			return msg.open();
		} else {
			System.out.println(message);
		}
		return SWT.CANCEL;

	}

}
