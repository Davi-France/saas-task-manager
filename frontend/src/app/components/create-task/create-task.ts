import { Component } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { TaskService } from '../../services/taskService';
import { TaskInterface } from '../../models/task.model';

@Component({
  selector: 'app-create-task',
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './create-task.html',
  styleUrl: './create-task.scss'
})
export class CreateTask {
  title = ''
  description = ''
  completed = false

  constructor(private taskService: TaskService) { }

  submit() {
    const task: TaskInterface = {
      title: this.title,
      description: this.description,
      completed: this.completed
    }

    this.taskService.createTask(task).subscribe({
      next: (res) => {
        console.log("Task:", res);
        this.title = '';
        this.description = '';
        this.completed = false;
      },
      error: (err) => {
        console.log(err)
      }
    })
  }
}
