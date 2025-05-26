import React, { useState } from 'react'
import api from '../api/axios'

type ProblemInput = {
  url: string
  problem_num: number
  title: string
}

type OpinionInput = {
  rate: number
  comment: string
  categoryIds: number[]
  feedbackCategoryIds: number[]
}

const allCategories = [
  { id: 1, name: 'bfs' },
  { id: 2, name: 'dfs' },
  { id: 3, name: 'dp' },
]

const allFeedbackCategories = [
  { id: 1, name: '어렵다' },
  { id: 2, name: '깔끔하다' },
]

const AddProblemPage: React.FC = () => {
  const [problem, setProblem] = useState<ProblemInput>({
    url: '',
    problem_num: 0,
    title: '',
  })

  const [opinion, setOpinion] = useState<OpinionInput>({
    rate: 0,
    comment: '',
    categoryIds: [],
    feedbackCategoryIds: [],
  })

  const handleProblemChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setProblem(prev => ({
      ...prev,
      [name]: name === 'problem_num' ? Number(value) : value,
    }))
  }

  const handleOpinionChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setOpinion(prev => ({
      ...prev,
      [name]: name === 'rate' ? Number(value) : value,
    }))
  }

  const toggleCategory = (id: number, type: 'category' | 'feedback') => {
    const key = type === 'category' ? 'categoryIds' : 'feedbackCategoryIds'
    setOpinion(prev => {
      const current = prev[key]
      return {
        ...prev,
        [key]: current.includes(id)
          ? current.filter(cid => cid !== id)
          : [...current, id],
      }
    })
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    const payload = {
      problem,
      opinion,
    }

    try {
      await api.post('/problem', payload)
      alert('문제가 성공적으로 등록되었습니다!')
      // 초기화할 수도 있음
      setProblem({ url: '', problem_num: 0, title: '' })
      setOpinion({ rate: 0, comment: '', categoryIds: [], feedbackCategoryIds: [] })
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
          name="problem_num"
          value={problem.problem_num || ''}
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

        <hr className="my-4" />

        <input
          type="number"
          name="rate"
          value={opinion.rate || ''}
          onChange={handleOpinionChange}
          placeholder="평점 (0~10)"
          className="border p-2 w-full"
        />
        <input
          type="text"
          name="comment"
          value={opinion.comment}
          onChange={handleOpinionChange}
          placeholder="의견 코멘트"
          className="border p-2 w-full"
        />

        <div>
          <p className="font-medium mb-1">카테고리 선택</p>
          {allCategories.map(cat => (
            <label key={cat.id} className="mr-4">
              <input
                type="checkbox"
                checked={opinion.categoryIds.includes(cat.id)}
                onChange={() => toggleCategory(cat.id, 'category')}
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
                checked={opinion.feedbackCategoryIds.includes(fc.id)}
                onChange={() => toggleCategory(fc.id, 'feedback')}
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
