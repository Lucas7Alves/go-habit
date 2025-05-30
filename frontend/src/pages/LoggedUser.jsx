import { useEffect, useState } from "react";
import UserProfile from "../components/UserProfile";
import Relatorio from "../components/Relatorio";
import MeuGrafico from "../components/MeuGrafico";
import { auth, db } from "../firebase/firebaseConfig";
import { doc, getDoc } from "firebase/firestore";
import { onAuthStateChanged } from "firebase/auth";
import Rodape from '../components/Rodape.jsx';

export default function LoggedUser() {
  const [userData, setUserData] = useState(null);
  const [relatorioTexto, setRelatorioTexto] = useState("");

  const avatarImages = [
    "/avatars/Brown_boy.svg",
    "/avatars/Brown_girl.svg",
    "/avatars/Black_boy.svg",
    "/avatars/Black_girl.svg",
    "/avatars/Malhado_boy.svg",
    "/avatars/Malhado_girl.svg",
    "/avatars/White_boy.svg",
    "/avatars/White_girl.svg",
  ];

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, async (user) => {
      if (user) {
        const userRef = doc(db, "users", user.uid);
        const userSnap = await getDoc(userRef);

        if (userSnap.exists()) {
          const data = userSnap.data();
          const avatarIndex = data.avatarIndex || 0; // fallback para o primeiro avatar
          setUserData({
            name: data.nome || "Usuário",
            avatar: avatarImages[avatarIndex] || avatarImages[0],
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

        const response = await fetch(`http://localhost:5000/relatorio`);
        const data = await response.json();
        setRelatorioTexto(data.relatorio);
      } else {
        console.log("Usuário não autenticado.");
      }
    });

    return () => unsubscribe();
  }, []);

  if (!userData) return <p>Carregando...</p>;

  return (
    <div style={{ padding: "2rem", paddingBottom:0, color: "black", display: "flex", flexDirection:"column", alignItems:"center" }}>
      <UserProfile name={userData.name} avatar={userData.avatar} />
      <MeuGrafico />
      <Relatorio goals={userData.weekly_goals} texto={relatorioTexto} />
    </div>
    
  );
  
}
