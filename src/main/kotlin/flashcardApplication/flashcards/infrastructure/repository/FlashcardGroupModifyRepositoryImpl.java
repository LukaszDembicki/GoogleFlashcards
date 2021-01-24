package flashcardApplication.flashcards.infrastructure.repository;

import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

public class FlashcardGroupModifyRepositoryImpl implements FlashcardGroupModifyRepository {
    private MongoTemplate mongoTemplate;

    @Autowired
    public FlashcardGroupModifyRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void updateFlashcardKnowledgeTypeById(String id, String knowledgeType, String userId) {
        Query query = new Query(new Criteria()
                .andOperator(
                        Criteria.where("flashcardList._id").is(new ObjectId(id)),
                        Criteria.where("userId").is(userId)
                )
        );

        Update update = new Update();
        update.set("flashcardList.$.knowledgeType", knowledgeType);
        update.set("flashcardList.$.updatedAt", new Date());

        this.mongoTemplate.updateFirst(query, update, FlashcardGroup.class);
    }
}
