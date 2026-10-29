package com.app.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorApi {

	private String message;
	private String status;
	private String error;
	private LocalDateTime localDateTime = LocalDateTime.now();
}
