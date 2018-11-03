-- This is a Database schema for use in the Event Attendance App.
-- It keeps track of all members in a contact list, what sessions they've attended, and what lessions are taught at what session.

drop database if exists CARIS;
create database CARIS;

use CARIS;

create table member -- keeps track of all members who might attend sessions. Individual member data is saved in Google's local contacts.
(
    ID int unsigned NOT NULL,
    firstName varchar(255) NOT NULL, -- matches user's gmail account name
    lastName varchar(255) NOT NULL,
    age int unsigned,
    picture varchar(255),
    notes varchar(511),
    timesVisited int unsigned,
    PRIMARY KEY (ID)
);

create table attendeeList -- Keeps track of all members attending any given session.
(
	member int unsigned NOT NULL,
    dateAttended datetime NOT NULL,
    PRIMARY KEY (member, dateAttended),
    CONSTRAINT FOREIGN KEY (member) REFERENCES members(ID)
);

create table benefits -- keeps track of all individual meetings where lessons are taught. Is linked to lessons so that we can tell what lesson is being taught at the session.
(
	resetTime datetime,
    doesReset int(1) NOT NULL
);