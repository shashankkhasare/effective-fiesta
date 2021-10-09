package com.example.demo.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Student;
import com.example.demo.dto.StudentDTO;
import com.example.demo.mapper.StudentMapper;
import com.example.demo.repository.StudentRepository;
import com.example.demo.rest.errors.BadRequestAlertException;

@RestController
@RequestMapping("/api")
public class StudentResource {

	private final Logger log = LoggerFactory.getLogger(StudentResource.class);

	private static final String ENTITY_NAME = "student";

	@Value("${app.name}")
	private String applicationName;

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	private StudentRepository studentRepository;

	@PostMapping("/students")
	public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDto) throws URISyntaxException {
		log.debug("REST request to save Student : {}", studentDto);
		if (studentDto.getId() != null) {
			throw new BadRequestAlertException("A new student cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Student result = studentRepository.save(studentMapper.toEntity(studentDto));
		return ResponseEntity.created(new URI("/api/students/" + result.getId())).body(studentMapper.toDTO(result));
	}

	@PutMapping("/students")
	public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDto) {
		log.debug("REST request to update Student : {}", studentDto);
		if (studentDto.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}

		if (!studentRepository.existsById(studentDto.getId())) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}

		Student result = studentRepository.save(studentMapper.toEntity(studentDto));
		return ResponseEntity.ok().body(studentMapper.toDTO(result));
	}

	@GetMapping("/students")
	public List<StudentDTO> getAllStudents() {
		log.debug("REST request to get all Students");
		return studentMapper.toDTO(studentRepository.findAll());
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<StudentDTO> getStudent(@PathVariable Long id) {
		log.debug("REST request to get Student : {}", id);
		Optional<Student> student = studentRepository.findById(id);
		return student.map(response -> ResponseEntity.ok().body(studentMapper.toDTO(student.get())))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
		log.debug("REST request to delete Student : {}", id);
		studentRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
