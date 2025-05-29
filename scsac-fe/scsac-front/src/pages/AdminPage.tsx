import React, { useState } from 'react'
import api from '../api/axios'
import type { RootState } from '../store'
import { useSelector } from 'react-redux'
import './adminPage.css'

const AdminPage: React.FC = () => {
  const [num, setNum] = useState('')
  const [generation, setGeneration] = useState('')
  const [initialPassword, setInitialPassword] = useState('')
  const [updateGen, setUpdateGen] = useState('')
  const [toBeAdminId, setAdminId] = useState('')


  const user = useSelector((state: RootState) => state.user)
  const handleBatchInsert = async () => {
    try {
      await api.post(`/user/?num=${num}&generation=${generation}&password=${encodeURIComponent(initialPassword)}`)
      alert('기수 일괄 등록 완료')
    } catch (err) {
      alert('기수 등록 실패')
    }
  }

  const handleUpdateAdminAuthority = async () => {
    try {
      await api.put(`/user/admin?generation=${parseInt(updateGen)}`)
      alert('기수 권한 수정 완료')
    } catch (err) {
      alert('권한 수정 실패')
    }
  }

  const handleAddAdmin = async () => {
    try {
      await api.put(`/user/addAdmin?id=${toBeAdminId}`)
      alert('관리자 추가 완료')
    } catch (err) {
      alert('관리자 추가 실패')
    }
  }

  return (
    <div className="admin-page-container">
      <h2>관리자 페이지</h2>

      <section className="admin-section">
        <h3>1. 기수 일괄 등록</h3>
        <input
          type="number"
          placeholder="등록할 수"
          value={num}
          onChange={(e) => setNum(e.target.value)}
        />
        <input
          type="number"
          placeholder="기수"
          value={generation}
          onChange={(e) => setGeneration(e.target.value)}
        />
        <input
          type="password"
          placeholder="초기 비밀번호"
          value={initialPassword}
          onChange={(e) => setInitialPassword(e.target.value)}
        />
        <button onClick={handleBatchInsert}>등록</button>
      </section>

      <section className="admin-section">
        <h3>2. 기수 권한 수정</h3>
        <input
          type="number"
          placeholder="권한 부여할 기수"
          value={updateGen}
          onChange={(e) => setUpdateGen(e.target.value)}
        />
        <button onClick={handleUpdateAdminAuthority}>수정</button>
      </section>

      <section className="admin-section">
        <h3>3. 관리자 추가</h3>
        <input
          type="text"
          placeholder="관리자 ID"
          value={toBeAdminId}
          onChange={(e) => setAdminId(e.target.value)}
        />
        <button onClick={handleAddAdmin}>추가</button>
      </section>
    </div>
  )
}

export default AdminPage
