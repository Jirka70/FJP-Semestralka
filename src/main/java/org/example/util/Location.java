package org.example.util;

import java.util.Objects;

public class Location {
    public final int mLine;
    public final int mCharPositionInLine;

    public Location(int line, int charPositionInLine) {
        mLine = line;
        mCharPositionInLine = charPositionInLine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Location location)) {
            return false;
        }

        return mLine == location.mLine && mCharPositionInLine == location.mCharPositionInLine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mLine, mCharPositionInLine);
    }

    @Override
    public String toString() {
        return mLine + ":" + mCharPositionInLine;
    }
}
