import React, { useEffect, useState } from 'react'
import api from '../api/axios'
import { Link } from 'react-router-dom'
import './ProblemListPage.css'

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
  categories: string[]
}


const allCategories = ['구현', '시뮬레이션',  'BFS', 'DFS', '백트래킹', '기타']
const searchConditions = ['문제 번호', '문제 제목', '평점', '알고리즘 분류']
const conditionMap: { [key: string]: string } = {
  '문제 번호': 'problemNum',
  '문제 제목': 'title',
  '평점': 'rate',
  '알고리즘 분류': 'category'
}

const ProblemListPage: React.FC = () => {
  const [problems, setProblems] = useState<Problem[]>([])

  const [searchCondition, setSearchCondition] = useState<string>('문제 번호')
  const [searchValue, setSearchValue] = useState<string>('')


  useEffect(() => {
    api.get('/problem/')
      .then(res => {
        console.log('[GET] problems에 대한 응답:', res.data)

        const problemsData = Array.isArray(res.data) ? res.data : []
      
        setProblems(problemsData)
        console.log('문제 목록:', problemsData)
      })
      .catch(err => {
        console.error('문제 목록 불러오기 실패:', err)
        setProblems([])
      })


  }, [])

  const filteredProblems = Array.isArray(problems)
  ? problems
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


<div className="problem-search">
  <select
    value={searchCondition}
    onChange={(e) => setSearchCondition(e.target.value)}
  >
    {searchConditions.map(cond => (
      <option key={cond} value={cond}>{cond}</option>
    ))}
  </select>

  {searchCondition === '알고리즘 분류' ? (
    <select
      value={searchValue}
      onChange={(e) => setSearchValue(e.target.value)}
    >
      <option value="">전체</option>
      {allCategories.map(cat => (
        <option key={cat} value={cat}>{cat}</option>
      ))}
    </select>
  ) : (
    <input
      type="text"
      value={searchValue}
      onChange={(e) => setSearchValue(e.target.value)}
      onKeyDown={(e) => {
        if (e.key === 'Enter') { handleSearch(); }
      }}
      placeholder="검색어 입력"
    />
  )}

  <button onClick={handleSearch}>검색</button>
</div>



    </div>

    <ul className="problem-table">
  <li className="problem-row problem-header">
    <span className="problem-col">제목</span>
    <span className="problem-col">문제 번호</span>
    <span className="problem-col">평점</span>
    <span className="problem-col">분류</span>
    <span className="problem-col">링크</span>
  </li>

  {filteredProblems.map(problem => (
    <li key={problem.id} className="problem-row">
      <Link to={`/problems/${problem.id}`} className="problem-col problem-title">
        {problem.title}
      </Link>
      <span className="problem-col">{problem.problemNum}</span>
      <span className="problem-col">{problem.rate}</span>
      <span className="problem-col">
        {problem.categories.join(', ')}
      </span>
      <a
        href={problem.url}
        target="_blank"
        rel="noopener noreferrer"
        className="problem-col problem-link"
      >
        링크
      </a>
    </li>
  ))}
</ul>


    </div>
  )
}

export default ProblemListPage
