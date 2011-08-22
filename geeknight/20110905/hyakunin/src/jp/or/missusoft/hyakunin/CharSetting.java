package jp.or.missusoft.hyakunin;

public class CharSetting {
    /**
     * ï∂éö
     */
    public final String charcter;

    /**
     * âÒì]äpìx
     */
    public final float angle;

    /**
     * xÇÃç∑ï™<br />
     * Paint#getFontSpacing() * xÇ™ë´Ç≥ÇÍÇÈ<br />
     * -0.5fÇ™ê›íËÇ≥ÇÍÇΩèÍçáÅA1/2ï∂éöï™ç∂Ç…Ç∏ÇÍÇÈ
     */
    public final float x;

    /**
     * xÇÃç∑ï™<br />
     * Paint#getFontSpacing() * yÇ™ë´Ç≥ÇÍÇÈ<br />
     * -0.5fÇ™ê›íËÇ≥ÇÍÇΩèÍçáÅA1/2ï∂éöï™è„Ç…Ç∏ÇÍÇÈ
     */
    public final float y;

    public CharSetting(String charcter, float angle, float x, float y) {
        super();
        this.charcter = charcter;
        this.angle = angle;
        this.x = x;
        this.y = y;
    }

    public static final CharSetting[] settings = {
            new CharSetting("ÅA", 0.0f, 0.7f, -0.6f), new CharSetting("ÅB", 0.0f, 0.7f, -0.6f),
            new CharSetting("Åu", 90.0f, -1.0f, -0.3f), new CharSetting("Åv", 90.0f, -1.0f, 0.0f),
            new CharSetting("Çü", 0.0f, 0.1f, -0.1f), new CharSetting("Ç°", 0.0f, 0.1f, -0.1f),
            new CharSetting("Ç£", 0.0f, 0.1f, -0.1f), new CharSetting("Ç•", 0.0f, 0.1f, -0.1f),
            new CharSetting("Çß", 0.0f, 0.1f, -0.1f), new CharSetting("Ç¡", 0.0f, 0.1f, -0.1f),
            new CharSetting("Ç·", 0.0f, 0.1f, -0.1f), new CharSetting("Ç„", 0.0f, 0.1f, -0.1f),
            new CharSetting("ÇÂ", 0.0f, 0.1f, -0.1f), new CharSetting("É@", 0.0f, 0.1f, -0.1f),
            new CharSetting("ÉB", 0.0f, 0.1f, -0.1f), new CharSetting("ÉD", 0.0f, 0.1f, -0.1f),
            new CharSetting("ÉF", 0.0f, 0.1f, -0.1f), new CharSetting("ÉH", 0.0f, 0.1f, -0.1f),
            new CharSetting("Éb", 0.0f, 0.1f, -0.1f), new CharSetting("ÉÉ", 0.0f, 0.1f, -0.1f),
            new CharSetting("ÉÖ", 0.0f, 0.1f, -0.1f), new CharSetting("Éá", 0.0f, 0.1f, -0.1f),
            new CharSetting("Å[", -90.0f, 0.0f, 0.9f), new CharSetting("a", 90.0f, 0.0f, -0.1f),
            new CharSetting("b", 90.0f, 0.0f, -0.1f), new CharSetting("c", 90.0f, 0.0f, -0.1f),
            new CharSetting("d", 90.0f, 0.0f, -0.1f), new CharSetting("e", 90.0f, 0.0f, -0.1f),
            new CharSetting("f", 90.0f, 0.0f, -0.1f), new CharSetting("g", 90.0f, 0.0f, -0.1f),
            new CharSetting("h", 90.0f, 0.0f, -0.1f), new CharSetting("i", 90.0f, 0.0f, -0.1f),
            new CharSetting("j", 90.0f, 0.0f, -0.1f), new CharSetting("k", 90.0f, 0.0f, -0.1f),
            new CharSetting("l", 90.0f, 0.0f, -0.1f), new CharSetting("m", 90.0f, 0.0f, -0.1f),
            new CharSetting("n", 90.0f, 0.0f, -0.1f), new CharSetting("o", 90.0f, 0.0f, -0.1f),
            new CharSetting("p", 90.0f, 0.0f, -0.1f), new CharSetting("q", 90.0f, 0.0f, -0.1f),
            new CharSetting("r", 90.0f, 0.0f, -0.1f), new CharSetting("s", 90.0f, 0.0f, -0.1f),
            new CharSetting("t", 90.0f, 0.0f, -0.1f), new CharSetting("u", 90.0f, 0.0f, -0.1f),
            new CharSetting("v", 90.0f, 0.0f, -0.1f), new CharSetting("w", 90.0f, 0.0f, -0.1f),
            new CharSetting("x", 90.0f, 0.0f, -0.1f), new CharSetting("y", 90.0f, 0.0f, -0.1f),
            new CharSetting("z", 90.0f, 0.0f, -0.1f), new CharSetting("A", 90.0f, 0.0f, -0.1f),
            new CharSetting("B", 90.0f, 0.0f, -0.1f), new CharSetting("C", 90.0f, 0.0f, -0.1f),
            new CharSetting("D", 90.0f, 0.0f, -0.1f), new CharSetting("E", 90.0f, 0.0f, -0.1f),
            new CharSetting("F", 90.0f, 0.0f, -0.1f), new CharSetting("G", 90.0f, 0.0f, -0.1f),
            new CharSetting("H", 90.0f, 0.0f, -0.1f), new CharSetting("I", 90.0f, 0.0f, -0.1f),
            new CharSetting("J", 90.0f, 0.0f, -0.1f), new CharSetting("K", 90.0f, 0.0f, -0.1f),
            new CharSetting("L", 90.0f, 0.0f, -0.1f), new CharSetting("M", 90.0f, 0.0f, -0.1f),
            new CharSetting("N", 90.0f, 0.0f, -0.1f), new CharSetting("O", 90.0f, 0.0f, -0.1f),
            new CharSetting("P", 90.0f, 0.0f, -0.1f), new CharSetting("Q", 90.0f, 0.0f, -0.1f),
            new CharSetting("R", 90.0f, 0.0f, -0.1f), new CharSetting("S", 90.0f, 0.0f, -0.1f),
            new CharSetting("T", 90.0f, 0.0f, -0.1f), new CharSetting("U", 90.0f, 0.0f, -0.1f),
            new CharSetting("V", 90.0f, 0.0f, -0.1f), new CharSetting("W", 90.0f, 0.0f, -0.1f),
            new CharSetting("X", 90.0f, 0.0f, -0.1f), new CharSetting("Y", 90.0f, 0.0f, -0.1f),
            new CharSetting("Z", 90.0f, 0.0f, -0.1f), new CharSetting("ÇÅ", 90.0f, 0.0f, -0.1f),
            new CharSetting("ÇÇ", 90.0f, 0.0f, -0.1f), new CharSetting("ÇÉ", 90.0f, 0.0f, -0.1f),
            new CharSetting("ÇÑ", 90.0f, 0.0f, -0.1f), new CharSetting("ÇÖ", 90.0f, 0.0f, -0.1f),
            new CharSetting("ÇÜ", 90.0f, 0.0f, -0.1f), new CharSetting("Çá", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çà", 90.0f, 0.0f, -0.1f), new CharSetting("Çâ", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çä", 90.0f, 0.0f, -0.1f), new CharSetting("Çã", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çå", 90.0f, 0.0f, -0.1f), new CharSetting("Çç", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çé", 90.0f, 0.0f, -0.1f), new CharSetting("Çè", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çê", 90.0f, 0.0f, -0.1f), new CharSetting("Çë", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çí", 90.0f, 0.0f, -0.1f), new CharSetting("Çì", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çî", 90.0f, 0.0f, -0.1f), new CharSetting("Çï", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çñ", 90.0f, 0.0f, -0.1f), new CharSetting("Çó", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çò", 90.0f, 0.0f, -0.1f), new CharSetting("Çô", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çö", 90.0f, 0.0f, -0.1f), new CharSetting("Ç`", 90.0f, 0.0f, -0.1f),
            new CharSetting("Ça", 90.0f, 0.0f, -0.1f), new CharSetting("Çb", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çc", 90.0f, 0.0f, -0.1f), new CharSetting("Çd", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çe", 90.0f, 0.0f, -0.1f), new CharSetting("Çf", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çg", 90.0f, 0.0f, -0.1f), new CharSetting("Çh", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çi", 90.0f, 0.0f, -0.1f), new CharSetting("Çj", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çk", 90.0f, 0.0f, -0.1f), new CharSetting("Çl", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çm", 90.0f, 0.0f, -0.1f), new CharSetting("Çn", 90.0f, 0.0f, -0.1f),
            new CharSetting("Ço", 90.0f, 0.0f, -0.1f), new CharSetting("Çp", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çq", 90.0f, 0.0f, -0.1f), new CharSetting("Çr", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çs", 90.0f, 0.0f, -0.1f), new CharSetting("Çt", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çu", 90.0f, 0.0f, -0.1f), new CharSetting("Çv", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çw", 90.0f, 0.0f, -0.1f), new CharSetting("Çx", 90.0f, 0.0f, -0.1f),
            new CharSetting("Çy", 90.0f, 0.0f, -0.1f), new CharSetting("ÅF", 90.0f, 0.0f, -0.1f),
            new CharSetting("ÅG", 90.0f, 0.0f, -0.1f), new CharSetting("Å^", 90.0f, 0.0f, -0.1f),
            new CharSetting("", 90.0f, 0.0f, -0.1f), new CharSetting(":", 90.0f, 0.0f, -0.1f),
            new CharSetting(";", 90.0f, 0.0f, -0.1f), new CharSetting("/", 90.0f, 0.0f, -0.1f),
            new CharSetting(".", 90.0f, 0.0f, -0.1f),
    };

    public static CharSetting getSetting(String character) {
        for (int i = 0; i < settings.length; i++) {
            if (settings[i].charcter.equals(character)) {
                return settings[i];
            }
        }
        return null;
    }

    private static final String[] PUNCTUATION_MARK = {
            "ÅA", "ÅB", "Åu", "Åv"
    };

    public static boolean isPunctuationMark(String s) {
        for (String functuantionMark : PUNCTUATION_MARK) {
            if (functuantionMark.equals(s)) {
                return true;
            }
        }
        return false;
    }

}