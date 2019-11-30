package org.isolution.babel;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SimpleDictionary implements Dictionary {
    private Map<Class, Map<Class, Map>> universe = new HashMap<>();

    @Override
    public <T1, T2> Dictionary put(final T1 key1,
                                   final T2 key2) {
        doPut(key1, key2);
        doPut(key2, key1);

        return this;
    }

    public <T1, T2> Dictionary doPut(final T1 key1,
                                     final T2 key2) {
        final Map<Class, Map> dictionaryForKey1 = dictionariesFor(key1.getClass());
        final Map<T1, T2> fromKey1ToKey2 = key2ToKey2(dictionaryForKey1, key2.getClass());
        T2 t2 = fromKey1ToKey2.putIfAbsent(key1, key2);
        if (t2 != null && t2 != key2) {
            throw new IllegalArgumentException("Conflicting translation");
        }
        return this;
    }

    @NotNull
    private Map key2ToKey2(final Map<Class, Map> dictionaryForKey1,
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
        final Map<T1, T2> map = key2ToKey2(dictionaryForKey1, as);
        return map.get(from);
    }
}
