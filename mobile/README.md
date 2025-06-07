# GoHabit - Aplicativo de Controle de Metas de Hábitos em Grupo

**Instituição:** Centro Universitário Tiradentes
**Curso:** Análise e Desenvolvimento de Sistemas  
**Período:** 5º Período  
**Disciplina:** Desenvolvimento Mobile  
**Professor:** Diógenes Carvalho Matias  

## 📱 Nome do Projeto
**GoHabit**

## 🎯 Objetivo
O objetivo deste projeto é aplicar os conhecimentos de programação mobile adquiridos durante a disciplina, desenvolvendo um aplicativo funcional utilizando Java e Firebase no Android Studio. O aplicativo permite que usuários criem e gerenciem metas de hábitos em grupo, com foco em motivação coletiva e frequência semanal.

## 🛠 Tecnologias Utilizadas
- **Java** (linguagem principal)
- **Firebase Authentication** (login com e-mail e senha)
- **Firebase Firestore** (armazenamento de dados em nuvem)
- **Android Studio** (IDE de desenvolvimento)
- **Material Design** (para UI/UX)

## ▶️ Como Executar o App

1. Clone o repositório ou baixe o código-fonte na pasta `src/`.
2. Abra o projeto no **Android Studio**.
3. Configure o Firebase:
   - Crie um projeto no [Firebase Console](https://console.firebase.google.com/)
   - Ative a autenticação por e-mail/senha.
   - Adicione o arquivo `google-services.json` na pasta `app/`.
4. Conecte um dispositivo físico ou use o emulador.
5. Clique em **Run (▶️)** para compilar e executar o app.

## 👥 Integrantes do Grupo

- **Ana Beatriz Gonçalo de Oliveira**  
  RA: 1241301988  
- **João José Galdino da Silva Júnior**  
  RA: 1241302917  
- **Leon de França Albuquerque Trindade**  
  RA: 1241303085  
- **Lucas Alves da Silva**  
  RA: 1241303107  
- **Wellerson Paulo Morais da Silva**  
  RA: 1241303883

## 📂 Estrutura do Repositório

```yaml
gohabit-app/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/souunit/gohabit/  # Código-fonte (Activities, Fragments, Adapters etc.)
│   │   │   ├── res/                       # Recursos (layouts XML, imagens, strings)
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── docs/
│   └── README.md                         # Arquivo de instruções e informações
├── diagrams/
│   ├── diagrama.pdf                   # Diagrama Entidade-Relacionamento
├── firebase/
│   └── google-services.json              # Configuração do Firebase (não subir no Git público)
├── .gitignore
├── LICENSE
└── build.gradle                          # Configuração geral do projeto
```
