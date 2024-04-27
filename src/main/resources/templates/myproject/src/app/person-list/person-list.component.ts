import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-person-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './person-list.component.html',
  styleUrl: './person-list.component.css',
})

export class PersonListComponent {

  people = [
    {
      id: 1,
      firstname: "qwe",
      lastname: "rtz",
      birthdate: "zui",
    },
    {
      id: 1,
      firstname: "qwe",
      lastname: "rtz",
      birthdate: "zui",
    },
  ];

}