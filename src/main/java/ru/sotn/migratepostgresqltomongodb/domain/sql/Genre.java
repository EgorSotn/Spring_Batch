package ru.sotn.migratepostgresqltomongodb.domain.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "genres")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Genre {
    public Genre(String nameGenre) {
        this.nameGenre = nameGenre;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genre")
    private long idGenre;

    @Column(name = "name_genre", nullable = false, unique = true)
    private String nameGenre;
}
