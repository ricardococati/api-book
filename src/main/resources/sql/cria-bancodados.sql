drop table dna if exists;

create table dna (
	id_dna integer primary key,
	dna_sequence varchar(255),
	is_mutant boolean
);
