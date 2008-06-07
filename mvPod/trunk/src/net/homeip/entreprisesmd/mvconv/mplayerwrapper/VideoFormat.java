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

	public static final VideoFormat FORMAT_3IV1 = new VideoFormat( "FORMAT_3IV1" );
	public static final VideoFormat FORMAT_3IV2 = new VideoFormat( "FORMAT_3IV2" );
	public static final VideoFormat FORMAT_8BPS = new VideoFormat( "FORMAT_8BPS" );
	public static final VideoFormat FORMAT_AASC = new VideoFormat( "FORMAT_AASC" );
	public static final VideoFormat FORMAT_ACTL = new VideoFormat( "FORMAT_ACTL" );
	public static final VideoFormat FORMAT_AP41 = new VideoFormat( "FORMAT_AP41" );
	public static final VideoFormat FORMAT_ASV1 = new VideoFormat( "FORMAT_ASV1" );
	public static final VideoFormat FORMAT_ASV2 = new VideoFormat( "FORMAT_ASV2" );
	public static final VideoFormat FORMAT_AVDJ = new VideoFormat( "FORMAT_AVDJ" );
	public static final VideoFormat FORMAT_AVRN = new VideoFormat( "FORMAT_AVRN" );
	public static final VideoFormat FORMAT_BLZ0 = new VideoFormat( "FORMAT_BLZ0" );
	public static final VideoFormat FORMAT_CDVC = new VideoFormat( "FORMAT_CDVC" );
	public static final VideoFormat FORMAT_CFHD = new VideoFormat( "FORMAT_CFHD" );
	public static final VideoFormat FORMAT_CJPG = new VideoFormat( "FORMAT_CJPG" );
	public static final VideoFormat FORMAT_CLJR = new VideoFormat( "FORMAT_CLJR" );
	public static final VideoFormat FORMAT_COL1 = new VideoFormat( "FORMAT_COL1" );
	public static final VideoFormat FORMAT_CRAM = new VideoFormat( "FORMAT_CRAM" );
	public static final VideoFormat FORMAT_CSCD = new VideoFormat( "FORMAT_CSCD" );
	public static final VideoFormat FORMAT_CVID = new VideoFormat( "FORMAT_CVID" );
	public static final VideoFormat FORMAT_CYUV = new VideoFormat( "FORMAT_CYUV" );
	public static final VideoFormat FORMAT_DIV5 = new VideoFormat( "FORMAT_DIV5" );
	public static final VideoFormat FORMAT_DIVX = new VideoFormat( "FORMAT_DIVX" );
	public static final VideoFormat FORMAT_DMB1 = new VideoFormat( "FORMAT_DMB1" );
	public static final VideoFormat FORMAT_DUCK = new VideoFormat( "FORMAT_DUCK" );
	public static final VideoFormat FORMAT_DV50 = new VideoFormat( "FORMAT_DV50" );
	public static final VideoFormat FORMAT_DVC = new VideoFormat( "FORMAT_DVC" );
	public static final VideoFormat FORMAT_DVCP = new VideoFormat( "FORMAT_DVCP" );
	public static final VideoFormat FORMAT_DVSD = new VideoFormat( "FORMAT_DVSD" );
	public static final VideoFormat FORMAT_DX50 = new VideoFormat( "FORMAT_DX50" );
	public static final VideoFormat FORMAT_DXGM = new VideoFormat( "FORMAT_DXGM" );
	public static final VideoFormat FORMAT_FLV1 = new VideoFormat( "FORMAT_FLV1" );
	public static final VideoFormat FORMAT_FPS1 = new VideoFormat( "FORMAT_FPS1" );
	public static final VideoFormat FORMAT_GXVE = new VideoFormat( "FORMAT_GXVE" );
	public static final VideoFormat FORMAT_H261 = new VideoFormat( "FORMAT_H261" );
	public static final VideoFormat FORMAT_H263 = new VideoFormat( "FORMAT_H263" );
	public static final VideoFormat FORMAT_H264 = new VideoFormat( "FORMAT_H264" );
	public static final VideoFormat FORMAT_H264_AVC = new VideoFormat( "FORMAT_H264_AVC" );
	public static final VideoFormat FORMAT_HFYU = new VideoFormat( "FORMAT_HFYU" );
	public static final VideoFormat FORMAT_I263 = new VideoFormat( "FORMAT_I263" );
	public static final VideoFormat FORMAT_ICOD = new VideoFormat( "FORMAT_ICOD" );
	public static final VideoFormat FORMAT_IJPG = new VideoFormat( "FORMAT_IJPG" );
	public static final VideoFormat FORMAT_IV32 = new VideoFormat( "FORMAT_IV32" );
	public static final VideoFormat FORMAT_IV41 = new VideoFormat( "FORMAT_IV41" );
	public static final VideoFormat FORMAT_IV50 = new VideoFormat( "FORMAT_IV50" );
	public static final VideoFormat FORMAT_JPEG = new VideoFormat( "FORMAT_JPEG" );
	public static final VideoFormat FORMAT_JPGL = new VideoFormat( "FORMAT_JPGL" );
	public static final VideoFormat FORMAT_KMVC = new VideoFormat( "FORMAT_KMVC" );
	public static final VideoFormat FORMAT_LOCO = new VideoFormat( "FORMAT_LOCO" );
	public static final VideoFormat FORMAT_LSVM = new VideoFormat( "FORMAT_LSVM" );
	public static final VideoFormat FORMAT_LSVX = new VideoFormat( "FORMAT_LSVX" );
	public static final VideoFormat FORMAT_LZO1 = new VideoFormat( "FORMAT_LZO1" );
	public static final VideoFormat FORMAT_M263 = new VideoFormat( "FORMAT_M263" );
	public static final VideoFormat FORMAT_M4S2 = new VideoFormat( "FORMAT_M4S2" );
	public static final VideoFormat FORMAT_MJPA = new VideoFormat( "FORMAT_MJPA" );
	public static final VideoFormat FORMAT_MJPB = new VideoFormat( "FORMAT_MJPB" );
	public static final VideoFormat FORMAT_MJPG = new VideoFormat( "FORMAT_MJPG" );
	public static final VideoFormat FORMAT_MP43 = new VideoFormat( "FORMAT_MP43" );
	public static final VideoFormat FORMAT_MP4S = new VideoFormat( "FORMAT_MP4S" );
	public static final VideoFormat FORMAT_MP4V = new VideoFormat( "FORMAT_MP4V" );
	public static final VideoFormat FORMAT_MPEG_XVID = new VideoFormat( "FORMAT_MPEG_XVID" );
	public static final VideoFormat FORMAT_MPEG_PES = new VideoFormat( "FORMAT_MPEG_PES" );
	public static final VideoFormat FORMAT_MPG3 = new VideoFormat( "FORMAT_MPG3" );
	public static final VideoFormat FORMAT_MPG4 = new VideoFormat( "FORMAT_MPG4" );
	public static final VideoFormat FORMAT_MSS1 = new VideoFormat( "FORMAT_MSS1" );
	public static final VideoFormat FORMAT_MSZH = new VideoFormat( "FORMAT_MSZH" );
	public static final VideoFormat FORMAT_MV43 = new VideoFormat( "FORMAT_MV43" );
	public static final VideoFormat FORMAT_MVDV = new VideoFormat( "FORMAT_MVDV" );
	public static final VideoFormat FORMAT_MWV1 = new VideoFormat( "FORMAT_MWV1" );
	public static final VideoFormat FORMAT_PGVV = new VideoFormat( "FORMAT_PGVV" );
	public static final VideoFormat FORMAT_PIM1 = new VideoFormat( "FORMAT_PIM1" );
	public static final VideoFormat FORMAT_PNG = new VideoFormat( "FORMAT_PNG" );
	public static final VideoFormat FORMAT_PNG1 = new VideoFormat( "FORMAT_PNG1" );
	public static final VideoFormat FORMAT_PSIV = new VideoFormat( "FORMAT_PSIV" );
	public static final VideoFormat FORMAT_PVW2 = new VideoFormat( "FORMAT_PVW2" );
	public static final VideoFormat FORMAT_Q10 = new VideoFormat( "FORMAT_Q10" );
	public static final VideoFormat FORMAT_Q11 = new VideoFormat( "FORMAT_Q11" );
	public static final VideoFormat FORMAT_QDRW = new VideoFormat( "FORMAT_QDRW" );
	public static final VideoFormat FORMAT_RAW = new VideoFormat( "FORMAT_RAW" );
	public static final VideoFormat FORMAT_RLE = new VideoFormat( "FORMAT_RLE" );
	public static final VideoFormat FORMAT_RMP4 = new VideoFormat( "FORMAT_RMP4" );
	public static final VideoFormat FORMAT_RPZA = new VideoFormat( "FORMAT_RPZA" );
	public static final VideoFormat FORMAT_RT21 = new VideoFormat( "FORMAT_RT21" );
	public static final VideoFormat FORMAT_SEDG = new VideoFormat( "FORMAT_SEDG" );
	public static final VideoFormat FORMAT_SMC = new VideoFormat( "FORMAT_SMC" );
	public static final VideoFormat FORMAT_SMP4 = new VideoFormat( "FORMAT_SMP4" );
	public static final VideoFormat FORMAT_SNOW = new VideoFormat( "FORMAT_SNOW" );
	public static final VideoFormat FORMAT_SP54 = new VideoFormat( "FORMAT_SP54" );
	public static final VideoFormat FORMAT_SP61 = new VideoFormat( "FORMAT_SP61" );
	public static final VideoFormat FORMAT_SVQ1 = new VideoFormat( "FORMAT_SVQ1" );
	public static final VideoFormat FORMAT_SVQ3 = new VideoFormat( "FORMAT_SVQ3" );
	public static final VideoFormat FORMAT_THEO = new VideoFormat( "FORMAT_THEO" );
	public static final VideoFormat FORMAT_TM20 = new VideoFormat( "FORMAT_TM20" );
	public static final VideoFormat FORMAT_TSCC = new VideoFormat( "FORMAT_TSCC" );
	public static final VideoFormat FORMAT_U263 = new VideoFormat( "FORMAT_U263" );
	public static final VideoFormat FORMAT_UCOD = new VideoFormat( "FORMAT_UCOD" );
	public static final VideoFormat FORMAT_ULTI = new VideoFormat( "FORMAT_ULTI" );
	public static final VideoFormat FORMAT_UMP4 = new VideoFormat( "FORMAT_UMP4" );
	public static final VideoFormat FORMAT_VCR1 = new VideoFormat( "FORMAT_VCR1" );
	public static final VideoFormat FORMAT_VCR2 = new VideoFormat( "FORMAT_VCR2" );
	public static final VideoFormat FORMAT_VDOM = new VideoFormat( "FORMAT_VDOM" );
	public static final VideoFormat FORMAT_VIXL = new VideoFormat( "FORMAT_VIXL" );
	public static final VideoFormat FORMAT_VMNC = new VideoFormat( "FORMAT_VMNC" );
	public static final VideoFormat FORMAT_VP30 = new VideoFormat( "FORMAT_VP30" );
	public static final VideoFormat FORMAT_VP31 = new VideoFormat( "FORMAT_VP31" );
	public static final VideoFormat FORMAT_VP40 = new VideoFormat( "FORMAT_VP40" );
	public static final VideoFormat FORMAT_VP50 = new VideoFormat( "FORMAT_VP50" );
	public static final VideoFormat FORMAT_VP61 = new VideoFormat( "FORMAT_VP61" );
	public static final VideoFormat FORMAT_VP62 = new VideoFormat( "FORMAT_VP62" );
	public static final VideoFormat FORMAT_VSSH = new VideoFormat( "FORMAT_VSSH" );
	public static final VideoFormat FORMAT_VSSV = new VideoFormat( "FORMAT_VSSV" );
	public static final VideoFormat FORMAT_WINX = new VideoFormat( "FORMAT_WINX" );
	public static final VideoFormat FORMAT_WMV1 = new VideoFormat( "FORMAT_WMV1" );
	public static final VideoFormat FORMAT_WMV2 = new VideoFormat( "FORMAT_WMV2" );
	public static final VideoFormat FORMAT_WMV3 = new VideoFormat( "FORMAT_WMV3" );
	public static final VideoFormat FORMAT_WMVA = new VideoFormat( "FORMAT_WMVA" );
	public static final VideoFormat FORMAT_WMVP = new VideoFormat( "FORMAT_WMVP" );
	public static final VideoFormat FORMAT_WNV1 = new VideoFormat( "FORMAT_WNV1" );
	public static final VideoFormat FORMAT_WV1F = new VideoFormat( "FORMAT_WV1F" );
	public static final VideoFormat FORMAT_WVC1 = new VideoFormat( "FORMAT_WVC1" );
	public static final VideoFormat FORMAT_WVP2 = new VideoFormat( "FORMAT_WVP2" );
	public static final VideoFormat FORMAT_YV12 = new VideoFormat( "FORMAT_YV12" );
	public static final VideoFormat FORMAT_YVU9 = new VideoFormat( "FORMAT_YVU9" );
	public static final VideoFormat FORMAT_ZLIB = new VideoFormat( "FORMAT_ZLIB" );
	public static final VideoFormat FORMAT_ZMBV = new VideoFormat( "FORMAT_ZMBV" );
	public static final VideoFormat FORMAT_ZYGO = new VideoFormat( "FORMAT_ZYGO" );

	private static Map<String, VideoFormat> formats = new HashMap<String, VideoFormat>();
	static {
		formats.put("0x00000001", FORMAT_RAW);
		formats.put("0x00000001", FORMAT_RLE);
		formats.put("0x00000002", FORMAT_RLE);
		formats.put("0x00000004", FORMAT_DIVX);
		formats.put("0x10000001", FORMAT_MPEG_PES);
		formats.put("0x10000002", FORMAT_MPEG_PES);
		formats.put("0x10000005", FORMAT_H264);
		formats.put("3IV1", FORMAT_3IV1);
		formats.put("3IV2", FORMAT_3IV2);
		formats.put("8BPS", FORMAT_8BPS);
		formats.put("AASC", FORMAT_AASC);
		formats.put("ACTL", FORMAT_ACTL);
		formats.put("AP41", FORMAT_AP41);
		formats.put("ASV1", FORMAT_ASV1);
		formats.put("ASV2", FORMAT_ASV2);
		formats.put("avc1", FORMAT_H264_AVC);
		formats.put("AVDJ", FORMAT_AVDJ);
		formats.put("AVRn", FORMAT_AVRN);
		formats.put("BLZ0", FORMAT_BLZ0);
		formats.put("CDVC", FORMAT_CDVC);
		formats.put("CFHD", FORMAT_CFHD);
		formats.put("CJPG", FORMAT_CJPG);
		formats.put("CLJR", FORMAT_CLJR);
		formats.put("COL1", FORMAT_COL1);
		formats.put("CRAM", FORMAT_CRAM);
		formats.put("CSCD", FORMAT_CSCD);
		formats.put("cvid", FORMAT_CVID);
		formats.put("cyuv", FORMAT_CYUV);
		formats.put("DIV5", FORMAT_DIV5);
		formats.put("DIVX", FORMAT_DIVX);
		formats.put("dmb1", FORMAT_DMB1);
		formats.put("DUCK", FORMAT_DUCK);
		formats.put("dv50", FORMAT_DV50);
		formats.put("dvc ", FORMAT_DVC);
		formats.put("dvcp", FORMAT_DVCP);
		formats.put("dvsd", FORMAT_DVSD);
		formats.put("DVSD", FORMAT_DVSD);
		formats.put("DX50", FORMAT_DX50);
		formats.put("DXGM", FORMAT_DXGM);
		formats.put("FLV1", FORMAT_FLV1);
		formats.put("FPS1", FORMAT_FPS1);
		formats.put("GXVE", FORMAT_GXVE);
		formats.put("H261", FORMAT_H261);
		formats.put("h263", FORMAT_H263);
		formats.put("h264", FORMAT_H264);
		formats.put("HFYU", FORMAT_HFYU);
		formats.put("I263", FORMAT_I263);
		formats.put("icod", FORMAT_ICOD);
		formats.put("IJPG", FORMAT_IJPG);
		formats.put("IV32", FORMAT_IV32);
		formats.put("IV41", FORMAT_IV41);
		formats.put("IV50", FORMAT_IV50);
		formats.put("jpeg", FORMAT_JPEG);
		formats.put("JPGL", FORMAT_JPGL);
		formats.put("KMVC", FORMAT_KMVC);
		formats.put("LOCO", FORMAT_LOCO);
		formats.put("lsvm", FORMAT_LSVM);
		formats.put("lsvx", FORMAT_LSVX);
		formats.put("LZO1", FORMAT_LZO1);
		formats.put("M263", FORMAT_M263);
		formats.put("M4S2", FORMAT_M4S2);
		formats.put("mjpa", FORMAT_MJPA);
		formats.put("mjpb", FORMAT_MJPB);
		formats.put("MJPG", FORMAT_MJPG);
		formats.put("MP43", FORMAT_MP43);
		formats.put("MP4S", FORMAT_MP4S);
		formats.put("mp4v", FORMAT_MP4V);
		formats.put("MPG3", FORMAT_MPG3);
		formats.put("MPG4", FORMAT_MPG4);
		formats.put("MSS1", FORMAT_MSS1);
		formats.put("MSZH", FORMAT_MSZH);
		formats.put("MV43", FORMAT_MV43);
		formats.put("MVDV", FORMAT_MVDV);
		formats.put("MWV1", FORMAT_MWV1);
		formats.put("PGVV", FORMAT_PGVV);
		formats.put("PIM1", FORMAT_PIM1);
		formats.put("png ", FORMAT_PNG);
		formats.put("PNG1", FORMAT_PNG1);
		formats.put("PSIV", FORMAT_PSIV);
		formats.put("PVW2", FORMAT_PVW2);
		formats.put("Q1.0", FORMAT_Q10);
		formats.put("Q1.1", FORMAT_Q11);
		formats.put("qdrw", FORMAT_QDRW);
		formats.put("rle ", FORMAT_RLE);
		formats.put("RMP4", FORMAT_RMP4);
		formats.put("rpza", FORMAT_RPZA);
		formats.put("RT21", FORMAT_RT21);
		formats.put("s263", FORMAT_H263);
		formats.put("SEDG", FORMAT_SEDG);
		formats.put("smc ", FORMAT_SMC);
		formats.put("SMP4", FORMAT_SMP4);
		formats.put("SNOW", FORMAT_SNOW);
		formats.put("SP54", FORMAT_SP54);
		formats.put("SP61", FORMAT_SP61);
		formats.put("SVQ1", FORMAT_SVQ1);
		formats.put("SVQ3", FORMAT_SVQ3);
		formats.put("theo", FORMAT_THEO);
		formats.put("TM20", FORMAT_TM20);
		formats.put("tscc", FORMAT_TSCC);
		formats.put("U263", FORMAT_U263);
		formats.put("UCOD", FORMAT_UCOD);
		formats.put("ULTI", FORMAT_ULTI);
		formats.put("UMP4", FORMAT_UMP4);
		formats.put("VCR1", FORMAT_VCR1);
		formats.put("VCR2", FORMAT_VCR2);
		formats.put("VDOM", FORMAT_VDOM);
		formats.put("VIXL", FORMAT_VIXL);
		formats.put("VMnc", FORMAT_VMNC);
		formats.put("VP30", FORMAT_VP30);
		formats.put("VP31", FORMAT_VP31);
		formats.put("VP40", FORMAT_VP40);
		formats.put("VP50", FORMAT_VP50);
		formats.put("VP61", FORMAT_VP61);
		formats.put("VP62", FORMAT_VP62);
		formats.put("VSSH", FORMAT_VSSH);
		formats.put("VSSV", FORMAT_VSSV);
		formats.put("WINX", FORMAT_WINX);
		formats.put("WMV1", FORMAT_WMV1);
		formats.put("WMV2", FORMAT_WMV2);
		formats.put("WMV3", FORMAT_WMV3);
		formats.put("WMVA", FORMAT_WMVA);
		formats.put("WMVP", FORMAT_WMVP);
		formats.put("WNV1", FORMAT_WNV1);
		formats.put("WV1F", FORMAT_WV1F);
		formats.put("WVC1", FORMAT_WVC1);
		formats.put("WVP2", FORMAT_WVP2);
		formats.put("XVID", FORMAT_MPEG_XVID);
		formats.put("YV12", FORMAT_YV12);
		formats.put("YVU9", FORMAT_YVU9);
		formats.put("ZLIB", FORMAT_ZLIB);
		formats.put("ZMBV", FORMAT_ZMBV);
		formats.put("ZyGo", FORMAT_ZYGO);
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
		this.formatId = id;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (obj instanceof VideoFormat) {
			return ((VideoFormat) obj).formatId.equals(formatId);
		}
		return false;
	}

	/**
	 * Return the format id.
	 * 
	 * @return format identifier.
	 */
	public String getFormatID() {
		return formatId;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return formatId.hashCode();
	}

}
