
import { Component, Input, Output, EventEmitter, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { User, UserRequest } from '../../models/user.model';

@Component({
  selector: 'app-user-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.css']
})
export class UserFormComponent implements OnInit {
  @Input() user: User | null = null;
  @Input() isEditing = false;
  @Output() userSaved = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  formData: UserRequest = {
    name: '',
    email: ''
  };

  errorMessage = '';
  private userService = inject(UserService);

  ngOnInit(): void {
    if (this.user) {
      this.formData = {
        name: this.user.name,
        email: this.user.email
      };
    }
  }

  onSubmit(): void {
    if (!this.formData.name || !this.formData.email) {
      this.errorMessage = 'Please fill in all fields';
      return;
    }

    if (this.isEditing && this.user?.id) {
      this.userService.updateUser(this.user.id, this.formData).subscribe({
        next: () => {
          this.userSaved.emit();
          this.resetForm();
        },
        error: (error) => {
          this.errorMessage = 'Error updating user: ' + error.message;
          console.error('Error updating user:', error);
        }
      });
    } else {
      this.userService.createUser(this.formData).subscribe({
        next: () => {
          this.userSaved.emit();
          this.resetForm();
        },
        error: (error) => {
          this.errorMessage = 'Error creating user: ' + error.error || error.message;
          console.error('Error creating user:', error);
        }
      });
    }
  }

  onCancel(): void {
    this.cancelled.emit();
    this.resetForm();
  }

  resetForm(): void {
    this.formData = {
      name: '',
      email: ''
    };
    this.errorMessage = '';
  }
}
