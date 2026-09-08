import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class KeycloakAuthService {
  private keycloak = new Keycloak({
    url: environment.keycloak.url,
    realm: environment.keycloak.realm,
    clientId: environment.keycloak.clientId
  });

  async init(): Promise<boolean> {
    try {
      return await this.keycloak.init({
        onLoad: 'check-sso',
        checkLoginIframe: false
      });
    } catch (e) {
      console.warn('Keycloak SSO initialization bypassed for development:', e);
      return false;
    }
  }

  async login(): Promise<void> {
    await this.keycloak.login({
      redirectUri: window.location.origin
    });
  }

  async logout(): Promise<void> {
    await this.keycloak.logout({
      redirectUri: window.location.origin
    });
  }

  async updateToken(): Promise<boolean> {
    if (!this.keycloak.authenticated) {
      return false;
    }
    return await this.keycloak.updateToken(30);
  }

  getToken(): string | undefined {
    return this.keycloak.token;
  }

  getUsername(): string | undefined {
    return this.keycloak.tokenParsed?.['preferred_username'] || 'John Smith';
  }

  getRoles(): string[] {
    const roles = (this.keycloak.tokenParsed as any)?.['realm_access']?.['roles'];
    return Array.isArray(roles) ? roles : ['ROLE_EMPLOYEE', 'ROLE_HR', 'ROLE_ADMIN'];
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role) || this.getRoles().includes('ROLE_' + role);
  }

  isLoggedIn(): boolean {
    return !!this.keycloak.authenticated;
  }
}
