
import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { UserFormComponent } from '../user-form/user-form.component';

@Component({
  selector: 'app-user-list',
  standalone: true,
  imports: [CommonModule, UserFormComponent],
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: User[] = [];
  selectedUser: User | null = null;
  isEditing = false;
  isCreating = false;
  errorMessage = '';
  userCount = '';
  private userService = inject(UserService);

  ngOnInit(): void {
    this.loadUsers();
    this.loadUserCount();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (data) => {
        this.users = data;
        this.errorMessage = '';
      },
      error: (error) => {
        this.errorMessage = 'Error loading users: ' + error.message;
        console.error('Error loading users:', error);
      }
    });
  }

  loadUserCount(): void {
    this.userService.getUserCount().subscribe({
      next: (data) => {
        this.userCount = data;
      },
      error: (error) => {
        console.error('Error loading user count:', error);
      }
    });
  }

  onCreateUser(): void {
    this.isCreating = true;
    this.isEditing = false;
    this.selectedUser = null;
  }

  onEditUser(user: User): void {
    this.selectedUser = { ...user };
    this.isEditing = true;
    this.isCreating = false;
  }

  onDeleteUser(id: number | undefined): void {
    if (!id) return;

    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.loadUsers();
          this.loadUserCount();
          this.errorMessage = '';
        },
        error: (error) => {
          this.errorMessage = 'Error deleting user: ' + error.message;
          console.error('Error deleting user:', error);
        }
      });
    }
  }

  onUserSaved(): void {
    this.isCreating = false;
    this.isEditing = false;
    this.selectedUser = null;
    this.loadUsers();
    this.loadUserCount();
  }

  onCancel(): void {
    this.isCreating = false;
    this.isEditing = false;
    this.selectedUser = null;
  }

  trackByUserId(index: number, user: User): number | undefined {
    return user.id;
  }
}
