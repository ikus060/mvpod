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
public class VideoFormat {

	public static final VideoFormat FORMAT_3IV1 = new VideoFormat("FORMAT_3IV1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_3IV2 = new VideoFormat("FORMAT_3IV2"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_8BPS = new VideoFormat("FORMAT_8BPS"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_AASC = new VideoFormat("FORMAT_AASC"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_ACTL = new VideoFormat("FORMAT_ACTL"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_AP41 = new VideoFormat("FORMAT_AP41"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_ASV1 = new VideoFormat("FORMAT_ASV1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_ASV2 = new VideoFormat("FORMAT_ASV2"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_AVDJ = new VideoFormat("FORMAT_AVDJ"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_AVRN = new VideoFormat("FORMAT_AVRN"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_BLZ0 = new VideoFormat("FORMAT_BLZ0"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_CDVC = new VideoFormat("FORMAT_CDVC"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_CFHD = new VideoFormat("FORMAT_CFHD"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_CJPG = new VideoFormat("FORMAT_CJPG"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_CLJR = new VideoFormat("FORMAT_CLJR"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_COL1 = new VideoFormat("FORMAT_COL1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_CRAM = new VideoFormat("FORMAT_CRAM"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_CSCD = new VideoFormat("FORMAT_CSCD"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_CVID = new VideoFormat("FORMAT_CVID"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_CYUV = new VideoFormat("FORMAT_CYUV"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_DIV5 = new VideoFormat("FORMAT_DIV5"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_DIVX = new VideoFormat(
			"FORMAT_DIVX", new String[] { ".divx" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoFormat FORMAT_DMB1 = new VideoFormat("FORMAT_DMB1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_DUCK = new VideoFormat("FORMAT_DUCK"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_DV50 = new VideoFormat("FORMAT_DV50"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_DVC = new VideoFormat("FORMAT_DVC"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_DVCP = new VideoFormat("FORMAT_DVCP"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_DVSD = new VideoFormat("FORMAT_DVSD"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_DX50 = new VideoFormat("FORMAT_DX50"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_DXGM = new VideoFormat("FORMAT_DXGM"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_FLV1 = new VideoFormat("FORMAT_FLV1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_FPS1 = new VideoFormat("FORMAT_FPS1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_GXVE = new VideoFormat("FORMAT_GXVE"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_H261 = new VideoFormat("FORMAT_H261"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_H263 = new VideoFormat(
			"FORMAT_H263", new String[] { ".263" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoFormat FORMAT_H264 = new VideoFormat(
			"FORMAT_H264", new String[] { ".264" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoFormat FORMAT_H264_AVC = new VideoFormat(
			"FORMAT_H264_AVC"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_HFYU = new VideoFormat("FORMAT_HFYU"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_I263 = new VideoFormat("FORMAT_I263"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_ICOD = new VideoFormat("FORMAT_ICOD"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_IJPG = new VideoFormat("FORMAT_IJPG"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_IV32 = new VideoFormat("FORMAT_IV32"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_IV41 = new VideoFormat("FORMAT_IV41"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_IV50 = new VideoFormat("FORMAT_IV50"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_JPEG = new VideoFormat("FORMAT_JPEG"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_JPGL = new VideoFormat("FORMAT_JPGL"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_KMVC = new VideoFormat("FORMAT_KMVC"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_LOCO = new VideoFormat("FORMAT_LOCO"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_LSVM = new VideoFormat("FORMAT_LSVM"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_LSVX = new VideoFormat("FORMAT_LSVX"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_LZO1 = new VideoFormat("FORMAT_LZO1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_M263 = new VideoFormat("FORMAT_M263"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_M4S2 = new VideoFormat("FORMAT_M4S2"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MJPA = new VideoFormat("FORMAT_MJPA"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MJPB = new VideoFormat("FORMAT_MJPB"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MJPG = new VideoFormat("FORMAT_MJPG"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MP43 = new VideoFormat("FORMAT_MP43"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MP4S = new VideoFormat("FORMAT_MP4S"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MP4V = new VideoFormat(
			"FORMAT_MP4V", new String[] { ".mp4v" }); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoFormat FORMAT_MPEG_PES = new VideoFormat(
			"FORMAT_MPEG_PES"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MPEG_XVID = new VideoFormat(
			"FORMAT_MPEG_XVID", new String[]{".divx"}); //$NON-NLS-1$ //$NON-NLS-2$
	public static final VideoFormat FORMAT_MPG3 = new VideoFormat("FORMAT_MPG3"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MPG4 = new VideoFormat("FORMAT_MPG4"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MSS1 = new VideoFormat("FORMAT_MSS1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MSZH = new VideoFormat("FORMAT_MSZH"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MV43 = new VideoFormat("FORMAT_MV43"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MVDV = new VideoFormat("FORMAT_MVDV"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_MWV1 = new VideoFormat("FORMAT_MWV1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_PGVV = new VideoFormat("FORMAT_PGVV"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_PIM1 = new VideoFormat("FORMAT_PIM1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_PNG = new VideoFormat("FORMAT_PNG"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_PNG1 = new VideoFormat("FORMAT_PNG1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_PSIV = new VideoFormat("FORMAT_PSIV"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_PVW2 = new VideoFormat("FORMAT_PVW2"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_Q10 = new VideoFormat("FORMAT_Q10"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_Q11 = new VideoFormat("FORMAT_Q11"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_QDRW = new VideoFormat("FORMAT_QDRW"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_RAW = new VideoFormat("FORMAT_RAW"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_RLE = new VideoFormat("FORMAT_RLE"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_RMP4 = new VideoFormat("FORMAT_RMP4"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_RPZA = new VideoFormat("FORMAT_RPZA"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_RT21 = new VideoFormat("FORMAT_RT21"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_SEDG = new VideoFormat("FORMAT_SEDG"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_SMC = new VideoFormat("FORMAT_SMC"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_SMP4 = new VideoFormat("FORMAT_SMP4"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_SNOW = new VideoFormat("FORMAT_SNOW"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_SP54 = new VideoFormat("FORMAT_SP54"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_SP61 = new VideoFormat("FORMAT_SP61"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_SVQ1 = new VideoFormat("FORMAT_SVQ1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_SVQ3 = new VideoFormat("FORMAT_SVQ3"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_THEO = new VideoFormat("FORMAT_THEO"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_TM20 = new VideoFormat("FORMAT_TM20"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_TSCC = new VideoFormat("FORMAT_TSCC"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_U263 = new VideoFormat("FORMAT_U263"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_UCOD = new VideoFormat("FORMAT_UCOD"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_ULTI = new VideoFormat("FORMAT_ULTI"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_UMP4 = new VideoFormat("FORMAT_UMP4"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VCR1 = new VideoFormat("FORMAT_VCR1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VCR2 = new VideoFormat("FORMAT_VCR2"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VDOM = new VideoFormat("FORMAT_VDOM"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VIXL = new VideoFormat("FORMAT_VIXL"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VMNC = new VideoFormat("FORMAT_VMNC"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VP30 = new VideoFormat("FORMAT_VP30"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VP31 = new VideoFormat("FORMAT_VP31"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VP40 = new VideoFormat("FORMAT_VP40"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VP50 = new VideoFormat("FORMAT_VP50"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VP61 = new VideoFormat("FORMAT_VP61"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VP62 = new VideoFormat("FORMAT_VP62"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VSSH = new VideoFormat("FORMAT_VSSH"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_VSSV = new VideoFormat("FORMAT_VSSV"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WINX = new VideoFormat("FORMAT_WINX"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WMV1 = new VideoFormat("FORMAT_WMV1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WMV2 = new VideoFormat("FORMAT_WMV2"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WMV3 = new VideoFormat("FORMAT_WMV3"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WMVA = new VideoFormat("FORMAT_WMVA"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WMVP = new VideoFormat("FORMAT_WMVP"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WNV1 = new VideoFormat("FORMAT_WNV1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WV1F = new VideoFormat("FORMAT_WV1F"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WVC1 = new VideoFormat("FORMAT_WVC1"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_WVP2 = new VideoFormat("FORMAT_WVP2"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_YV12 = new VideoFormat("FORMAT_YV12"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_YVU9 = new VideoFormat("FORMAT_YVU9"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_ZLIB = new VideoFormat("FORMAT_ZLIB"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_ZMBV = new VideoFormat("FORMAT_ZMBV"); //$NON-NLS-1$
	public static final VideoFormat FORMAT_ZYGO = new VideoFormat("FORMAT_ZYGO"); //$NON-NLS-1$

	private static Map<String, VideoFormat> formats = new HashMap<String, VideoFormat>();
	static {
		formats.put("0x00000001", FORMAT_RAW); //$NON-NLS-1$
		formats.put("0x00000001", FORMAT_RLE); //$NON-NLS-1$
		formats.put("0x00000002", FORMAT_RLE); //$NON-NLS-1$
		formats.put("0x00000004", FORMAT_DIVX); //$NON-NLS-1$
		formats.put("0x10000001", FORMAT_MPEG_PES); //$NON-NLS-1$
		formats.put("0x10000002", FORMAT_MPEG_PES); //$NON-NLS-1$
		formats.put("0x10000005", FORMAT_H264); //$NON-NLS-1$
		formats.put("3IV1", FORMAT_3IV1); //$NON-NLS-1$
		formats.put("3IV2", FORMAT_3IV2); //$NON-NLS-1$
		formats.put("8BPS", FORMAT_8BPS); //$NON-NLS-1$
		formats.put("AASC", FORMAT_AASC); //$NON-NLS-1$
		formats.put("ACTL", FORMAT_ACTL); //$NON-NLS-1$
		formats.put("AP41", FORMAT_AP41); //$NON-NLS-1$
		formats.put("ASV1", FORMAT_ASV1); //$NON-NLS-1$
		formats.put("ASV2", FORMAT_ASV2); //$NON-NLS-1$
		formats.put("avc1", FORMAT_H264_AVC); //$NON-NLS-1$
		formats.put("AVDJ", FORMAT_AVDJ); //$NON-NLS-1$
		formats.put("AVRn", FORMAT_AVRN); //$NON-NLS-1$
		formats.put("BLZ0", FORMAT_BLZ0); //$NON-NLS-1$
		formats.put("CDVC", FORMAT_CDVC); //$NON-NLS-1$
		formats.put("CFHD", FORMAT_CFHD); //$NON-NLS-1$
		formats.put("CJPG", FORMAT_CJPG); //$NON-NLS-1$
		formats.put("CLJR", FORMAT_CLJR); //$NON-NLS-1$
		formats.put("COL1", FORMAT_COL1); //$NON-NLS-1$
		formats.put("CRAM", FORMAT_CRAM); //$NON-NLS-1$
		formats.put("CSCD", FORMAT_CSCD); //$NON-NLS-1$
		formats.put("cvid", FORMAT_CVID); //$NON-NLS-1$
		formats.put("cyuv", FORMAT_CYUV); //$NON-NLS-1$
		formats.put("DIV5", FORMAT_DIV5); //$NON-NLS-1$
		formats.put("DIVX", FORMAT_DIVX); //$NON-NLS-1$
		formats.put("dmb1", FORMAT_DMB1); //$NON-NLS-1$
		formats.put("DUCK", FORMAT_DUCK); //$NON-NLS-1$
		formats.put("dv50", FORMAT_DV50); //$NON-NLS-1$
		formats.put("dvc ", FORMAT_DVC); //$NON-NLS-1$
		formats.put("dvcp", FORMAT_DVCP); //$NON-NLS-1$
		formats.put("dvsd", FORMAT_DVSD); //$NON-NLS-1$
		formats.put("DVSD", FORMAT_DVSD); //$NON-NLS-1$
		formats.put("DX50", FORMAT_DX50); //$NON-NLS-1$
		formats.put("DXGM", FORMAT_DXGM); //$NON-NLS-1$
		formats.put("FLV1", FORMAT_FLV1); //$NON-NLS-1$
		formats.put("FPS1", FORMAT_FPS1); //$NON-NLS-1$
		formats.put("GXVE", FORMAT_GXVE); //$NON-NLS-1$
		formats.put("H261", FORMAT_H261); //$NON-NLS-1$
		formats.put("h263", FORMAT_H263); //$NON-NLS-1$
		formats.put("h264", FORMAT_H264); //$NON-NLS-1$
		formats.put("HFYU", FORMAT_HFYU); //$NON-NLS-1$
		formats.put("I263", FORMAT_I263); //$NON-NLS-1$
		formats.put("icod", FORMAT_ICOD); //$NON-NLS-1$
		formats.put("IJPG", FORMAT_IJPG); //$NON-NLS-1$
		formats.put("IV32", FORMAT_IV32); //$NON-NLS-1$
		formats.put("IV41", FORMAT_IV41); //$NON-NLS-1$
		formats.put("IV50", FORMAT_IV50); //$NON-NLS-1$
		formats.put("jpeg", FORMAT_JPEG); //$NON-NLS-1$
		formats.put("JPGL", FORMAT_JPGL); //$NON-NLS-1$
		formats.put("KMVC", FORMAT_KMVC); //$NON-NLS-1$
		formats.put("LOCO", FORMAT_LOCO); //$NON-NLS-1$
		formats.put("lsvm", FORMAT_LSVM); //$NON-NLS-1$
		formats.put("lsvx", FORMAT_LSVX); //$NON-NLS-1$
		formats.put("LZO1", FORMAT_LZO1); //$NON-NLS-1$
		formats.put("M263", FORMAT_M263); //$NON-NLS-1$
		formats.put("M4S2", FORMAT_M4S2); //$NON-NLS-1$
		formats.put("mjpa", FORMAT_MJPA); //$NON-NLS-1$
		formats.put("mjpb", FORMAT_MJPB); //$NON-NLS-1$
		formats.put("MJPG", FORMAT_MJPG); //$NON-NLS-1$
		formats.put("MP43", FORMAT_MP43); //$NON-NLS-1$
		formats.put("MP4S", FORMAT_MP4S); //$NON-NLS-1$
		formats.put("mp4v", FORMAT_MP4V); //$NON-NLS-1$
		formats.put("MPG3", FORMAT_MPG3); //$NON-NLS-1$
		formats.put("MPG4", FORMAT_MPG4); //$NON-NLS-1$
		formats.put("MSS1", FORMAT_MSS1); //$NON-NLS-1$
		formats.put("MSZH", FORMAT_MSZH); //$NON-NLS-1$
		formats.put("MV43", FORMAT_MV43); //$NON-NLS-1$
		formats.put("MVDV", FORMAT_MVDV); //$NON-NLS-1$
		formats.put("MWV1", FORMAT_MWV1); //$NON-NLS-1$
		formats.put("PGVV", FORMAT_PGVV); //$NON-NLS-1$
		formats.put("PIM1", FORMAT_PIM1); //$NON-NLS-1$
		formats.put("png ", FORMAT_PNG); //$NON-NLS-1$
		formats.put("PNG1", FORMAT_PNG1); //$NON-NLS-1$
		formats.put("PSIV", FORMAT_PSIV); //$NON-NLS-1$
		formats.put("PVW2", FORMAT_PVW2); //$NON-NLS-1$
		formats.put("Q1.0", FORMAT_Q10); //$NON-NLS-1$
		formats.put("Q1.1", FORMAT_Q11); //$NON-NLS-1$
		formats.put("qdrw", FORMAT_QDRW); //$NON-NLS-1$
		formats.put("rle ", FORMAT_RLE); //$NON-NLS-1$
		formats.put("RMP4", FORMAT_RMP4); //$NON-NLS-1$
		formats.put("rpza", FORMAT_RPZA); //$NON-NLS-1$
		formats.put("RT21", FORMAT_RT21); //$NON-NLS-1$
		formats.put("s263", FORMAT_H263); //$NON-NLS-1$
		formats.put("SEDG", FORMAT_SEDG); //$NON-NLS-1$
		formats.put("smc ", FORMAT_SMC); //$NON-NLS-1$
		formats.put("SMP4", FORMAT_SMP4); //$NON-NLS-1$
		formats.put("SNOW", FORMAT_SNOW); //$NON-NLS-1$
		formats.put("SP54", FORMAT_SP54); //$NON-NLS-1$
		formats.put("SP61", FORMAT_SP61); //$NON-NLS-1$
		formats.put("SVQ1", FORMAT_SVQ1); //$NON-NLS-1$
		formats.put("SVQ3", FORMAT_SVQ3); //$NON-NLS-1$
		formats.put("theo", FORMAT_THEO); //$NON-NLS-1$
		formats.put("TM20", FORMAT_TM20); //$NON-NLS-1$
		formats.put("tscc", FORMAT_TSCC); //$NON-NLS-1$
		formats.put("U263", FORMAT_U263); //$NON-NLS-1$
		formats.put("UCOD", FORMAT_UCOD); //$NON-NLS-1$
		formats.put("ULTI", FORMAT_ULTI); //$NON-NLS-1$
		formats.put("UMP4", FORMAT_UMP4); //$NON-NLS-1$
		formats.put("VCR1", FORMAT_VCR1); //$NON-NLS-1$
		formats.put("VCR2", FORMAT_VCR2); //$NON-NLS-1$
		formats.put("VDOM", FORMAT_VDOM); //$NON-NLS-1$
		formats.put("VIXL", FORMAT_VIXL); //$NON-NLS-1$
		formats.put("VMnc", FORMAT_VMNC); //$NON-NLS-1$
		formats.put("VP30", FORMAT_VP30); //$NON-NLS-1$
		formats.put("VP31", FORMAT_VP31); //$NON-NLS-1$
		formats.put("VP40", FORMAT_VP40); //$NON-NLS-1$
		formats.put("VP50", FORMAT_VP50); //$NON-NLS-1$
		formats.put("VP61", FORMAT_VP61); //$NON-NLS-1$
		formats.put("VP62", FORMAT_VP62); //$NON-NLS-1$
		formats.put("VSSH", FORMAT_VSSH); //$NON-NLS-1$
		formats.put("VSSV", FORMAT_VSSV); //$NON-NLS-1$
		formats.put("WINX", FORMAT_WINX); //$NON-NLS-1$
		formats.put("WMV1", FORMAT_WMV1); //$NON-NLS-1$
		formats.put("WMV2", FORMAT_WMV2); //$NON-NLS-1$
		formats.put("WMV3", FORMAT_WMV3); //$NON-NLS-1$
		formats.put("WMVA", FORMAT_WMVA); //$NON-NLS-1$
		formats.put("WMVP", FORMAT_WMVP); //$NON-NLS-1$
		formats.put("WNV1", FORMAT_WNV1); //$NON-NLS-1$
		formats.put("WV1F", FORMAT_WV1F); //$NON-NLS-1$
		formats.put("WVC1", FORMAT_WVC1); //$NON-NLS-1$
		formats.put("WVP2", FORMAT_WVP2); //$NON-NLS-1$
		formats.put("XVID", FORMAT_MPEG_XVID); //$NON-NLS-1$
		formats.put("YV12", FORMAT_YV12); //$NON-NLS-1$
		formats.put("YVU9", FORMAT_YVU9); //$NON-NLS-1$
		formats.put("ZLIB", FORMAT_ZLIB); //$NON-NLS-1$
		formats.put("ZMBV", FORMAT_ZMBV); //$NON-NLS-1$
		formats.put("ZyGo", FORMAT_ZYGO); //$NON-NLS-1$
	}

	/**
	 * Return the associated audio format, as define in VideoInfo, according to
	 * the given video identifer return by mplayer.
	 * 
	 * @param formatID
	 *            the formatID return by mplayer.
	 * @return the format identifier.
	 */
	public static VideoFormat fromMplayerVideoFormatID(String formatID) {

		return formats.get(formatID);

	}

	/**
	 * File extensions list.
	 */
	private String[] fileExtentions;

	/**
	 * Format identifier.
	 */
	private String formatId;

	/**
	 * Create a new video format object.
	 * 
	 * @param id
	 *            the format identifier.
	 */
	private VideoFormat(String id) {
		this(id, null);
	}

	/**
	 * Create a new video format object.
	 * 
	 * @param id
	 *            the format identifier.
	 */
	private VideoFormat(String id, String[] extentions) {
		this.formatId = id;
		this.fileExtentions = extentions;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof VideoFormat) {
			return ((VideoFormat) obj).formatId.equals(this.formatId);
		}
		return false;
	}

	/**
	 * Return a list of associated files extention. (e.g.: .mp3, .flac, .ogg)
	 * 
	 * @return list of file extentions.
	 */
	public String[] getFileExtentions() {
		if (this.fileExtentions != null) {
			return this.fileExtentions.clone();
		}
		return new String[0];
	}

	/**
	 * Return the format id.
	 * 
	 * @return format identifier.
	 */
	public String getFormatID() {
		return this.formatId;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return this.formatId.hashCode();
	}

}
