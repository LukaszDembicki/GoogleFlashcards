package flashcardApplication.testPackage.mongodb;

import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import org.bson.types.ObjectId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MongoRepositoryInterface extends MongoRepository<FlashcardGroup, String> {
    @Query("{ 'flashcardList.id' : ?0 }")
    public Optional<FlashcardGroup> findById(String id);

    @Modifying
    @Query("{'flashcardList._id' :objectId}, {$set:{'flashcardList.$.textRight':':textRight'}})")
    void updateTextRight(@Param(value = "id") ObjectId objectId, @Param(value = "textRight") String textRight);

/*
    @Modifying
    @Query("update Customer u set u.phone = :phone where u.id = :id")
    void updatePhone(@Param(value = "id") long id, @Param(value = "phone") String phone);
*/

}
