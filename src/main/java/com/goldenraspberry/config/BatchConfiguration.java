package com.goldenraspberry.config;

import com.goldenraspberry.domain.entity.MovieEntity;
import com.goldenraspberry.domain.repository.MovieRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MovieRepository movieRepository;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MovieRepository movieRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.movieRepository = movieRepository;
    }

    @Bean
    public FlatFileItemReader<MovieEntity> reader() {
        return new FlatFileItemReaderBuilder<MovieEntity>()
                .name("movieItemReader")
                .resource(new ClassPathResource("movielist.csv"))
                .linesToSkip(1)
                .lineTokenizer(createStudentLineTokenizer())
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(MovieEntity.class);
                }})
                .build();
    }

    @Bean
    public RepositoryItemWriter<MovieEntity> writer() {
        return new RepositoryItemWriterBuilder<MovieEntity>()
                .repository(movieRepository)
                .methodName("save")
                .build();
    }

    private LineTokenizer createStudentLineTokenizer() {
        DelimitedLineTokenizer studentLineTokenizer = new DelimitedLineTokenizer();
        studentLineTokenizer.setDelimiter(";");
        studentLineTokenizer.setNames("year", "title", "studio", "producer", "winner");
        return studentLineTokenizer;
    }

    @Bean
    public Step step1(ItemReader<MovieEntity> itemReader, ItemWriter<MovieEntity> itemWriter) {
        return this.stepBuilderFactory.get("step1")
                .<MovieEntity, MovieEntity>chunk(5)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Job movieUpdateJob(JobCompletionNotificationListener listener, Step step1) {
        return this.jobBuilderFactory.get("movieUpdateJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }

}
