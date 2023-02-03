package com.library.controller;

import com.library.dto.BookDTO;
import com.library.dto.CategoryDTO;
import com.library.service.BookService;
import com.library.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO category = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDto, @PathVariable int categoryId) {
        CategoryDTO updatedCategoryDto = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable int categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted...", HttpStatus.OK);
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable int categoryId) {
        CategoryDTO categoryDto = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "categoryId", required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = "asc", required = false) String sortOrder
    ) {
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryDTOList, HttpStatus.OK);
    }

    @PostMapping("/{categoryId}/addBook")
    public ResponseEntity<BookDTO> createBookWithCategory(@Valid @RequestBody BookDTO bookDTO, @PathVariable int categoryId) {
        BookDTO bookWithCategory = bookService.createBookWithCategory(bookDTO, categoryId);
        return new ResponseEntity<>(bookWithCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}/updateBook/{bookId}")
    public ResponseEntity<BookDTO> updateBookWithCategory(@PathVariable int bookId, @PathVariable int categoryId) {
        BookDTO bookDTO = bookService.updateBookWithCategory(bookId, categoryId);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/getBook")
    public ResponseEntity<List<BookDTO>> getAllBooksWithCategory(@PathVariable int categoryId) {
        List<BookDTO> bookByCategory = bookService.getBookByCategory(categoryId);
        return new ResponseEntity<>(bookByCategory, HttpStatus.OK);
    }
}
