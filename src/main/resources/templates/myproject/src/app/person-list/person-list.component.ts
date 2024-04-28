import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpService } from '../config.service';

@Component({
  selector: 'app-person-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './person-list.component.html',
  styleUrl: './person-list.component.css',
})

export class PersonListComponent implements OnInit{

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

  posts: any;

  constructor(private httpService: HttpService){}

  // title = 'myproject';

  ngOnInit(): void {
    // this.httpService.getPosts().subscribe(
    //   (response) => {this.posts = response;},
    //   (error) => {console.log(error)}
    // );
  }
}