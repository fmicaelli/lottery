--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.7
-- Dumped by pg_dump version 9.5.7

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: euromillions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE euromillions (
    year_draw_number character(7) NOT NULL,
    draw_date date NOT NULL,
    number_in_cycle integer NOT NULL,
    ball_1 integer NOT NULL,
    ball_2 integer NOT NULL,
    ball_3 integer NOT NULL,
    ball_4 integer NOT NULL,
    ball_5 integer NOT NULL,
    star_1 integer NOT NULL,
    star_2 integer NOT NULL
);


--
-- Name: firstkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY euromillions
    ADD CONSTRAINT firstkey PRIMARY KEY (year_draw_number);


--
-- PostgreSQL database dump complete
--

