package net.homeip.entreprisesmd.mvconv.gui.options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.TypedListener;

/**
 * This component can be use to change the bitrate value with a scaler.
 * 
 * @author patapouf
 * 
 */
public class ScaleEditor extends Composite {

	/**
	 * Margin for label layout calculation.
	 */
	private static final int VALUE_MARGIN = 2;

	/**
	 * Increment value.
	 */
	private int incrementValue = 1;

	/**
	 * Minimum value.
	 */
	private int minimumValue = 0;

	/**
	 * Maximum value.
	 */
	private int maximumValue = 100;

	/**
	 * Page increment.
	 */
	private int pageIncrementValue = -1;

	/**
	 * Scale component use to change the value.
	 */
	private Scale scale;

	/**
	 * Label showing the selected value.
	 */
	private Label value;

	/**
	 * Expression to format the display value.
	 */
	private String formatExpression = "%d"; //$NON-NLS-1$

	/**
	 * Selection listener to dispatch event.
	 */
	private Listener listener = new Listener() {
		public void handleEvent(Event event) {

			updateValue();
			ScaleEditor.this.notifyListeners(event.type, event);

		}
	};

	/**
	 * Create a new bitrate editor.
	 * 
	 * @param parent
	 *            the composite parent.
	 * @param style
	 *            the style of this widgets.
	 */
	public ScaleEditor(Composite parent, int style) {
		super(parent, style);

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		super.setLayout(layout);

		scale = new Scale(this, SWT.HORIZONTAL);
		scale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		int[] types = new int[] { SWT.Selection, SWT.DefaultSelection,
				SWT.MouseDoubleClick, SWT.MouseDown, SWT.MouseUp, SWT.KeyUp,
				SWT.KeyDown };
		for (int index = 0; index < types.length; index++) {
			scale.addListener(types[index], listener);
		}

		value = new Label(this, SWT.NONE);
		value.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		this.setTabList(new Control[] { scale });

		updateIncrementSetup();
		updateLayoutValue();
		updateValue();

	}

	/**
	 * Adds the listener to the collection of listeners who will be notified
	 * when the user changes the receiver's value, by sending it one of the
	 * messages defined in the SelectionListener interface.
	 * 
	 * @param listener
	 *            the listener which should be notified.
	 */
	public void addSelectionListener(SelectionListener listener) {
		checkWidget();
		if (listener == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		TypedListener typedListener = new TypedListener(listener);
		addListener(SWT.Selection, typedListener);
		addListener(SWT.DefaultSelection, typedListener);
	}

	/**
	 * Return the format expression use to display the value.
	 * 
	 * @return the format expression.
	 */
	public String getFormatValue() {
		return formatExpression;
	}

	/**
	 * Returns the amount that the receiver's value will be modified by when the
	 * up/down (or right/left) arrows are pressed.
	 * 
	 * @return the increment value.
	 */
	public int getIncrement() {
		return incrementValue;
	}

	/**
	 * Returns the maximum value which the receiver will allow.
	 * 
	 * @return the maximum value.
	 */
	public int getMaximum() {
		return maximumValue;
	}

	/**
	 * Returns the minimum value which the receiver will allow.
	 * 
	 * @return the minimum value.
	 */
	public int getMinimum() {
		return minimumValue;
	}

	/**
	 * Returns the amount that the receiver's value will be modified by when the
	 * up/down (or right/left) arrows are pressed.
	 * 
	 * @return the increment value.
	 */
	public int getPageIncrement() {
		if (pageIncrementValue != -1) {
			return pageIncrementValue;
		} else {
			return incrementValue;
		}
	}

	/**
	 * Returns the 'selection', which is the receiver's position.
	 * 
	 * @return the selection.
	 */
	public int getSelection() {

		return scale.getSelection() - (scale.getSelection() % incrementValue);

	}

	/**
	 * Removes the listener from the collection of listeners who will be
	 * notified when the user changes the receiver's value.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified.
	 */
	public void removeSelectionListener(SelectionListener listener) {
		checkWidget();
		if (listener == null)
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		removeListener(SWT.Selection, listener);
		removeListener(SWT.DefaultSelection, listener);
	}

	/**
	 * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
	 */
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		value.setEnabled(enabled);
	}

	/**
	 * Sets the format string to disply the value. This string will be use to
	 * display the value by using the Formatter class. The string must contain
	 * %d.
	 * 
	 * @param format
	 *            the format string.
	 */
	public void setFormatValue(String format) {
		this.formatExpression = format;
		updateLayoutValue();
		updateValue();
	}

	/**
	 * @see org.eclipse.swt.widgets.Composite#setLayout(org.eclipse.swt.widgets.Layout)
	 */
	public void setLayout(Layout layout) {
		// Do nothing
	}

	/**
	 * Sets the amount that the receiver's value will be modified by when the
	 * up/down (or right/left) arrows are pressed to the argument, which must be
	 * at least one.
	 * 
	 * @param value
	 *            the increment value.
	 */
	public void setIncrement(int value) {

		if (value <= 0) {
			throw new IllegalArgumentException("Invalid increment value " //$NON-NLS-1$
					+ value);
		}

		this.incrementValue = value;

		updateIncrementSetup();
	}

	/**
	 * Sets the amount that the receiver's value will be modified by when the
	 * up/down (or right/left) arrows are pressed to the argument, which must be
	 * at least one.
	 * 
	 * @param value
	 *            the increment value.
	 */
	public void setPageIncrement(int value) {

		if (value <= 0) {
			throw new IllegalArgumentException("Invalid page increment value " //$NON-NLS-1$
					+ value);
		}

		this.pageIncrementValue = value;

		updateIncrementSetup();
	}

	/**
	 * Sets the maximum value that the receiver will allow.
	 * 
	 * @param value
	 *            the maximum value.
	 */
	public void setRange(int min, int max) {
		if (max <= min) {
			throw new IllegalArgumentException("Invalid min/max value, min " //$NON-NLS-1$
					+ min + ", max " + max); //$NON-NLS-1$
		}

		this.minimumValue = min;
		this.maximumValue = max;

		updateIncrementSetup();
		updateLayoutValue();
		updateValue();
	}

	/**
	 * Sets the 'selection', which is the receiver's value, to the argument
	 * which must be greater than or equal to zero.
	 * 
	 * @param value
	 *            the value
	 */
	public void setSelection(int value) {

		if (value % incrementValue != 0) {
			throw new IllegalArgumentException(
					"Invalid selection value. Not a multiple of increment"); //$NON-NLS-1$
		}

		scale.setSelection(value);
		updateValue();
	}

	/**
	 * Update the display value
	 */
	void updateLayoutValue() {

		String formatedString = String.format(formatExpression, scale
				.getMaximum());
		GC gc = new GC(this);
		Point size = gc.textExtent(formatedString);
		gc.dispose();

		value.setLayoutData(new GridData(size.x + VALUE_MARGIN, SWT.DEFAULT));

	}

	/**
	 * Update the increment setup.
	 */
	void updateIncrementSetup() {

		scale.setMinimum(minimumValue);
		scale.setMaximum(maximumValue);
		scale.setMinimum(minimumValue);
		scale.setMaximum(maximumValue);

		scale.setIncrement(incrementValue);
		if (pageIncrementValue != -1) {
			scale.setPageIncrement(pageIncrementValue);
		} else {
			scale.setPageIncrement(incrementValue);
		}

	}

	/**
	 * Update the display value
	 */
	void updateValue() {

		String formatedString = String.format(formatExpression, this
				.getSelection());
		value.setText(formatedString);
	}

}
