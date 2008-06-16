package net.homeip.entreprisesmd.mvconv.gui.options.video;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profiles;
import net.homeip.entreprisesmd.mvconv.gui.IProfileContextListener;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.XVideoEncodingOptions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * This class offer to change the options for X264 codec.
 * 
 * @author patapouf
 * 
 */
public class XVideoOptionsComposite extends VideoOptionsInterface {
	/**
	 * Number of b-frame to use when enable.
	 */
	private static final int BFRAME_NUMBER = 4;
	/**
	 * Default bitrate value
	 */
	private static final int BITRATE_DEFAULT = 687;

	/**
	 * Return the mapper for this interface.
	 * 
	 * @return the mapper.
	 */
	public static VideoOptionsMapper getMapper() {
		return new VideoOptionsMapper() {
			public VideoOptionsInterface createInterface(Composite parent,
					int style) {
				return new XVideoOptionsComposite(parent, style);
			}

			public VideoEncodingOptions getEncodingOptions() {
				return new XVideoEncodingOptions(BITRATE_DEFAULT);
			}

			public VideoFormat getVideoFormat() {
				return VideoFormat.FORMAT_MPEG_XVID;
			}
		};
	}

	/**
	 * Button to enable/disable B-Frame usage.
	 */
	private Button bframeButton;
	/**
	 * Button to enable/disable Cartoon optimization.
	 */
	private Button cartoonButton;

	/**
	 * Listener to profile context.
	 */
	private IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};

	/**
	 * Selection listener to bitrate editor.
	 */
	private SelectionListener selectionListener = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			if (e.widget == bframeButton) {
				bframeSelectionAsChanged();
			} else if (e.widget == trellisButton) {
				trellisSelectionAsChanged();
			} else if (e.widget == twoPassButton) {
				twoPassSelectionAsChanged();
			} else if (e.widget == cartoonButton) {
				cartoonSelectionAsChanged();
			} else if (e.widget == quarterPixelButton) {
				quarterPixelSelectionAsChanged();
			}
		}
	};
	/**
	 * Button to enable/disable quarter pixel options.
	 */
	private Button quarterPixelButton;
	/**
	 * Trellis enable/disable button.
	 */
	private Button trellisButton;
	/**
	 * Two pass quality button.
	 */
	private Button twoPassButton;

	/**
	 * Create a new lame composite interface.
	 * 
	 * @param parent
	 *            the parent of this interface.
	 * @param style
	 *            the style.
	 */
	public XVideoOptionsComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Notify this view that user change the selection of b-frame.
	 */
	private void bframeSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof XVideoEncodingOptions)) {
			return;
		}
		XVideoEncodingOptions xVidOptions = (XVideoEncodingOptions) videoOptions;

		// Check if value as changed
		boolean bframeSelected = bframeButton.getSelection();
		if (bframeSelected != (xVidOptions.getMaxBFrame() > 0)) {

			// Change profile value
			xVidOptions.setMaxBFrame(bframeSelected ? BFRAME_NUMBER : 0);
			options.setVideoOptions(xVidOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this view that user change the selection of cartoon optimization.
	 */
	private void cartoonSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof XVideoEncodingOptions)) {
			return;
		}
		XVideoEncodingOptions xVidOptions = (XVideoEncodingOptions) videoOptions;

		// Check if value as changed
		boolean cartoonSelected = cartoonButton.getSelection();
		if (cartoonSelected != xVidOptions.isCartoonEnabled()) {

			// Change profile value
			xVidOptions.enableCartoon(cartoonSelected);
			options.setVideoOptions(xVidOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Initialize this view with the given view site.
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {

		super.init(site);

		this.setLayout(new GridLayout(2, true));

		// B-Frame
		String bframeText = Localization
				.getString(Localization.OPTIONS_XVID_BFRAME);
		bframeButton = new Button(this, SWT.CHECK);
		bframeButton.setText(bframeText);
		bframeButton.addSelectionListener(selectionListener);

		// Trellis
		String trellisText = Localization
				.getString(Localization.OPTIONS_XVID_TRELLIS);
		trellisButton = new Button(this, SWT.CHECK);
		trellisButton.setText(trellisText);
		trellisButton.addSelectionListener(selectionListener);

		// Two-pass
		String twoPassText = Localization
				.getString(Localization.OPTIONS_TWO_PASS);
		twoPassButton = new Button(this, SWT.CHECK);
		twoPassButton.setText(twoPassText);
		twoPassButton.addSelectionListener(selectionListener);

		// Cartoon
		String cartoonText = Localization
				.getString(Localization.OPTIONS_XVID_CARTOON);
		cartoonButton = new Button(this, SWT.CHECK);
		cartoonButton.setText(cartoonText);
		cartoonButton.addSelectionListener(selectionListener);

		// QuarterPixel
		String quarterPixelText = Localization
				.getString(Localization.OPTIONS_XVID_QUARTER_PIXEL);
		quarterPixelButton = new Button(this, SWT.CHECK);
		quarterPixelButton.setText(quarterPixelText);
		quarterPixelButton.addSelectionListener(selectionListener);

		// Force update
		profileAsChanged();

		// Add listener
		getViewSite().getProfileContext().addProfileContextListener(
				profileContextListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getViewSite().getProfileContext().removeProfileContextListener(
						profileContextListener);
			}
		});

	}

	/**
	 * Update this view to reflect the profile modification.
	 */
	private void profileAsChanged() {

		// Get the Lame encoding options
		Profile selectedProfile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (selectedProfile instanceof HardCodedProfile) {
			return;
		}
		VideoEncodingOptions videoOptions = selectedProfile
				.getEncodingOptions().getVideoOptions();
		if (!(videoOptions instanceof XVideoEncodingOptions)) {
			return;
		}
		XVideoEncodingOptions xVidOptions = (XVideoEncodingOptions) videoOptions;

		// Change trellis
		if (trellisButton.getSelection() != xVidOptions.isTrellisEnabled()) {
			trellisButton.setSelection(xVidOptions.isTrellisEnabled());
		}

		// Change B-Frame
		if (bframeButton.getSelection() != (xVidOptions.getMaxBFrame() > 0)) {
			bframeButton.setSelection(xVidOptions.getMaxBFrame() > 0);
		}

		// Change two-pass
		if (twoPassButton.getSelection() != (xVidOptions.getPass() == 2)) {
			twoPassButton.setSelection(xVidOptions.getPass() == 2);
		}

		// Change Cartoon
		if (cartoonButton.getSelection() != xVidOptions.isCartoonEnabled()) {
			cartoonButton.setSelection(xVidOptions.isCartoonEnabled());
		}

		// Change Cartoon
		if (quarterPixelButton.getSelection() != xVidOptions
				.isQuarterPixelEnabled()) {
			quarterPixelButton
					.setSelection(xVidOptions.isQuarterPixelEnabled());
		}

		this.layout();
	}

	/**
	 * Notify this class that user change the selected trellis mode.
	 */
	private void quarterPixelSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof XVideoEncodingOptions)) {
			return;
		}
		XVideoEncodingOptions xVidOptions = (XVideoEncodingOptions) videoOptions;

		// Check if value as changed
		boolean quarterPixelSelection = quarterPixelButton.getSelection();
		if (quarterPixelSelection != xVidOptions.isQuarterPixelEnabled()) {

			// Change profile value
			xVidOptions.enableQuarterPixel(quarterPixelSelection);
			options.setVideoOptions(xVidOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this class that user change the selected trellis mode.
	 */
	private void trellisSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();
		if (!(videoOptions instanceof XVideoEncodingOptions)) {
			return;
		}
		XVideoEncodingOptions xVidOptions = (XVideoEncodingOptions) videoOptions;

		// Check if value as changed
		boolean trellisSelection = trellisButton.getSelection();
		if (trellisSelection != xVidOptions.isTrellisEnabled()) {

			// Change profile value
			xVidOptions.enableTrellis(trellisSelection);
			options.setVideoOptions(xVidOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}

	/**
	 * Notify this class that user change the selected trellis mode.
	 */
	private void twoPassSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();

		// Check if value as changed
		boolean twoPassSelection = twoPassButton.getSelection();
		if (twoPassSelection != (videoOptions.getPass() == 2)) {

			// Change profile value
			videoOptions.setPass(twoPassSelection ? 2 : 1);
			options.setVideoOptions(videoOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}
}
