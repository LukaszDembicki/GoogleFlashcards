import React, {useEffect, useState} from 'react';
import styles from '../styles/landing-page.module.css'
import Link from "@material-ui/core/Link";
import {Cloud} from "@material-ui/icons";
import {makeStyles} from "@material-ui/core/styles";
import PageLoadingIndicator from "../components/page/PageLoadingIndicator";
import {Typography} from "@material-ui/core";
import LoginForm from "../components/user/LoginForm";
import {useGoogleLogout} from "react-google-login";

function Copyright() {
    return (
        <Typography variant="body2">
            {'Copyright © '}
            <Link color="inherit" href="">
                Flashcards
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    }
}));


export default function Home() {

    const classes = useStyles();

    const [loadingPageIndicator, setLoadingPageIndicator] = React.useState(true);

    const hideLoadingPageIndicator = () => {
        setLoadingPageIndicator(false);
    };

    useEffect(() => {
        hideLoadingPageIndicator()
    }, []);
    
    return (
        <div>
            {loadingPageIndicator ? (<PageLoadingIndicator off={false}/>) : (
                <div>
                    <section id={styles.header}>
                        <div id={styles.inner}>
                            <Cloud style={{fontSize: '5.5em'}}/>
                            <Typography style={{marginBottom: '15px'}} variant="h3">
                                Upload your phasebook <br/>
                                from google translator
                            </Typography>
                            <Typography variant="p">
                                This flashcards application will help you
                                remember vocabulary
                            </Typography>
                            <LoginForm/>
                        </div>
                    </section>

                    <section id={styles.sectionOnMobile}>
                        <div className={styles.sectionLeftRight}>
                            <div id={styles.sectionLeft}>
                                <Typography variant="h2">
                                    Learn anywhere you want
                                </Typography>
                                <Typography variant="body1">
                                    Just import your list of phrases and start learning today!
                                </Typography>
                            </div>
                        </div>
                        <div className={styles.sectionLeftRight}>
                            <div id={styles.sectionRight}>
                                <img id={styles.imageOnMobile} src="/image/landing-page/on-mobile.png"/>
                            </div>
                        </div>
                    </section>

                    <section id={styles.footer}>
                        <Copyright/>
                    </section>
                </div>
            )}
        </div>
    );
}


