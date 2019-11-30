package org.isolution.babel;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleDictionaryTest {

    private Dictionary dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new SimpleDictionary();
    }

    @Test
    void testPut() {
        dictionary.put(English.Fish, Indonesian.Ikan)
                .put(English.Fish, Deutsch.Food);
        assertThat(dictionary.translate(English.Fish, Indonesian.class))
                .isEqualTo(Indonesian.Ikan);
    }

    @Test
    void fail_when_there_is_conflicting_translation() {
        dictionary.put(English.Fish, Indonesian.Ikan);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> dictionary.put(Indonesian.Ikan, English.Chicken));
    }

}