package ru.sotn.migratepostgresqltomongodb.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.sotn.migratepostgresqltomongodb.domain.mongo.AuthorMongo;
import ru.sotn.migratepostgresqltomongodb.domain.mongo.BookMongo;
import ru.sotn.migratepostgresqltomongodb.domain.mongo.CommentMongo;
import ru.sotn.migratepostgresqltomongodb.domain.mongo.GenreMongo;
import ru.sotn.migratepostgresqltomongodb.domain.sql.Author;
import ru.sotn.migratepostgresqltomongodb.domain.sql.Book;
import ru.sotn.migratepostgresqltomongodb.domain.sql.Genre;


import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Configuration
public class JobConfig {

    public static final String IMPORT_BOOK_JOB = "importBookJob";
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Logger logger = LoggerFactory.getLogger("Batch");
    @Bean
    public JpaPagingItemReader<Book> reader(EntityManagerFactory managerFactory){
        return  new JpaPagingItemReaderBuilder<Book>()
                .name("BookReader")
                .entityManagerFactory(managerFactory)
                .queryString("SELECT b FROM Book b")
                .build();
    }
    @Bean
    public ItemProcessor<Book, BookMongo> processor() {
        return (ItemProcessor<Book, BookMongo>) book->{
            String name = book.getName();
            String year = book.getYear();
            AuthorMongo author = new AuthorMongo(book.getName(), book.getYear());
            List<GenreMongo> genres = book.getGenres().stream().map(g->{
                return new GenreMongo(g.getNameGenre());
            }).collect(Collectors.toList());

            List<CommentMongo> commentMongos = book.getComments().stream().map(c->{
                return new CommentMongo(c.getText());
            }).collect(Collectors.toList());

            return new BookMongo(name,year,genres,author,commentMongos);
        };
    }

    @Bean
    public MongoItemWriter<BookMongo> writer(MongoTemplate mongoTemplate){
        return new MongoItemWriterBuilder<BookMongo>()
                .collection("books")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Job importBookInSqlToMongo(Step step1){
        return jobBuilderFactory.get(IMPORT_BOOK_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Start job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("End job");
                    }
                })
                .build();
    }

    @Bean
    public Step step1(JpaPagingItemReader<Book> reader, MongoItemWriter<BookMongo> writer, ItemProcessor<Book,BookMongo> itemProcessor){
        return stepBuilderFactory.get("step1")
                .<Book,BookMongo>chunk(1)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListener<Book>() {
                    @Override
                    public void beforeRead() {
                        logger.info("Start read");
                    }

                    @Override
                    public void afterRead(Book book) {
                        logger.info("Book " + book.getName()+" " + book.getAuthor().toString());
                    }

                    @Override
                    public void onReadError(Exception e) {
                        logger.info("Read error");
                    }
                })
                .build();
    }
}
