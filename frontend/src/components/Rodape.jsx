import { Link } from 'react-router-dom';

export default function Rodape(){
    return (
        <footer style={{width:"100%"}}>
            <div class="LogoGoHabit">
                <img id='logotipo' src="../src/img/GoHabit_logo_flatten.svg" alt="Logo GoHabit" />
                <h1>GoHabit</h1>
            </div>

            <div class="LinksNavegacao">
                <Link to="/">Início</Link>
                <Link to="/quem-somos">Quem Somos</Link>
                <Link to="/nossa-solucao">Nossa Solução</Link>
            </div>

            <div class="LinksDocumentosLegais">
                <Link to="/politica-de-privacidade">Política de Privacidade</Link>
                <Link to="/termos-de-uso">Termos de Uso</Link>
                <p>© 2025 GoHabit. Todos os direitos reservados.</p>
            </div>
        </footer>
    );
}