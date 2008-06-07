package net.homeip.entreprisesmd.mvconv.gui;

import net.homeip.entreprisesmd.mvconv.mplayerwrapper.MPlayerWrapper;

public interface MPlayerProvider {
	
	/**
	 * Return an mplayer wrapper.
	 * @return an mplayer wrapper.
	 */
	MPlayerWrapper getWrapper();

}
