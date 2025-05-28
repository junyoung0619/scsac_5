// 📁 src/App.tsx
import Header from './components/Header'
import LoginPage from './pages/LoginPage'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import MainPage from './pages/MainPage'
import MyPage from './pages/MyPage'
import EditProfile from './pages/EditProfilePage'
import ProblemListPage from './pages/ProblemListPage'
import AddProblemPage from './pages/AddProblemPage'
import ProblemDetailPage from './pages/ProblemDetailPage'
import AdminPage from './pages/AdminPage'
import { useEffect } from 'react'
import { useDispatch } from 'react-redux'
import { login } from './store/userSlice'
import api from './api/axios'

function App() {
  console.log("App mounted")
  const dispatch = useDispatch()
  // const [isLoading, setIsLoading] = useState(true)


  useEffect(() => {
    const restoreUser = async () => {
      const token = localStorage.getItem("jwt")
      if (token) {
        try {
          const res = await api.get("/user/me", {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          })

          const user = res.data;

          dispatch(login({
            id: user.id,
            password: user.password,
            authority: user.authority,
            generation: user.generation,
            affiliate: user.affiliate,
            name: user.name,
            nickname: user.nickname,
            boj_id: user.boj_id,
          }))
        } catch (err) {
          console.error("사용자 정보 복구 실패", err)
          localStorage.removeItem("jwt")
        }
      }
    }

    restoreUser()
  }, [])


  return (
    <>
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/main" element={<MainPage />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/editProfile" element={<EditProfile />} />
        <Route path="/problems" element={<ProblemListPage />} />
        <Route path="/add-problem" element={<AddProblemPage />} />
        <Route path="/problems/:id" element={<ProblemDetailPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/admin" element={<AdminPage />} />
      </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
