ALTER TABLE tb_customer DROP CONSTRAINT IF EXISTS tb_customer_pkey;

CREATE SEQUENCE tb_customer_id_seq START 1;

ALTER TABLE tb_customer 
    ALTER COLUMN id SET DEFAULT nextval('tb_customer_id_seq');

ALTER TABLE tb_customer 
    ALTER COLUMN id SET NOT NULL;

ALTER TABLE tb_customer 
    ADD PRIMARY KEY (id);