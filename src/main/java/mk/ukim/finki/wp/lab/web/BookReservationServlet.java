package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@WebServlet(name = "bookReservationServlet", urlPatterns = "/bookReservation")
public class BookReservationServlet extends HttpServlet {

    private final BookReservationService bookReservationService;
    private final SpringTemplateEngine springTemplateEngine;

    public BookReservationServlet(BookReservationService bookReservationService, SpringTemplateEngine springTemplateEngine) {
        this.bookReservationService = bookReservationService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String title = req.getParameter("bookTitle");
        String readerName = req.getParameter("readerName");
        String readerAddress = req.getParameter("readerAddress");
        long numberOfCopies = Long.parseLong(req.getParameter("numberOfCopies"));
        String clientIP = req.getRemoteAddr();

        BookReservation reservation = bookReservationService.placeReservation(title, readerName, readerAddress, numberOfCopies, clientIP);

        IWebExchange webExchange = JakartaServletWebApplication.buildApplication(getServletContext())
                .buildExchange(req, resp);
        WebContext context = new WebContext(webExchange);
        context.setVariable("reservation", reservation);

        // Handle recentBooks
        HttpSession session = req.getSession(true);
        List<String> recentBooks = (List<String>) session.getAttribute("recentBooks");
        if (recentBooks == null) {
            recentBooks = new ArrayList<>();
        }

        // Add the newly reserved book
        recentBooks.add(title);
        if (recentBooks.size() > 3) {
            recentBooks.remove(0);
        }
        session.setAttribute("recentBooks", recentBooks);

        springTemplateEngine.process("reservationConfirmation.html", context, resp.getWriter());
    }
}
