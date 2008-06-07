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
import net.homeip.entreprisesmd.mvconv.gui.options.audio.AudioOptionsInterface;
import net.homeip.entreprisesmd.mvconv.gui.options.audio.AudioOptionsMapper;
import net.homeip.entreprisesmd.mvconv.gui.options.audio.FAACOptionsComposite;
import net.homeip.entreprisesmd.mvconv.gui.options.audio.LameOptionsComposite;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.AudioFormat;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.EncodingOptions;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.audiooption.AudioEncodingOptions;

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
public class AudioOptionsComposite extends Composite implements IViewPart {
	/**
	 * List of interface mapper.
	 */
	private List<AudioOptionsMapper> mappers = new ArrayList<AudioOptionsMapper>();

	/**
	 * Audio codec list.
	 */
	private ComboViewer audioCodecViewer;
	/**
	 * Composite component where audio option interface will be create.
	 */
	private Composite comp;

	/**
	 * Label provider for audio codec list.
	 */
	private LabelProvider labelProvider = new LabelProvider() {
		public String getText(Object element) {
			if (element instanceof AudioOptionsMapper) {
				AudioOptionsMapper mapper = (AudioOptionsMapper) element;
				return Localization.getLocalizedFormat(mapper.getAudioFormat());
			}
			return element.toString();
		}
	};

	/**
	 * Last selected mapper.
	 */
	private AudioOptionsMapper lastMapper;

	/**
	 * Listener to profile context.
	 */
	private IProfileContextListener profileContextListener = new IProfileContextListener() {
		public void profileContextAsChanged(ProfileContext context) {
			profileAsChanged();
		}
	};

	/**
	 * Selection listener to audio codec list.
	 */
	private ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
		public void selectionChanged(SelectionChangedEvent event) {
			audioCodecSelectionChanged();
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
	public AudioOptionsComposite(Composite parent, int style) {
		super(parent, style);

		registerMapper(LameOptionsComposite.getMapper());
		registerMapper(FAACOptionsComposite.getMapper());

	}

	/**
	 * Notify this class that user select a new audio codec.
	 */
	private void audioCodecSelectionChanged() {

		AudioOptionsMapper mapperSelected = getAudioOptionsMapperSelection();
		AudioEncodingOptions audioOptions = mapperSelected.getEncodingOptions();

		ProfileContext profileContext = getViewSite().getProfileContext();
		Profile profile = profileContext.getSelectedProfile();
		EncodingOptions options = profile.getEncodingOptions();
		Class<? extends AudioEncodingOptions> clazz = options.getAudioOptions()
				.getClass();

		if (!clazz.isAssignableFrom(audioOptions.getClass())) {

			options.setAudioOptions(audioOptions);
			Profile newProfile = Profiles.createOnFlyProfile(options);
			profileContext.setSelectedProfile(newProfile);

		}

	}

	/**
	 * Return the mapper associate with the given audio format.
	 * 
	 * @param format
	 *            the audio format.
	 * @return the mapper.
	 */
	private AudioOptionsMapper findMapper(AudioFormat format) {

		int index = 0;
		while (index < mappers.size()
				&& !mappers.get(index).getAudioFormat().equals(format)) {
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
	private AudioOptionsMapper getAudioOptionsMapperSelection() {
		Object selection = ((IStructuredSelection) audioCodecViewer
				.getSelection()).getFirstElement();
		if (!(selection instanceof AudioOptionsMapper)) {
			return null;
		}
		return (AudioOptionsMapper) selection;
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

		String audioCodecText = Localization.getString(Localization.OPTIONS_AUDIO_CODEC);
		
		Label label = new Label(this, SWT.NONE);
		label.setText(audioCodecText);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true,false));
		
		
		audioCodecViewer = new ComboViewer(this, SWT.READ_ONLY | SWT.DROP_DOWN);
		audioCodecViewer.setLabelProvider(labelProvider);
		audioCodecViewer.addSelectionChangedListener(selectionListener);
		audioCodecViewer.getCombo().setLayoutData(
				new GridData(SWT.LEFT, SWT.FILL, true, false));
		for (int index = 0; index < mappers.size(); index++) {
			audioCodecViewer.add(mappers.get(index));
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
	private void profileAsChanged() {

		// Get audio options from curent profile context
		Profile selectedProfile = site.getProfileContext().getSelectedProfile();
		if (selectedProfile instanceof HardCodedProfile) {
			return;
		}
		AudioEncodingOptions audioOptions = selectedProfile
				.getEncodingOptions().getAudioOptions();

		// Get associated mapper
		AudioOptionsMapper mapper = findMapper(audioOptions.getAudioFormat());

		// Set audio codec selection
		AudioOptionsMapper mapperSelected = getAudioOptionsMapperSelection();
		if (mapperSelected == null || !mapperSelected.equals(mapper)) {
			audioCodecViewer.setSelection(new StructuredSelection(mapper));
		}

		if (mapper == lastMapper)
			return;

		// Remove older component
		Control[] childs = comp.getChildren();
		for (int index = 0; index < childs.length; index++) {
			childs[index].dispose();
		}

		// Create new component
		AudioOptionsInterface optionsInterface = mapper.createInterface(comp,
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
	private void registerMapper(AudioOptionsMapper mapper) {

		mappers.add(mapper);

	}

}
