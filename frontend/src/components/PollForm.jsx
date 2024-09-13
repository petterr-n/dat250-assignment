import * as React from 'react';
import Input from '@mui/material/Input';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import FormControl, { useFormControl } from '@mui/material/FormControl';
import AccountCircle from '@mui/icons-material/AccountCircle';
import { FormHelperText } from "@mui/material";
import ListItemButton from '@mui/material/ListItemButton';
import { Box, TextField, Button, List, ListItem, IconButton } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import {HelpOutline} from "@mui/icons-material";

function MyFormRequired() {
    const { required } = useFormControl() || {};

    const helperText = React.useMemo(() => {
        if (required) {
            return 'Missing question';
        }
        return '';
    }, [required]);

    return <FormHelperText>{helperText}</FormHelperText>;
}

export default function InputWithIcon() {
    const [options, setOptions] = React.useState(['']);
    const [question, setQuestion] = React.useState('');

    const handleOptionChange = (index, event) => {
        const newOptions = [...options];
        newOptions[index] = event.target.value;
        setOptions(newOptions);
    };

    const handleAddOption = () => {
        setOptions([...options, '']);
    };

    const handleRemoveOption = (index) => {
        const newOptions = options.filter((_, i) => i !== index);
        setOptions(newOptions);
    };

    // Handle form submission
    const handleSubmit = () => {
        // Implement form submit logic here (e.g., send data to the server or process it)
        console.log({ question, options });
    };

    return (
        <form autoComplete="off">
            <FormControl>
                <Box sx={{ '& > :not(style)': { m: 1 } }}>
                    <Box sx={{ display: 'flex', alignItems: 'flex-end' }}>
                        <HelpOutline sx={{ color: 'action.active', mr: 1, my: 0.5 }} />                        <TextField
                            id="input-question"
                            label="Question"
                            variant="standard"
                            multiline
                            fullWidth
                            value={question}
                            onChange={(e) => setQuestion(e.target.value)}
                        />
                        <MyFormRequired />
                    </Box>
                </Box>

                {/* Poll Options List */}
                <Box sx={{ width: '100%', maxWidth: 500, margin: '0 auto' }}>
                    <List>
                        {options.map((option, index) => (
                            <ListItem key={index} sx={{ display: 'flex', alignItems: 'center' }}>
                                <TextField
                                    fullWidth
                                    label={`Option ${index + 1}`}
                                    variant="outlined"
                                    value={option}
                                    onChange={(event) => handleOptionChange(index, event)}
                                />
                                <IconButton edge="end" aria-label="delete" onClick={() => handleRemoveOption(index)}>
                                    <DeleteIcon />
                                </IconButton>
                            </ListItem>
                        ))}
                    </List>

                    <Button
                        variant="contained"
                        onClick={handleAddOption}
                        sx={{ marginTop: 2 }}
                    >
                        Add Option
                    </Button>
                </Box>

                <Box sx={{ marginTop: 3 }}>
                    <Button
                        id="create-poll-id"
                        variant="contained"
                        onClick={handleSubmit}
                        disabled={!question.trim() || options.length === 0 || options.some(option => !option.trim())}  // Disable button if question or options are empty
                    >
                        Create Form
                    </Button>
                </Box>

            </FormControl>
        </form>
    );
}
