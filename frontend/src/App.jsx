import { useCallback, useEffect, useState } from 'react'
import { errorMessage, reservationService } from './services/reservationService.js'

const services = ['Capacitación', 'Asesoría', 'Laboratorio', 'Acompañamiento']
const initialForm = { customerName: '', reservationDate: '', reservationTime: '', service: '' }

function formatDate(date) {
  return new Intl.DateTimeFormat('es-CO', { dateStyle: 'medium', timeZone: 'UTC' })
    .format(new Date(`${date}T12:00:00Z`))
}

export default function App() {
  const [reservations, setReservations] = useState([])
  const [form, setForm] = useState(initialForm)
  const [loading, setLoading] = useState(true)
  const [saving, setSaving] = useState(false)
  const [toast, setToast] = useState(null)

  const refresh = useCallback(async () => {
    try {
      setReservations(await reservationService.getAll())
    } catch (error) {
      setToast({ type: 'error', text: errorMessage(error) })
    } finally { setLoading(false) }
  }, [])

  useEffect(() => { refresh() }, [refresh])
  useEffect(() => {
    if (!toast) return
    const timer = window.setTimeout(() => setToast(null), 4500)
    return () => window.clearTimeout(timer)
  }, [toast])

  function update(event) {
    setForm(current => ({ ...current, [event.target.name]: event.target.value }))
  }

  async function submit(event) {
    event.preventDefault()
    setSaving(true)
    try {
      await reservationService.create(form)
      setForm(initialForm)
      setToast({ type: 'success', text: 'La reserva quedó creada.' })
      await refresh()
    } catch (error) {
      setToast({ type: 'error', text: errorMessage(error) })
    } finally { setSaving(false) }
  }

  async function cancel(id) {
    try {
      await reservationService.cancel(id)
      setToast({ type: 'success', text: 'La reserva fue cancelada.' })
      await refresh()
    } catch (error) { setToast({ type: 'error', text: errorMessage(error) }) }
  }

  const today = new Date().toLocaleDateString('en-CA')

  return (
    <main className="page-shell">
      <header className="topbar"><a className="brand" href="#inicio" aria-label="Inicio"><span className="brand-mark">R</span><span>Agenda<span className="brand-light"> TdeA</span></span></a><span className="topbar-note">Gestión de reservas</span></header>
      <section className="hero" id="inicio"><div><div className="eyebrow"><span className="live-dot" /> ESPACIOS PARA TI</div><h1>Tu tiempo,<br /><span>bien organizado.</span></h1><p>Agenda una reserva y encuentra todos tus espacios en un solo lugar.</p></div><div className="hero-art" aria-hidden="true"><div className="art-orbit orbit-one"/><div className="art-orbit orbit-two"/><div className="calendar-card"><div className="calendar-head"><span>AGENDA</span><span>✳</span></div><div className="calendar-month">Tu próximo espacio</div><div className="calendar-date">{new Intl.DateTimeFormat('es-CO', { day: '2-digit' }).format(new Date())}</div><div className="calendar-foot">Un buen día para empezar</div></div></div></section>

      {toast && <div className={`toast ${toast.type}`} role="status"><span>{toast.type === 'success' ? '✓' : '!'}</span>{toast.text}<button type="button" aria-label="Cerrar aviso" onClick={() => setToast(null)}>×</button></div>}

      <section className="booking-card" aria-labelledby="booking-title"><div className="section-heading"><div><span className="section-kicker">NUEVA CITA</span><h2 id="booking-title">Haz una reserva</h2><p>Completa tus datos para elegir un espacio.</p></div><span className="step-badge">01 <i>/ 01</i></span></div>
        <form onSubmit={submit} className="reservation-form">
          <label className="field field-wide"><span>Nombre del cliente</span><input name="customerName" value={form.customerName} onChange={update} placeholder="¿Cómo te llamas?" maxLength="120" autoComplete="name" required /></label>
          <label className="field"><span>Fecha</span><input name="reservationDate" type="date" min={today} value={form.reservationDate} onChange={update} required /></label>
          <label className="field"><span>Hora</span><input name="reservationTime" type="time" value={form.reservationTime} onChange={update} required /></label>
          <label className="field field-wide"><span>Servicio</span><select name="service" value={form.service} onChange={update} required><option value="" disabled>Selecciona un servicio</option>{services.map(item => <option key={item} value={item}>{item}</option>)}</select></label>
          <div className="form-footer"><span><span className="required-star">*</span> Todos los campos son obligatorios</span><button className="primary-button" disabled={saving}>{saving ? 'Guardando…' : <>Crear reserva <span aria-hidden="true">↗</span></>}</button></div>
        </form>
      </section>

      <section className="list-section" aria-labelledby="list-title"><div className="list-heading"><div><span className="section-kicker">TU AGENDA</span><h2 id="list-title">Reservas</h2><p>Consulta y administra tus espacios agendados.</p></div><div className="reservation-count"><strong>{reservations.filter(r => r.status === 'ACTIVA').length.toString().padStart(2, '0')}</strong><span>activas</span></div></div>
        <div className="table-card"><div className="table-scroll"><table><thead><tr><th>ID</th><th>CLIENTE</th><th>FECHA</th><th>HORA</th><th>SERVICIO</th><th>ESTADO</th><th><span className="sr-only">Acciones</span></th></tr></thead><tbody>
          {loading ? <tr><td className="empty-state" colSpan="7">Cargando tu agenda…</td></tr> : reservations.length === 0 ? <tr><td className="empty-state" colSpan="7"><span className="empty-icon">✳</span><strong>Aún no hay reservas</strong><span>Tu próxima cita empieza con el formulario de arriba.</span></td></tr> : reservations.map(item => <tr key={item.id}><td className="id-cell">{String(item.id).padStart(2, '0')}</td><td className="customer-cell">{item.customerName}</td><td>{formatDate(item.reservationDate)}</td><td>{item.reservationTime?.slice(0, 5)}</td><td>{item.service}</td><td><span className={`status-pill ${item.status === 'ACTIVA' ? 'active' : 'cancelled'}`}><i />{item.status === 'ACTIVA' ? 'Activa' : 'Cancelada'}</span></td><td>{item.status === 'ACTIVA' && <button className="cancel-button" onClick={() => cancel(item.id)} aria-label={`Cancelar reserva ${item.id}`}>Cancelar</button>}</td></tr>)}
        </tbody></table></div><div className="table-caption"><span><i className="caption-dot"/> Datos actualizados en tiempo real</span><button type="button" className="refresh-button" onClick={refresh} aria-label="Actualizar reservas">↻ Actualizar</button></div></div>
      </section>
      <footer><span>Agenda TdeA</span><span>Hecho para que tu día fluya mejor <b>✳</b></span></footer>
    </main>
  )
}
