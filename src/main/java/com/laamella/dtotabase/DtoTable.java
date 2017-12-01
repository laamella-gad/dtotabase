package com.laamella.dtotabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * A simple all-Java in memory pretend database table.
 */
public class DtoTable<T> implements Serializable {
    private final List<T> rows = new ArrayList<>();

    /**
     * Selects all rows from the table that match the where-predicate.
     */
    public final List<T> select(Predicate<T> where) {
        return rows.stream().filter(where).collect(toList());
    }

    /**
     * Selects all rows from the table.
     */
    public final List<T> select() {
        return new ArrayList<>(rows);
    }

    /**
     * Selects all rows from the table that match the key by using "equals."
     */
    public final List<T> select(T key) {
        return rows.stream().filter(key::equals).collect(toList());
    }

    /**
     * Deletes all rows from the table that match the "where" predicate. Returns the deleted rows.
     */
    public final List<T> delete(Predicate<T> where) {
        final List<T> selected = select(where);
        rows.removeAll(selected);
        return selected;
    }

    /**
     * Deletes all rows from the table that match the "where" predicate. Returns the deleted rows.
     */
    public final List<T> delete(T where) {
        final List<T> selected = select(where);
        rows.removeAll(selected);
        return selected;
    }

    /**
     * Deletes all rows from the table. Returns the deleted rows.
     */
    public final List<T> delete() {
        final List<T> deletedRows = new ArrayList<>(rows);
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
    public final List<T> update(Predicate<T> where, Consumer<T> update) {
        final List<T> selected = select(where);
        selected.forEach(update);
        return selected;
    }
}
