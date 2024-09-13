import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import TextField from "@mui/material/TextField";
import FormControl from "@mui/material/FormControl";

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
};

export default function BasicModal() {
    const [open, setOpen] = React.useState(false);
    const [username, setUsername] = React.useState('');
    const [email, setEmail] = React.useState('');
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);

    // Handle form submission
    const handleSubmit = () => {
        // Implement form submit logic here (e.g., send data to the server or process it)
        console.log({ username, email });
        handleClose();
    };

    return (
        <form>
            <FormControl>
            <Button onClick={handleOpen} color="inherit" >SignUp / Login</Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Typography id="modal-modal-title" variant="h6" component="h2">
                        Create a new user
                    </Typography>
                    <Typography id="create-user-description" sx={{ mt: 2 }}>
                        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1}} >
                            <TextField
                                fullWidth
                                label={`Username`}
                                variant="outlined"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                            />
                        </Box>

                        <Box sx={{ display: 'flex', alignItems: 'center' }}>
                            <TextField
                                fullWidth
                                label={`Email`}
                                variant="outlined"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </Box>
                    </Typography>
                    <Box sx={{ marginTop: 3 }}>
                        <Button
                            onClick={handleSubmit}
                            variant="contained"
                            sx={{ mt: 2 }}
                        >
                            Create User
                        </Button>
                    </Box>
                </Box>
            </Modal>
            </FormControl>
        </form>
    );
}
