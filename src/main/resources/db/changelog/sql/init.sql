-- создаем таблицу пользователей users
create table if not exists users (
    id uuid not null,
    email varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    priority integer default 1,
    role varchar(16),
    created_on timestamp(6) default current_timestamp,
    updated_on timestamp(6) default current_timestamp,
    primary key (id)
);
create unique index idx_users_email on users(email);
create unique index idx_users_username on users(username);

-- создаем таблицу ролей authorities
create table if not exists authorities (
    id uuid not null,
    authority varchar(255),
    user_id uuid,
    primary key (id),
    constraint fk_authorities_user foreign key(user_id) references users(id)
);
create unique index idx_authorities_user_authority on authorities(user_id, authority);

-- создаем таблицу токенов refresh_tokens
create table if not exists refresh_tokens (
    id uuid not null,
    expiration timestamp(6) not null,
    token varchar(255) not null,
    user_id uuid,
    created_on timestamp(6) default current_timestamp,
    primary key (id),
    constraint fk_refresh_tokens_user foreign key(user_id) references users(id)
);
create unique index idx_refresh_tokens_token on refresh_tokens(token);

-- создаем таблицу станций stations
create table if not exists stations (
    id uuid not null,
    name varchar(50) default 'no-name-station',
    user_id uuid not null,
    created_on timestamp(6) default current_timestamp,
    updated_on timestamp(6) default current_timestamp,
    version bigint default 1,
    primary key (id)
);
create index idx_stations_user_id ON stations(user_id);

-- создаем таблицу агрегатов units
create table if not exists units (
    id uuid not null,
    unit_number integer default 1,
    unit_type varchar(50) not null default 'no-name-unit',
    weight_precision integer default 0,
    weight_unit_measure varchar(5) default 'kg',
    vibration_precision integer default 0,
    vibration_unit_measure varchar(5) default 'um',
    description varchar(255),
    station_id uuid not null,
    created_on timestamp(6) default current_timestamp,
    updated_on timestamp(6) default current_timestamp,
    version bigint default 1,
    primary key (id),
    constraint units_stations_id_fk foreign key (station_id) references stations(id)
);
create index idx_units_station_id ON units(station_id);

-- создаем таблицу пусков runs
create table if not exists runs (
    id uuid not null,
    run_number integer default 0,
    reference_run_id uuid,
    unit_id uuid not null,
    run_parameters jsonb,
    created_on timestamp(6) default current_timestamp,
    updated_on timestamp(6) default current_timestamp,
    version bigint default 1,
    primary key (id),
    constraint runs_units_id_fk foreign key (unit_id) references units(id),
    constraint runs_reference_run_fk foreign key (reference_run_id) references runs(id),
    constraint runs_no_self_reference_chk check (reference_run_id is null or reference_run_id <> id)
);
create index idx_runs_unit_id on runs(unit_id);
create index idx_runs_reference_run_id on runs(reference_run_id);

-- создаем таблицу записей вибрации records
create table if not exists records (
    id uuid not null,
    run_id uuid not null,
    point_name varchar(15) default 'point',
    mode_name varchar(15) default 'No-load 100%n',
    mag_vibration double precision not null default 0,
    phase_vibration double precision not null default 0,
    is_used boolean not null default true,
    is_manual_sensitivity boolean not null default false,
    created_on timestamp(6) default current_timestamp,
    updated_on timestamp(6) default current_timestamp,
    version bigint default 1,
    primary key (id),
    constraint records_runs_id_fk foreign key (run_id) references runs(id)
);
create index idx_records_run_id on records(run_id);

--
-- -- заполняем таблицу пользователей
-- insert into users (email, username, password) values
--     ('iioz@ya.ru', 'nikolay','$2a$12$sq./cdz8r.pv3wmpsjv3x.44//hq5/rm9erxbula4daankit0jork'),
--     ('pavel@mail.com', 'pavel','$2a$12$sq./cdz8r.pv3wmpsjv3x.44//hq5/rm9erxbula4daankit0jork'),
--     ('nikita@mail.com', 'nikita','$2a$12$sq./cdz8r.pv3wmpsjv3x.44//hq5/rm9erxbula4daankit0jork');
--
-- -- заполняем таблицу станций
-- insert into stations (name, user_id) values
--     ('Туполанг гэс', 1),
--     ('Воткиснкая гэс', 1),
--     ('Волжская гэс', 1),
--     ('Богучанская гэс', 1),
--     ('Модельный агрегат', 1),
--     ('Нижегородская гэс', 1);
--
-- -- заполняем таблицу агрегатов
-- insert into units (unit_number, unit_type, station_id) values
--     (3, 'СВ 477/180-16 ухл4', 1),
--     (7, 'СВ 1548/203-66 ухл4', 4),
--     (4, 'СВ 477/180-16 ухл4', 1),
--     (8, 'СВ 1488/175-88 ухл4', 2),
--     (2, 'СВ 1488/175-88 ухл4', 2),
--     (2, 'СВ 1488/200-88 ухл4', 3),
--     (1, 'СВ 1345/145-96 ухл4', 6),
--     (1, 'без названия', 5);
--
-- -- заполняем таблицу пусков
-- insert into runs (run_number, reference_run_id, run_parameters, unit_id) values
--    (0, null, '{"mag_weight": 0, "phase_weight": 0, "num_plane": 1}'::jsonb, 1),
--    (1, null, '{"mag_weight": 48, "phase_weight": -135, "num_plane": 1}'::jsonb, 1),
--    (2, null, '{"mag_weight": 15.4, "phase_weight": -135, "num_plane": 1}'::jsonb, 1),
--    (3, null, '{"mag_weight": 36.4, "phase_weight": -135, "num_plane": 1}'::jsonb, 1),
--    (4, null, '{"mag_weight": 37, "phase_weight": 180, "num_plane": 1}'::jsonb, 1),
--    (0, null, '{"mag_weight": 0, "phase_weight": 0, "num_plane": 1}'::jsonb, 2),
--    (1, null, '{"mag_weight": 250, "phase_weight": 0, "num_plane": 1}'::jsonb, 2),
--    (0, null, '{"mag_weight": 0, "phase_weight": 0, "num_plane": 1}'::jsonb, 7),
--    (0, 8,    '{"mag_weight": 30, "phase_weight": 0, "num_plane": 1}'::jsonb, 7),
--    (0, 8,    '{"mag_weight": 100, "phase_weight": 100, "num_plane": 1}'::jsonb, 7),
--    (0, 8,    '{"mag_weight": 50, "phase_weight": -90, "num_plane": 1}'::jsonb, 7),
--    (0, 10,   '{"mag_weight": 60, "phase_weight": 90, "num_plane": 1}'::jsonb, 7),
--    (0, 9,    '{"mag_weight": 21.2132, "phase_weight": 135, "num_plane": 1}'::jsonb, 7),
--    (0, 12,   '{"mag_weight": 21.2132, "phase_weight": 0, "num_plane": 1}'::jsonb, 7),
--    (0, null, '{"mag_weight": 36.5, "phase_weight": -135, "num_plane": 1}'::jsonb, 7);
--
-- -- заполняем таблицу записей вибрации
-- insert into records (point_name, mode_name, run_id, mag_vibration, phase_vibration) values
--    ('ТП', 'No-load 100%n', 6, 183, 51),
--    ('ТП', 'No-load 100%n', 6, 350, 52),
--    ('ТП', 'No-load 100%n', 6, 362, 68),
--    ('ТП', 'No-load 100%n', 7, 13, 331),
--    ('ТП', 'No-load 100%n', 7, 123, 41),
--    ('ТП', 'No-load 100%n', 7, 209, 74),
--
--    ('ВГП', 'No-load 100%n', 8, 100, -30),
--    ('ВГП', 'No-load 100%n', 9, 126.8706, -23.21),
--    ('ВГП', 'No-load 100%n', 10, 84.5237, 35),
--    ('ВГП', 'No-load 100%n', 11, 132.2876, -49.1066),
--    ('ВГП', 'No-load 100%n', 12, 39.3918, 75.0694),
--    ('ВГП', 'No-load 100%n', 13, 107.462, -19.0078),
--    ('ВГП', 'No-load 100%n', 14, 88.882, -13.0039),
--
--    ('ВГП', 'No-load 100%n', 1, 400, 174),
--    ('ВГП', 'No-load 100%n', 1, 249, 190),
--    ('ВГП', 'No-load 100%n', 1, 310, 189),
--    ('ВГП', 'No-load 100%n', 1, 312, 189),
--    ('ВГП', 'No-load 100%n', 2, 240, 189),
--    ('ВГП', 'No-load 100%n', 2, 288, 190),
--    ('ВГП', 'No-load 100%n', 2, 278, 192),
--    ('ВГП', 'No-load 100%n', 3, 209, 192),
--    ('ВГП', 'No-load 100%n', 3, 248, 192),
--    ('ВГП', 'No-load 100%n', 3, 244, 194),
--    ('ВГП', 'No-load 100%n', 4, 120, 191),
--    ('ВГП', 'No-load 100%n', 4, 156, 184),
--    ('ВГП', 'No-load 100%n', 4, 147, 193),
--    ('ВГП', 'No-load 100%n', 15, 199, 166),
--    ('ВГП', 'No-load 100%n', 15, 260, 162),
--    ('ВГП', 'No-load 100%n', 15, 196, 177),
--    ('ВГП', 'No-load 100%n', 5, 228, 208),
--    ('ВГП', 'No-load 100%n', 5, 273, 204),
--    ('ВГП', 'No-load 100%n', 5, 259, 207);
