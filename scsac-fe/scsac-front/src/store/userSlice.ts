import { createSlice } from '@reduxjs/toolkit'
import type {PayloadAction} from '@reduxjs/toolkit'

export type UserState = {
  isLoggedIn: boolean
  id: string
  password: string
  authority: number
  generation: number
  affiliate: string
  name: string | null
  nickname: string | null
  boj_id: string | null
}

// 초기 상태
const initialState: UserState = {
  isLoggedIn: false,
  id: '0',
  password: '',
  authority: 3,
  generation: 0,
  affiliate: '',
  name: null,
  nickname: null,
  boj_id: null,
}

// ✅ Payload에는 isLoggedIn을 제외한 나머지 정보만 받도록
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    login: (_, action: PayloadAction<Omit<UserState, 'isLoggedIn'>>) => {
      return {
        isLoggedIn: true,
        ...action.payload,
      }
    },
    logout: () => {
      return { ...initialState }
    },
  },
})

export const { login, logout } = userSlice.actions
export default userSlice.reducer