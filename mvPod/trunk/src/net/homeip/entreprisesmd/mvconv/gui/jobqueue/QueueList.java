package net.homeip.entreprisesmd.mvconv.gui.jobqueue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TypedListener;

/**
 * This component display a list of QueueItem.
 * 
 * @author patapouf
 * 
 */
public class QueueList extends Composite {

	/**
	 * Composite that contain QueueItem.
	 */
	protected Composite comp;

	/**
	 * Scrolled composite to scroll the <code>comp</code> composite.
	 */
	private ScrolledComposite scroll;

	/**
	 * Create a new Queue list.
	 * 
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public QueueList(Composite parent, int style) {
		super(parent, style);

		this.setLayout(new FillLayout());

		// Setup scrolled composite
		scroll = new ScrolledComposite(this, SWT.V_SCROLL | SWT.H_SCROLL);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);

		comp = new Composite(scroll, SWT.DOUBLE_BUFFERED);
		scroll.setContent(comp);

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 1;
		layout.horizontalSpacing = 1;
		comp.setLayout(layout);
		comp.setBackground(Display.getDefault().getSystemColor(
				SWT.COLOR_LIST_BACKGROUND));
		comp.setForeground(Display.getDefault().getSystemColor(
				SWT.COLOR_LIST_FOREGROUND));

		// Draw some line between each item
		comp.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event event) {

				GC gc = event.gc;
				Control[] childs = comp.getChildren();
				for (int index = 0; index < childs.length; index++) {
					Rectangle rect = childs[index].getBounds();

					gc.drawFocus(rect.x, rect.y + rect.height, rect.width, 0);
				}

			}
		});

	}

	/**
	 * Adds the listener to receive events.
	 * <p>
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addSelectionListener(SelectionListener listener) {
		checkWidget();
		if (listener == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		TypedListener typedListener = new TypedListener(listener);
		addListener(SWT.Selection, typedListener);
		addListener(SWT.DefaultSelection, typedListener);
	}

	/**
	 * Create a new queue item.
	 * 
	 * @param item
	 *            the item to create
	 * @param index
	 *            the index where to create this item.
	 */
	void createItem(QueueItem item, int index) {

		item.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		comp.layout();

		scroll.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));

	}

	/**
	 * Remove an item.
	 * 
	 * @param item
	 *            the item to remove.
	 */
	void destroyItem(QueueItem item) {

		comp.layout();

		scroll.setMinSize(comp.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		scroll.layout();

	}

	/**
	 * Return the tab that is located at the specified index.
	 * 
	 * @param index
	 *            the index of the tab item
	 * @return the item at the specified index
	 */
	public QueueItem getItem(int index) {
		return (QueueItem) comp.getChildren()[index];
	}

	/**
	 * Return the number of tabs in the folder.
	 * 
	 * @return the number of tabs in the folder
	 */
	public int getItemCount() {
		return comp.getChildren().length;
	}

	/**
	 * Return the tab items.
	 * 
	 * @return the tab items
	 */
	public QueueItem[] getItems() {
		return (QueueItem[]) comp.getChildren();
	}

	/**
	 * Return the selected tab item, or an empty array if there is no selection.
	 * 
	 * @return the selected tab item
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS when called from the
	 *                wrong thread</li>
	 *                <li>ERROR_WIDGET_DISPOSED when the widget has been
	 *                disposed</li>
	 *                </ul>
	 */
	/*
	 * public QueueItem getSelection() { //checkWidget(); if (selectedIndex ==
	 * -1) return null; return items[selectedIndex]; }
	 */
	/**
	 * Return the index of the selected tab item, or -1 if there is no
	 * selection.
	 * 
	 * @return the index of the selected tab item or -1
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS when called from the
	 *                wrong thread</li>
	 *                <li>ERROR_WIDGET_DISPOSED when the widget has been
	 *                disposed</li>
	 *                </ul>
	 */
	/*
	 * public int getSelectionIndex() { //checkWidget(); return selectedIndex; }
	 */

	/**
	 * Return the index of the specified tab or -1 if the tab is not in the
	 * receiver.
	 * 
	 * @param item
	 *            the tab item for which the index is required
	 * 
	 * @return the index of the specified tab item or -1
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS when called from the
	 *                wrong thread</li>
	 *                <li>ERROR_WIDGET_DISPOSED when the widget has been
	 *                disposed</li>
	 *                </ul>
	 */
	/*
	 * public int indexOf(DividerItem item) { checkWidget(); if (item == null) {
	 * SWT.error(SWT.ERROR_NULL_ARGUMENT); } for (int i = 0; i < items.length;
	 * i++) { if (items[i] == item) return i; } return -1; }
	 */

	/*
	 * void onDispose(Event event) { removeListener(SWT.Dispose, listener);
	 * notifyListeners(SWT.Dispose, event); event.type = SWT.None;
	 * 
	 * inDispose = true;
	 * 
	 * int length = items.length; for (int i = 0; i < length; i++) { if
	 * (items[i] != null) { items[i].dispose(); } } }
	 */

	/**
	 * Removes the listener.
	 * 
	 * @param listener
	 *            the listener
	 * 
	 */
	public void removeSelectionListener(SelectionListener listener) {
		checkWidget();
		if (listener == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		removeListener(SWT.Selection, listener);
		removeListener(SWT.DefaultSelection, listener);
	}

	/**
	 * Set the selection to the tab at the specified index.
	 * 
	 * @param index
	 *            the index of the tab item to be selected
	 */
	public void setSelection(int index) {
		// TODO, implement this function
	}

}
