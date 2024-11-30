import {
  APP_INITIALIZER,
  ApplicationConfig, inject,
  provideAppInitializer,
  Provider,
  provideZoneChangeDetection
} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptors} from '@angular/common/http';
import {httpTokenInterceptor} from './services/interceptor/http-token.interceptor';
import {KeycloakService} from './services/keycloak/keycloak.service';



export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(withInterceptors([httpTokenInterceptor])),

    provideAppInitializer(() => {
      const kcFactory = ((kcService: KeycloakService) => {
       return () => kcService.init();
     })(inject(KeycloakService));
      return kcFactory();
    })
  ]
};
