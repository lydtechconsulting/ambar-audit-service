CREATE SCHEMA IF NOT EXISTS enquiry;

create table enquiry.membership
(
	id uuid NOT NULL
		CONSTRAINT membership_pkey
			PRIMARY KEY,
    aggregate_id varchar(255) NOT NULL
        CONSTRAINT membership_aggregate_id_key
            UNIQUE,
	name varchar(255) NOT NULL,
	membership_status varchar(32) NOT NULL,
	created_at timestamp NOT NULL,
	last_updated_at timestamp NOT NULL
);
