package ru.sotn.migratepostgresqltomongodb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.sotn.migratepostgresqltomongodb.domain.sql.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
