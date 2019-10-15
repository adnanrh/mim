import java.util.Objects;

/**
 * Class to represent a selection of a range of characters in a string
 * by start and end indices.
 */
public class Selection {

    private int startIndex; // the index of the first character in the selection
    private int endIndex; // the index of the last character in the selection

    public Selection(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Selection selection = (Selection) o;
        return startIndex == selection.startIndex &&
                endIndex == selection.endIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startIndex, endIndex);
    }
}
