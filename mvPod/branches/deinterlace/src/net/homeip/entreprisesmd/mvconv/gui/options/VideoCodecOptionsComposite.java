package net.homeip.entreprisesmd.mvconv.gui.options;

import java.util.ArrayList;
import java.util.List;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.core.profile.HardCodedProfile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profile;
import net.homeip.entreprisesmd.mvconv.core.profile.Profiles;
import net.homeip.entreprisesmd.mvconv.gui.IProfileContextListener;
import net.homeip.entreprisesmd.mvconv.gui.IViewPart;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.gui.ProfileContext;
import net.homeip.entreprisesmd.mvconv.gui.options.video.VideoOptionsInterface;
import net.homeip.entreprisesmd.mvconv.gui.options.video.VideoOptionsMapper;
import net.homeip.entreprisesmd.mvconv.gui.options.video.X264OptionsComposite;
import net.homeip.entreprisesmd.mvconv.gui.options.video.XVideoOptionsComposite;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.videooption.VideoEncodingOptions;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

/**
 * This class display audio options to user and give him the chance to change
 * the audio codec.
 * 
 * @author patapouf
 * 
 */
public class VideoCodecOptionsComposite extends Composite implements IViewPart {
	/**
	 * List of interface mapper.
	 */
	private List<VideoOptionsMapper> mappers = new ArrayList<VideoOptionsMapper>();

	/**
	 * Audio codec list.
	 */
	private ComboViewer codecViewer;
	/**
	 * Composite component where audio option interface will be create.
	 */
	private Composite comp;

	/**
	 * Label provider for video codec list.
	 */
	private LabelProvider labelProvider = new LabelProvider() {
		public String getText(Object element) {
			if (element instanceof VideoOptionsMapper) {
				VideoOptionsMapper mapper = (VideoOptionsMapper) element;
				return Localization.getLocalizedFormat(mapper.getVideoFormat());
			}
			return element.toString();
		}
	};

	/**
	 * Last selected mapper.
	 */
	private VideoOptionsMapper lastMapper;

	/**
	 * Listener to profile context.
	 */
	private IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};

	/**
	 * Selection listener to codec list.
	 */
	private ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			codecSelectionChanged();
		}
	};

	/**
	 * View site.
	 */
	private IViewSite site;

	/**
	 * Create a new composite interface to select audio options for audio
	 * encoding.
	 * 
	 * @param parent
	 *            the parent of this composite interface.
	 * @param style
	 *            the style
	 */
	public VideoCodecOptionsComposite(Composite parent, int style) {
		super(parent, style);

		registerMapper(X264OptionsComposite.getMapper());
		registerMapper(XVideoOptionsComposite.getMapper());

	}

	/**
	 * Notify this class that user select a new audio codec.
	 */
	void codecSelectionChanged() {

		//Get the new mapper selected
		VideoOptionsMapper mapperSelected = getVideoOptionsMapperSelection();
		VideoEncodingOptions videoOptions = mapperSelected.getEncodingOptions();

		ProfileContext profileContext = getViewSite().getProfileContext();
		Profile profile = profileContext.getSelectedProfile();
		EncodingOptions options = profile.getEncodingOptions();
		VideoEncodingOptions currentVideoOptions = options.getVideoOptions();
		
		//Check if the new Mapper match the current video encoding options
		if (!mapperSelected.match(currentVideoOptions)) {

			// Update the video encoding options with generic value of previous options
			videoOptions.setBitrate(currentVideoOptions.getBitrate());
			videoOptions.setPass(currentVideoOptions.getPass());
			videoOptions.setOutputFrameRate(currentVideoOptions.getOutputFrameRate());
			videoOptions.setMaxOutputFrameRate(currentVideoOptions.getMaxOutputFrameRate());
			
			//Update the video encoding options with a new one
			options.setVideoOptions(videoOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			profileContext.setSelectedProfile(newProfile);

		}

	}

	/**
	 * Return the Mapper associate with the given audio encoding options.
	 * 
	 * @param options
	 *            the audio encoding options to match.
	 * @return the mapper.
	 */
	private VideoOptionsMapper findMapper(VideoEncodingOptions options) {

		int index = 0;
		while (index < mappers.size()
				&& !mappers.get(index).match(options)) {
			index++;
		}
		if (index < mappers.size()) {
			return mappers.get(index);
		}
		return null;

	}

	/**
	 * Return the selected mapper in the audioCodec viewer.
	 * 
	 * @return the selected mapper.
	 */
	private VideoOptionsMapper getVideoOptionsMapperSelection() {
		Object selection = ((IStructuredSelection) codecViewer.getSelection())
				.getFirstElement();
		if (!(selection instanceof VideoOptionsMapper)) {
			return null;
		}
		return (VideoOptionsMapper) selection;
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

		GridLayout layout = new GridLayout(2, false);
		this.setLayout(layout);

		String audioCodecText = Localization
				.getString(Localization.OPTIONS_VIDEO_CODEC);

		Label label = new Label(this, SWT.NONE);
		label.setText(audioCodecText);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false));

		codecViewer = new ComboViewer(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		codecViewer.setLabelProvider(labelProvider);
		codecViewer.addSelectionChangedListener(selectionListener);
		codecViewer.getCombo().setLayoutData(
				new GridData(SWT.LEFT, SWT.FILL, true, false));
		for (int index = 0; index < mappers.size(); index++) {
			codecViewer.add(mappers.get(index));
		}

		// Composite component
		comp = new Group(this, SWT.NONE);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		comp.setLayout(new FillLayout());

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
	void profileAsChanged() {

		// Get video options from curent profile context
		Profile selectedProfile = site.getProfileContext().getSelectedProfile();
		if (selectedProfile instanceof HardCodedProfile) {
			return;
		}
		VideoEncodingOptions videoOptions = selectedProfile
				.getEncodingOptions().getVideoOptions();

		// Get associated mapper
		VideoOptionsMapper mapper = findMapper(videoOptions);
		mapper.setDefaultEncodingOptions(videoOptions);
		
		// Set video codec selection
		VideoOptionsMapper mapperSelected = getVideoOptionsMapperSelection();
		if (mapperSelected == null || !mapperSelected.equals(mapper)) {
			codecViewer.setSelection(new StructuredSelection(mapper));
		}

		if (mapper == lastMapper)
			return;

		// Remove older component
		Control[] childs = comp.getChildren();
		for (int index = 0; index < childs.length; index++) {
			childs[index].dispose();
		}

		// Create new component
		VideoOptionsInterface optionsInterface = mapper.createInterface(comp,
				SWT.NONE);
		optionsInterface.init(getViewSite());
		comp.layout();

		lastMapper = mapper;

	}

	/**
	 * Register the give mapper in this class.
	 * 
	 * @param mapper
	 *            the mapper.
	 */
	private void registerMapper(VideoOptionsMapper mapper) {
		mappers.add(mapper);
	}

}
