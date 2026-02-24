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

-- REVOKE ALL
-- ON ALL TABLES IN SCHEMA public 
-- FROM PUBLIC;

GRANT ALL 
ON ALL TABLES IN SCHEMA "vbazurtob_portfolio" 
TO hrapp_demo;

GRANT USAGE ON SCHEMA "vbazurtob_portfolio" 
TO hrapp_demo; -- For viewing data in pgAdmin

ALTER USER hrapp_demo SET search_path to "vbazurtob_portfolio";

-- Grant usage on sequences
GRANT USAGE ON SEQUENCE "vbazurtob_portfolio".job_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtob_portfolio".job_type_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtob_portfolio".applicant_academics_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtob_portfolio".applicant_work_experience_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtob_portfolio".applicant_skills_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtob_portfolio".job_applicant_id_seq TO hrapp_demo;




ALTER DEFAULT PRIVILEGES 
    FOR ROLE hrapp_demo 
    IN SCHEMA "vbazurtob_portfolio" 
    GRANT ALL ON TABLES TO hrapp_demo;
  
