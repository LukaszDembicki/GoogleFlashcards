import flashcardsAPI from "../../lib/api/flashcards";
import FlashcardsKnowType from "./type/FlashcardsKnowType"
import FlashcardsDontKnowType from "./type/FlashcardsDontKnowType";
import FlashcardsAbstractType from "./type/FlashcardsAbstractType";
import FlashcardsUnassignedType from "./type/FlashcardsUnassignedType";

class FlashcardsModel {

    flashcardsKnowType;
    flashcardsDontKnowType;
    flashcardsUnassignedType;

    currentFlashcardsType = FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_UNASSIGNED;
    currentFlashcardsTypeObject;

    flashcardList = [
        /*
            {
                "id": "5fcd254ecf7d001e21fe7648",
                "flashcardId": 0,
                "knowledgeType": "",
                "textLeft": "",
                "textRight": "",
                "createdAt": 1607279950133,
                "updatedAt": null
            }
         */
    ];

    constructor() {
        this.flashcardsKnowType = new FlashcardsKnowType();
        this.flashcardsDontKnowType = new FlashcardsDontKnowType();
        this.flashcardsUnassignedType = new FlashcardsUnassignedType();

        this.currentFlashcardsTypeObject = this.flashcardsUnassignedType;
    }

    setFlashcardList(flashcardList) {
        this.flashcardList = flashcardList
        this.recalculateFlashcards();
    }

    recalculateFlashcards() {
        this.flashcardsKnowType.reset();
        this.flashcardsDontKnowType.reset();
        this.flashcardsUnassignedType.reset();

        // fill flashcards type objects
        for (let [key, flashcard] of Object.entries(this.flashcardList)) {
            // set flashcards by type
            switch (flashcard.knowledgeType) {
                case FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_KNOW:
                    this.flashcardsKnowType.addFlashcardToList(flashcard);
                    break;
                case FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_DONT_KNOW:
                    this.flashcardsDontKnowType.addFlashcardToList(flashcard);
                    break;
                default:
                    this.flashcardsUnassignedType.addFlashcardToList(flashcard);
            }
        }

        switch (this.currentFlashcardsType) {
            case FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_KNOW:
                this.currentFlashcardsTypeObject = this.flashcardsKnowType;
                break;
            case FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_DONT_KNOW:
                this.currentFlashcardsTypeObject = this.flashcardsDontKnowType;
                break;
            default:
                this.currentFlashcardsTypeObject = this.flashcardsUnassignedType;
        }

        this.currentFlashcardsTypeObject.init();
    }

    getCurrentFlashcardText() {
        return this.currentFlashcardsTypeObject.getFlashcardText();
    }

    getCurrentFlashcardNumber() {
        if (this.getTotalFlashcardsInCurrentType() === 0) {
            return 0;
        }

        return this.currentFlashcardsTypeObject.getCurrentFlashcardId();
    }

    getTotalFlashcardsInKnowType() {
        return this.flashcardsKnowType.getTotalFlashcards()
    }

    getTotalFlashcardsInDontKnowType() {
        return this.flashcardsDontKnowType.getTotalFlashcards();
    }

    getTotalFlashcardsInUnassignedType() {
        return this.flashcardsUnassignedType.getTotalFlashcards();
    }

    setCurrentFlashcardsType(type) {
        if (this.currentFlashcardsType === type) {
            return;
        }

        this.currentFlashcardsType = type;
        this.recalculateFlashcards();
    }

    getTotalFlashcardsInCurrentType() {
        return this.currentFlashcardsTypeObject.getTotalFlashcards();
    }

    flipFlashcard() {
        this.currentFlashcardsTypeObject.flipFlashcard();
    }

    nextFlashcard() {
        this.currentFlashcardsTypeObject.nextFlashcard();
        this.recalculateFlashcards();
    }

    prevFlashcard() {
        this.currentFlashcardsTypeObject.prevFlashcard();
        this.recalculateFlashcards();
    }

    moveFlashcardToNewType(type) {
        if (
            this.currentFlashcardsType === type ||
            this.currentFlashcardsTypeObject.getCurrentFlashcard() === null
        ) {
            return;
        }

        this.currentFlashcardsTypeObject.getCurrentFlashcard().knowledgeType = type;
        flashcardsAPI.updateFlashcardKnowledgeType(
            this.currentFlashcardsTypeObject.getCurrentFlashcard()
        );

        this.recalculateFlashcards();
    }
}

export default FlashcardsModel;