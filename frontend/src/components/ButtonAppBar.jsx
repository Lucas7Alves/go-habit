import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { Link } from 'react-router-dom';

import { useAuthState } from "react-firebase-hooks/auth";
import { auth } from "../firebase/firebaseConfig";
import { signOut } from "firebase/auth";

export default function ButtonAppBar() {
  const [user] = useAuthState(auth);

  return (
    <Box sx={{ width: '100%', m: 0, p: 0}}>
      <AppBar
        position="static" 
        sx={{
          width: '100%',
          paddingLeft: '3vw',
          paddingRight: '3vw',
          backgroundColor: '#996AF9',
          zIndex: 1100,  
        }}
      >
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1, textAlign: 'left', fontWeight: 'bold' }}>
            GoHabit
          </Typography>

          <Button color="inherit" 
          sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }} 
          component={Link} to="/">Início</Button>

          <Button color="inherit" 
          sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }} 
          component={Link} to="/quem-somos">Quem Somos</Button>

          <Button color="inherit" 
          sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }} 
          component={Link} to="/nossa-solucao">Nossa Solução</Button>

          {user ? (
            <Button
              color="inherit"
              onClick={() => signOut(auth)}
              sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)' } }}
              >Sair</Button>
          ) : (
            <>
              <Button
                color="inherit"
                sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }}
                component={Link} to="/login">Login</Button>

              <Button
                color="inherit"
                sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }}
                component={Link} to="/cadastrar-se">Cadastrar-se</Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}