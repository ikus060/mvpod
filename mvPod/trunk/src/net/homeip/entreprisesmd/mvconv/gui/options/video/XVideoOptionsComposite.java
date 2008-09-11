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
			XVideoEncodingOptions encodingOptions;

			public VideoOptionsInterface createInterface(Composite parent,
					int style) {
				return new XVideoOptionsComposite(parent, style);
			}

			public VideoEncodingOptions getEncodingOptions() {
				if (encodingOptions == null) {
					encodingOptions = new XVideoEncodingOptions(BITRATE_DEFAULT);
				}
				return encodingOptions;
			}

			public VideoFormat getVideoFormat() {
				return VideoFormat.FORMAT_MPEG_XVID;
			}

			public boolean match(VideoEncodingOptions options) {
				if (options instanceof XVideoEncodingOptions)
					return true;
				return false;
			}

			public void setDefaultEncodingOptions(VideoEncodingOptions options) {
				if (options instanceof XVideoEncodingOptions) {
					encodingOptions = (XVideoEncodingOptions) options;
				}
			}
		};
	}

	/**
	 * Button to enable/disable B-Frame usage.
	 */
	Button bframeButton;
	/**
	 * Button to enable/disable Cartoon optimization.
	 */
	Button cartoonButton;

	/**
	 * Listener to profile context.
	 */
	IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};

	/**
	 * Selection listener to bitrate editor.
	 */
	SelectionListener selectionListener = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			if (e.widget == XVideoOptionsComposite.this.bframeButton) {
				bframeSelectionAsChanged();
			} else if (e.widget == XVideoOptionsComposite.this.trellisButton) {
				trellisSelectionAsChanged();
			} else if (e.widget == XVideoOptionsComposite.this.twoPassButton) {
				twoPassSelectionAsChanged();
			} else if (e.widget == XVideoOptionsComposite.this.cartoonButton) {
				cartoonSelectionAsChanged();
			} else if (e.widget == XVideoOptionsComposite.this.quarterPixelButton) {
				quarterPixelSelectionAsChanged();
			}
		}
	};
	/**
	 * Button to enable/disable quarter pixel options.
	 */
	Button quarterPixelButton;
	/**
	 * Trellis enable/disable button.
	 */
	Button trellisButton;
	/**
	 * Two pass quality button.
	 */
	Button twoPassButton;

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
	void bframeSelectionAsChanged() {
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
		boolean bframeSelected = this.bframeButton.getSelection();
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
	void cartoonSelectionAsChanged() {
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
		boolean cartoonSelected = this.cartoonButton.getSelection();
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
		this.bframeButton = new Button(this, SWT.CHECK);
		this.bframeButton.setText(bframeText);
		this.bframeButton.addSelectionListener(this.selectionListener);

		// Trellis
		String trellisText = Localization
				.getString(Localization.OPTIONS_XVID_TRELLIS);
		this.trellisButton = new Button(this, SWT.CHECK);
		this.trellisButton.setText(trellisText);
		this.trellisButton.addSelectionListener(this.selectionListener);

		// Two-pass
		String twoPassText = Localization
				.getString(Localization.OPTIONS_TWO_PASS);
		this.twoPassButton = new Button(this, SWT.CHECK);
		this.twoPassButton.setText(twoPassText);
		this.twoPassButton.addSelectionListener(this.selectionListener);

		// Cartoon
		String cartoonText = Localization
				.getString(Localization.OPTIONS_XVID_CARTOON);
		this.cartoonButton = new Button(this, SWT.CHECK);
		this.cartoonButton.setText(cartoonText);
		this.cartoonButton.addSelectionListener(this.selectionListener);

		// QuarterPixel
		String quarterPixelText = Localization
				.getString(Localization.OPTIONS_XVID_QUARTER_PIXEL);
		this.quarterPixelButton = new Button(this, SWT.CHECK);
		this.quarterPixelButton.setText(quarterPixelText);
		this.quarterPixelButton.addSelectionListener(this.selectionListener);

		// Force update
		profileAsChanged();

		// Add listener
		getViewSite().getProfileContext().addProfileContextListener(
				this.profileContextListener);

		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				getViewSite().getProfileContext().removeProfileContextListener(
						XVideoOptionsComposite.this.profileContextListener);
			}
		});

	}

	/**
	 * Update this view to reflect the profile modification.
	 */
	void profileAsChanged() {

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
		if (this.trellisButton.getSelection() != xVidOptions.isTrellisEnabled()) {
			this.trellisButton.setSelection(xVidOptions.isTrellisEnabled());
		}

		// Change B-Frame
		if (this.bframeButton.getSelection() != (xVidOptions.getMaxBFrame() > 0)) {
			this.bframeButton.setSelection(xVidOptions.getMaxBFrame() > 0);
		}

		// Change two-pass
		if (this.twoPassButton.getSelection() != (xVidOptions.getPass() == 2)) {
			this.twoPassButton.setSelection(xVidOptions.getPass() == 2);
		}

		// Change Cartoon
		if (this.cartoonButton.getSelection() != xVidOptions.isCartoonEnabled()) {
			this.cartoonButton.setSelection(xVidOptions.isCartoonEnabled());
		}

		// Change Cartoon
		if (this.quarterPixelButton.getSelection() != xVidOptions
				.isQuarterPixelEnabled()) {
			this.quarterPixelButton.setSelection(xVidOptions
					.isQuarterPixelEnabled());
		}

		this.layout();
	}

	/**
	 * Notify this class that user change the selected trellis mode.
	 */
	void quarterPixelSelectionAsChanged() {
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
		boolean quarterPixelSelection = this.quarterPixelButton.getSelection();
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
	void trellisSelectionAsChanged() {
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
		boolean trellisSelection = this.trellisButton.getSelection();
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
	void twoPassSelectionAsChanged() {
		Profile profile = getViewSite().getProfileContext()
				.getSelectedProfile();
		if (profile instanceof HardCodedProfile) {
			return;
		}

		// Get encoding options
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions videoOptions = options.getVideoOptions();

		// Check if value as changed
		boolean twoPassSelection = this.twoPassButton.getSelection();
		if (twoPassSelection != (videoOptions.getPass() == 2)) {

			// Change profile value
			videoOptions.setPass(twoPassSelection ? 2 : 1);
			options.setVideoOptions(videoOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			getViewSite().getProfileContext().setSelectedProfile(newProfile);
		}
	}
}
