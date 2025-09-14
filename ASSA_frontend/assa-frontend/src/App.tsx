import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import SearchPage from './pages/SearchPage';
import ResultPage from './pages/ResultPage';
import DetailPage from './pages/DetailPage';

function App() {
  return (
    <Router>
      <div className="App">
        <nav style={{ padding:'20px', backgroundColor: '#f0f0f0'}}>
            <Link to="/" style={{ marginRight: '20px'}} />
            <Link to="/result" style={{ marginRight: '20px'}} />
            <Link to="/detail/1" style={{ marginRight: '20px'}} />
        </nav>

        <Routes>
          <Route path="/" element={<SearchPage />} />
          <Route path="/result" element={<ResultPage />} />
          <Route path="/detail/:id" element={<DetailPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
