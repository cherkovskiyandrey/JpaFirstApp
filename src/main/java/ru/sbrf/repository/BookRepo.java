package ru.sbrf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.domain.Book;

import java.util.Date;
import java.util.List;

public interface BookRepo extends JpaRepository<Book, Long> {

    Book findFirstByName(String name);
}
