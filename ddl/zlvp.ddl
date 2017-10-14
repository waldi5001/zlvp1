--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.12
-- Dumped by pg_dump version 9.5.9

-- Started on 2017-10-14 14:22:48 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2293 (class 1262 OID 25568)
-- Name: zlvpTest; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE "zlvpTest" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'de_DE.UTF-8' LC_CTYPE = 'de_DE.UTF-8';


\connect "zlvp"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 11861)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2295 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 25569)
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
    geschlecht integer,
    handy text,
    nottel character varying(15)
);


--
-- TOC entry 174 (class 1259 OID 25575)
-- Name: Person_PeID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Person_PeID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2296 (class 0 OID 0)
-- Dependencies: 174
-- Name: Person_PeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Person_PeID_seq" OWNED BY persont.peid;


--
-- TOC entry 175 (class 1259 OID 25577)
-- Name: anrede; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE anrede (
    anid integer NOT NULL,
    anrede character varying(10)
);


--
-- TOC entry 176 (class 1259 OID 25580)
-- Name: anrede_anid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE anrede_anid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2297 (class 0 OID 0)
-- Dependencies: 176
-- Name: anrede_anid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE anrede_anid_seq OWNED BY anrede.anid;


--
-- TOC entry 177 (class 1259 OID 25582)
-- Name: essen; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE essen (
    esid integer NOT NULL,
    lager integer,
    datum date,
    morgen text,
    mittag text,
    abend text
);


--
-- TOC entry 178 (class 1259 OID 25588)
-- Name: essen_EsID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "essen_EsID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2298 (class 0 OID 0)
-- Dependencies: 178
-- Name: essen_EsID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "essen_EsID_seq" OWNED BY essen.esid;


--
-- TOC entry 179 (class 1259 OID 25590)
-- Name: funktion; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE funktion (
    fuid integer NOT NULL,
    name character varying(15)
);


--
-- TOC entry 180 (class 1259 OID 25593)
-- Name: funktionen_fuid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE funktionen_fuid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2299 (class 0 OID 0)
-- Dependencies: 180
-- Name: funktionen_fuid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE funktionen_fuid_seq OWNED BY funktion.fuid;


--
-- TOC entry 181 (class 1259 OID 25595)
-- Name: geschlecht; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE geschlecht (
    geid integer NOT NULL,
    name character varying(10) NOT NULL
);


--
-- TOC entry 182 (class 1259 OID 25598)
-- Name: geschlecht_geid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE geschlecht_geid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2300 (class 0 OID 0)
-- Dependencies: 182
-- Name: geschlecht_geid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE geschlecht_geid_seq OWNED BY geschlecht.geid;


--
-- TOC entry 183 (class 1259 OID 25600)
-- Name: gruppe; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE gruppe (
    grid integer NOT NULL,
    name text,
    schlachtruf text
);


--
-- TOC entry 184 (class 1259 OID 25606)
-- Name: gruppe_GrID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "gruppe_GrID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2301 (class 0 OID 0)
-- Dependencies: 184
-- Name: gruppe_GrID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "gruppe_GrID_seq" OWNED BY gruppe.grid;


--
-- TOC entry 185 (class 1259 OID 25608)
-- Name: jahr; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE jahr (
    jaid integer NOT NULL,
    jahr integer
);


--
-- TOC entry 186 (class 1259 OID 25611)
-- Name: jahr_JaID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "jahr_JaID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2302 (class 0 OID 0)
-- Dependencies: 186
-- Name: jahr_JaID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "jahr_JaID_seq" OWNED BY jahr.jaid;


--
-- TOC entry 187 (class 1259 OID 25613)
-- Name: lager; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lager (
    laid integer NOT NULL,
    name text,
    thema text,
    ort text,
    datumstart date,
    datumstop date
);


--
-- TOC entry 188 (class 1259 OID 25619)
-- Name: lager_LaID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "lager_LaID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2303 (class 0 OID 0)
-- Dependencies: 188
-- Name: lager_LaID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "lager_LaID_seq" OWNED BY lager.laid;


--
-- TOC entry 189 (class 1259 OID 25621)
-- Name: lagerinfo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lagerinfo (
    liid integer NOT NULL,
    person integer
);


--
-- TOC entry 190 (class 1259 OID 25624)
-- Name: lagerinfo_liid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE lagerinfo_liid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2304 (class 0 OID 0)
-- Dependencies: 190
-- Name: lagerinfo_liid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE lagerinfo_liid_seq OWNED BY lagerinfo.liid;


--
-- TOC entry 191 (class 1259 OID 25626)
-- Name: lagerort; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lagerort (
    loid integer NOT NULL,
    lagerort character varying(25) NOT NULL
);


--
-- TOC entry 192 (class 1259 OID 25629)
-- Name: lagerort_loid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE lagerort_loid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2305 (class 0 OID 0)
-- Dependencies: 192
-- Name: lagerort_loid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE lagerort_loid_seq OWNED BY lagerort.loid;


--
-- TOC entry 193 (class 1259 OID 25631)
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
-- TOC entry 194 (class 1259 OID 25637)
-- Name: legenda_lgid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE legenda_lgid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2306 (class 0 OID 0)
-- Dependencies: 194
-- Name: legenda_lgid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE legenda_lgid_seq OWNED BY legenda.lgid;


--
-- TOC entry 195 (class 1259 OID 25639)
-- Name: legendatyp; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE legendatyp (
    tyid integer NOT NULL,
    typ character varying(20)
);


--
-- TOC entry 196 (class 1259 OID 25642)
-- Name: legendatyp_tyid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE legendatyp_tyid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2307 (class 0 OID 0)
-- Dependencies: 196
-- Name: legendatyp_tyid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE legendatyp_tyid_seq OWNED BY legendatyp.tyid;


--
-- TOC entry 197 (class 1259 OID 25644)
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
-- TOC entry 198 (class 1259 OID 25649)
-- Name: programm; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE programm (
    prid integer NOT NULL,
    lager integer,
    datum date,
    vormittag text,
    nachmittag text,
    nacht text
);


--
-- TOC entry 199 (class 1259 OID 25655)
-- Name: programm_PrID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "programm_PrID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2308 (class 0 OID 0)
-- Dependencies: 199
-- Name: programm_PrID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "programm_PrID_seq" OWNED BY programm.prid;


--
-- TOC entry 200 (class 1259 OID 25657)
-- Name: schaeden; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE schaeden (
    scid integer NOT NULL,
    zeid integer,
    bezeichnung text,
    datum date
);


--
-- TOC entry 201 (class 1259 OID 25663)
-- Name: schäden_scid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "schäden_scid_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2309 (class 0 OID 0)
-- Dependencies: 201
-- Name: schäden_scid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "schäden_scid_seq" OWNED BY schaeden.scid;


--
-- TOC entry 202 (class 1259 OID 25665)
-- Name: stgrlat; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stgrlat (
    lager integer,
    gruppe integer
);


--
-- TOC entry 203 (class 1259 OID 25668)
-- Name: stlaja; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlaja (
    lager integer,
    jahr integer
);


--
-- TOC entry 222 (class 1259 OID 27224)
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
-- TOC entry 204 (class 1259 OID 25678)
-- Name: stgrle; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stgrle (
    person integer,
    gruppe integer
);


--
-- TOC entry 205 (class 1259 OID 25683)
-- Name: stgrze; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stgrze (
    gruppe integer,
    zelt integer
);


--
-- TOC entry 206 (class 1259 OID 25690)
-- Name: stlalo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlalo (
    lager integer,
    lagerort integer
);


--
-- TOC entry 207 (class 1259 OID 25695)
-- Name: stlama; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlama (
    person_id integer NOT NULL,
    lager_id integer NOT NULL
);


--
-- TOC entry 208 (class 1259 OID 25700)
-- Name: stlast; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlast (
    lager integer NOT NULL,
    person integer NOT NULL,
    funktion integer NOT NULL
);


--
-- TOC entry 209 (class 1259 OID 25705)
-- Name: stlaze; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlaze (
    lager integer,
    zelt integer
);


--
-- TOC entry 210 (class 1259 OID 25710)
-- Name: stlolg; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlolg (
    lagerort integer,
    legenda integer
);


--
-- TOC entry 211 (class 1259 OID 25715)
-- Name: sttegr; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE sttegr (
    person integer,
    gruppe integer
);


--
-- TOC entry 212 (class 1259 OID 25720)
-- Name: waehrung; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE waehrung (
    waid integer NOT NULL,
    waehrung character varying(5)
);


--
-- TOC entry 213 (class 1259 OID 25723)
-- Name: waehrung_waid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE waehrung_waid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2310 (class 0 OID 0)
-- Dependencies: 213
-- Name: waehrung_waid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE waehrung_waid_seq OWNED BY waehrung.waid;


--
-- TOC entry 214 (class 1259 OID 25725)
-- Name: zdbez; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE zdbez (
    zdbid integer NOT NULL,
    bezeichnung text
);


--
-- TOC entry 215 (class 1259 OID 25731)
-- Name: zdbez_zdbid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE zdbez_zdbid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2311 (class 0 OID 0)
-- Dependencies: 215
-- Name: zdbez_zdbid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE zdbez_zdbid_seq OWNED BY zdbez.zdbid;


--
-- TOC entry 216 (class 1259 OID 25733)
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
-- TOC entry 217 (class 1259 OID 25739)
-- Name: zelt_ZeID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "zelt_ZeID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2312 (class 0 OID 0)
-- Dependencies: 217
-- Name: zelt_ZeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "zelt_ZeID_seq" OWNED BY zelt.zeid;


--
-- TOC entry 218 (class 1259 OID 25741)
-- Name: zeltdetail; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE zeltdetail (
    zdid integer NOT NULL,
    anzahl integer,
    zelt integer,
    bezeichnung integer,
    "schlüssel" character varying(20)
);


--
-- TOC entry 219 (class 1259 OID 25744)
-- Name: zeltdetail_zdid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE zeltdetail_zdid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2313 (class 0 OID 0)
-- Dependencies: 219
-- Name: zeltdetail_zdid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE zeltdetail_zdid_seq OWNED BY zeltdetail.zdid;


--
-- TOC entry 220 (class 1259 OID 25746)
-- Name: zverleih; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE zverleih (
    zvid integer NOT NULL,
    datum date,
    person text,
    bemerkung text,
    ze integer
);


--
-- TOC entry 221 (class 1259 OID 25752)
-- Name: zverleih_zvid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE zverleih_zvid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2314 (class 0 OID 0)
-- Dependencies: 221
-- Name: zverleih_zvid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE zverleih_zvid_seq OWNED BY zverleih.zvid;


--
-- TOC entry 2052 (class 2604 OID 25754)
-- Name: anid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY anrede ALTER COLUMN anid SET DEFAULT nextval('anrede_anid_seq'::regclass);


--
-- TOC entry 2053 (class 2604 OID 25755)
-- Name: esid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY essen ALTER COLUMN esid SET DEFAULT nextval('"essen_EsID_seq"'::regclass);


--
-- TOC entry 2054 (class 2604 OID 25756)
-- Name: fuid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY funktion ALTER COLUMN fuid SET DEFAULT nextval('funktionen_fuid_seq'::regclass);


--
-- TOC entry 2055 (class 2604 OID 25757)
-- Name: geid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY geschlecht ALTER COLUMN geid SET DEFAULT nextval('geschlecht_geid_seq'::regclass);


--
-- TOC entry 2056 (class 2604 OID 25758)
-- Name: grid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY gruppe ALTER COLUMN grid SET DEFAULT nextval('"gruppe_GrID_seq"'::regclass);


--
-- TOC entry 2057 (class 2604 OID 25759)
-- Name: jaid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY jahr ALTER COLUMN jaid SET DEFAULT nextval('"jahr_JaID_seq"'::regclass);


--
-- TOC entry 2058 (class 2604 OID 25760)
-- Name: laid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lager ALTER COLUMN laid SET DEFAULT nextval('"lager_LaID_seq"'::regclass);


--
-- TOC entry 2059 (class 2604 OID 25761)
-- Name: liid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerinfo ALTER COLUMN liid SET DEFAULT nextval('lagerinfo_liid_seq'::regclass);


--
-- TOC entry 2060 (class 2604 OID 25762)
-- Name: loid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerort ALTER COLUMN loid SET DEFAULT nextval('lagerort_loid_seq'::regclass);


--
-- TOC entry 2061 (class 2604 OID 25763)
-- Name: lgid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda ALTER COLUMN lgid SET DEFAULT nextval('legenda_lgid_seq'::regclass);


--
-- TOC entry 2062 (class 2604 OID 25764)
-- Name: tyid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY legendatyp ALTER COLUMN tyid SET DEFAULT nextval('legendatyp_tyid_seq'::regclass);


--
-- TOC entry 2051 (class 2604 OID 25765)
-- Name: peid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont ALTER COLUMN peid SET DEFAULT nextval('"Person_PeID_seq"'::regclass);


--
-- TOC entry 2063 (class 2604 OID 25766)
-- Name: prid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY programm ALTER COLUMN prid SET DEFAULT nextval('"programm_PrID_seq"'::regclass);


--
-- TOC entry 2064 (class 2604 OID 25767)
-- Name: scid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY schaeden ALTER COLUMN scid SET DEFAULT nextval('"schäden_scid_seq"'::regclass);


--
-- TOC entry 2065 (class 2604 OID 25778)
-- Name: waid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY waehrung ALTER COLUMN waid SET DEFAULT nextval('waehrung_waid_seq'::regclass);


--
-- TOC entry 2066 (class 2604 OID 25779)
-- Name: zdbid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zdbez ALTER COLUMN zdbid SET DEFAULT nextval('zdbez_zdbid_seq'::regclass);


--
-- TOC entry 2067 (class 2604 OID 25780)
-- Name: zeid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt ALTER COLUMN zeid SET DEFAULT nextval('"zelt_ZeID_seq"'::regclass);


--
-- TOC entry 2068 (class 2604 OID 25781)
-- Name: zdid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail ALTER COLUMN zdid SET DEFAULT nextval('zeltdetail_zdid_seq'::regclass);


--
-- TOC entry 2069 (class 2604 OID 25782)
-- Name: zvid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zverleih ALTER COLUMN zvid SET DEFAULT nextval('zverleih_zvid_seq'::regclass);


--
-- TOC entry 2075 (class 2606 OID 25784)
-- Name: anrede_anrede_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY anrede
    ADD CONSTRAINT anrede_anrede_key UNIQUE (anrede);


--
-- TOC entry 2077 (class 2606 OID 25786)
-- Name: anrede_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY anrede
    ADD CONSTRAINT anrede_pkey PRIMARY KEY (anid);


--
-- TOC entry 2079 (class 2606 OID 25788)
-- Name: essen_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY essen
    ADD CONSTRAINT essen_pkey PRIMARY KEY (esid);


--
-- TOC entry 2081 (class 2606 OID 25790)
-- Name: funktionen_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY funktion
    ADD CONSTRAINT funktionen_name_key UNIQUE (name);


--
-- TOC entry 2083 (class 2606 OID 25792)
-- Name: funktionen_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY funktion
    ADD CONSTRAINT funktionen_pkey PRIMARY KEY (fuid);


--
-- TOC entry 2085 (class 2606 OID 25794)
-- Name: geschlecht_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geschlecht
    ADD CONSTRAINT geschlecht_pkey PRIMARY KEY (geid);


--
-- TOC entry 2087 (class 2606 OID 25796)
-- Name: gruppe_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gruppe
    ADD CONSTRAINT gruppe_name_key UNIQUE (name);


--
-- TOC entry 2089 (class 2606 OID 25798)
-- Name: gruppe_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gruppe
    ADD CONSTRAINT gruppe_pkey PRIMARY KEY (grid);


--
-- TOC entry 2091 (class 2606 OID 25800)
-- Name: jahr_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY jahr
    ADD CONSTRAINT jahr_pkey PRIMARY KEY (jaid);


--
-- TOC entry 2093 (class 2606 OID 25802)
-- Name: lager_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lager
    ADD CONSTRAINT lager_pkey PRIMARY KEY (laid);


--
-- TOC entry 2095 (class 2606 OID 25804)
-- Name: lagerinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerinfo
    ADD CONSTRAINT lagerinfo_pkey PRIMARY KEY (liid);


--
-- TOC entry 2097 (class 2606 OID 25806)
-- Name: lagerort_lagerort_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerort
    ADD CONSTRAINT lagerort_lagerort_key UNIQUE (lagerort);


--
-- TOC entry 2099 (class 2606 OID 25808)
-- Name: lagerort_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerort
    ADD CONSTRAINT lagerort_pkey PRIMARY KEY (loid);


--
-- TOC entry 2101 (class 2606 OID 25810)
-- Name: legenda_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_pkey PRIMARY KEY (lgid);


--
-- TOC entry 2103 (class 2606 OID 25812)
-- Name: legenda_typ_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_typ_key UNIQUE (typ, name, firma);


--
-- TOC entry 2105 (class 2606 OID 25814)
-- Name: legendatyp_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legendatyp
    ADD CONSTRAINT legendatyp_pkey PRIMARY KEY (tyid);


--
-- TOC entry 2107 (class 2606 OID 25816)
-- Name: legendatyp_typ_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legendatyp
    ADD CONSTRAINT legendatyp_typ_key UNIQUE (typ);


--
-- TOC entry 2071 (class 2606 OID 25818)
-- Name: person_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont
    ADD CONSTRAINT person_pkey PRIMARY KEY (peid);


--
-- TOC entry 2073 (class 2606 OID 25820)
-- Name: persont_vorname_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont
    ADD CONSTRAINT persont_vorname_key UNIQUE (vorname, nachname, gebdat);


--
-- TOC entry 2109 (class 2606 OID 25822)
-- Name: programm_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programm
    ADD CONSTRAINT programm_pkey PRIMARY KEY (prid);


--
-- TOC entry 2111 (class 2606 OID 25824)
-- Name: schäden_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY schaeden
    ADD CONSTRAINT "schäden_pkey" PRIMARY KEY (scid);


--
-- TOC entry 2113 (class 2606 OID 25828)
-- Name: stgrlat_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT stgrlat_lager_key UNIQUE (lager, gruppe);


--
-- TOC entry 2117 (class 2606 OID 25830)
-- Name: stgrle_person_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT stgrle_person_key UNIQUE (person, gruppe);


--
-- TOC entry 2119 (class 2606 OID 25834)
-- Name: stgrze_gruppe_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT stgrze_gruppe_key UNIQUE (gruppe, zelt);


--
-- TOC entry 2115 (class 2606 OID 25838)
-- Name: stlaja_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT stlaja_lager_key UNIQUE (lager, jahr);


--
-- TOC entry 2121 (class 2606 OID 25842)
-- Name: stlalo_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT stlalo_lager_key UNIQUE (lager, lagerort);


--
-- TOC entry 2123 (class 2606 OID 27230)
-- Name: stlama_primary_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT stlama_primary_key PRIMARY KEY (person_id, lager_id);


--
-- TOC entry 2125 (class 2606 OID 25850)
-- Name: stlast_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT stlast_lager_key UNIQUE (lager, person);


--
-- TOC entry 2127 (class 2606 OID 25854)
-- Name: stlaze_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT stlaze_lager_key UNIQUE (lager, zelt);


--
-- TOC entry 2129 (class 2606 OID 25858)
-- Name: stlolg_lagerort_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlolg
    ADD CONSTRAINT stlolg_lagerort_key UNIQUE (lagerort, legenda);


--
-- TOC entry 2131 (class 2606 OID 25862)
-- Name: sttegr_person_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT sttegr_person_key UNIQUE (person, gruppe);


--
-- TOC entry 2133 (class 2606 OID 25866)
-- Name: waehrung_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY waehrung
    ADD CONSTRAINT waehrung_pkey PRIMARY KEY (waid);


--
-- TOC entry 2135 (class 2606 OID 25868)
-- Name: zdbez_bezeichnung_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zdbez
    ADD CONSTRAINT zdbez_bezeichnung_key UNIQUE (bezeichnung);


--
-- TOC entry 2137 (class 2606 OID 25870)
-- Name: zdbez_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zdbez
    ADD CONSTRAINT zdbez_pkey PRIMARY KEY (zdbid);


--
-- TOC entry 2139 (class 2606 OID 25872)
-- Name: zelt_bezeichnung_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt
    ADD CONSTRAINT zelt_bezeichnung_key UNIQUE (bezeichnung);


--
-- TOC entry 2141 (class 2606 OID 25874)
-- Name: zelt_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt
    ADD CONSTRAINT zelt_pkey PRIMARY KEY (zeid);


--
-- TOC entry 2143 (class 2606 OID 25876)
-- Name: zeltdetail_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail
    ADD CONSTRAINT zeltdetail_pkey PRIMARY KEY (zdid);


--
-- TOC entry 2145 (class 2606 OID 25878)
-- Name: zverleih_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zverleih
    ADD CONSTRAINT zverleih_pkey PRIMARY KEY (zvid);


--
-- TOC entry 2147 (class 2606 OID 25879)
-- Name: essen_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY essen
    ADD CONSTRAINT "essen_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2148 (class 2606 OID 25884)
-- Name: lagerinfo_person_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerinfo
    ADD CONSTRAINT lagerinfo_person_fkey FOREIGN KEY (person) REFERENCES persont(peid);


--
-- TOC entry 2149 (class 2606 OID 25889)
-- Name: legenda_anrede_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_anrede_fkey FOREIGN KEY (anrede) REFERENCES anrede(anid);


--
-- TOC entry 2151 (class 2606 OID 27214)
-- Name: legenda_lagerort_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_lagerort_fkey FOREIGN KEY (lagerort_id) REFERENCES lagerort(loid);


--
-- TOC entry 2150 (class 2606 OID 25894)
-- Name: legenda_typ_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_typ_fkey FOREIGN KEY (typ) REFERENCES legendatyp(tyid);


--
-- TOC entry 2146 (class 2606 OID 25899)
-- Name: person_geschlecht_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont
    ADD CONSTRAINT person_geschlecht_fkey FOREIGN KEY (geschlecht) REFERENCES geschlecht(geid);


--
-- TOC entry 2152 (class 2606 OID 25904)
-- Name: programm_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programm
    ADD CONSTRAINT "programm_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2153 (class 2606 OID 25909)
-- Name: schäden_zeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY schaeden
    ADD CONSTRAINT "schäden_zeid_fkey" FOREIGN KEY (zeid) REFERENCES zelt(zeid);


--
-- TOC entry 2154 (class 2606 OID 25914)
-- Name: stgrla_Gruppe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT "stgrla_Gruppe_fkey" FOREIGN KEY (gruppe) REFERENCES gruppe(grid);


--
-- TOC entry 2155 (class 2606 OID 25919)
-- Name: stgrla_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT "stgrla_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2158 (class 2606 OID 25924)
-- Name: stgrle_Gruppe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT "stgrle_Gruppe_fkey" FOREIGN KEY (gruppe) REFERENCES gruppe(grid);


--
-- TOC entry 2159 (class 2606 OID 25929)
-- Name: stgrle_person_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT stgrle_person_fkey FOREIGN KEY (person) REFERENCES persont(peid);


--
-- TOC entry 2160 (class 2606 OID 25934)
-- Name: stgrze_gruppe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT stgrze_gruppe_fkey FOREIGN KEY (gruppe) REFERENCES gruppe(grid);


--
-- TOC entry 2161 (class 2606 OID 25939)
-- Name: stgrze_zelt_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT stgrze_zelt_fkey FOREIGN KEY (zelt) REFERENCES zelt(zeid);


--
-- TOC entry 2156 (class 2606 OID 25944)
-- Name: stlaja_Jahr_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT "stlaja_Jahr_fkey" FOREIGN KEY (jahr) REFERENCES jahr(jaid);


--
-- TOC entry 2157 (class 2606 OID 25949)
-- Name: stlaja_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT "stlaja_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2162 (class 2606 OID 25954)
-- Name: stlalo_lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT stlalo_lager_fkey FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2163 (class 2606 OID 25959)
-- Name: stlalo_lagerort_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT stlalo_lagerort_fkey FOREIGN KEY (lagerort) REFERENCES lagerort(loid);


--
-- TOC entry 2164 (class 2606 OID 25964)
-- Name: stlama_la_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT stlama_la_fkey FOREIGN KEY (lager_id) REFERENCES lager(laid);


--
-- TOC entry 2165 (class 2606 OID 25969)
-- Name: stlama_pe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT stlama_pe_fkey FOREIGN KEY (person_id) REFERENCES persont(peid);


--
-- TOC entry 2166 (class 2606 OID 25974)
-- Name: stlast_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT "stlast_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2167 (class 2606 OID 25979)
-- Name: stlast_funktion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT stlast_funktion_fkey FOREIGN KEY (funktion) REFERENCES funktion(fuid);


--
-- TOC entry 2168 (class 2606 OID 25984)
-- Name: stlast_person_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT stlast_person_fkey FOREIGN KEY (person) REFERENCES persont(peid);


--
-- TOC entry 2169 (class 2606 OID 25989)
-- Name: stlaze_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT "stlaze_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2170 (class 2606 OID 25994)
-- Name: stlaze_Zelt_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT "stlaze_Zelt_fkey" FOREIGN KEY (zelt) REFERENCES zelt(zeid);


--
-- TOC entry 2171 (class 2606 OID 25999)
-- Name: stlolg_legenda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlolg
    ADD CONSTRAINT stlolg_legenda_fkey FOREIGN KEY (legenda) REFERENCES legenda(lgid);


--
-- TOC entry 2172 (class 2606 OID 26004)
-- Name: sttegr_Gruppe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT "sttegr_Gruppe_fkey" FOREIGN KEY (gruppe) REFERENCES gruppe(grid);


--
-- TOC entry 2173 (class 2606 OID 26009)
-- Name: sttegr_person_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT sttegr_person_fkey FOREIGN KEY (person) REFERENCES persont(peid);


--
-- TOC entry 2174 (class 2606 OID 26014)
-- Name: zelt_waehrung_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt
    ADD CONSTRAINT zelt_waehrung_fkey FOREIGN KEY (waehrung) REFERENCES waehrung(waid);


--
-- TOC entry 2175 (class 2606 OID 26019)
-- Name: zeltdetail_bezeichnung_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail
    ADD CONSTRAINT zeltdetail_bezeichnung_fkey FOREIGN KEY (bezeichnung) REFERENCES zdbez(zdbid);


--
-- TOC entry 2176 (class 2606 OID 26024)
-- Name: zeltdetail_zelt_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail
    ADD CONSTRAINT zeltdetail_zelt_fkey FOREIGN KEY (zelt) REFERENCES zelt(zeid);


--
-- TOC entry 2177 (class 2606 OID 26029)
-- Name: zverleih_ze_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zverleih
    ADD CONSTRAINT zverleih_ze_fkey FOREIGN KEY (ze) REFERENCES zelt(zeid);


-- Completed on 2017-10-14 14:23:08 CEST

--
-- PostgreSQL database dump complete
--

