import { Component, OnInit } from '@angular/core';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { TaskService } from '../../services/taskService';
import { TaskInterface } from '../../models/task.model';
import { CreateTask } from '../../components/create-task/create-task';
import { CommonModule } from '@angular/common';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-kanban',
  templateUrl: './kanban.html',
  styleUrls: ['./kanban.scss'],
  standalone: true,
  imports: [CommonModule, DragDropModule, FormsModule, CreateTask],
})
export class KanbanComponent implements OnInit {
  todoTasks: TaskInterface[] = [];
  inProgressTasks: TaskInterface[] = [];
  doneTasks: TaskInterface[] = [];

  showForm = false;
  currentStatus: TaskInterface['status'] = 'todo';

  constructor(private taskService: TaskService) { }

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks() {
    this.taskService.getTasks().subscribe({
      next: tasks => {
        this.todoTasks = tasks.filter(t => t.status === 'todo');
        this.inProgressTasks = tasks.filter(t => t.status === 'in-progress');
        this.doneTasks = tasks.filter(t => t.status === 'done');
      },
      error: err => console.error(err)
    });
  }

  drop(event: CdkDragDrop<TaskInterface[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );

      const movedTask = event.container.data[event.currentIndex];

      // atualiza o status localmente
      if (event.container.id === 'todo') movedTask.status = 'todo';
      if (event.container.id === 'in-progress') movedTask.status = 'in-progress';
      if (event.container.id === 'done') movedTask.status = 'done';

      const payload = {
        title: movedTask.title,
        description: movedTask.description,
        status: movedTask.status
      };

      this.taskService.updateTask(movedTask.id!, payload).subscribe({
        next: () => console.log('Status atualizado no backend'),
        error: (err) => console.error('Erro ao atualizar status', err)
      });
    }
  }

  showCreateForm(status: TaskInterface['status']) {
    this.currentStatus = status;
    this.showForm = true;
  }

  closeForm() {
    this.showForm = false;
  }

  onTaskCreated(task: TaskInterface) {
    if (task.status === 'todo') this.todoTasks.push(task);
    if (task.status === 'in-progress') this.inProgressTasks.push(task);
    if (task.status === 'done') this.doneTasks.push(task);
    this.closeForm();
  }
}
