package com.scaler.demo.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scaler.demo.dtos.CreateTaskDTO;
import com.scaler.demo.dtos.ErrorResponseDTO;
import com.scaler.demo.dtos.UpdateClassDTO;
import com.scaler.demo.entitites.TaskEntity;
import com.scaler.demo.service.TaskService;

@RestController
@RequestMapping("/tasks") // ? makes /tasks global for the controller

public class TaskController {
	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping("")
	public ResponseEntity<List<TaskEntity>> getTasks() {
		var tasks = taskService.getTasks();

		return ResponseEntity.ok(tasks);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskEntity> getTaskById(@PathVariable("id") Integer id) {
		var task = taskService.getTaskById(id);
		if (task == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(task);
	}

	@PostMapping("")
	public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {
		System.out.println(body);
		var task = taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());

		return ResponseEntity.ok(task);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateClassDTO body)
			throws ParseException {
		var task = taskService.updateTask(id, body.getDescription(), body.getDeadline(), body.getCompleted());

		if (task == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(task);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e) {
		if (e instanceof ParseException) {
			return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid Date format"));
		}

		return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
	}
}
