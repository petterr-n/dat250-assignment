import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import Poll from './VotePoll';
import PollForm from './PollForm'


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
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs
                    value={value}
                    onChange={handleChange}
                    aria-label="basic tabs example"
                    sx={{ display: 'flex' }}  // Ensure the tabs take up full width
                    TabIndicatorProps={{ sx: { height: '4px', width: '50%' } }}  // Adjust the indicator width
                >
                    <Tab
                        label="Create a Poll"
                        {...a11yProps(0)}
                        sx={{ flex: 1 }}  // Set equal width for both tabs
                    />
                    <Tab
                        label="View Polls"
                        {...a11yProps(1)}
                        sx={{ flex: 1 }}  // Set equal width for both tabs
                    />
                </Tabs>
            </Box>
            <CustomTabPanel value={value} index={0}>
                <PollForm/>
            </CustomTabPanel>
            <CustomTabPanel value={value} index={1}>
                <Poll/>
                <Poll/>
            </CustomTabPanel>
        </Box>
    );
}
