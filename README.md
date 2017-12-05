# Dtotabase
The most basic in-memory database in the world.

# Why?
Because for test code I needed a data structure where I could easily store DTO's (or just any object) and get them out again in various ways with the least amount of hassle.

Of course, we have maps and lists and sets and everything,
but this project treats DTO's more like database rows.
You can do SELECT, INSERT, DELETE and UPDATE, by using functions.
Hence "DTO Database."

It's incredibly basic, anybody could make it in an hour.
Well, I saved you that hour.

# Maven

Here it is:
[![Maven Central](https://img.shields.io/maven-central/v/com.laamella/dtotabase.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.laamella%22%20AND%20a%3A%22dtotabase%22)

# Usage
(This is all taken from the unit tests.)

#### Create a table
```java
DtoTable<Address> addressTable = new DtoTable<>()
```
Subclass it if you like to add more methods.

#### Put some data in

```java
addressTable.insert(address1, address2)
```

Duplicates (objects which are `equals`) will be ignored
#### Retrieve some data

```java
Set<Address> selected = addressTable.select(r -> r.number > 50)
```
This is the big advantage of Dtotabase:
quickly select data in any way you like with just a simple lambda expression.

#### Update some data

```java
addressTable.update(
        address -> address.number > 50,
        address -> address.number *= 2)
```

#### Delete some data

```java
addressTable.delete(r -> r.name.contains("lane"))
```

# Support
There may not be much activity since this project is very small,
but I do intend to support it.
Do report issues when you like more features or find bugs.