package io.biker.management.dbSequence.service;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import io.biker.management.dbSequence.entity.DatabaseSequence;

@Service
public class SequenceGeneratorService {
    private MongoOperations mongoOperations;

    public SequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public int generateSequence(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();

        DatabaseSequence counter = mongoOperations.findAndModify(query, update, options.returnNew(true).upsert(true),
                DatabaseSequence.class);
        if (counter == null) {
            return 1;
        } else {
            return counter.getSeq();
        }
    }
}
