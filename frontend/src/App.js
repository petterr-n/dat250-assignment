import logo from './logo.svg';
import './App.css';
import Appbar from './components/Appbar';
import Poll from './components/Poll';
import VoteOptions from "./components/VoteOptions";


function App() {
  return (
    <div className="App">
      <Appbar />
        <Poll />
    </div>
  );
}

export default App;
