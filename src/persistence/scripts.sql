drop database if exists DesafioSenior;
create database DesafioSenior;
use DesafioSenior;

create table cidade(
	ibge_id int primary key,
	uf char(2),
	name char(50),
	capital boolean,
	lon double,
	lat double,
	no_accents char(50),
	alternative_names char(50),
	microregion char(50),
	mesoregion char(50)
);
