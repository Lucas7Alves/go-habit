import { useEffect, useState } from "react";
import UserProfile from "../components/UserProfile";
import Relatorio from "../components/Relatorio";
import MeuGrafico from "../components/MeuGrafico";
import { auth, db } from "../firebase/firebaseConfig"; // Importar auth e db do Firebase
import { doc, getDoc } from "firebase/firestore";
import { onAuthStateChanged } from "firebase/auth";
import Rodape from '../components/Rodape.jsx';

export default function LoggedUser() {
  const [userData, setUserData] = useState(null);

  useEffect(() => {
    // Observando o estado de autenticação do usuário
    const unsubscribe = onAuthStateChanged(auth, async (user) => {
      console.log("User Authenticated: ", user);
      if (user) {
        // Obtendo os dados do usuário no Firestore
        const userRef = doc(db, "users", user.uid); 
        const userSnap = await getDoc(userRef);

        if (userSnap.exists()) {
          console.log("Dados do Firestore: ", userSnap.data());
          // Se o usuário existir no Firestore, define os dados do usuário no estado
          setUserData({
            name: userSnap.data().nome,  // Nome do Firestore
            avatar: userSnap.data().avatar || "https://www.w3schools.com/w3images/avatar2.png", 
            weekly_goals: [
              {
      "goal_id": "goal1",
      "goal_description": "Beber 10L de água",
      "week_code": "18",
      "completed_at": "2024-05-06T15:30:00Z",
      "status": "concluída"
    },
    {
      "goal_id": "goal2",
      "goal_description": "Treinar 1h diária",
      "week_code": "18",
      "completed_at": null,
      "status": "pendente"
    },
    {
      "goal_id": "goal3",
      "goal_description": "Caminhar 5km",
      "week_code": "18",
      "completed_at": null,
      "status": "pendente"
    }
            ], // Metas semanais, se necessário
          });
        } else {
          console.log("Usuário não encontrado no Firestore.");
        }
      } else {
        console.log("Usuário não autenticado.");
      }

      const response = await fetch(`http://localhost:5000/relatorio`);
          const data = await response.json();
          setRelatorioTexto(data.relatorio); // Salva o texto do relatório no estado
    });

    // Limpar a subscrição quando o componente for desmontado
    return () => unsubscribe();
  }, []);

  // Se os dados do usuário ainda não estiverem carregados
  if (!userData) return <p>Carregando...</p>;

  return (
    <div style={{ padding: "2rem", paddingBottom:0, color: "black", display: "flex", flexDirection:"column", alignItems:"center" }}>
      <UserProfile name={userData.name} avatar={userData.avatar} />
      <MeuGrafico />
      <Relatorio goals={userData.weekly_goals} />
      <Rodape />
      
    </div>
    
  );
  
}
