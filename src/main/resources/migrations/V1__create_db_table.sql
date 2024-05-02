CREATE TABLE department
(
    department_id SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    location      VARCHAR(100)
);

CREATE TABLE teacher
(
    teacher_id    SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    department_id INT REFERENCES department (department_id),
    hire_date     DATE
);

CREATE TABLE student
(
    student_id    SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    date_of_birth DATE,
    department_id INT REFERENCES department (department_id),
    teacher_id    INT REFERENCES teacher (teacher_id)
);

CREATE TABLE course
(
    course_id     SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    credits       INT,
    department_id INT REFERENCES department (department_id)
);

CREATE TABLE enrollment
(
    enrollment_id SERIAL PRIMARY KEY,
    student_id    INT REFERENCES student (student_id),
    course_id     INT REFERENCES course (course_id),
    grade         DECIMAL(3, 2),
    CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES student (student_id),
    CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES course (course_id)
);

-- Insert departments
INSERT INTO department (name, location)
VALUES ('Computer Science', 'Building A'),
       ('Mathematics', 'Building B'),
       ('Physics', 'Building C');

-- Insert teachers
INSERT INTO teacher (name, department_id, hire_date)
VALUES ('John Smith', 1, '2020-01-01'),
       ('Jane Doe', 2, '2019-05-15'),
       ('David Johnson', 3, '2021-03-10');

-- Insert students
INSERT INTO student (name, date_of_birth, department_id, teacher_id)
VALUES ('Alice Johnson', '2000-02-15', 1, 1),
       ('Bob Smith', '2001-07-20', 2, 2),
       ('Eva Brown', '2002-04-10', 1, 1),
       ('Max Williams', '2000-11-05', 3, 3);

-- Insert courses
INSERT INTO course (name, credits, department_id)
VALUES ('Introduction to Programming', 3, 1),
       ('Calculus I', 4, 2),
       ('Mechanics', 3, 3);

-- Insert enrollments
INSERT INTO enrollment (student_id, course_id, grade)
VALUES (1, 1, 3.5),
       (2, 2, 4.0),
       (3, 1, 3.7),
       (4, 3, 3.9);
