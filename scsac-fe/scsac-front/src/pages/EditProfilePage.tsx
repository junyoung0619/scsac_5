import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import type { RootState } from '../store'
import axios from '../api/axios'

const EditProfile: React.FC = () => {
  const user = useSelector((state: RootState) => state.user)
  const navigate = useNavigate()

  const [form, setForm] = useState<{
    name: string
    affiliate: string
    generation: number
    nickname: string
    boj_id: string
    password: string
  }>({
    name: user.name || '',
    affiliate: user.affiliate || '',
    generation: user.generation || 0,
    nickname: user.nickname || '',
    boj_id: user.boj_id || '',
    password: '', // 초기값 빈 문자열
  })

  const [error, setError] = useState('')

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  const handleSubmit = async () => {
    // 모든 값이 채워져 있는지 검사
    const hasEmpty = Object.values(form).some(val => typeof val === 'string' && val.trim() === '')
    if (hasEmpty || form.generation === 0) {
      setError('모든 항목을 입력해 주세요.')
      return
    }

    try {
      await axios.put(`/user/${user.id}`, form)
      alert('회원정보가 수정되었습니다.')
      navigate('/mypage')
    } catch (err) {
      setError('수정 중 오류가 발생했습니다.')
    }
  }

  return (
    <div>
      <h2>회원정보 수정</h2>
      <div>
        <label>비밀번호</label><br />
        <input type="password" name="password" value={form.password} onChange={handleChange} /><br />

        <label>이름</label><br />
        <input type="text" name="name" value={form.name} onChange={handleChange} /><br />

        <label>계열사</label><br />
        <input type="text" name="affiliate" value={form.affiliate} onChange={handleChange} /><br />

        <label>기수</label><br />
        <input type="number" name="generation" value={form.generation} onChange={handleChange} /><br />

        <label>닉네임</label><br />
        <input type="text" name="nickname" value={form.nickname} onChange={handleChange} /><br />

        <label>BOJ 아이디</label><br />
        <input type="text" name="boj_id" value={form.boj_id} onChange={handleChange} /><br />

        {error && <p style={{ color: 'red' }}>{error}</p>}

        <button onClick={handleSubmit}>수정 완료</button>
      </div>
    </div>
  )
}

export default EditProfile
