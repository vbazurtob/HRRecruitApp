\c "HRRecruitApp";

SET search_path TO "vbazurtobPortfolio";


INSERT INTO "vbazurtobPortfolio".job_type ("id", description) VALUES (DEFAULT, 'IT');
INSERT INTO "vbazurtobPortfolio".job_type ("id", description) VALUES (DEFAULT, 'Administrative');
INSERT INTO "vbazurtobPortfolio".job_type ("id", description) VALUES (DEFAULT, 'Sales');
INSERT INTO "vbazurtobPortfolio".job_type ("id", description) VALUES (DEFAULT, 'Customer Support');
INSERT INTO "vbazurtobPortfolio".job_type ("id", description) VALUES (DEFAULT, 'Field');
INSERT INTO "vbazurtobPortfolio".job_type ("id", description) VALUES (DEFAULT, 'Technical Support');



-- Demo User App
INSERT INTO "vbazurtobPortfolio".applicant(
            username, password, names, lastname, address1, address2, country, 
            state, zipcode, email)
    VALUES ('abc', '$2b$10$OKpfQs/kMErTVykVNDgPHOxAatoaslUSetOdnvm41D09TIBEzSMSm', 'ABC', 'Demo U.', 'St', '2005', 'NZ', 
            'RR', 'X45644', 'abc@abc.com');
