package io.github.rainyaphthyl.multibgmfix.util.version;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.ParametersAreNullableByDefault;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class AppendingVersion extends AbstractList<Comparable<?>> implements Comparable<AppendingVersion> {
    private final Comparable<?>[] identifiers;
    private final AtomicReference<String> text = new AtomicReference<>(null);

    @ParametersAreNullableByDefault
    private AppendingVersion(Comparable<?>... identifiers) {
        if (identifiers == null) {
            this.identifiers = new Comparable<?>[0];
        } else {
            this.identifiers = new Comparable<?>[identifiers.length];
            System.arraycopy(identifiers, 0, this.identifiers, 0, identifiers.length);
        }
    }

    public static AppendingVersion getAppendix(String label) {
        if (label == null) {
            return null;
        }
        String[] subLabels = label.split("\\.");
        Comparable<?>[] identifiers = new Comparable[subLabels.length];
        try {
            for (int i = 0; i < subLabels.length; ++i) {
                if (VersionPatterns.PATTERN_ALPHA_NUM.matcher(subLabels[i]).matches()) {
                    identifiers[i] = subLabels[i];
                } else if (VersionPatterns.PATTERN_PURE_NUM.matcher(subLabels[i]).matches()) {
                    identifiers[i] = Integer.valueOf(subLabels[i]);
                } else {
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return new AppendingVersion(identifiers);
    }

    /**
     * {@code null} is higher
     */
    public static int compare_appendix(AppendingVersion v1, AppendingVersion v2) {
        if (v1 == v2) {
            return 0;
        } else if (v1 == null) {
            return 1;
        } else if (v2 == null) {
            return -1;
        } else {
            return v1.compareTo(v2);
        }
    }

    @ParametersAreNonnullByDefault
    public static int compare_section(Comparable<?> s1, Comparable<?> s2) {
        boolean pure1 = s1 instanceof Integer;
        boolean pure2 = s2 instanceof Integer;
        if (pure1 == pure2) {
            if (pure1) {
                return ((Integer) s1).compareTo((Integer) s2);
            } else {
                return String.valueOf(s1).compareTo(String.valueOf(s2));
            }
        } else {
            return pure1 ? -1 : 1;
        }
    }

    @Override
    public int size() {
        return identifiers.length;
    }

    @Override
    public String toString() {
        synchronized (text) {
            if (text.get() == null) {
                StringBuilder builder = new StringBuilder();
                if (0 < identifiers.length) {
                    builder.append(identifiers[0]);
                }
                for (int i = 1; i < identifiers.length; ++i) {
                    builder.append('.').append(identifiers[i]);
                }
                text.set(builder.toString());
            }
        }
        return text.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    public int compareTo(AppendingVersion that) {
        if (this == that) {
            return 0;
        }
        int minLength = Math.min(identifiers.length, that.identifiers.length);
        for (int i = 0; i < minLength; ++i) {
            int flag = compare_section(identifiers[i], that.identifiers[i]);
            if (flag != 0) {
                return flag;
            }
        }
        return Integer.compare(identifiers.length, that.identifiers.length);
    }

    @Override
    public Comparable<?> get(int index) {
        if (index < identifiers.length && index >= 0) {
            return identifiers[index];
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        } else if (that instanceof AppendingVersion) {
            AppendingVersion version = (AppendingVersion) that;
            return Arrays.equals(identifiers, version.identifiers);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(identifiers);
    }
}
