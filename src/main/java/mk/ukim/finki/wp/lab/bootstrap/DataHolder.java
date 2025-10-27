package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Book> books = null;
    public static List<BookReservation> reservations = null;

    @PostConstruct
    public void init() {
        books = new ArrayList<>();
        reservations = new ArrayList<>();

        books.add(new Book("Normal People", "Drama", 7.5));
        books.add(new Book("The Great Gatsby", "Classic", 8.2));
        books.add(new Book("Harry Potter and the Sorcerer's Stone", "Fantasy", 9.1));
        books.add(new Book("To Kill a Mockingbird", "Classic", 8.9));
        books.add(new Book("1984", "Dystopian", 8.7));
        books.add(new Book("The Hobbit", "Fantasy", 8.8));
        books.add(new Book("Pride and Prejudice", "Romance", 8.5));
        books.add(new Book("The Catcher in the Rye", "Fiction", 7.8));
        books.add(new Book("The Lord of the Rings", "Fantasy", 9.3));
        books.add(new Book("The Da Vinci Code", "Mystery", 7.6));


    }
}
