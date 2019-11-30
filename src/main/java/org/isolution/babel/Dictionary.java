package org.isolution.babel;

public interface Dictionary {

    <T1, T2> Dictionary put(T1 key1, T2 key2);

    <T1, T2> T2 translate(T1 from, Class<T2> as);
}
