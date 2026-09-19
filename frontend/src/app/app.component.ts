import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { 
  SkillProfileService, 
  SkillProfileResponse, 
  Skill, 
  CompetencyGap 
} from './services/skill-profile.service';
import {
  LearningService,
  Course,
  Enrollment,
  LearningPath
} from './services/learning.service';
import {
  CertificationService,
  Certification,
  Compliance,
  CertificationReport
} from './services/certification.service';
import {
  CareerService,
  CareerPlan,
  Job,
  Analytics
} from './services/career.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  activeTab: 'dashboard' | 'profile' | 'catalog' | 'competency' | 'assessments' | 'learning' | 'certifications' | 'career' | 'jobs' | 'analytics' = 'dashboard';

  get activeTabTitle(): string {
    switch (this.activeTab) {
      case 'dashboard': return 'Dashboard';
      case 'profile': return 'Employees';
      case 'catalog': return 'Skills';
      case 'competency': return 'Competency Gap Mapping';
      case 'assessments': return 'Assessments & Verification';
      case 'learning': return 'Learning';
      case 'certifications': return 'Certifications';
      case 'career': return 'Career';
      case 'jobs': return 'Jobs';
      case 'analytics': return 'Analytics';
      default: return 'Dashboard';
    }
  }

  get activeTabSub(): string {
    switch (this.activeTab) {
      case 'dashboard': return 'Monitor skills, learning, certifications and career development.';
      case 'profile': return 'Manage employee skill profiles, proficiencies, and verified credentials.';
      case 'catalog': return 'Browse, create, and manage enterprise skill definitions.';
      case 'competency': return 'Analyze skill gaps against target organizational roles.';
      case 'assessments': return 'Conduct skill assessments and verify employee proficiencies.';
      case 'learning': return 'Access courses, track enrollments, and manage learning paths.';
      case 'certifications': return 'Track professional certifications, expirations, and compliance.';
      case 'career': return 'Plan career growth, mentorship, and review executive analytics.';
      case 'jobs': return 'View active internal job openings and matching criteria.';
      case 'analytics': return 'Executive metrics, skill readiness, and compliance reports.';
      default: return 'Monitor skills, learning, certifications and career development.';
    }
  }

  get overallLearningProgress(): number {
    if (this.enrollments && this.enrollments.length > 0) {
      const sum = this.enrollments.reduce((acc, curr) => acc + (curr.progress || (curr.completed ? 100 : 0)), 0);
      return Math.round((sum / this.enrollments.length) * 100) / 100;
    }
    if (this.courses && this.courses.length > 0) {
      const sum = this.courses.reduce((acc, curr) => acc + (curr.completionRate || 85), 0);
      return Math.round((sum / this.courses.length) * 100) / 100;
    }
    return 88.5;
  }

  get formattedLearningProgress(): string {
    return this.overallLearningProgress.toFixed(2) + '%';
  }

  // Authentication State (Default to false so Login Page appears first)
  isLoggedIn: boolean = false;
  loginEmail: string = 'vishalkumar@skillsphere.io';
  loginPassword: string = 'password123';
  loginRole: string = 'Administrator';
  loginErrorMsg: string = '';

  currentUser = {
    name: 'Vishal Kumar',
    email: 'vishalkumar@skillsphere.io',
    role: 'Administrator',
    avatar: 'VK'
  };

  login(): void {
    if (!this.loginEmail || !this.loginPassword) {
      this.loginErrorMsg = 'Please enter both email and password.';
      return;
    }

    let name = 'Vishal Kumar';
    let initials = 'VK';

    if (this.loginEmail.toLowerCase().includes('john')) {
      name = 'John Smith';
      initials = 'JS';
    } else if (this.loginEmail.toLowerCase().includes('jane')) {
      name = 'Jane Doe';
      initials = 'JD';
    } else if (this.loginEmail.toLowerCase().includes('keerthi')) {
      name = 'Keerthi Nathan';
      initials = 'KN';
    }

    this.currentUser = {
      name: name,
      email: this.loginEmail,
      role: this.loginRole,
      avatar: initials
    };

    this.isLoggedIn = true;
    this.loginErrorMsg = '';
    this.activeTab = 'dashboard';
  }

  logout(): void {
    this.isLoggedIn = false;
    this.loginErrorMsg = '';
  }

  profile: SkillProfileResponse | null = null;
  catalog: Skill[] = [];
  competencyGaps: CompetencyGap[] = [];
  
  targetRole: string = 'Tech Lead';
  selectedEmpId: string = '';
  loadingProfile: boolean = false;
  loadingGaps: boolean = false;
  loadingCatalog: boolean = false;

  // New Skill Form
  newSkillName: string = '';
  newSkillCategory: string = 'TECHNICAL';
  newSkillLevel: string = 'Advanced';
  newSkillDescription: string = '';
  catalogSuccessMsg: string = '';

  // New Assessment Form
  newAssessScore: number = 85;
  newAssessSkillId: string = '';
  assessSuccessMsg: string = '';

  // Milestone 2 State
  courses: Course[] = [];
  enrollments: Enrollment[] = [];
  learningPaths: LearningPath[] = [];
  learningMsg: string = '';

  newCourseTitle: string = '';
  newCourseDesc: string = '';
  newCourseDuration: number = 40;
  newCourseType: string = 'ONLINE';
  newCourseInstructor: string = 'Jane Doe';

  // Milestone 3 State
  profCertifications: Certification[] = [];
  expiringCertifications: Certification[] = [];
  expiredCertifications: Certification[] = [];
  employeeCompliance: Compliance | null = null;
  certReport: CertificationReport | null = null;
  certMsg: string = '';

  newCertName: string = '';
  newCertIssuer: string = 'AWS / Oracle';
  newCertCredId: string = '';
  newCertIssued: string = '2025-03-15';
  newCertExpiry: string = '2028-03-15';

  // Milestone 4 Career & Analytics State
  careerPlans: CareerPlan[] = [];
  internalJobs: Job[] = [];
  execAnalytics: Analytics | null = null;
  careerMsg: string = '';

  // New Career Plan Form
  newCurrentRole: string = 'Developer';
  newTargetRole: string = 'Tech Lead';
  newMentor: string = 'Jane Doe';
  newSkillGapsText: string = 'Angular +3';
  newTrainingPlanText: string = 'Angular Advanced Bootcamp';

  // New Internal Job Form
  newJobTitle: string = 'Senior Cloud Architect';
  newJobDept: string = 'Infrastructure';
  newJobSkills: string = 'AWS, Docker, Kubernetes';
  newJobExp: number = 4;

  constructor(
    private skillService: SkillProfileService,
    private learningService: LearningService,
    private certService: CertificationService,
    private careerService: CareerService
  ) {}

  ngOnInit(): void {
    this.loadJohnSmithProfile();
    this.loadCatalog();
    this.loadLearningData();
    this.loadCertificationData();
    this.loadCareerData();
  }

  loadJohnSmithProfile(): void {
    this.loadingProfile = true;
    this.skillService.getJohnSmithProfile().subscribe({
      next: (data) => {
        this.profile = data;
        if (data && data.employee) {
          this.selectedEmpId = data.employee.id;
          this.loadCompetencyGaps();
          this.loadEmployeeEnrollments();
          this.loadEmployeeCertifications();
        }
        this.loadingProfile = false;
      },
      error: (err) => {
        console.error('Error fetching John Smith profile:', err);
        this.loadingProfile = false;
      }
    });
  }

  loadCatalog(): void {
    this.loadingCatalog = true;
    this.skillService.getSkillCatalog().subscribe({
      next: (data) => {
        this.catalog = data;
        if (data && data.length > 0 && !this.newAssessSkillId) {
          this.newAssessSkillId = data[0].skillId || data[0].id || '';
        }
        this.loadingCatalog = false;
      },
      error: (err) => {
        console.error('Error loading catalog:', err);
        this.loadingCatalog = false;
      }
    });
  }

  loadCompetencyGaps(): void {
    if (!this.selectedEmpId) return;
    this.loadingGaps = true;
    this.skillService.getCompetencyGaps(this.selectedEmpId, this.targetRole).subscribe({
      next: (data) => {
        this.competencyGaps = data;
        this.loadingGaps = false;
      },
      error: (err) => {
        console.error('Error loading competency gaps:', err);
        this.loadingGaps = false;
      }
    });
  }

  addSkillToCatalog(): void {
    if (!this.newSkillName) return;

    this.skillService.addSkillToCatalog({
      name: this.newSkillName,
      category: this.newSkillCategory,
      level: this.newSkillLevel,
      description: this.newSkillDescription
    }).subscribe({
      next: (res) => {
        this.catalogSuccessMsg = `Successfully added skill: ${res.name || this.newSkillName}`;
        this.newSkillName = '';
        this.newSkillDescription = '';
        this.loadCatalog();
        setTimeout(() => this.catalogSuccessMsg = '', 4000);
      },
      error: (err) => {
        console.error('Error adding skill:', err);
        if (err.status === 403 || err.status === 401) {
          this.catalogSuccessMsg = 'Access Denied: HR Manager or Administrator role required to modify catalog.';
        } else {
          this.catalogSuccessMsg = err.error?.message || err.message || 'Error adding skill to catalog.';
        }
      }
    });
  }

  submitAssessment(): void {
    if (!this.selectedEmpId || !this.newAssessSkillId) return;

    this.skillService.createAssessment({
      empId: this.selectedEmpId,
      skillId: this.newAssessSkillId,
      score: this.newAssessScore
    } as any).subscribe({
      next: (res) => {
        const passedText = res.passed ? 'PASSED (≥ 70%)' : 'FAILED (< 70%)';
        this.assessSuccessMsg = `Assessment created! Score: ${res.score}% - ${passedText}`;
        this.loadJohnSmithProfile();
        setTimeout(() => this.assessSuccessMsg = '', 4000);
      },
      error: (err) => {
        console.error('Error submitting assessment:', err);
        this.assessSuccessMsg = 'Error submitting assessment.';
      }
    });
  }

  verifyAssessment(assessId: string): void {
    this.skillService.verifyAssessment(assessId).subscribe({
      next: () => {
        this.loadJohnSmithProfile();
      },
      error: (err) => {
        console.error('Error verifying assessment:', err);
      }
    });
  }

  // --- Milestone 2 Methods ---

  loadLearningData(): void {
    this.learningService.getCourses().subscribe({
      next: (data) => this.courses = data,
      error: (err) => console.error('Error loading courses:', err)
    });

    this.learningService.getLearningPaths().subscribe({
      next: (data) => this.learningPaths = data,
      error: (err) => console.error('Error loading learning paths:', err)
    });
  }

  loadEmployeeEnrollments(): void {
    if (!this.selectedEmpId) return;
    this.learningService.getEnrollments(this.selectedEmpId).subscribe({
      next: (data) => this.enrollments = data,
      error: (err) => console.error('Error loading enrollments:', err)
    });
  }

  createNewCourse(): void {
    if (!this.newCourseTitle) return;
    this.learningService.createCourse({
      title: this.newCourseTitle,
      description: this.newCourseDesc,
      duration: this.newCourseDuration,
      type: this.newCourseType,
      instructor: this.newCourseInstructor,
      rating: 4.85
    }).subscribe({
      next: (res) => {
        this.learningMsg = `Created Course: ${res.title}`;
        this.newCourseTitle = '';
        this.newCourseDesc = '';
        this.loadLearningData();
        setTimeout(() => this.learningMsg = '', 4000);
      },
      error: (err) => console.error('Error creating course:', err)
    });
  }

  enrollInCourse(courseId: string): void {
    if (!this.selectedEmpId) return;
    this.learningService.enroll(this.selectedEmpId, courseId).subscribe({
      next: () => {
        this.learningMsg = `Enrolled in Course successfully!`;
        this.loadEmployeeEnrollments();
        setTimeout(() => this.learningMsg = '', 4000);
      },
      error: (err) => console.error('Error enrolling:', err)
    });
  }

  updateProgress(enrollmentId: string, progress: number): void {
    this.learningService.updateProgress(enrollmentId, progress).subscribe({
      next: () => this.loadEmployeeEnrollments(),
      error: (err) => console.error('Error updating progress:', err)
    });
  }

  completeCourse(enrollmentId: string): void {
    this.learningService.completeCourse(enrollmentId).subscribe({
      next: () => {
        this.learningMsg = 'Course completed! Certificate can now be generated.';
        this.loadEmployeeEnrollments();
        setTimeout(() => this.learningMsg = '', 4000);
      },
      error: (err) => console.error('Error completing course:', err)
    });
  }

  generateCertificate(enrollmentId: string): void {
    this.learningService.generateCertificate(enrollmentId).subscribe({
      next: (cert) => {
        this.learningMsg = `Certificate Generated! Number: ${cert.certificateNumber}`;
        setTimeout(() => this.learningMsg = '', 6000);
      },
      error: (err) => {
        console.error('Error generating certificate:', err);
        this.learningMsg = 'Error generating certificate (ensure course is completed).';
      }
    });
  }

  // --- Milestone 3 Methods ---

  loadCertificationData(): void {
    this.certService.getExpiring().subscribe({
      next: (data) => this.expiringCertifications = data,
      error: (err) => console.error('Error fetching expiring certs:', err)
    });

    this.certService.getExpired().subscribe({
      next: (data) => this.expiredCertifications = data,
      error: (err) => console.error('Error fetching expired certs:', err)
    });

    this.certService.getReport().subscribe({
      next: (data) => this.certReport = data,
      error: (err) => console.error('Error fetching cert report:', err)
    });
  }

  loadEmployeeCertifications(): void {
    if (!this.selectedEmpId) return;
    this.certService.getEmployeeCertifications(this.selectedEmpId).subscribe({
      next: (data) => this.profCertifications = data,
      error: (err) => console.error('Error fetching employee certs:', err)
    });

    this.certService.getCompliance(this.selectedEmpId).subscribe({
      next: (data) => this.employeeCompliance = data,
      error: (err) => console.error('Error fetching compliance:', err)
    });
  }

  registerProfessionalCert(): void {
    if (!this.selectedEmpId || !this.newCertName) {
      this.certMsg = 'Please enter a certification name.';
      return;
    }

    this.certService.register({
      empId: this.selectedEmpId,
      name: this.newCertName,
      issuingOrganization: this.newCertIssuer || 'AWS / Oracle',
      credentialId: this.newCertCredId || 'CRED-' + Math.floor(Math.random() * 89999 + 10000),
      issued: this.newCertIssued || '2025-03-15',
      expiry: this.newCertExpiry || '2028-03-15'
    }).subscribe({
      next: (res) => {
        this.certMsg = `Successfully registered certification: ${res.name} (Status: ${res.status})`;
        this.newCertName = '';
        this.newCertCredId = '';
        this.loadEmployeeCertifications();
        this.loadCertificationData();
        setTimeout(() => this.certMsg = '', 5000);
      },
      error: (err) => {
        console.error('Error registering cert:', err);
        this.certMsg = err.error?.message || err.message || 'Error registering certification.';
      }
    });
  }

  requestRenewal(certId: string): void {
    if (!certId) return;
    this.certService.requestRenewal(certId, 'HR').subscribe({
      next: (res) => {
        this.certMsg = `Renewal requested for Certification (Status: ${res.status || 'REQUESTED'}). Event logged!`;
        this.loadEmployeeCertifications();
        this.loadCertificationData();
        setTimeout(() => this.certMsg = '', 5000);
      },
      error: (err) => {
        console.error('Error requesting renewal:', err);
        this.certMsg = err.error?.message || err.message || 'Error requesting renewal.';
      }
    });
  }

  // --- Milestone 4 Methods ---

  loadCareerData(): void {
    this.careerService.getCareerPlans().subscribe({
      next: (data) => this.careerPlans = data,
      error: (err) => console.error('Error fetching career plans:', err)
    });

    this.careerService.getJobs().subscribe({
      next: (data) => this.internalJobs = data,
      error: (err) => console.error('Error fetching jobs:', err)
    });

    this.careerService.getAnalytics().subscribe({
      next: (data) => this.execAnalytics = data,
      error: (err) => console.error('Error fetching analytics:', err)
    });
  }

  createCareerPlan(): void {
    if (!this.selectedEmpId) return;

    const empName = this.profile ? this.profile.employee.name : 'John Smith';

    this.careerService.createCareerPlan({
      empId: this.selectedEmpId,
      employeeName: empName,
      currentRole: this.newCurrentRole,
      targetRole: this.newTargetRole,
      progress: 67,
      mentor: this.newMentor,
      skillGaps: this.newSkillGapsText,
      trainingPlan: this.newTrainingPlanText,
      status: 'ACTIVE'
    }).subscribe({
      next: (res) => {
        const eligibleText = res.promotionEligible ? 'ELIGIBLE' : 'NOT ELIGIBLE';
        this.careerMsg = `Career Plan created! Score: ${res.promotionScore} - ${eligibleText}`;
        this.loadCareerData();
        setTimeout(() => this.careerMsg = '', 5000);
      },
      error: (err) => console.error('Error creating career plan:', err)
    });
  }

  createInternalJob(): void {
    if (!this.newJobTitle) return;

    this.careerService.createJob({
      title: this.newJobTitle,
      department: this.newJobDept,
      requiredSkills: this.newJobSkills,
      minimumExperience: this.newJobExp,
      active: true
    }).subscribe({
      next: (res) => {
        this.careerMsg = `Internal Job Posted: ${res.title}`;
        this.newJobTitle = '';
        this.loadCareerData();
        setTimeout(() => this.careerMsg = '', 4000);
      },
      error: (err) => console.error('Error posting job:', err)
    });
  }
}
