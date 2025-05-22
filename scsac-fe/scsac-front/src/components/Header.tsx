// 📁 src/components/Header.tsx
import { useSelector, useDispatch } from 'react-redux'
import type { RootState } from '../store'
import { logout } from '../store/userSlice'
import './Header.css'

function Header() {
  const { isLoggedIn, nickname } = useSelector((state: RootState) => state.user)
  const dispatch = useDispatch()

  const handleLogout = () => {
    dispatch(logout())
    alert('로그아웃 되었습니다.') // 라우터 없으므로 alert 처리
  }

  return (
    <header className="header">
      <div className="header-title">SCSAC</div>

      <div className="header-buttons">
        {isLoggedIn ? (
          <>
            <span className="nickname">{nickname}님</span>
            <button onClick={() => alert('마이페이지 기능은 추후 추가됩니다.')}>마이페이지</button>
            <button onClick={handleLogout}>로그아웃</button>
          </>
        ) : (
          <button onClick={() => alert('이미 로그인 화면입니다.')}>로그인</button>
        )}
      </div>
    </header>
  )
}

export default Header
