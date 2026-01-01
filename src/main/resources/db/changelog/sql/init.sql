-- Создаем таблицу пользователей users
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

-- Создаем таблицу ролей authorities
create table if not exists authorities (
    id uuid not null,
    authority varchar(255),
    user_id uuid,
    primary key (id),
    constraint fk_authorities_user foreign key(user_id) references users(id)
);
create unique index idx_authorities_user_authority on authorities(user_id, authority);

-- Создаем таблицу токенов refresh_tokens
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

-- Создаем таблицу станций stations
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

-- Создаем таблицу агрегатов units
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

-- Создаем таблицу пусков runs
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

-- Создаем таблицу записей вибрации records
create table if not exists records (
    id uuid not null,
    run_id uuid not null,
    point_name varchar(15) default 'point',
    mode_name varchar(20) default 'No-load 100%n',
    mag_vibration double precision not null default 0,
    phase_vibration double precision not null default 0,
    is_used boolean not null default true,
    is_manual_sensitivity boolean not null default false,
    mag_sensitivity double precision,
    phase_sensitivity double precision,
    created_on timestamp(6) default current_timestamp,
    updated_on timestamp(6) default current_timestamp,
    version bigint default 1,
    primary key (id),
    constraint records_runs_id_fk foreign key (run_id) references runs(id)
);
create index idx_records_run_id on records(run_id);

-- Заполняем таблицу пользователей
insert into users (id, email, username, password, priority, role) values
    ('c0a80130-9b6a-1d4e-819b-6a3d93bc0000', 'krajoff@ya.ru', 'krajoff',
     '$2a$12$.YSJGOzqewYrFN4MHGtdjuJdULRaWe7nSsbUx5Hn6EGQnz2D1bsHi', 1, 'ROLE_USER'),
    ('c0a80130-9b6a-1d4e-819b-6a3d9b500002', 'krajon@ya.ru', 'krajon',
     '$2a$12$.YSJGOzqewYrFN4MHGtdjuJdULRaWe7nSsbUx5Hn6EGQnz2D1bsHi', 1, 'ROLE_USER');

-- Заполняем таблицу станций
insert into stations (id, name, user_id) values
    ('c0a80130-9b6a-119c-819b-6a4275e00001','Тестовая станция ГЭС 0', 'c0a80130-9b6a-1d4e-819b-6a3d93bc0000'),
    ('c0a80130-9b6a-119c-819b-6a42989f0002','Воткинская ГЭС', 'c0a80130-9b6a-1d4e-819b-6a3d93bc0000');

-- Заполняем таблицу агрегатов
insert into units (id, unit_number, unit_type, weight_precision, weight_unit_measure, vibration_precision, vibration_unit_measure, station_id) values
    ('c0a80130-9b6a-1463-819b-6a45095c0000',1, 'СВ 477/180-16 УХЛ4', 2, 'кг',
     2, 'мкм', 'c0a80130-9b6a-119c-819b-6a4275e00001'),
    ('c0a80130-9b6a-1463-819b-6a4533ab0001', 2,'СВ 477/180-16 УХЛ4', 2, 'кг',
     2, 'мкм', 'c0a80130-9b6a-119c-819b-6a4275e00001');

-- Заполняем таблицу пусков
insert into runs (id, run_number, reference_run_id, run_parameters, unit_id) values
   ('c0a80130-9b6a-1a53-819b-6a4a751c0000',0, null,
    '{"mag_weight": 0, "phase_weight": 0, "num_plane": 0}'::jsonb, 'c0a80130-9b6a-1463-819b-6a45095c0000'),
   ('c0a80130-9b6a-1a53-819b-6a4abeab0001', 1, 'c0a80130-9b6a-1a53-819b-6a4a751c0000',
    '{"mag_weight": 30, "phase_weight": 0, "num_plane": 1}'::jsonb, 'c0a80130-9b6a-1463-819b-6a45095c0000'),
   ('c0a80130-9b6a-1a53-819b-6a4aff510002', 2, 'c0a80130-9b6a-1a53-819b-6a4a751c0000',
    '{"mag_weight": 100, "phase_weight": 100, "num_plane": 1}'::jsonb, 'c0a80130-9b6a-1463-819b-6a45095c0000'),
   ('c0a80130-9b6a-1a53-819b-6a4b09b00003', 3, 'c0a80130-9b6a-1a53-819b-6a4a751c0000',
    '{"mag_weight": 50, "phase_weight": -90, "num_plane": 1}'::jsonb, 'c0a80130-9b6a-1463-819b-6a45095c0000'),
   ('c0a80130-9b6a-1a53-819b-6a4b11ec0004', 4, 'c0a80130-9b6a-1a53-819b-6a4a751c0000',
    '{"mag_weight": 60, "phase_weight": 90, "num_plane": 1}'::jsonb, 'c0a80130-9b6a-1463-819b-6a45095c0000'),
   ('c0a80130-9b6a-1a53-819b-6a4b19c90005', 5, 'c0a80130-9b6a-1a53-819b-6a4abeab0001',
    '{"mag_weight": 21.2132, "phase_weight": 135, "num_plane": 1}'::jsonb, 'c0a80130-9b6a-1463-819b-6a45095c0000'),
    ('c0a80130-9b6a-1a53-819b-6a4b89c90006', 6, 'c0a80130-9b6a-1a53-819b-6a4b19c90005',
    '{"mag_weight": 21.2132, "phase_weight": 0, "num_plane": 1}'::jsonb, 'c0a80130-9b6a-1463-819b-6a45095c0000');

-- Заполняем таблицу записей вибрации
insert into records (id, point_name, mode_name, run_id, mag_vibration, phase_vibration) values
   ('c0a80130-6666-7777-819b-6a4a751c0000', 'ВГП', 'No-load 100%n', 'c0a80130-9b6a-1a53-819b-6a4a751c0000', 100, -30),
   ('c0a80130-6666-7777-819b-6a4a751c0001', 'ВГП', 'No-load 100%n', 'c0a80130-9b6a-1a53-819b-6a4abeab0001', 126.8706, -23.21),
   ('c0a80130-6666-7777-819b-6a4a751c0002', 'ВГП', 'No-load 100%n', 'c0a80130-9b6a-1a53-819b-6a4aff510002', 84.5237, 35),
   ('c0a80130-6666-7777-819b-6a4a751c0003', 'ВГП', 'No-load 100%n', 'c0a80130-9b6a-1a53-819b-6a4b09b00003', 132.2876, -49.1066),
   ('c0a80130-6666-7777-819b-6a4a751c0004','ВГП', 'No-load 100%n', 'c0a80130-9b6a-1a53-819b-6a4b11ec0004', 39.3918, 75.0694),
   ('c0a80130-6666-7777-819b-6a4a751c0005','ВГП', 'No-load 100%n', 'c0a80130-9b6a-1a53-819b-6a4b19c90005', 107.462, -19.0078),
   ('c0a80130-6666-7777-819b-6a4a751c0006','ВГП', 'No-load 100%n', 'c0a80130-9b6a-1a53-819b-6a4b89c90006', 88.882, -13.0039);
