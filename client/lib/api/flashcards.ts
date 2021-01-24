import axios from "axios";
import AppToken from "../../model/AppToken";

const flashcardsAPI = {
    getFlashcardsGroups: async () => {
        try {
            return await axios.get(
                `${process.env.SERVER_API_URL}/v2/flashcards/groups/all`,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + AppToken.getAppToken()
                    },
                }
            );
        } catch (error) {
            console.log('Error while fetching flashcards ' + error.response);
            return error.response;
        }
    },

    updateFlashcardKnowledgeType: async (flashcard) => {

        if (typeof flashcard !== "string") {
            flashcard = JSON.stringify(flashcard);
        }

        try {
            return await axios.post(
                `${process.env.SERVER_API_URL}/v2/flashcards/update-flashcard-type`,
                flashcard,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + AppToken.getAppToken()
                    },
                }
            );
        } catch (error) {
            console.log('Error while update flashcards ' + error.response);
            return error.response;
        }
    },
};

export default flashcardsAPI;
