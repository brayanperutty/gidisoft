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