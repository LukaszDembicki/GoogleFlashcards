import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import {CloudUpload} from "@material-ui/icons";
import UserApi from "../../lib/api/user"
import Input from "@material-ui/core/Input";
import {CircularProgress} from "@material-ui/core";

function getModalStyle() {
    const top = 50;
    const left = 50;

    return {
        top: `${top}%`,
        left: `${left}%`,
        transform: `translate(-${top}%, -${left}%)`,
    };
}

const iconSmall = makeStyles((theme) => ({
    upload: {
        marginRight: 25,
        marginTop: 4
    }
}));

const iconLarge = makeStyles((theme) => ({
    upload: {
        marginRight: 0,
        marginTop: 0,
        width: 50,
        height: 50
    }
}));

const paperStyles = makeStyles((theme) => ({
    paper: {
        position: 'absolute',
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
        minWidth: 250,
        width: '50%',
    }
}));

export default function ImportModal(props) {
    const paperStyle = paperStyles();
    let iconStyle = iconSmall();
    switch (props.size) {
        case 'large':
            iconStyle = iconLarge();
            break;
    }

    const [modalStyle] = React.useState(getModalStyle);
    const [open, setOpen] = React.useState(false);
    const [importStatusMessage, setImportStatusMessage] = React.useState("");
    const [isUploadInProgress, setIsUploadInProgress] = React.useState(false);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };


    const uploadFile = (event) => {
        setIsUploadInProgress(true);

        let file = event.target.files[0];
        let reader = new FileReader();

        reader.readAsText(file);

        reader.addEventListener('error', () => {
            setIsUploadInProgress(false);
            setImportStatusMessage('File not uploaded! Try again');
        });

        reader.onloadend = function () {
            // render.result - gives just text
            let xmlHttpRequest = UserApi.uploadFile(file, file.name);

            xmlHttpRequest.onload = function () {
                setIsUploadInProgress(false);
                props.reloadFlashcardsCallback();
                closeWindowAfterUploadSuccess();
            }

            xmlHttpRequest.onerror = function () {
                setIsUploadInProgress(false);
            }
        };
    }

    const closeWindowAfterUploadSuccess = () => {
        setImportStatusMessage('File has been uploaded. You can now use app!');
        setTimeout(function () {
            handleClose();
            setImportStatusMessage("");
        }, 1200);
    }

    const body = (
        <div style={modalStyle} className={paperStyle.paper}>
            <h2 id="simple-modal-title">Import your google translations.</h2>
            <div id="simple-modal-description">
                <Input type="file" onChange={uploadFile}/>
            </div>
            {importStatusMessage}
            <br/>
            {isUploadInProgress ? <CircularProgress/> : null}
        </div>
    );

    return (
        <div>
            <CloudUpload className={iconStyle.upload} onClick={handleOpen}/>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
            >
                <p>{body}</p>
            </Modal>
        </div>
    );
}