import axios from 'axios'

const api = axios.create({ baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080' })

export const reservationService = {
  async getAll() { return (await api.get('/reservas')).data },
  async create(reservation) { return (await api.post('/reservas', reservation)).data },
  async cancel(id) { return (await api.delete(`/reservas/${id}`)).data },
}

export function errorMessage(error) {
  const data = error?.response?.data
  if (data?.fields && Object.keys(data.fields).length) return Object.values(data.fields).join(' ')
  return data?.message || 'No fue posible conectar con el servidor. Intenta de nuevo.'
}
