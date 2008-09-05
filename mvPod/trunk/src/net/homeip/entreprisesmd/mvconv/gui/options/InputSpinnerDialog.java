package net.homeip.entreprisesmd.mvconv.gui.options;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class InputSpinnerDialog extends Dialog {
	/**
	 * The title of the dialog.
	 */
	private String title;

	/**
	 * The message to display, or <code>null</code> if none.
	 */
	private String message;

	/**
	 * The input value; the empty string by default.
	 */
	private Double value;

	/**
	 * The input validator, or <code>null</code> if none.
	 */
	private IInputValidator validator;

	/**
	 * Ok button widget.
	 */
	private Button okButton;

	/**
	 * Input text widget.
	 */
	private DigitsSpinner spinner;

	/**
	 * Error message label widget.
	 */
	private Text errorMessageText;

	/**
	 * Error message string.
	 */
	private String errorMessage;

	/**
	 * Digits value for spinner component.
	 */
	private int digits = 0;
	/**
	 * Increment value for spinner component.
	 */
	private double increment = 1;
	/**
	 * Minimum value for spinner component.
	 */
	private Double minimum = null;
	/**
	 * Maximum value for spinner component.
	 */
	private Double maximum = null;
	/**
	 * Unit value for spinner component.
	 */
	private String unitString = ""; //$NON-NLS-1$

	/**
	 * Creates an input dialog with OK and Cancel buttons. Note that the dialog
	 * will have no visual representation (no widgets) until it is told to open.
	 * <p>
	 * Note that the <code>open</code> method blocks for input dialogs.
	 * </p>
	 * 
	 * @param parentShell
	 *            the parent shell, or <code>null</code> to create a top-level
	 *            shell
	 * @param dialogTitle
	 *            the dialog title, or <code>null</code> if none
	 * @param dialogMessage
	 *            the dialog message, or <code>null</code> if none
	 * @param initialValue
	 *            the initial input value
	 * @param validator
	 *            an input validator, or <code>null</code> if none
	 */
	public InputSpinnerDialog(Shell parentShell, String dialogTitle,
			String dialogMessage, Double initialValue, IInputValidator validator) {
		super(parentShell);
		this.title = dialogTitle;
		this.message = dialogMessage;
		if (initialValue == null) {
			throw new NullPointerException();
		}
		this.value = initialValue;
		this.validator = validator;
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			this.value = this.spinner.getSelection();
		} else {
			this.value = null;
		}
		super.buttonPressed(buttonId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (this.title != null) {
			shell.setText(this.title);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Cancel buttons by default
		this.okButton = createButton(parent, IDialogConstants.OK_ID,
				IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		// do this here because setting the text will set enablement on the ok
		// button
		this.spinner.setFocus();
		if (this.value != null) {
			this.spinner.setSelection(this.value);
		}
	}

	/*
	 * (non-Javadoc) Method declared on Dialog.
	 */
	protected Control createDialogArea(Composite parent) {
		// create composite
		Composite composite = (Composite) super.createDialogArea(parent);
		// create message
		if (this.message != null) {
			Label label = new Label(composite, SWT.WRAP);
			label.setText(this.message);
			GridData data = new GridData(GridData.GRAB_HORIZONTAL
					| GridData.GRAB_VERTICAL | GridData.HORIZONTAL_ALIGN_FILL
					| GridData.VERTICAL_ALIGN_CENTER);
			data.widthHint = convertHorizontalDLUsToPixels(IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH);
			label.setLayoutData(data);
			label.setFont(parent.getFont());
		}
		this.spinner = new DigitsSpinner(composite, SWT.BORDER);
		this.spinner.setDigits(this.digits);
		if (this.minimum != null) {
			this.spinner.setMinimum(this.minimum);
		}
		if (this.maximum != null) {
			this.spinner.setMaximum(this.maximum);
		}
		this.spinner.setIncrement(this.increment);
		this.spinner.setUnitString(this.unitString);

		this.spinner.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false));
		this.spinner.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				validateInput();
			}
		});
		this.errorMessageText = new Text(composite, SWT.READ_ONLY | SWT.WRAP);
		this.errorMessageText.setLayoutData(new GridData(
				GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
		this.errorMessageText.setBackground(this.errorMessageText.getDisplay()
				.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		// Set the error message text
		// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=66292
		setErrorMessage(this.errorMessage);

		applyDialogFont(composite);
		return composite;
	}

	/**
	 * Returns the number of decimal places used by the receiver.
	 * 
	 * @return the digits.
	 */
	public int getDigits() {
		return this.digits;
	}

	/**
	 * Returns the error message label.
	 * 
	 * @return the error message label
	 * @deprecated use setErrorMessage(String) instead
	 */
	@Deprecated
	protected Label getErrorMessageLabel() {
		return null;
	}

	/**
	 * Returns the amount that the receiver's value will be modified by when the
	 * up/down (or right/left) arrows are pressed.
	 * 
	 * @return the increment value.
	 */
	public double getIncrement() {
		return this.increment;
	}

	/**
	 * Returns the maximum value which the receiver will allow.
	 * 
	 * @return the maximum value.
	 */
	public double getMaximum() {
		return this.maximum;
	}

	/**
	 * Returns the minimum value which the receiver will allow.
	 * 
	 * @return the minimum value.
	 */
	public double getMinimum() {
		return this.minimum;
	}

	/**
	 * Returns the ok button.
	 * 
	 * @return the ok button
	 */
	protected Button getOkButton() {
		return this.okButton;
	}

	/**
	 * Returns the spinner area.
	 * 
	 * @return the spinner area
	 */
	protected DigitsSpinner getSpinner() {
		return this.spinner;
	}

	/**
	 * Return the format expression use to display the value.
	 * 
	 * @return the format expression.
	 */
	public String getUnitString() {
		return this.unitString;
	}

	/**
	 * Returns the validator.
	 * 
	 * @return the validator
	 */
	protected IInputValidator getValidator() {
		return this.validator;
	}

	/**
	 * Returns the string typed into this input dialog.
	 * 
	 * @return the input string
	 */
	public Double getValue() {
		return this.value;
	}

	/**
	 * Validates the input.
	 * <p>
	 * The default implementation of this framework method delegates the request
	 * to the supplied input validator object; if it finds the input invalid,
	 * the error message is displayed in the dialog's message line. This hook
	 * method is called whenever the text changes in the input field.
	 * </p>
	 */
	protected void validateInput() {
		String invalidMessage = null;
		if (this.validator != null) {
			invalidMessage = this.validator.isValid(Double.toString(this.spinner
					.getSelection()));
		}
		// Bug 16256: important not to treat "" (blank error) the same as null
		// (no error)
		setErrorMessage(invalidMessage);
	}

	/**
	 * Sets the number of decimal places used by the receiver.
	 * <p>
	 * The digit setting is used to allow for floating point values in the
	 * receiver. For example, to set the selection to a floating point value of
	 * 1.37 call setDigits() with a value of 2 and setSelection() with a value
	 * of 137. Similarly, if getDigits() has a value of 2 and getSelection()
	 * returns 137 this should be interpreted as 1.37. This applies to all
	 * numeric APIs.
	 * </p>
	 * 
	 * @param value
	 *            the new digits (must be greater than or equal to zero)
	 */
	public void setDigits(int value) {
		this.digits = value;
	}

	/**
	 * Sets or clears the error message. If not <code>null</code>, the OK
	 * button is disabled.
	 * 
	 * @param errorMessage
	 *            the error message, or <code>null</code> to clear
	 * @since 3.0
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		if (this.errorMessageText != null
				&& !this.errorMessageText.isDisposed()) {
			this.errorMessageText
					.setText(errorMessage == null ? " \n " : errorMessage); //$NON-NLS-1$
			// Disable the error message text control if there is no error, or
			// no error text (empty or whitespace only). Hide it also to avoid
			// color change.
			// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=130281
			boolean hasError = errorMessage != null
					&& (StringConverter.removeWhiteSpaces(errorMessage))
							.length() > 0;
			this.errorMessageText.setEnabled(hasError);
			this.errorMessageText.setVisible(hasError);
			this.errorMessageText.getParent().update();
			// Access the ok button by id, in case clients have overridden
			// button creation.
			// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=113643
			Control button = getButton(IDialogConstants.OK_ID);
			if (button != null) {
				button.setEnabled(errorMessage == null);
			}
		}
	}

	/**
	 * Sets the amount that the receiver's value will be modified by when the
	 * up/down (or right/left) arrows are pressed to the argument, which must be
	 * at least one.
	 * 
	 * @param value
	 *            the increment value.
	 */
	public void setIncrement(double value) {
		this.increment = value;
	}

	/**
	 * Sets the maximum value that the receiver will allow.
	 * 
	 * @param value
	 *            the maximum value.
	 */
	public void setMaximum(double value) {
		this.maximum = value;
	}

	/**
	 * Sets the minimum value that the receiver will allow.
	 * 
	 * @param value
	 *            the minimum value.
	 */
	public void setMinimum(double value) {
		this.minimum = value;
	}

	/**
	 * Sets the format string to disply the value. This string will be use to
	 * display the value by using the Formatter class. The string must contain
	 * %d.
	 * 
	 * @param unit
	 *            the format string.
	 */
	public void setUnitString(String unit) {
		this.unitString = unit;
	}

}
