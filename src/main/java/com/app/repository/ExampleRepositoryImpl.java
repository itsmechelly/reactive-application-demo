package com.app.repository;

import com.app.model.domain.Example;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Repository
public class ExampleRepositoryImpl implements ExampleRepository {

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public Flux<Example> getAllExample(final Integer age) {
        log.info("getAllExample(), for age: {}", age);
        if (age == null) {
            return reactiveMongoTemplate.findAll(Example.class, Example.COLLECTION_NAME);
        }
        final Criteria criteria = Criteria.where(Example.AGE_FIELD).is(age);
        final Query query = Query.query(criteria);
        return getAllExamplesByQuery(query);
    }

    private Flux<Example> getAllExamplesByQuery(final Query query) {
        return reactiveMongoTemplate.find(query, Example.class, Example.COLLECTION_NAME);
    }

    @Override
    public Mono<Example> getExampleById(final String id) {
        log.info("getExampleById(), for id: {}", id);
        return reactiveMongoTemplate.findById(id, Example.class, Example.COLLECTION_NAME);
    }

    @Override
    public Mono<Example> createExample(final Example example) {
        log.info("createExample() for example: {}", example);
        return reactiveMongoTemplate.save(example, Example.COLLECTION_NAME);
    }
}
