CREATE TABLE UserData(User_ID int, email TEXT, username TEXT, password TEXT, isAdmin TEXT, extra1 TEXT, extra2 TEXT, PRIMARY KEY(User_ID));

CREATE TABLE userEventAccess (uea int, uid int, EventName TEXT, PRIMARY KEY(uea), FOREIGN KEY (uid) REFERENCES UserData(User_ID));

CREATE TABLE userOrgAccess (uoa int, uid int, OrganizationName TEXT, PRIMARY KEY(uoa), FOREIGN KEY (uid) REFERENCES UserData(User_ID));

CREATE TABLE mail (ID int, EventID int, OrgID int, Send_at TEXT, Sender TEXT, Sender_email TEXT, PRIMARY KEY(ID));

CREATE TABLE Document (did int, mail_ID int NOT NULL, subject TEXT, content TEXT, PRIMARY KEY(did), FOREIGN KEY (mail_ID) REFERENCES mail(ID));

CREATE TABLE DocEventAccess (dea int, did int, EventName TEXT, PRIMARY KEY(dea), FOREIGN KEY (did) REFERENCES Document(did));

CREATE TABLE DocOrgAccess (doa int, did int, OrganizationName TEXT, PRIMARY KEY(doa), FOREIGN KEY (did) REFERENCES Document(did));

CREATE TABLE Attachment (did int, AttachmentLink TEXT, PRIMARY KEY(did), FOREIGN KEY (did) REFERENCES Document(did));