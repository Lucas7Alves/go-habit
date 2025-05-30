import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Inicio from './pages/Inicio';
import QuemSomos from './pages/QuemSomos';
import NossaSolucao from './pages/NossaSolucao';
import Login from './pages/Login';
import CadastrarSe from './pages/CadastrarSe';
import './App.css';
import ButtonAppBar from './components/ButtonAppBar';
import LoggedUser from './pages/LoggedUser';
import BotaoTopo from './components/BotaoTopo';
import PoliticaDePrivacidade from './pages/PoliticaDePrivacidade';
import TermosDeUso from './pages/TermosDeUso';
import ScrollToTop from './components/ScrollToTop';

function App() {
  return (
    <BrowserRouter >
      {/* Navbar aparece em todas as páginas */}
      <ButtonAppBar />

      {/* Botão para voltar ao topo */}
      <BotaoTopo />

      {/* Componente para corrigir o scroll ao ser direcionado para outra página */}
      <ScrollToTop />


      {/* Conteúdo de cada rota */}
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="/quem-somos" element={<QuemSomos />} />
        <Route path="/nossa-solucao" element={<NossaSolucao />} />
        <Route path="/login" element={<Login />} />
        <Route path="/cadastrar-se" element={<CadastrarSe />} />
        <Route path="/LoggedUser" element={<LoggedUser />} />
        <Route path="/politica-de-privacidade" element={<PoliticaDePrivacidade />} />
        <Route path="/termos-de-uso" element={<TermosDeUso />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;