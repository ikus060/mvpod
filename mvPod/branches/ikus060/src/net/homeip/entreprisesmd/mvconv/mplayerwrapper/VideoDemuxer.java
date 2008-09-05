package net.homeip.entreprisesmd.mvconv.mplayerwrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contain all functionally to handle video format.
 * <p>
 * This class can be use to map mplayer video format value to one of the
 * constant value of this class.
 * </p>
 * 
 * @author patapouf
 * 
 */
public class VideoDemuxer {

	public static final VideoDemuxer MUXER_3GP = new VideoDemuxer("MUXER_3GP", //$NON-NLS-1$
			new String[] { ".3gp", ".3g2" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoDemuxer MUXER_AAC = new VideoDemuxer("MUXER_AAC", //$NON-NLS-1$
			new String[] { ".aac", ".aacp" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoDemuxer MUXER_ASF = new VideoDemuxer("MUXER_ASF", //$NON-NLS-1$
			new String[] { ".asf", ".asx", ".wma", ".wmv" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	public static final VideoDemuxer MUXER_AUDIO = new VideoDemuxer(
			"MUXER_AUDIO", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_AVINI = new VideoDemuxer(
			"MUXER_AVINI", new String[] { ".avi" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoDemuxer MUXER_AVI = new VideoDemuxer("MUXER_AVI", //$NON-NLS-1$
			new String[] { ".avi" }); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_AVS = new VideoDemuxer("MUXER_AVS", //$NON-NLS-1$
			new String[] { ".avi" }); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_FILM = new VideoDemuxer(
			"MUXER_FILM", new String[] { ".cpk", ".cak", ".film" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	public static final VideoDemuxer MUXER_FLI = new VideoDemuxer("MUXER_FLI", //$NON-NLS-1$
			new String[] { ".fli", ".flc", ".cel" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final VideoDemuxer MUXER_GIF = new VideoDemuxer("MUXER_GIF", //$NON-NLS-1$
			new String[] { ".gif" }); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_H264ES = new VideoDemuxer(
			"MUXER_H264ES", new String[] { ".h264" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoDemuxer MUXER_LAVF = new VideoDemuxer(
			"MUXER_LAVF", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_LAVFPREF = new VideoDemuxer(
			"MUXER_LAVFPREF", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_LMLM4 = new VideoDemuxer(
			"MUXER_LMLM4", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_MF = new VideoDemuxer("MUXER_MF", //$NON-NLS-1$
			new String[0]);
	public static final VideoDemuxer MUXER_MKV = new VideoDemuxer("MUXER_MKV", //$NON-NLS-1$
			new String[] { ".mkv" }); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_MOV = new VideoDemuxer("MUXER_MOV", //$NON-NLS-1$
			new String[] { ".mov", ".qt" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoDemuxer MUXER_MPG4 = new VideoDemuxer(
			"MUXER_MPG4", new String[] { ".mp4" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoDemuxer MUXER_MPC = new VideoDemuxer("MUXER_MPC", //$NON-NLS-1$
			new String[] { ".mpc" }); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_MPEG4ES = new VideoDemuxer(
			"MUXER_MPEG4ES", new String[] { ".mpg", ".m2p", ".vob" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	public static final VideoDemuxer MUXER_MPEGES = new VideoDemuxer(
			"MUXER_MPEGES", new String[] { ".mpg", ".m2p", ".vob" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	public static final VideoDemuxer MUXER_MPEGGXF = new VideoDemuxer(
			"MUXER_MPEGGXF", new String[] { ".mpg", ".m2p", ".vob" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	public static final VideoDemuxer MUXER_MPEGPES = new VideoDemuxer(
			"MUXER_MPEGPES", new String[] { ".mpg", ".m2p", ".vob" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	public static final VideoDemuxer MUXER_MPEGPS = new VideoDemuxer(
			"MUXER_MPEGPS", new String[] { ".mpg", ".m2p", ".vob", ".evo", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					".eac3" }); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_MPEGTS = new VideoDemuxer(
			"MUXER_MPEGTS", new String[] { ".mpg", ".m2p", ".ts" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	public static final VideoDemuxer MUXER_NSV = new VideoDemuxer("MUXER_NSV", //$NON-NLS-1$
			new String[] { ".nsv" }); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_NUV = new VideoDemuxer("MUXER_NUV", //$NON-NLS-1$
			new String[] { ".nuv" }); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_OGG = new VideoDemuxer("MUXER_OGG", //$NON-NLS-1$
			new String[] { ".ogg", ".spx", ".ogm" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	public static final VideoDemuxer MUXER_PVA = new VideoDemuxer("MUXER_PVA", //$NON-NLS-1$
			new String[] { ".pva" }); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_RAWAUDIO = new VideoDemuxer(
			"MUXER_RAWAUDIO", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_RAWDV = new VideoDemuxer(
			"MUXER_RAWDV", new String[] { ".eac3" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoDemuxer MUXER_RAWVIDEO = new VideoDemuxer(
			"MUXER_RAWVIDEO", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_REAL = new VideoDemuxer(
			"MUXER_REAL", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_REALAUDIO = new VideoDemuxer(
			"MUXER_REALAUDIO", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_ROQ = new VideoDemuxer("MUXER_ROQ", //$NON-NLS-1$
			new String[0]);
	public static final VideoDemuxer MUXER_RTP = new VideoDemuxer("MUXER_RTP", //$NON-NLS-1$
			new String[0]);
	public static final VideoDemuxer MUXER_SMJPEG = new VideoDemuxer(
			"MUXER_SMJPEG", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_TIVO = new VideoDemuxer(
			"MUXER_TIVO", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_TV = new VideoDemuxer("MUXER_TV", //$NON-NLS-1$
			new String[0]);
	public static final VideoDemuxer MUXER_VIVO = new VideoDemuxer(
			"MUXER_VIVO", new String[0]); //$NON-NLS-1$
	public static final VideoDemuxer MUXER_VQF = new VideoDemuxer("MUXER_VQF", //$NON-NLS-1$
			new String[0]);
	public static final VideoDemuxer MUXER_Y4M = new VideoDemuxer("MUXER_Y4M", //$NON-NLS-1$
			new String[0]);

	private static Map<String, VideoDemuxer> demuxers = new HashMap<String, VideoDemuxer>();
	static {
		demuxers.put("aac", MUXER_AAC); //$NON-NLS-1$
		demuxers.put("asf", MUXER_ASF); //$NON-NLS-1$
		demuxers.put("audio", MUXER_AUDIO); //$NON-NLS-1$
		demuxers.put("avini", MUXER_AVINI); //$NON-NLS-1$
		demuxers.put("avi", MUXER_AVI); //$NON-NLS-1$
		demuxers.put("avs", MUXER_AVS); //$NON-NLS-1$
		demuxers.put("film", MUXER_FILM); //$NON-NLS-1$
		demuxers.put("fli", MUXER_FLI);  //$NON-NLS-1$
		demuxers.put("gif", MUXER_GIF);  //$NON-NLS-1$
		demuxers.put("h264es", MUXER_H264ES);  //$NON-NLS-1$
		demuxers.put("lavf", MUXER_LAVF);  //$NON-NLS-1$
		demuxers.put("lavfpref", MUXER_LAVFPREF);  //$NON-NLS-1$
		demuxers.put("lmlm4", MUXER_LMLM4);  //$NON-NLS-1$
		demuxers.put("mf", MUXER_MF);  //$NON-NLS-1$
		demuxers.put("mkv", MUXER_MKV);  //$NON-NLS-1$
		demuxers.put("mov", MUXER_MOV);  //$NON-NLS-1$
		demuxers.put("mpc", MUXER_MPC);  //$NON-NLS-1$
		demuxers.put("mpeg4es", MUXER_MPEG4ES);  //$NON-NLS-1$
		demuxers.put("mpeges", MUXER_MPEGES);  //$NON-NLS-1$
		demuxers.put("mpeggxf", MUXER_MPEGGXF);  //$NON-NLS-1$
		demuxers.put("mpegpes", MUXER_MPEGPES);  //$NON-NLS-1$
		demuxers.put("mpegps", MUXER_MPEGPS);  //$NON-NLS-1$
		demuxers.put("mpegts", MUXER_MPEGTS);  //$NON-NLS-1$
		demuxers.put("nsv", MUXER_NSV);  //$NON-NLS-1$
		demuxers.put("nuv", MUXER_NUV);  //$NON-NLS-1$
		demuxers.put("ogg", MUXER_OGG);  //$NON-NLS-1$
		demuxers.put("pva", MUXER_PVA);  //$NON-NLS-1$
		demuxers.put("rawaudio", MUXER_RAWAUDIO);  //$NON-NLS-1$
		demuxers.put("rawdv", MUXER_RAWDV);  //$NON-NLS-1$
		demuxers.put("rawvideo", MUXER_RAWVIDEO);  //$NON-NLS-1$
		demuxers.put("real", MUXER_REAL);  //$NON-NLS-1$
		demuxers.put("realaudio", MUXER_REALAUDIO);  //$NON-NLS-1$
		demuxers.put("roq", MUXER_ROQ);  //$NON-NLS-1$
		demuxers.put("rtp", MUXER_RTP);  //$NON-NLS-1$
		demuxers.put("smjpeg", MUXER_SMJPEG);  //$NON-NLS-1$
		demuxers.put("tivo", MUXER_TIVO);  //$NON-NLS-1$
		demuxers.put("tv", MUXER_TV);  //$NON-NLS-1$
		demuxers.put("vivo", MUXER_VIVO);  //$NON-NLS-1$
		demuxers.put("vqf", MUXER_VQF);  //$NON-NLS-1$
		demuxers.put("y4m", MUXER_Y4M);  //$NON-NLS-1$
	}

	/**
	 * Return the associated audio format, as define in VideoInfo, according to
	 * the given video identifer return by mplayer.
	 * 
	 * @param muxerID
	 *            the formatID return by mplayer.
	 * @return the format identifier.
	 */
	public static VideoDemuxer fromVideoFormatID(String muxerID) {

		return demuxers.get(muxerID);

	}

	/**
	 * Format identifier.
	 */
	private String demuxerId;
	/**
	 * File extensions list.
	 */
	private String[] fileExtentions;

	/**
	 * Create a new video demuxer format object.
	 * 
	 * @param id
	 *            the muxer identifier.
	 */
	private VideoDemuxer(String id, String[] extentions) {
		this.demuxerId = id;
		this.fileExtentions = extentions;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof VideoDemuxer) {
			return ((VideoDemuxer) obj).demuxerId.equals(this.demuxerId);
		}
		return false;
	}

	/**
	 * Return the demuxer id.
	 * 
	 * @return format identifier.
	 */
	public String getDemuxerID() {
		return this.demuxerId;
	}

	/**
	 * Return a list of associated files extention.
	 * 
	 * @return list of file extentions.
	 */
	public String[] getFileExtentions() {
		return this.fileExtentions.clone();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.demuxerId.hashCode();
	}

}
