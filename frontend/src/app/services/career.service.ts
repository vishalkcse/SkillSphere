import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CareerPlan {
  planId: string;
  empId: string;
  employeeName?: string;
  currentRole: string;
  targetRole: string;
  progress: number;
  mentor?: string;
  skillGaps?: string;
  trainingPlan?: string;
  promotionScore?: number;
  promotionEligible?: boolean;
  status: string;
}

export interface Job {
  jobId: string;
  title: string;
  department: string;
  requiredSkills: string;
  minimumExperience: number;
  active: boolean;
}

export interface Analytics {
  totalCareerPlans: number;
  activeCareerPlans: number;
  completedPlans: number;
  promotionEligible: number;
  averageProgress: number;
  skillCoverage: number;
  activeJobs: number;
}

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CareerService {
  private baseUrl = `${environment.apiUrl}/api/career`;

  constructor(private http: HttpClient) {}

  getCareerPlans(): Observable<CareerPlan[]> {
    return this.http.get<CareerPlan[]>(`${this.baseUrl}/plans`);
  }

  getEmployeePlans(empId: string): Observable<CareerPlan[]> {
    return this.http.get<CareerPlan[]>(`${this.baseUrl}/plans/employee/${empId}`);
  }

  createCareerPlan(data: Partial<CareerPlan>): Observable<CareerPlan> {
    return this.http.post<CareerPlan>(`${this.baseUrl}/plans`, data);
  }

  updateCareerPlan(id: string, data: Partial<CareerPlan>): Observable<CareerPlan> {
    return this.http.put<CareerPlan>(`${this.baseUrl}/plans/${id}`, data);
  }

  getJobs(): Observable<Job[]> {
    return this.http.get<Job[]>(`${this.baseUrl}/jobs/active`);
  }

  createJob(data: Partial<Job>): Observable<Job> {
    return this.http.post<Job>(`${this.baseUrl}/jobs`, data);
  }

  getAnalytics(): Observable<Analytics> {
    return this.http.get<Analytics>(`${this.baseUrl}/analytics`);
  }
}
