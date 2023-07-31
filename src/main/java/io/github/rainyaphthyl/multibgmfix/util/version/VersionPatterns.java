package io.github.rainyaphthyl.multibgmfix.util.version;

import java.util.regex.Pattern;

public class VersionPatterns {
    public static final Pattern PATTERN_ALPHA_NUM = Pattern.compile("^[A-Za-z-][0-9A-Za-z-]*$");
    public static final Pattern PATTERN_PURE_NUM = Pattern.compile("^0|([1-9][0-9]*)$");
    public static final Pattern PATTERN_FULL = Pattern.compile("^([0-9.]+)-([0-9A-Za-z-.]+)\\+([0-9A-Za-z-.]+)$");
    public static final Pattern PATTERN_WITH_PRE = Pattern.compile("^([0-9.]+)-([0-9A-Za-z-.]+)$");
    public static final Pattern PATTERN_WITH_BUILD = Pattern.compile("^([0-9.]+)\\+([0-9A-Za-z-.]+)$");
    public static final Pattern PATTERN_SIMPLE = Pattern.compile("^[0-9.]+$");
}
