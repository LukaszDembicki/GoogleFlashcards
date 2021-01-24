import React from 'react'
import styles from "../../styles/authentication.module.css"
import {GoogleLogout} from "react-google-login";
import GoogleToken from "../../model/GoogleToken";
import AppToken from "../../model/AppToken";
import {useRouter} from "next/router";

export default function Logout(props) {

    const router = useRouter();

    const logoutSuccess = (response) => {
        AppToken.clearAppTokenFromLocalStorage();
        GoogleToken.clearGoogleTokenFromLocalStorage();

        router.push(`${process.env.CLIENT_URL}`);
    }

    return (
        <GoogleLogout
            clientId={`${process.env.GOOGLE_ID}`}
            buttonText="Logout"
            // @ts-ignore
            onLogoutSuccess={logoutSuccess}
            render={renderProps => (
                <button id={styles.logoutButton} onClick={renderProps.onClick}
                        disabled={renderProps.disabled}>Logout</button>
            )}
        >
        </GoogleLogout>
    );
}