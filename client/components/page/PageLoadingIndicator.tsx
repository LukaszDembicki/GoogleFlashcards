import React from "react";
import {CircularProgress} from "@material-ui/core";
import styled from "@emotion/styled";

const PageLoadingIndicatorPosition = styled("div")`
    root: {
        background: '#ebebeb',
        position: 'fixed',
        transition: '0.2s',
        height: '100%',
        width: '100%',
        zIndex: 1000,
    }
`;

class PageLoadingIndicator extends React.Component {

    constructor(props) {
        super(props);
        this.off = this.off.bind(this);
        this.state = {
            off: props.display
        }
    }

    off() {
        this.setState({
            // @ts-ignore
            off: !this.state.off
        });
    }

    render() {
        return (
            <>
                <PageLoadingIndicatorPosition>
                    <CircularProgress style={{left: 'calc(50% - 25px)', position: 'absolute', top: '50%'}}/>
                </PageLoadingIndicatorPosition>
            </>
        );
    }
}

export default PageLoadingIndicator;
