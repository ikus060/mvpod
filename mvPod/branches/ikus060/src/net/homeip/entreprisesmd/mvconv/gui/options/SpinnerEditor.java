package net.homeip.entreprisesmd.mvconv.gui.options;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TypedListener;

/**
 * This component can be use to change value with a spinner.
 * 
 * @author patapouf
 * 
 */
public class SpinnerEditor extends Composite {

	/**
	 * Margin for label layout calculation.
	 */
	private static final int VALUE_MARGIN = 2;

	/**
	 * Scale component use to change the value.
	 */
	private Spinner spinner;

	/**
	 * Label showing the selected value.
	 */
	private Label value;

	/**
	 * Expression to format the display value.
	 */
	private String unitExpression = "%d";

	/**
	 * Selection listener to dispatch event.
	 */
	private Listener listener = new Listener() {
		public void handleEvent(Event event) {

			updateValue();
			SpinnerEditor.this.notifyListeners(event.type, event);

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
	public SpinnerEditor(Composite parent, int style) {
		super(parent, SWT.NONE);

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		super.setLayout(layout);

		spinner = new Spinner(this, style);
		spinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		int[] types = new int[] { SWT.Modify, SWT.Selection,
				SWT.DefaultSelection, SWT.MouseDoubleClick, SWT.MouseDown,
				SWT.MouseUp };
		for (int index = 0; index < types.length; index++) {
			spinner.addListener(types[index], listener);
		}

		value = new Label(this, SWT.NONE);
		value.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

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
	 * Returns the amount that the receiver's value will be modified by when the
	 * up/down (or right/left) arrows are pressed.
	 * 
	 * @return the increment value.
	 */
	public int getIncrement() {
		return spinner.getIncrement();
	}

	/**
	 * Returns the maximum value which the receiver will allow.
	 * 
	 * @return the maximum value.
	 */
	public int getMaximum() {
		return spinner.getMaximum();
	}

	/**
	 * Returns the minimum value which the receiver will allow.
	 * 
	 * @return the minimum value.
	 */
	public int getMinimum() {
		return spinner.getMinimum();
	}

	/**
	 * Returns the 'selection', which is the receiver's position.
	 * 
	 * @return the selection.
	 */
	public int getSelection() {

		return spinner.getSelection();

	}

	/**
	 * Return the format expression use to display the value.
	 * 
	 * @return the format expression.
	 */
	public String getUnitString() {
		return unitExpression;
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
		spinner.setIncrement(value);
	}

	/**
	 * Sets the maximum value that the receiver will allow.
	 * 
	 * @param value
	 *            the maximum value.
	 */
	public void setMaximum(int value) {
		spinner.setMaximum(value);
	}

	/**
	 * Sets the minimum value that the receiver will allow.
	 * 
	 * @param value
	 *            the minimum value.
	 */
	public void setMinimum(int value) {
		spinner.setMinimum(value);
	}

	/**
	 * Sets the 'selection', which is the receiver's value, to the argument
	 * which must be greater than or equal to zero.
	 * 
	 * @param value
	 *            the value
	 */
	public void setSelection(int value) {
		spinner.setSelection(value);
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
		this.unitExpression = unit;
		updateLayoutValue();
	}

	/**
	 * Update the display value
	 */
	private void updateLayoutValue() {

		GC gc = new GC(this);
		Point size = gc.textExtent(unitExpression);
		gc.dispose();

		value.setLayoutData(new GridData(size.x + VALUE_MARGIN, SWT.DEFAULT));

	}

	/**
	 * Update the display value
	 */
	private void updateValue() {

		String formatedString = String.format(unitExpression, this
				.getSelection());
		value.setText(formatedString);
	}

}
