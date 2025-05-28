package com.shelfwise.shelfwise.repository;

import com.shelfwise.shelfwise.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    BookEntity findBookEntitiesByBid(UUID id);

    List<BookEntity> findBookEntitiesByAuthorIgnoreCase(String author);

    List<BookEntity> findBookEntityByTitleIgnoreCase(String title);

    List<BookEntity> findBookEntityByGenreIgnoreCase(String genre);

    List<BookEntity> findBookEntitiesByAuthorAndTitleIgnoreCase(String author, String title);

    List<BookEntity> findBookEntitiesByAuthorAndGenreIgnoreCase(String author, String genre);

    List<BookEntity> findBookEntitiesByTitleAndGenreIgnoreCase(String title, String genre);

    List<BookEntity> findBookEntitiesByTitleAndGenreAndAuthorIgnoreCase(String title, String genre, String Author);
}
