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
//import AdminPage from './pages/AdminPage'

function App() {
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

      </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
