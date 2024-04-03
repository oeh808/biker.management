package io.biker.management.dbSequence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "DB Sequence")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseSequence {
    @Id
    private String id;

    private int seq;
}
