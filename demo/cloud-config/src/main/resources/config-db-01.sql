CREATE SEQUENCE IF NOT EXISTS HIBERNATE_SEQUENCE;
CREATE TABLE IF NOT EXISTS PROPERTIES
(
    APPLICATION   varchar(255)      not null,
    PROFILE       varchar(255) ,
    LABEL         varchar(255) ,
    KEY          varchar(255)       not null,
    VALUE        varchar(1024)       not null,
   -- LAST_ACCESSED TIMESTAMP not null,
    ID       bigint,
    CRUTCH       varchar(1),
    primary key (id)
);

comment on table PROPERTIES is 'cloud-config properties';

DROP TABLE IF EXISTS SOURCES;
CREATE TABLE SOURCES
(
    APPLICATION   varchar(255) not null,
    PROFILE       varchar(255) ,
    LABEL         varchar(255) ,
    FILENAME         varchar(255) primary key
);


insert into SOURCES values ('application', 'dev', null, 'application-dev.yml');
insert into SOURCES values ('core-data', 'dev', null, 'core-data-dev.yml');
insert into SOURCES values ('service-adapter-gitlab', 'dev', null, 'service-adapter-gitlab-dev.yml');
insert into SOURCES values ('service-adapter-jenkins-ci', 'dev', null, 'service-adapter-jenkins-ci-dev.yml');
insert into SOURCES values ('service-adapter-jenkins-cd', 'dev', null, 'service-adapter-jenkins-cd-dev.yml');
insert into SOURCES values ('ssd-role-manager', 'dev', null, 'ssd-role-manager-dev.yml');
insert into SOURCES values ('ssd-transaction-manager', 'dev', null, 'ssd-transaction-manager-dev.yml');
insert into SOURCES values ('ssd-ui-admin', 'dev', null, 'ssd-ui-admin-dev.yml');

insert into SOURCES values ('application', 'demo', null, 'application-demo.yml');
insert into SOURCES values ('core-data-demo', 'demo', null, 'core-data-demo-demo.yml');
insert into SOURCES values ('service-adapter-gitlab-demo', 'demo', null, 'service-adapter-gitlab-demo-demo.yml');
insert into SOURCES values ('service-adapter-jenkins-ci-demo', 'demo', null, 'service-adapter-jenkins-ci-demo-demo.yml');
insert into SOURCES values ('service-adapter-jenkins-cd-demo', 'demo', null, 'service-adapter-jenkins-cd-demo-demo.yml');
insert into SOURCES values ('ssd-role-manager-demo', 'demo', null, 'ssd-role-manager-demo-demo.yml');
insert into SOURCES values ('ssd-transaction-manager-demo', 'demo', null, 'ssd-transaction-manager-demo-demo.yml');
insert into SOURCES values ('ssd-ui-admin-demo', 'demo', null, 'ssd-ui-admin-demo-demo.yml');

insert into SOURCES values ('ssd-ui-admin', 'local', null, 'ssd-ui-admin-local.yml');


