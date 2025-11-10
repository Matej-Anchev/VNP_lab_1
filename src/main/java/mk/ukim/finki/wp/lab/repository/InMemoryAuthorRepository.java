package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.exceptions.AuthorNotFound;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryAuthorRepository implements AuthorRepository {

    private final List<Author> authors;

    // Конструктор каде ги иницијализираме авторите
    public InMemoryAuthorRepository() {
        authors = new ArrayList<>();
        authors.add(new Author("J.K.", "Rowling", "United Kingdom", "British author, best known for the Harry Potter series."));
        authors.add(new Author("George", "Orwell", "United Kingdom", "English novelist and essayist, known for '1984' and 'Animal Farm'."));
        authors.add(new Author("F. Scott", "Fitzgerald", "United States", "American novelist, famous for 'The Great Gatsby'."));

    }

    @Override
    public List<Author> findAll() {
        return authors;
    }

    @Override
    public Author findById(Long id) throws AuthorNotFound {
        return authors.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new AuthorNotFound("Author does not exist"));
    }

}
