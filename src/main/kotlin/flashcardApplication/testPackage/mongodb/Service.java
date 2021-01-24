package flashcardApplication.testPackage.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import flashcardApplication.flashcards.infrastructure.model.Flashcard;
import flashcardApplication.flashcards.infrastructure.model.FlashcardGroup;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@org.springframework.stereotype.Service
public class Service {

    private final MongoRepositoryInterface mongoRepositoryInterface;
    private final MongoOperations mongoOperations;

    @Autowired
    public Service(
            MongoRepositoryInterface mongoRepositoryInterface,
            MongoOperations mongoOperations
    ) {
        this.mongoRepositoryInterface = mongoRepositoryInterface;
        this.mongoOperations = mongoOperations;
    }

    public void findByFlashcardId(String id) {

//        this.mongoOperations.find()


//        Optional<FlashcardGroup> result = mongoRepositoryInterface.findById(id);
//
//        String a = "";
    }

    public void updateTextRight(String id, String textRight) {
        ObjectId objectId = new ObjectId(id);
        mongoRepositoryInterface.updateTextRight(objectId, textRight);
    }












    private MongoOperations getConnection() {
        // CONNECTION

        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27021/database");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return new MongoTemplate(MongoClients.create(mongoClientSettings), "database");
    }


    /**
     * FlashcardGroup need to have     @DBRef
     * https://docs.spring.io/spring-data/mongodb/docs/1.3.3.RELEASE/reference/html/mapping-chapter.html
     */
    public void testRelation() {
        MongoOperations mongoOperations = this.getConnection();



        // automatycznie dodawać createAt i updatedAt


        String a = "";


    }

    /**
     * https://docs.spring.io/spring-data/data-mongodb/docs/current-SNAPSHOT/reference/html/#reference
     * new Update..
     */
    private void pullPushEmbeded()
    {

    }

    private void removeFlashcardById() {
        MongoOperations mongoOperations = this.getConnection();
//        mongoOperations.findAndModify()
        FlashcardGroup resultFlashcardGroup = mongoOperations.findOne(new Query(where("flashcardList").is("5f7a1d7b1441b8013ad9179e")), FlashcardGroup.class);
    }

    private FlashcardGroup addFlashcardToFlashcardGroup(FlashcardGroup flashcardGroup) {
        Flashcard flashcardEntity = new Flashcard();
        flashcardEntity.setTextRight("some");
        flashcardEntity.setTextLeft("some2");

        flashcardGroup.addFlashcard(flashcardEntity);

        MongoOperations mongoOperations = this.getConnection();
        return mongoOperations.save(flashcardGroup);
    }

    private FlashcardGroup createFlashcardGroup(String userId) {
        MongoOperations mongoOperations = this.getConnection();

        FlashcardGroup flashcardGroup = new FlashcardGroup();
        List<Flashcard> flashcardList = new ArrayList<>();
        flashcardGroup.setName("Flashcard group ");
        flashcardGroup.setUserId("1");
        if (userId != null) {
            flashcardGroup.setUserId(userId);
            flashcardGroup.setName("Flashcard group " + "userId " + userId);
        }

        return mongoOperations.save(flashcardGroup);
    }

    private FlashcardGroup insertFlashcardWithFlashcardGroup() {
        MongoOperations mongoOperations = this.getConnection();

        Flashcard flashcardEntity = new Flashcard();
        flashcardEntity.setTextRight("some");
        flashcardEntity.setTextLeft("some2");
        flashcardEntity.setId(new ObjectId().toString());
//        mongoOperations.insert(flashcardEntity);

        FlashcardGroup flashcardGroup = new FlashcardGroup();
        List<Flashcard> flashcardList = new ArrayList<>();
        String userId = "1";

        flashcardEntity.setKnowledgeType("test knowledge type");
        flashcardEntity.setTextRight("text right");
        flashcardEntity.setTextLeft("text left");
        flashcardList.add(flashcardEntity);

        flashcardGroup.setUserId(userId);
        flashcardGroup.setName("test name");
        flashcardGroup.setOrderNumber(1);
        flashcardGroup.setFlashcardList(flashcardList);

        return mongoOperations.insert(flashcardGroup);
    }


    public void test() {
        // CONNECTION
        MongoOperations mongoOps = this.getConnection();

        // INSERT
//        mongoOps.insert(new Person("Joe", 34));

        // SELECT
        System.out.println(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));


        // DROP COLLECTION
//        mongoOps.dropCollection("person");
    }

    public void flashcardGroup() {
        MongoOperations mongoOperations = this.getConnection();

//        Query query = new Query();
//        query.

//        mongoOperations.executeQuery();


//         insert
//        FlashcardGroup flashcardGroup = new FlashcardGroup();
//        Flashcard flashcardEntity = new Flashcard();
//        List<Flashcard> flashcardList = new ArrayList<>();
//        String userId = "1";
//
//
//        flashcardEntity.setKnowledgeType("test knowledge type");
//        flashcardEntity.setTextRight("text right");
//        flashcardEntity.setTextLeft("text left");
//        flashcardList.add(flashcardEntity);
//
//        flashcardGroup.setUserId(userId);
//        flashcardGroup.setName("test name");
//        flashcardGroup.setOrderNumber(1);
//        flashcardGroup.setFlashcardList(flashcardList);
//
//        mongoOperations.insert(flashcardGroup);

        // update
//        FlashcardGroup resultFlashcardGroup = mongoOperations.findOne(new Query(where("name").is("test name")), FlashcardGroup.class);
//        Flashcard resultFlashcard = resultFlashcardGroup.getFlashcardList().get(0);
//        resultFlashcard.setTextLeft("New updated text left!");

        // mongoOps.updateFirst(query(where("name").is("Joe")), update("age", 35), Person.class);
//        mongoOperations.updateFirst(Query.query(where("name").is("test name")), Update.update("flashcardList.$.textLeft", "new updated text left"), FlashcardGroup.class);

//        mongoOperations.save(resultFlashcardGroup);

    }
}
