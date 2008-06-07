package net.homeip.entreprisesmd.mvconv.gui.options;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profile;
import net.homeip.entreprisesmd.mvconv.gui.IProfileContextListener;
import net.homeip.entreprisesmd.mvconv.gui.IViewPart;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * This component display every available encoding options to the user and other
 * component to change this settings.
 * 
 * @author patapouf
 * 
 */
public class EncodingOptionsComposite extends Composite implements IViewPart {

	/**
	 * The property key to retrieve the os value.
	 */
	private static final String PROPERTY_OS = "os.name";

	private static final int HARD_CODED = 0;
	private static final int CUSTOM = 1;

	private static final int MIN_WIDTH = 0;
	private static final int MIN_HEIGHT = 250;

	/**
	 * The view site.
	 */
	private IViewSite site;
	/**
	 * The tabfolder that display the options.
	 */
	private CTabFolder tabFolder;

	/**
	 * Last type of profile
	 */
	private int lastProfileType = -1;

	/**
	 * Interface to edit options of hard coded profile.
	 */
	private HardCodedProfileOptionsComposite hardCodecProfileOptions;
	/**
	 * Interface to edit audio option of user profile.
	 */
	private AudioOptionsComposite audioOptionsComposite;

	/**
	 * Interface to edit video option of user profile.
	 */
	private VideoOptionsComposite videoOptionsComposite;

	/**
	 * Listener to profile context.
	 */
	private IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			EncodingOptionsComposite.this.profileContextAsChanged();
		}
	};

	/**
	 * Create a new OuputOptionsComposite.
	 * 
	 * @param parent
	 *            the parent of this composite interface.
	 * @param style
	 *            the style of this composite.
	 */
	public EncodingOptionsComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * @see org.eclipse.swt.widgets.Composite#computeSize(int, int, boolean)
	 */
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point size = super.computeSize(wHint, hHint, changed);
		size.x = Math.max(size.x, MIN_WIDTH);
		size.y = Math.max(size.y, MIN_HEIGHT);
		return size;
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
	 * Initialize this view with the given view site. Sub-class that overload
	 * this method must call super.init().
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {

		this.site = site;

		this.setLayout(new FillLayout());
		tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setSimple(false);

		SummaryOptionsComposite summary = new SummaryOptionsComposite(
				tabFolder, SWT.NONE);
		summary.init(getViewSite());

		// Create the summary tab item (always present)
		CTabItem item = new CTabItem(tabFolder, SWT.NONE);
		item.setText(Localization.getString(Localization.SUMMARY_TITLE));
		item.setControl(summary);
		tabFolder.setSelection(item);

		// Create component to edit options
		hardCodecProfileOptions = new HardCodedProfileOptionsComposite(
				tabFolder, SWT.NONE);
		hardCodecProfileOptions.init(getViewSite());

		audioOptionsComposite = new AudioOptionsComposite(tabFolder, SWT.NONE);
		audioOptionsComposite.init(getViewSite());

		// audioOptionsComposite2 = new AudioOptionsComposite(tabFolder,
		// SWT.NONE);
		// audioOptionsComposite2.init(getViewSite());

		videoOptionsComposite = new VideoOptionsComposite(tabFolder, SWT.NONE);
		videoOptionsComposite.init(getViewSite());

		// videoOptionsComposite2 = new VideoOptionsComposite(tabFolder,
		// SWT.NONE);
		// videoOptionsComposite2.init(getViewSite());

		// Under linux, we change the color of tabFolder to get a better effect
		String os = System.getProperty(PROPERTY_OS);
		if (os.equals("Linux")) {
			tabFolder.setSelectionBackground(Display.getDefault()
					.getSystemColor(SWT.COLOR_LIST_SELECTION));
			tabFolder.setSelectionForeground(Display.getDefault()
					.getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT));
		}

		// Listener to profile context
		getViewSite().getProfileContext().addProfileContextListener(
				profileContextListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getViewSite().getProfileContext().removeProfileContextListener(
						profileContextListener);
			}
		});

		profileContextAsChanged();
	}

	/**
	 * Notify this class that profile context as changed.
	 * <p>
	 * This implementation update the view to reflect the change.
	 * <p>
	 */
	private void profileContextAsChanged() {

		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();

		if (profile instanceof HardCodedProfile
				&& lastProfileType != HARD_CODED) {

			removeOlderTabItem();

			CTabItem item = new CTabItem(tabFolder, SWT.NONE);
			item.setText(Localization
					.getString(Localization.CUSTOM_OPTIONS_TITLE));
			item.setControl(hardCodecProfileOptions);

			lastProfileType = HARD_CODED;

		} else if (!(profile instanceof HardCodedProfile)
				&& lastProfileType != CUSTOM) {

			removeOlderTabItem();

			CTabItem item = new CTabItem(tabFolder, SWT.NONE);
			item.setText(Localization
					.getString(Localization.VIDEO_OPTIONS_TITLE));
			item.setControl(videoOptionsComposite);

			// item = new CTabItem(tabFolder, SWT.NONE);
			// item.setText(Localization
			// .getString(Localization.VIDEO_OPTIONS_TITLE));
			// item.setControl(videoOptionsComposite2);

			item = new CTabItem(tabFolder, SWT.NONE);
			item.setText(Localization
					.getString(Localization.AUDIO_OPTIONS_TITLE));
			item.setControl(audioOptionsComposite);

			// item = new CTabItem(tabFolder, SWT.NONE);
			// item.setText(Localization
			// .getString(Localization.AUDIO_OPTIONS_TITLE));
			// item.setControl(audioOptionsComposite2);

			// TODO : Add options for muxer

			lastProfileType = CUSTOM;
		}

	}

	/**
	 * Remove all tab item except summary tab.
	 */
	private void removeOlderTabItem() {

		CTabItem[] items = tabFolder.getItems();

		for (int index = items.length - 1; index >= 1; index--) {
			items[index].dispose();
		}

	}

}
