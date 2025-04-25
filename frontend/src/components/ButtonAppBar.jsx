// src/components/ButtonAppBar.jsx

import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import { Link } from 'react-router-dom';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';

import { useAuthState } from "react-firebase-hooks/auth";
import { auth } from "../firebase/firebaseConfig";
import { signOut } from "firebase/auth";

export default function ButtonAppBar() {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const [user] = useAuthState(auth);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  return (
    <Box sx={{ width: '100%', m: 0, p: 0, marginBottom: '6vh' }}>
      <AppBar
        position="fixed"  
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

          <Button color="inherit" sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }} component={Link} to="/">Início</Button>
          <Button color="inherit" sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }} component={Link} to="/quem-somos">Quem Somos</Button>
          <Button color="inherit" sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }} component={Link} to="/nossa-solucao">Nossa Solução</Button>

          <Button
            color="inherit"
            sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }}
            onClick={handleClick}
            endIcon={<KeyboardArrowDownIcon />}
          >
            Mais
          </Button>
          <Menu
            anchorEl={anchorEl}
            open={open}
            onClose={handleClose}
            anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
            transformOrigin={{ vertical: 'top', horizontal: 'right' }}
          >
            <MenuItem
              sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }}
              component={Link}
              to="/modelo-de-negocio"
              onClick={handleClose}
            >
              Modelo de Negócio
            </MenuItem>
            <MenuItem
              sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }}
              component={Link}
              to="/etapas-de-desenvolvimento"
              onClick={handleClose}
            >
              Etapas de Desenvolvimento
            </MenuItem>
            <MenuItem
              sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }}
              component={Link}
              to="/documentos"
              onClick={handleClose}
            >
              Documentos
            </MenuItem>
          </Menu>

          {user ? (
            <Button
              color="inherit"
              onClick={() => signOut(auth)}
              sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)' } }}
            >
              Sair
            </Button>
          ) : (
            <>
              <Button
                color="inherit"
                sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }}
                component={Link}
                to="/login"
              >
                Login
              </Button>
              <Button
                color="inherit"
                sx={{ '&:hover': { backgroundColor: 'rgba(109, 39, 175, 0.5)', color: 'inherit' } }}
                component={Link}
                to="/cadastrar-se"
              >
                Cadastrar-se
              </Button>
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}
