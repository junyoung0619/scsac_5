import React from 'react'
import { Link } from 'react-router-dom'

type Opinion = {
  id: number
  problem_id: number
  score: number
  feedback: string
  comment: string
  category: string // ex. 'bfs', 'dfs'
}

type Problem = {
  id: number
  url: string
  problem_num: string
  title: string
  rate: number
  opinions: Opinion[]
}

// 예시 데이터
const problems: Problem[] = [
  {
    id: 1,
    url: 'https://example.com/1',
    problem_num: '1001',
    title: '두 수의 합',
    rate: 4.5,
    opinions: [
      {
        id: 1,
        problem_id: 1,
        score: 5,
        feedback: '구현 연습하기 좋아요',
        comment: '기초 구현에 적합',
        category: '구현',
      },
      {
        id: 2,
        problem_id: 1,
        score: 4,
        feedback: 'bfs 연습에 좋아요',
        comment: 'bfs로도 풀 수 있음',
        category: 'bfs',
      },
    ],
  },
  {
    id: 2,
    url: 'https://example.com/2',
    problem_num: '1002',
    title: '문자열 뒤집기',
    rate: 4.2,
    opinions: [
      {
        id: 3,
        problem_id: 2,
        score: 4,
        feedback: '문자열 다루기 연습',
        comment: '문자열 처리에 익숙해질 수 있음',
        category: '문자열',
      },
    ],
  },
]

const ProblemListPage: React.FC = () => {
  return (
    <div>
      <h2>문제 목록</h2>
      <Link to="/add-problem">
        <button>문제 등록</button>
      </Link>
      <ul>
        {problems.map((problem) => (
          <li key={problem.id}>
            <Link to={`/problems/${problem.id}`}>
              <h3>{problem.title}</h3>
              <p>문제 번호: {problem.problem_num}</p>
              <p>평점: {problem.rate}</p>
              <a href={problem.url} target="_blank" rel="noopener noreferrer">문제 링크</a>
              <div>
                <strong>분류:</strong>{' '}
                {Array.from(new Set(problem.opinions.map(op => op.category))).join(', ')}
              </div>
            </Link>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default ProblemListPage