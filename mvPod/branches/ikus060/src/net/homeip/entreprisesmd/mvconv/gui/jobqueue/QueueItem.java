package net.homeip.entreprisesmd.mvconv.gui.jobqueue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;

/**
 * Instances of this class represent an item within the queue list that display
 * the progress of a task.
 * 
 * @author patapouf
 */
public class QueueItem extends Composite {

	/**
	 * Maximum percent value.
	 */
	private static final int MAX_PERCENT = 100;
	/**
	 * Width of the right column.
	 */
	private static final int RIGHT_COLUMN_WIDTH = 75;

	/**
	 * Parent of this queue item.
	 */
	private QueueList parent;

	/**
	 * The description label.
	 */
	private CLabel descriptionLabel;

	/**
	 * The progress description Label.
	 */
	private Label progressDescriptionLabel;

	/**
	 * The spacer label.
	 */
	private Label spacerLabel;

	/**
	 * Link to execute the actions.
	 */
	private Link action1, action2;

	/**
	 * The progress bar.
	 */
	private ProgressBar progressBar;

	/**
	 * Font with bold style.
	 */
	private Font boldFont;

	/**
	 * Collection of listener.
	 */
	private List<QueueItemListener> queueItemListeners = new ArrayList<QueueItemListener>();

	/**
	 * Constructs a new instance of this class given its parent (which must be a
	 * Queue) and a style value describing its behavior and appearance. The item
	 * is added to the end of the items maintained by its parent.
	 * 
	 * @param parent
	 *            a Queue which will be the parent of the new instance (cannot
	 *            be null).
	 * @param style
	 *            the style of control to construct.
	 */
	public QueueItem(QueueList parent, int style) {
		this(parent, SWT.NONE, -1);
	}

	/**
	 * Constructs a new instance of this class given its parent (which must be a
	 * Queue), a style value describing its behavior and appearance, and the
	 * index at which to place it in the items maintained by its parent.
	 * 
	 * @param parent
	 *            a Queue which will be the parent of the new instance (cannot
	 *            be null).
	 * @param style
	 *            the style of control to construct.
	 * @param index
	 *            the zero-relative index to store the receiver in its parent.
	 */
	public QueueItem(QueueList parent, int style, int index) {

		super(parent.comp, SWT.NONE);
		this.parent = parent;

		// Create font
		FontData[] boldedFontData = getFont().getFontData();
		for (int fontIndex = 0; fontIndex < boldedFontData.length; fontIndex++) {
			boldedFontData[fontIndex].setStyle(SWT.BOLD);
		}
		boldFont = new Font(Display.getCurrent(), boldedFontData);

		super.setLayout(new GridLayout(2, false));

		descriptionLabel = new CLabel(this, SWT.NONE) {
			public Point computeSize (int wHint, int hHint, boolean changed){
				Point size = super.computeSize(wHint, hHint, changed);
				return new Point(0,size.y);
			}
		};
		descriptionLabel.setFont(boldFont);
		descriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false));

		// Action text
		action1 = new Link(this, SWT.NONE);
		action1.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));

		// Progress bar
		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		progressBar.setMinimum(0);
		progressBar.setMaximum(MAX_PERCENT);
		progressBar.setVisible(false);

		// ACtion text
		action2 = new Link(this, SWT.NONE);
		action2.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false));

		// Progress description
		progressDescriptionLabel = new Label(this, SWT.NONE);
		progressDescriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, false));

		spacerLabel = new Label(this, SWT.NONE);
		GridData spacerLayoutData = new GridData(SWT.RIGHT, SWT.FILL, false,
				false);
		spacerLayoutData.widthHint = RIGHT_COLUMN_WIDTH;
		spacerLabel.setLayoutData(spacerLayoutData);

		installListener();

		setBackground(Display.getDefault().getSystemColor(
				SWT.COLOR_LIST_BACKGROUND));
		setForeground(Display.getDefault().getSystemColor(
				SWT.COLOR_LIST_FOREGROUND));
		
		// Create item with parent
		parent.createItem(this, index);

		// Disposing font
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				boldFont.dispose();
				boldFont = null;
			}
		});

	}

	/**
	 * Adds the listener to the collection of listeners who will be notified
	 * when the user changes the receiver's selection, by sending it one of the
	 * messages defined in the SelectionListener interface.
	 * 
	 * @param listener
	 *            the listener which should be notified when the user changes
	 *            the receiver's selection
	 */
	public void addQueueItemListener(QueueItemListener listener) {

		queueItemListeners.add(listener);

	}

	/**
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	public void dispose() {
		if (isDisposed()) {
			return;
		}

		super.dispose();

		parent.destroyItem(this);

		parent = null;
	}

	/**
	 * Return a short description of the action.
	 * 
	 * @return the description of the action.
	 */
	public String getAction1Description() {
		return action1.getText();
	}

	/**
	 * Return a short description of the action.
	 * 
	 * @return the description of the action.
	 */
	public String getAction2Description() {
		return action2.getText();
	}

	/**
	 * Return the item description.
	 * 
	 * @return the item description.
	 */
	public String getDescription() {
		return descriptionLabel.getText();
	}

	/**
	 * Return the item percent completed.
	 * 
	 * @return the percent completed.
	 */
	public int getPercentCompleted() {
		return progressBar.getSelection();
	}

	/**
	 * Return the progress Description.
	 * 
	 * @return the progress description.
	 */
	public String getProgressDescription() {
		return progressDescriptionLabel.getText();
	}

	/**
	 * Add all listener to every component.
	 */
	private void installListener() {

		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				event.widget = QueueItem.this;
				QueueItem.this.notifyListeners(event.type, event);
			}
		};

		Listener actionListener = new Listener() {
			public void handleEvent(Event event) {

				int action = 0;
				if (event.widget == action1) {
					action = 1;
				}
				if (event.widget == action2) {
					action = 2;
				}
				event.widget = QueueItem.this;

				Iterator<QueueItemListener> iter = queueItemListeners
						.iterator();
				while (iter.hasNext()) {
					iter.next().actionSelected(action,
							new SelectionEvent(event));
				}
			}
		};

		int[] eventType = new int[] { SWT.DragDetect, SWT.KeyDown, SWT.KeyUp,
				SWT.MenuDetect, SWT.MouseDoubleClick, SWT.MouseDown,
				SWT.MouseUp, SWT.MouseWheel };
		for (int eventIndex = 0; eventIndex < eventType.length; eventIndex++) {
			descriptionLabel.addListener(eventType[eventIndex], listener);
			progressDescriptionLabel.addListener(eventType[eventIndex],
					listener);
			progressBar.addListener(eventType[eventIndex], listener);
		}

		action1.addListener(SWT.Selection, actionListener);
		action1.addListener(SWT.DefaultSelection, actionListener);

		action2.addListener(SWT.Selection, actionListener);
		action2.addListener(SWT.DefaultSelection, actionListener);

	}

	/**
	 * Removes the listener from the collection of listeners who will be
	 * notified when the user changes the receiver's selection.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified.
	 */
	public void removeQueueItemListener(QueueItemListener listener) {
		queueItemListeners.remove(listener);
	}

	/**
	 * Set the description of the action in one or two word.
	 * 
	 * @param actionDescription
	 *            the description of the action.
	 */
	public void setAction1Description(String actionDescription) {
		action1.setText("<a>" + actionDescription + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$
		this.layout();
	}

	/**
	 * Set the description of the action in one or two word.
	 * 
	 * @param actionDescription
	 *            the description of the action.
	 */
	public void setAction2Description(String actionDescription) {
		action2.setText("<a>" + actionDescription + "</a>"); //$NON-NLS-1$ //$NON-NLS-2$
		this.layout();
	}

	/**
	 * @see org.eclipse.swt.widgets.Control#setBackground(org.eclipse.swt.graphics.Color)
	 */
	public void setBackground(Color color) {
		descriptionLabel.setBackground(color);
		progressDescriptionLabel.setBackground(color);
		action1.setBackground(color);
		action2.setBackground(color);
		spacerLabel.setBackground(color);
		super.setBackground(color);
	}

	/**
	 * Set the item description. Describe in few word the job. i.e. : Convert
	 * image myimage.png to convertedimage.jpg, Downloading myfile.zip, etc.
	 * 
	 * @param description
	 *            the new item description.
	 */
	public void setDescription(String description) {
		descriptionLabel.setText(description);
	}

	/** 
	 * @see org.eclipse.swt.widgets.Composite#setLayout(org.eclipse.swt.widgets.Layout)
	 */
	public void setLayout(Layout layout) {
		// Do nothing
	}

	/**
	 * @see org.eclipse.swt.widgets.Control#setForeground(org.eclipse.swt.graphics.Color)
	 */
	public void setForeground(Color color) {
		descriptionLabel.setForeground(color);
		progressDescriptionLabel.setForeground(color);
		action1.setForeground(color);
		action2.setForeground(color);
		spacerLabel.setForeground(color);
		super.setForeground(color);
	}

	/**
	 * Set the percent completed. To hide the progress bar, set the value to -1.
	 * 
	 * @param percent
	 *            the new percent completed value.
	 */
	public void setPercentCompleted(int percent) {
		if (percent < -1 || percent > MAX_PERCENT + 1) {
			throw new IllegalArgumentException("Invalid percent value " //$NON-NLS-1$
					+ percent);
		}
		if (percent == -1) {
			progressBar.setVisible(false);
		} else {
			progressBar.setVisible(true);
		}
		progressBar.setSelection(percent);
	}

	/**
	 * Set the progress description. Can be use to give more detail on the
	 * progress like the rate of data being treat.
	 * 
	 * @param description
	 *            the new progress description
	 */
	public void setProgressDescription(String description) {
		progressDescriptionLabel.setText(description);
	}

}
