package com.laamella.dtotabase;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;

/**
 * A simple all-Java in memory pretend database table.
 *
 * @param <T> the type of the DTO.
 */
public class GuavaImmutableDtoTable<T> extends DtoTableTemplate<T, Set<T>, ImmutableSet<T>, ImmutableList<T>> {
    private final Set<T> rows = new HashSet<>();

    @Override
    protected Set<T> getRows() {
        return rows;
    }

    @Override
    protected Collector<T, ?, ImmutableSet<T>> toSet() {
        return ImmutableSet.toImmutableSet();
    }

    @Override
    protected Collector<T, ?, ImmutableList<T>> toList() {
        return ImmutableList.toImmutableList();
    }

    @Override
    protected ImmutableList<T> isToL(Set<T> ts) {
        return ImmutableList.copyOf(ts);
    }

    @Override
    protected void sort(ImmutableList<T> ts, Comparator<T> orderBy) {
        ts.sort(orderBy);
    }
}
