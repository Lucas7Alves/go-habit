import { useEffect, useState } from "react";
import UserProfile from "../components/UserProfile";
import Relatorio from "../components/Relatorio";
import MeuGrafico from "../components/MeuGrafico";
import { auth, db } from "../firebase/firebaseConfig";
import { doc, getDoc } from "firebase/firestore";
import { onAuthStateChanged } from "firebase/auth";

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
            weekly_goals: [], // Você pode adaptar isso depois
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
    <div style={{ padding: "2rem", color: "black" }}>
      <UserProfile name={userData.name} avatar={userData.avatar} />
      <MeuGrafico />
      <Relatorio goals={userData.weekly_goals} texto={relatorioTexto} />
    </div>
  );
}
