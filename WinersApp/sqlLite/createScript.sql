.mode columns
.headers on
CREATE TABLE users (
  Email_Address TEXT PRIMARY KEY,
  Password_Hash TEXT,
  Date_Created TEXT,
  Contributor NUMERIC
);

CREATE TABLE wines (
  Wine_ID NUMERIC PRIMARY KEY,
  Winery_ID NUMERIC,
  Color TEXT,
  Country TEXT,
  Grape_Variety TEXT,
  Region TEXT,
  Vineyard TEXT,
  Year TEXT,
  Wine_Name TEXT,
  Price NUMERIC,
  URL_of_Photo TEXT,
  User_Entered TEXT
);

CREATE TABLE events (
  Event_ID NUMERIC PRIMARY KEY,
  Start_Date TEXT,
  End_Date TEXT,
  Start_Time TEXT,
  End_Time TEXT,
  Type TEXT,
  Repeat_Frequency TEXT,
  Title TEXT,
  Description TEXT,
  Duration_Days NUMERIC,
  Event_Occurance_Count NUMERIC
);

CREATE TABLE my_wines (
  Email_Address TEXT,
  Wine_ID NUMERIC
);

CREATE TABLE user_calendar (
  Email_Address TEXT,
  Event_ID NUMERIC
);

CREATE TABLE wine_descriptors (
  Wine_ID NUMERIC,
  Season NUMERIC,
  Body NUMERIC,
  Acidic NUMERIC,
  Acrid NUMERIC,
  Ageworthy NUMERIC,
  Aggressive NUMERIC,
  Alcoholic NUMERIC,
  Aromatic NUMERIC,
  Astringent NUMERIC,
  Austere NUMERIC,
  Awkward NUMERIC,
  Full_description TEXT
);

CREATE TABLE wineries (
  Winery_ID NUMERIC PRIMARY KEY,
  Name TEXT,
  Address TEXT,
  City TEXT,
  Zip NUMERIC,
  URL_of_Photo TEXT,
  Open_Mondays NUMERIC,
  Open_Tuesdays NUMERIC,
  Open_Wednesdays NUMERIC,
  Open_Thursdays NUMERIC,
  Open_Fridays NUMERIC,
  Open_Saturdays NUMERIC,
  Open_Sundays NUMERIC,
  Close_Mondays NUMERIC,
  Close_Tuesdays NUMERIC,
  Close_Wednesdays NUMERIC,
  Close_Thursdays NUMERIC,
  Close_Fridays NUMERIC,
  Close_Saturdays NUMERIC,
  Close_Sundays NUMERIC
);
