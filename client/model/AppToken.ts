const AppToken = {
    APP_TOKEN_LIVE_TIME: 3600,
    LOCALSTORAGE_KEY_TOKEN: 'userToken',

    appToken: null,
    tokenCreatedAt: null,
    expiredAt: null,

    getAppToken: function () {
        return JSON.parse(localStorage.getItem(AppToken.LOCALSTORAGE_KEY_TOKEN)).appToken;
    },

    setAppToken: function (appToken) {
        let token = AppToken;
        let timeNow = Date.now();
        token.appToken = appToken;
        token.tokenCreatedAt = timeNow;
        token.expiredAt = timeNow + this.APP_TOKEN_LIVE_TIME;

        JSON.stringify(this);
        localStorage.setItem(this.LOCALSTORAGE_KEY_TOKEN, JSON.stringify(token));
    },

    clearAppTokenFromLocalStorage: function () {
        localStorage.setItem(this.LOCALSTORAGE_KEY_TOKEN, null);
        this.clearAllFields();
    },

    clearAllFields: function () {
        this.appToken = null;
        this.tokenCreatedAt = null;
        this.expiredAt = null;
    }
};

export default AppToken;