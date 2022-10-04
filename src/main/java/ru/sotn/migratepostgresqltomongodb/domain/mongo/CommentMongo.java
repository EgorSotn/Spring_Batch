package ru.sotn.migratepostgresqltomongodb.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;


@Document("comments")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CommentMongo {
    @Id
    private String id_comment;


    @Field("book")
    private BookMongo bookMongo;

    @Field("text")
    private String text;

    public CommentMongo(String id_comment, String text) {
        this.id_comment = id_comment;
        this.text = text;
    }

    public CommentMongo(String text) {
        this.text = text;
    }



    @Override
    public String toString() {
        return "Comment{" +
                "idComment=" + id_comment +
                ", text='" + text + '\'' +
                '}';
    }

}
