package io.github.dadikovi.domain;

// TODO: we could use lombok here.
public class AggregationResult {
    private String key;
    private String value;

    public AggregationResult( String key, String value ) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
