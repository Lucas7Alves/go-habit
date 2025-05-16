import { Link } from 'react-router-dom';

export default function Rodape(){
    return (
        <footer>
            <div class="LogoGoHabit">
                <img src="" alt="Logo GoHabit" />
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