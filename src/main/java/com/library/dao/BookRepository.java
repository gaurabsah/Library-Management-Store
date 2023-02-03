package com.library.dao;

import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Category;
import com.library.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<List<Book>> findByBookNameContainingIgnoreCase(String bookName);

    Optional<List<Book>> findByCategory(Category category);

    Optional<List<Book>> findByPublisher(Publisher publisher);

    Optional<List<Book>> findByAuthor(Author author);
}
