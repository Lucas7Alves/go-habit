import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Inicio from './pages/Inicio';
import QuemSomos from './pages/QuemSomos';
import NossaSolucao from './pages/NossaSolucao';
import ModeloNegocio from './pages/ModeloNegocio';
import EtapasDesenvolvimento from './pages/EtapasDesenvolvimento';
import Documentos from './pages/Documentos';
import Login from './pages/Login';
import CadastrarSe from './pages/CadastrarSe';
import './index.css';
import './App.css';
import ButtonAppBar from './components/ButtonAppBar';

function App() {
  return (
    <BrowserRouter>
      {/* Navbar aparece em todas as páginas */}
      <ButtonAppBar />

      {/* Conteúdo de cada rota */}
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="/quem-somos" element={<QuemSomos />} />
        <Route path="/nossa-solucao" element={<NossaSolucao />} />
        <Route path="/modelo-de-negocio" element={<ModeloNegocio />} />
        <Route path="/etapas-de-desenvolvimento" element={<EtapasDesenvolvimento />} />
        <Route path="/documentos" element={<Documentos />} />
        <Route path="/login" element={<Login />} />
        <Route path="/cadastrar-se" element={<CadastrarSe />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
