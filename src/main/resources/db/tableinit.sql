drop table if exists url_info;
drop table if exists member;

create table member (member_id bigint not null auto_increment, email varchar(255) not null, password varchar(255) not null, primary key (member_id));
create table url_info (url_info_id bigint not null auto_increment, created_at datetime(6), fake_url varchar(255), open_status bit not null, real_url longtext not null, visit_count bigint default 0, member_id bigint, primary key (url_info_id));