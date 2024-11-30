import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {HeaderComponent} from './header/header/header.component';
import {TaskListComponent} from './pages/task-list/task-list.component';
import {AddTaskComponent} from './pages/add-task/add-task.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, HeaderComponent, TaskListComponent, AddTaskComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'todo-ui';
}
