import axios from 'axios'
import React, { useState } from 'react'

type ProblemInput = {
  url: string
  problem_num: number
  title: string
  rate: number
}

type OpinionInput = {
  rate: number
  comment: string
  categoryIds: number[]
  feedbackCategoryIds: number[]
}

const AddProblemPage: React.FC = () => {
  const [problem, setProblem] = useState<ProblemInput>({
    url: '',
    problem_num: 0,
    title: '',
    rate: 0,
  })

  const [currentOpinion, setCurrentOpinion] = useState<OpinionInput>({
    rate: 0,
    comment: '',
    categoryIds: [],
    feedbackCategoryIds: [],
  })

  const handleProblemChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setProblem(prev => ({
      ...prev,
      [name]: name === 'problem_num' || name === 'rate' ? Number(value) : value,
    }))
  }

  const handleOpinionChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target
    setCurrentOpinion(prev => ({
      ...prev,
      [name]: name === 'rate' ? Number(value) : value,
    }))
  }

  const handleCategoryToggle = (id: number, type: 'category' | 'feedback') => {
    setCurrentOpinion(prev => {
      const key = type === 'category' ? 'categoryIds' : 'feedbackCategoryIds'
      const list = prev[key]
      return {
        ...prev,
        [key]: list.includes(id)
          ? list.filter(cid => cid !== id)
          : [...list, id],
      }
    })
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    const payload = {
      problem,
      opinion: currentOpinion,  // opinions 배열이 아니라 단일 opinion
    }
    console.log('등록 데이터:', payload)
    axios.post('/problem', payload)
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

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <h1 className="text-2xl font-bold mb-4">문제 등록 페이지</h1>
      <form onSubmit={handleSubmit} className="space-y-4 bg-white shadow p-6 rounded-lg">
        <h2 className="text-xl font-semibold">문제 정보</h2>
        <input
          name="url"
          placeholder="문제 URL"
          onChange={handleProblemChange}
          className="border p-2 w-full"
          value={problem.url}
        />
        <input
          name="problem_num"
          placeholder="문제 번호"
          type="number"
          onChange={handleProblemChange}
          className="border p-2 w-full"
          value={problem.problem_num || ''}
        />
        <input
          name="title"
          placeholder="문제 제목"
          onChange={handleProblemChange}
          className="border p-2 w-full"
          value={problem.title}
        />
        <input
          name="rate"
          placeholder="난이도 (0~10)"
          type="number"
          onChange={handleProblemChange}
          className="border p-2 w-full"
          value={problem.rate || ''}
        />

        <hr className="my-4" />
        <h3 className="text-lg font-semibold">의견 입력</h3>
        <input
          name="rate"
          placeholder="의견 점수"
          type="number"
          value={currentOpinion.rate || ''}
          onChange={handleOpinionChange}
          className="border p-2 w-full"
        />
        <input
          name="comment"
          placeholder="의견 코멘트"
          value={currentOpinion.comment}
          onChange={handleOpinionChange}
          className="border p-2 w-full"
        />

        <div>
          <p className="font-medium">카테고리</p>
          {allCategories.map(cat => (
            <label key={cat.id} className="mr-2">
              <input
                type="checkbox"
                checked={currentOpinion.categoryIds.includes(cat.id)}
                onChange={() => handleCategoryToggle(cat.id, 'category')}
              />
              {cat.name}
            </label>
          ))}
        </div>

        <div>
          <p className="font-medium">피드백 카테고리</p>
          {allFeedbackCategories.map(fc => (
            <label key={fc.id} className="mr-2">
              <input
                type="checkbox"
                checked={currentOpinion.feedbackCategoryIds.includes(fc.id)}
                onChange={() => handleCategoryToggle(fc.id, 'feedback')}
              />
              {fc.name}
            </label>
          ))}
        </div>

        <button type="submit" className="bg-green-700 text-white px-4 py-2 rounded">
          문제 등록
        </button>
      </form>
    </div>
  )
}

export default AddProblemPage
