import GitHubIcon from '@mui/icons-material/GitHub';
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import EmailIcon from '@mui/icons-material/Email';

export default function QuemSomos() {
    return (
        <div id="main">
            <p style={{marginTop: '40px'}}>O projeto está sendo desenvolvido por um grupo de estudantes dedicados e comprometidos em alcançar os melhores resultados. Cada membro contribui com seu conhecimento e habilidades para garantir o sucesso dessa iniciativa. Abaixo, apresentamos os integrantes que fazem parte desta equipe e que desempenham papéis essenciais no andamento do projeto.
            </p>
            <h2>Equipe</h2>

            <div id='perfil'>
                <section>
                    <img src="../src/img/Ana-Oliveira.jpg" alt="Foto Ana Oliveira" />
                    <article>
                        <h3>Ana Oliveira</h3>
                        <h4>Função</h4>
                        <a href="https://www.linkedin.com/in/ana-oliveira-21439924b/"><LinkedInIcon />Ana oliveira</a>
                        <a href="https://github.com/Anna-Olyvera"><GitHubIcon />anna-Olyvera</a>
                        <a href="mailto:anab.goncalo@gmail.com"><EmailIcon />anab.goncalo@gmail.com</a>
                    </article>
                </section>

                <section>
                    <img src="../src/img/Ana-Oliveira.jpg" alt="Foto Bruna Carvalho" />
                    <article>
                        <h3>Bruna Carvalho</h3>
                        <h4>Função</h4>
                        <a href="https://www.linkedin.com/in/bru-carvb/"><LinkedInIcon />Bruna Carvalho</a>
                        <a href="https://github.com/brucarv"><GitHubIcon />brucarv</a>
                        <a href="mailto:"><EmailIcon /></a>
                    </article>
                </section>

                <section>
                    <img src="../src/img/Ana-Oliveira.jpg" alt="Foto João Galdino" />
                    <article>
                        <h3>João Galdino</h3>
                        <h4>Função</h4>
                        <a href="https://www.linkedin.com/in/joaogaldino7/"><LinkedInIcon />João Galdino</a>
                        <a href="https://github.com/joaogldn"><GitHubIcon />joaogldn</a>
                        <a href="mailto:"><EmailIcon /></a>
                    </article>
                </section>

                <section>
                    <img src="../src/img/Ana-Oliveira.jpg" alt="Foto Leon de França" />
                    <article>
                        <h3>Leon de França</h3>
                        <h4>Função</h4>
                        <a href="https://www.linkedin.com/in/leon-trindade-805232246/"><LinkedInIcon />Leon Trindade</a>
                        <a href="https://github.com/leonstro"><GitHubIcon />leonstro</a>
                        <a href="mailto:"><EmailIcon /></a>
                    </article>
                </section>

                <section>
                    <img src="../src/img/Ana-Oliveira.jpg" alt="Foto Lucas Alves" />
                    <article>
                        <h3>Lucas Alves</h3>
                        <h4>Função</h4>
                        <a href="https://www.linkedin.com/in/lucasalves0909/"><LinkedInIcon />Lucas Alves</a>
                        <a href="https://github.com/Lucas7Alves"><GitHubIcon />Lucas7Alves</a>
                        <a href="mailto:"><EmailIcon /></a>
                    </article>
                </section>
                
                <section>
                    <img src="../src/img/Ana-Oliveira.jpg" alt="Foto Wellerson Morais" />
                    <article>
                        <h3>Wellerson Morais</h3>
                        <p>Função</p>
                        <a href="https://www.linkedin.com/in/wellerson-paulo-61289326b/"><LinkedInIcon />Wellerson Paulo</a>
                        <a href="https://github.com/WellersonMorais"><GitHubIcon />WellersonMorais</a>
                        <a href="mailto:"><EmailIcon /></a>
                    </article>
                </section>
            </div>

            <h2>Orientador</h2>

            <section>
                <img src="../src/img/Ana-Oliveira.jpg" alt="Foto Vinícius Amador" />
                <article>
                    <h3>Vinícius Amador</h3>
                    <h4>Professor Orientador</h4>
                    <a href="https://www.linkedin.com/in/vinícius-costa-amador-684484241/"><LinkedInIcon />Vinícius Costa</a>
                    <a href="https://github.com/viniciusamador"><GitHubIcon />ViniciusAmador</a>
                </article>
            </section>

        </div>
    );
}