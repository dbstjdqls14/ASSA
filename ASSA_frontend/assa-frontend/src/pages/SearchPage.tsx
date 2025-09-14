import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const SearchPage: React.FC = () => {
  const [searchTerm, setSearchTerm] = useState('');
  const navigate = useNavigate();

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchTerm.trim()) {
      // 나중에 실제 API 호출로 변경
      navigate(`/result?q=${encodeURIComponent(searchTerm)}`);
    }
  };

  return (
    <div style={{ padding: '50px', textAlign: 'center' }}>
      <h1>ASSA - 핸드폰 검색</h1>
      <form onSubmit={handleSearch} style={{ marginTop: '30px' }}>
        <input
          type="text"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          placeholder="핸드폰 모델명을 입력하세요 (예: iPhone 15)"
          style={{
            padding: '15px',
            fontSize: '16px',
            width: '400px',
            marginRight: '10px',
            border: '2px solid #ddd',
            borderRadius: '5px'
          }}
        />
        <button
          type="submit"
          style={{
            padding: '15px 30px',
            fontSize: '16px',
            backgroundColor: '#007bff',
            color: 'white',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer'
          }}
        >
          검색
        </button>
      </form>
    </div>
  );
};

export default SearchPage;
