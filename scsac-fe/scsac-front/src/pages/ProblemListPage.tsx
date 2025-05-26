import React, { useEffect, useState } from 'react'
import api from '../api/axios'
import { Link } from 'react-router-dom'
import './ProblemListPage.css'
import type { RootState } from '../store'  // 스토어 타입 경로 맞게 바꿔주세요
import { useSelector } from 'react-redux'

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
  problemNum: number
  title: string
  rate: number
  opinions: Opinion[]
}

const allCategories = ['구현', 'bfs', 'dfs', '백트래킹']
const searchConditions = ['문제 번호', '문제 제목', '평점', '알고리즘 분류']
const conditionMap: { [key: string]: string } = {
  '문제 번호': 'problemNum',
  '문제 제목': 'title',
  '평점': 'rate',
  '알고리즘 분류': 'category'
}

const ProblemListPage: React.FC = () => {
  const [problems, setProblems] = useState<Problem[]>([])
  const [selectedCategory, setSelectedCategory] = useState<string>('')

  const [searchCondition, setSearchCondition] = useState<string>('문제 번호')
  const [searchValue, setSearchValue] = useState<string>('')
  const userId = useSelector((state: RootState) => state.user.id)

  console.log('현재 userId:', userId)

  useEffect(() => {
    api.get('/problem/')
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


    const handleSearch = async () => {
      const backendKey = conditionMap[searchCondition]
      try {
        const res = await api.get('/problem/search', {
          params: {
            searchCondition: backendKey,
            value: searchValue
          },
          withCredentials: true 
        })
        if (Array.isArray(res.data)) {
          setProblems(res.data)
        } else {
          setProblems([])
        }
      } catch (err) {
        console.error('검색 실패:', err)
      }
    }

  return (
<div className="problem-list-container">
  <h2 className="problem-list-title">문제 목록</h2>

    <div className="problem-list-controls">
      <Link to="/add-problem" className="add-problem-button">문제 등록</Link>
      <div className="category-filter">
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

      <div className="problem-search">
          <select
            value={searchCondition}
            onChange={(e) => setSearchCondition(e.target.value)}
          >
            {searchConditions.map(cond => (
              <option key={cond} value={cond}>{cond}</option>
            ))}
          </select>
          <input
            type="text"
            value={searchValue}
            onChange={(e) => setSearchValue(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === 'Enter') { handleSearch(); }
            }}
            placeholder="검색어 입력"
          />
          <button onClick={handleSearch}>검색</button>
        </div>



    </div>

    <ul className="problem-list">
  {filteredProblems.map(problem => (
    <li key={problem.id} className="problem-item">
      <Link to={`/problems/${problem.id}`}>
        <h3 className="text-lg font-semibold text-blue-800">{problem.title}</h3>
      </Link>

      <p><strong>문제 번호:</strong> <span className="text-gray-700">{problem.problemNum}</span></p>
      <p><strong>평점:</strong> <span className="text-yellow-600 font-semibold">{problem.rate}</span></p>

      <a
        href={problem.url}
        target="_blank"
        rel="noopener noreferrer"
        className="text-blue-500 underline"
      >
        문제 링크
      </a>

      <div className="mt-1">
        <strong>분류:</strong>{' '}
        <span className="text-green-700 font-medium">
          {Array.from(new Set((problem.opinions ?? []).map(op => op.category))).join(', ')}
        </span>
      </div>
    </li>
  ))}
</ul>
    </div>
  )
}

export default ProblemListPage
