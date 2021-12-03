create table device
(
    id bigint PRIMARY KEY,
    title character varying,
    code character varying,
    lan character varying,
    lon character varying,
    plant_id bigint
);

CREATE SEQUENCE device_seq;

ALTER SEQUENCE device_seq OWNED BY device.id;

ALTER TABLE  device ALTER COLUMN id SET DEFAULT nextval('device_seq'::regclass);