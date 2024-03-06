package com.scaler.demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CreateTaskDTO {
	String title; 
	String description; 
	String deadline;
}
