package com.example.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.demo.domain.Student;
import com.example.demo.dto.StudentDTO;

@Mapper(componentModel = "spring")
public interface StudentMapper {

	Student toEntity(StudentDTO dto);

	List<Student> toEntity(List<StudentDTO> dto);

	StudentDTO toDTO(Student entity);

	List<StudentDTO> toDTO(List<Student> entity);
}