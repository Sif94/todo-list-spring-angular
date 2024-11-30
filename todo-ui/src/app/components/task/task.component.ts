import {Component, input, output} from '@angular/core';
import {TaskResponse} from '../../services/models/task-response';
import {TaskService} from '../../services/services/task.service';

@Component({
  selector: 'app-task',
  imports: [],
  templateUrl: './task.component.html',
  styleUrl: './task.component.css'
})
export class TaskComponent {
  task = input.required<TaskResponse>()
  complete = output<TaskResponse>()
  constructor(private taskService: TaskService) {
  }

  onComplete() {
    this.complete.emit(this.task())
  }
}
