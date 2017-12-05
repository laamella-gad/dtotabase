package com.laamella.dtotabase;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * A simple all-Java in memory pretend database table.
 */
public class DtoTable<T> implements Serializable {
    private final Set<T> rows = new HashSet<>();

    /**
     * Selects all rows from the table that match the where-predicate.
     */
    public final Set<T> select(Predicate<T> where) {
        return rows.stream().filter(where).collect(toSet());
    }

    /**
     * Selects all rows from the table that match the where-predicate and order them.
     */
    public final List<T> select(Predicate<T> where, Comparator<T> orderBy) {
        return rows.stream().filter(where).sorted(orderBy).collect(toList());
    }

    /**
     * Selects all rows from the table.
     */
    public final Set<T> select() {
        return new HashSet<>(rows);
    }

    /**
     * Selects all rows from the table and order them.
     */
    public final List<T> select(Comparator<T> orderBy) {
        final List<T> ordered = new ArrayList<>(rows);
        ordered.sort(orderBy);
        return ordered;
    }

    /**
     * Selects the row from the table that matches the key by using "equals."
     */
    public final Optional<T> select(T key) {
        return rows.stream().filter(key::equals).findAny();
    }

    /**
     * Deletes all rows from the table that match the "where" predicate. Returns the deleted rows.
     */
    public final Set<T> delete(Predicate<T> where) {
        final Set<T> selected = select(where);
        rows.removeAll(selected);
        return selected;
    }

    /**
     * Deletes the row from the table that matches the "where" predicate. Returns the deleted row.
     */
    public final Optional<T> delete(T where) {
        final Optional<T> selected = select(where);
        selected.ifPresent(rows::remove);
        return selected;
    }

    /**
     * Deletes all rows from the table. Returns the deleted rows.
     */
    public final Set<T> delete() {
        final Set<T> deletedRows = new HashSet<>(rows);
        rows.clear();
        return deletedRows;
    }

    /**
     * Inserts a new row into the table. Returns this for method call chaining.
     */
    public final DtoTable<T> insert(T row) {
        rows.add(row);
        return this;
    }

    /**
     * Insert several new rows into the table. Returns this for method call chaining.
     */
    @SafeVarargs
    public final DtoTable<T> insert(T... rows) {
        this.rows.addAll(asList(rows));
        return this;
    }

    /**
     * Insert rows from a Java collection to the table. Returns this for method call chaining.
     */
    public final DtoTable<T> insert(Collection<T> rows) {
        this.rows.addAll(rows);
        return this;
    }

    /**
     * Calls the "update" consumer for each row that matches the "where" predicate. Returns the matched rows.
     */
    public final Set<T> update(Predicate<T> where, Consumer<T> update) {
        final Set<T> selected = select(where);
        selected.forEach(update);
        return selected;
    }
}
