--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.12
-- Dumped by pg_dump version 9.5.7

-- Started on 2017-08-30 08:45:42 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2342 (class 1262 OID 16889)
-- Name: zlvp; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE zlvp WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'de_DE.UTF-8' LC_CTYPE = 'de_DE.UTF-8';


\connect zlvp

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
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 173 (class 1259 OID 16890)
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
-- TOC entry 174 (class 1259 OID 16898)
-- Name: Person_PeID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "Person_PeID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 174
-- Name: Person_PeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "Person_PeID_seq" OWNED BY persont.peid;


--
-- TOC entry 175 (class 1259 OID 16900)
-- Name: anrede; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE anrede (
    anid integer NOT NULL,
    anrede character varying(10)
);


--
-- TOC entry 176 (class 1259 OID 16903)
-- Name: anrede_anid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE anrede_anid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 176
-- Name: anrede_anid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE anrede_anid_seq OWNED BY anrede.anid;


--
-- TOC entry 177 (class 1259 OID 16905)
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
-- TOC entry 178 (class 1259 OID 16911)
-- Name: essen_EsID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "essen_EsID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2347 (class 0 OID 0)
-- Dependencies: 178
-- Name: essen_EsID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "essen_EsID_seq" OWNED BY essen.esid;


--
-- TOC entry 179 (class 1259 OID 16913)
-- Name: funktion; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE funktion (
    fuid integer NOT NULL,
    name character varying(15)
);


--
-- TOC entry 180 (class 1259 OID 16916)
-- Name: funktionen_fuid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE funktionen_fuid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2348 (class 0 OID 0)
-- Dependencies: 180
-- Name: funktionen_fuid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE funktionen_fuid_seq OWNED BY funktion.fuid;


--
-- TOC entry 181 (class 1259 OID 16918)
-- Name: geschlecht; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE geschlecht (
    geid integer NOT NULL,
    name character varying(10) NOT NULL
);


--
-- TOC entry 182 (class 1259 OID 16921)
-- Name: geschlecht_geid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE geschlecht_geid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2349 (class 0 OID 0)
-- Dependencies: 182
-- Name: geschlecht_geid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE geschlecht_geid_seq OWNED BY geschlecht.geid;


--
-- TOC entry 183 (class 1259 OID 16923)
-- Name: gruppe; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE gruppe (
    grid integer NOT NULL,
    name text,
    schlachtruf text
);


--
-- TOC entry 184 (class 1259 OID 16929)
-- Name: gruppe_GrID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "gruppe_GrID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2350 (class 0 OID 0)
-- Dependencies: 184
-- Name: gruppe_GrID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "gruppe_GrID_seq" OWNED BY gruppe.grid;


--
-- TOC entry 185 (class 1259 OID 16931)
-- Name: jahr; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE jahr (
    jaid integer NOT NULL,
    jahr integer
);


--
-- TOC entry 186 (class 1259 OID 16934)
-- Name: jahr_JaID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "jahr_JaID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2351 (class 0 OID 0)
-- Dependencies: 186
-- Name: jahr_JaID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "jahr_JaID_seq" OWNED BY jahr.jaid;


--
-- TOC entry 187 (class 1259 OID 16936)
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
-- TOC entry 188 (class 1259 OID 16942)
-- Name: lager_LaID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "lager_LaID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2352 (class 0 OID 0)
-- Dependencies: 188
-- Name: lager_LaID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "lager_LaID_seq" OWNED BY lager.laid;


--
-- TOC entry 189 (class 1259 OID 16944)
-- Name: lagerinfo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lagerinfo (
    liid integer NOT NULL,
    person integer
);


--
-- TOC entry 190 (class 1259 OID 16947)
-- Name: lagerinfo_liid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE lagerinfo_liid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2353 (class 0 OID 0)
-- Dependencies: 190
-- Name: lagerinfo_liid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE lagerinfo_liid_seq OWNED BY lagerinfo.liid;


--
-- TOC entry 191 (class 1259 OID 16949)
-- Name: lagerort; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE lagerort (
    loid integer NOT NULL,
    lagerort character varying(25) NOT NULL
);


--
-- TOC entry 192 (class 1259 OID 16952)
-- Name: lagerort_loid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE lagerort_loid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2354 (class 0 OID 0)
-- Dependencies: 192
-- Name: lagerort_loid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE lagerort_loid_seq OWNED BY lagerort.loid;


--
-- TOC entry 193 (class 1259 OID 16954)
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
    bemerkung text
);


--
-- TOC entry 194 (class 1259 OID 16960)
-- Name: legenda_lgid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE legenda_lgid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2355 (class 0 OID 0)
-- Dependencies: 194
-- Name: legenda_lgid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE legenda_lgid_seq OWNED BY legenda.lgid;


--
-- TOC entry 195 (class 1259 OID 16962)
-- Name: legendatyp; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE legendatyp (
    tyid integer NOT NULL,
    typ character varying(20)
);


--
-- TOC entry 196 (class 1259 OID 16965)
-- Name: legendatyp_tyid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE legendatyp_tyid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2356 (class 0 OID 0)
-- Dependencies: 196
-- Name: legendatyp_tyid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE legendatyp_tyid_seq OWNED BY legendatyp.tyid;


--
-- TOC entry 232 (class 1259 OID 17405)
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
-- TOC entry 197 (class 1259 OID 16972)
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
-- TOC entry 198 (class 1259 OID 16978)
-- Name: programm_PrID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "programm_PrID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2357 (class 0 OID 0)
-- Dependencies: 198
-- Name: programm_PrID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "programm_PrID_seq" OWNED BY programm.prid;


--
-- TOC entry 199 (class 1259 OID 16980)
-- Name: schaeden; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE schaeden (
    scid integer NOT NULL,
    zeid integer,
    bezeichnung text,
    datum date
);


--
-- TOC entry 200 (class 1259 OID 16986)
-- Name: schäden_scid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "schäden_scid_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2358 (class 0 OID 0)
-- Dependencies: 200
-- Name: schäden_scid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "schäden_scid_seq" OWNED BY schaeden.scid;


--
-- TOC entry 201 (class 1259 OID 16988)
-- Name: stgrlat; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stgrlat (
    lager integer,
    gruppe integer,
    stgrlaid integer NOT NULL
);


--
-- TOC entry 202 (class 1259 OID 16991)
-- Name: stlaja; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlaja (
    lager integer,
    jahr integer,
    stlajaid integer NOT NULL
);


--
-- TOC entry 203 (class 1259 OID 16994)
-- Name: stgrla; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW stgrla AS
 SELECT stgrlat.gruppe,
    stgrlat.stgrlaid,
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
-- TOC entry 204 (class 1259 OID 16999)
-- Name: stgrla_stGrLaID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "stgrla_stGrLaID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2359 (class 0 OID 0)
-- Dependencies: 204
-- Name: stgrla_stGrLaID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "stgrla_stGrLaID_seq" OWNED BY stgrlat.stgrlaid;


--
-- TOC entry 205 (class 1259 OID 17001)
-- Name: stgrle; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stgrle (
    stgrle integer NOT NULL,
    person integer,
    gruppe integer
);


--
-- TOC entry 206 (class 1259 OID 17004)
-- Name: stgrle_stGrLe_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "stgrle_stGrLe_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2360 (class 0 OID 0)
-- Dependencies: 206
-- Name: stgrle_stGrLe_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "stgrle_stGrLe_seq" OWNED BY stgrle.stgrle;


--
-- TOC entry 207 (class 1259 OID 17006)
-- Name: stgrze; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stgrze (
    stgrzeid integer NOT NULL,
    gruppe integer,
    zelt integer
);


--
-- TOC entry 208 (class 1259 OID 17009)
-- Name: stgrze_stgrzeid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE stgrze_stgrzeid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2361 (class 0 OID 0)
-- Dependencies: 208
-- Name: stgrze_stgrzeid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE stgrze_stgrzeid_seq OWNED BY stgrze.stgrzeid;


--
-- TOC entry 209 (class 1259 OID 17011)
-- Name: stlaja_stLaJaID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "stlaja_stLaJaID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2362 (class 0 OID 0)
-- Dependencies: 209
-- Name: stlaja_stLaJaID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "stlaja_stLaJaID_seq" OWNED BY stlaja.stlajaid;


--
-- TOC entry 210 (class 1259 OID 17013)
-- Name: stlalo; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlalo (
    stlaloid integer NOT NULL,
    lager integer,
    lagerort integer
);


--
-- TOC entry 211 (class 1259 OID 17016)
-- Name: stlalo_stlaloid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE stlalo_stlaloid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2363 (class 0 OID 0)
-- Dependencies: 211
-- Name: stlalo_stlaloid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE stlalo_stlaloid_seq OWNED BY stlalo.stlaloid;


--
-- TOC entry 212 (class 1259 OID 17018)
-- Name: stlama; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlama (
    stlamaid integer NOT NULL,
    pe integer,
    la integer
);


--
-- TOC entry 213 (class 1259 OID 17021)
-- Name: stlama_stlamaid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE stlama_stlamaid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2364 (class 0 OID 0)
-- Dependencies: 213
-- Name: stlama_stlamaid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE stlama_stlamaid_seq OWNED BY stlama.stlamaid;


--
-- TOC entry 214 (class 1259 OID 17023)
-- Name: stlast; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlast (
    stlastid integer NOT NULL,
    lager integer NOT NULL,
    person integer NOT NULL,
    funktion integer NOT NULL
);


--
-- TOC entry 215 (class 1259 OID 17026)
-- Name: stlast_stLaStID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "stlast_stLaStID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2365 (class 0 OID 0)
-- Dependencies: 215
-- Name: stlast_stLaStID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "stlast_stLaStID_seq" OWNED BY stlast.stlastid;


--
-- TOC entry 216 (class 1259 OID 17028)
-- Name: stlaze; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlaze (
    stlazeid integer NOT NULL,
    lager integer,
    zelt integer
);


--
-- TOC entry 2366 (class 0 OID 0)
-- Dependencies: 216
-- Name: COLUMN stlaze.stlazeid; Type: COMMENT; Schema: public; Owner: -
--

COMMENT ON COLUMN stlaze.stlazeid IS '
';


--
-- TOC entry 217 (class 1259 OID 17031)
-- Name: stlaze_stLaZeID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "stlaze_stLaZeID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2367 (class 0 OID 0)
-- Dependencies: 217
-- Name: stlaze_stLaZeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "stlaze_stLaZeID_seq" OWNED BY stlaze.stlazeid;


--
-- TOC entry 218 (class 1259 OID 17033)
-- Name: stlolg; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE stlolg (
    stlolgid integer NOT NULL,
    lagerort integer,
    legenda integer
);


--
-- TOC entry 219 (class 1259 OID 17036)
-- Name: stlolg_stlolgid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE stlolg_stlolgid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2368 (class 0 OID 0)
-- Dependencies: 219
-- Name: stlolg_stlolgid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE stlolg_stlolgid_seq OWNED BY stlolg.stlolgid;


--
-- TOC entry 220 (class 1259 OID 17038)
-- Name: sttegr; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE sttegr (
    person integer,
    gruppe integer,
    sttegrid integer NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 17041)
-- Name: sttegr_stTeGrID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "sttegr_stTeGrID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2369 (class 0 OID 0)
-- Dependencies: 221
-- Name: sttegr_stTeGrID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "sttegr_stTeGrID_seq" OWNED BY sttegr.sttegrid;


--
-- TOC entry 222 (class 1259 OID 17043)
-- Name: waehrung; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE waehrung (
    waid integer NOT NULL,
    waehrung character varying(5)
);


--
-- TOC entry 223 (class 1259 OID 17046)
-- Name: waehrung_waid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE waehrung_waid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2370 (class 0 OID 0)
-- Dependencies: 223
-- Name: waehrung_waid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE waehrung_waid_seq OWNED BY waehrung.waid;


--
-- TOC entry 224 (class 1259 OID 17048)
-- Name: zdbez; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE zdbez (
    zdbid integer NOT NULL,
    bezeichnung text
);


--
-- TOC entry 225 (class 1259 OID 17054)
-- Name: zdbez_zdbid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE zdbez_zdbid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2371 (class 0 OID 0)
-- Dependencies: 225
-- Name: zdbez_zdbid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE zdbez_zdbid_seq OWNED BY zdbez.zdbid;


--
-- TOC entry 226 (class 1259 OID 17056)
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
-- TOC entry 227 (class 1259 OID 17062)
-- Name: zelt_ZeID_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE "zelt_ZeID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2372 (class 0 OID 0)
-- Dependencies: 227
-- Name: zelt_ZeID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE "zelt_ZeID_seq" OWNED BY zelt.zeid;


--
-- TOC entry 228 (class 1259 OID 17064)
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
-- TOC entry 229 (class 1259 OID 17067)
-- Name: zeltdetail_zdid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE zeltdetail_zdid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2373 (class 0 OID 0)
-- Dependencies: 229
-- Name: zeltdetail_zdid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE zeltdetail_zdid_seq OWNED BY zeltdetail.zdid;


--
-- TOC entry 230 (class 1259 OID 17069)
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
-- TOC entry 231 (class 1259 OID 17075)
-- Name: zverleih_zvid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE zverleih_zvid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2374 (class 0 OID 0)
-- Dependencies: 231
-- Name: zverleih_zvid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE zverleih_zvid_seq OWNED BY zverleih.zvid;


--
-- TOC entry 2072 (class 2604 OID 17077)
-- Name: anid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY anrede ALTER COLUMN anid SET DEFAULT nextval('anrede_anid_seq'::regclass);


--
-- TOC entry 2073 (class 2604 OID 17078)
-- Name: esid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY essen ALTER COLUMN esid SET DEFAULT nextval('"essen_EsID_seq"'::regclass);


--
-- TOC entry 2074 (class 2604 OID 17079)
-- Name: fuid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY funktion ALTER COLUMN fuid SET DEFAULT nextval('funktionen_fuid_seq'::regclass);


--
-- TOC entry 2075 (class 2604 OID 17080)
-- Name: geid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY geschlecht ALTER COLUMN geid SET DEFAULT nextval('geschlecht_geid_seq'::regclass);


--
-- TOC entry 2076 (class 2604 OID 17081)
-- Name: grid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY gruppe ALTER COLUMN grid SET DEFAULT nextval('"gruppe_GrID_seq"'::regclass);


--
-- TOC entry 2077 (class 2604 OID 17082)
-- Name: jaid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY jahr ALTER COLUMN jaid SET DEFAULT nextval('"jahr_JaID_seq"'::regclass);


--
-- TOC entry 2078 (class 2604 OID 17083)
-- Name: laid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lager ALTER COLUMN laid SET DEFAULT nextval('"lager_LaID_seq"'::regclass);


--
-- TOC entry 2079 (class 2604 OID 17084)
-- Name: liid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerinfo ALTER COLUMN liid SET DEFAULT nextval('lagerinfo_liid_seq'::regclass);


--
-- TOC entry 2080 (class 2604 OID 17085)
-- Name: loid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerort ALTER COLUMN loid SET DEFAULT nextval('lagerort_loid_seq'::regclass);


--
-- TOC entry 2081 (class 2604 OID 17086)
-- Name: lgid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda ALTER COLUMN lgid SET DEFAULT nextval('legenda_lgid_seq'::regclass);


--
-- TOC entry 2082 (class 2604 OID 17087)
-- Name: tyid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY legendatyp ALTER COLUMN tyid SET DEFAULT nextval('legendatyp_tyid_seq'::regclass);


--
-- TOC entry 2071 (class 2604 OID 17088)
-- Name: peid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont ALTER COLUMN peid SET DEFAULT nextval('"Person_PeID_seq"'::regclass);


--
-- TOC entry 2083 (class 2604 OID 17089)
-- Name: prid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY programm ALTER COLUMN prid SET DEFAULT nextval('"programm_PrID_seq"'::regclass);


--
-- TOC entry 2084 (class 2604 OID 17090)
-- Name: scid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY schaeden ALTER COLUMN scid SET DEFAULT nextval('"schäden_scid_seq"'::regclass);


--
-- TOC entry 2085 (class 2604 OID 17091)
-- Name: stgrlaid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat ALTER COLUMN stgrlaid SET DEFAULT nextval('"stgrla_stGrLaID_seq"'::regclass);


--
-- TOC entry 2087 (class 2604 OID 17092)
-- Name: stgrle; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle ALTER COLUMN stgrle SET DEFAULT nextval('"stgrle_stGrLe_seq"'::regclass);


--
-- TOC entry 2088 (class 2604 OID 17093)
-- Name: stgrzeid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze ALTER COLUMN stgrzeid SET DEFAULT nextval('stgrze_stgrzeid_seq'::regclass);


--
-- TOC entry 2086 (class 2604 OID 17094)
-- Name: stlajaid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja ALTER COLUMN stlajaid SET DEFAULT nextval('"stlaja_stLaJaID_seq"'::regclass);


--
-- TOC entry 2089 (class 2604 OID 17095)
-- Name: stlaloid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo ALTER COLUMN stlaloid SET DEFAULT nextval('stlalo_stlaloid_seq'::regclass);


--
-- TOC entry 2090 (class 2604 OID 17096)
-- Name: stlamaid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama ALTER COLUMN stlamaid SET DEFAULT nextval('stlama_stlamaid_seq'::regclass);


--
-- TOC entry 2091 (class 2604 OID 17097)
-- Name: stlastid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast ALTER COLUMN stlastid SET DEFAULT nextval('"stlast_stLaStID_seq"'::regclass);


--
-- TOC entry 2092 (class 2604 OID 17098)
-- Name: stlazeid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze ALTER COLUMN stlazeid SET DEFAULT nextval('"stlaze_stLaZeID_seq"'::regclass);


--
-- TOC entry 2093 (class 2604 OID 17099)
-- Name: stlolgid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlolg ALTER COLUMN stlolgid SET DEFAULT nextval('stlolg_stlolgid_seq'::regclass);


--
-- TOC entry 2094 (class 2604 OID 17100)
-- Name: sttegrid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr ALTER COLUMN sttegrid SET DEFAULT nextval('"sttegr_stTeGrID_seq"'::regclass);


--
-- TOC entry 2095 (class 2604 OID 17101)
-- Name: waid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY waehrung ALTER COLUMN waid SET DEFAULT nextval('waehrung_waid_seq'::regclass);


--
-- TOC entry 2096 (class 2604 OID 17102)
-- Name: zdbid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zdbez ALTER COLUMN zdbid SET DEFAULT nextval('zdbez_zdbid_seq'::regclass);


--
-- TOC entry 2097 (class 2604 OID 17103)
-- Name: zeid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt ALTER COLUMN zeid SET DEFAULT nextval('"zelt_ZeID_seq"'::regclass);


--
-- TOC entry 2098 (class 2604 OID 17104)
-- Name: zdid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail ALTER COLUMN zdid SET DEFAULT nextval('zeltdetail_zdid_seq'::regclass);


--
-- TOC entry 2099 (class 2604 OID 17105)
-- Name: zvid; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY zverleih ALTER COLUMN zvid SET DEFAULT nextval('zverleih_zvid_seq'::regclass);


--
-- TOC entry 2105 (class 2606 OID 17107)
-- Name: anrede_anrede_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY anrede
    ADD CONSTRAINT anrede_anrede_key UNIQUE (anrede);


--
-- TOC entry 2107 (class 2606 OID 17109)
-- Name: anrede_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY anrede
    ADD CONSTRAINT anrede_pkey PRIMARY KEY (anid);


--
-- TOC entry 2109 (class 2606 OID 17111)
-- Name: essen_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY essen
    ADD CONSTRAINT essen_pkey PRIMARY KEY (esid);


--
-- TOC entry 2111 (class 2606 OID 17113)
-- Name: funktionen_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY funktion
    ADD CONSTRAINT funktionen_name_key UNIQUE (name);


--
-- TOC entry 2113 (class 2606 OID 17115)
-- Name: funktionen_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY funktion
    ADD CONSTRAINT funktionen_pkey PRIMARY KEY (fuid);


--
-- TOC entry 2115 (class 2606 OID 17117)
-- Name: geschlecht_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY geschlecht
    ADD CONSTRAINT geschlecht_pkey PRIMARY KEY (geid);


--
-- TOC entry 2117 (class 2606 OID 17119)
-- Name: gruppe_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gruppe
    ADD CONSTRAINT gruppe_name_key UNIQUE (name);


--
-- TOC entry 2119 (class 2606 OID 17121)
-- Name: gruppe_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY gruppe
    ADD CONSTRAINT gruppe_pkey PRIMARY KEY (grid);


--
-- TOC entry 2121 (class 2606 OID 17123)
-- Name: jahr_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY jahr
    ADD CONSTRAINT jahr_pkey PRIMARY KEY (jaid);


--
-- TOC entry 2123 (class 2606 OID 17125)
-- Name: lager_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lager
    ADD CONSTRAINT lager_pkey PRIMARY KEY (laid);


--
-- TOC entry 2125 (class 2606 OID 17127)
-- Name: lagerinfo_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerinfo
    ADD CONSTRAINT lagerinfo_pkey PRIMARY KEY (liid);


--
-- TOC entry 2127 (class 2606 OID 17129)
-- Name: lagerort_lagerort_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerort
    ADD CONSTRAINT lagerort_lagerort_key UNIQUE (lagerort);


--
-- TOC entry 2129 (class 2606 OID 17131)
-- Name: lagerort_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerort
    ADD CONSTRAINT lagerort_pkey PRIMARY KEY (loid);


--
-- TOC entry 2131 (class 2606 OID 17133)
-- Name: legenda_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_pkey PRIMARY KEY (lgid);


--
-- TOC entry 2133 (class 2606 OID 17135)
-- Name: legenda_typ_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_typ_key UNIQUE (typ, name, firma);


--
-- TOC entry 2135 (class 2606 OID 17137)
-- Name: legendatyp_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legendatyp
    ADD CONSTRAINT legendatyp_pkey PRIMARY KEY (tyid);


--
-- TOC entry 2137 (class 2606 OID 17139)
-- Name: legendatyp_typ_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legendatyp
    ADD CONSTRAINT legendatyp_typ_key UNIQUE (typ);


--
-- TOC entry 2101 (class 2606 OID 17141)
-- Name: person_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont
    ADD CONSTRAINT person_pkey PRIMARY KEY (peid);


--
-- TOC entry 2103 (class 2606 OID 17143)
-- Name: persont_vorname_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont
    ADD CONSTRAINT persont_vorname_key UNIQUE (vorname, nachname, gebdat);


--
-- TOC entry 2139 (class 2606 OID 17149)
-- Name: programm_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programm
    ADD CONSTRAINT programm_pkey PRIMARY KEY (prid);


--
-- TOC entry 2141 (class 2606 OID 17151)
-- Name: schäden_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY schaeden
    ADD CONSTRAINT "schäden_pkey" PRIMARY KEY (scid);


--
-- TOC entry 2143 (class 2606 OID 17153)
-- Name: stgrla_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT stgrla_pkey PRIMARY KEY (stgrlaid);


--
-- TOC entry 2145 (class 2606 OID 17155)
-- Name: stgrlat_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT stgrlat_lager_key UNIQUE (lager, gruppe);


--
-- TOC entry 2151 (class 2606 OID 17157)
-- Name: stgrle_person_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT stgrle_person_key UNIQUE (person, gruppe);


--
-- TOC entry 2153 (class 2606 OID 17159)
-- Name: stgrle_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT stgrle_pkey PRIMARY KEY (stgrle);


--
-- TOC entry 2155 (class 2606 OID 17161)
-- Name: stgrze_gruppe_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT stgrze_gruppe_key UNIQUE (gruppe, zelt);


--
-- TOC entry 2157 (class 2606 OID 17163)
-- Name: stgrze_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT stgrze_pkey PRIMARY KEY (stgrzeid);


--
-- TOC entry 2147 (class 2606 OID 17165)
-- Name: stlaja_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT stlaja_lager_key UNIQUE (lager, jahr);


--
-- TOC entry 2149 (class 2606 OID 17167)
-- Name: stlaja_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT stlaja_pkey PRIMARY KEY (stlajaid);


--
-- TOC entry 2159 (class 2606 OID 17169)
-- Name: stlalo_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT stlalo_lager_key UNIQUE (lager, lagerort);


--
-- TOC entry 2161 (class 2606 OID 17171)
-- Name: stlalo_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT stlalo_pkey PRIMARY KEY (stlaloid);


--
-- TOC entry 2163 (class 2606 OID 17173)
-- Name: stlama_pe_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT stlama_pe_key UNIQUE (pe, la);


--
-- TOC entry 2165 (class 2606 OID 17175)
-- Name: stlama_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT stlama_pkey PRIMARY KEY (stlamaid);


--
-- TOC entry 2167 (class 2606 OID 17177)
-- Name: stlast_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT stlast_lager_key UNIQUE (lager, person);


--
-- TOC entry 2169 (class 2606 OID 17179)
-- Name: stlast_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT stlast_pkey PRIMARY KEY (stlastid);


--
-- TOC entry 2171 (class 2606 OID 17181)
-- Name: stlaze_lager_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT stlaze_lager_key UNIQUE (lager, zelt);


--
-- TOC entry 2173 (class 2606 OID 17183)
-- Name: stlaze_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT stlaze_pkey PRIMARY KEY (stlazeid);


--
-- TOC entry 2175 (class 2606 OID 17185)
-- Name: stlolg_lagerort_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlolg
    ADD CONSTRAINT stlolg_lagerort_key UNIQUE (lagerort, legenda);


--
-- TOC entry 2177 (class 2606 OID 17187)
-- Name: stlolg_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlolg
    ADD CONSTRAINT stlolg_pkey PRIMARY KEY (stlolgid);


--
-- TOC entry 2179 (class 2606 OID 17189)
-- Name: sttegr_person_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT sttegr_person_key UNIQUE (person, gruppe);


--
-- TOC entry 2181 (class 2606 OID 17191)
-- Name: sttegr_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT sttegr_pkey PRIMARY KEY (sttegrid);


--
-- TOC entry 2183 (class 2606 OID 17193)
-- Name: waehrung_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY waehrung
    ADD CONSTRAINT waehrung_pkey PRIMARY KEY (waid);


--
-- TOC entry 2185 (class 2606 OID 17195)
-- Name: zdbez_bezeichnung_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zdbez
    ADD CONSTRAINT zdbez_bezeichnung_key UNIQUE (bezeichnung);


--
-- TOC entry 2187 (class 2606 OID 17197)
-- Name: zdbez_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zdbez
    ADD CONSTRAINT zdbez_pkey PRIMARY KEY (zdbid);


--
-- TOC entry 2189 (class 2606 OID 17199)
-- Name: zelt_bezeichnung_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt
    ADD CONSTRAINT zelt_bezeichnung_key UNIQUE (bezeichnung);


--
-- TOC entry 2191 (class 2606 OID 17201)
-- Name: zelt_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt
    ADD CONSTRAINT zelt_pkey PRIMARY KEY (zeid);


--
-- TOC entry 2193 (class 2606 OID 17203)
-- Name: zeltdetail_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail
    ADD CONSTRAINT zeltdetail_pkey PRIMARY KEY (zdid);


--
-- TOC entry 2195 (class 2606 OID 17205)
-- Name: zverleih_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zverleih
    ADD CONSTRAINT zverleih_pkey PRIMARY KEY (zvid);


--
-- TOC entry 2197 (class 2606 OID 17206)
-- Name: essen_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY essen
    ADD CONSTRAINT "essen_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2198 (class 2606 OID 17211)
-- Name: lagerinfo_person_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY lagerinfo
    ADD CONSTRAINT lagerinfo_person_fkey FOREIGN KEY (person) REFERENCES persont(peid);


--
-- TOC entry 2199 (class 2606 OID 17216)
-- Name: legenda_anrede_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_anrede_fkey FOREIGN KEY (anrede) REFERENCES anrede(anid);


--
-- TOC entry 2200 (class 2606 OID 17221)
-- Name: legenda_typ_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY legenda
    ADD CONSTRAINT legenda_typ_fkey FOREIGN KEY (typ) REFERENCES legendatyp(tyid);


--
-- TOC entry 2196 (class 2606 OID 17226)
-- Name: person_geschlecht_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY persont
    ADD CONSTRAINT person_geschlecht_fkey FOREIGN KEY (geschlecht) REFERENCES geschlecht(geid);


--
-- TOC entry 2201 (class 2606 OID 17231)
-- Name: programm_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY programm
    ADD CONSTRAINT "programm_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2202 (class 2606 OID 17236)
-- Name: schäden_zeid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY schaeden
    ADD CONSTRAINT "schäden_zeid_fkey" FOREIGN KEY (zeid) REFERENCES zelt(zeid);


--
-- TOC entry 2203 (class 2606 OID 17241)
-- Name: stgrla_Gruppe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT "stgrla_Gruppe_fkey" FOREIGN KEY (gruppe) REFERENCES gruppe(grid);


--
-- TOC entry 2204 (class 2606 OID 17246)
-- Name: stgrla_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrlat
    ADD CONSTRAINT "stgrla_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2207 (class 2606 OID 17251)
-- Name: stgrle_Gruppe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT "stgrle_Gruppe_fkey" FOREIGN KEY (gruppe) REFERENCES gruppe(grid);


--
-- TOC entry 2208 (class 2606 OID 17256)
-- Name: stgrle_person_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrle
    ADD CONSTRAINT stgrle_person_fkey FOREIGN KEY (person) REFERENCES persont(peid);


--
-- TOC entry 2209 (class 2606 OID 17261)
-- Name: stgrze_gruppe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT stgrze_gruppe_fkey FOREIGN KEY (gruppe) REFERENCES gruppe(grid);


--
-- TOC entry 2210 (class 2606 OID 17266)
-- Name: stgrze_zelt_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stgrze
    ADD CONSTRAINT stgrze_zelt_fkey FOREIGN KEY (zelt) REFERENCES zelt(zeid);


--
-- TOC entry 2205 (class 2606 OID 17271)
-- Name: stlaja_Jahr_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT "stlaja_Jahr_fkey" FOREIGN KEY (jahr) REFERENCES jahr(jaid);


--
-- TOC entry 2206 (class 2606 OID 17276)
-- Name: stlaja_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaja
    ADD CONSTRAINT "stlaja_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2211 (class 2606 OID 17281)
-- Name: stlalo_lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT stlalo_lager_fkey FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2212 (class 2606 OID 17286)
-- Name: stlalo_lagerort_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlalo
    ADD CONSTRAINT stlalo_lagerort_fkey FOREIGN KEY (lagerort) REFERENCES lagerort(loid);


--
-- TOC entry 2213 (class 2606 OID 17291)
-- Name: stlama_la_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT stlama_la_fkey FOREIGN KEY (la) REFERENCES lager(laid);


--
-- TOC entry 2214 (class 2606 OID 17296)
-- Name: stlama_pe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlama
    ADD CONSTRAINT stlama_pe_fkey FOREIGN KEY (pe) REFERENCES persont(peid);


--
-- TOC entry 2215 (class 2606 OID 17301)
-- Name: stlast_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT "stlast_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2216 (class 2606 OID 17306)
-- Name: stlast_funktion_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT stlast_funktion_fkey FOREIGN KEY (funktion) REFERENCES funktion(fuid);


--
-- TOC entry 2217 (class 2606 OID 17311)
-- Name: stlast_person_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlast
    ADD CONSTRAINT stlast_person_fkey FOREIGN KEY (person) REFERENCES persont(peid);


--
-- TOC entry 2218 (class 2606 OID 17316)
-- Name: stlaze_Lager_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT "stlaze_Lager_fkey" FOREIGN KEY (lager) REFERENCES lager(laid);


--
-- TOC entry 2219 (class 2606 OID 17321)
-- Name: stlaze_Zelt_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlaze
    ADD CONSTRAINT "stlaze_Zelt_fkey" FOREIGN KEY (zelt) REFERENCES zelt(zeid);


--
-- TOC entry 2220 (class 2606 OID 17410)
-- Name: stlolg_legenda_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY stlolg
    ADD CONSTRAINT stlolg_legenda_fkey FOREIGN KEY (legenda) REFERENCES legenda(lgid);


--
-- TOC entry 2221 (class 2606 OID 17326)
-- Name: sttegr_Gruppe_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT "sttegr_Gruppe_fkey" FOREIGN KEY (gruppe) REFERENCES gruppe(grid);


--
-- TOC entry 2222 (class 2606 OID 17331)
-- Name: sttegr_person_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sttegr
    ADD CONSTRAINT sttegr_person_fkey FOREIGN KEY (person) REFERENCES persont(peid);


--
-- TOC entry 2223 (class 2606 OID 17336)
-- Name: zelt_waehrung_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zelt
    ADD CONSTRAINT zelt_waehrung_fkey FOREIGN KEY (waehrung) REFERENCES waehrung(waid);


--
-- TOC entry 2224 (class 2606 OID 17341)
-- Name: zeltdetail_bezeichnung_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail
    ADD CONSTRAINT zeltdetail_bezeichnung_fkey FOREIGN KEY (bezeichnung) REFERENCES zdbez(zdbid);


--
-- TOC entry 2225 (class 2606 OID 17346)
-- Name: zeltdetail_zelt_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zeltdetail
    ADD CONSTRAINT zeltdetail_zelt_fkey FOREIGN KEY (zelt) REFERENCES zelt(zeid);


--
-- TOC entry 2226 (class 2606 OID 17351)
-- Name: zverleih_ze_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY zverleih
    ADD CONSTRAINT zverleih_ze_fkey FOREIGN KEY (ze) REFERENCES zelt(zeid);


-- Completed on 2017-08-30 08:45:58 CEST

--
-- PostgreSQL database dump complete
--

