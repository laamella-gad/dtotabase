package com.laamella.dtotabase;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * A simple all-Java in memory pretend database table.
 *
 * @param <T> the type of the DTO.
 */
public class DtoTable<T> extends DtoTableTemplate<T, Set<T>, Set<T>, List<T>> {
    private final Set<T> rows = new HashSet<>();

    @Override
    protected Set<T> getRows() {
        return rows;
    }

    @Override
    protected Collector<T, ?, Set<T>> toSet() {
        return Collectors.toSet();
    }

    @Override
    protected Collector<T, ?, List<T>> toList() {
        return Collectors.toList();
    }

    @Override
    protected List<T> isToL(Set<T> ts) {
        return new ArrayList<>(ts);
    }

    @Override
    protected void sort(List<T> ts, Comparator<T> orderBy) {
        ts.sort(orderBy);
    }
}
