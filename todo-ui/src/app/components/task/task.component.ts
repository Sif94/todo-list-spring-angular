import {Component, input} from '@angular/core';
import {TaskResponse} from '../../services/models/task-response';

@Component({
  selector: 'app-task',
  imports: [],
  templateUrl: './task.component.html',
  styleUrl: './task.component.css'
})
export class TaskComponent {
  task = input.required<TaskResponse>()
}
