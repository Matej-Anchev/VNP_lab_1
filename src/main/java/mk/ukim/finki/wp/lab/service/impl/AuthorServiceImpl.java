package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.model.exceptions.AuthorNotFound;
import mk.ukim.finki.wp.lab.repository.AuthorRepository;
import mk.ukim.finki.wp.lab.repository.InMemoryAuthorRepository;
import mk.ukim.finki.wp.lab.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository rep;

    public AuthorServiceImpl(AuthorRepository rep) {
        this.rep = rep;
    }

    @Override
    public List<Author> findAll() {
        return rep.findAll();
    }

    @Override
    public Author findById(long id) throws AuthorNotFound {
        return rep.findById(id);
    }
}
