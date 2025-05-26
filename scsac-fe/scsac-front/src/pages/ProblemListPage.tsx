import React, { useEffect, useState } from 'react'
import api from '../api/axios'
import { Link } from 'react-router-dom'

type Opinion = {
  id: number
  problem_id: number
  score: number
  feedback: string
  comment: string
  category: string
}

type Problem = {
  id: number
  url: string
  problem_num: string
  title: string
  rate: number
  opinions: Opinion[]
}

const allCategories = ['구현', 'bfs', 'dfs', '백트래킹']

const ProblemListPage: React.FC = () => {
  const [problems, setProblems] = useState<Problem[]>([])
  const [selectedCategory, setSelectedCategory] = useState<string>('')

  useEffect(() => {
    api.get('/problem')
      .then(res => {
        console.log('[GET] problems에 대한 응답:', res.data)
        const problemsData = Array.isArray(res.data) ? res.data : []
        setProblems(problemsData)
      })
      .catch(err => {
        console.error('문제 목록 불러오기 실패:', err)
        setProblems([])
      })
  }, [])

  const filteredProblems = Array.isArray(problems)
    ? (selectedCategory
        ? problems.filter(problem =>
            problem.opinions.some(op => op.category === selectedCategory)
          )
        : problems)
    : []

  return (
    <div>
      <h2>문제 목록</h2>
      <Link to="/add-problem">
        <button>문제 등록</button>
      </Link>

      <div style={{ margin: '1em 0' }}>
        <label htmlFor="categoryFilter">카테고리 필터: </label>
        <select
          id="categoryFilter"
          value={selectedCategory}
          onChange={(e) => setSelectedCategory(e.target.value)}
        >
          <option value="">전체 보기</option>
          {allCategories.map(cat => (
            <option key={cat} value={cat}>{cat}</option>
          ))}
        </select>
      </div>

      <ul>
        {filteredProblems.map(problem => (
          <li key={problem.id}>
            <Link to={`/problems/${problem.id}`}>
              <h3>{problem.title}</h3>
              <p>문제 번호: {problem.problem_num}</p>
              <p>평점: {problem.rate}</p>
              <a href={problem.url} target="_blank" rel="noopener noreferrer">문제 링크</a>
              <div>
                <strong>분류:</strong>{' '}
                {Array.from(new Set((problem.opinions ?? []).map(op => op.category))).join(', ')}
              </div>
            </Link>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default ProblemListPage
