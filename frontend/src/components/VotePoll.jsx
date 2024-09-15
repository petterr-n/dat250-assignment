import * as React from 'react';
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { FormControlLabel, Radio, RadioGroup } from "@mui/material";

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

const buttonStyle = {
    marginTop: '10px',
};

export default function BasicModal({ question, options: initialOptions = [], pollId }) {  // Default options to an empty array
    const [open, setOpen] = React.useState(false);
    const [selectedOption, setSelectedOption] = React.useState('');  // State for the selected option
    const [options, setOptions] = React.useState(initialOptions);  // State for options with votes

    // Ensure pollId is valid
    React.useEffect(() => {
        if (!pollId) {
            console.error('pollId is undefined or invalid');
        }
    }, [pollId]);

    const handleOpen = () => {
        if (!pollId) {
            console.error('pollId is undefined or invalid');
            return;
        }

        // Fetch the latest poll options and votes when opening the modal
        fetch(`http://localhost:8080/polls/${pollId}`)
            .then(response => response.json())
            .then(data => {
                if (data && data.options) {
                    setOptions(data.options);
                }
                setOpen(true);
            })
            .catch(error => {
                console.error('Error fetching updated poll data:', error);
            });
    };

    const handleClose = () => setOpen(false);

    const handleSubmit = () => {
        if (!selectedOption) {
            console.error('No option selected');
            return;
        }

        // Construct vote data with the correct structure
        const voteData = {
            option: { // Ensure this matches your backend Vote structure
                optionId: Number(selectedOption) // Convert to number if necessary
            }
        };

        // Send vote data
        fetch(`http://localhost:8080/polls/${pollId}/votes`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(voteData),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok.');
                }
                return response.json();
            })
            .then(data => {
                console.log('Vote submitted successfully:', data);
                handleClose();  // Close the modal after voting
            })
            .catch(error => {
                console.error('Error submitting vote:', error);
            });
    };



    return (
        <div>
            <Button sx={buttonStyle} variant="contained" onClick={handleOpen}>
                {question}
            </Button>
            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
                    <Typography id="modal-modal-title" variant="h6" component="h2">
                        {question}
                    </Typography>
                    <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                        {options.length > 0 ? (  // Check if options array has elements
                            <RadioGroup
                                aria-labelledby="poll-options-group"
                                value={selectedOption}
                                onChange={(e) => setSelectedOption(e.target.value)}  // Update selected option
                                name="radio-buttons-group"
                            >
                                {options.map((option) => (
                                    <FormControlLabel
                                        key={option.optionId}  // Assuming optionId is unique
                                        value={option.optionId}  // Send optionId when voting
                                        control={<Radio />}
                                        label={`${option.caption} (${option.votes} votes)`}  // Display caption and votes
                                    />
                                ))}
                            </RadioGroup>
                        ) : (
                            <Typography>No options available.</Typography>
                        )}
                    </Typography>
                    <Box sx={{ marginTop: 3 }}>
                        <Button
                            id="id-vote-button"
                            variant="contained"
                            onClick={handleSubmit}
                            disabled={!selectedOption}  // Disable button if no option is selected
                        >
                            Vote
                        </Button>
                    </Box>
                </Box>
            </Modal>
        </div>
    );
}
