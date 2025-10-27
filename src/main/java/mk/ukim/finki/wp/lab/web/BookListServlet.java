package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.repository.InMemoryBookRepository;
import mk.ukim.finki.wp.lab.service.BookService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "bookListServlet", urlPatterns = "/books")
public class BookListServlet extends HttpServlet {

    private BookService bookService;

    private final SpringTemplateEngine springTemplateEngine;

    public BookListServlet(BookService bookService, SpringTemplateEngine springTemplateEngine) {
        this.bookService = bookService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        IWebExchange webExchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(webExchange);

        String title = req.getParameter("title");
        String minRatingParam = req.getParameter("minRating");

        List<Book> books = null;

        if (title != null && !title.isBlank() && minRatingParam != null && !minRatingParam.isBlank()) {
            try {
                double minRating = Double.parseDouble(minRatingParam);
                books = bookService.searchBooks(title.trim(), minRating);
            } catch (NumberFormatException e) {
                books = java.util.Collections.emptyList();
                context.setVariable("searchError", "Please enter a valid number for rating.");
            }
        }

        context.setVariable("books", books);

        springTemplateEngine.process("listBooks.html", context, resp.getWriter());
    }

}
