import axios from "axios";
import FlashcardGroupModel from "../../model/Flashcards/FlashcardGroupModel";
import AppToken from "../../model/AppToken";


const UserAPI = {
    loginWithGoogleToken: async (idToken) => {
        try {
            return await axios.post(
                `${process.env.SERVER_API_URL}/user/google/login`,
                JSON.stringify({
                    idToken: idToken
                }),
                {
                    headers: {
                        "Content-Type": "application/json",
                    },
                }
            );
        } catch (error) {
            console.log('Error while login' + error.response);
            return error.response;
        }
    },

    uploadFile: function (blob, filename) {
        let formData = new FormData();

        formData.append('file', blob, filename);
        let xmlHttpRequest = new XMLHttpRequest();
        xmlHttpRequest.open("POST", `${process.env.SERVER_API_URL}/flashcards/${FlashcardGroupModel.getCurrentFlashcardGroupId()}/import/file`);
        xmlHttpRequest.setRequestHeader('Authorization', 'Bearer ' + AppToken.getAppToken());
        // xmlHttpRequest.setRequestHeader('Content-Type', 'multipart/form-data'); // browser will add automatically
        xmlHttpRequest.send(formData);

        return xmlHttpRequest;
    }
};

export default UserAPI;
