import {Component, OnInit, signal} from '@angular/core';
import {PageResponseTaskResponse} from '../../services/models/page-response-task-response';
import {TaskResponse} from '../../services/models/task-response';
import {TaskComponent} from '../../components/task/task.component';
import {TaskService} from '../../services/services/task.service';

@Component({
  selector: 'app-task-list',
  imports: [
    TaskComponent
  ],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent implements OnInit{
  tasks = signal<PageResponseTaskResponse>({
    content: [],
    isFirst: true,
    isLast: true,
    page: 0,
    size: 10,
    totalElements: 0,
    totalPages: 0
  })
  constructor(private taskService: TaskService) {
  }

  ngOnInit(): void {
    this.taskService.getTasksByUser().subscribe({
      next: data => {
        this.tasks.set(data)
      },
      error: err => {
        console.log(err)
      }
    })
  }

}

