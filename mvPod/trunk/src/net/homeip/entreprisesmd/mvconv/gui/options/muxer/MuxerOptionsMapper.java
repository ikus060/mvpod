package net.homeip.entreprisesmd.mvconv.gui.options.muxer;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.VideoDemuxer;
import net.homeip.entreprisesmd.mvconv.mplayerwrapper.muxer.Muxer;

import org.eclipse.swt.widgets.Composite;

/**
 * This interface are use to map the Muxer and an muxer options interface.
 * 
 * @author patapouf
 * 
 */
public interface MuxerOptionsMapper {

	/**
	 * Return the video demuxer of this Muxer mapper.
	 * 
	 * @return the video demuxer.
	 */
	VideoDemuxer getVideoDemuxer();

	/**
	 * Return a default Muxer.
	 * 
	 * @return the Muxer.
	 */
	Muxer getMuxer();

	/**
	 * Create the new muxer options interface.
	 * 
	 * @param parent
	 *            the parent of this interface.
	 * @param style
	 *            the style.
	 */
	MuxerOptionsInterface createInterface(Composite parent, int style);

}
