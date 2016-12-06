package ru.sbrf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sbrf.domain.Author;

import java.util.List;

public interface UserRepo extends JpaRepository<Author, Long> {

    Author findFirstByFirstNameAndLastName(String firstName, String lastName);

}
