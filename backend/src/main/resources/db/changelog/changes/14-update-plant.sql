UPDATE plant SET plant_kind = 'NPS' WHERE UPPER(full_name) LIKE '%НПС%' OR UPPER(full_name) LIKE 'НПС%';