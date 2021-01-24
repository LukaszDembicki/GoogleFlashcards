import React from "react";
import Container from "@material-ui/core/Container";
import Typography from "@material-ui/core/Typography";
import {withStyles} from '@material-ui/styles';
import Grid from "@material-ui/core/Grid";
import Box from "@material-ui/core/Box";
import Link from "@material-ui/core/Link";

class Footer extends React.Component {

    render() {

        return (
            <div style={{display: 'flex', flexDirection: 'column', minHeight: '10vh', paddingTop: 50}}>
                <Container maxWidth="md" component="footer"
                           style={{borderTop: '1px solid #f0f0f0', paddingTop: 3, paddingBottom: 3, marginTop: 8}}>
                    <Grid container spacing={4} justify="space-evenly">
                        <Box mt={5}>
                            <Typography variant="body2" color="textSecondary">
                                {'Copyright © '}
                                <Link color="inherit" href="https://googleflashcards.us/">
                                    Flashcards
                                </Link>{' '}
                                {new Date().getFullYear()}
                                {'.'}
                            </Typography>
                        </Box>
                    </Grid>
                </Container>
            </div>
        );
    }
}

// export default withStyles(styles)(Footer);
export default Footer;