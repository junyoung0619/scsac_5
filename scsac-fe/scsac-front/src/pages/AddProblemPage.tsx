import React, { useState } from 'react'
import api from '../api/axios'
import type { RootState } from '../store'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import './AddProblemPage.css'

type Problem = {
  url: string
  problemNum: number
  title: string
  rate: number
  categories?: string[] // 사용 안함
  opinions: {
    userId: string
    comment: string
    category: string[]
    feedbackCategory: string[]
  }[]
}

const allCategories = [
  { id: 1, name: '구현' },
  { id: 2, name: '시뮬레이션' },
  { id: 3, name: 'BFS' },
  { id: 4, name: 'DFS' },
  { id: 5, name: '백트래킹' },
  { id: 6, name: '기타' }
]

const allFeedbackCategories = [
  { id: 1, name: '구현 많음' },
  { id: 2, name: '실수하기 쉬운' },
  { id: 3, name: '기발한' },
  { id: 4, name: '교육적인' },
]

const AddProblemPage: React.FC = () => {
  const [problem, setProblem] = useState<Omit<Problem, 'opinions'>>({
    url: '',
    problemNum: 0,
    title: '',
    rate: 0,
  })

  const [comment, setComment] = useState('')
  const [selectedCategories, setSelectedCategories] = useState<string[]>([])
  const [selectedFeedbackCategories, setSelectedFeedbackCategories] = useState<string[]>([])
  const navigate = useNavigate()
  const handleProblemChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setProblem(prev => ({
      ...prev,
      [name]: name === 'problemNum' || name === 'rate' ? Number(value) : value
    }))
  }

  const toggleSelection = (name: string, type: 'category' | 'feedback') => {
    if (type === 'category') {
      setSelectedCategories(prev =>
        prev.includes(name) ? prev.filter(c => c !== name) : [...prev, name]
      )
    } else {
      setSelectedFeedbackCategories(prev =>
        prev.includes(name) ? prev.filter(c => c !== name) : [...prev, name]
      )
    }
  }
  const user = useSelector((state: RootState) => state.user)
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()

    const payload: Problem = {
      ...problem,
      opinions: [{
        userId: user.id,
        comment: comment,
        category: selectedCategories,
        feedbackCategory: selectedFeedbackCategories
      }]
    }

    try {
      console.log('등록할 데이터:', payload)
      await api.post('/problem/', payload)
      alert('문제가 성공적으로 등록되었습니다!')
      setProblem({ url: '', problemNum: 0, title: '', rate: 0 })
      setComment('')
      setSelectedCategories([])
      setSelectedFeedbackCategories([])
      navigate("/problems")
    } catch (err) {
      console.error('등록 실패:', err)
      alert('등록 중 오류가 발생했습니다.')
    }
  }

  return (
    <div className="add-problem-container">
      <h1 className="page-title">문제 등록</h1>
      <form onSubmit={handleSubmit} className="form-container">
        <input
          type="text"
          name="url"
          value={problem.url}
          onChange={handleProblemChange}
          placeholder="문제 URL"
          className="input-box"
        />
        <input
          type="number"
          name="problemNum"
          value={problem.problemNum || ''}
          onChange={handleProblemChange}
          placeholder="문제 번호"
          className="input-box"
          min={0}
        />
        <input
          type="text"
          name="title"
          value={problem.title}
          onChange={handleProblemChange}
          placeholder="문제 제목"
          className="input-box"
        />
        <input
          type="number"
          name="rate"
          value={problem.rate || ''}
          onChange={handleProblemChange}
          placeholder="평점 (0~5)"
          className="input-box"
          min={0}
          max={5}
        />
        <input
          type="text"
          name="comment"
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          placeholder="의견 코멘트"
          className="input-box-comment"
        />

        <section className="checkbox-section">
          <p className="label-title">카테고리 선택</p>
          <div className="checkbox-group">
            {allCategories.map(cat => (
              <label key={cat.id} className="checkbox-label">
                <input
                  type="checkbox"
                  checked={selectedCategories.includes(cat.name)}
                  onChange={() => toggleSelection(cat.name, 'category')}
                />
                {cat.name}
              </label>
            ))}
          </div>
        </section>

        <section className="checkbox-section">
          <p className="label-title">피드백 카테고리 선택</p>
          <div className="checkbox-group">
            {allFeedbackCategories.map(fc => (
              <label key={fc.id} className="checkbox-label">
                <input
                  type="checkbox"
                  checked={selectedFeedbackCategories.includes(fc.name)}
                  onChange={() => toggleSelection(fc.name, 'feedback')}
                />
                {fc.name}
              </label>
            ))}
          </div>
        </section>

        <button type="submit" className="submit-button">등록하기</button>
      </form>
    </div>
  )
}

export default AddProblemPage
