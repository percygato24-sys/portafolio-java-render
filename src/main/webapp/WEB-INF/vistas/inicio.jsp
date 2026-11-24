<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
String ctx = request.getContextPath();
boolean admin = Boolean.TRUE.equals(session.getAttribute("admin"));
%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Mi Portafolio | Inicio</title>
<link rel="stylesheet" href="<%=ctx%>/css/estilos.css">
</head>
<body>
<div class="app-shell">
  <aside class="sidebar">
    <a class="brand" href="<%=ctx%>/inicio">
      <span class="brand-mark">H</span>
      <span>HIZARRA<small>SAMANIEGO</small></span>
    </a>
    <div class="side-label">MENÚ PRINCIPAL</div>
    <a class="nav-link active" href="<%=ctx%>/inicio"><span>⌂</span> Inicio</a>
    <div class="side-label">CONTENIDO ACADÉMICO</div>
    <div class="week-nav">
      <% for (int i = 1; i <= 16; i++) { %>
        <a class="nav-link" href="<%=ctx%>/semana?n=<%=i%>">
          <span class="week-number"><%=String.format("%02d", i)%></span> Semana <%=i%>
        </a>
      <% } %>
    </div>
    <div class="sidebar-bottom">
      <% if (admin) { %>
        <a class="nav-link" href="<%=ctx%>/salir"><span>↪</span> Cerrar sesión</a>
      <% } else { %>
        <a class="nav-link" href="<%=ctx%>/login"><span>⚿</span> Administración</a>
      <% } %>
      <div class="profile-mini">
        <div class="avatar">PA</div>
        <div>
          <strong>Mi portafolio</strong>
          <small>Ingeniería de Sistemas</small>
        </div>
      </div>
    </div>
  </aside>

  <main class="main-content">
    <header class="topbar">
      <div>
        <span class="eyebrow">ESPACIO ACADÉMICO / 2026</span>
        <h1>Mi portafolio</h1>
      </div>
      <a class="admin-button" href="<%=ctx%>/login">⌘ Administración</a>
    </header>

    <section class="hero">
      <div class="hero-copy">
        <span class="pill"><i></i> PORTAFOLIO DIGITAL</span>
        <h2>Ideas que se convierten<br><span>en proyectos.</span></h2>
        <p>Un espacio para organizar, presentar y conservar mis actividades, prácticas y aprendizajes universitarios.</p>
        <a class="primary-button" href="#semanas">Explorar mis semanas <span>→</span></a>
      </div>
      <div class="hero-art">
        <div class="orb orb-one"></div>
        <div class="orb orb-two"></div>
        <div class="hero-symbol">P<span>.</span></div>
        <div class="floating-card">✦ <span>APRENDER · CREAR · CRECER</span></div>
      </div>
    </section>

    <section class="stats-grid">
      <article class="stat-card">
        <span class="stat-icon purple">▦</span>
        <div><strong>16</strong><small>Semanas académicas</small></div>
      </article>
      <article class="stat-card">
        <span class="stat-icon cyan">▤</span>
        <div><strong><%=request.getAttribute("totalTrabajos")%></strong><small>Trabajos guardados</small></div>
      </article>
      <article class="stat-card">
        <span class="stat-icon blue">⌁</span>
        <div><strong>2026</strong><small>Periodo académico</small></div>
      </article>
    </section>

    <section id="semanas" class="section-block">
      <div class="section-heading">
        <div>
          <span class="eyebrow">ORGANIZACIÓN DEL CURSO</span>
          <h2>Mis 16 semanas</h2>
          <p>Selecciona una semana para consultar o subir tus actividades.</p>
        </div>
        <span class="section-count">01 — 16</span>
      </div>
      <div class="weeks-grid">
        <% for (int i = 1; i <= 16; i++) { %>
          <a class="week-card" href="<%=ctx%>/semana?n=<%=i%>">
            <div class="week-card-top">
              <span class="week-tag">SEMANA <%=String.format("%02d", i)%></span>
              <span class="arrow">↗</span>
            </div>
            <div class="week-folder">▱</div>
            <h3>Semana <%=i%></h3>
            <p>Actividades y trabajos académicos</p>
            <div class="card-bottom"><span>Ver contenido</span><span>→</span></div>
          </a>
        <% } %>
      </div>
    </section>

    <footer class="footer">
      <span>MI PORTAFOLIO UNIVERSITARIO</span>
      <span>Diseñado para organizar mi aprendizaje · 2026</span>
    </footer>
  </main>
</div>
</body>
</html>