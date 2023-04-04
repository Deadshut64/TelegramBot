CREATE TABLE customers
(
    customer_name text  NOT NULL,
    chat_id bigint NOT NULL,
    basket text,
    amount integer,
    registration timestamp without time zone,
    CONSTRAINT customers_pkey PRIMARY KEY (chat_id)
)