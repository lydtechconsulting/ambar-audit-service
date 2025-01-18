CREATE SCHEMA IF NOT EXISTS audit;

create table audit.membership_audit
(
	id uuid NOT NULL
		CONSTRAINT membership_audit_pkey
			PRIMARY KEY,
    aggregate_id varchar(255) NOT NULL
        CONSTRAINT membership_audit_aggregate_id_key
            UNIQUE,
	name varchar(255) NOT NULL,
	membership_status varchar(32) NOT NULL,
	created_at timestamp NOT NULL,
	last_updated_at timestamp NOT NULL
);
