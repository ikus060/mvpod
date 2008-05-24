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

	public static final VideoDemuxer MUXER_AAC = new VideoDemuxer("MUXER_AAC",
			new String[] { ".aac", ".aacp" });
	public static final VideoDemuxer MUXER_ASF = new VideoDemuxer("MUXER_ASF",
			new String[] { ".asf", ".asx", ".wma", ".wmv" });
	public static final VideoDemuxer MUXER_AUDIO = new VideoDemuxer(
			"MUXER_AUDIO", new String[0]);
	public static final VideoDemuxer MUXER_AVINI = new VideoDemuxer(
			"MUXER_AVINI", new String[] { ".avi" });
	public static final VideoDemuxer MUXER_AVI = new VideoDemuxer("MUXER_AVI",
			new String[] { ".avi" });
	public static final VideoDemuxer MUXER_AVS = new VideoDemuxer("MUXER_AVS",
			new String[] { ".avi" });
	public static final VideoDemuxer MUXER_FILM = new VideoDemuxer(
			"MUXER_FILM", new String[] { ".cpk", ".cak", ".film" });
	public static final VideoDemuxer MUXER_FLI = new VideoDemuxer("MUXER_FLI",
			new String[] { ".fli", ".flc", ".cel" });
	public static final VideoDemuxer MUXER_GIF = new VideoDemuxer("MUXER_GIF",
			new String[] { ".gif" });
	public static final VideoDemuxer MUXER_H264ES = new VideoDemuxer(
			"MUXER_H264ES", new String[] { ".h264" });
	public static final VideoDemuxer MUXER_LAVF = new VideoDemuxer(
			"MUXER_LAVF", new String[0]);
	public static final VideoDemuxer MUXER_LAVFPREF = new VideoDemuxer(
			"MUXER_LAVFPREF", new String[0]);
	public static final VideoDemuxer MUXER_LMLM4 = new VideoDemuxer(
			"MUXER_LMLM4", new String[0]);
	public static final VideoDemuxer MUXER_MF = new VideoDemuxer("MUXER_MF",
			new String[0]);
	public static final VideoDemuxer MUXER_MKV = new VideoDemuxer("MUXER_MKV",
			new String[] { ".mkv" });
	public static final VideoDemuxer MUXER_MOV = new VideoDemuxer("MUXER_MOV",
			new String[] { ".mov", ".mp4", ".qt", ".3g2" });
	public static final VideoDemuxer MUXER_MPC = new VideoDemuxer("MUXER_MPC",
			new String[] { ".mpc" });
	public static final VideoDemuxer MUXER_MPEG4ES = new VideoDemuxer(
			"MUXER_MPEG4ES", new String[] { ".mpg", ".m2p", ".vob" });
	public static final VideoDemuxer MUXER_MPEGES = new VideoDemuxer(
			"MUXER_MPEGES", new String[] { ".mpg", ".m2p", ".vob" });
	public static final VideoDemuxer MUXER_MPEGGXF = new VideoDemuxer(
			"MUXER_MPEGGXF", new String[] { ".mpg", ".m2p", ".vob" });
	public static final VideoDemuxer MUXER_MPEGPES = new VideoDemuxer(
			"MUXER_MPEGPES", new String[] { ".mpg", ".m2p", ".vob" });
	public static final VideoDemuxer MUXER_MPEGPS = new VideoDemuxer(
			"MUXER_MPEGPS", new String[] { ".mpg", ".m2p", ".vob", ".evo",
					".eac3" });
	public static final VideoDemuxer MUXER_MPEGTS = new VideoDemuxer(
			"MUXER_MPEGTS", new String[] { ".mpg", ".m2p", ".ts" });
	public static final VideoDemuxer MUXER_NSV = new VideoDemuxer("MUXER_NSV",
			new String[] { ".nsv" });
	public static final VideoDemuxer MUXER_NUV = new VideoDemuxer("MUXER_NUV",
			new String[] { ".nuv" });
	public static final VideoDemuxer MUXER_OGG = new VideoDemuxer("MUXER_OGG",
			new String[] { ".ogg", ".spx", ".ogm" });
	public static final VideoDemuxer MUXER_PVA = new VideoDemuxer("MUXER_PVA",
			new String[] { ".pva" });
	public static final VideoDemuxer MUXER_RAWAUDIO = new VideoDemuxer(
			"MUXER_RAWAUDIO", new String[0]);
	public static final VideoDemuxer MUXER_RAWDV = new VideoDemuxer(
			"MUXER_RAWDV", new String[] { ".eac3" });
	public static final VideoDemuxer MUXER_RAWVIDEO = new VideoDemuxer(
			"MUXER_RAWVIDEO", new String[0]);
	public static final VideoDemuxer MUXER_REAL = new VideoDemuxer(
			"MUXER_REAL", new String[0]);
	public static final VideoDemuxer MUXER_REALAUDIO = new VideoDemuxer(
			"MUXER_REALAUDIO", new String[0]);
	public static final VideoDemuxer MUXER_ROQ = new VideoDemuxer("MUXER_ROQ",
			new String[0]);
	public static final VideoDemuxer MUXER_RTP = new VideoDemuxer("MUXER_RTP",
			new String[0]);
	public static final VideoDemuxer MUXER_SMJPEG = new VideoDemuxer(
			"MUXER_SMJPEG", new String[0]);
	public static final VideoDemuxer MUXER_TIVO = new VideoDemuxer(
			"MUXER_TIVO", new String[0]);
	public static final VideoDemuxer MUXER_TV = new VideoDemuxer("MUXER_TV",
			new String[0]);
	public static final VideoDemuxer MUXER_VIVO = new VideoDemuxer(
			"MUXER_VIVO", new String[0]);
	public static final VideoDemuxer MUXER_VQF = new VideoDemuxer("MUXER_VQF",
			new String[0]);
	public static final VideoDemuxer MUXER_Y4M = new VideoDemuxer("MUXER_Y4M",
			new String[0]);

	private static Map<String, VideoDemuxer> demuxers = new HashMap<String, VideoDemuxer>();
	static {
		demuxers.put("aac", MUXER_AAC);
		demuxers.put("asf", MUXER_ASF);
		demuxers.put("audio", MUXER_AUDIO);
		demuxers.put("avini", MUXER_AVINI);
		demuxers.put("avi", MUXER_AVI);
		demuxers.put("avs", MUXER_AVS);
		demuxers.put("film", MUXER_FILM);
		demuxers.put("fli", MUXER_FLI);
		demuxers.put("gif", MUXER_GIF);
		demuxers.put("h264es", MUXER_H264ES);
		demuxers.put("lavf", MUXER_LAVF);
		demuxers.put("lavfpref", MUXER_LAVFPREF);
		demuxers.put("lmlm4", MUXER_LMLM4);
		demuxers.put("mf", MUXER_MF);
		demuxers.put("mkv", MUXER_MKV);
		demuxers.put("mov", MUXER_MOV);
		demuxers.put("mpc", MUXER_MPC);
		demuxers.put("mpeg4es", MUXER_MPEG4ES);
		demuxers.put("mpeges", MUXER_MPEGES);
		demuxers.put("mpeggxf", MUXER_MPEGGXF);
		demuxers.put("mpegpes", MUXER_MPEGPES);
		demuxers.put("mpegps", MUXER_MPEGPS);
		demuxers.put("mpegts", MUXER_MPEGTS);
		demuxers.put("nsv", MUXER_NSV);
		demuxers.put("nuv", MUXER_NUV);
		demuxers.put("ogg", MUXER_OGG);
		demuxers.put("pva", MUXER_PVA);
		demuxers.put("rawaudio", MUXER_RAWAUDIO);
		demuxers.put("rawdv", MUXER_RAWDV);
		demuxers.put("rawvideo", MUXER_RAWVIDEO);
		demuxers.put("real", MUXER_REAL);
		demuxers.put("realaudio", MUXER_REALAUDIO);
		demuxers.put("roq", MUXER_ROQ);
		demuxers.put("rtp", MUXER_RTP);
		demuxers.put("smjpeg", MUXER_SMJPEG);
		demuxers.put("tivo", MUXER_TIVO);
		demuxers.put("tv", MUXER_TV);
		demuxers.put("vivo", MUXER_VIVO);
		demuxers.put("vqf", MUXER_VQF);
		demuxers.put("y4m", MUXER_Y4M);
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
			return ((VideoDemuxer) obj).demuxerId.equals(demuxerId);
		}
		return false;
	}

	/**
	 * Return the demuxer id.
	 * 
	 * @return format identifier.
	 */
	public String getDemuxerID() {
		return demuxerId;
	}

	/**
	 * Return a list of associated files extention.
	 * 
	 * @return list of file extentions.
	 */
	public String[] getFileExtentions() {
		return fileExtentions.clone();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return demuxerId.hashCode();
	}

}
