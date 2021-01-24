const GoogleToken = {
    LOCALSTORAGE_KEY_GOOGLE_TOKEN: 'googleToken',

    googleToken: null,

    setGoogleToken: function (googleToken) {
        let googleTokenModel = GoogleToken;
        googleTokenModel.googleToken = googleToken;

        localStorage.setItem(GoogleToken.LOCALSTORAGE_KEY_GOOGLE_TOKEN, JSON.stringify(googleTokenModel));
    },

    clearGoogleTokenFromLocalStorage: function () {
        localStorage.setItem(this.LOCALSTORAGE_KEY_GOOGLE_TOKEN, null);
        this.clearAllFields()
    },

    clearAllFields: function () {
        this.googleToken = null;
    }
};

export default GoogleToken;