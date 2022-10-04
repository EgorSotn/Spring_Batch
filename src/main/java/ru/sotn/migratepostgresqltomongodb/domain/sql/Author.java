package ru.sotn.migratepostgresqltomongodb.domain.sql;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_author")
    private long idAuthor;

    @Column(name = "name_author", nullable = false, unique = true)
    private String nameAuthor;

    @Column(name = "year_author")
    private String year;

    public Author(String nameAuthor, String year) {
        this.nameAuthor = nameAuthor;
        this.year = year;
    }
}
