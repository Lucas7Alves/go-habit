import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Inicio from './pages/Inicio';
import QuemSomos from './pages/QuemSomos';
import NossaSolucao from './pages/NossaSolucao';
import Login from './pages/Login';
import CadastrarSe from './pages/CadastrarSe';
import Home from './pages/Home';
import './App.css';
import ButtonAppBar from './components/ButtonAppBar';

function App() {
  return (
    <BrowserRouter >
      {/* Navbar aparece em todas as páginas */}
      <ButtonAppBar />

      {/* Conteúdo de cada rota */}
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="/quem-somos" element={<QuemSomos />} />
        <Route path="/nossa-solucao" element={<NossaSolucao />} />
        <Route path="/login" element={<Login />} />
        <Route path="/cadastrar-se" element={<CadastrarSe />} />
        <Route path="/Home" element={<Home />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
