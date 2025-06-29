import React, { useState } from 'react'
import { useSelector, useDispatch} from 'react-redux'
import { useNavigate, useLocation } from 'react-router-dom'
import type { RootState } from '../store'
import api from '../api/axios'
import { logout } from '../store/userSlice'
import './EditProfilePage.css'


const EditProfile: React.FC = () => {
  const user = useSelector((state: RootState) => state.user)
  const navigate = useNavigate()
  const dispatch = useDispatch()
  const location = useLocation()
  const passwordFromPrev = location.state?.password || ''


  const [form, setForm] = useState({
    id: user.id || '',
    name: user.name || '',
    authority: user.authority || '',
    affiliate: user.affiliate || '',
    generation: user.generation || 0,
    nickname: user.nickname || '',
    bojId: user.boj_id || '',
    password: passwordFromPrev,
  })

  const [error, setError] = useState('')

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  const handleSubmit = async () => {
    const hasEmpty = Object.entries(form).some(([key, val]) => {
      if (key === 'id') return false // id는 제외하고 검증
      return typeof val === 'string' && val.trim() === ''
    })

    if (hasEmpty || form.generation === 0) {
      setError('모든 항목을 입력해 주세요.')
      return
    }

    try {
      await api.put(`/user/user`, form)
      alert('회원정보가 수정되었습니다.')
      alert('다시 로그인 해주세요.')
      dispatch(logout())
      navigate('/')
    } catch (err) {
      setError('수정 중 오류가 발생했습니다.')
    }
  }

  return (
    <div className="edit-profile-container">
      <h2>회원정보 수정</h2>
      <div className="edit-profile-form">
  <div className="form-item">
    <label>비밀번호</label>
    <input type="password" name="password" value={form.password} onChange={handleChange} />
  </div>

  <div className="form-item">
    <label>이름</label>
    <input type="text" name="name" value={form.name} onChange={handleChange} />
  </div>

  <div className="form-item">
    <label>계열사</label>
    <input type="text" name="affiliate" value={form.affiliate} onChange={handleChange} />
  </div>

  <div className="form-item">
    <label>닉네임</label>
    <input type="text" name="nickname" value={form.nickname} onChange={handleChange} />
  </div>

  <div className="form-item">
    <label>BOJ 아이디</label>
    <input type="text" name="bojId" value={form.bojId} onChange={handleChange} />
  </div>

  {error && <p className="error-message">{error}</p>}

  <button className="edit-profile-button" onClick={handleSubmit}>수정 완료</button>
</div>
    </div>
  )
}

export default EditProfile
