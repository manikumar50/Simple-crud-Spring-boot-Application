package com.example.SampleCrudOperation;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller 
{
	@Autowired
	StudentRep st;
	
	
	//get -> request
	
	// fetch all data
	@GetMapping("/students")
	public List<Student> getAllStudent()
	{
		return st.findAll();
	}
	
	//fetch using Stdeunt id
	@GetMapping("/student{id}")
	public ResponseEntity getById(@PathVariable("id") int id)
	{
		Student std=st.findById(id).orElse(null);
		return (std==null)? new ResponseEntity<>(HttpStatus.NOT_FOUND):new ResponseEntity<>(std,HttpStatus.OK);
	}
	
	// Create a new Row or add new row
	
	@PostMapping("/addstd")
	public ResponseEntity<Student> addStudent(@RequestBody Student std)
	{
		if(std==null) 
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		else
		{
			Student newStd=st.save(std);
			return new ResponseEntity<>(newStd,HttpStatus.OK);
		}
	}
	
	
	//put -> update
	@PutMapping("/update{id}")
	public ResponseEntity<Student> UpdateById(@PathVariable("id") int id,@RequestBody Student std)
	{
		Student currentStd=st.findById(id).orElse(null);
		
		if(currentStd==null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		// here update is only possible in Non primary key fields, here id can't change
		currentStd.setName(std.getName());
		currentStd.setRollno(std.getRollno());
		
		Student Upstd=st.save(currentStd);
		
		return new ResponseEntity<>(Upstd,HttpStatus.OK);
	}
	
	
	// delete a student entry by student id
	@DeleteMapping("/remove{id}")
	public ResponseEntity deleteStd(@PathVariable("id") int id)
	{
		Student crrstd=st.findById(id).orElse(null);
		if(crrstd==null)
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else{
			st.delete(crrstd);
			return new ResponseEntity<>(st.findAll(),HttpStatus.OK);
		}
		
	}


}
