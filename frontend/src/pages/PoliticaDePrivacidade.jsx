import Rodape from '../components/Rodape.jsx';

export default function PoliticaDePrivacidade() {
    return(
        <div id="main">
            <main id='documentoLegal'>
                <h1>Política de Privacidade</h1>
                <h3>Esta Política de Privacidade explica como coletamos, usamos, armazenamos e protegemos seus dados no uso do app GoHabit.</h3>

                <h2>Quais dados coletamos</h2>
                <p>Coletamos nome, e-mail, senha, bio, ícone de avatar e informações relacionadas às metas (nome, frequência, categoria, data de conclusão e dificuldade). Na versão mobile, também são coletadas informações da toca, como nome da equipe, membros, metas da toca e código de acesso. Esses dados são essenciais para o funcionamento e personalização da experiência no aplicativo.</p>

                <h2>Como coletamos dados</h2>
                <p>Os dados são coletados diretamente por meio de formulários preenchidos pelos usuários no app e site. Utilizamos APIs para gerenciar autenticação e banco de dados. Não utilizamos cookies para rastreamento ou armazenamento de informações, nem tecnologias similares em segundo plano.</p>

                <h2>Por que coletamos os dados</h2>
                <p>Coletamos dados para permitir o uso das funcionalidades do app, como criação de perfil, metas e equipes. Também usamos as informações para oferecer uma experiência personalizada, manter o progresso do usuário, gerar estatísticas internas e facilitar a comunicação entre usuários e equipe.</p>

                <h2>Com quem compartilhamos</h2>
                <p>Compartilhamos informações apenas com serviços essenciais ao funcionamento da plataforma, sempre de forma segura e conforme as finalidades descritas nesta política. Esses serviços incluem:</p>
                <ul>
                    <li>Firebase: para autenticação, banco de dados e notificações</li>
                    <li>Google Analytics (se utilizado): para métricas de uso</li>
                </ul>
                <p>Vale destacar que, em hipótese alguma, os dados serão vendidos a terceiros.</p>

                <h2>Como os dados são armazenados e protegidos</h2>
                <p>Após validação de conformidade com as regras de negócio, os dados são enviados de forma segura para o Firebase, onde são armazenados com criptografia. A segurança é reforçada por autenticação e restrições de acesso, garantindo proteção contra usos indevidos.</p>

                <h2>Direitos do usuário</h2>
                <p>Os usuários do GoHabit têm controle total sobre seus dados e podem, a qualquer momento, solicitar acesso às informações armazenadas e/ou realizar alterações no cadastro, como correções, exclusões ou revogação de consentimentos previamente concedidos.</p>

                <h2>Como entrar em contato</h2>
                <p>Em caso de dúvidas, problemas ou solicitações, os usuários podem entrar em contato diretamente com um dos desenvolvedores. As informações de contato estão disponíveis na seção "Quem Somos" dentro do aplicativo ou site.</p>

                <h2>Atualizações futuras na política</h2>
                <p>Esta política pode ser atualizada periodicamente. O usuário será notificado por meio do app ou por e-mail sobre mudanças significativas.</p>
            </main>
            <Rodape />
        </div>
    )
}