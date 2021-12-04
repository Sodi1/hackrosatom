create table spill(
    id  bigint PRIMARY KEY,
    plant character varying,
    license_area character varying,
    number_of character varying,
    priority_pollutant_type character varying,
    registration_number character varying,
    registration_number_register character varying,
    old_registration_numbers character varying,
    location_contaminated_site character varying,
    administrative_region character varying,
    lon character varying,
    lan character varying,
    date_of_registration_register timestamp without time zone,
    date_of_last_spill timestamp without time zone,
    technical_investigation_act character varying,
    date_of character varying,
    land_category_bef_pollution character varying,
    avail_special_prot_zone character varying,
    contaminated_site_area character varying,
    level_pollution_soils character varying,
    level_of_oil character varying,
    year_reclamation character varying
);


  CREATE SEQUENCE spill_seq;

    ALTER SEQUENCE spill_seq OWNED BY spill.id;

    ALTER TABLE  spill ALTER COLUMN id SET DEFAULT nextval('spill_seq'::regclass);

