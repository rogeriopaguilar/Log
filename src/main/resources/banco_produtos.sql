-- SEQUENCE: public.produto_id_seq

-- DROP SEQUENCE public.produto_id_seq;

CREATE SEQUENCE public.produto_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.produto_id_seq
    OWNER TO postgres;

-- Table: public.produto

-- DROP TABLE public.produto;

CREATE TABLE IF NOT EXISTS public.produto
(
    id bigint NOT NULL DEFAULT nextval('produto_id_seq'::regclass),
    feito_por character varying(255) COLLATE pg_catalog."default",
    marca character varying(255) COLLATE pg_catalog."default",
    nome character varying(255) COLLATE pg_catalog."default",
    preco real NOT NULL,
    CONSTRAINT produto_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.produto
    OWNER to postgres;