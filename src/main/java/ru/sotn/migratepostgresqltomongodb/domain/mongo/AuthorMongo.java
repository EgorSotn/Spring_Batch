package ru.sotn.migratepostgresqltomongodb.domain.mongo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@Document("authors")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
//@CompoundIndexes({
//
//        @CompoundIndex( def = "{'name' : 1}", unique = true)
//})
public class AuthorMongo {
    @Id
    private String idAuthor;

    @Field("name")
    private String nameAuthor;

    @Field("year")
    private String year;

    public AuthorMongo(String nameAuthor, String year) {
        this.nameAuthor = nameAuthor;
        this.year = year;
    }
}
