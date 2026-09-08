-- SkillSphere Nexus — Milestone 1 Database Seed Script

-- 1. Create Core Tables (If not managed by JPA Hibernate ddl-auto)
CREATE TABLE IF NOT EXISTS employees (
    emp_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    title VARCHAR(255),
    department VARCHAR(255),
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS skills (
    skill_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) UNIQUE NOT NULL,
    category VARCHAR(50) NOT NULL,
    level VARCHAR(50),
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS employee_skills (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    emp_id UUID NOT NULL REFERENCES employees(emp_id) ON DELETE CASCADE,
    skill_id UUID NOT NULL REFERENCES skills(skill_id) ON DELETE CASCADE,
    proficiency INT CHECK (proficiency BETWEEN 1 AND 10),
    experience_years INT DEFAULT 1,
    verified BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS certifications (
    cert_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    emp_id UUID NOT NULL REFERENCES employees(emp_id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    credential_id VARCHAR(255),
    issued DATE,
    expiry DATE,
    status VARCHAR(50) DEFAULT 'VALID'
);

CREATE TABLE IF NOT EXISTS assessments (
    assess_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    emp_id UUID NOT NULL REFERENCES employees(emp_id) ON DELETE CASCADE,
    skill_id UUID REFERENCES skills(skill_id) ON DELETE SET NULL,
    title VARCHAR(255),
    score FLOAT CHECK (score BETWEEN 0 AND 100),
    passed BOOLEAN,
    verified BOOLEAN DEFAULT FALSE,
    assessment_date DATE
);

CREATE TABLE IF NOT EXISTS competency_frameworks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role_title VARCHAR(255) NOT NULL,
    skill_id UUID REFERENCES skills(skill_id) ON DELETE CASCADE,
    required_proficiency INT CHECK (required_proficiency BETWEEN 1 AND 10),
    department VARCHAR(255),
    target_proficiency_summary TEXT,
    min_required_score INT
);

-- 2. Seed Initial Milestone 1 Baseline Data
INSERT INTO employees (emp_id, name, email, password, title, department, role)
VALUES 
    ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'John Smith', 'john.smith@skillsphere.com', 'password123', 'Senior Developer', 'Engineering', 'DEVELOPER'),
    ('b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22', 'Sarah Connor', 'sarah.hr@skillsphere.com', 'hrpass123', 'HR Talent Lead', 'Human Resources', 'HR'),
    ('c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33', 'Alex Vance', 'alex.vance@skillsphere.com', 'alex123', 'Cloud Architect', 'Infrastructure', 'TECH_LEAD')
ON CONFLICT (email) DO NOTHING;

INSERT INTO skills (skill_id, name, category, level, description, is_active)
VALUES
    ('d3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44', 'Java', 'TECHNICAL', 'Advanced', 'Enterprise Java Development & Microservices', true),
    ('e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a55', 'Spring Boot', 'TECHNICAL', 'Advanced', 'Cloud Native Web APIs & JPA', true),
    ('f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a66', 'Angular', 'TECHNICAL', 'Advanced', 'Single Page Web App Architecture', true),
    ('a6eebc99-9c0b-4ef8-bb6d-6bb9bd380a77', 'AWS Architecture', 'TECHNICAL', 'Expert', 'Cloud Infrastructure & S3/EC2/RDS', true),
    ('b7eebc99-9c0b-4ef8-bb6d-6bb9bd380a88', 'Agile Leadership', 'DOMAIN', 'Expert', 'Scrum & Sprint Delivery Management', true),
    ('c8eebc99-9c0b-4ef8-bb6d-6bb9bd380a99', 'Communication & Mentorship', 'SOFT', 'Advanced', 'Cross-team collaboration & public speaking', true)
ON CONFLICT (name) DO NOTHING;

INSERT INTO employee_skills (id, emp_id, skill_id, proficiency, experience_years, verified)
VALUES
    (gen_random_uuid(), 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44', 8, 5, true),
    (gen_random_uuid(), 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'e4eebc99-9c0b-4ef8-bb6d-6bb9bd380a55', 7, 4, true),
    (gen_random_uuid(), 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a66', 5, 2, true);

INSERT INTO certifications (cert_id, emp_id, name, credential_id, issued, expiry, status)
VALUES
    (gen_random_uuid(), 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'AWS Solutions Architect Associate', 'AWS-SAA-88392', CURRENT_DATE - INTERVAL '1 year', CURRENT_DATE + INTERVAL '2 years', 'VALID'),
    (gen_random_uuid(), 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Java OCP 17 Professional', 'ORCL-OCP-10492', CURRENT_DATE - INTERVAL '3 years', CURRENT_DATE - INTERVAL '2 months', 'EXPIRED');

INSERT INTO assessments (assess_id, emp_id, skill_id, title, score, passed, verified, assessment_date)
VALUES
    (gen_random_uuid(), 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44', 'Enterprise Java & Microservices Assessment Q3', 87.0, true, true, CURRENT_DATE - INTERVAL '2 weeks');

INSERT INTO competency_frameworks (id, role_title, skill_id, required_proficiency, department, target_proficiency_summary, min_required_score)
VALUES
    (gen_random_uuid(), 'Tech Lead', 'd3eebc99-9c0b-4ef8-bb6d-6bb9bd380a44', 9, 'Engineering', 'Mastery in Java backend architectures', 85),
    (gen_random_uuid(), 'Tech Lead', 'f5eebc99-9c0b-4ef8-bb6d-6bb9bd380a66', 8, 'Engineering', 'Proficient in Angular 20 SPA frontends', 80),
    (gen_random_uuid(), 'Tech Lead', 'b7eebc99-9c0b-4ef8-bb6d-6bb9bd380a88', 7, 'Engineering', 'Strong Agile process leadership', 75);
