CREATE SEQUENCE "public".authenticable_id_auth_seq START WITH 20 INCREMENT BY 1;

CREATE SEQUENCE "public".categorie_id_seq START WITH 20 INCREMENT BY 1;

CREATE SEQUENCE "public".compte_id_compte_seq START WITH 20 INCREMENT BY 1;

CREATE SEQUENCE "public".duree_enchere_id_duree_enchere_seq START WITH 20 INCREMENT BY 1;

CREATE SEQUENCE "public".enchere_id_enchere_seq START WITH 20 INCREMENT BY 1;

CREATE SEQUENCE "public".hibernate_sequence START WITH 20 INCREMENT BY 1;

CREATE SEQUENCE "public".rencherir_id_seq START WITH 20 INCREMENT BY 1;

CREATE SEQUENCE "public".token_id_token_seq START WITH 20 INCREMENT BY 1;

CREATE  TABLE "public".categorie ( 
	id                   bigint DEFAULT nextval('categorie_id_seq'::regclass) NOT NULL  ,
	nom                  varchar(255)    ,
	CONSTRAINT categorie_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE "public".duree_enchere ( 
	id_duree_enchere     integer DEFAULT nextval('duree_enchere_id_duree_enchere_seq'::regclass) NOT NULL  ,
	"min"                integer  NOT NULL  ,
	"max"                integer  NOT NULL  ,
	CONSTRAINT duree_enchere_pkey PRIMARY KEY ( id_duree_enchere )
 );

CREATE  TABLE "public".encheredto ( 
	id_enchere           bigint  NOT NULL  ,
	date_debut           timestamp    ,
	date_fin             timestamp    ,
	CONSTRAINT encheredto_pkey PRIMARY KEY ( id_enchere )
 );

CREATE  TABLE "public"."role" ( 
	id                   bigint  NOT NULL  ,
	name                 varchar(255)    ,
	CONSTRAINT role_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE "public".authenticable ( 
	id_auth              bigint DEFAULT nextval('authenticable_id_auth_seq'::regclass) NOT NULL  ,
	"password"           varchar(255)    ,
	username             varchar(255)    ,
	role_id              bigint    ,
	CONSTRAINT authenticable_pkey PRIMARY KEY ( id_auth )
 );

CREATE  TABLE "public".membre ( 
	id_auth              bigint  NOT NULL  ,
	CONSTRAINT membre_pkey PRIMARY KEY ( id_auth )
 );

CREATE  TABLE "public".token ( 
	id_token             bigint DEFAULT nextval('token_id_token_seq'::regclass) NOT NULL  ,
	created_at           timestamp    ,
	expired_at           timestamp    ,
	"value"              varchar(255)    ,
	id_auth              bigint    ,
	CONSTRAINT token_pkey PRIMARY KEY ( id_token )
 );

CREATE  TABLE "public".compte ( 
	id_compte            integer DEFAULT nextval('compte_id_compte_seq'::regclass) NOT NULL  ,
	id_membre            integer  NOT NULL  ,
	solde                numeric(20,2)  NOT NULL  ,
	dates                date  NOT NULL  ,
	etat                 integer DEFAULT 0   ,
	CONSTRAINT compte_pkey PRIMARY KEY ( id_compte )
 );

CREATE  TABLE "public".enchere ( 
	id_enchere           bigint DEFAULT nextval('enchere_id_enchere_seq'::regclass) NOT NULL  ,
	date_debut           timestamp    ,
	date_fin             timestamp    ,
	description          varchar(255)    ,
	prix_minimum         double precision    ,
	categorie_id         bigint    ,
	membre_id            bigint    ,
	CONSTRAINT enchere_pkey PRIMARY KEY ( id_enchere )
 );

CREATE  TABLE "public".enchere_photos ( 
	enchere_id_enchere   bigint  NOT NULL  ,
	photos               varchar(255)    
 );

CREATE  TABLE "public".rencherissement ( 
	id                   bigint DEFAULT nextval('rencherir_id_seq'::regclass) NOT NULL  ,
	date_enchere         timestamp    ,
	prix_enchere         double precision    ,
	enchere_id           bigint    ,
	membre_id            bigint    ,
	CONSTRAINT rencherir_pkey PRIMARY KEY ( id )
 );

CREATE UNIQUE INDEX authenticable_username_uindex ON "public".authenticable ( username );

ALTER TABLE "public".authenticable ADD CONSTRAINT fk8sg4yl6j5k1lclbungcnqf2sc FOREIGN KEY ( role_id ) REFERENCES "public"."role"( id );

ALTER TABLE "public".compte ADD CONSTRAINT compte_id_membre_fkey FOREIGN KEY ( id_membre ) REFERENCES "public".membre( id_auth ) ON DELETE CASCADE;

ALTER TABLE "public".enchere ADD CONSTRAINT fk60xi1tj96hjtuxoahcnkugal4 FOREIGN KEY ( categorie_id ) REFERENCES "public".categorie( id );

ALTER TABLE "public".enchere ADD CONSTRAINT fk1icmit91lfr6k942gysccmhuf FOREIGN KEY ( membre_id ) REFERENCES "public".membre( id_auth ) ON DELETE CASCADE;

ALTER TABLE "public".enchere_photos ADD CONSTRAINT fk9tinjr3ohq3ic0q8vxr1l9wt9 FOREIGN KEY ( enchere_id_enchere ) REFERENCES "public".enchere( id_enchere );

ALTER TABLE "public".membre ADD CONSTRAINT fkna060549mkl3sw9hjveypaoku FOREIGN KEY ( id_auth ) REFERENCES "public".authenticable( id_auth );

ALTER TABLE "public".rencherissement ADD CONSTRAINT fkjvfd0pvlg7cp6kbw6lb13y7oj FOREIGN KEY ( enchere_id ) REFERENCES "public".enchere( id_enchere );

ALTER TABLE "public".rencherissement ADD CONSTRAINT fklchqx2r5xwq406pq7gol42eft FOREIGN KEY ( membre_id ) REFERENCES "public".membre( id_auth ) ON DELETE CASCADE;

ALTER TABLE "public".token ADD CONSTRAINT fk30vdo175rckd5xrxmv0toht1w FOREIGN KEY ( id_auth ) REFERENCES "public".authenticable( id_auth ) ON DELETE CASCADE;

CREATE VIEW "public".v_compte AS  SELECT membre.id_auth,
    a.username,
    compte.id_compte,
    compte.solde,
    compte.dates,
    compte.etat
   FROM ((compte
     JOIN membre ON ((membre.id_auth = compte.id_membre)))
     JOIN authenticable a ON ((a.id_auth = membre.id_auth)));

CREATE VIEW "public".v_compte_actu AS  SELECT v_validation.id_auth,
    sum(v_validation.solde) AS solde_actu
   FROM v_validation
  GROUP BY v_validation.id_auth;

CREATE VIEW "public".v_compte_actuf AS  SELECT membre.id_auth,
    membre.username,
    COALESCE(v_compte_actu.solde_actu, 0.0) AS solde_actu
   FROM (v_membre membre
     LEFT JOIN v_compte_actu ON ((v_compte_actu.id_auth = membre.id_auth)));

CREATE VIEW "public".v_compte_final AS  SELECT v_compte_actuf.id_auth,
    ((v_compte_actuf.solde_actu)::double precision - COALESCE(v.prix_courant, (0.0)::double precision)) AS solde_actu
   FROM (v_compte_actuf
     LEFT JOIN v_encheres v ON ((v.membre_id = v_compte_actuf.id_auth)));

CREATE VIEW "public".v_encheres AS  SELECT enchere.id_enchere,
    enchere.date_debut,
    enchere.date_fin,
    enchere.description,
    enchere.prix_minimum,
    enchere.categorie_id,
    enchere.membre_id AS owner_id,
    enchere_max.membre_id,
    authenticable.username AS owner,
    c.nom AS categorie,
    COALESCE(enchere_max.prix_enchere, enchere.prix_minimum) AS prix_courant,
        CASE
            WHEN (enchere.date_fin <= now()) THEN 'ended'::text
            ELSE 'active'::text
        END AS statut
   FROM (((enchere
     JOIN authenticable ON ((enchere.membre_id = authenticable.id_auth)))
     JOIN categorie c ON ((c.id = enchere.categorie_id)))
     LEFT JOIN v_rencherissement_max enchere_max ON ((enchere_max.enchere_id = enchere.id_enchere)))
  ORDER BY enchere.id_enchere;

CREATE VIEW "public".v_membre AS  SELECT a.id_auth,
    a.password,
    a.username
   FROM (membre
     JOIN authenticable a ON ((a.id_auth = membre.id_auth)));

CREATE VIEW "public".v_nonvalidation AS  SELECT v_compte.id_auth,
    v_compte.username,
    v_compte.id_compte,
    v_compte.solde,
    v_compte.dates,
    v_compte.etat
   FROM v_compte
  WHERE (v_compte.etat = 0)
  ORDER BY v_compte.dates DESC;

CREATE VIEW "public".v_rencherissement_max AS  SELECT rencherissement.id,
    rencherissement.date_enchere,
    rencherissement.prix_enchere,
    rencherissement.enchere_id,
    rencherissement.membre_id
   FROM (rencherissement
     JOIN ( SELECT rencherissement_1.enchere_id,
            max(rencherissement_1.prix_enchere) AS max
           FROM rencherissement rencherissement_1
          GROUP BY rencherissement_1.enchere_id) rencher ON (((rencher.max = rencherissement.prix_enchere) AND (rencher.enchere_id = rencherissement.enchere_id))));

CREATE VIEW "public".v_validation AS  SELECT v_compte.id_auth,
    v_compte.username,
    v_compte.id_compte,
    v_compte.solde,
    v_compte.dates,
    v_compte.etat
   FROM v_compte
  WHERE (v_compte.etat = 1)
  ORDER BY v_compte.dates DESC;

INSERT INTO "public".categorie( id, nom ) VALUES ( 1, 'Vehicule');
INSERT INTO "public".categorie( id, nom ) VALUES ( 2, 'informatique');
INSERT INTO "public".categorie( id, nom ) VALUES ( 3, 'Smartphones');
INSERT INTO "public".categorie( id, nom ) VALUES ( 4, 'accessoires');
INSERT INTO "public".categorie( id, nom ) VALUES ( 5, 'Voitures');
INSERT INTO "public".categorie( id, nom ) VALUES ( 6, 'Moto');
INSERT INTO "public".categorie( id, nom ) VALUES ( 7, 'bicyclette');
INSERT INTO "public".categorie( id, nom ) VALUES ( 8, 'Meuble');
INSERT INTO "public".categorie( id, nom ) VALUES ( 9, 'electromanager');
INSERT INTO "public".duree_enchere( id_duree_enchere, "min", "max" ) VALUES ( 1, 86400, 604800);
INSERT INTO "public"."role"( id, name ) VALUES ( 2, 'ADMIN');
INSERT INTO "public"."role"( id, name ) VALUES ( 1, 'MEMBRE');
INSERT INTO "public".authenticable( id_auth, "password", username, role_id ) VALUES ( 6, 'Maitre', 'Mr Rojo', 2);
INSERT INTO "public".authenticable( id_auth, "password", username, role_id ) VALUES ( 1, 'rindra', 'Rindra', 1);
INSERT INTO "public".authenticable( id_auth, "password", username, role_id ) VALUES ( 2, 'jonhs', 'Jonhs', 1);
INSERT INTO "public".authenticable( id_auth, "password", username, role_id ) VALUES ( 3, '1', 'RATSIMO', 1);
INSERT INTO "public".authenticable( id_auth, "password", username, role_id ) VALUES ( 4, '1', 'RATSIRAKA', 1);
INSERT INTO "public".authenticable( id_auth, "password", username, role_id ) VALUES ( 5, '1', 'RABENANRINDRA', 1);
INSERT INTO "public".membre( id_auth ) VALUES ( 1);
INSERT INTO "public".membre( id_auth ) VALUES ( 2);
INSERT INTO "public".membre( id_auth ) VALUES ( 3);
INSERT INTO "public".membre( id_auth ) VALUES ( 4);
INSERT INTO "public".membre( id_auth ) VALUES ( 5);
INSERT INTO "public".token( id_token, created_at, expired_at, "value", id_auth ) VALUES ( 19, '2023-01-20 02:30:04 PM', '2023-01-20 03:30:04 PM', '1-f9b886e754bdd4c99672d05886f9ae1071bcdb28de98fe6f4a974a4e297385df', 1);
INSERT INTO "public".token( id_token, created_at, expired_at, "value", id_auth ) VALUES ( 20, '2023-01-20 02:31:17 PM', '2023-01-20 03:31:17 PM', '6-4a22fc8c852b4844535e9dca9c536e36543846a40de41b58a96c448da846ec49', 6);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 1, 2, 40000, '2023-01-01', 1);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 2, 3, 40000, '2023-01-01', 1);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 3, 4, 40000, '2023-01-01', 1);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 4, 5, 40000, '2023-01-01', 1);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 9, 2, 40000, '2023-01-01', 1);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 10, 2, 50000, '2023-01-02', 0);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 11, 3, 40000, '2023-01-02', 0);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 12, 4, 40000, '2023-01-02', 0);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 13, 5, 40000, '2023-01-02', 0);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 16, 2, 50000, '2023-01-03', 0);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 17, 3, 40000, '2023-01-03', 0);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 18, 4, 40000, '2023-01-03', 0);
INSERT INTO "public".compte( id_compte, id_membre, solde, dates, etat ) VALUES ( 19, 5, 40000, '2023-01-03', 0);
INSERT INTO "public".enchere( id_enchere, date_debut, date_fin, description, prix_minimum, categorie_id, membre_id ) VALUES ( 18, '2023-01-20 02:51:56 PM', '2023-01-22 08:51:55 AM', 'Voiture karenji provenant de Madagascar, luxe et à la fois consistant', 1000.0, 1, 2);
INSERT INTO "public".enchere( id_enchere, date_debut, date_fin, description, prix_minimum, categorie_id, membre_id ) VALUES ( 1, '2023-01-19 01:00:00 AM', '2023-01-10 10:00:00 PM', 'Mixeur Melangeur KEJB002W', 10000.0, 9, 2);
INSERT INTO "public".enchere( id_enchere, date_debut, date_fin, description, prix_minimum, categorie_id, membre_id ) VALUES ( 2, '2023-01-20 03:00:00 AM', '2023-01-21 02:00:00 AM', 'MIXEUR SANS FIL-KMJB023B', 10000.0, 9, 3);
INSERT INTO "public".enchere( id_enchere, date_debut, date_fin, description, prix_minimum, categorie_id, membre_id ) VALUES ( 3, '2023-01-20 01:00:00 PM', '2023-01-22 02:00:00 AM', 'Thomson NEO 14C', 20000.0, 2, 4);
