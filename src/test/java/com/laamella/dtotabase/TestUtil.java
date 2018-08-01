package com.laamella.dtotabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.fail;

public class TestUtil {
    public static <T> void assertEmpty(Collection<T> result) {
        assertContains(result);
    }

    @SafeVarargs
    public static <T> void assertContains(Collection<T> result, T... expected) {
        List<T> actual = new ArrayList<>(result);

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
