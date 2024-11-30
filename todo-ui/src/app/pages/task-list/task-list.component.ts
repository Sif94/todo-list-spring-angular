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
  tasks = signal<PageResponseTaskResponse>({})
  page = signal(0);
  size = signal(2);
  pages = signal<Array<number>>([]);
  constructor(private taskService: TaskService) {
  }

  ngOnInit(): void {
    this.getAllMyTasks();
  }
  getAllMyTasks(){
    this.taskService.getTasksByUser({
      page: this.page(),
      size: this.size()
    }).subscribe({
      next: data => {
        this.tasks.update(() => data)
        this.pages.update(() => Array(this.tasks().totalPages)
          .fill(0)
          .map((x, i) => i));
      }
    })
  }


  gotToPage(page: number) {
    this.page.update(() => page);
    console.log(this.page())
    this.getAllMyTasks();
  }

  goToFirstPage() {
    this.page.update(() => 0);
    console.log(this.page())
    this.getAllMyTasks();
  }

  goToPreviousPage() {
    this.page.update(value => value - 1);
    if(this.page() >= 0){
    this.getAllMyTasks();
    }
  }

  goToLastPage() {
    this.page.update(value => this.tasks().totalPages as number - 1);
    this.getAllMyTasks();
  }

  goToNextPage() {
    this.page.update(value => value + 1);
    if(this.page() <= this.tasks().totalPages! - 1){
    this.getAllMyTasks();
    console.log(this.tasks())
    }
  }

  get isLastPage() {
    return this.page() === this.tasks().totalPages as number - 1;
  }

  completeTask(task: TaskResponse) {
    this.taskService.updateTaskStatus({
      "id": task.id as number
    }).subscribe({
      next: () => {
        task.isDone = !task.isDone
      }
    })
  }
}

