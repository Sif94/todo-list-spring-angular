import {Component, output, signal} from '@angular/core';
import {PrimaryButtonComponent} from '../../components/primary-button/primary-button.component';
import {KeycloakService} from '../../services/keycloak/keycloak.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [
    PrimaryButtonComponent
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
constructor(private kcService: KeycloakService, private router: Router) {
}
  goToAddTaskPage() {
    this.router.navigate(['add-task'])
  }

  async logout() {
  await this.kcService.logout();
  }
}
