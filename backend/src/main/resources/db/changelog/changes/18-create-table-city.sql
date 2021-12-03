create table city
    (
    id bigint PRIMARY KEY,
    address_city character varying,
    postal_code character varying,
    country character varying,
    federal_district character varying,
    region_type character varying,
    region character varying,
    area_type character varying,
    area character varying,
    city_type character varying,
    city character varying,
    settlement_type character varying,
    settlement character varying,
    kladr_id character varying,
    fias_id character varying,
    fias_level character varying,
    capital_marker character varying,
    okato character varying,
    oktmo character varying,
    tax_office character varying,
    timezone character varying,
    geo_lat character varying,
    geo_lon character varying,
    population_city character varying,
    foundation_year character varying);

    CREATE SEQUENCE city_seq;

    ALTER SEQUENCE city_seq OWNED BY city.id;

    ALTER TABLE  city ALTER COLUMN id SET DEFAULT nextval('city_seq'::regclass);