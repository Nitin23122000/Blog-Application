package com.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Category;
import com.entities.Post;
import com.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	
	Page<Post> findByUser(User user, Pageable pg);
	
	Page<Post> findByCategory(Category category,Pageable pg);
	
}
