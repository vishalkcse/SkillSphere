import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Certification {
  certId?: string;
  id?: string;
  empId?: string;
  employeeName?: string;
  name?: string;
  certName?: string;
  issuingOrganization?: string;
  credentialId?: string;
  issued: string;
  expiry: string;
  issueDate?: string;
  expiryDate?: string;
  status: string;
}

export interface Renewal {
  renewalId: string;
  certificationId: string;
  oldExpiry?: string;
  newExpiry?: string;
  status: string;
  requestedBy?: string;
  approvedBy?: string;
}

export interface Compliance {
  employeeName: string;
  totalCertifications: number;
  validCertifications: number;
  expiredCertifications: number;
  compliant: boolean;
}

export interface CertificationReport {
  total: number;
  active: number;
  expired: number;
  pendingRenewal: number;
  expiringWithin30Days: number;
  renewalRate: number;
}

export interface CertificationAudit {
  auditId: string;
  certificationId: string;
  employeeId: string;
  action: string;
  performedBy: string;
  performedAt: string;
}

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CertificationService {
  private baseUrl = `${environment.apiUrl}/api/certifications`;

  constructor(private http: HttpClient) {}

  register(data: Partial<Certification>): Observable<Certification> {
    return this.http.post<Certification>(this.baseUrl, data);
  }

  getById(id: string): Observable<Certification> {
    return this.http.get<Certification>(`${this.baseUrl}/${id}`);
  }

  getEmployeeCertifications(empId: string): Observable<Certification[]> {
    return this.http.get<Certification[]>(`${this.baseUrl}/employee/${empId}`);
  }

  update(id: string, data: Partial<Certification>): Observable<Certification> {
    return this.http.put<Certification>(`${this.baseUrl}/${id}`, data);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getExpiring(): Observable<Certification[]> {
    return this.http.get<Certification[]>(`${this.baseUrl}/expiring`);
  }

  getExpired(): Observable<Certification[]> {
    return this.http.get<Certification[]>(`${this.baseUrl}/expired`);
  }

  requestRenewal(certificationId: string, requestedBy: string): Observable<Renewal> {
    return this.http.post<Renewal>(
      `${this.baseUrl}/renewals/${certificationId}?requestedBy=${encodeURIComponent(requestedBy)}`,
      null
    );
  }

  approveRenewal(renewalId: string, newExpiry: string, approvedBy: string): Observable<Renewal> {
    return this.http.put<Renewal>(
      `${this.baseUrl}/renewals/${renewalId}/approve?newExpiry=${newExpiry}&approvedBy=${encodeURIComponent(approvedBy)}`,
      null
    );
  }

  getCompliance(empId: string): Observable<Compliance> {
    return this.http.get<Compliance>(`${this.baseUrl}/compliance/${empId}`);
  }

  getReport(): Observable<CertificationReport> {
    return this.http.get<CertificationReport>(`${this.baseUrl}/report`);
  }

  getAudit(certificationId: string): Observable<CertificationAudit[]> {
    return this.http.get<CertificationAudit[]>(`${this.baseUrl}/${certificationId}/audit`);
  }
}
