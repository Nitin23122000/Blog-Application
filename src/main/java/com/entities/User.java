package com.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dto.PostDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long uid;
	
	private String username;
	
	private String password;
	
	@Column(name = "mobile")
	private String mobileno;
	
	private String address;
	
	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY) 
	private List<Post> posts;
	
	@OneToMany(mappedBy = "user",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<Comment> comment = new HashSet<>();
}
