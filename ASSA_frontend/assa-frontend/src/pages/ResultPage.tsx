import React, { useEffect, useState } from 'react';
import { useSearchParams, Link } from 'react-router-dom';

interface Phone {
  id: number;
  name: string;
  brand: string;
  price: string;
  image: string;
}

const ResultPage: React.FC = () => {
  const [searchParams] = useSearchParams();
  const [phones, setPhones] = useState<Phone[]>([]);
  const [loading, setLoading] = useState(true);
  const query = searchParams.get('q') || '';

  useEffect(() => {
    // 임시 데이터 (나중에 실제 API 호출로 변경)
    setTimeout(() => {
      const mockData: Phone[] = [
        { id: 1, name: 'iPhone 15 Pro', brand: 'Apple', price: '1,550,000원', image: '📱' },
        { id: 2, name: 'Galaxy S24 Ultra', brand: 'Samsung', price: '1,450,000원', image: '📱' },
        { id: 3, name: 'Pixel 8 Pro', brand: 'Google', price: '1,200,000원', image: '📱' }
      ];
      setPhones(mockData);
      setLoading(false);
    }, 1000);
  }, [query]);

  if (loading) {
    return <div style={{ padding: '50px', textAlign: 'center' }}>🔄 검색 중...</div>;
  }

  return (
    <div style={{ padding: '30px' }}>
      <h2>"{query}" 검색 결과</h2>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '20px', marginTop: '30px' }}>
        {phones.map(phone => (
          <div key={phone.id} style={{
            border: '1px solid #ddd',
            borderRadius: '10px',
            padding: '20px',
            textAlign: 'center',
            backgroundColor: '#f9f9f9'
          }}>
            <div style={{ fontSize: '60px', marginBottom: '10px' }}>{phone.image}</div>
            <h3>{phone.name}</h3>
            <p><strong>브랜드:</strong> {phone.brand}</p>
            <p><strong>가격:</strong> {phone.price}</p>
            <Link
              to={`/detail/${phone.id}`}
              style={{
                display: 'inline-block',
                padding: '10px 20px',
                backgroundColor: '#28a745',
                color: 'white',
                textDecoration: 'none',
                borderRadius: '5px',
                marginTop: '10px'
              }}
            >
              자세히 보기
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ResultPage;
