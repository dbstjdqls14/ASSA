import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

interface PhoneDetail {
  id: number;
  name: string;
  brand: string;
  price: string;
  image: string;
  specs: {
    display: string;
    processor: string;
    memory: string;
    storage: string;
    camera: string;
    battery: string;
  };
  description: string;
}

const DetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [phone, setPhone] = useState<PhoneDetail | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 임시 데이터 (나중에 실제 API 호출로 변경)
    setTimeout(() => {
      const mockData: PhoneDetail = {
        id: parseInt(id || '1'),
        name: 'iPhone 15 Pro',
        brand: 'Apple',
        price: '1,550,000원',
        image: '📱',
        specs: {
          display: '6.1인치 Super Retina XDR',
          processor: 'A17 Pro 칩',
          memory: '8GB RAM',
          storage: '128GB/256GB/512GB/1TB',
          camera: '48MP 메인 + 12MP 초광각 + 12MP 망원',
          battery: '3274mAh'
        },
        description: '티타늄 디자인과 A17 Pro 칩으로 한층 업그레이드된 프로 모델입니다.'
      };
      setPhone(mockData);
      setLoading(false);
    }, 800);
  }, [id]);

  if (loading) {
    return <div style={{ padding: '50px', textAlign: 'center' }}>로딩 중...</div>;
  }

  if (!phone) {
    return <div style={{ padding: '50px', textAlign: 'center' }}>제품을 찾을 수 없습니다.</div>;
  }

  return (
    <div style={{ padding: '30px', maxWidth: '800px', margin: '0 auto' }}>
      <Link 
        to="/result" 
        style={{ 
          color: '#007bff', 
          textDecoration: 'none',
          fontSize: '16px',
          marginBottom: '20px',
          display: 'inline-block'
        }}
      >
        ← 목록으로 돌아가기
      </Link>

      <div style={{ 
        display: 'grid', 
        gridTemplateColumns: '1fr 1fr', 
        gap: '40px',
        marginTop: '20px'
      }}>
        <div style={{ textAlign: 'center' }}>
          <div style={{ fontSize: '120px', marginBottom: '20px' }}>{phone.image}</div>
          <h1>{phone.name}</h1>
          <p style={{ fontSize: '18px', color: '#666' }}>{phone.brand}</p>
          <p style={{ fontSize: '24px', fontWeight: 'bold', color: '#e74c3c' }}>{phone.price}</p>
          <p style={{ fontSize: '16px', lineHeight: '1.6', marginTop: '20px' }}>
            {phone.description}
          </p>
        </div>

        <div>
          <h2>상세 스펙</h2>
          <div style={{ 
            backgroundColor: '#f8f9fa', 
            padding: '20px', 
            borderRadius: '10px',
            marginTop: '20px'
          }}>
            {Object.entries(phone.specs).map(([key, value]) => (
              <div key={key} style={{ 
                display: 'flex', 
                justifyContent: 'space-between',
                padding: '10px 0',
                borderBottom: '1px solid #dee2e6'
              }}>
                <strong style={{ textTransform: 'capitalize' }}>
                  {key === 'display' ? '디스플레이' :
                   key === 'processor' ? '프로세서' :
                   key === 'memory' ? '메모리' :
                   key === 'storage' ? '저장용량' :
                   key === 'camera' ? '카메라' :
                   key === 'battery' ? '배터리' : key}:
                </strong>
                <span>{value}</span>
              </div>
            ))}
          </div>

          <div style={{ marginTop: '30px' }}>
            <button style={{
              padding: '15px 30px',
              fontSize: '16px',
              backgroundColor: '#28a745',
              color: 'white',
              border: 'none',
              borderRadius: '5px',
              cursor: 'pointer',
              marginRight: '10px'
            }}>
              가격 비교
            </button>
            <button style={{
              padding: '15px 30px',
              fontSize: '16px',
              backgroundColor: '#17a2b8',
              color: 'white',
              border: 'none',
              borderRadius: '5px',
              cursor: 'pointer'
            }}>
              구매하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DetailPage;
