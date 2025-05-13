import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Avatar from '@mui/material/Avatar';
import { Link, useNavigate } from 'react-router-dom';

import { useAuthState } from "react-firebase-hooks/auth";
import { auth, db } from "../firebase/firebaseConfig";
import { signOut } from "firebase/auth";
import { doc, getDoc } from "firebase/firestore";

export default function ButtonAppBar() {
  const [user] = useAuthState(auth);
  const [userData, setUserData] = React.useState(null);
  const navigate = useNavigate();
  const [hasRedirected, setHasRedirected] = React.useState(false); // evita múltiplos redirecionamentos

  React.useEffect(() => {
    const fetchUserData = async () => {
      if (user) {
        const userRef = doc(db, "users", user.uid);
        const userSnap = await getDoc(userRef);
        if (userSnap.exists()) {
          const data = {
            name: userSnap.data().nome,
            avatar: userSnap.data().avatar || "https://www.w3schools.com/w3images/avatar2.png"
          };
          setUserData(data);

          // Redireciona após login se ainda não redirecionou
          if (!hasRedirected) {
            navigate("/LoggedUser");
            setHasRedirected(true);
          }
        }
      } else {
        setUserData(null);
        setHasRedirected(false);
      }
    };
    fetchUserData();
  }, [user, navigate, hasRedirected]);

  const handleLogout = async () => {
    await signOut(auth);
    navigate("/"); // redireciona para Início após logout
  };

  return (
    <Box sx={{ width: '100%', m: 0, p: 0 }}>
      <AppBar position="static" sx={{ width: '100%', paddingLeft: '3vw', paddingRight: '3vw', backgroundColor: '#996AF9', zIndex: 1100 }}>
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1, textAlign: 'left', fontWeight: 'bold' }}>
            GoHabit
          </Typography>

          <Button color="inherit" component={Link} to="/">Início</Button>
          <Button color="inherit" component={Link} to="/quem-somos">Quem Somos</Button>
          <Button color="inherit" component={Link} to="/nossa-solucao">Nossa Solução</Button>

          {user ? (
            <>
              <Button color="inherit" component={Link} to="/LoggedUser">Meus Perfil</Button>
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, ml: 2 }}>
                <Avatar alt={userData?.name} src={userData?.avatar} sx={{ width: 32, height: 32 }} />
                <Typography variant="body1">{userData?.name}</Typography>
              </Box>
              <Button color="inherit" onClick={handleLogout}>Sair</Button>
            </>
          ) : (
            <>
              <Button color="inherit" component={Link} to="/login">Login</Button>
              <Button color="inherit" component={Link} to="/cadastrar-se">Cadastrar-se</Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}