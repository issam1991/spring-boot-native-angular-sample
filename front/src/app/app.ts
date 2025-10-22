import { Component } from '@angular/core';
import { UserListComponent } from './components/user-list/user-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [UserListComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  title = 'User Management Application';
}
