import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeycloakAuthService } from './keycloak.service';
import { environment } from '../../environments/environment';

export const authInterceptor: HttpInterceptorFn = async (req, next) => {
  const auth = inject(KeycloakAuthService);
  const isApiRequest = req.url.startsWith(environment.apiUrl) || req.url.startsWith('http://localhost:8080');
  
  if (!isApiRequest) {
    return next(req);
  }

  try {
    await auth.updateToken();
    const token = auth.getToken();
    if (!token) {
      return next(req);
    }

    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

    return next(authReq);
  } catch (error) {
    console.error('JWT refresh failed:', error);
    return next(req);
  }
};
