package ru.sotn.migratepostgresqltomongodb.shell;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.sotn.migratepostgresqltomongodb.domain.sql.Book;
import ru.sotn.migratepostgresqltomongodb.repository.BookRepository;

import javax.batch.operations.JobOperator;

import java.util.ArrayList;
import java.util.List;

import static ru.sotn.migratepostgresqltomongodb.config.JobConfig.IMPORT_BOOK_JOB;

@ShellComponent
@AllArgsConstructor
public class BatchCommand {
    private final BookRepository bookRepository;
    private final JobLauncher jobLauncher;
    private final Job job;
//    private final JobOperator jobOperator;
    @ShellMethod(value = "startMigrationJobWithJobOperator", key = "sm-jo")
    public void startMigrationJobWithJobOperator() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
//        Long executionId = jobOperator.start(IMPORT_BOOK_JOB);
        jobLauncher.run(job, new JobParameters());
    }


//    @ShellMethod(value = "Get all books", key = "printAll")
//    private List<String> getAllBooks() {
//        List<Book> books = bookRepository.findAll();
//        List<String> str = new ArrayList<>();
//        for (Book b : books) {
//            str.add(b.getName() + b.getYear());
//        }
//        return str;
//    }
}
