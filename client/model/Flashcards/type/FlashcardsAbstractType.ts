class FlashcardsAbstractType {

    static FLASHCARD_KNOWLEDGE_TYPE_KNOW = 'Know';
    static FLASHCARD_KNOWLEDGE_TYPE_DONT_KNOW = 'Don\'t know';
    static FLASHCARD_KNOWLEDGE_TYPE_UNASSIGNED = 'Unassigned';
    static FLASHCARD_SIDE_LEFT = 'textLeft';
    static FLASHCARD_SIDE_RIGHT = 'textRight';

    flashcardsList = [];

    currentFlashcard = null;
    currentFlashcardId = 1;

    currentFlashcardSide = FlashcardsAbstractType.FLASHCARD_SIDE_LEFT;
    totalFlashcards = 0;

    addFlashcardToList(flashcard) {
        this.flashcardsList.push(flashcard);
    }

    reset() {
        this.flashcardsList = [];
        this.currentFlashcard = null;
        this.totalFlashcards = 0;
    }

    init() {
        // assign flashcards ids
        this.flashcardsList.map((flashcard, i) => {
            flashcard.flashcardId = i + 1;
        });

        this.matchCurrentFlashcardId();
    }

    matchCurrentFlashcardId() {
        // set current flashcard
        for (let [key, flashcard] of Object.entries(this.flashcardsList)) {
            if (flashcard.flashcardId === this.getCurrentFlashcardId()) {
                return this.currentFlashcard = flashcard;
            }
        }

        // ensure that no flashcards remain in type
        let flashcardListLength = this.flashcardsList.length;
        if (flashcardListLength > 0) {
            this.currentFlashcardId = flashcardListLength;
            this.currentFlashcard = this.flashcardsList[flashcardListLength - 1];
            return;
        }

        this.currentFlashcard = null;
    }

    getCurrentFlashcard() {
        return this.currentFlashcard;
    }

    getCurrentFlashcardId() {
        return this.currentFlashcardId;
    }

    getFlashcardText() {
        let currentFlashcard = this.currentFlashcard;
        if (currentFlashcard === null) {
            return '';
        }

        if (this.currentFlashcardSide === FlashcardsAbstractType.FLASHCARD_SIDE_LEFT) {
            return currentFlashcard.textLeft;
        } else {
            return currentFlashcard.textRight;
        }
    }

    flipFlashcard() {
        if (this.currentFlashcardSide === FlashcardsAbstractType.FLASHCARD_SIDE_LEFT) {
            this.currentFlashcardSide = FlashcardsAbstractType.FLASHCARD_SIDE_RIGHT;
        } else {
            this.currentFlashcardSide = FlashcardsAbstractType.FLASHCARD_SIDE_LEFT;
        }
    }

    getTotalFlashcards() {
        return this.totalFlashcards = this.flashcardsList.length;
    }

    nextFlashcard() {
        if (this.currentFlashcardId >= this.getTotalFlashcards()) {
            return;
        }

        this.currentFlashcardId++;
    }

    prevFlashcard() {
        if (this.currentFlashcardId <= 1) {
            return;
        }

        this.currentFlashcardId--;
    }
}

export default FlashcardsAbstractType