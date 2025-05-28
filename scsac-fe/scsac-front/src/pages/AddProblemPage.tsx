import React, { useState } from 'react'
import api from '../api/axios'
import type { RootState } from '../store'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'

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
  { id: 1, name: '어렵다' },
  { id: 2, name: '깔끔하다' },
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
    <div className="max-w-3xl mx-auto p-6 bg-white shadow rounded-lg">
      <h1 className="text-2xl font-bold mb-6">문제 등록</h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input
          type="text"
          name="url"
          value={problem.url}
          onChange={handleProblemChange}
          placeholder="문제 URL"
          className="border p-2 w-full"
        />
        <input
          type="number"
          name="problemNum"
          value={problem.problemNum || ''}
          onChange={handleProblemChange}
          placeholder="문제 번호"
          className="border p-2 w-full"
        />
        <input
          type="text"
          name="title"
          value={problem.title}
          onChange={handleProblemChange}
          placeholder="문제 제목"
          className="border p-2 w-full"
        />
        <input
          type="number"
          name="rate"
          value={problem.rate || ''}
          onChange={handleProblemChange}
          placeholder="평점 (0~5)"
          className="border p-2 w-full"
        />
        <input
          type="text"
          name="comment"
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          placeholder="의견 코멘트"
          className="border p-2 w-full"
        />

        <div>
          <p className="font-medium mb-1">카테고리 선택</p>
          {allCategories.map(cat => (
            <label key={cat.id} className="mr-4">
              <input
                type="checkbox"
                checked={selectedCategories.includes(cat.name)}
                onChange={() => toggleSelection(cat.name, 'category')}
              />
              {' '}{cat.name}
            </label>
          ))}
        </div>

        <div>
          <p className="font-medium mb-1">피드백 카테고리 선택</p>
          {allFeedbackCategories.map(fc => (
            <label key={fc.id} className="mr-4">
              <input
                type="checkbox"
                checked={selectedFeedbackCategories.includes(fc.name)}
                onChange={() => toggleSelection(fc.name, 'feedback')}
              />
              {' '}{fc.name}
            </label>
          ))}
        </div>

        <button type="submit" className="bg-green-700 text-white px-4 py-2 rounded">
          등록하기
        </button>
      </form>
    </div>
  )
}

export default AddProblemPage
