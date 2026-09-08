import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Course {
  courseId: string;
  title: string;
  description: string;
  duration?: number;
  durationHours?: number;
  type: string;
  instructor?: string;
  rating?: number;
  active?: boolean;
  category?: string;
  completionRate?: number;
}

export interface Enrollment {
  enrollmentId: string;
  empId: string;
  courseId: string;
  courseTitle?: string;
  enrolledAt: string;
  progress: number;
  completed: boolean;
  score: number;
  completedAt?: string;
  course?: Course;
}

export interface LearningPath {
  pathId: string;
  name?: string;
  title?: string;
  description?: string;
  careerTrack?: string;
  targetRole?: string;
  progress?: number;
  active?: boolean;
  courses?: Course[];
}

export interface LearningCertificate {
  certificateId: string;
  empId: string;
  courseId: string;
  courseName: string;
  score: number;
  issuedDate: string;
  certificateNumber: string;
}

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LearningService {
  private baseUrl = `${environment.apiUrl}/api/learning`;

  constructor(private http: HttpClient) {}

  getCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.baseUrl}/courses`);
  }

  getCourse(courseId: string): Observable<Course> {
    return this.http.get<Course>(`${this.baseUrl}/courses/${courseId}`);
  }

  createCourse(course: Partial<Course>): Observable<Course> {
    return this.http.post<Course>(`${this.baseUrl}/courses`, course);
  }

  enroll(empId: string, courseId: string): Observable<Enrollment> {
    return this.http.post<Enrollment>(
      `${this.baseUrl}/enrollments?empId=${empId}&courseId=${courseId}`,
      null
    );
  }

  getEnrollments(empId: string): Observable<Enrollment[]> {
    return this.http.get<Enrollment[]>(`${this.baseUrl}/enrollments/employee/${empId}`);
  }

  updateProgress(enrollmentId: string, progress: number): Observable<Enrollment> {
    return this.http.put<Enrollment>(
      `${this.baseUrl}/progress/${enrollmentId}?progress=${progress}`,
      null
    );
  }

  submitAssessment(enrollmentId: string, score: number): Observable<Enrollment> {
    return this.http.post<Enrollment>(
      `${this.baseUrl}/progress/${enrollmentId}/assessment?score=${score}`,
      null
    );
  }

  completeCourse(enrollmentId: string): Observable<Enrollment> {
    return this.http.post<Enrollment>(
      `${this.baseUrl}/progress/${enrollmentId}/complete`,
      null
    );
  }

  generateCertificate(enrollmentId: string): Observable<LearningCertificate> {
    return this.http.post<LearningCertificate>(
      `${this.baseUrl}/certificates/${enrollmentId}`,
      null
    );
  }

  getLearningPaths(): Observable<LearningPath[]> {
    return this.http.get<LearningPath[]>(`${this.baseUrl}/paths`);
  }

  createLearningPath(path: Partial<LearningPath>): Observable<LearningPath> {
    return this.http.post<LearningPath>(`${this.baseUrl}/paths`, path);
  }
}
