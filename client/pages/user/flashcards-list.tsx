import {Box, Container} from "@material-ui/core";
import React, {useEffect, useState} from "react";
import {makeStyles} from '@material-ui/core/styles';
import Page from "../../components/page/Page";
import LineBreak from "../../components/page/LineBreak";
import Navbar from "../../components/layout/Navbar";
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import Check from '@material-ui/icons/Check';
import Close from '@material-ui/icons/Close';
import {ArrowLeft, ArrowRight, Block, FlipToBack} from "@material-ui/icons";
import Button from "@material-ui/core/Button";
import Footer from "../../components/layout/Footer";
import FlashcardsModel from "../../model/Flashcards/FlashcardsModel";
import FlashcardsAbstractType from "../../model/Flashcards/type/FlashcardsAbstractType";
import flashcardsAPI from "../../lib/api/flashcards";
import PageLoadingIndicator from "../../components/page/PageLoadingIndicator";
import {useRouter} from "next/router";
import ImportModal from "../../components/page/ImportModal";

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        height: 350,
        maxWidth: 561,
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
        display: "flex",
        flexDirection: "column",
        justifyContent: "center"
    },
    flashcardText: {
        display: "flex",
        flexDirection: "column",
        justifyContent: "center"
    },
    box: {
        textAlign: 'center',
    }
}));

const FlashcardsList = () => {

    const classes = useStyles();
    const router = useRouter()

    const [reload, setReload] = useState(false);
    const [showIndicator, setShowIndicator] = useState(true);

    const [flashcardsModel, setFlashcardsModel] = useState({
        "flashcards": new FlashcardsModel()
    });

    const [isZeroFlashcardsTotal, setIsZeroFlashcardsTotal] = useState(false);

    useEffect(() => {
        flashcardsAPI.getFlashcardsGroups().then(flashcardList => {

            if (typeof flashcardList === 'undefined' || flashcardList.status === 403) {
                router.push("/user/logout")

                return;
            }

            if (typeof flashcardList.data === 'undefined' || flashcardList.data[0].flashcardList.length === 0) {
                setIsZeroFlashcardsTotal(true);
                return;
            } else {
                setIsZeroFlashcardsTotal(false);
            }

            let initialFlashcardsModel = new FlashcardsModel();
            initialFlashcardsModel.setFlashcardList(flashcardList.data[0].flashcardList);
            setFlashcardsModel({
                "flashcards": initialFlashcardsModel
            });
        }).finally(() => {
            setShowIndicator(false);
        });

    }, [reload]);

    function reloadFlashcardsFromAPI() {
        setReload(!reload);
    }

    function updateFlashcardModel() {
        setFlashcardsModel({
                "flashcards": flashcardsModel.flashcards
            }
        )
    }

    return (
        <>
            {showIndicator ? (<PageLoadingIndicator/>) : (
                // @ts-ignore
                <Page styles={{height: '100vh'}}>
                    {/*<Navbar callBackAfterImport={setReload}/>*/}
                    <Navbar reloadFlashcardsCallback={reloadFlashcardsFromAPI}/>
                    <Container maxWidth="lg">
                        <Container maxWidth="sm">
                            {isZeroFlashcardsTotal ? (
                                <Grid container spacing={3}>
                                    <LineBreak/>
                                    <Grid container xs={12} justify="center">
                                        <Typography variant="h3" color="textPrimary">
                                            Import your flashcards:
                                        </Typography>
                                        <LineBreak/>
                                        <ImportModal reloadFlashcardsCallback={reloadFlashcardsFromAPI} size="large"/>
                                        <LineBreak/>
                                        <Typography variant="body2" color="textPrimary">
                                            Go to
                                            <i><a
                                                href="https://translate.google.com/"> https://translate.google.com/ </a></i>
                                            and click <b>Saved</b> and export your vocabulary.
                                        </Typography>
                                        <Typography variant="body2" color="textPrimary">
                                            Then import downloaded file with button above.
                                        </Typography>
                                    </Grid>
                                </Grid>
                            ) : (
                                <Grid container spacing={3}>
                                    <LineBreak/>

                                    <Grid container xs={12}>
                                        <Typography variant="body2" color="textPrimary">
                                            {flashcardsModel.flashcards.getCurrentFlashcardNumber()} /
                                            {flashcardsModel.flashcards.getTotalFlashcardsInCurrentType()}
                                        </Typography>
                                    </Grid>
                                    <Grid container xs={12}>
                                        <Grid
                                            spacing={3}
                                            item xs={12}
                                        >
                                            <Paper className={classes.paper}>
                                                <Typography style={{padding: '3px'}} variant="h4" color="textPrimary">
                                                    {flashcardsModel.flashcards.getCurrentFlashcardText()}
                                                </Typography>
                                            </Paper>
                                        </Grid>
                                    </Grid>

                                    <Grid container style={{marginTop: 8}} xs={12} spacing={4}>
                                        <Grid item>
                                            <Check onClick={() => {
                                                flashcardsModel.flashcards.moveFlashcardToNewType(FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_KNOW);
                                                updateFlashcardModel();
                                            }} color="primary"/>
                                        </Grid>
                                        <Grid item>
                                            <Close onClick={() => {
                                                flashcardsModel.flashcards.moveFlashcardToNewType(FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_DONT_KNOW);
                                                updateFlashcardModel();
                                            }} color="secondary"/>
                                        </Grid>
                                        <Grid item>
                                            <ArrowLeft onClick={() => {
                                                flashcardsModel.flashcards.prevFlashcard();
                                                updateFlashcardModel();
                                            }} color="primary"/>
                                        </Grid>

                                        <Grid item>
                                            <ArrowRight onClick={() => {
                                                flashcardsModel.flashcards.nextFlashcard();
                                                updateFlashcardModel();
                                            }} color="primary"/>
                                        </Grid>

                                        <Grid item>
                                            <FlipToBack onClick={() => {
                                                flashcardsModel.flashcards.flipFlashcard();
                                                updateFlashcardModel();
                                            }} color="primary"/>
                                        </Grid>
                                    </Grid>

                                    <Grid container xs={12} direction="column">
                                        <hr style={{
                                            border: 'none',
                                            borderTop: '1px solid rgba(223, 223, 223, 0.35)',
                                            marginTop: 30,
                                            marginBottom: 30,

                                            width: '100%'
                                        }}/>
                                        <Button onClick={() => {
                                            flashcardsModel.flashcards.setCurrentFlashcardsType(FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_KNOW);
                                            updateFlashcardModel();
                                        }} color="primary">
                                            Know ({flashcardsModel.flashcards.getTotalFlashcardsInKnowType()})
                                        </Button>
                                        <Button onClick={() => {
                                            flashcardsModel.flashcards.setCurrentFlashcardsType(FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_DONT_KNOW);
                                            updateFlashcardModel();
                                        }} color="secondary">
                                            Don't know ({flashcardsModel.flashcards.getTotalFlashcardsInDontKnowType()})
                                        </Button>
                                        <Button onClick={() => {
                                            flashcardsModel.flashcards.setCurrentFlashcardsType(FlashcardsAbstractType.FLASHCARD_KNOWLEDGE_TYPE_UNASSIGNED);
                                            updateFlashcardModel();
                                        }} color="default">
                                            Unassigned
                                            ({flashcardsModel.flashcards.getTotalFlashcardsInUnassignedType()})
                                        </Button>
                                    </Grid>
                                </Grid>
                            )}

                            <Footer/>
                        </Container>
                    </Container>
                </Page>
            )}
        </>
    )
}

export default FlashcardsList