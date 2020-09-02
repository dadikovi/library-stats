package io.github.dadikovi.domain;

public class StatParam {
    private String key;
    private String value;

    public StatParam( String key, String value ) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
