package com.ninja.demo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Batch Model
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="tbl_lms_batch")
public class Batch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_lms_batch_batch_id_seq")
	@SequenceGenerator(name = "tbl_lms_batch_batch_id_seq", sequenceName = "tbl_lms_batch_batch_id_seq", allocationSize = 1)
	@Column(name = "batch_id")
	private int batchId;
	
	@Column(name = "batch_name")
	private String batchName;
	
	@Column(name = "batch_description")
	private String batchDescription;
	
	@Column(name = "batch_status")
	private String batchStatus;
		
	@Column(name = "batch_no_of_classes")
	private int batchNoOfClasses;
	
	@Column(name="creation_time")
	private Date creationTime = new Date();
	
	@Column(name="last_mod_time")
	private Date lastModTime = new Date();
	
	@ManyToOne
    @JoinColumn(name="batch_program_id", nullable=false)
	private Program program;

}
