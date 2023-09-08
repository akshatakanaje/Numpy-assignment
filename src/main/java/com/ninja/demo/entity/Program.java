package com.ninja.demo.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
* Program Model
*/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="tbl_lms_program")
public class Program {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_lms_program_program_id_seq")
	@SequenceGenerator(name = "tbl_lms_program_program_id_seq", sequenceName = "tbl_lms_program_program_id_seq", allocationSize = 1)
	@Column(name = "program_id")
	private int programId;
	
	@Column(name = "program_name")
	private String programName;
	
	@Column(name = "program_description")
	private String programDescription;
	
	@Column(name = "program_status")
	private String programStatus;
	
	@Column(name="creation_time")
	private Date creationTime = new Date();
	
	@Column(name="last_mod_time")
	private Date lastModTime = new Date();
	
	
	@OneToMany(mappedBy = "program", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
	private Set<Batch> batches = new HashSet<Batch>();

}
