package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.exceptions.AuthorNotFound;

import java.util.List;


public interface AuthorRepository {
    public List<Author> findAll();
    public Author findById(Long id) throws AuthorNotFound;
}
