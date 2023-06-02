import './App.css';
import {Routes, Route, BrowserRouter} from 'react-router-dom';
import Main from './component/Main';
import Buy from './screen/Buy';
import List from './screen/List';
import Pur from './Pur';
import NewList from './screen/NewList';

function App() {
  return (
     <BrowserRouter>
        <Routes>
           <Route path="/" element={<Main/>}></Route>
           <Route path="/List" element={<List/>}></Route>
           <Route path="/Buy" element={<Buy/>}></Route>
           <Route path="/Pur/*" element={<Pur/>}></Route>
           <Route path="/NewList" element={<NewList/>}></Route>
        </Routes>
     </BrowserRouter>
  );
}

export default App;