package com.laamella.dtotabase;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;

import static java.util.Arrays.asList;

/**
 * A simple all-Java in memory pretend database table.
 *
 * @param <T> the type of the DTO.
 * @param <S> the type of the set returned.
 * @param <L> the type of the list returned.
 * @param <IS> the type of the internal set.
 */
public abstract class DtoTableTemplate<T, IS extends Collection<T>, S extends Collection<T>, L extends Collection<T>> implements Serializable {
    protected abstract IS getRows();

    /**
     * Selects all rows from the table that match the where-predicate.
     */
    public final S select(Predicate<T> where) {
        return getRows().stream().filter(where).collect(toSet());
    }

    protected abstract Collector<T, ?, S> toSet();

    protected abstract Collector<T, ?, L> toList();

    protected abstract L isToL(IS s);

    protected abstract void sort(L l, Comparator<T> orderBy);

    /**
     * Selects all rows from the table that match the where-predicate and order them.
     */
    public final L select(Predicate<T> where, Comparator<T> orderBy) {
        return getRows().stream().filter(where).sorted(orderBy).collect(toList());
    }

    /**
     * Selects all rows from the table.
     */
    public final Set<T> select() {
        return new HashSet<>(getRows());
    }

    /**
     * Selects all rows from the table and order them.
     */
    public final L select(Comparator<T> orderBy) {
        final L ordered = isToL(getRows());
        sort(ordered, orderBy);
        return ordered;
    }

    /**
     * Selects the row from the table that matches the key by using "equals."
     */
    public final Optional<T> select(T key) {
        return getRows().stream().filter(key::equals).findAny();
    }

    /**
     * Deletes all rows from the table that match the "where" predicate. Returns the deleted rows.
     */
    public final S delete(Predicate<T> where) {
        final S selected = select(where);
        getRows().removeAll(selected);
        return selected;
    }

    /**
     * Deletes the row from the table that matches the "where" predicate. Returns the deleted row.
     */
    public final Optional<T> delete(T where) {
        final Optional<T> selected = select(where);
        selected.ifPresent(getRows()::remove);
        return selected;
    }

    /**
     * Deletes all rows from the table. Returns the deleted rows.
     */
    public final Set<T> delete() {
        final Set<T> deletedRows = new HashSet<>(getRows());
        getRows().clear();
        return deletedRows;
    }

    /**
     * Inserts a new row into the table. Returns this for method call chaining.
     */
    public final DtoTableTemplate<T, IS, S, L> insert(T row) {
        getRows().add(row);
        return this;
    }

    /**
     * Insert several new rows into the table. Returns this for method call chaining.
     */
    @SafeVarargs
    public final DtoTableTemplate<T, IS, S, L> insert(T... rows) {
        getRows().addAll(asList(rows));
        return this;
    }

    /**
     * Insert rows from a Java collection to the table. Returns this for method call chaining.
     */
    public final DtoTableTemplate<T, IS, S, L> insert(Collection<T> rows) {
        getRows().addAll(rows);
        return this;
    }

    /**
     * Calls the "update" consumer for each row that matches the "where" predicate. Returns the matched rows.
     */
    public final S update(Predicate<T> where, Consumer<T> update) {
        final S selected = select(where);
        selected.forEach(update);
        return selected;
    }
}
