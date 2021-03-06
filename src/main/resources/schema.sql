create table customer
(
    id         bigint generated by default as identity,
    first_name text,
    last_name  text,
    age        int,
    job        text,
    place      text,
    address      text
);

create index "customer_address_btree" on customer (address);
create index "customer_address_hash" on customer using hash (address);



create table two_column
(
    one text,
    two text,
    perc int
);
create index "two_btree" on two_column (one, two);

create table one_column
(
    one text,
    perc int
);
create index "one_btree" on one_column (one);
