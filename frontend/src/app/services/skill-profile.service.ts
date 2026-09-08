import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Employee {
  id: string;
  name: string;
  email: string;
  title: string;
  department: string;
  role: string;
}

export interface Skill {
  id: string;
  skillId?: string;
  skillName?: string;
  name?: string;
  category: string;
  level?: string;
  description?: string;
}

export interface EmployeeSkill {
  id: string;
  skill: Skill;
  proficiencyLevel: number;
  proficiency?: number;
  experienceYears: number;
  verified: boolean;
}

import { Certification } from './certification.service';
export type { Certification };

export interface Assessment {
  assessId: string;
  title: string;
  score: number;
  passed: boolean;
  verified: boolean;
  assessmentDate?: string;
}

export interface CompetencyGap {
  skillName: string;
  currentProficiency: number;
  requiredProficiency: number;
  gap: number;
}

export interface SkillProfileResponse {
  employee: Employee;
  skills: EmployeeSkill[];
  certifications: Certification[];
  assessments: Assessment[];
  formattedOutputSummary?: string;
}

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SkillProfileService {
  private baseUrl = `${environment.apiUrl}/api`;

  constructor(private http: HttpClient) {}

  getProfile(empId: string): Observable<SkillProfileResponse> {
    return this.http.get<SkillProfileResponse>(`${this.baseUrl}/skills/employee/${empId}`);
  }

  getJohnSmithProfile(): Observable<SkillProfileResponse> {
    return this.http.get<SkillProfileResponse>(`${this.baseUrl}/skills/skill-profiles/john-smith`);
  }

  getSkillCatalog(): Observable<Skill[]> {
    return this.http.get<Skill[]>(`${this.baseUrl}/skills/catalog`);
  }

  addSkillToCatalog(skill: Partial<Skill>): Observable<Skill> {
    return this.http.post<Skill>(`${this.baseUrl}/skills/catalog`, skill);
  }

  getCompetencyGaps(empId: string, targetRole: string): Observable<CompetencyGap[]> {
    return this.http.get<CompetencyGap[]>(`${this.baseUrl}/competency/gaps?empId=${empId}&targetRole=${encodeURIComponent(targetRole)}`);
  }

  createAssessment(assessment: Partial<Assessment>): Observable<Assessment> {
    return this.http.post<Assessment>(`${this.baseUrl}/assessments`, assessment);
  }

  verifyAssessment(assessId: string): Observable<Assessment> {
    return this.http.put<Assessment>(`${this.baseUrl}/assessments/${assessId}/verify`, {});
  }
}
