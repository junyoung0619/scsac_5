import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import api from '../api/axios'
import './ProblemDetailPage.css'
import type { RootState } from '../store'  // 스토어 타입 경로 맞게 바꿔주세요
import { useSelector } from 'react-redux'

type Opinion = {
  id: number
  score: number
  feedback: string
  comment: string
  category: string
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

const ProblemDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>()
  const [problem, setProblem] = useState<Problem | null>(null)
  const userId = useSelector((state: RootState) => state.user.id)

  const hasUserOpinion = problem?.opinions.some(op => op.userId === userId)

  console.log('현재 userId:', userId)
  useEffect(() => {
    if (id) {
      api.get(`/problem/${id}`)
        .then(res => {
          setProblem(res.data)
        })
        .catch(err => {
          console.error('문제 상세 불러오기 실패:', err)
        })
    }
  }, [id])

  if (!problem) return <div className="text-center mt-10 text-gray-600">로딩 중...</div>

  return (
    <div className="problem-detail-container">
      <h2 className="text-3xl font-bold text-blue-800 mb-4">{problem.title}</h2>
      <div className="space-y-2 mb-6 text-gray-800">
        <p><strong className="text-gray-700">문제 번호:</strong> {problem.problemNum}</p>
        <p><strong className="text-gray-700">평점:</strong> <span className="text-yellow-600 font-semibold">{problem.rate}</span></p>
        <p>
          <strong className="text-gray-700">문제 링크:</strong>{' '}
          <a
            href={problem.url}
            target="_blank"
            rel="noopener noreferrer"
            className="text-blue-600 underline inline-flex items-center gap-1"
          >
            링크 열기
          </a>
        </p>
      </div>

      {/* 의견 목록 */}


      <h3 className="text-2xl font-semibold border-b pb-1 border-gray-300 mb-4">의견 목록</h3>

      {!hasUserOpinion && (
        <button
          onClick={() => console.log('작성 페이지로 이동')}
          className="mb-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          의견 작성하기
        </button>
      )}
      
      {problem.opinions && problem.opinions.length > 0 ? (
        <ul className="space-y-4">
          {problem.opinions.map(op => (
            <li key={op.id} className="border rounded-md p-4 shadow-sm bg-gray-50">
              <p><strong className="text-gray-600">점수:</strong> {op.score}</p>
              <p><strong className="text-gray-600">코멘트:</strong> {op.comment}</p>
              <p><strong className="text-gray-600">피드백:</strong> {op.feedback}</p>
              <p><strong className="text-gray-600">카테고리:</strong> <span className="text-green-700 font-medium"> {op.category}</span></p>
              {op.userId === userId && (
                <div>
                <button
                  onClick={() => console.log(`수정 페이지로 이동: opinionId=${op.id}`)}
                  className="mt-2 text-blue-600 hover:underline"
                >
                  수정
                </button>
                  <button
                  onClick={() => console.log(`삭제 페이지로 이동: opinionId=${op.id}`)}
                  className="mt-2 text-blue-600 hover:underline"
                > 삭제
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
