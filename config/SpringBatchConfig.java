package com.bridgelabz.candidatebatchproject.config;

import com.bridgelabz.candidatebatchproject.model.CandidateModel;
import com.bridgelabz.candidatebatchproject.repository.ICandidateRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
//@AllArgsConstructor
//@NoArgsConstructor
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ICandidateRepository candidateRepository;

    @Bean
    public FlatFileItemReader<CandidateModel> reader(){

        FlatFileItemReader<CandidateModel> itemReader=new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/candidate.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;

    }
    @Bean
    LineMapper<CandidateModel> lineMapper() {
        DefaultLineMapper<CandidateModel> lineMapper=new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","cicId","fullName","email","mobileNo","hireDate","degree","aggPer","city","state","preferredJobLocation","passOutYear","creatorUser","candidateStatus");

        BeanWrapperFieldSetMapper<CandidateModel> fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(CandidateModel.class);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        lineMapper.setLineTokenizer(lineTokenizer);
        return lineMapper;

    }

    @Bean
    public CandidateProcessor candidateProcessor()
    {
        return new CandidateProcessor();
    }

    public RepositoryItemWriter<CandidateModel> writer(){
        RepositoryItemWriter<CandidateModel> itemWriter=new RepositoryItemWriter<>();
        itemWriter.setRepository(candidateRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public Step step(){
      return   stepBuilderFactory.get("csvStep").<CandidateModel,CandidateModel>chunk(10)
                .reader(reader())
                .processor(candidateProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job(){
        return  jobBuilderFactory.get("importCandidateDetails")
                .start(step()).build();
    }

}
