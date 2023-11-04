package seedu.address.model.client;

/**
 * Represents a document with a URL, ensuring it is a valid URL.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class Document {

    public static final String MESSAGE_CONSTRAINTS = "Invalid URL format";

    // Regular expression to match a valid URL
    public static final String VALIDATION_REGEX =
            "(https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z0-9\\/]{2,}(\\.[a-zA-Z0-9\\/]{2,"
                    + "})(\\.[a-zA-Z0-9\\/]{2,})?";

    private final String url;

    /**
     * Constructs a {@code Document} with the specified URL.
     *
     * @param url The URL of the document.
     */
    public Document(String url) {
        this.url = url;
    }

    /**
     * Returns true if a given string is a valid URL.
     */
    public static boolean isValidUrl(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return url;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Document)) {
            return false;
        }

        Document otherDocument = (Document) other;
        return url.equals(otherDocument.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
