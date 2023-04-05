package com.app.model.domain;

import com.app.core.model.domain.BaseDomain;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Document(collection = Example.COLLECTION_NAME)
public class Example extends BaseDomain {

    public static final String COLLECTION_NAME = "example";
    public static final String NAME_FIELD = "name";
    public static final String AGE_FIELD = "age";

    @Field(NAME_FIELD)
    private String name;

    @Field(AGE_FIELD)
    @Indexed
    private Integer age;
}
