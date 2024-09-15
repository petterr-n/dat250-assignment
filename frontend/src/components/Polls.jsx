import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import Poll from './VotePoll';
import PollForm from './PollForm';
import Typography from "@mui/material/Typography";
import { useEffect, useState } from "react";

function CustomTabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
        </div>
    );
}

CustomTabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

export default function BasicTabs() {
    const [value, setValue] = useState(0);

    // State to store polls fetched from the backend and created via the form
    const [polls, setPolls] = useState([]);

    // Function to fetch polls from the backend
    const fetchPolls = () => {
        fetch('http://localhost:8080/polls')  // Adjust the URL based on your backend
            .then(response => response.json())
            .then(data => setPolls(data))
            .catch(error => console.error('Error fetching polls:', error));
    };

    // Callback to handle adding a new poll
    const handleAddPoll = (newPoll) => {
        setPolls((prevPolls) => [...prevPolls, newPoll]);
    };

    // Handle tab change
    const handleChange = (event, newValue) => {
        setValue(newValue);

        // Fetch polls when switching to the "View Polls" tab (index 1)
        if (newValue === 1) {
            fetchPolls();
        }
    };

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs
                    value={value}
                    onChange={handleChange}
                    aria-label="poll tabs"
                    sx={{ display: 'flex' }}
                    TabIndicatorProps={{ sx: { height: '4px', width: '50%' } }}  // Adjust the indicator width
                >
                    <Tab label="Create a Poll" {...a11yProps(0)} sx={{ flex: 1 }} />
                    <Tab label="View Polls" {...a11yProps(1)} sx={{ flex: 1 }} />
                </Tabs>
            </Box>

            {/* Create Poll Tab */}
            <CustomTabPanel value={value} index={0}>
                <PollForm onAddPoll={handleAddPoll} />
            </CustomTabPanel>

            {/* View Polls Tab */}
            <CustomTabPanel value={value} index={1}>
                {polls.length > 0 ? (
                    polls.map((poll, index) => (
                        <Poll key={index} question={poll.question} options={poll.options} pollId={poll.pollId}/>
                    ))
                ) : (
                    <Typography>No polls available.</Typography>
                )}
            </CustomTabPanel>
        </Box>
    );
}
