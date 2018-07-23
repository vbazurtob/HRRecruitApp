\c "HRRecruitApp";




INSERT INTO "public".job_type ("id", description) VALUES (DEFAULT, 'IT');
INSERT INTO "public".job_type ("id", description) VALUES (DEFAULT, 'Administrative');
INSERT INTO "public".job_type ("id", description) VALUES (DEFAULT, 'Sales');
INSERT INTO "public".job_type ("id", description) VALUES (DEFAULT, 'Customer Support');
INSERT INTO "public".job_type ("id", description) VALUES (DEFAULT, 'Field');
INSERT INTO "public".job_type ("id", description) VALUES (DEFAULT, 'Technical Support');



-- Demo Applicant user
INSERT INTO "public".applicant(
            username, password, names, lastname, address1, address2, country, 
            state, zipcode, email)
    VALUES ('demo', '$2a$10$5ebPOJeVYLUmxo9jQgiHweQQ4gIRT2iTvx6CeE7X26nRIMrsNkgZS', 'Demo', 'Demo U.', 'St', '2005', 'NZ', 
            'RR', 'X45644', 'demo@abc.com');


INSERT INTO "public".applicant(
            username, password, names, lastname, address1, address2, country, 
            state, zipcode, email)
    VALUES ('abc', '$2a$10$OKpfQs/kMErTVykVNDgPHOxAatoaslUSetOdnvm41D09TIBEzSMSm', 'ABC', 'Demo U.', 'St', '2005', 'NZ', 
            'RR', 'X45644', 'abc@abc.com');
            
            
-- Demo HR user
INSERT INTO "public".hr_user(
            username, password, names, lastname, email, role)
    VALUES ('admin', '$2a$10$RCUBOckyEHolcG1iMLegIeeoQuWO70gKjiQxGWy39RK7T2.D9jufC', 'Admin HR', 'Demo User', 'abc@abc.com', 'HR_ADMIN');

 
