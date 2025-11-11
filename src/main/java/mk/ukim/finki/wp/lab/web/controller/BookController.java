package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import mk.ukim.finki.wp.lab.service.impl.AuthorServiceImpl;
import mk.ukim.finki.wp.lab.service.impl.BookServiceImpl;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookServiceImpl bookService;
    private final AuthorServiceImpl authorService;

    public BookController(BookServiceImpl bookService, AuthorServiceImpl authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String getBooksPage(@RequestParam(required = false) String error,
                               @RequestParam (required = false) String title,
                               @RequestParam (required = false) String minRating,
                               Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        List<Book> books = new ArrayList<>();

        if (title != null && !title.isEmpty() && minRating != null && !minRating.isEmpty()) {
            books = bookService.searchBooks(title, Double.valueOf(minRating));
        } else {
            books = bookService.listAll();
        }

        model.addAttribute("books", books);

        return "listBooks";
    }

    @PostMapping("/add")
    public String saveBook(@RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam Double averageRating,
                           @RequestParam Long authorId) {

        Author author = authorService.findById(authorId);
        bookService.save(new Book(title, genre, averageRating, author));

        return "redirect:/books";
    }


    @PostMapping("/edit/{bookId}")
    public String editBook(@PathVariable Long bookId,
                           @RequestParam String title,
                           @RequestParam String genre,
                           @RequestParam Double averageRating,
                           @RequestParam Long authorId) {

        bookService.delete(bookId);

        Author author = authorService.findById(authorId);

        bookService.save(new Book(title, genre, averageRating, author));

        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/book-form/{id}")
    public String getEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.listAll()
                .stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (book == null) {
            return "redirect:/books?error=BookNotFound";
        }
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        return "book-form";
    }

    @GetMapping("/book-form")
    public String getAddBookPage(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "book-form";
    }

    /*@GetMapping("/search")
    public String Search(@RequestParam("title") String title, @RequestParam("minRating") String minRating, Model model) {
        if (title != null && !title.isEmpty() && minRating != null && !minRating.isEmpty()) {
            minRating = String.valueOf(Double.parseDouble(minRating));

            List<Book> books = bookService.searchBooks(title, Double.valueOf(minRating));
            model.addAttribute("books", books);

        }
        return "redirect:/books";
    }*/
}