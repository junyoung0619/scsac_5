import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import type { RootState } from '../store'
import { useNavigate } from 'react-router-dom'
import axios from '../api/axios'
import './MyPage.css'

const fieldNameMap: { [key: string]: string } = {
  id: '학번',
  name: '이름',
  affiliate: '계열사',
  nickname: '닉네임',
  bojId: 'BOJ 아이디',
  // 필요시 다른 필드도 추가
}

const excludedKeys = ['isLoggedIn', 'authority', 'password', 'generation']

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
        navigate('/editProfile', { state: { password: passwordInput } })
      } else {
        setError('비밀번호가 일치하지 않습니다.')
      }
    } catch {
      setError('비밀번호가 일치하지 않습니다.')
    }
  }

  return (
    <div className="mypage-container">
      <h2>마이페이지</h2>
      {user.isLoggedIn ? (
        <div className="mypage-info">
          {Object.entries(user)
            .filter(([key]) => !excludedKeys.includes(key))
            .map(([key, value]) => (
              <p key={key}>
                <strong>{fieldNameMap[key] || key}:</strong> {value}
              </p>
            ))}

          <button className="edit-button" onClick={() => setShowModal(true)}>
            회원정보 수정
          </button>

          {showModal && (
            <div className="modal-overlay">
              <div className="modal-content">
                <h3>비밀번호 확인</h3>
                <input
                  type="password"
                  placeholder="비밀번호를 입력하세요."
                  className="modal-input"
                  value={passwordInput}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <div className="modal-buttons">
                  <button onClick={handlePasswordCheck}>확인</button>
                  <button onClick={() => setShowModal(false)} style={{ marginLeft: '10px' }}>취소</button>
                </div>
                {error && <p className="error-message">{error}</p>}
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
