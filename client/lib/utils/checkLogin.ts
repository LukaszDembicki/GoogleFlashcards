import AppToken from "../../model/AppToken";

const checkLogin = () =>
    AppToken.getAppToken()?.length !== 0;

export default checkLogin;
