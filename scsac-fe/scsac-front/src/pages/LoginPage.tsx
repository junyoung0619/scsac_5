import { useState } from 'react'
import { useDispatch } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import { login } from '../store/userSlice'

import api from '../api/axios'
import '../components/LoginPage.css'


function LoginPage() {
  const [id, setId] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const dispatch = useDispatch()
  const navigate = useNavigate()
  65;
  const handleLogin = async () => {
    try {
      const response = await api.post('/login',{ id, password })

      const token = response.data
      localStorage.setItem("jwt", token)
      
      console.log('로그인 성공', response.data) // user 정보 출력
      alert("로그인 성공")

      // redux에 사용자 정보를 저장하기 위해 다시 한 번 axios 요청

      const user_infoRes = await api.get(`/user/${id}`)

      const userInfo = user_infoRes.data
      dispatch(login({
        id: userInfo.id,
        password: userInfo.password,
        authority: userInfo.authority,
        generation: userInfo.generation,
        affiliate: userInfo.affiliate,
        name: userInfo.name,
        nickname: userInfo.nickname,
        boj_id: userInfo.bojId,
      }))
      
      if (!userInfo.name || !userInfo.nickname) {
        navigate('/editProfile', { state: { password: password} })
      } else {
        navigate('/problems')
      }
  
    } 
    
    catch (err) {
      setError('아이디 또는 비밀번호가 올바르지 않습니다.')
    }
  }


  return (

    <div className="login-container">
    <h1 style={{ fontSize: '64px', textAlign: 'center', marginBottom: '20px' }}>🌅</h1>
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
