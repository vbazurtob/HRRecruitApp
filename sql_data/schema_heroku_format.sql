



CREATE DATABASE "HRRecruitApp";




\c "HRRecruitApp";



CREATE TABLE IF NOT EXISTS "public".job_type(
    id SERIAL PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS "public".job(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150),
    description TEXT,
    job_type_id INTEGER NOT NULL REFERENCES "public".job_type(id),
    salary INTEGER,
    date_posted DATE,
    status CHAR(1) -- O open, C closed
);



CREATE TABLE IF NOT EXISTS "public".applicant(
    username VARCHAR(35) PRIMARY KEY,
    password VARCHAR(128),
    names VARCHAR(100),
    lastname VARCHAR(100),
    address1 VARCHAR(150),
    address2 VARCHAR(150),
    country CHAR(2),
    state VARCHAR(100),
    zipcode VARCHAR(10),
    email VARCHAR(45)
    
);

CREATE TABLE IF NOT EXISTS "public".applicant_academics(
    id BIGSERIAL PRIMARY KEY,
    applicant_id VARCHAR(35) NOT NULL REFERENCES "public".applicant(username),
    institution VARCHAR(255) NOT NULL,
    started DATE NOT NULL,
    finished DATE ,
    in_progress CHAR(1) DEFAULT 'N',
    degree_type VARCHAR(80) NOT NULL,
    degree_name VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS "public".applicant_work_experience(
    id BIGSERIAL PRIMARY KEY,
    applicant_id VARCHAR(35) NOT NULL REFERENCES "public".applicant(username),
    institution VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    started DATE NOT NULL,
    finished DATE,
    in_progress CHAR(1) DEFAULT 'N'
);

CREATE TABLE IF NOT EXISTS "public".applicant_skills(
    id BIGSERIAL PRIMARY KEY,
    applicant_id VARCHAR(35) NOT NULL REFERENCES "public".applicant(username),
    name VARCHAR(100) NOT NULL,
    proficiency INTEGER NOT NULL -- 1 - 5 Scale    
);

CREATE TABLE IF NOT EXISTS "public".job_applicant(
    id BIGSERIAL PRIMARY KEY,
    applicant_id VARCHAR(35) NOT NULL REFERENCES "public".applicant (username),
    job_id BIGINT NOT NULL REFERENCES "public".job(id),
    date_application_sent TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "public".hr_user(
    username VARCHAR(35) PRIMARY KEY,
    password VARCHAR(128),
    names VARCHAR(100),
    lastname VARCHAR(100),
    email VARCHAR(45),
    role VARCHAR(15)
); 
