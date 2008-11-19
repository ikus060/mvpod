package net.homeip.entreprisesmd.mvconv.gui.options.muxer;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.MP4CreatorMuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This class offer to change the options for MP4Converter.
 * 
 * @author patapouf
 * 
 */
public class MP4ConverterOptionsComposite extends MuxerOptionsInterface {

	/**
	 * Return the mapper for this interface.
	 * 
	 * @return the mapper.
	 */
	public static MuxerOptionsMapper getMapper() {
		return new MuxerOptionsMapper() {
			Muxer muxer = new MP4CreatorMuxer();

			public MuxerOptionsInterface createInterface(Composite parent,
					int style) {
				return new MP4ConverterOptionsComposite(parent, style);
			}

			public Muxer getMuxer() {
				return this.muxer;
			}

			public VideoDemuxer getVideoDemuxer() {
				return this.muxer.getVideoDemuxer();
			}
		};
	}

	/**
	 * Create a new lame composite interface.
	 * 
	 * @param parent
	 *            the parent of this interface.
	 * @param style
	 *            the style.
	 */
	public MP4ConverterOptionsComposite(Composite parent, int style) {
		super(parent, style);
	}

	/**
	 * Initialize this view with the given view site.
	 * 
	 * @param site
	 *            the view site.
	 */
	public void init(IViewSite site) {

		super.init(site);

		this.setLayout(new GridLayout(1, false));

		String notice = Localization.getString(Localization.OPTIONS_MP4_NOTICE);
		Label label = new Label(this, SWT.NONE);
		label.setText(notice);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,true, true));
		
		// Add disposal instruction
		this.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {

			}
		});

	}
}
