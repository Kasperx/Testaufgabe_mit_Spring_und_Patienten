import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { PersonListComponent } from './person-list/person-list.component';
import { TestDataComponent } from './test-data/test-data.component';
import { NgbModule} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    PersonListComponent,
    TestDataComponent,
    NgbModule
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent{
  
  title: string = "Spielwiese";
}
