package com.example.demo.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class StudentDTO implements Serializable {

	private Long id;

	private String firstName;

	private String lastName;
}
