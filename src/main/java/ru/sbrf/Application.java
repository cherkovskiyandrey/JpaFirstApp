package ru.sbrf;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import ru.sbrf.configuration.MainConfig;
import ru.sbrf.domain.Author;
import ru.sbrf.domain.Book;
import ru.sbrf.repository.BookRepo;
import ru.sbrf.repository.UserRepo;

import java.util.Date;
import java.util.List;


public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        ctx.registerShutdownHook();

        final PlatformTransactionManager transactionalManager = ctx.getBean(PlatformTransactionManager.class);
        final TransactionTemplate tt = new TransactionTemplate(transactionalManager);
        final UserRepo userRepo = ctx.getBean(UserRepo.class);
        final BookRepo bookRepo = ctx.getBean(BookRepo.class);


        //Создаём автора
        inTransaction(tt, () -> {
            final Author author = userRepo.save(new Author("Andrey", "Cherkovskiy"));
            System.out.println(author.getId());
        });

        //Назначаем на книгу автора
        inTransaction(tt, () -> {
            final Author author = userRepo.findFirstByFirstNameAndLastName("Andrey", "Cherkovskiy");
            author.getBookList().add(new Book("Java book", new Date()));
        });

        //Смотрим какие книги написал автор
        inTransaction(tt, () -> {
            final Author author = userRepo.findFirstByFirstNameAndLastName("Andrey", "Cherkovskiy");
            System.out.println(author.getBookList());
        });

        //Смотрим кто написал книгу
        inTransaction(tt, () -> {
            final Book book = bookRepo.findFirstByName("Java book");
            System.out.println(book.getAuthorList());
        });

        //Назначаем на книгу ещё одного автора
        inTransaction(tt, () -> {
            final Book book = bookRepo.findFirstByName("Java book");
            final Author author = userRepo.save(new Author("Joshua", "Bloch"));
            author.getBookList().add(book);
        });

        //Удаляем первого автора
        inTransaction(tt, () -> {
            final Author author = userRepo.findFirstByFirstNameAndLastName("Andrey", "Cherkovskiy");
            userRepo.delete(author);
        });

        //Смотрим кто написал книгу
        inTransaction(tt, () -> {
            final Book book = bookRepo.findFirstByName("Java book");
            System.out.println(book.getAuthorList());
        });

        ctx.close();
    }


    private static void inTransaction(TransactionTemplate transactionTemplate, Runnable action) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                action.run();
            }
        });
    }
}
