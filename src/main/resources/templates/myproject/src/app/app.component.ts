import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { PersonListComponent } from './person-list/person-list.component';
import { NgbModule} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    PersonListComponent,
    NgbModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent{
}
