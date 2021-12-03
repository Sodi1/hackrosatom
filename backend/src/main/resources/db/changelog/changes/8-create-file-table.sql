create table file_image
(
    id bigint PRIMARY KEY,
    issue_id bigint REFERENCES issue(id),
    file_path character varying,
    created_at timestamp without time zone
);

CREATE SEQUENCE file_image_seq;

ALTER SEQUENCE file_image_seq OWNED BY file_image.id;

ALTER TABLE  file_image ALTER COLUMN id SET DEFAULT nextval('file_image_seq'::regclass);