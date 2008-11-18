package net.homeip.entreprisesmd.mvconv.gui.options.muxer;

import net.homeip.entreprisesmd.mvconv.core.Localization;
import net.homeip.entreprisesmd.mvconv.gui.IViewSite;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.DefaultMuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * This class offer to change the options for Lame codec.
 * 
 * @author patapouf
 * 
 */
public class AviOptionsComposite extends MuxerOptionsInterface {

	/**
	 * Return the mapper for this interface.
	 * 
	 * @return the mapper.
	 */
	public static MuxerOptionsMapper getMapper() {
		return new MuxerOptionsMapper() {
			Muxer muxer = new DefaultMuxer();

			public MuxerOptionsInterface createInterface(Composite parent,
					int style) {
				return new AviOptionsComposite(parent, style);
			}

			public Muxer getMuxer() {
				return muxer;
			}

			public VideoDemuxer getVideoDemuxer() {
				return muxer.getVideoDemuxer();
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
	public AviOptionsComposite(Composite parent, int style) {
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

		this.setLayout(new GridLayout(4, false));
		
		String notice = Localization.getString(Localization.OPTIONS_AVI_NOTICE);
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
