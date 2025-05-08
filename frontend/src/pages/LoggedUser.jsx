import { useEffect, useState } from "react";
import UserProfile from "../components/UserProfile";
import Relatorio from "../components/Relatorio";
import { userDataMock } from "../backendMock/UserDataMock"; // troca pela API real depois

export default function LoggedUser() {
  const [userData, setUserData] = useState(null);

  useEffect(() => {
    // Substituir futuramente com uma chamada real ao backend
    setTimeout(() => {
      setUserData(userDataMock);
    }, 300); // simula carregamento
  }, []);

  if (!userData) return <p>Carregando...</p>;

  return (
    <div style={{ padding: "2rem", color: 'black' }}>
      <UserProfile name={userData.name} avatar={userData.avatar} />
      <Relatorio goals={userData.weekly_goals} />
    </div>
  );
}
