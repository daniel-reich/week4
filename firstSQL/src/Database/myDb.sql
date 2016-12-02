CREATE TABLE teacher (
  teacherId INT,
  firstName VARCHAR(30),
  lastName VARCHAR(30),
  age INT,
  salary DECIMAL
);

CREATE TABLE student (
  studentId INT,
  firstName VARCHAR(30),
  lastName VARCHAR(30),
  grade INT,
  gpa DECIMAL
);

CREATE TABLE class(
  classId INT,
  name VARCHAR(30),
  description VARCHAR(30),
  teacherId INT
);

CREATE TABLE classStudent(
  classStudentId INT,
  classId INT,
  studentId INT
);

INSERT INTO teacher VALUES
  (541,'Mary','Smith',37,45000),
  (542,'Richard','Jorgensen',59,66000),
  (543,'Samantha','Myers',27,36000);

INSERT INTO class VALUES
  (1001,'Chemistry','Intro to chemistry',541),
  (1007,'Biology','Intro to biology',541),
  (2013,'English','Grammer and composition',542),
  (2014,'British Literature','Survey of 19th century British lit',542),
  (1002,'Organic Chemistry','Second year chemistry class',543),
  (3355,'Woodshop','Projects in wood',543);

INSERT INTO student VALUES
  (606,'Martha','Jones',9,3.9),
  (607,'Clark','Gates',9,4.0),
  (608,'Sebastion','Rodriguez',11,3.8),
  (609,'Aubree','Hamilton',12,2.5),
  (610,'Jesus','Ramirez',11,3.0),
  (611,'Sally','Fields',9,2.3),
  (612,'Bernardo','OHiggins',12,1.9),
  (613,'Ramon','Friere',12,3.4),
  (614,'Federico','Errasuriz',110,3.6),
  (615,'Jose','Ibanez',10,4.0);

INSERT INTO classStudent VALUES
  (14763,1001,606),
  (14764,1007,607),
  (14765,2013,608),
  (14766,2014,609),
  (14767,1002,610),
  (14768,3355,611),
  (14769,1001,607),
  (14770,1007,608),
  (14771,2013,609),
  (14772,2014,610),
  (14773,1002,611),
  (14774,3355,612),
  (14775,1001,608),
  (14776,1007,609),
  (14777,2013,610),
  (14778,2014,611),
  (14779,1002,612),
  (14780,3355,613),
  (14781,1001,609),
  (14782,1007,610),
  (14783,2013,611),
  (14784,2014,612),
  (14785,1002,613),
  (14786,3355,614),
  (14787,1001,610),
  (14788,1007,611),
  (14789,2013,612),
  (14790,2014,613),
  (14791,1002,614),
  (14792,3355,615);

UPDATE teacher SET firstName='Jumbo', lastName='Jim' WHERE teacherId = 542;
UPDATE class SET name='Blowing Bubbles 101' WHERE classId = 1001;
UPDATE student SET firstName='Saca', lastName='Tooya' WHERE StudentId = 606;
DELETE FROM student WHERE studentId = 615;

SELECT teacher.firstName, teacher.lastName, class.name
FROM teacher
INNER JOIN class
ON teacher.teacherId=class.teacherID
