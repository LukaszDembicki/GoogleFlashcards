import React from "react";
import Toolbar from "@material-ui/core/Toolbar";
import Typography from "@material-ui/core/Typography";
import AppBar from "@material-ui/core/AppBar";
import {makeStyles, withStyles} from '@material-ui/styles';
import Logout from "../user/Logout";
import ImportModal from "../page/ImportModal";
import {PAGE_FLASHCARD_LIST} from "../../lib/utils/constant";

const navbarStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    title: {
        flexGrow: 1,
    }
}));

const Navbar = (props) => {

    const classes = navbarStyles();

    return (
        <div className={classes.root}>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h5" className={classes.title}>
                        <b><a href={PAGE_FLASHCARD_LIST}>F</a></b>
                    </Typography>
                    <ImportModal reloadFlashcardsCallback={props.reloadFlashcardsCallback}/>
                    <Logout/>
                </Toolbar>
            </AppBar>
        </div>
    );
};

// export default withStyles(navbarStyles)(Navbar);
export default Navbar;