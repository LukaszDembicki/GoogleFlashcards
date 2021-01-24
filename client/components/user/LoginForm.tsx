import React from 'react';
import GoogleLogin from 'react-google-login';
import {Modal} from "@material-ui/core";
import styles from '../../styles/landing-page.module.css'
import UserAPI from "../../lib/api/user";
import {useRouter} from "next/router";
import { mutate } from "swr";
import AppToken from "../../model/AppToken";
import FlashcardGroupModel from "../../model/Flashcards/FlashcardGroupModel";
import flashcardsAPI from "../../lib/api/flashcards";
import GoogleToken from "../../model/GoogleToken";

const LoginForm = ({res}) => {
    const router = useRouter();

    const [open, setOpen] = React.useState(false);
    const [body, setBody] = React.useState('');

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const responseGoogleSuccess = async (response) => {
        try {
            const { data, status } = await UserAPI.loginWithGoogleToken(response.tokenId);

            if (status !== 200) {
                setOpen(true);
                setBody("Login failed. \n " +
                    "If you are using Google Chrome browser, check if you allow third-party cookies in your settings." +
                    "Error message was: " + response.error);

                return;
            }

            AppToken.setAppToken(data);
            GoogleToken.setGoogleToken(response.tokenId)

            await flashcardsAPI.getFlashcardsGroups().then(result => {
                FlashcardGroupModel.setFlashcardsGroups(result.data);
            }).finally(() => {
                console.log('you are logged in, redirect');
                router.push("/user/flashcards-list");
            });
        } catch (error) {
            console.error(error);
        } finally {
            //
        }
    }

    const responseGoogleFail = (response) => {
        //
    }

    return (
        <div>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                <p style={{border: 'none', color: 'white', textAlign: "center", fontSize: '25px'}}>{body}</p>
            </Modal>
            <GoogleLogin
                clientId={`${process.env.GOOGLE_ID}`}
                render={renderProps => (
                    <button id={styles.loginButton} onClick={renderProps.onClick}
                            disabled={renderProps.disabled}>Login with google</button>
                )}
                buttonText="Login"
                onSuccess={responseGoogleSuccess}
                onFailure={responseGoogleFail}
                isSignedIn={true}
                cookiePolicy={'single_host_origin'}
            />
        </div>
    );
}

export default LoginForm;
