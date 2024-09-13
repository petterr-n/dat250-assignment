import logo from './logo.svg';
import './App.css';
import Appbar from './components/Appbar';
import Poll from './components/VotePoll';
import VoteOptions from "./components/VoteOptions";

import Polls from './components/Polls';

function App() {
  return (
    <div className="App">
      <Appbar />
        <Polls />
    </div>
  );
}

export default App;
