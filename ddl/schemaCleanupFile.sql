alter table legenda add column lagerort_id integer not null default 1;
alter table legenda add constraint legenda_lagerort_fkey FOREIGN KEY (lagerort_id) REFERENCES lagerort;
update legenda lg set lagerort_id = (select lagerort from stlolg where legenda = lg.lgid);
alter table legenda alter column lagerort_id drop default;

select lolg.stlolgid,lo.lagerort,lg.ort,lg.* from legenda lg 
inner join stlolg lolg on lg.lgid = lolg.legenda
inner join lagerort lo on lo.loid = lolg.lagerort
order by lolg.legenda;

delete from stlolg where stlolgid = 35;
delete from stlolg where stlolgid = 54;
delete from stlolg where stlolgid = 55;
delete from stlolg where stlolgid = 28;
delete from stlolg where stlolgid = 56;
delete from stlolg where stlolgid = 106;
delete from stlolg where stlolgid = 51;

drop table stlolg;

drop view stgrla;

CREATE OR REPLACE VIEW public.stgrla AS
 SELECT stgrlat.gruppe,
        CASE
            WHEN has_table_privilege('stgrlat'::text, 'select'::text) = true THEN stgrlat.lager
            WHEN stgrlat.lager < (( SELECT min(l.laid) AS min
               FROM lager l
                 JOIN stlaja ON stlaja.lager = l.laid
                 JOIN jahr j ON stlaja.jahr = j.jaid
              WHERE j.jaid = (( SELECT max(jahr.jaid) AS max
                       FROM jahr)))) THEN stgrlat.lager
            ELSE ( SELECT min(l.laid) AS min
               FROM lager l
                 JOIN stlaja ON stlaja.lager = l.laid
                 JOIN jahr j ON stlaja.jahr = j.jaid
              WHERE j.jaid = (( SELECT max(jahr.jaid) AS max
                       FROM jahr)))
        END AS lager
   FROM stgrlat;

alter table stgrlat DROP column stgrlaid;
alter table stgrle  DROP column stgrle;
alter table stgrze  DROP column stgrzeid;
alter table stlaja  DROP column stlajaid;
alter table stlalo  DROP column stlaloid;
alter table stlama  DROP column stlamaid;
alter table stlast  DROP column stlastid;
alter table stlaze  DROP column stlazeid;
alter table sttegr  DROP column sttegrid;

alter table stgrlat drop CONSTRAINT stgrla_pkey;
alter table stgrle  drop CONSTRAINT stgrle_person_key;
alter table stgrze  drop CONSTRAINT stgrze_gruppe_key;
alter table stlaja  drop CONSTRAINT stlaja_lager_key;
alter table stlalo  drop CONSTRAINT stlalo_lager_key;
alter table stlama  drop CONSTRAINT stlama_pe_key;
alter table stlast  drop CONSTRAINT stlast_lager_key;
alter table stlaze  drop CONSTRAINT stlaze_lager_key;
alter table sttegr  drop CONSTRAINT sttegr_person_key;

alter table stgrlat ADD CONSTRAINT stgrlat_primary_key PRIMARY KEY (lager,gruppe);
alter table stgrle  ADD CONSTRAINT stgrle_primary_key  PRIMARY KEY (person,gruppe);
alter table stgrze  ADD CONSTRAINT stgrze_primary_key  PRIMARY KEY (gruppe,zelt);
alter table stlaja  ADD CONSTRAINT stlaja_primary_key  PRIMARY KEY (lager, jahr);
alter table stlalo  ADD CONSTRAINT stlalo_primary_key  PRIMARY KEY (lager,lagerort);
alter table stlama  ADD CONSTRAINT stlama_primary_key  PRIMARY KEY (person_id, lager_id);
alter table stlast  ADD CONSTRAINT stlast_primary_key  PRIMARY KEY (lager, person,funktion);
alter table stlaze  ADD CONSTRAINT stlaze_primary_key  PRIMARY KEY (lager, zelt);
alter table sttegr  ADD CONSTRAINT sttegr_primary_key  PRIMARY KEY (person, gruppe);

alter table stlama rename column pe to person_id;
alter table stlama rename column la to lager_id;
alter table zverleih rename column ze to zelt_id;
alter table zeltdetail rename column "schlüssel" to schluessel;

alter table waehrung alter column waehrung set not null;
alter table sttegr alter column person set not null;
alter table sttegr alter column gruppe set not null;
alter table stlaze alter column lager set not null;
alter table stlaze alter column zelt set not null;
alter table stlalo alter column lager set not null;
alter table stlalo alter column lagerort set not null;
alter table stgrze alter column gruppe set not null;
alter table stgrze alter column zelt set not null;
alter table stgrle alter column person set not null;
alter table stgrle alter column gruppe set not null;
alter table stlaja alter column lager set not null;
alter table stlaja alter column jahr set not null;
alter table stgrlat alter column lager set not null;
alter table stgrlat alter column gruppe set not null;
alter table schaeden alter column zeid set not null;
alter table programm alter column lager set not null;
alter table programm alter column datum set not null;
alter table legendatyp alter column typ set not null;
alter table lagerinfo alter column person set not null;
alter table lager alter column datumstart set not null;
alter table lager alter column datumstop set not null;
alter table lager alter column name set not null;
alter table jahr alter column jahr set not null;
alter table gruppe alter column name set not null;
alter table essen alter column lager set not null;
alter table essen alter column datum set not null;
alter table anrede alter column anrede set not null;
alter table funktion alter column name set not null;
alter table zeltdetail alter column zelt set not null;
alter table zverleih alter column zelt_id set not null;
