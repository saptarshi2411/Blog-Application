import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from './Register';
import Login from './Login';
import AddBlog from './AddBlog'; 
import OwnBlogs from './OwnBlogs'; 

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Register />} /> 
          <Route path="/login" element={<Login />} /> 
          <Route path="/addBlog" element={<AddBlog />} /> 
          <Route path="/ownBlog" element={<OwnBlogs />} /> 
        </Routes>
      </div>
    </Router>
  );
}

export default App;
