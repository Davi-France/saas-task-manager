import { Component } from '@angular/core';
import { CreateTask } from '../../components/create-task/create-task';
import { TaskInterface } from '../../models/task.model';
import { CommonModule } from '@angular/common';
import { TaskService } from '../../services/taskService';

@Component({
  selector: 'app-tasks',
  imports: [CreateTask, CommonModule],
  templateUrl: './tasks.html',
  styleUrl: './tasks.scss'
})

export class Tasks {

  constructor(private taskService: TaskService) { }

  tasks: TaskInterface[] = []

  ngOnInit(): void {
    this.taskService.getTasks().subscribe((res: TaskInterface[]) => {
      this.tasks = res
    })
  }
}
