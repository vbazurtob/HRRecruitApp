-- Personal License Agreement
-- Copyright Notice
--
-- © 2025 Voltaire Bazurto Blacio. All rights reserved.
-- License Terms
--
-- Ownership: All code contained in this portfolio is the sole property of Voltaire Bazurto Blacio and is hereby copyrighted by me.
--
-- Permitted Use: Others are welcome to view and study the code for personal, educational, or non-commercial purposes. You may share insights or information about the code, but you cannot use it for any commercial products, either as-is or in a derivative form.
--
-- Restrictions: The code may not be used, reproduced, or distributed for commercial purposes under any circumstances without my explicit written permission.
--
-- Rights Reserved: I reserve the right to use the code, or any future versions thereof, for my own purposes in any way I choose, including but not limited to the development of future commercial derivative works under my name or personal brand.
--
-- Disclaimer: The code is provided "as is" without warranty of any kind, either express or implied. I am not responsible for any damages resulting from the use of this code.

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

 
