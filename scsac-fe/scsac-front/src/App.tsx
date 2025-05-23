// 📁 src/App.tsx
import Header from './components/Header'
import LoginPage from './pages/LoginPage'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import MainPage from './pages/MainPage'
import MyPage from './pages/MyPage'
import EditProfile from './pages/EditProfilePage'


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
      </Routes>
    </BrowserRouter>
    </>
  )
}

export default App
