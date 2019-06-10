package com.lms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.dto.AuthorDTO;
import com.lms.dto.BookDTO;
import com.lms.dto.CategoryDTO;
import com.lms.dto.PublisherDTO;
import com.lms.model.Author;
import com.lms.model.Book;
import com.lms.model.Category;
import com.lms.model.Publisher;
import com.lms.service.AuthorService;
import com.lms.service.BookService;
import com.lms.service.CategoryService;
import com.lms.service.PublisherService;
import com.lms.util.ErrorMessage;
import com.lms.util.FieldErrorMessage;

@RestController
public class BooksController {
	@Autowired
	private BookService bookService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private PublisherService publisherService;

	@PostMapping("/book")
	public BookDTO addBook(@Valid @RequestBody Book book) {
		Book temp =  bookService.save(book);
		ArrayList<CategoryDTO> cats = new ArrayList<>();
		CategoryDTO catt;
		ArrayList<AuthorDTO> auths = new ArrayList<>();
		AuthorDTO autt;
		PublisherDTO pub;

		for (int k = 0; k < temp.getCategories().size(); k++) {
			catt = new CategoryDTO(temp.getCategories().get(k).getId(), temp.getCategories().get(k).getType());
			cats.add(catt);
		}
		for (int k = 0; k < temp.getAuthors().size(); k++) {
			autt = new AuthorDTO(temp.getAuthors().get(k).getId(), temp.getAuthors().get(k).getName());
			auths.add(autt);
		}
		pub = new PublisherDTO(temp.getPublisher().getId(), temp.getPublisher().getName(),
				temp.getPublisher().getAddress());

		return new BookDTO(temp.getId(), temp.getTitle(), temp.getDatePublished(), pub, cats, auths);
		

	}
	@PutMapping("/book")
	public BookDTO updateBook(@Valid @RequestBody Book book) throws ValidationException{
		if(book.getId()==null) {
			throw new ValidationException("Required ID");
		}
		Book temp =  bookService.save(book);
		ArrayList<CategoryDTO> cats = new ArrayList<>();
		CategoryDTO catt;
		ArrayList<AuthorDTO> auths = new ArrayList<>();
		AuthorDTO autt;
		PublisherDTO pub;

		for (int k = 0; k < temp.getCategories().size(); k++) {
			catt = new CategoryDTO(temp.getCategories().get(k).getId(), temp.getCategories().get(k).getType());
			cats.add(catt);
		}
		for (int k = 0; k < temp.getAuthors().size(); k++) {
			autt = new AuthorDTO(temp.getAuthors().get(k).getId(), temp.getAuthors().get(k).getName());
			auths.add(autt);
		}
		pub = new PublisherDTO(temp.getPublisher().getId(), temp.getPublisher().getName(),
				temp.getPublisher().getAddress());

		return new BookDTO(temp.getId(), temp.getTitle(), temp.getDatePublished(), pub, cats, auths);

	}
	@DeleteMapping("/book/{id}")
	public ResponseEntity<ErrorMessage> deleteBook(@PathVariable Long id) throws ValidationException{
		Book b=bookService.findById(id);
		if(b!=null) {
			bookService.deleteById(id);
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("200", "Ok"), HttpStatus.OK);
		}
		else {
			throw new ValidationException("Don't Exist");
		}
	

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e) {
		List<FieldError> fieldError2 = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = fieldError2.stream()
				.map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
		return fieldErrorMessages;
	}

	@GetMapping("/books")
	public List<BookDTO> getBooks(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "datePublished", required = false) String datePublished,
			@RequestParam(value = "publisher", required = false) String publisher,
			@RequestParam(value = "author", required = false) String author,
			@RequestParam(value = "category", required = false) String category)throws NoSuchElementException {
		List<Book> temp;
		List<BookDTO> bookDTO = new ArrayList<>();
		if (title != null) {
			temp = bookService.findByTitle(title);
		} else if (datePublished != null) {
			temp = bookService.findByDatePublished(datePublished);
		} else if (publisher != null) {
			Publisher pub = publisherService.findByName(publisher);
			if (pub != null) {
				temp = bookService.findByPublisher(pub);
			} else {
				return bookDTO;
			}
		} else if (author != null) {
			Author auth = authorService.findByName(author);
			List<Author> auths = new ArrayList<>();
			auths.add(auth);
			if (auth != null) {
				temp = bookService.findByAuthors(auths);
			} else {
				return bookDTO;
			}
		} else if (category != null) {
			Category cat = categoryService.findByType(category);
//			List<Category> cats=new ArrayList<>();
//			cats.add(cat);
			if (cat != null) {
				/*
				 * we can also use find By for just one object we dont need to search by list
				 * like we did above in authors case both are working fine
				 */
				temp = bookService.findByCategories(cat);
			} else {
				return bookDTO;
			}
		}

		else {
			temp = bookService.findAll();
		}
		if(temp==null) {
			throw new NoSuchElementException("No Record Found");
		}

		ArrayList<CategoryDTO> cats = new ArrayList<>();
		CategoryDTO catt;
		ArrayList<AuthorDTO> auths = new ArrayList<>();
		AuthorDTO autt;
		PublisherDTO pub;
		for (int i = 0; i < temp.size(); i++) {
			for (int k = 0; k < temp.get(i).getCategories().size(); k++) {
				catt = new CategoryDTO(temp.get(i).getCategories().get(k).getId(),
						temp.get(i).getCategories().get(k).getType());
				cats.add(catt);
			}
			for (int k = 0; k < temp.get(i).getAuthors().size(); k++) {
				autt = new AuthorDTO(temp.get(i).getAuthors().get(k).getId(),
						temp.get(i).getAuthors().get(k).getName());
				auths.add(autt);
			}
			pub = new PublisherDTO(temp.get(i).getPublisher().getId(), temp.get(i).getPublisher().getName(),
					temp.get(i).getPublisher().getAddress());
			bookDTO.add(new BookDTO(temp.get(i).getId(), temp.get(i).getTitle(), temp.get(i).getDatePublished(), pub,
					cats, auths));

			/*
			 * list.clear clear the reference and we are passing ref to costructor , so that
			 * list also get cleared and get null result at the end auths.clear();
			 * cats.clear();
			 */
			cats = new ArrayList<>();
			auths = new ArrayList<>();
		}
		return bookDTO;

	}

	@GetMapping("/book/title/{title}")
	public List<BookDTO> getBookByTitle(@PathVariable String title) {
		List<BookDTO> bookDTO = new ArrayList<>();
		List<Book> temp = bookService.findByTitle(title);
		ArrayList<CategoryDTO> cats = new ArrayList<>();
		CategoryDTO catt;
		ArrayList<AuthorDTO> auths = new ArrayList<>();
		AuthorDTO autt;
		PublisherDTO pub;
		for (int i = 0; i < temp.size(); i++) {
			for (int k = 0; k < temp.get(i).getCategories().size(); k++) {
				catt = new CategoryDTO(temp.get(i).getCategories().get(k).getId(),
						temp.get(i).getCategories().get(k).getType());
				cats.add(catt);
			}
			for (int k = 0; k < temp.get(i).getAuthors().size(); k++) {
				autt = new AuthorDTO(temp.get(i).getAuthors().get(k).getId(),
						temp.get(i).getAuthors().get(k).getName());
				auths.add(autt);
			}
			pub = new PublisherDTO(temp.get(i).getPublisher().getId(), temp.get(i).getPublisher().getName(),
					temp.get(i).getPublisher().getAddress());
			bookDTO.add(new BookDTO(temp.get(i).getId(), temp.get(i).getTitle(), temp.get(i).getDatePublished(), pub,
					cats, auths));

			/*
			 * list.clear clear the reference and we are passing ref to costructor , so that
			 * list also get cleared and get null result at the end auths.clear();
			 * cats.clear();
			 */
			cats = new ArrayList<>();
			auths = new ArrayList<>();
		}
		return bookDTO;

	}

	@GetMapping("/book/id/{id}")
	public BookDTO getBookById(@PathVariable Long id) throws  NoSuchElementException{
System.out.println("Asdfasdf");
		Book temp = bookService.findById(id);
		System.out.println("ddddddddddddddd");
		if(temp==null) {
			throw new NoSuchElementException("No Record Found");
		}
		ArrayList<CategoryDTO> cats = new ArrayList<>();
		CategoryDTO catt;
		ArrayList<AuthorDTO> auths = new ArrayList<>();
		AuthorDTO autt;
		PublisherDTO pub;

		for (int k = 0; k < temp.getCategories().size(); k++) {
			catt = new CategoryDTO(temp.getCategories().get(k).getId(), temp.getCategories().get(k).getType());
			cats.add(catt);
		}
		for (int k = 0; k < temp.getAuthors().size(); k++) {
			autt = new AuthorDTO(temp.getAuthors().get(k).getId(), temp.getAuthors().get(k).getName());
			auths.add(autt);
		}
		pub = new PublisherDTO(temp.getPublisher().getId(), temp.getPublisher().getName(),
				temp.getPublisher().getAddress());

		return new BookDTO(temp.getId(), temp.getTitle(), temp.getDatePublished(), pub, cats, auths);

	}

}
