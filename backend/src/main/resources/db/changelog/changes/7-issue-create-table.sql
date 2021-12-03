create table issue
(
    id bigint PRIMARY KEY,
    plant_id bigint REFERENCES plant(id),
    issue_title character varying,
    issue_description character varying,
    is_satellite boolean,
    created_at timestamp without time zone
);

CREATE SEQUENCE issue_seq;

ALTER SEQUENCE issue_seq OWNED BY issue.id;

ALTER TABLE  issue ALTER COLUMN id SET DEFAULT nextval('issue_seq'::regclass);