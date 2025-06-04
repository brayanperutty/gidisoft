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
VALUES (1, 'Ciencias Agrarias Y Del Ambiente');
INSERT INTO public.faculties(id, "name")
VALUES (2, 'Ingeniería');
INSERT INTO public.faculties(id, "name")
VALUES (3, 'Ciencias Básicas');
INSERT INTO public.faculties(id, "name")
VALUES (4, 'Ciencias Empresariales');
INSERT INTO public.faculties(id, "name")
VALUES (5, 'Educación, Artes Y Humanidades');
INSERT INTO public.faculties(id, "name")
VALUES (6, 'Ciencias De La Salud');
ALTER SEQUENCE public.faculties_id_seq RESTART 7;

-- Investigation Groups
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (1, 'Grupo de Investigación Ambiente y Vida - GIAV', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (2, 'Grupo De Investigación En Ciencias Agronómicas Y Pecuarias - GICAP', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (3, 'Grupo De Investigación En Ciencia Y Tecnología Agroindustrial - GICITECA', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (4, 'Grupo De Investigación En Reproducción Animal Tropical - TROPSYNC', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (5, 'Grupo De Investigación En Procesos Ambientales - GIPROAM UFPS', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (6, 'Grupo De Investigación En Diagnóstico Y Manejo De Enfermedades En Plantas - GIDMEP', 1);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (7, 'Grupo De Investigación En Fluidos Y Térmicas - FLUTER', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (8, 'Grupo De Investigación Y Desarrollo De Ingeniería Del Software - GIDISOFT', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (9, 'Grupo De Investigación En Diseño Mecánico, Materiales Y Procesos - GIDIMA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (10, 'Grupo De Investigación En Geotecnia Ambiental - GIGA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (11, 'Grupo De Investigación En Productividad Y Competitividad - GIPYC', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (12, 'Grupo De Investigación Y Desarrollo En Electrónica Y Telecomunicaciones - GT GIDET', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (13, 'Grupo De Investigación Y Desarrollo En Microelectrónica Aplicada Y Control UFPS - GIDMAC', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (14, 'Grupo De Investigación En Innovación Y Gestión Productiva - GIINGPRO', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (15, 'Grupo De Investigación En Automatización Y Control - GIAC', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (16, 'Grupo De Investigación En Geología, Geotecnia Y Minería - GI.GEOENERGIA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (17, 'Grupo De Investigación Polimatas', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (18, 'Grupo De Investigación En Hidrología Y Recursos Hídricos - HYDROS', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (19, 'Grupo De Investigación En Transporte Y Obras Civiles - GITOC', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (20, 'Grupo De Investigación En Desarrollo De Procesos Industriales - GIDPI', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (21, 'Grupo De Investigación Ingeniería Y Desarrollo Social UFPS - INDES', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (22, 'Grupo De Investigación Y Desarrollo En Energía - GRIDEN', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (23, 'Grupo De Investigación En Inteligencia Artificial UPFS - GIA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (24, 'Grupo De Investigación En Infraestructura Vial UFPS - GINFRAVIAL', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (25, 'Grupo De Investigación En Tecnología, Innovación Y Sociedad UFPS - GITecInso', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (26, 'Grupo De Investigación De Nuevos Biomateriales - GINBIOMA', 2);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (27, 'Grupo De Investigación En Tecnología Cerámica - GITEC', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (28, 'Grupo De Investigación Enseñanza De Las Ciencias - ARQUIMEDES', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (29, 'Grupo De Investigación En Ciencias Biológicas - MAJUMBA', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (30, 'Grupo De Investigación En Química Básica Aplicada - GIQUIBA', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (31, 'Grupo De Investigación En Fitobioquímica Y Biología Molecular - FITOBIOMOL', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (32, 'Grupo De Investigación En Materiales Poliméricos - GIMAPOL', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (33, 'Grupo De Investigación Euler', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (34, 'Grupo De Investigación En Instrumentación Y Física De La Materia Condensada - GIFIMAC', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (35, 'Grupo De Investigación En Estadística Aplicada - GRAUNT', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (36, 'Grupo De Investigación En Recursos Energéticos Y Materiales - GIREM', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (37, 'Grupo De Investigación En Física De Materiales Nanoestructurados - GFMN', 3);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (38, 'Grupo De Investigación & Desarrollo Regional UFPS - IDR', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (39, 'Grupo de Investigación Contable Cinera - GICC', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (40, 'Grupo De Investigación Para El Desarrollo Socioeconómico - GIDSE', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (41, 'Grupo De Investigación Zulima Science UFPS', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (42, 'Grupo De Investigación Ciencias Sociales Y Humanas - GICSH', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (43, 'Grupo De Investigación Gerencia Y Escenarios Para El Desarrollo - GEDES', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (44, 'Grupo De Investigación En Logística, Competitividad Y Negocios Internacionales - GILOCNI', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (45, 'Grupo De Investigación En Gestión Y Organizaciones - GYO', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (46, 'Grupo De Investigación Contable, Financiero Y Fiscal - GICOFF', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (47, 'Grupo De Investigación Proyección Empresarial - GIPE', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (48, 'Grupo De Investigación Formación Financiera UFPS - GIFOFI', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (49, 'Grupo De Investigación & Gestión UFPS - I&G', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (50, 'Grupo De Investigación Contable, Administrativa Y Financiera - GICAF', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (51, 'Grupo De Investigación En Administración Financiera Y De Proyectos - GIAFPRO', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (52, 'Grupo De Investigación Sistema De Gestión Y Control Empresarial - SIG&CE', 4);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (53, 'Grupo De Investigación En Estudios Sociales Y Pedagogía Para La Paz - GIESPPAZ', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (54, 'Grupo De Investigación En Orientación Educativa, Vocacional Y Ocupacional UPFS - GIOEVO', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (55, 'Grupo De Investigación En Problemas Socioeconómicos, Regionales Y Fronterizos UFPS - GIPSERF', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (56, 'Grupo De Investigación Jurídico, Comercial Y Fronterizo UFPS - GIJCF', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (57, 'Grupo De Investigación En Arquitectura Y Materiales Alternativos UFPS - GRAMA', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (58, 'Grupo De Investigación En Comunicación - APIKUNA', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (59, 'Grupo De Investigación En Pedagogía Y Práctica Pedagógica UFPS - INPEPRA', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (60, 'Grupo De Investigación Taller De Arquitectura Y Gestión Del Territorio - TAR_GET', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (61, 'Grupo De Investigación Trabajo Social UFPS - GITS', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (62, 'Grupo De Investigación De La Calidad Y Evaluación De La Educación - GCIES', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (63, 'Grupo De Investigación Laboratorio De Investigación En Diseño - d_lab', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (64, 'Grupo De Investigación En Comunicación Y Medios - GICOM UFPS', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (65, 'Grupo De Investigación En Justicia, Derechos Humanos Y Democracia - JHUSDEM', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (66, 'Grupo De Investigación Pedagógica, Ciencia Y Espiritualidad Universidad Santo Tomas - UFPS', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (67, 'Grupo De Investigación En Tecnología Educativa, Innovación Y Prácticas Formativas UFPS - GITIP', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (68, 'Grupo De Investigación En Educación En Ciencia, Tecnología, Ingeniería, Matemáticas Y Arte - GIESTEMA', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (69, 'Grupo De Investigación En Pedagogía Y Gestión Ambiental UFPS - GIPGEAM', 5);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (70, 'Grupo De Investigación De Salud Pública - GISP', 6);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (71, 'Grupo De Investigación Para El Cuidado De La Salud - UFPS GINCUS', 6);
INSERT INTO public.investigation_group(id, "name", faculty_id)
VALUES (72, 'Grupo De Investigación En Regencia De Farmacia - GIRFAR UFPS', 6);
ALTER SEQUENCE public.investigation_group_id_seq RESTART 73;

--- ProducType
INSERT INTO public.products_type(id, "name")
VALUES (1, 'Actualización GrupLAC - Actualización CGIS');
INSERT INTO public.products_type(id, "name")
VALUES (2, 'Participación convocatoria de reconocimiento Minciencias');
INSERT INTO public.products_type(id, "name")
VALUES (3, 'Proyectos terminados y/o ejecución, avalados con financiación interna (FINU) o externa.');
INSERT INTO public.products_type(id, "name")
VALUES (4, 'Artículo publicado o remitido revista científica');
INSERT INTO public.products_type(id, "name")
VALUES (5, 'Participación propuesta investigación en convocatoria interna o externa');
INSERT INTO public.products_type(id, "name")
VALUES (6, 'Ponencia evento académico regional, nacional o internacional');
INSERT INTO public.products_type(id, "name")
VALUES (7, 'Dirección trabajo de grado (post-grado, maestría)');
INSERT INTO public.products_type(id, "name")
VALUES (8, 'Otros productos');
ALTER SEQUENCE public.products_type_id_seq RESTART 9;
