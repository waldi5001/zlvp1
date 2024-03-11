pg_dump -sxOC --no-comments -n public -d zlvp > /tmp/zlvp.ddl
--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3 (Debian 15.3-1.pgdg110+1)
-- Dumped by pg_dump version 15.3 (Debian 15.3-1.pgdg110+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: zlvp; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE zlvp WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'de_DE.UTF-8';


\connect zlvp

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

-- CREATE SCHEMA public;
SET search_path TO public;


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: persont; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE persont (
    peid integer NOT NULL,
    vorname character varying(50) NOT NULL,
    nachname character varying(50) NOT NULL,
    gebdat date NOT NULL,
    strasse text,
    plz text,
    ort text,
    telnr text,
    email text,
    jahrgang integer,
    vornamenachname text,
    geschlecht integer,
    handy text,
    nottel character varying(15)
);


--
-- Name: Person_Jahrgang_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Person_Jahrgang_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: Person_Jahrgang_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Person_Jahrgang_seq" OWNED BY persont.jahrgang;


--
-- Name: Person_PeID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Person_PeID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: Person_PeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Person_PeID_seq" OWNED BY persont.peid;


--
-- Name: anrede; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE anrede (
    anid integer NOT NULL,
    anrede character varying(10) NOT NULL
);


--
-- Name: anrede_anid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE anrede_anid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: anrede_anid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE anrede_anid_seq OWNED BY anrede.anid;


--
-- Name: essen; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE essen (
    esid integer NOT NULL,
    lager integer NOT NULL,
    datum date NOT NULL,
    morgen text,
    mittag text,
    abend text
);


--
-- Name: essen_EsID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "essen_EsID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: essen_EsID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "essen_EsID_seq" OWNED BY essen.esid;


--
-- Name: funktion; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE funktion (
    fuid integer NOT NULL,
    name character varying(15) NOT NULL
);


--
-- Name: funktionen_fuid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE funktionen_fuid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: funktionen_fuid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE funktionen_fuid_seq OWNED BY funktion.fuid;


--
-- Name: geschlecht; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE geschlecht (
    geid integer NOT NULL,
    name character varying(10) NOT NULL
);


--
-- Name: geschlecht_geid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE geschlecht_geid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: geschlecht_geid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE geschlecht_geid_seq OWNED BY geschlecht.geid;


--
-- Name: gruppe; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE gruppe (
    grid integer NOT NULL,
    name text NOT NULL,
    schlachtruf text
);


--
-- Name: gruppe_GrID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "gruppe_GrID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: gruppe_GrID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "gruppe_GrID_seq" OWNED BY gruppe.grid;


--
-- Name: jahr; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE jahr (
    jaid integer NOT NULL,
    jahr integer NOT NULL
);


--
-- Name: jahr_JaID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "jahr_JaID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: jahr_JaID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "jahr_JaID_seq" OWNED BY jahr.jaid;


--
-- Name: lager; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lager (
    laid integer NOT NULL,
    name text NOT NULL,
    thema text,
    ort text,
    datumstart date NOT NULL,
    datumstop date NOT NULL
);


--
-- Name: lager_LaID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "lager_LaID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: lager_LaID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "lager_LaID_seq" OWNED BY lager.laid;


--
-- Name: lagerinfo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lagerinfo (
    liid integer NOT NULL,
    person integer NOT NULL
);


--
-- Name: lagerinfo_liid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE lagerinfo_liid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: lagerinfo_liid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE lagerinfo_liid_seq OWNED BY lagerinfo.liid;


--
-- Name: lagerort; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lagerort (
    loid integer NOT NULL,
    lagerort character varying(25) NOT NULL
);


--
-- Name: lagerort_loid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE lagerort_loid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: lagerort_loid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE lagerort_loid_seq OWNED BY lagerort.loid;


--
-- Name: legenda; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE legenda (
    lgid integer NOT NULL,
    typ integer,
    name character varying(50),
    vorname character varying(50),
    strasse character varying(30),
    plz character varying(5),
    ort character varying(20),
    tel character varying(20),
    handy character varying(20),
    fax character varying(20),
    email character varying(20),
    anrede integer,
    firma character varying(50),
    bemerkung text,
    lagerort_id integer NOT NULL
);


--
-- Name: legenda_lgid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE legenda_lgid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: legenda_lgid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE legenda_lgid_seq OWNED BY legenda.lgid;


--
-- Name: legendatyp; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE legendatyp (
    tyid integer NOT NULL,
    typ character varying(20) NOT NULL
);


--
-- Name: legendatyp_tyid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE legendatyp_tyid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: legendatyp_tyid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE legendatyp_tyid_seq OWNED BY legendatyp.tyid;


--
-- Name: person; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW person AS
 SELECT persont.peid,
    persont.vorname,
    (
        CASE
            WHEN (( SELECT count(*) AS count
               FROM persont p1
              WHERE (((p1.vorname)::text = (persont.vorname)::text) AND ((p1.nachname)::text = (persont.nachname)::text))) <> 1) THEN (((((persont.nachname)::text || ' ('::text) || date_part('year'::text, persont.gebdat)) || ')'::text))::character varying
            ELSE persont.nachname
        END)::character varying(50) AS nachname,
    persont.geschlecht,
        CASE
            WHEN (has_table_privilege('persont'::text, 'select'::text) = true) THEN persont.strasse
            ELSE NULL::text
        END AS strasse,
        CASE
            WHEN (has_table_privilege('persont'::text, 'select'::text) = true) THEN persont.plz
            ELSE NULL::text
        END AS plz,
        CASE
            WHEN (has_table_privilege('persont'::text, 'select'::text) = true) THEN persont.ort
            ELSE NULL::text
        END AS ort,
        CASE
            WHEN (has_table_privilege('persont'::text, 'select'::text) = true) THEN persont.telnr
            ELSE NULL::text
        END AS telnr,
        CASE
            WHEN (has_table_privilege('persont'::text, 'select'::text) = true) THEN persont.email
            ELSE NULL::text
        END AS email,
        CASE
            WHEN (has_table_privilege('persont'::text, 'select'::text) = true) THEN persont.gebdat
            ELSE NULL::date
        END AS gebdat,
        CASE
            WHEN (has_table_privilege('persont'::text, 'select'::text) = true) THEN persont.handy
            ELSE NULL::text
        END AS handy,
        CASE
            WHEN (has_table_privilege('persont'::text, 'select'::text) = true) THEN (persont.nottel)::text
            ELSE NULL::text
        END AS nottel
   FROM persont;


--
-- Name: programm; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE programm (
    prid integer NOT NULL,
    lager integer NOT NULL,
    datum date NOT NULL,
    vormittag text,
    nachmittag text,
    nacht text
);


--
-- Name: programm_PrID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "programm_PrID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: programm_PrID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "programm_PrID_seq" OWNED BY programm.prid;


--
-- Name: schaeden; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE schaeden (
    scid integer NOT NULL,
    zeid integer NOT NULL,
    bezeichnung text,
    datum date
);


--
-- Name: schäden_scid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "schäden_scid_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: schäden_scid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "schäden_scid_seq" OWNED BY schaeden.scid;


--
-- Name: stgrlat; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stgrlat (
    lager integer NOT NULL,
    gruppe integer NOT NULL
);


--
-- Name: stlaja; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlaja (
    lager integer NOT NULL,
    jahr integer NOT NULL
);


--
-- Name: stgrla; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW stgrla AS
 SELECT stgrlat.gruppe,
        CASE
            WHEN (has_table_privilege('stgrlat'::text, 'select'::text) = true) THEN stgrlat.lager
            WHEN (stgrlat.lager < ( SELECT min(l.laid) AS min
               FROM ((lager l
                 JOIN stlaja ON ((stlaja.lager = l.laid)))
                 JOIN jahr j ON ((stlaja.jahr = j.jaid)))
              WHERE (j.jaid = ( SELECT max(jahr.jaid) AS max
                       FROM jahr)))) THEN stgrlat.lager
            ELSE ( SELECT min(l.laid) AS min
               FROM ((lager l
                 JOIN stlaja ON ((stlaja.lager = l.laid)))
                 JOIN jahr j ON ((stlaja.jahr = j.jaid)))
              WHERE (j.jaid = ( SELECT max(jahr.jaid) AS max
                       FROM jahr)))
        END AS lager
   FROM stgrlat;


--
-- Name: stgrle; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stgrle (
    person integer NOT NULL,
    gruppe integer NOT NULL
);


--
-- Name: stgrze; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stgrze (
    gruppe integer NOT NULL,
    zelt integer NOT NULL
);


--
-- Name: stlalo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlalo (
    lager integer NOT NULL,
    lagerort integer NOT NULL
);


--
-- Name: stlama; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlama (
    person_id integer NOT NULL,
    lager_id integer NOT NULL
);


--
-- Name: stlast; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlast (
    lager integer NOT NULL,
    person integer NOT NULL,
    funktion integer NOT NULL
);


--
-- Name: stlaze; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlaze (
    lager integer NOT NULL,
    zelt integer NOT NULL
);

--
-- Name: sttegr; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE sttegr (
    person integer NOT NULL,
    gruppe integer NOT NULL
);


--
-- Name: waehrung; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE waehrung (
    waid integer NOT NULL,
    waehrung character varying(5) NOT NULL
);


--
-- Name: waehrung_waid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE waehrung_waid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: waehrung_waid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE waehrung_waid_seq OWNED BY waehrung.waid;


--
-- Name: zdbez; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE zdbez (
    zdbid integer NOT NULL,
    bezeichnung text
);


--
-- Name: zdbez_zdbid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE zdbez_zdbid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: zdbez_zdbid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE zdbez_zdbid_seq OWNED BY zdbez.zdbid;


--
-- Name: zelt; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE zelt (
    zeid integer NOT NULL,
    bezeichnung text,
    preis double precision,
    angeschafft date,
    waehrung integer
);


--
-- Name: zelt_ZeID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "zelt_ZeID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: zelt_ZeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "zelt_ZeID_seq" OWNED BY zelt.zeid;


--
-- Name: zeltdetail; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE zeltdetail (
    zdid integer NOT NULL,
    anzahl integer,
    zelt integer,
    bezeichnung integer,
    schluessel character varying(20)
);


--
-- Name: zeltdetail_zdid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE zeltdetail_zdid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: zeltdetail_zdid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE zeltdetail_zdid_seq OWNED BY zeltdetail.zdid;


--
-- Name: zverleih; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE zverleih (
    zvid integer NOT NULL,
    datum date,
    person text,
    bemerkung text,
    zelt_id integer NOT NULL
);


--
-- Name: zverleih_zvid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE zverleih_zvid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: zverleih_zvid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE zverleih_zvid_seq OWNED BY zverleih.zvid;


--
-- Name: anrede anid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY anrede ALTER COLUMN anid SET DEFAULT nextval('anrede_anid_seq'::regclass);


--
-- Name: essen esid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY essen ALTER COLUMN esid SET DEFAULT nextval('"essen_EsID_seq"'::regclass);


--
-- Name: funktion fuid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY funktion ALTER COLUMN fuid SET DEFAULT nextval('funktionen_fuid_seq'::regclass);


--
-- Name: geschlecht geid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY geschlecht ALTER COLUMN geid SET DEFAULT nextval('geschlecht_geid_seq'::regclass);


--
-- Name: gruppe grid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY gruppe ALTER COLUMN grid SET DEFAULT nextval('"gruppe_GrID_seq"'::regclass);


--
-- Name: jahr jaid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY jahr ALTER COLUMN jaid SET DEFAULT nextval('"jahr_JaID_seq"'::regclass);


--
-- Name: lager laid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lager ALTER COLUMN laid SET DEFAULT nextval('"lager_LaID_seq"'::regclass);


--
-- Name: lagerinfo liid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerinfo ALTER COLUMN liid SET DEFAULT nextval('lagerinfo_liid_seq'::regclass);


--
-- Name: lagerort loid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerort ALTER COLUMN loid SET DEFAULT nextval('lagerort_loid_seq'::regclass);


--
-- Name: legenda lgid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda ALTER COLUMN lgid SET DEFAULT nextval('legenda_lgid_seq'::regclass);


--
-- Name: legendatyp tyid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY legendatyp ALTER COLUMN tyid SET DEFAULT nextval('legendatyp_tyid_seq'::regclass);


--
-- Name: persont peid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont ALTER COLUMN peid SET DEFAULT nextval('"Person_PeID_seq"'::regclass);


--
-- Name: programm prid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY programm ALTER COLUMN prid SET DEFAULT nextval('"programm_PrID_seq"'::regclass);


--
-- Name: schaeden scid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY schaeden ALTER COLUMN scid SET DEFAULT nextval('"schäden_scid_seq"'::regclass);


--
-- Name: waehrung waid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY waehrung ALTER COLUMN waid SET DEFAULT nextval('waehrung_waid_seq'::regclass);


--
-- Name: zdbez zdbid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zdbez ALTER COLUMN zdbid SET DEFAULT nextval('zdbez_zdbid_seq'::regclass);


--
-- Name: zelt zeid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt ALTER COLUMN zeid SET DEFAULT nextval('"zelt_ZeID_seq"'::regclass);


--
-- Name: zeltdetail zdid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail ALTER COLUMN zdid SET DEFAULT nextval('zeltdetail_zdid_seq'::regclass);


--
-- Name: zverleih zvid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zverleih ALTER COLUMN zvid SET DEFAULT nextval('zverleih_zvid_seq'::regclass);


--
-- Name: anrede anrede_anrede_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY anrede
    ADD CONSTRAINT anrede_anrede_key UNIQUE (anrede);


--
-- Name: anrede anrede_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY anrede
    ADD CONSTRAINT anrede_pkey PRIMARY KEY (anid);


--
-- Name: essen essen_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY essen
    ADD CONSTRAINT essen_pkey PRIMARY KEY (esid);


--
-- Name: funktion funktionen_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY funktion
    ADD CONSTRAINT funktionen_name_key UNIQUE (name);


--
-- Name: funktion funktionen_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY funktion
    ADD CONSTRAINT funktionen_pkey PRIMARY KEY (fuid);


--
-- Name: geschlecht geschlecht_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geschlecht
    ADD CONSTRAINT geschlecht_pkey PRIMARY KEY (geid);


--
-- Name: gruppe gruppe_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gruppe
    ADD CONSTRAINT gruppe_name_key UNIQUE (name);


--
-- Name: gruppe gruppe_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gruppe
    ADD CONSTRAINT gruppe_pkey PRIMARY KEY (grid);


--
-- Name: jahr jahr_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY jahr
    ADD CONSTRAINT jahr_pkey PRIMARY KEY (jaid);


--
-- Name: lager lager_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lager
    ADD CONSTRAINT lager_pkey PRIMARY KEY (laid);


--
-- Name: lagerinfo lagerinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerinfo
    ADD CONSTRAINT lagerinfo_pkey PRIMARY KEY (liid);


--
-- Name: lagerort lagerort_lagerort_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerort
    ADD CONSTRAINT lagerort_lagerort_key UNIQUE (lagerort);


--
-- Name: lagerort lagerort_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerort
    ADD CONSTRAINT lagerort_pkey PRIMARY KEY (loid);


--
-- Name: legenda legenda_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_pkey PRIMARY KEY (lgid);


--
-- Name: legenda legenda_typ_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_typ_key UNIQUE (typ, name, firma);


--
-- Name: legendatyp legendatyp_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legendatyp
    ADD CONSTRAINT legendatyp_pkey PRIMARY KEY (tyid);


--
-- Name: legendatyp legendatyp_typ_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legendatyp
    ADD CONSTRAINT legendatyp_typ_key UNIQUE (typ);


--
-- Name: persont person_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont
    ADD CONSTRAINT person_pkey PRIMARY KEY (peid);


--
-- Name: persont persont_vorname_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont
    ADD CONSTRAINT persont_vorname_key UNIQUE (vorname, nachname, gebdat);


--
-- Name: programm programm_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programm
    ADD CONSTRAINT programm_pkey PRIMARY KEY (prid);


--
-- Name: schaeden schäden_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY schaeden
    ADD CONSTRAINT "schäden_pkey" PRIMARY KEY (scid);


--
-- Name: stgrlat stgrlat_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT stgrlat_primary_key PRIMARY KEY (lager, gruppe);


--
-- Name: stgrle stgrle_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT stgrle_primary_key PRIMARY KEY (person, gruppe);


--
-- Name: stgrze stgrze_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT stgrze_primary_key PRIMARY KEY (gruppe, zelt);


--
-- Name: stlaja stlaja_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT stlaja_primary_key PRIMARY KEY (lager, jahr);


--
-- Name: stlalo stlalo_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT stlalo_primary_key PRIMARY KEY (lager, lagerort);


--
-- Name: stlama stlama_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT stlama_primary_key PRIMARY KEY (person_id, lager_id);


--
-- Name: stlast stlast_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT stlast_primary_key PRIMARY KEY (lager, person, funktion);


--
-- Name: stlaze stlaze_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT stlaze_primary_key PRIMARY KEY (lager, zelt);

--
-- Name: sttegr sttegr_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT sttegr_primary_key PRIMARY KEY (person, gruppe);


--
-- Name: waehrung waehrung_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY waehrung
    ADD CONSTRAINT waehrung_pkey PRIMARY KEY (waid);


--
-- Name: zdbez zdbez_bezeichnung_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zdbez
    ADD CONSTRAINT zdbez_bezeichnung_key UNIQUE (bezeichnung);


--
-- Name: zdbez zdbez_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zdbez
    ADD CONSTRAINT zdbez_pkey PRIMARY KEY (zdbid);


--
-- Name: zelt zelt_bezeichnung_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt
    ADD CONSTRAINT zelt_bezeichnung_key UNIQUE (bezeichnung);


--
-- Name: zelt zelt_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt
    ADD CONSTRAINT zelt_pkey PRIMARY KEY (zeid);


--
-- Name: zeltdetail zeltdetail_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail
    ADD CONSTRAINT zeltdetail_pkey PRIMARY KEY (zdid);


--
-- Name: zverleih zverleih_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zverleih
    ADD CONSTRAINT zverleih_pkey PRIMARY KEY (zvid);


--
-- Name: ix_schaeden_zelt; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX ix_schaeden_zelt ON schaeden USING btree (zeid);


--
-- Name: ix_stgrle_person; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX ix_stgrle_person ON stgrle USING btree (person);


--
-- Name: ix_stgrze_zelt; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX ix_stgrze_zelt ON stgrze USING btree (zelt);


--
-- Name: ix_sttegr_persont; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX ix_sttegr_persont ON sttegr USING btree (person);


--
-- Name: ix_zeltdetail_zdbez; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX ix_zeltdetail_zdbez ON zeltdetail USING btree (bezeichnung);


--
-- Name: ix_zeltdetail_zelt; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX ix_zeltdetail_zelt ON zeltdetail USING btree (zelt);


--
-- Name: ix_zverleih_zelt; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX ix_zverleih_zelt ON zverleih USING btree (zelt_id);


--
-- Name: essen fk_essen_lager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY essen
    ADD CONSTRAINT fk_essen_lager FOREIGN KEY (lager) REFERENCES lager(laid) ON DELETE CASCADE;


--
-- Name: lagerinfo fk_lagerinfo_persont; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerinfo
    ADD CONSTRAINT fk_lagerinfo_persont FOREIGN KEY (person) REFERENCES persont(peid);


--
-- Name: legenda fk_legenda_anrede; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT fk_legenda_anrede FOREIGN KEY (anrede) REFERENCES anrede(anid);


--
-- Name: legenda fk_legenda_lagerort; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT fk_legenda_lagerort FOREIGN KEY (lagerort_id) REFERENCES lagerort(loid);


--
-- Name: legenda fk_legenda_legendatyp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT fk_legenda_legendatyp FOREIGN KEY (typ) REFERENCES legendatyp(tyid);


--
-- Name: persont fk_persont_geschlecht; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont
    ADD CONSTRAINT fk_persont_geschlecht FOREIGN KEY (geschlecht) REFERENCES geschlecht(geid);


--
-- Name: programm fk_programm_lager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programm
    ADD CONSTRAINT fk_programm_lager FOREIGN KEY (lager) REFERENCES lager(laid) ON DELETE CASCADE;


--
-- Name: schaeden fk_schaeden_zelt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY schaeden
    ADD CONSTRAINT fk_schaeden_zelt FOREIGN KEY (zeid) REFERENCES zelt(zeid);


--
-- Name: stgrlat fk_stgrlat_gruppe; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT fk_stgrlat_gruppe FOREIGN KEY (gruppe) REFERENCES gruppe(grid) ON DELETE CASCADE;


--
-- Name: stgrlat fk_stgrlat_lager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT fk_stgrlat_lager FOREIGN KEY (lager) REFERENCES lager(laid) ON DELETE CASCADE;


--
-- Name: stgrle fk_stgrle_gruppe; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT fk_stgrle_gruppe FOREIGN KEY (gruppe) REFERENCES gruppe(grid) ON DELETE CASCADE;


--
-- Name: stgrle fk_stgrle_persont; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT fk_stgrle_persont FOREIGN KEY (person) REFERENCES persont(peid);


--
-- Name: stgrze fk_stgrze_gruppe; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT fk_stgrze_gruppe FOREIGN KEY (gruppe) REFERENCES gruppe(grid) ON DELETE CASCADE;


--
-- Name: stgrze fk_stgrze_zelt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT fk_stgrze_zelt FOREIGN KEY (zelt) REFERENCES zelt(zeid);


--
-- Name: stlaja fk_stlaja_jahr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT fk_stlaja_jahr FOREIGN KEY (jahr) REFERENCES jahr(jaid) ON DELETE CASCADE;


--
-- Name: stlaja fk_stlaja_lager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT fk_stlaja_lager FOREIGN KEY (lager) REFERENCES lager(laid) ON DELETE CASCADE;


--
-- Name: stlalo fk_stlalo_lager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT fk_stlalo_lager FOREIGN KEY (lager) REFERENCES lager(laid) ON DELETE CASCADE;


--
-- Name: stlalo fk_stlalo_lagerort; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT fk_stlalo_lagerort FOREIGN KEY (lagerort) REFERENCES lagerort(loid);


--
-- Name: stlama fk_stlama_lager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT fk_stlama_lager FOREIGN KEY (lager_id) REFERENCES lager(laid) ON DELETE CASCADE;


--
-- Name: stlama fk_stlama_persont; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT fk_stlama_persont FOREIGN KEY (person_id) REFERENCES persont(peid);


--
-- Name: stlast fk_stlast_funktion; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT fk_stlast_funktion FOREIGN KEY (funktion) REFERENCES funktion(fuid);


--
-- Name: stlast fk_stlast_lager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT fk_stlast_lager FOREIGN KEY (lager) REFERENCES lager(laid) ON DELETE CASCADE;


--
-- Name: stlast fk_stlast_persont; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT fk_stlast_persont FOREIGN KEY (person) REFERENCES persont(peid);


--
-- Name: stlaze fk_stlaze_lager; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT fk_stlaze_lager FOREIGN KEY (lager) REFERENCES lager(laid) ON DELETE CASCADE;


--
-- Name: stlaze fk_stlaze_zelt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT fk_stlaze_zelt FOREIGN KEY (zelt) REFERENCES zelt(zeid);


--
-- Name: sttegr fk_sttegr_gruppe; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT fk_sttegr_gruppe FOREIGN KEY (gruppe) REFERENCES gruppe(grid) ON DELETE CASCADE;


--
-- Name: sttegr fk_sttegr_persont; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT fk_sttegr_persont FOREIGN KEY (person) REFERENCES persont(peid);


--
-- Name: zelt fk_zelt_waehrung; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt
    ADD CONSTRAINT fk_zelt_waehrung FOREIGN KEY (waehrung) REFERENCES waehrung(waid);


--
-- Name: zeltdetail fk_zeltdetail_zdbez; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail
    ADD CONSTRAINT fk_zeltdetail_zdbez FOREIGN KEY (bezeichnung) REFERENCES zdbez(zdbid);


--
-- Name: zeltdetail fk_zeltdetail_zelt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail
    ADD CONSTRAINT fk_zeltdetail_zelt FOREIGN KEY (zelt) REFERENCES zelt(zeid);


--
-- Name: zverleih fk_zverleih_zelt; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zverleih
    ADD CONSTRAINT fk_zverleih_zelt FOREIGN KEY (zelt_id) REFERENCES zelt(zeid);


--
-- PostgreSQL database dump complete
--

