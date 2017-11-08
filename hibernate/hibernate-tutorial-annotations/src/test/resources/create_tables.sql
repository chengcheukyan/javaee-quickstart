CREATE TABLE person (person_id bigint, name varchar(255), primary key(person_id));
CREATE TABLE phone (phone_id bigint, phone_num varchar(255), person_id bigint, primary key(phone_id));
CREATE TABLE house (house_id bigint, name varchar(255), primary key(house_id));
CREATE TABLE room (room_id bigint, purpose varchar(255), house_id bigint, primary key(room_id));
CREATE TABLE houseroom (room_id bigint,  house_id bigint);