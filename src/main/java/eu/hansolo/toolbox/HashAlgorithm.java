package eu.hansolo.toolbox;

import eu.hansolo.toolbox.versioning.Api;
import eu.hansolo.toolbox.versioning.OutputFormat;

import java.util.Arrays;
import java.util.List;

import static eu.hansolo.toolbox.Constants.COLON;
import static eu.hansolo.toolbox.Constants.COMMA;
import static eu.hansolo.toolbox.Constants.COMMA_NEW_LINE;
import static eu.hansolo.toolbox.Constants.CURLY_BRACKET_CLOSE;
import static eu.hansolo.toolbox.Constants.CURLY_BRACKET_OPEN;
import static eu.hansolo.toolbox.Constants.INDENTED_QUOTES;
import static eu.hansolo.toolbox.Constants.NEW_LINE;
import static eu.hansolo.toolbox.Constants.QUOTES;


public enum HashAlgorithm implements Api {
    MD5("MSD5", "md5"),
    SHA1("SHA1", "sha1"),
    SHA256("SHA256", "sha256"),
    SHA224("SHA224", "sha224"),
    SHA384("SHA384", "sha384"),
    SHA512("SHA512", "sha512"),
    SHA3_256("SHA-3 256", "sha3_256"),
    NONE("-", ""),
    NOT_FOUND("", "");

    private final String uiString;
    private final String apiString;


    HashAlgorithm(final String uiString, final String apiString) {
        this.uiString  = uiString;
        this.apiString = apiString;
    }


    @Override public String getUiString() { return uiString; }

    @Override public String getApiString() { return apiString; }

    @Override public Api getDefault() { return HashAlgorithm.NONE; }

    @Override public Api getNotFound() { return HashAlgorithm.NOT_FOUND; }

    @Override public Api[] getAll() { return values(); }

    @Override public String toString(final OutputFormat outputFormat) {
        StringBuilder msgBuilder = new StringBuilder();
        switch(outputFormat) {
            case FULL, REDUCED, REDUCED_ENRICHED -> {
                msgBuilder.append(CURLY_BRACKET_OPEN).append(NEW_LINE)
                          .append(INDENTED_QUOTES).append("name").append(QUOTES).append(COLON).append(QUOTES).append(name()).append(QUOTES).append(COMMA_NEW_LINE)
                          .append(INDENTED_QUOTES).append("ui_string").append(QUOTES).append(COLON).append(QUOTES).append(uiString).append(QUOTES).append(COMMA_NEW_LINE)
                          .append(INDENTED_QUOTES).append("api_string").append(QUOTES).append(COLON).append(QUOTES).append(apiString).append(QUOTES).append(NEW_LINE)
                          .append(CURLY_BRACKET_CLOSE);
            }
            case FULL_COMPRESSED, REDUCED_COMPRESSED, REDUCED_ENRICHED_COMPRESSED -> {
                msgBuilder.append(CURLY_BRACKET_OPEN)
                          .append(QUOTES).append("name").append(QUOTES).append(COLON).append(QUOTES).append(name()).append(QUOTES).append(COMMA)
                          .append(QUOTES).append("ui_string").append(QUOTES).append(COLON).append(QUOTES).append(uiString).append(QUOTES).append(COMMA)
                          .append(QUOTES).append("api_string").append(QUOTES).append(COLON).append(QUOTES).append(apiString).append(QUOTES)
                          .append(CURLY_BRACKET_CLOSE);
            }
        }
        return msgBuilder.toString();
    }

    @Override public String toString() { return toString(OutputFormat.FULL_COMPRESSED); }

    public static HashAlgorithm fromText(final String text) {
        if (null == text) { return NOT_FOUND; }
        switch(text) {
            case "md5":
            case "MD5":
            case "md-5":
            case "md_5":
            case "MD-5":
            case "MD_5":
                return MD5;
            case "sha1":
            case "SHA1":
            case "sha-1":
            case "SHA-1":
            case "sha_1":
            case "SHA_1":
                return SHA1;
            case "sha256":
            case "SHA256":
            case "sha_256":
            case "SHA_256":
            case "sha-256":
            case "SHA-256":
                return SHA256;
            case "sha224":
            case "SHA224":
            case "sha_224":
            case "SHA_224":
            case "sha-224":
            case "SHA-224":
                return SHA224;
            case "sha384":
            case "SHA384":
            case "sha_384":
            case "SHA_384":
            case "sha-384":
            case "SHA-384":
                return SHA384;
            case "sha512":
            case "SHA512":
            case "sha_512":
            case "SHA_512":
            case "sha-512":
            case "SHA-512":
                return SHA512;
            case "sha3_256":
            case "SHA3_256":
            case "sha-3-256":
            case "SHA-3-256":
            case "sha_3_256":
            case "SHA_3_256":
                return SHA3_256;
            default:
                return NOT_FOUND;
        }
    }

    public static List<HashAlgorithm> getAsList() { return Arrays.asList(values()); }
}
