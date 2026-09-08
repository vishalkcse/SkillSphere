package com.skillsphere.config;

import com.skillsphere.entity.*;
import com.skillsphere.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;
    private final EmployeeSkillRepository employeeSkillRepository;
    private final CertificationRepository certificationRepository;
    private final AssessmentRepository assessmentRepository;
    private final CompetencyFrameworkRepository competencyFrameworkRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LearningPathRepository learningPathRepository;
    private final LearningPathCourseRepository learningPathCourseRepository;
    private final CareerPlanRepository careerPlanRepository;
    private final JobRepository jobRepository;

    @Override
    public void run(String... args) throws Exception {
        if (employeeRepository.count() > 0) {
            return;
        }

        // 1. Seed Employees
        Employee john = employeeRepository.save(new Employee(
                "John Smith", "john.smith@skillsphere.com", "password123",
                "Developer", "Engineering", Role.EMPLOYEE
        ));

        Employee sarah = employeeRepository.save(new Employee(
                "Sarah Connor", "sarah.hr@skillsphere.com", "hrpass123",
                "HR Talent Specialist", "Human Resources", Role.HR
        ));

        Employee alex = employeeRepository.save(new Employee(
                "Alex Vance", "alex.vance@skillsphere.com", "alex123",
                "Cloud Architect", "Infrastructure", Role.MANAGER
        ));

        // 2. Seed Skills Catalog
        Skill javaSkill = skillRepository.save(new Skill("Java", "Advanced", "Enterprise Java Development", "TECHNICAL"));
        Skill springBootSkill = skillRepository.save(new Skill("Spring Boot", "Advanced", "Microservices & REST APIs", "TECHNICAL"));
        Skill awsSkill = skillRepository.save(new Skill("AWS Architecture", "Expert", "Cloud Systems Infrastructure", "TECHNICAL"));
        Skill dockerSkill = skillRepository.save(new Skill("Docker & Kubernetes", "Intermediate", "Container Orchestration", "TECHNICAL"));
        Skill agileSkill = skillRepository.save(new Skill("Agile & Scrum", "Expert", "Sprint & Release Management", "DOMAIN"));
        Skill leadershipSkill = skillRepository.save(new Skill("Leadership & Communication", "Advanced", "Team Mentorship & Stakeholder Management", "SOFT"));
        Skill angularSkill = skillRepository.save(new Skill("Angular", "Advanced", "Frontend Single Page Web Apps", "TECHNICAL"));

        skillRepository.save(new Skill("Python Data Science", "Intermediate", "Machine Learning & Analytics", "TECHNICAL"));
        skillRepository.save(new Skill("PostgreSQL DB", "Expert", "Database Schema Design & Query Tuning", "TECHNICAL"));
        skillRepository.save(new Skill("Kafka Streaming", "Intermediate", "Event Driven Microservice Messaging", "TECHNICAL"));

        // 3. Seed Employee Skills
        employeeSkillRepository.save(new EmployeeSkill(john, javaSkill, 8, 5, true));
        employeeSkillRepository.save(new EmployeeSkill(john, springBootSkill, 7, 4, true));
        employeeSkillRepository.save(new EmployeeSkill(john, awsSkill, 6, 2, true));

        employeeSkillRepository.save(new EmployeeSkill(alex, awsSkill, 9, 7, true));
        employeeSkillRepository.save(new EmployeeSkill(alex, javaSkill, 8, 6, true));

        // 4. Seed Certifications
        certificationRepository.save(new Certification(
                john, "AWS SAA", "AWS-SAA-88392",
                LocalDate.now().minusYears(1), LocalDate.now().plusYears(2), "VALID"
        ));

        certificationRepository.save(new Certification(
                john, "Java OCP", "ORCL-OCP-10492",
                LocalDate.now().minusYears(3), LocalDate.now().minusMonths(2), "EXPIRED"
        ));

        // 5. Seed Assessment
        assessmentRepository.save(new Assessment(
                john, "Enterprise Java & Microservices Assessment Q3", 87.0f,
                LocalDate.now().minusWeeks(2), true
        ));

        // 6. Seed Competency Frameworks
        competencyFrameworkRepository.save(new CompetencyFramework("Tech Lead", javaSkill, 9, "Engineering"));
        competencyFrameworkRepository.save(new CompetencyFramework("Tech Lead", angularSkill, 8, "Engineering"));
        competencyFrameworkRepository.save(new CompetencyFramework("Tech Lead", agileSkill, 8, "Engineering"));
        competencyFrameworkRepository.save(new CompetencyFramework("Tech Lead", leadershipSkill, 7, "Engineering"));

        competencyFrameworkRepository.save(new CompetencyFramework("Senior Developer", javaSkill, 8, "Engineering"));
        competencyFrameworkRepository.save(new CompetencyFramework("Senior Developer", springBootSkill, 7, "Engineering"));
        competencyFrameworkRepository.save(new CompetencyFramework("Senior Developer", awsSkill, 6, "Engineering"));

        competencyFrameworkRepository.save(new CompetencyFramework("Cloud Architect", awsSkill, 9, "Infrastructure"));
        competencyFrameworkRepository.save(new CompetencyFramework("Cloud Architect", dockerSkill, 8, "Infrastructure"));

        // 7. Seed Milestone 2 Courses (Learning Management)
        Course javaCourse = courseRepository.save(Course.builder()
                .title("Advanced Microservices with Spring Boot 4")
                .description("Master cloud-native Spring Boot, JPA, Kafka, and Redis caching.")
                .type(Course.CourseType.ONLINE_COURSE)
                .category("TECHNICAL")
                .durationHours(36)
                .completionRate(87.5f)
                .build());

        Course angularCourse = courseRepository.save(Course.builder()
                .title("Angular 20 Enterprise Frontend Bootcamp")
                .description("Interactive web apps with Angular Material, Signals, and RxJS.")
                .type(Course.CourseType.BOOTCAMP)
                .category("TECHNICAL")
                .durationHours(40)
                .completionRate(92.0f)
                .build());

        Course awsWorkshop = courseRepository.save(Course.builder()
                .title("AWS Solutions Architect Hands-On Workshop")
                .description("Practical workshop on EC2, S3, RDS, Lambda, and IAM security.")
                .type(Course.CourseType.WORKSHOP)
                .category("TECHNICAL")
                .durationHours(16)
                .completionRate(89.0f)
                .build());

        Course agileWebinar = courseRepository.save(Course.builder()
                .title("Agile Leadership & Scrum Master Webinar")
                .description("Keynote on sprint planning, velocity tracking, and agile transformation.")
                .type(Course.CourseType.WEBINAR)
                .category("DOMAIN")
                .durationHours(4)
                .completionRate(95.0f)
                .build());

        // 8. Seed Learning Paths
        LearningPath path = learningPathRepository.save(LearningPath.builder()
                .title("Senior Full-Stack Cloud Engineer Track")
                .targetRole("Senior Developer")
                .careerTrack("Java Full Stack")
                .description("Comprehensive learning track covering Spring Boot, Angular 20, and AWS.")
                .progress(67)
                .build());

        learningPathCourseRepository.save(LearningPathCourse.builder().learningPath(path).course(javaCourse).sequenceOrder(1).build());
        learningPathCourseRepository.save(LearningPathCourse.builder().learningPath(path).course(angularCourse).sequenceOrder(2).build());
        learningPathCourseRepository.save(LearningPathCourse.builder().learningPath(path).course(awsWorkshop).sequenceOrder(3).build());

        // 9. Seed Enrollments
        enrollmentRepository.save(Enrollment.builder()
                .empId(john.getEmpId())
                .course(javaCourse)
                .enrolledAt(java.time.LocalDateTime.now().minusDays(15))
                .progress(85)
                .completed(false)
                .score(88.5f)
                .build());

        enrollmentRepository.save(Enrollment.builder()
                .empId(john.getEmpId())
                .course(angularCourse)
                .enrolledAt(java.time.LocalDateTime.now().minusDays(30))
                .progress(100)
                .completed(true)
                .score(94.0f)
                .completedAt(java.time.LocalDateTime.now().minusDays(2))
                .build());

        // 10. Seed Milestone 4 Career Plans & Internal Jobs
        careerPlanRepository.save(CareerPlan.builder()
                .empId(john.getEmpId())
                .employeeName(john.getName())
                .currentRole("Developer")
                .targetRole("Tech Lead")
                .progress(67)
                .mentor("Jane Doe")
                .skillGaps("Angular +3")
                .trainingPlan("Angular Advanced Bootcamp")
                .promotionScore(80)
                .promotionEligible(true)
                .status(CareerPlan.PlanStatus.ACTIVE)
                .build());

        jobRepository.save(Job.builder()
                .title("Senior Java Developer")
                .department("Engineering")
                .requiredSkills("Java, Spring Boot, Microservices")
                .minimumExperience(3)
                .active(true)
                .build());

        jobRepository.save(Job.builder()
                .title("Cloud Infrastructure Engineer")
                .department("Infrastructure")
                .requiredSkills("AWS, Docker, Kubernetes")
                .minimumExperience(4)
                .active(true)
                .build());

        System.out.println(">>> SkillSphere Nexus Milestone 1, 2, 3 & 4 Dataset Successfully Seeded! <<<");
    }
}
