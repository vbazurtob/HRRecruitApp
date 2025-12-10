/*
 * Personal License Agreement
 * Copyright Notice
 *
 * © 2025 Voltaire Bazurto Blacio. All rights reserved.
 * License Terms
 *
 *     Ownership: All code contained in this portfolio is the sole property of Voltaire Bazurto Blacio and is hereby copyrighted by me.
 *
 *     Permitted Use: Others are welcome to view and study the code for personal, educational, or non-commercial purposes. You may share insights or information about the code, but you cannot use it for any commercial products, either as-is or in a derivative form.
 *
 *     Restrictions: The code may not be used, reproduced, or distributed for commercial purposes under any circumstances without my explicit written permission.
 *
 *     Rights Reserved: I reserve the right to use the code, or any future versions thereof, for my own purposes in any way I choose, including but not limited to the development of future commercial derivative works under my name or personal brand.
 *
 *     Disclaimer: The code is provided "as is" without warranty of any kind, either express or implied. I am not responsible for any damages resulting from the use of this code.
 *
 * By accessing this portfolio, you agree to abide by these terms.
 */

SET search_path TO "vbazurtobPortfolio";

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".job_type (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".job (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(150),
    description TEXT,
    job_type_id INTEGER NOT NULL REFERENCES "vbazurtobPortfolio".job_type(id),
    salary INTEGER,
    date_posted DATE,
    status CHAR(1) -- O open, C closed
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".applicant (
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

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".applicant_academics (
    id BIGSERIAL PRIMARY KEY,
    applicant_id VARCHAR(35) NOT NULL REFERENCES "vbazurtobPortfolio".applicant(username),
    institution VARCHAR(255) NOT NULL,
    started DATE NOT NULL,
    finished DATE,
    in_progress CHAR(1) DEFAULT 'N',
    degree_type VARCHAR(80) NOT NULL,
    degree_name VARCHAR(150) NOT NULL
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".applicant_work_experience (
    id BIGSERIAL PRIMARY KEY,
    applicant_id VARCHAR(35) NOT NULL REFERENCES "vbazurtobPortfolio".applicant(username),
    institution VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    started DATE NOT NULL,
    finished DATE,
    in_progress CHAR(1) DEFAULT 'N'
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".applicant_skills (
    id BIGSERIAL PRIMARY KEY,
    applicant_id VARCHAR(35) NOT NULL REFERENCES "vbazurtobPortfolio".applicant(username),
    name VARCHAR(100) NOT NULL,
    proficiency INTEGER NOT NULL -- 1 - 5 Scale
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".job_applicant (
    id BIGSERIAL PRIMARY KEY,
    applicant_id VARCHAR(35) NOT NULL REFERENCES "vbazurtobPortfolio".applicant(username),
    job_id BIGINT NOT NULL REFERENCES "vbazurtobPortfolio".job(id),
    date_application_sent TIMESTAMP
);

CREATE TABLE IF NOT EXISTS "vbazurtobPortfolio".hr_user (
    username VARCHAR(35) PRIMARY KEY,
    password VARCHAR(128),
    names VARCHAR(100),
    lastname VARCHAR(100),
    email VARCHAR(45),
    role VARCHAR(15)
);
