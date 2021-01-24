import AppToken from "../../model/AppToken";
import GoogleToken from "../../model/GoogleToken";
import {useGoogleLogout} from "react-google-login";
import {useRouter} from "next/router";

export default function logout() {
    const router = useRouter()

    const onLogoutSuccess = () => {
        AppToken.clearAppTokenFromLocalStorage();
        GoogleToken.clearGoogleTokenFromLocalStorage();

        router.push(`${process.env.CLIENT_URL}`)
    };

    const onFailure = () => {
        router.push(`${process.env.CLIENT_URL}`)
    };

    const { signOut } = useGoogleLogout({
        clientId: `${process.env.GOOGLE_ID}`,
        onLogoutSuccess: onLogoutSuccess,
        onFailure: onFailure,
    });

    if (typeof window !== 'undefined'){
        signOut();
    }
};