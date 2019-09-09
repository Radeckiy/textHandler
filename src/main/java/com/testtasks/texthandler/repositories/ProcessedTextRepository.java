package com.testtasks.texthandler.repositories;

import com.testtasks.texthandler.models.ProcessedText;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProcessedTextRepository extends MongoRepository<ProcessedText, String> {
    ProcessedText findBy_id(ObjectId _id);
}
