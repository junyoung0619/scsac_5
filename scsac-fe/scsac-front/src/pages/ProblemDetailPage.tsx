import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import api from '../api/axios'
import './ProblemDetailPage.css'
import type { RootState } from '../store'
import { useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom'
import './ProblemDetailPage.css'

type Opinion = {
  id: number
  rate: number
  feedbackCategory: string[]
  comment: string
  category: string[]
  userId: string
}

type Problem = {
  id: number
  url: string
  problemNum: string
  title: string
  rate: number
  opinions: Opinion[]
}

const categoryOptions = ['구현', '시뮬레이션', 'BFS', 'DFS', '백트래킹', '기타']
const feedbackCategoryOptions = ['구현 많음', '실수하기 쉬운', '기발한', '교육적인']

const ProblemDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>()
  const [problem, setProblem] = useState<Problem | null>(null)
  const userId = useSelector((state: RootState) => state.user.id)
  const [showForm, setShowForm] = useState(false)
  const navigate = useNavigate()

  // 작성 및 수정용 state
  const [rate, setRate] = useState<number>(0)
  const [comment, setComment] = useState<string>('')
  const [category, setCategory] = useState<string[]>([])
  const [feedbackCategory, setFeedbackCategory] = useState<string[]>([])
  const [editingOpinionId, setEditingOpinionId] = useState<number | null>(null)

  const fetchProblem = () => {
    if (id) {
      api.get(`/problem/${id}`)
        .then(res => setProblem(res.data))
        .catch(err => console.error('문제 상세 불러오기 실패:', err))
    }
  }

  useEffect(() => {
    fetchProblem()
  }, [id])

  const resetForm = () => {
    setRate(0)
    setComment('')
    setCategory([])
    setFeedbackCategory([])
    setEditingOpinionId(null)
    setShowForm(false)
  }

  const handleSubmit = () => {
    if (!id) return

    if (rate < 1 || rate > 5) {
      alert('평점은 1에서 5 사이여야 합니다.')
      return
    }

    if (!comment.trim()) {
      alert('코멘트를 입력해주세요.')
      return
    }

    if (editingOpinionId) {
      // 수정 요청: 필요한 필드만 보냄
      api.put('/opinion/', {
        id: editingOpinionId,
        problemId: Number(id),  // 추가
        userId,                 // 추가
        rate,
        comment,
        category,
        feedbackCategory,
      })
        .then(() => {
          resetForm()
          fetchProblem()
        })
        .catch(err => {
          console.error('의견 수정 실패:', err)
          alert('의견 수정에 실패했습니다.')
        })
    } else {
      // 신규 등록 요청: problemId, userId 등 모두 포함
      api.post('/opinion/', {
        problemId: Number(id),
        userId,
        rate,
        comment,
        category,
        feedbackCategory,
      })
        .then(() => {
          resetForm()
          fetchProblem()
        })
        .catch(err => {
          console.error('의견 등록 실패:', err)
          alert('의견 등록에 실패했습니다.')
        })
    }
  }

  const handleEdit = (opinion: Opinion) => {
    setRate(opinion.rate)
    setComment(opinion.comment)
    setCategory(opinion.category)
    setFeedbackCategory(opinion.feedbackCategory)
    setEditingOpinionId(opinion.id)
    setShowForm(true)
  }

  const handleDelete = (opinionId: number) => {
    if (window.confirm('정말로 삭제하시겠습니까?')) {
      api.delete(`/opinion/${opinionId}`)
        .then((res) => {
          const result = res.data  // 응답 값이 0 또는 1이라고 가정
          if (result === 0) {
            navigate('/problems')
          } else {
            fetchProblem()
          }
        })
        .catch(err => {
          console.error('삭제 실패:', err)
          alert('삭제 중 오류가 발생했습니다.')
        })
    }
  }

  const hasUserOpinion = problem?.opinions.some(op => op.userId === userId)

  if (!problem) return <div className="text-center mt-10 text-gray-600">로딩 중...</div>

  return (
    <div className="problem-detail-container">
      <h2 className="text-3xl font-bold text-blue-800 mb-4">{problem.title}</h2>
      <div className="space-y-2 mb-6 text-gray-800">
        <p><strong>문제 번호:</strong> {problem.problemNum}</p>
        <p><strong>평점:</strong> <span className="text-yellow-600">{problem.rate}</span></p>
        <p>
          <strong>문제 링크:</strong>{' '}
          <a href={problem.url} target="_blank" rel="noopener noreferrer" className="text-blue-600 underline">
            링크 열기
          </a>
        </p>
      </div>

      <h3 className="text-2xl font-semibold border-b pb-1 border-gray-300 mb-4">의견 목록</h3>

      {!hasUserOpinion && !showForm && (
        <button
          onClick={() => setShowForm(true)}
          className="mb-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          의견 작성하기
        </button>
      )}

      {showForm && (
        <div className="mb-6 p-4 border rounded bg-white shadow-sm">
          <h4 className="text-xl font-semibold mb-3">
            {editingOpinionId ? '의견 수정' : '의견 작성'}
          </h4>

          <div className="mb-3">
            <label className="block font-medium">평점 (1~5)</label>
            <input
              type="number"
              value={rate}
              onChange={e => setRate(Number(e.target.value))}
              className="border p-2 rounded w-20"
              min={1}
              max={5}
            />
          </div>

          <div className="mb-3">
            <label className="block font-medium">코멘트</label>
            <textarea
              value={comment}
              onChange={e => setComment(e.target.value)}
              className="border p-2 rounded w-full"
              rows={3}
            />
          </div>

          <div className="mb-3">
            <label className="block font-medium">카테고리</label>
            {categoryOptions.map(opt => (
              <label key={opt} className="block text-sm">
                <input
                  type="checkbox"
                  value={opt}
                  checked={category.includes(opt)}
                  onChange={e => {
                    const selected = e.target.value
                    setCategory(prev =>
                      prev.includes(selected)
                        ? prev.filter(c => c !== selected)
                        : [...prev, selected]
                    )
                  }}
                  className="mr-2"
                />
                {opt}
              </label>
            ))}
          </div>

          <div className="mb-4">
            <label className="block font-medium">피드백 카테고리</label>
            {feedbackCategoryOptions.map(opt => (
              <label key={opt} className="block text-sm">
                <input
                  type="checkbox"
                  value={opt}
                  checked={feedbackCategory.includes(opt)}
                  onChange={e => {
                    const selected = e.target.value
                    setFeedbackCategory(prev =>
                      prev.includes(selected)
                        ? prev.filter(c => c !== selected)
                        : [...prev, selected]
                    )
                  }}
                  className="mr-2"
                />
                {opt}
              </label>
            ))}
          </div>

          <div className="flex gap-4">
            <button onClick={handleSubmit} className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
              {editingOpinionId ? '수정 완료' : '등록'}
            </button>
            <button onClick={resetForm} className="text-gray-500 hover:underline">
              취소
            </button>
          </div>
        </div>
      )}

      {problem.opinions.length > 0 ? (
        <ul className="space-y-4">
          {problem.opinions.map(op => (
            <li key={op.id} className="border rounded-md p-4 shadow-sm bg-gray-50">
              <p><strong>점수:</strong> {op.rate}</p>
              <p><strong>의견:</strong> {op.comment}</p>
              <p>
              {op.feedbackCategory.map(cat => (
                <span key={cat} className={`tag ${cat.replace(/\s/g, '\\ ')}`}>{cat}</span>
              ))}
            </p>
            <p>
              {op.category.map(cat => (
                <span key={cat} className={`tag ${cat}`}>{cat}</span>
              ))}
            </p>
              {op.userId === userId && (
                <div className="mt-2 space-x-4">
                  <button
                    onClick={() => handleEdit(op)}
                    className="text-blue-600 hover:underline"
                  >
                    수정
                  </button>
                  <button
                    onClick={() => handleDelete(op.id)}
                    className="text-red-600 hover:underline"
                  >
                    삭제
                  </button>
                </div>
              )}
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-gray-500">등록된 의견이 없습니다.</p>
      )}
    </div>
  )
}

export default ProblemDetailPage
