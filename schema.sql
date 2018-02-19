

CREATE SCHEMA IF NOT EXISTS "vbazurtobPortfolio";


CREATE DATABASE "HRRecruitApp";

\c "HRRecruitApp";

SET search_path TO "vbazurtobPortfolio,public";


CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".job_type(
    id SERIAL PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".job(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150),
    description TEXT,
    job_type_id INTEGER NOT NULL REFERENCES "vbazurtobPortfolio".job_type(id),
    salary INTEGER 
);



CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".applicant(
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

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".applicant_academics(
    applicant_id VARCHAR(35) NOT NULL REFERENCES "vbazurtobPortfolio".applicant(username),
    institution VARCHAR(255) NOT NULL,
    started DATE NOT NULL,
    finished DATE NOT NULL,
    degree_type VARCHAR(80) NOT NULL,
    degree_name VARCHAR(150) NOT NULL,
    PRIMARY KEY(applicant_id, institution, started, finished, degree_type, degree_name )
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".applicant_work_experience(
    applicant_id VARCHAR(35) NOT NULL REFERENCES "vbazurtobPortfolio".applicant(username),
    institution VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    started DATE NOT NULL,
    finished DATE NOT NULL,
    PRIMARY KEY(applicant_id, institution, position, started, finished)
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".applicant_skills(
    applicant_id VARCHAR(35) NOT NULL REFERENCES "vbazurtobPortfolio".applicant(username),
    name VARCHAR(100) NOT NULL,
    proficiency INTEGER NOT NULL, -- 1 - 5 Scale    
    PRIMARY KEY(applicant_id, name)
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".job_applicant(
    
    applicant_id VARCHAR(35) NOT NULL REFERENCES "vbazurtobPortfolio".applicant (username),
    job_id BIGINT NOT NULL REFERENCES "vbazurtobPortfolio".job(id),
    date_application_sent TIMESTAMP,
    
    PRIMARY KEY(applicant_id, job_id)
);
