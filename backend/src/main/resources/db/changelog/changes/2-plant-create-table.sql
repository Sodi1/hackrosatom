create table public.plant
(
    id bigint PRIMARY KEY,
    full_name character varying,
    short_name character varying,
    legal_address character varying,
    fact_address character varying,
    processing_depth character varying,
    list_produced character varying,
    foundation_date character varying,
    refinery_status character varying
);

CREATE SEQUENCE plant_seq;

ALTER SEQUENCE plant_seq OWNED BY plant.id;

ALTER TABLE  plant ALTER COLUMN id SET DEFAULT nextval('plant_seq'::regclass);