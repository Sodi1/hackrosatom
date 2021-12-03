TRUNCATE table telemetry;

ALTER TABLE telemetry DROP COLUMN light;
ALTER TABLE telemetry DROP COLUMN temperature;

ALTER TABLE telemetry ADD COLUMN tempwater float(4);
ALTER TABLE telemetry ADD COLUMN tempair float(4);
ALTER TABLE telemetry ADD COLUMN lightwater float(4);
ALTER TABLE telemetry ADD COLUMN lighair float(4);
