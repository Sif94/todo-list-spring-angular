import { Component } from '@angular/core';
import {TaskRequest} from '../../services/models/task-request';
import {TaskService} from '../../services/services/task.service';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-add-task',
  imports: [
    FormsModule,
    RouterLink
  ],
  templateUrl: './add-task.component.html',
  styleUrl: './add-task.component.css'
})
export class AddTaskComponent {
  errorMsg: Array<string> = [];
  taskRequest: TaskRequest = {
    description: '',
    dueDate: '',
    title: ''
  };

  constructor(
    private taskService: TaskService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
  }

  saveTask(event: Event) {
    event.preventDefault()
    this.taskService.addTask$Response({
      body: this.taskRequest
    }).subscribe({
      next: () => {

        this.router.navigate(["/"])
      },
      error: (err) => {
        console.log(err.error);
        this.errorMsg = err.error.validationErrors;
      }
    })
  }
}
