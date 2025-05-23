// src/pages/LoginPage.tsx
import { useState } from 'react'
import api from '../api/axios'
import '../components/LoginPage.css'


function LoginPage() {
  const [id, setId] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  const handleLogin = async () => {
    try {
      const csrfToken = getCsrfTokenFromCookie()
      const response = await api.post(
        '/login',
        { id, password },
        {
          headers: {
            'X-XSRF-TOKEN': csrfToken || '', // ✅ 헤더 이름은 이거!
          },
        }
      )
      setError("로그인성공")
      console.log('로그인 성공', response.data)
      window.location.href = '/'
    } catch (err) {
      setError('아이디 또는 비밀번호가 올바르지 않습니다.')
    }
  }

  return (
    <div className="login-container">
      <h2>로그인</h2>
      <input type="text" placeholder="아이디" value={id} 
        onChange={(e) => setId(e.target.value)}/>

      <input type="password" placeholder="비밀번호" value={password} 
        onChange={(e) => setPassword(e.target.value)} 
        onKeyDown={(e) => {if (e.key === 'Enter') handleLogin()}}/> {/* 엔터 키 눌렀을 때도 로그인 실행 */}
      
      <button onClick={handleLogin}>로그인</button>

      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  )
}

export default LoginPage
