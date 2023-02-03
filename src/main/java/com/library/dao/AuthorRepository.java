package com.library.dao;

import com.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Optional<List<Author>> findByAuthorName(String name);

    Optional<List<Author>> findByAuthorNameContainingIgnoreCase(String authorName);
}
