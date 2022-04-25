package com.rebirth.veterinaryexample.app.domain.enums;

public enum BreedSize {
    Extra_Large("Extra Large"),
    Large("Large"),
    Medium("Medium"),
    Small("Small"),
    Tiny("Tiny");

    private final String value;

    private BreedSize(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
