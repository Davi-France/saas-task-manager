import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TaskService } from '../../services/taskService';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-task-details',
  standalone: true,
  imports: [MatButtonModule],
  templateUrl: './task-details.html',
})
export class TaskDetailsComponent {
  constructor(
    public dialogRef: MatDialogRef<TaskDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public task: any,
    private taskService: TaskService
  ) { }

  close() {
    this.dialogRef.close();
  }

  deleteTask() {
    if (confirm('Tem certeza que deseja deletar esta tarefa?')) {
      this.taskService.deleteTask(this.task.id).subscribe({
        next: () => this.dialogRef.close({ deleted: true }),
        error: (err) => console.error('Erro ao deletar task:', err)
      });
    }
  }
}
