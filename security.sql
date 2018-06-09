DO
$body$
BEGIN
   IF NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_user
      WHERE  usename = 'hrapp_demo') THEN

      CREATE ROLE hrapp_demo LOGIN PASSWORD 'demo';
   END IF;
END
$body$;

-- REVOKE CONNECT ON DATABASE "HRRecruitApp" FROM PUBLIC;
GRANT CONNECT ON DATABASE "HRRecruitApp" TO hrapp_demo;

-- REVOKE ALL
-- ON ALL TABLES IN SCHEMA public 
-- FROM PUBLIC;

GRANT ALL 
ON ALL TABLES IN SCHEMA "vbazurtobPortfolio" 
TO hrapp_demo;

GRANT USAGE ON SCHEMA "vbazurtobPortfolio" 
TO hrapp_demo; -- For viewing data in pgAdmin

ALTER USER hrapp_demo SET search_path to "vbazurtobPortfolio";

-- Grant usage on sequences
GRANT USAGE ON SEQUENCE "vbazurtobPortfolio".job_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtobPortfolio".job_type_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtobPortfolio".applicant_academics_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtobPortfolio".applicant_work_experience_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtobPortfolio".applicant_skills_id_seq TO hrapp_demo;
GRANT USAGE ON SEQUENCE "vbazurtobPortfolio".job_applicant_id_seq TO hrapp_demo;




ALTER DEFAULT PRIVILEGES 
    FOR ROLE hrapp_demo 
    IN SCHEMA "vbazurtobPortfolio" 
    GRANT ALL ON TABLES TO hrapp_demo;
 
