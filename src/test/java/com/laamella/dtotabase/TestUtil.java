package com.laamella.dtotabase;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.fail;

public class TestUtil {
    public static <T> void assertEmpty(Collection<T> result) {
        assertContains(result);
    }

    @SafeVarargs
    public static <T> void assertContains(Collection<T> result, T... expectedArg) {
        Set<T> actual = new HashSet<>(result);
        Set<T> expected = new HashSet<>(asList(expectedArg));

        final StringBuilder out = new StringBuilder();
        for (T e : expected) {
            if (actual.contains(e)) {
                actual.remove(e);
            } else {
                out.append("Missing: ").append(e).append("\n");
            }
        }
        for (T a : actual) {
            out.append("Unexpected: ").append(a).append("\n");
        }

        String s = out.toString();
        if (s.isEmpty()) {
            return;
        }
        fail(s);
    }
}
