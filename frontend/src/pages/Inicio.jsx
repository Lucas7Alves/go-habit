import Rodape from '../components/Rodape.jsx';

export default function Inicio() {
    return (
        <div id="main">
            <div class="Banner">
                <img src="../src/img/teste3.jpg" alt="banner" />
                <h1>GoHabit: <br />sua rotina mais leve <br />e saudável.</h1>
            </div>

            <main>
                <h2>Sobre o Projeto</h2>
                <p>De acordo com uma pesquisa feita pela  Fundação Oswaldo Cruz, a maior instituição de pesquisa biomédica da América latina, 56% dos brasileiros estão em sobrepeso ou obesos. Levando esta estatística em consideração, será desenvolvido uma aplicação para criação de metas e hábitos saudáveis de alimentação, sono, e exercícios físicos com foco principal na realização de tais atividades em grupo, tendo em vista que diversos estudos apontam que a mudança de hábitos, quando realizadas em equipe, aumentam o desempenho individual dos integrantes.
                </p>
                <p>O projeto terá acesso mobile para dispositivos Android, para que o maior número de pessoas possa ter acesso, tendo prazo de conclusão em Junho de 2025. Para manter a qualidade da aplicação as etapas a serem implementadas são Criação do plano de ação, Realização das ações necessárias, e  Manutenção da aplicação, tendo como uma das metas a elaboração de um MVP (Mínimo Produto Viável) para confirmar se o produto final conseguirá atingir os resultados esperados. Relacionado a gastos de produção, espera que os únicos gastos sejam com a licença para publicar na loja Play Store e com a hospedagem do site.
                </p>
                
                <h2>Parceiros</h2>
                <div id="perfil">
                    <section>
                        <img src="../src/img/Rucks-House.png" alt="Logo da Marca Ruck's House" />
                        <article>
                            <h3>Ruck's House</h3>
                            <p>A Ruck's House nasceu do sonho de um casal que superou problemas de saúde ao adotar uma alimentação natural e equilibrada. Hoje, a loja oferece produtos orgânicos, integrais e sem aditivos, promovendo uma vida mais saudável para todos.</p>
                            <h4>Usuários ativos do GoHabit têm descontos exclusivos em nossos produtos naturais e ainda podem montar kits semanais para manter a alimentação no ritmo certo.</h4>
                        </article>
                    </section>
                    <section style={{backgroundColor: 'rgb(54, 54, 54)'}}>
                        <img src="../src/img/ModaFit.png" alt="Logo da Marca ModaFit" />
                        <article>
                            <h3>ModaFit</h3>
                            <p>A ModaFit surgiu da paixão por movimento e natureza de um grupo de jovens atletas que buscavam roupas confortáveis sem agredir o meio ambiente. Inconformados com a poluição da indústria têxtil, criaram uma marca de moda fitness com tecidos reciclados, produção local e impacto ambiental reduzido.</p>
                            <h4>Treinou com frequência no GoHabit? Ganhe descontos em roupas fitness sustentáveis e acesse coleções criadas para quem vive o movimento com saúde.</h4>
                        </article>
                    </section>
                    <section>
                        <img src="../src/img/ZenMove.png" alt="Logo da Marca ZenMove" />
                        <article>
                            <h3>ZenMove</h3>
                            <p>Criada por uma fisioterapeuta e um educador físico, a ZenMove une práticas como yoga, pilates e funcional para promover o equilíbrio corpo-mente. A missão é incentivar o movimento como ferramenta de transformação pessoal.</p>
                            <h4>Se você está entre os destaques do GoHabit, suas aulas mensais são por nossa conta! E se é assinante da plataforma, aproveite nossos planos com valor especial.</h4>
                        </article>
                    </section>
                </div>
            </main>
            <Rodape />
        </div>
    )
}