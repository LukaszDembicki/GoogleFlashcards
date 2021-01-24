import {FLASHCARDS_GROUPS} from "../../lib/utils/constant";

const FlashcardGroupModel = {

    setFlashcardsGroups: function (flashcardGroups) {
        localStorage.setItem(FLASHCARDS_GROUPS, JSON.stringify(flashcardGroups));
    },

    getCurrentFlashcardGroup: function () {
        // @ts-ignore
        let FlashcardGroupModel = localStorage.getItem(FLASHCARDS_GROUPS);
        if (FlashcardGroupModel === null || FlashcardGroupModel[0] === 'undefined') {
            return {"id": 0};
//            throw new Error('At least one flashcard group has to exists!')
        }

        return JSON.parse(FlashcardGroupModel)[0];
    },

    getCurrentFlashcardGroupId: function () {
        console.log(this.getCurrentFlashcardGroup());
        return this.getCurrentFlashcardGroup().id;
    },
}

export default FlashcardGroupModel
