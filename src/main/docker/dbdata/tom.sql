--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10 (Ubuntu 10.10-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.10 (Ubuntu 10.10-0ubuntu0.18.04.1)

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
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: address; Type: TABLE; Schema: public; Owner: tom
--

CREATE TABLE public.address (
    id bigint NOT NULL,
    city character varying(255),
    country character varying(255),
    street character varying(255)
);


ALTER TABLE public.address OWNER TO tom;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: tom
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO tom;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: tom
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO tom;

--
-- Name: person; Type: TABLE; Schema: public; Owner: tom
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    firstname character varying(255),
    lastname character varying(255),
    address_id bigint
);


ALTER TABLE public.person OWNER TO tom;

--
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: tom
--

COPY public.address (id, city, country, street) FROM stdin;
2	city1	country1	street1
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: tom
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	Create person table	SQL	V1__Create_person_table.sql	412676317	tom	2019-10-29 08:17:01.393073	14	t
2	1.1	Create foreign key	SQL	V1.1__Create_foreign_key.sql	-835301333	tom	2019-10-29 08:17:01.421791	3	t
3	2	Insert data	SQL	V2__Insert_data.sql	-717254005	tom	2019-10-29 08:17:01.435279	5	t
4	3	Create sequence	SQL	V3__Create_sequence.sql	-689862005	tom	2019-10-29 08:24:54.300337	7	t
\.


--
-- Data for Name: person; Type: TABLE DATA; Schema: public; Owner: tom
--

COPY public.person (id, firstname, lastname, address_id) FROM stdin;
1	James	Bond	2
3	Jason	Bourne	\N
4	Jack	Bauer	\N
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: tom
--

SELECT pg_catalog.setval('public.hibernate_sequence', 22, true);


--
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: tom
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: tom
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: tom
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: tom
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: person fkk7rgn6djxsv2j2bv1mvuxd4m9; Type: FK CONSTRAINT; Schema: public; Owner: tom
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT fkk7rgn6djxsv2j2bv1mvuxd4m9 FOREIGN KEY (address_id) REFERENCES public.address(id);


--
-- PostgreSQL database dump complete
--

