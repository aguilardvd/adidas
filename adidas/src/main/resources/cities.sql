CREATE TABLE if not exists public.cities (
	city varchar NULL,
	destination_city varchar,
	departure_time int8 NULL,
	arrival_time int8 null,
	primary KEY( city, destination_city )
	
)
WITH (
	OIDS=FALSE
) ;

INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Zaragoza','Huesca',41,62);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Zaragoza','Madrid',12,85);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Zaragoza','Teruel',21,32);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Huesca','Madrid',12,45);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Huesca','Barcelona',14,75);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Huesca','Zaragoza',21,75);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Madrid','Zaragoza',14,63);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Madrid','Huesca',34,87);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Madrid','Barcelona',10,91);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Madrid','Teruel',14,20);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Teruel','Zaragoza',41,85);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Teruel','Madrid',13,74);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Teruel','Barcelona',52,87);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Teruel','Cádiz',27,41);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Barcelona','Huesca',23,64);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Barcelona','Madrid',34,81);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Barcelona','Teruel',31,98);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Barcelona','Cádiz',20,34);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Cádiz','Teruel',8,97);
INSERT INTO public.cities (city,destination_city,departure_time,arrival_time) VALUES (
'Cádiz','Barcelona',41,88);
