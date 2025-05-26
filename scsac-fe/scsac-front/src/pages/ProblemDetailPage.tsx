import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import api from '../api/axios'

type Opinion = {
  id: number
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

const ProblemDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>()
  const [problem, setProblem] = useState<Problem | null>(null)

  useEffect(() => {
    if (id) {
      api.get(`/problem/${id}`)
        .then(res => {
          console.log('문제 상세 정보:', res.data)
          console.log('[TEST] 응답 상태:', res.status)
          setProblem(res.data)
        })
        .catch(err => {
          console.error('문제 상세 불러오기 실패:', err)
        })
    }
  }, [id])

  if (!problem) return <div>로딩 중...</div>

  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-2">{problem.title}</h2>
      <p>문제 번호: {problem.problem_num}</p>
      <span
      onClick={() => window.open(problem.url, '_blank', 'noopener,noreferrer')}
      className="text-blue-600 underline cursor-pointer"
    >
      문제 링크
    </span>

      <h3 className="text-xl font-semibold mt-4">의견</h3>
      {problem.opinions && problem.opinions.length > 0 ? (
        <ul className="list-disc ml-6">
          {problem.opinions.map(op => (
            <li key={op.id}>
              <p>점수: {op.score}</p>
              <p>코멘트: {op.comment}</p>
              <p>카테고리: {op.category}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p>등록된 의견이 없습니다.</p>
      )}
    </div>
  )
}

export default ProblemDetailPage
