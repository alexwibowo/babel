package org.isolution.babel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SimpleDictionaryTest {

    private Dictionary dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new SimpleDictionary();
    }

    @Test
    void simple_translation() {
        dictionary.put(English.Fish, Indonesian.Ikan)
                .put(English.Fish, Deutsch.Fisch)
                .put(English.Cow, Indonesian.Sapi)
                .put(English.Cow, Deutsch.Kuh)
                .put(English.Chicken, Indonesian.Ayam)
                .put(English.Chicken, Deutsch.Hähnchen);

        assertThat(dictionary.translate(English.Fish, Indonesian.class))
                .isEqualTo(Indonesian.Ikan);
        assertThat(dictionary.translate(Indonesian.Ikan, English.class))
                .isEqualTo(English.Fish);
    }

    @Test
    void fail_when_there_is_conflicting_translation() {
        dictionary.put(English.Fish, Indonesian.Ikan);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> dictionary.put(Indonesian.Ikan, English.Chicken));
    }

}