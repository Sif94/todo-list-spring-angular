import {Component, signal} from '@angular/core';
import {PrimaryButtonComponent} from '../../components/primary-button/primary-button.component';
import {KeycloakService} from '../../services/keycloak/keycloak.service';

@Component({
  selector: 'app-header',
  imports: [
    PrimaryButtonComponent
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  title = signal<string>("Ecommerce APP");
constructor(private kcService: KeycloakService) {
}
  showButtonClicked() {
    console.log("Button clicked")
  }

  async logout() {
  await this.kcService.logout();
  }
}
