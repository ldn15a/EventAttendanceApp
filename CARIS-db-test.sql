-- This is a Database schema for use in the Event Attendance App.
-- It keeps track of all members in a contact list, what sessions they've attended, and what lessions are taught at what session.

drop database if exists CARIS;
create database CARIS;

use CARIS;

create table members -- keeps track of all members who might attend sessions. Individual member data is saved in Google's local contacts.
(
    contact varchar(255) NOT NULL, -- matches user's gmail account name
    PRIMARY KEY (contact)
);

create table lessons -- keeps track of the different lessons that users can teach.
(
	lessonID int unsigned NOT NULL,
    lessonName varchar(255),
    PRIMARY KEY (lessonID)
);

create table sessions -- keeps track of all individual meetings where lessons are taught. Is linked to lessons so that we can tell what lesson is being taught at the session.
(
	sessionID int unsigned NOT NULL,
    lesson int unsigned NOT NULL,
    sessionDate datetime, -- format: YYYY-MM-DD HH:MI:SS
    sessionLocation varchar(255),
    PRIMARY KEY (lesson, sessionDate, sessionLocation), -- this allows multiple sessions to occur at the same time in different places, or in the same place at different times.
    CONSTRAINT FOREIGN KEY (lesson) REFERENCES lessons(lessonID)
);

create table attendeeList -- Keeps track of all members attending any given session.
(
	member varchar(255) NOT NULL,
    sessionAttended int unsigned NOT NULL,
    PRIMARY KEY (member, sessionAttended),
    CONSTRAINT FOREIGN KEY (member) REFERENCES members(contact)
);