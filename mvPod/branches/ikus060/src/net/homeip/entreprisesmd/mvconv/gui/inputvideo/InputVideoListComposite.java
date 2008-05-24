package net.homeip.entreprisesmd.mvconv.gui.inputvideo;

import net.homeip.entreprisesmd.mvconv.core.VideoInfoFormater;
import net.homeip.entreprisesmd.mvconv.core.video.Video;
import net.homeip.entreprisesmd.mvconv.core.video.VideoList;
import net.homeip.entreprisesmd.mvconv.core.video.VideoListObserver;
import net.homeip.entreprisesmd.mvconv.gui.IViewPart;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * This composite interface are intent to show a list of all input video.
 * 
 * @author patapouf
 * 
 */
public class InputVideoListComposite extends Composite implements IViewPart {

	/**
	 * The property key to retrieve the os value.
	 */
	private static final String OS_NAME = "os.name";
	/**
	 * Linux OS name.
	 */
	private static final String OS_NAME_LINUX = "Linux";

	/**
	 * Data identifier.
	 */
	private static final String VIDEO_DATA = "InputVideoData";

	/**
	 * Usage identifier.
	 */
	private static final String USED = "Used";

	/**
	 * The tab folder control.
	 */
	private CTabFolder tabFolder;

	/**
	 * The input video to observer.
	 */
	private VideoList videoList;

	/**
	 * The view site.
	 */
	private IViewSite site;

	/**
	 * The video list observer.
	 */
	private VideoListObserver inputVideoListObserver = new VideoListObserver() {
		public void listHasChanged(VideoList list) {
			updateList(list);
		}
	};

	/**
	 * Listener to tabFolder event.
	 */
	private CTabFolder2Listener tabFolderListener = new CTabFolder2Adapter() {
		public void close(CTabFolderEvent event) {
			CTabItem item = (CTabItem) event.item;
			Video video = (Video) item.getData(VIDEO_DATA);
			videoList.removeVideo(video);
		}
	};

	/**
	 * Selection listener to the viewer component.
	 */
	private SelectionListener selectionListener = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			Video video = (Video) ((CTabItem) e.item).getData(VIDEO_DATA);
			if (video != null) {
				setActionContext(video);
			}
		}
	};

	/**
	 * Create a new InputVideoListComposite class.
	 * 
	 * @param parent
	 *            parent of this composition.
	 * @param style
	 *            the style of this composite.
	 */
	public InputVideoListComposite(Composite parent, int style) {
		super(parent, SWT.NONE);
	}

	/**
	 * Return the TabItem for a given input video object.
	 * 
	 * @param video
	 *            the input video object.
	 * @return the associated TabItem.
	 */
	private CTabItem getTabItemForInputVideo(Video video) {
		int index = 0;
		while (index < tabFolder.getItemCount()
				&& video != tabFolder.getItem(index).getData(VIDEO_DATA)) {
			index++;
		}
		if (index < tabFolder.getItemCount()) {
			return tabFolder.getItem(index);
		}
		return null;
	}

	/**
	 * Return the current video selected.
	 * 
	 * @return the video selected or null.
	 */
	public Video getSelection() {
		CTabItem item = tabFolder.getSelection();
		if (item == null) {
			return null;
		}
		return (Video) item.getData(VIDEO_DATA);
	}

	/**
	 * Return the view site.
	 * 
	 * @return the view site.
	 */
	public IViewSite getViewSite() {
		return site;
	}

	/**
	 * Use to update the tabFolder to reflect the videoList.
	 * 
	 * @param list
	 *            the video list.
	 */
	private void updateList(VideoList list) {

		// Set a tag to each tabFolder
		for (int index = 0; index < tabFolder.getItemCount(); index++) {
			tabFolder.getItem(index).setData(USED, null);
		}

		// Loop on each video in the list to associate it the an existing
		// tabItem or create a new one.
		Video[] videos = list.getVideos();
		for (int index = 0; index < videos.length; index++) {
			Video video = videos[index];

			CTabItem item = getTabItemForInputVideo(video);
			if (item == null) {
				item = new CTabItem(tabFolder, SWT.CLOSE);

				item.setText(VideoInfoFormater.formatInputVideo(video
						.getInputVideo()));

				InputVideoComposite comp = InputVideoCompositeFactory
						.createInputVideoComposite(video, getViewSite(),
								tabFolder, SWT.NONE);

				item.setControl(comp);

				tabFolder.setSelection(item);
			}

			item.setData(USED, 1);
			item.setData(VIDEO_DATA, video);

		}

		// Remove unused tabItem
		for (int index = tabFolder.getItemCount() - 1; index >= 0; index--) {
			if (tabFolder.getItem(index).getData(USED) == null) {
				tabFolder.getItem(index).dispose();
			}
		}

		// Set the selection if none exist
		if (tabFolder.getSelection() == null && tabFolder.getItemCount() > 0) {
			tabFolder.setSelection(0);
		}
		if (tabFolder.getSelection() != null) {
			setActionContext((Video) tabFolder.getSelection().getData(
					VIDEO_DATA));
		} else {
			setActionContext(null);
		}

	}

	/**
	 * Use to change the action context value with the given video.
	 * 
	 * @param video
	 *            the new video selected in the videoList.
	 */
	private void setActionContext(Video video) {

		if (video != null) {
			IStructuredSelection selection = new StructuredSelection(video);
			getViewSite().getActionContext().setSubject(videoList, selection);
		} else {
			getViewSite().getActionContext().setSubject(null, null);
		}

	}

	/**
	 * Initialize this view with the given view site.
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {

		// TODO : Support dropping (drag&drop) video file.

		this.site = site;

		this.videoList = site.getVideoList();
		videoList.addInputVideoListObserver(inputVideoListObserver);

		// Setup tab folder
		this.setLayout(new FillLayout());
		tabFolder = new CTabFolder(this, SWT.CLOSE | SWT.BORDER);
		tabFolder.setMRUVisible(false);
		tabFolder.setSimple(false);
		// TODO : There is a bug with Tooltip of CTabFolder. The "Close"
		// tooltip appear every where in the interface. It's not supposed to
		// happen. Submit the bug to SWT team.

		// Under linux, we change the color of tabFolder to get a better effect
		String os = System.getProperty(OS_NAME);
		if (os.equals(OS_NAME_LINUX)) {
			tabFolder.setSelectionBackground(Display.getDefault()
					.getSystemColor(SWT.COLOR_LIST_SELECTION));
			tabFolder.setSelectionForeground(Display.getDefault()
					.getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT));
		}

		// Add listener
		tabFolder.addCTabFolder2Listener(tabFolderListener);
		tabFolder.addSelectionListener(selectionListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				videoList.removeInputVideoListObserver(inputVideoListObserver);
				inputVideoListObserver = null;

				tabFolder.removeCTabFolder2Listener(tabFolderListener);
				tabFolderListener = null;

				tabFolder.removeSelectionListener(selectionListener);
				selectionListener = null;
			}
		});

	}

}
