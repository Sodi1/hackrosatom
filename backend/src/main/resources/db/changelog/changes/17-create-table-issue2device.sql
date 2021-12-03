create table issue_2_device
    (
        id bigint PRIMARY KEY,
        plant_id bigint REFERENCES plant(id),
        device_issue_id bigint REFERENCES issue(id),
        device_id bigint REFERENCES device(id),
        created_at timestamp without time zone
    );

    CREATE SEQUENCE issue_2_device_seq;

    ALTER SEQUENCE issue_2_device_seq OWNED BY issue_2_device.id;

    ALTER TABLE  issue_2_device ALTER COLUMN id SET DEFAULT nextval('issue_2_device_seq'::regclass);