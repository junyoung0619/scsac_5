// 📁 src/components/Header.tsx
import { useSelector, useDispatch } from 'react-redux'
import type { RootState } from '../store'
import { logout } from '../store/userSlice'
import './Header.css'
import { useNavigate } from 'react-router-dom'
import { useLocation } from 'react-router-dom';


function Header() {
  const location = useLocation();

  // 현재 경로가 문제 목록 페이지인지 확인
  const isProblemList = location.pathname.startsWith('/problems');
  const { isLoggedIn, nickname } = useSelector((state: RootState) => state.user)
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const handleLogout = () => {
    dispatch(logout())
    navigate('/')
    alert('로그아웃 되었습니다')
 }

  return (
    <header className={`header ${isProblemList ? 'problem-list' : 'other'}`}>
      <div className="header-title" 
  style={{ cursor: 'pointer' }} 
  onClick={() => navigate('/problems')}>SCSAC: Algorithm</div>

      <div className="header-buttons">
        {isLoggedIn ? ( 
          <div className="header-user">
            <div className="nickname">{nickname} 님</div>
            <div className="header-buttons-vertical">
              <button onClick={() => navigate('/mypage')}>마이페이지</button>
              <button onClick={handleLogout}>로그아웃</button>
            </div>
          </div>
        ) : (
          <div className="header-buttons-vertical">
          <button  onClick={() => navigate('/login')}>로그인</button>
          </div>
        )}
      </div>
    </header>
  )
}

export default Header
