/*CREATE SCHEMA studentdemo DEFAULT CHARACTER SET utf8mb4  DEFAULT COLLATE utf8mb4_danish_ci;*/

use studentdemo;

drop view if exists vansatt;
drop view if exists vstudent;

DROP TABLE IF EXISTS dokument;
DROP TABLE IF EXISTS kontakt;
DROP TABLE IF EXISTS student_steg;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS ansatt;
DROP TABLE IF EXISTS bruker;
DROP TABLE IF EXISTS brukertype;
DROP TABLE IF EXISTS utdanningssteg;
DROP TABLE IF EXISTS dokumenttype;
DROP TABLE IF EXISTS kontakttype;

CREATE TABLE kontakttype (
  kontakttype_id SMALLINT NOT NULL,
  kontakttype_nv VARCHAR(45) NULL,
  PRIMARY KEY (kontakttype_id));

CREATE TABLE dokumenttype (
  dokumenttype_id SMALLINT NOT NULL,
  dokumenttype_nv VARCHAR(45) NULL,
  dokumenttype_mal VARCHAR(255) NULL,
  PRIMARY KEY (dokumenttype_id));

CREATE TABLE utdanningssteg (
  utdanningssteg_id SMALLINT NOT NULL,
  utdanningssteg_nv VARCHAR(45) NULL,
  PRIMARY KEY (utdanningssteg_id));
 
CREATE TABLE brukertype (
  brukertype_id tinyint NOT NULL,
  brukertype_nv varchar(45) NOT NULL,
  PRIMARY KEY (brukertype_id)
);

CREATE TABLE bruker (
  bruker_id SMALLINT NOT NULL,
  brukertype_id tinyint NOT NULL,
  bruker_nv varchar(45) NOT NULL,
  passord varchar(45) NOT NULL,
  opprettet_dt datetime not null,
  endret_dt datetime not null,
  PRIMARY KEY (bruker_id)
);

alter table bruker
  add CONSTRAINT FK_bruker_brukertype FOREIGN KEY FK_bruker_brukertype (brukertype_id) 
  REFERENCES brukertype (brukertype_id)
  ON DELETE RESTRICT 
  ON UPDATE RESTRICT
;

ALTER TABLE bruker 
ADD UNIQUE INDEX UQ_bruker_nv (bruker_nv ASC) VISIBLE;

create table ansatt (
	ansatt_id SMALLINT NOT NULL,
    bruker_id SMALLINT NOT NULL,
	ansatt_nv VARCHAR(255) NOT NULL,
	mail_id varchar(45),
    opprettet_dt datetime not null,
    endret_dt datetime not null,
	PRIMARY KEY (ansatt_id)
);  

alter table ansatt
 ADD CONSTRAINT FK_ansatt_bruker FOREIGN KEY FK_ansatt_bruker (bruker_id)
    REFERENCES bruker (bruker_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT; 
  
CREATE TABLE student (
  student_id SMALLINT NOT NULL,
  bruker_id SMALLINT NOT NULL,
  student_nv VARCHAR(255) NOT NULL,
  mail_id varchar(45),
  discord_id varchar(45),
  github_id varchar(45),
  opprettet_dt datetime not null,
  endret_dt datetime not null,
PRIMARY KEY (student_id)
);

alter table student
 ADD CONSTRAINT FK_student_bruker FOREIGN KEY FK_student_bruker (bruker_id)
    REFERENCES bruker (bruker_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT; 
    
create table student_steg (
		student_steg_id int not null,
		student_id SMALLINT NOT NULL,
        utdanningssteg_id SMALLINT not null,
        dato_fra date not null,
        dato_til date null,
primary key (student_steg_id)
);        

alter table student_steg
 ADD CONSTRAINT FK_studentsteg_student FOREIGN KEY FK_studentsteg_student (student_id)
    REFERENCES student (student_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT; 

alter table student_steg
 ADD CONSTRAINT FK_studentsteg_steg FOREIGN KEY FK_studentsteg_steg (utdanningssteg_id)
    REFERENCES utdanningssteg (utdanningssteg_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT; 
   
create table kontakt (student_steg_id INT NOT NULL,
					  ansatt_id SMALLINT NOT NULL,
					  kontakttype_id SMALLINT NOT NULL,
primary key (student_steg_id, kontakttype_id)                      
); 
					    
ALTER TABLE kontakt
 ADD CONSTRAINT FK_kontakt_kontakttype FOREIGN KEY FK_kontakt_kontakttype (kontakttype_id)
    REFERENCES kontakttype (kontakttype_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT; 
					    
ALTER TABLE kontakt
 ADD CONSTRAINT FK_kontakt_ansatt FOREIGN KEY FK_kontakt_ansatt (ansatt_id)
    REFERENCES ansatt (ansatt_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT; 
					    
ALTER TABLE kontakt
 ADD CONSTRAINT FK_kontakt_steg FOREIGN KEY FK_kontakt_steg (student_steg_id)
    REFERENCES student_steg (student_steg_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT; 
    
create table dokument (
     dokument_id INT NOT NULL,
     student_steg_id INT NOT NULL,
     dokumenttype_id SMALLINT not null,
     url varchar(255),
PRIMARY KEY (dokument_id));
    
ALTER TABLE dokument
 ADD CONSTRAINT FK_dokument_studentsteg FOREIGN KEY FK_dokument_studentsteg (student_steg_id)
    REFERENCES student_steg (student_steg_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;   
    
ALTER TABLE dokument
 ADD CONSTRAINT FK_dokument_dokumenttype FOREIGN KEY FK_dokument_dokumenttype (dokumenttype_id)
    REFERENCES dokumenttype (dokumenttype_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;  
    
create view vansatt as
select bruker.bruker_id, brukertype_id, ansatt_id, ansatt_nv, mail_id, bruker_nv, ansatt.opprettet_dt, ansatt.endret_dt
from ansatt
left outer join bruker using (bruker_id);    
    
create view vstudent as
select bruker.bruker_id, brukertype_id, student.student_id, student_nv, mail_id, discord_id, github_id, bruker_nv, utdanningssteg_id, student.opprettet_dt, student.endret_dt
from student
left outer join bruker using (bruker_id)
left outer join student_steg on student.student_id = student_steg.student_id and now() between dato_fra and ifnull(dato_til,'2099-12-31');    
    
insert into utdanningssteg values(0, 'Ikke satt');    
insert into utdanningssteg values(1, '1. klasse');    
insert into utdanningssteg values(2, '2. klasse');    
insert into utdanningssteg values(3, '3. klasse');    
insert into utdanningssteg values(4, 'Uteksaminert');
insert into utdanningssteg values(99, 'Sluttet');  

insert into dokumenttype values(0, 'Ukjent', null);    
insert into dokumenttype values(1, 'Kontaktpunkt', null);    
insert into dokumenttype values(2, 'Opplæringsplan', null);    
insert into dokumenttype values(9, 'Vitnemål', null);  

insert into kontakttype values (0, 'Ikke satt');
insert into kontakttype values (1, 'Faglærer');
insert into kontakttype values (2, 'Stjernemaker');

insert into brukertype values (1, 'Student');
insert into brukertype values (2, 'Lærer');

insert into bruker values(1, 2, 'sigmund', 'freud', now(), now());    
insert into bruker values(2, 2, 'marie', 'curie', now(), now());   
insert into bruker values(3, 2, 'emmanuel', 'kant', now(), now());   
insert into bruker values(4, 2, 'isaac', 'newton', now(), now());   
insert into bruker values(5, 2, 'albert', 'einstein', now(), now());   
insert into bruker values(6, 1, 'petter', 'smart', now(), now());   
insert into bruker values(7, 1, 'helene', 'hare', now(), now());   

insert into ansatt values(1, 1, 'Sigmund', 'sigmund', now(), now());     
insert into ansatt values(2, 2, 'Marie', 'marie', now(), now());     
insert into ansatt values(3, 3, 'Emmanuel', 'emmanuel', now(), now());    
insert into ansatt values(4, 4, 'Isaac', 'isaac', now(), now());    
insert into ansatt values(5, 5, 'Albert', 'albert', now(), now());    

insert into student values(1, 6, 'Petter Smart', 'petter', 'discord1', 'github1', now(), now());     
insert into student values(2, 7, 'Helene Harefrøken', 'helene', 'discord2', 'github2', now(), now());     

insert into student_steg values (1, 1, 1, '2023-09-01', '2024-04-30');
insert into student_steg values (2, 2, 2, '2024-01-01', null); 

insert into kontakt values (1, 4, 1);
insert into kontakt values (1, 1, 2);
insert into kontakt values (2, 3, 2);
insert into kontakt values (2, 2, 1);