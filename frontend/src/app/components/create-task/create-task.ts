import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TaskService } from '../../services/taskService';
import { TaskInterface } from '../../models/task.model';

@Component({
  selector: 'app-create-task',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './create-task.html',
  styleUrls: ['./create-task.scss']
})
export class CreateTask {
  @Input() status!: 'todo' | 'in-progress' | 'done';
  @Output() taskCreated = new EventEmitter<TaskInterface>();
  @Output() close = new EventEmitter<void>();

  title = '';
  description = '';
  completed = false;

  constructor(private taskService: TaskService) { }

  submit() {
    const task: TaskInterface = {
      title: this.title,
      description: this.description,
      status: this.status,
    };

    this.taskService.createTask(task).subscribe({
      next: (res) => {
        this.taskCreated.emit(res);
        this.title = '';
        this.description = '';
        this.close.emit();
      },
      error: (err) => console.error(err),
    });
  }
}