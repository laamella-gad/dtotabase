package com.laamella.dtotabase;

import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static com.laamella.dtotabase.TestUtil.assertContains;
import static com.laamella.dtotabase.TestUtil.assertEmpty;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

class Address {
    String streetName;
    int number;

    Address(String streetName, int number) {
        this.streetName = streetName;
        this.number = number;
    }
}

public class DtoTableTest {
    private final DtoTable<Address> addressTable = new DtoTable<>();

    private final Address address1 = new Address("Groesbeekseweg", 200);
    private final Address address2 = new Address("Amsterdamsestraatweg", 16);

    @Test
    public void someInserts() {
        Set<Address> selected = addressTable
                .insert(address1)
                .insert(address1, address2)
                .insert(singletonList(address2))
                .select();

        assertContains(selected, address1, address1, address2, address2);
    }

    @Test
    public void selectAllRows() {
        addressTable.insert(address1);
        addressTable.insert(address2);

        Set<Address> selected = addressTable.select();

        assertContains(selected, address1, address2);
    }

    @Test
    public void selectSomeRows() {
        addressTable.insert(address1);
        addressTable.insert(address2);

        Set<Address> selected = addressTable.select(r -> r.number > 50);

        assertContains(selected, address1);
    }

    @Test
    public void selectRowByKey() {
        addressTable.insert(address1);
        addressTable.insert(address2);

        Optional<Address> selected = addressTable.select(address1);

        assertEquals(selected.get(), address1);
    }

    @Test
    public void deleteSomeRows() {
        addressTable.insert(address1);
        addressTable.insert(address2);

        Set<Address> deleted = addressTable.delete(r -> r.number > 50);

        assertContains(deleted, address1);
        assertContains(addressTable.select(), address2);
    }

    @Test
    public void deleteByKey() {
        addressTable.insert(address1);
        addressTable.insert(address2);

        Optional<Address> deleted = addressTable.delete(address1);

        assertEquals(deleted.get(), address1);
        assertContains(addressTable.select(), address2);
    }

    @Test
    public void deleteEverything() {
        addressTable.insert(address1);
        addressTable.insert(address2);

        Set<Address> deleted = addressTable.delete();

        assertContains(deleted, address1, address2);
        assertEmpty(addressTable.select());
    }

    @Test
    public void updateRows() {
        addressTable.insert(address1);
        addressTable.insert(address2);

        Set<Address> updated = addressTable.update(
                address -> address.number > 50,
                address -> address.number *= 2);

        assertContains(updated, address1);
        assertEquals(400, address1.number);
        assertEquals(16, address2.number);
    }
}
