/*
 * Copyright 2008, David Karnok 
 * The file is part of the Open Imperium Galactica project.
 * 
 * The code should be distributed under the LGPL license.
 * See http://www.gnu.org/licenses/lgpl.html for details.
 */
package hu.openig.gfx;

import hu.openig.utils.IOUtils;
import hu.openig.utils.LRUHashMap;
import hu.openig.utils.PCXImage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Text graphics drawer and container class.
 * @author karnokd
 */
public class TextGFX {
	/**
	 * Record to store a particularly sized character series's width and height.
	 * @author karnokd
	 */
	private static class SizedCharImages {
		/** The uniform character width. */
		public int width;
		/** The uniform character height. */
		public int height;
		/** The map of characters to its images. */
		public final Map<Character, BufferedImage> chars = new HashMap<Character, BufferedImage>();
	}
	/** The entire backing image. */
	private PCXImage charImage;
	/** Text height constant helper. */
	public static final int SIZE_5 = 5;
	/** Text height constant helper. */
	public static final int SIZE_7 = 7;
	/** Text height constant helper. */
	public static final int SIZE_10 = 10;
	/** Text height constant helper. */
	public static final int SIZE_14 = 14;
	/** Predefined color constant. */
	public static final int YELLOW = 0xFCFC58;
	/** Predefined color constant. */
	public static final int GREEN = 0x6CB068;
	/** Predefined color constant. */
	public static final int GRAY = 0x949494;
	/** Predefined color constant. */
	public static final int DARK_GRAY = 0x6C6C6C;
	/** Predefined color constant. */
	public static final int RED = 0xFC2828;
	/** Predefined color constant. */
	public static final int DARK_GREEN = 0x009800;
	/** Predefined color constant. */
	public static final int ORANGE = 0xFCB000;
	/** Predefined color constant. */
	public static final int WHITE = 0xFCFCFC;
	/** Predefined color constant. */
	public static final int CYAN = 0x00FCFC;
	/** Predefined color constant. */
	public static final int PURPLE = 0xFC00FC;
	/** Predefined color constant. */
	public static final int LIGHT_GREEN = 0xB0FC6C;
	/** Predefined color constant. */
	public static final int BLUE = 0x3C3CFC;
	/** Predefined color constant. */
	public static final int LIGHT_BLUE = 0x94A4FC;
	/** Predefined color constant for a race. */
	int GALACTIC_EMPIRE = ORANGE;
	/** Predefined color constant for a race. */
	int GARTHOG_REPUBLIC = RED;
	/** Predefined color constant for a race. */
	int MORGATH_EMPIRE = WHITE;
	/** Predefined color constant for a race. */
	int YCHOM_EMPIRE = WHITE;
	/** Predefined color constant for a race. */
	int DRIBS_EMPIRE = PURPLE;
	/** Predefined color constant for a race. */
	int SULLEP_EMPIRE = YELLOW;
	/** Predefined color constant for a race. */
	int DARGSLAN_KINGDOM= DARK_GREEN;
	/** Predefined color constant for a race. */
	int ECALEP_REPUBLIC = LIGHT_GREEN;
	/** Predefined color constant for a race. */
	int FREE_TRADERS = BLUE;
	/** Predefined color constant for a race. */
	int FREE_NATIONS_SOCIETY = LIGHT_BLUE;
	
	/** The cache for color-remaped charImages. */
	private Map<Integer, Map<Integer, SizedCharImages>> coloredCharImages = LRUHashMap.create(32);
	/**
	 * Constructor. Initializes the internal tables by processing the given file.
	 * @param charsetFile the character set .PCX file
	 */
	public TextGFX(String charsetFile) {
		charImage = new PCXImage(IOUtils.load(charsetFile));
		// map some colors
		split(YELLOW);
		split(GREEN);
		split(GRAY);
		split(RED);
	}
	/**
	 * Split the entire images into character sizes and characters
	 * @return the generated sized map of the character images
	 */
	private Map<Integer, SizedCharImages> split(int color) {
		// first sequence for siz
		String[] lineCharacters = {
				/* Size: 7 */
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmn",
				"opqrstuvwxyz?!()'\"+-:;.,1234567890%& /\u00DF",
				"\u00C4\u00D6\u00DC\u00E4\u00F6\u00FC\u00DF\u00EA\u00E9\u00E8\u00E0\u00C9\u00C1\u00C7\u00E7\u00F4\u00FB\u00F9\u00F2\u00EC\u00E1\u00F3\u00F1\u00D1\u00A1\u00BF\u00FA\u00ED\u00CD\u00D3\u00F3\u0150\u0151\u0170\u0171\u00DA",
				/* Size: 10 */
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmn",
				"opqrstuvwxyz?!()'\"+-:;.,1234567890%& /\u00DF",
				"\u00C4\u00D6\u00DC\u00E4\u00F6\u00FC\u00DF\u00EA\u00E9\u00E8\u00E0\u00C9\u00C1\u00C7\u00E7\u00F4\u00FB\u00F9\u00F2\u00EC\u00E1\u00F3\u00F1\u00D1\u00A1\u00BF\u00FA\u00ED\u00CD\u00D3\u00F3\u0150\u0151\u0170\u0171\u00DA",
				/* Size: 14 */
				"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
				"abcdefghijklmnopqrstuvwxyz",
				"?!()'\"+-:;.,1234567890%& /",
				"\u00DF \u00C4\u00D6\u00DC\u00E4\u00F6\u00FC\u00DF\u00EA\u00E9\u00E8\u00E0\u00C9\u00C1\u00C7\u00E7\u00F4\u00FB\u00F9\u00F2\u00EC\u00E1\u00F3\u00F1\u00D1",
				"\u00A1\u00BF\u00FA\u00ED\u00CD\u00D3\u00F3\u0150\u0151\u0170\u0171\u00DA",
				/* Size: 5 */
				"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz",
				"?!()'\"+-:;.,1234567890%& /\u00DF \u00C4\u00D6\u00DC\u00E4\u00F6\u00FC\u00DF\u00EA\u00E9\u00E8\u00E0\u00C9\u00C1\u00C7\u00E7\u00F4\u00FB\u00F9\u00F2\u00EC\u00E1\u00F3\u00F1\u00D1",
				"\u00A1\u00BF\u00FA\u00ED\u00CD\u00D3\u00F3\u0150\u0151\u0170\u0171\u00DA"
		};
		int[] height = {
				7, 7, 7, 
				10, 10, 10, 
				14, 14, 14, 14, 14,
				5, 5, 5
		};
		int[] width = {
				5, 5, 5, 
				7, 7, 7, 
				12, 12, 12, 12, 12,
				5, 5, 5
		};
		int[] spacingX = {
				3, 3, 3, 
				1, 1, 1, 
				0, 0, 0, 0, 0,
				1, 1, 1
		};
		int[] spacingY = {
				1, 1, 2, 
				0, 0, 1, 
				0, 0, 0, 0, 1,
				1, 1, 1
		};
		Map<Integer, SizedCharImages> charMap = new HashMap<Integer, SizedCharImages>();
		coloredCharImages.put(color, charMap);
		BufferedImage workImage = charImage.toBufferedImage(-2, createPaletteFor(color));
		int y = 0;
		for (int j = 0; j < lineCharacters.length; j++) {
			SizedCharImages charToImg = charMap.get(height[j]);
			if (charToImg == null) {
				charToImg = new SizedCharImages();
				charToImg.width = width[j];
				charToImg.height = height[j];
				charMap.put(height[j], charToImg);
			}
			int x = 0;
			String s = lineCharacters[j];
			for (int i = 0; i < s.length(); i++) {
				BufferedImage ci = workImage.getSubimage(x, y, width[j], height[j]);
				charToImg.chars.put(lineCharacters[j].charAt(i), ci);
				
				x += width[j] + spacingX[j];
			}
			y += height[j] + spacingY[j];
		}
		return charMap;
	}
	/**
	 * Create a palette using the supplied color.
	 * @param color
	 * @return
	 */
	private byte[] createPaletteFor(int color) {
		byte[] result = new byte[768];
		// full color
		result[3] = (byte)Math.min(((color & 0xFF0000) >> 16) * 1.3f, 0xFC);
		result[4] = (byte)Math.min(((color & 0xFF00) >> 8) * 1.3f, 0xFC);
		result[5] = (byte)Math.min(((color & 0xFF) >> 0) * 1.3f, 0xFC);
		// darker color
		result[6] = (byte)(((color & 0xFF0000) >> 16) * 1f);
		result[7] = (byte)(((color & 0xFF00) >> 8) * 1f);
		result[8] = (byte)(((color & 0xFF) >> 0) * 1f);
		// darkest color
		result[9] = (byte)(((color & 0xFF0000) >> 16) * 0.6f);
		result[10] = (byte)(((color & 0xFF00) >> 8) * 0.6f);
		result[11] = (byte)(((color & 0xFF) >> 0) * 0.6f);
		return result;
	}
	/**
	 * Draw the given text at the given location on the supplied graphics object.
	 * @param g the graphics object to draw to
	 * @param x the starting X coordinate
	 * @param y the starting Y coordinate
	 * @param size the font size
	 * @param color the color to use, alpha values are ignored at this level. If you need alpha, set the graphics object appropriately.
	 * @param text the text to print
	 */
	public void paintTo(Graphics2D g, int x, int y, int size, int color, String text) {
		Map<Integer, SizedCharImages> charMap = coloredCharImages.get(color);
		if (charMap == null) {
			charMap = split(color);
		}
		SizedCharImages charToImage = charMap.get(size);
		if (charToImage != null) {
			for (int i = 0; i < text.length(); i++) {
				BufferedImage ci = charToImage.chars.get(text.charAt(i));
				if (ci != null) {
					g.drawImage(ci, x, y, null);
				}
				x += charToImage.width + 2;
			}
		}
	}
}
