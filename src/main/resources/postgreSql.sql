-- Roles
INSERT INTO public.roles(id, "name", "type")
VALUES (1, 'Administrador', 'admin');
INSERT INTO public.roles(id, "name", "type")
VALUES (2, 'Docente Investigador', 'teacher');
INSERT INTO public.roles(id, "name", "type")
VALUES (3, 'Docente de grupo', 'director');
ALTER SEQUENCE public.roles_id_seq RESTART 4;

-- Users
INSERT INTO public.roles(id, role_id, user_status_id, "email", "name", "password", "phone_number", "usercode")
VALUES (1, 1, 1, 'admin@ufps.com', 'Judith del Pilar Rodriguez Tenjo',
        '$2a$10$4wpbdFKfYoN2qy6iIQgvf.7V2jzALqUpRPL2wbuuE.Uk4KjEAkTU2', '123456789', '0001');
INSERT INTO public.roles(id, role_id, user_status_id, "email", "name", "password", "phone_number", "usercode")
VALUES (2, 2, 1, 'user1@ufps.com', 'María del Pilar Rojas',
        '$2a$10$hYsiC4.9fxjCTsCbPa/5Pe8T4eC/l8XWyPIVMJqIm.B6MsIuSNOPu', '123456789', '0002');
INSERT INTO public.roles(id, role_id, user_status_id, "email", "name", "password", "phone_number", "usercode")
VALUES (3, 2, 1, 'user2@ufps.com', 'Marco Antonio Adarme',
        '$2a$10$xyUV/DE0fAZ7uIbMEnIPU.GohxTAeB4CkgffpYUuRw8hxY6KuG2X.', '123456789', '0003');
INSERT INTO public.roles(id, role_id, user_status_id, "email", "name", "password", "phone_number", "usercode")
VALUES (4, 2, 1, 'user3@ufps.com', 'Carlos René Angarita Sanguino',
        '$2a$10$SHAcsfEhXV/1UDhxyvNtoOsRqCTNkcvePodVs37TooQTHNsOpXEZi', '123456789', '0004');
ALTER SEQUENCE public.users_id_seq RESTART 5;

-- Status
INSERT INTO public.roles(id, "name")
VALUES (1, 'Borrador');
INSERT INTO public.roles(id, "name")
VALUES (2, 'Enviado');
ALTER SEQUENCE public.roles_id_seq RESTART 3;

-- Academic Periods
INSERT INTO public.roles(id, year, "period")
VALUES (1, 2025, 'I');
INSERT INTO public.roles(id, year, "period")
VALUES (2, 2025, 'II');
INSERT INTO public.roles(id, year, "period")
VALUES (3, 2026, 'I');
INSERT INTO public.roles(id, year, "period")
VALUES (4, 2026, 'II');
INSERT INTO public.roles(id, year, "period")
VALUES (5, 2027, 'I');
INSERT INTO public.roles(id, year, "period")
VALUES (6, 2027, 'II');
INSERT INTO public.roles(id, year, "period")
VALUES (7, 2028, 'I');
INSERT INTO public.roles(id, year, "period")
VALUES (8, 2028, 'II');
INSERT INTO public.roles(id, year, "period")
VALUES (9, 2029, 'I');
INSERT INTO public.roles(id, year, "period")
VALUES (10, 2029, 'II');
ALTER SEQUENCE public.roles_id_seq RESTART 11;


-- Faculties
INSERT INTO public.faculties(id, "name")
VALUES (1, 'CIENCIAS AGRARIAS Y DEL AMBIENTE');
INSERT INTO public.faculties(id, "name")
VALUES (2, 'INGENIERÍA');
INSERT INTO public.faculties(id, "name")
VALUES (3, 'CIENCIAS BÁSICAS');
INSERT INTO public.faculties(id, "name")
VALUES (4, 'CIENCIAS EMPRESARIALES');
INSERT INTO public.faculties(id, "name")
VALUES (5, 'EDUCACIÓN, ARTES Y HUMANIDADES');
INSERT INTO public.faculties(id, "name")
VALUES (6, 'CIENCIAS DE LA SALUD');
ALTER SEQUENCE public.faculties_id_seq RESTART 7;

-- Investigation Groups
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (1, 'GRUPO DE INVESTIGACIÓN AMBIENTE Y VIDA - GIAV', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (2, 'GRUPO DE INVESTIGACIÓN EN CIENCIAS AGRONÓMICAS Y PECUARIAS - GICAP', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (3, 'GRUPO DE INVESTIGACIÓN EN CIENCIA Y TECNOLOGÍA AGROINDUSTRIAL - GICITECA', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (4, 'GRUPO DE INVESTIGACIÓN EN REPRODUCCIÓN ANIMAL TROPICAL - TROPSYNC', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (5, 'GRUPO DE INVESTIGACIÓN EN PROCESOS AMBIENTALES - GIPROAM UFPS', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (6, 'GRUPO DE INVESTIGACIÓN EN DIAGNÓTICO Y MANEJO DE ENFERMEDADES EN PLANTAS - GIDMEP', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (7, 'GRUPO DE INVESTIGACIÓN EN FLUIDOS Y TÉRMICAS - FLUTER', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (8, 'GRUPO DE INVESTIGACIÓN Y DESARROLLO DE INGENIERÍA DEL SOFTWARE - GIDISOFT', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (9, 'GRUPO DE INVESTIGACIÓN EN DISEÑO MECÁNICO, MATERIALES Y PROCESOS - GIDIMA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (10, 'GRUPO DE INVESTIGACIÓN EN GEOTECNIA AMBIENTAL - GIGA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (11, 'GRUPO DE INVESTIGACIÓN EN PRODUCTIVIDAD Y COMPETITIVIDAD - GIPYC', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (12, 'GRUPO DE INVESTIGACIÓN Y DESARROLLO EN ELECTRÓNICA Y TELECOMUNICACIONES - GT GIDET', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (13, 'GRUPO DE INVESTIGACIÓN Y DESARROLLO EN MICROELECTRÓNICA APLICADA Y CONTROL UFPS - GIDMAC', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (14, 'GRUPO DE INVESTIGACIÓN EN INNOVACIÓN Y GESTIÓN PRODUCTIVA - GIINGPRO', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (15, 'GRUPO DE INVESTIGACIÓN EN AUTOMATIZACIÓN Y CONTROL - GIAC', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (16, 'GRUPO DE INVESTIGACIÓN EN GEOLOGÍA, GEOTECNIA Y MINERÍA - GI.GEOENERGIA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (17, 'GRUPO DE INVESTIGACIÓN POLIMATAS', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (18, 'GRUPO DE INVESTIGACIÓN EN HIDROLOGÍA Y RECURSOS HÍDRICOS - HYDROS', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (19, 'GRUPO DE INVESTIGACIÓN EN TRANSPORTE Y OBRAS CIVILES - GITOC', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (20, 'GRUPO DE INVESTIGACIÓN EN DESARROLLO DE PROCESOS INDUSTRIALES - GIDPI', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (21, 'GRUPO DE INVESTIGACIÓN INGENIERÍA Y DESARROLLO SOCIAL UFPS - INDES', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (22, 'GRUPO DE INVESTIGACIÓN Y DESARROLLO EN ENERGÍA - GRIDEN', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (23, 'GRUPO DE INVESTIGACIÓN EN INTELIGENCIA ARTIFICIAL UPFS - GIA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (24, 'GRUPO DE INVESTIGACIÓN EN INFRAESTRUCTURA VIAL UFPS - GINFRAVIAL', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (25, 'GRUPO DE INVESTIGACIÓN EN TECNOLOGÍA, INNOVACIÓN Y SOCIEDAD UFPS - GITecInso', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (26, 'GRUPO DE INVESTIGACIÓN DE NUEVOS BIOMATERIALES - GINBIOMA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (27, 'GRUPO DE INVESTIGACIÓN EN TECNOLOGÍA CERÁMICA - GITEC', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (28, 'GRUPO DE INVESTIGACIÓN ENSEÑANZA DE LAS CIENCIAS - ARQUIMEDES', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (29, 'GRUPO DE INVESTIGACIÓN EN CIENCIAS BIOLÓGICAS - MAJUMBA', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (30, 'GRUPO DE INVESTIGACIÓN EN QUÍMICA BÁSICA APLICADA - GIQUIBA', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (31, 'GRUPO DE INVESTIGACIÓN EN FITOBIOQUÍMICA Y BIOLOGÍA MOLECULAR - FITOBIOMOL', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (32, 'GRUPO DE INVESTIGACIÓN EN MATERIALES POLIMÉRICOS - GIMAPOL', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (33, 'GRUPO DE INVESTIGACIÓN EULER', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (34, 'GRUPO DE INVESTIGACIÓN EN INSTRUMENTACIÓN Y FÍSICA DE LA MATERIA CONDENSADA - GIFIMAC', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (35, 'GRUPO DE INVESTIGACIÓN EN ESTADÍSTICA APLICADA - GRAUNT', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (36, 'GRUPO DE INVESTIGACIÓN EN RECURSOS ENERGÉTICOS Y MATERIALES - GIREM', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (37, 'GRUPO DE INVESTIGACIÓN EN FÍSICA DE MATERIALES NANOESTRUCTURADOS - GFMN', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (38, 'GRUPO DE INVESTIGACIÓN & DESARROLLO REGIONAL UFPS - IDR', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (39, 'CONTABLE CINERA - GICC', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (40, 'GRUPO DE INVESTIGACIÓN PARA EL DESARROLLO SOCIOECONÓMICO - GIDSE', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (41, 'GRUPO DE INVESTIGACIÓN ZULIMA SCIENCE UFPS', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (42, 'GRUPO DE INVESTIGACIÓN CIENCIAS SOCIALES Y HUMANAS - GICSH', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (43, 'GRUPO DE INVESTIGACIÓN GERENCIA Y ESCENARIOS PARA EL DESARROLLO - GEDES', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (44, 'GRUPO DE INVESTIGACIÓN EN LOGÍSTICA, COMPETITIVIDAD Y NEGOCIOS INTERNACIONALES - GILOCNI', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (45, 'GRUPO DE INVESTIGACIÓN EN GESTIÓN Y ORGANIZACIONES - GYO', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (46, 'GRUPO DE INVESTIGACIÓN CONTABLE, FINANCIERO Y FISCAL - GICOFF', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (47, 'GRUPO DE INVESTIGACIÓN PROYECCIÓN EMPRESARIAL - GIPE', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (48, 'GRUPO DE INVESTIGACIÓN FORMACIÓN FINANCIERA UFPS - GIFOFI', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (49, 'GRUPO DE INVESTIGACIÓN & GESTIÓN UFPS - I&G', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (50, 'GRUPO DE INVESTIGACIÓN CONTABLE, ADMINISTRATIVA Y FINANCIERA - GICAF', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (51, 'GRUPO DE INVESTIGACIÓN EN ADMINISTRACIÓN FINANCIERA Y DE PROYECTOS - GIAFPRO', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (52, 'GRUPO DE INVESTIGACIÓN SISTEMA DE GESTIÓN Y CONTROL EMPRESARIAL - SIG&CE', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (53, 'GRUPO DE INVESTIGACIÓN EN ESTUDIOS SOCIALES Y PEDAGOGÍA PARA LA PAZ - GIESPPAZ', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (54, 'GRUPO DE INVESTIGACIÓN EN ORIENTACIÓN EDUCATIVA, VOCACIONAL Y OCUPACIONAL UPFS - GIOEVO', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (55, 'GRUPO DE INVESTIGACIÓN EN PROBLEMAS SOCIOECONÓMICOS, REGIONALES Y FRONTERIZOS UFPS - GIPSERF', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (56, 'GRUPO DE INVESTIGACIÓN JURÍDICO, COMERCIAL Y FRONTERIZO UFPS - GIJCF', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (57, 'GRUPO DE INVESTIGACIÓN EN ARQUITECTURA Y MATERIALES ALTERNATIVOS UFPS - GRAMA', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (58, 'GRUPO DE INVESTIGACIÓN EN COMUNICACIÓN - APIKUNA', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (59, 'GRUPO DE INVESTIGACIÓN EN PEDAGOGÍA Y PRÁCTICA PEDAGÓGICA UFPS - INPEPRA', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (60, 'GRUPO DE INVESTIGACIÓN TALLER DE ARQUITECTURA Y GESTIÓN DEL TERRITORIO - TAR_GET', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (61, 'GRUPO DE INVESTIGACIÓN TRABAJO SOCIAL UFPS - GITS', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (62, 'GRUPO DE INVESTIGACIÓN DE LA CALIDAD Y EVALUACIÓN DE LA EDUCACIÓN - GCIES', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (63, 'GRUPO DE INVESTIGACIÓN LABORATORIO DE INVESTIGACIÓN EN DISEÑO - d_lab', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (64, 'GRUPO DE INVESTIGACIÓN EN COMUNICACIÓN Y MEDIOS - GICOM UFPS', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (65, 'GRUPO DE INVESTIGACIÓN EN JUSTICIA, DERECHOS HUMANOS Y DEMOCRACIA - JHUSDEM', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (66, 'GRUPO DE INVESTIGACIÓN PEDAGÓGICA, CIENCIA Y ESPIRITUALIDAD UNIVERSIDAD SANTO TOMAS - UFPS', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (67, 'GRUPO DE INVESTIGACIÓN EN TECNOLOGÍA EDUCATIVA, INNOVACIÓN Y PRÁCTICAS FORMATIVAS UFPS - GITIP', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (68, 'GRUPO DE INVESTIGACIÓN EN EDUCACIÓN EN CIENCIA, TECNOLOGÍA, INGENIERÍA, MATEMÁTICAS Y ARTE - GIESTEMA', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (69, 'GRUPO DE INVESTIGACIÓN EN PEDAGOGÍA Y GESTIÓN AMBIENTAL UFPS - GIPGEAM', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (70, 'GRUPO DE INVESTIGACIÓN DE SALUD PÚBLICA - GISP', 6);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (71, 'GRUPO DE INVESTIGACIÓN PARA EL CUIDADO DE LA SALUD - UFPS GINCUS', 6);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (72, 'GRUPO DE INVESTIGACIÓN EN REGENCIA DE FARMACIA - GIRFAR UFPS', 6);
ALTER SEQUENCE public.investigation_groups_id_seq RESTART 73;
