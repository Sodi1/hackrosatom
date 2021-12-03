create table telemetry
(
    id bigint PRIMARY KEY,
    device_id bigint REFERENCES device(id),
    temperature float(4),
    light float(4),
    gas float(4),
    uv float(4),
    oil float(4),
    created_at timestamp without time zone
);

CREATE SEQUENCE telemetry_seq;

ALTER SEQUENCE telemetry_seq OWNED BY telemetry.id;

ALTER TABLE  telemetry ALTER COLUMN id SET DEFAULT nextval('telemetry_seq'::regclass);