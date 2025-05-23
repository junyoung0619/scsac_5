import { useState } from 'react'
import api from '../api/axios'
import '../components/LoginPage.css'


function Admin() {
  const [generation, setGeneration] = useState(0)
  const [count, setCount] = useState(0)
  

  return (
    <h2>관리자 페이지</h2>
    <form>
        <input type="number" value="${generation}"></input>
    </form>
  )
}

export default Admin
