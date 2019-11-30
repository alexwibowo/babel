package org.isolution.babel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimpleDictionary implements Dictionary {
    private Map<Class, Map<Class, Map>> universe = new HashMap<>();

    @Override
    public <T1, T2> Dictionary put(final T1 key1,
                                   final T2 key2) {
        final Map<Class, Map> dictionaryForKey1 = dictionariesFor(key1.getClass());
        final Map<T1, T2> fromKey1ToKey2 = dictionaryFromKey1ToKey2(dictionaryForKey1, key2.getClass());
        Object reverseTranslation = translate(key2, key1.getClass());
        if (reverseTranslation != null && !Objects.equals(key1, reverseTranslation)) {
            throw new IllegalArgumentException("Conflicting translation");
        }
        fromKey1ToKey2.put(key1, key2);

        return this;
    }

    @NotNull
    private Map dictionaryFromKey1ToKey2(final Map<Class, Map> dictionaryForKey1,
                                         final Class<?> class2) {
        return dictionaryForKey1.computeIfAbsent(class2, aClass -> new HashMap<>());
    }

    @NotNull
    private Map<Class, Map> dictionariesFor(Class<?> klazz) {
        return universe.computeIfAbsent(klazz, _ignored -> new HashMap<>());
    }

    @Override
    public <T1, T2> T2 translate(final T1 from,
                                 final Class<T2> as) {
        final Map<Class, Map> dictionaryForKey1 = dictionariesFor(from.getClass());
        final Map<T1, T2> map = dictionaryFromKey1ToKey2(dictionaryForKey1, as);
        return map.get(from);
    }
}
