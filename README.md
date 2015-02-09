# IndoorNavSrv
CXF Webservice for HS Mainz IndoorNavigation Project

The goal of this scientific project is to attempt to use cheap iBeacons with bluetooth low energy technology for indoor positioning and in the future possibly navigation.


This webservice is using CXF and Hibernate for DAO operations and will compile to a runnable jar including jetty.

A first Android Client can be found <a href="https://github.com/KekSfabrik/IndoorNavCl">here</a>.

The "official" site for the project (including the scientific paper once finished) can be found 
<a href="http://i3mainz.hs-mainz.de/de/studentenprojekt/indoor-positionsbestimmung-mit-hilfe-von-ibeacons">here</a>.

## DB Setup
3 postgresql/postgis tables should exist:
```
CREATE TABLE public.beacon (
  id serial PRIMARY KEY,
  uuid character varying(32) NOT NULL,
  major integer NOT NULL,
  minor integer NOT NULL
);
```
```
CREATE TABLE public.site (
  site serial PRIMARY KEY,
  name character varying(255) NOT NULL
);
```
```
CREATE TABLE public.location (
  site integer NOT NULL,
  beacon_id integer NOT NULL,
  coord geometry(PointZ),
  CONSTRAINT location_pkey PRIMARY KEY (site, beacon_id),
  CONSTRAINT location_beacon_id FOREIGN KEY (beacon_id)
      REFERENCES public.beacon (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT location_site_fkey FOREIGN KEY (site)
      REFERENCES public.site (site) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
```


Additionally, in order to successfully finish the unit tests during build 3 entries should exist:
```
INSERT INTO public.site (site, name) VALUES (1, 'KekSfabrik');
INSERT INTO public.beacon (id, uuid, major, minor) VALUES (1, '00000000000000000000000000000000', 100, 12);
INSERT INTO public.location (site, beacon_id, coord) VALUES (1, 1, ST_GeomFromText('POINTZ(0 0 1)', 4326));
```
Maybe some changes still have to be made regarding the number of objects in each table..
