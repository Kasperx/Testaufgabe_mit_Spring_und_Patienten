import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpService } from '../config.service';

@Component({
  selector: 'app-test-data',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './test-data.component.html',
  styleUrl: './test-data.component.css'
})
export class TestDataComponent implements OnInit{

  posts: any;

  constructor(private httpService: HttpService){}

  // title = 'myproject';

  ngOnInit(): void {
    this.httpService.getPosts().subscribe(
      (response) => {this.posts = response;},
      (error) => {console.log(error)}
    );
  }
}
