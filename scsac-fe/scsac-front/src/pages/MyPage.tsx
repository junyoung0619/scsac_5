import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import type { RootState } from '../store'
import { useNavigate } from 'react-router-dom'
import axios from '../api/axios'

// ... 생략된 import 부분 동일

const MyPage: React.FC = () => {
  const user = useSelector((state: RootState) => state.user)
  const navigate = useNavigate()

  const [showModal, setShowModal] = useState(false)
  const [passwordInput, setPassword] = useState('')
  const [error, setError] = useState('')

  const handlePasswordCheck = async () => {
    try {
      const res = await axios.post('/check', {
        id: user.id,
        password: passwordInput,
      })
      if (res.status === 200) {
        setShowModal(false)
        navigate('/editProfile', { state: { password: passwordInput } }) // ✅ 비밀번호 전달
      } else {
        setError('비밀번호가 일치하지 않습니다.')
      }
    } catch {
      setError('비밀번호가 일치하지 않습니다.')
    }
  }

  return (
    <div style={{ padding: '20px' }}>
      <h2>마이페이지</h2>
      {user.isLoggedIn ? (
        <div>
          <p><strong>이름:</strong> {user.name}</p>
          <p><strong>학번:</strong> {user.id}</p>
          <p><strong>계열사:</strong> {user.affiliate}</p>

          <button onClick={() => setShowModal(true)}>
            회원정보 수정
          </button>

          {showModal && (
            <div style={{
              position: 'fixed', top: 0, left: 0, right: 0, bottom: 0,
              backgroundColor: 'rgba(0,0,0,0.5)', display: 'flex',
              justifyContent: 'center', alignItems: 'center'
            }}>
              <div style={{
                backgroundColor: 'white', padding: '20px', borderRadius: '8px',
                width: '300px', textAlign: 'center'
              }}>
                <h3>비밀번호 확인</h3>
                <input
                  type="password"
                  placeholder="비밀번호를 입력하세요."
                  value={passwordInput}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <div style={{ marginTop: '10px' }}>
                  <button onClick={handlePasswordCheck}>확인</button>
                  <button onClick={() => setShowModal(false)} style={{ marginLeft: '10px' }}>취소</button>
                </div>
                {error && <p style={{ color: 'red' }}>{error}</p>}
              </div>
            </div>
          )}
        </div>
      ) : (
        <p>로그인이 필요합니다.</p>
      )}
    </div>
  )
}

export default MyPage
