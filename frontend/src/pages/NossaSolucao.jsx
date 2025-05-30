import Rodape from '../components/Rodape.jsx';

export default function NossaSolucao() {
    return (
        <div id="main">
            <div class="Banner">
                    <img src="../src/img/teste3.jpg" alt="banner" />
                    <h1>Entendendo o Projeto</h1>
            </div>
            
            <main>
                <h2 class="fundo">Problemática</h2>
                <p>Segundo a Fiocruz, 56% dos brasileiros estão com sobrepeso ou obesidade, reflexo da dificuldade em manter hábitos saudáveis como boa alimentação, exercícios e sono adequado. Fatores como sedentarismo, alimentação inadequada, rotina estressante e falta de apoio coletivo contribuem para esse cenário. É essencial promover soluções que incentivem e facilitem a adoção de rotinas saudáveis.</p>
                
                <h2 class="fundo">Solução</h2>
                <p>Uma aplicação mobile com o objetivo de incentivar grupos de amigos a desenvolver e manter hábitos saudáveis, por meio da definição de metas personalizadas e desafios semanais. A ideia é promover motivação mútua e engajamento contínuo, transformando a construção de uma rotina saudável em uma experiência coletiva, divertida e sustentável.</p>
            </main>
            <Rodape />
        </div>
    )
}