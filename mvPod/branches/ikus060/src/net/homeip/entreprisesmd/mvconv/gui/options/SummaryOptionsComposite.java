package net.homeip.entreprisesmd.mvconv.gui.options;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.VideoInfoFormater;
import net.homeip.entreprisesmd.mvconv.core.profile.Profile;
import net.homeip.entreprisesmd.mvconv.core.profile.ProfileList;
import net.homeip.entreprisesmd.mvconv.core.profile.ProfileListObserver;
import net.homeip.entreprisesmd.mvconv.core.profile.Profiles;
import net.homeip.entreprisesmd.mvconv.gui.IProfileContextListener;
import net.homeip.entreprisesmd.mvconv.gui.IViewPart;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoScalingOptions;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SummaryOptionsComposite extends Composite implements IViewPart {

	/**
	 * Width of summary text zone.
	 */
	private static final int SUMMARY_WIDTH = 300;
	/**
	 * Height of summary text zone.
	 */
	private static final int SUMMARY_HEIGHT = 50;
	/**
	 * Factor to create darker color.
	 */
	private static final double DARKER_COLOR_FACTOR = 0.95;

	/**
	 * Font with bold style.
	 */
	Font boldFont;
	/**
	 * Daker background color for audio/video summary.
	 */
	Color darkerBackground;
	/**
	 * Profile viewer.
	 */
	private ComboViewer profileViewer;
	/**
	 * Lane lto display video summary.
	 */
	private Text videoSummary;
	/**
	 * Label to display audio summary.
	 */
	private Text audioSummary;
	/**
	 * View site.
	 */
	private IViewSite site;
	/**
	 * Observer on the profile list.
	 */
	ProfileListObserver observer = new ProfileListObserver() {
		public void profileListAsChanged(ProfileList list) {
			SummaryOptionsComposite.this.profileListAsChanged();
		}
	};
	/**
	 * Label profile for profile viewer.
	 */
	private LabelProvider labelProvider = new LabelProvider() {
		public String getText(Object element) {
			if (element instanceof Profile) {
				return ((Profile) element).getName();
			} else {
				return element.toString();
			}
		}
	};
	/**
	 * Selection changed listener.
	 */
	private ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			selectionAsChanged();
		}
	};

	/**
	 * Listener to profile context.
	 */
	IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			SummaryOptionsComposite.this.profileContextAsChanged();
		}
	};

	/**
	 * Create a new options summary composite interface.
	 * 
	 * @param parent
	 *            the parent of this composite interface.
	 * @param style
	 *            the style
	 */
	public SummaryOptionsComposite(Composite parent, int style) {
		super(parent, style);
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
	 * Initialize this view with the given view site.
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {

		this.site = site;

		// Setup bolded font
		FontData[] boldedFontData = getFont().getFontData();
		for (int fontIndex = 0; fontIndex < boldedFontData.length; fontIndex++) {
			boldedFontData[fontIndex].setStyle(SWT.BOLD);
		}
		boldFont = new Font(Display.getCurrent(), boldedFontData);

		// Setup darker background
		RGB rgb = getBackground().getRGB();
		rgb.red = (int) (rgb.red * DARKER_COLOR_FACTOR);
		rgb.green = (int) (rgb.green * DARKER_COLOR_FACTOR);
		rgb.blue = (int) (rgb.blue * DARKER_COLOR_FACTOR);
		darkerBackground = new Color(Display.getCurrent(), rgb);
		
		

		GridLayout layout = new GridLayout(1, false);
		this.setLayout(layout);

		Composite comp = new Composite(this, SWT.NONE);
		comp.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		layout = new GridLayout(2, false);
		comp.setLayout(layout);

		// Profile viewer
		Label profile = new Label(comp, SWT.NONE);
		profile.setText(Localization.getString(Localization.OPTIONS_PROFILE));
		profile.setFont(boldFont);

		profileViewer = new ComboViewer(comp, SWT.READ_ONLY | SWT.DROP_DOWN);
		profileViewer.setLabelProvider(labelProvider);
		profileViewer.addSelectionChangedListener(selectionListener);

		// Video Summary
		Label video = new Label(comp, SWT.NONE);
		video.setText(Localization.getString(Localization.OPTIONS_VIDEO));
		video.setFont(boldFont);

		videoSummary = new Text(comp, SWT.BORDER | SWT.WRAP | SWT.READ_ONLY);
		videoSummary.setLayoutData(new GridData(SUMMARY_WIDTH, SUMMARY_HEIGHT));
		videoSummary.setBackground(darkerBackground);
		videoSummary.setForeground(this.getForeground());
		
		// Audio summary
		Label audio = new Label(comp, SWT.NONE);
		audio.setText(Localization.getString(Localization.OPTIONS_AUDIO));
		audio.setFont(boldFont);

		audioSummary = new Text(comp, SWT.BORDER | SWT.WRAP | SWT.READ_ONLY);
		audioSummary.setLayoutData(new GridData(SUMMARY_WIDTH, SUMMARY_HEIGHT));
		audioSummary.setBackground(darkerBackground);
		audioSummary.setForeground(this.getForeground());

		// Update the profile list
		profileListAsChanged();

		// Observer the profile list
		getViewSite().getProfileList().addProfileListObserver(this.observer);

		// Observer the profile context
		getViewSite().getProfileContext().addProfileContextListener(
				this.profileContextListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getViewSite().getProfileList().removeProfileListObserver(
						SummaryOptionsComposite.this.observer);

				getViewSite().getProfileContext().removeProfileContextListener(
						SummaryOptionsComposite.this.profileContextListener);

				SummaryOptionsComposite.this.darkerBackground.dispose();
				SummaryOptionsComposite.this.boldFont.dispose();
			}
		});

	}

	/**
	 * Notify this class that profile context as changed
	 */
	void profileContextAsChanged() {

		if (!this.getVisible())
			return;

		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		EncodingOptions encodingOptions = profile.getEncodingOptions();

		// Display video & audio summary

		String videoDescription = VideoInfoFormater
				.formatVideoEncodingOptions(encodingOptions.getVideoOptions());

		VideoScalingOptions scalingOptions = encodingOptions.getScaleOptions();
		if (scalingOptions != null) {
			videoDescription += "\r\n" //$NON-NLS-1$
					+ VideoInfoFormater
							.formatVideoScalingOptions(scalingOptions);
		}
		String audioDescription = VideoInfoFormater
				.formatAudioEncodingOptions(encodingOptions.getAudioOptions());

		videoSummary.setText(videoDescription);

		audioSummary.setText(audioDescription);

	}

	/**
	 * This implementation update the profile view according to the profile
	 * list.
	 */
	void profileListAsChanged() {

		ProfileList profileList = getViewSite().getProfileList();
		Profile[] profiles = profileList.getProfiles();
		Profile[] usedProfiles = new Profile[profiles.length+1];
		System.arraycopy(profiles, 0, usedProfiles, 1, profiles.length);
		
		usedProfiles[0] = Profiles.createCustomProfile();
		
		updateViewer(profileViewer, usedProfiles, null);

	}

	/**
	 * Class when the user change the selected profile.
	 */
	void selectionAsChanged() {

		IStructuredSelection selection = (IStructuredSelection) profileViewer
				.getSelection();
		if (selection != null && selection.getFirstElement() != null) {

			if (selection.getFirstElement() instanceof Profile) {
				site.getProfileContext().setSelectedProfile(
						(Profile) selection.getFirstElement());
				return;
			}

		} else {
			site.getProfileContext().setSelectedProfile(null);
		}

	}

	/**
	 * @see org.eclipse.swt.widgets.Control#setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			profileContextAsChanged();
		}
	}

	/**
	 * This method are use to update a viewer (Update the list and the
	 * selection).
	 * 
	 * @param viewer
	 *            the viewer component.
	 * @param elements
	 *            new list of element for viewer.
	 * @param selection
	 *            the selection element.
	 */
	void updateViewer(ComboViewer viewer, Object[] objects,
			Object selection) {

		// Update the list
		viewer.getCombo().removeAll();

		for (int index = 0; index < objects.length; index++) {
			Object element = objects[index];
			viewer.add(element);
			if (element.equals(selection)) {
				viewer.setSelection(new StructuredSelection(element));
			}
		}

		Object curSelection = ((IStructuredSelection) viewer.getSelection())
				.getFirstElement();
		if (curSelection == null && objects.length > 0) {
			StructuredSelection structSelection = new StructuredSelection(
					objects[0]);
			viewer.setSelection(structSelection);
		}
	}
}
