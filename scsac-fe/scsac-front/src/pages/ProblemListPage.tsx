
import React, { useEffect, useState } from 'react'
import api from '../api/axios'
import './ProblemListPage.css'
import { Link } from 'react-router-dom'

interface Problem {
  id: number
  url: string
  problemNum: number
  title: string
  rate: number
  categories: string[]
}

const allCategories = ['구현', '시뮬레이션', 'BFS', 'DFS', '백트래킹', '기타']
const searchConditions = ['문제 번호', '문제 제목', '평점', '알고리즘 분류']
const conditionMap: { [key: string]: string } = {
  '문제 번호': 'problemNum',
  '문제 제목': 'title',
  '평점': 'rate',
  '알고리즘 분류': 'category'
}

const ProblemListPage: React.FC = () => {
  const [problems, setProblems] = useState<Problem[]>([])
  const [page, setPage] = useState(0)
  const [totalPages, setTotalPages] = useState(0)
  const [searchCondition, setSearchCondition] = useState('문제 번호')
  const [searchValue, setSearchValue] = useState('')
  const [isSearchMode, setIsSearchMode] = useState(false)

  const fetchProblems = async (page: number) => {
    try {
      const res = await api.get('/problem', {
        params: { page, size: 10 }
      })
      setProblems(res.data.content)
      setTotalPages(res.data.totalPages)
    } catch (err) {
      console.error('문제 목록 로딩 실패:', err)
    }
  }

  const fetchSearchedProblems = async (page: number = 0) => {
    const backendKey = conditionMap[searchCondition]
    try {
      const res = await api.get('/problem/search', {
        params: {
          searchCondition: backendKey,
          value: searchValue,
          page,
          size: 10
        }
      })
      setProblems(res.data.content)
      setTotalPages(res.data.totalPages)
      setPage(page)
      setIsSearchMode(true)
    } catch (err) {
      console.error('검색 실패:', err)
    }
  }

  useEffect(() => {
    if (!isSearchMode) fetchProblems(page)
  }, [page])

  const handlePageChange = (newPage: number) => {
    if (newPage >= 0 && newPage < totalPages) {
      if (isSearchMode) {
        fetchSearchedProblems(newPage)
      } else {
        setPage(newPage)
      }
    }
  }

  const handleSearch = () => {
    fetchSearchedProblems(0)
  }

  return (
    <div className="problem-list-container">
      <h2 className="problem-list-title">문제 목록</h2>

      <div className="problem-search">
        <select value={searchCondition} onChange={e => setSearchCondition(e.target.value)}>
          {searchConditions.map(cond => (
            <option key={cond} value={cond}>{cond}</option>
          ))}
        </select>

        {searchCondition === '알고리즘 분류' ? (
          <select value={searchValue} onChange={e => setSearchValue(e.target.value)}>
            <option value="">전체</option>
            {allCategories.map(cat => (
              <option key={cat} value={cat}>{cat}</option>
            ))}
          </select>
        ) : (
          <input
            type="text"
            value={searchValue}
            onChange={e => setSearchValue(e.target.value)}
            onKeyDown={e => {
              if (e.key === 'Enter') handleSearch()
            }}
            placeholder="검색어 입력"
          />
        )}

        <button onClick={handleSearch}>검색</button>
        <Link to="/problems/add" className="add-problem-button">문제 등록</Link>
      </div>

      <ul className="problem-table">
        <li className="problem-row problem-header">
          <span className="problem-col">제목</span>
          <span className="problem-col">문제 번호</span>
          <span className="problem-col">평점</span>
          <span className="problem-col">분류</span>
          <span className="problem-col">링크</span>
        </li>
        {problems.map(problem => (
          <li key={problem.id} className="problem-row">
            <Link to={`/problems/${problem.id}`} className="problem-col problem-title">
              {problem.title}
            </Link>
            <span className="problem-col">{problem.problemNum}</span>
            <span className="problem-col">{problem.rate}</span>
            <span className="problem-col">{problem.categories?.join(', ')}</span>
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

      {totalPages > 1 && (
        <div className="pagination-controls">
          <button disabled={page === 0} onClick={() => handlePageChange(page - 1)}>이전</button>
          <span>{page + 1} / {totalPages}</span>
          <button disabled={page + 1 >= totalPages} onClick={() => handlePageChange(page + 1)}>다음</button>
        </div>
      )}
    </div>
  )
}

export default ProblemListPage
