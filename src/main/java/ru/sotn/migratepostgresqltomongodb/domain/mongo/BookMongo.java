package ru.sotn.migratepostgresqltomongodb.domain.mongo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Document("books")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@CompoundIndexes({
        @CompoundIndex(name = "name_author", def = "{'author.name' : 1, 'name': 1}", unique = true),
//        @CompoundIndex( def = "{'genres.name' : 1}", unique = false),
//        @CompoundIndex( def = "{'author.name' : 1}", unique = false)
})
public class BookMongo implements Serializable {
     

    @Id
    private String id;
    @Field("name")
    private String name;
    @Field("year")
    private String year;
    @Field("genres")
    private List<GenreMongo> genreMongos;
    @Field("author")
    private AuthorMongo authorMongo;


    private List<CommentMongo> commentMongos;

    public BookMongo(String id, String name, String year, GenreMongo genreMongo, AuthorMongo authorMongo, CommentMongo comments) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.genreMongos = Collections.singletonList(genreMongo);
        this.authorMongo = authorMongo;
        this.commentMongos = Collections.singletonList(comments);
    }

    public BookMongo(String id, String name, String year, GenreMongo genreMongo, AuthorMongo authorMongo) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.genreMongos = Collections.singletonList(genreMongo);
        this.authorMongo = authorMongo;
    }

    public BookMongo(String name, String year, List<GenreMongo> genreMongos, AuthorMongo authorMongo, List<CommentMongo> commentMongos) {
        this.name = name;
        this.year = year;
        this.genreMongos = genreMongos;
        this.authorMongo = authorMongo;
        this.commentMongos = commentMongos;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, genreMongos, authorMongo, commentMongos);
    }

    @Override
    public String toString() {
        return "Book{" +
                "idBook=" + id +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", genres=" + genreMongos +
                ", author=" + authorMongo +
                '}';
    }
}
