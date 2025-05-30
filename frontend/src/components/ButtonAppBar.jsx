import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';

import { Link, useNavigate } from 'react-router-dom';
import { useTheme } from '@mui/material/styles';
import useMediaQuery from '@mui/material/useMediaQuery';

import { useAuthState } from "react-firebase-hooks/auth";
import { auth, db } from "../firebase/firebaseConfig";
import { signOut } from "firebase/auth";
import { doc, getDoc } from "firebase/firestore";

export default function ButtonAppBar() {
  const [user] = useAuthState(auth);
  const [userData, setUserData] = React.useState(null);
  const navigate = useNavigate();
  const [hasRedirected, setHasRedirected] = React.useState(false);
  
  const theme = useTheme();
  const isMobile = useMediaQuery('(max-width:767px)');

  const [drawerOpen, setDrawerOpen] = React.useState(false);

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
    navigate("/");
  };

  const toggleDrawer = (open) => () => {
    setDrawerOpen(open);
  };

  // Links comuns (Início, Quem Somos, Nossa Solução)
  const navLinks = [
    { label: 'Início', to: '/' },
    { label: 'Quem Somos', to: '/quem-somos' },
    { label: 'Nossa Solução', to: '/nossa-solucao' },
  ];

  return (
    <Box sx={{ width: '100%', m: 0, p: 0 }}>
      <AppBar position="static" sx={{ width: '100%', paddingLeft: '3vw', paddingRight: '3vw', backgroundColor: '#996AF9', zIndex: 1100 }}>
        <Toolbar>
          <Box sx={{ display: 'flex', alignItems: 'center', flexGrow: 1 }}>
            <img id='logotipo' src="../src/img/GoHabit_logo_flatten.svg" alt="Logo GoHabit" style={{ height: '40px', marginRight: '8px' }} />
            <Typography variant="h6" component="div" sx={{ textAlign: 'left', fontWeight: 'bold' }}>
              GoHabit
            </Typography>
          </Box>

          {isMobile ? (
            <>
              <IconButton
                color="inherit"
                aria-label="menu"
                edge="start"
                onClick={toggleDrawer(true)}
                sx={{ mr: 2 }}
              >
                <MenuIcon />
              </IconButton>

              <Drawer
                anchor="right"
                open={drawerOpen}
                onClose={toggleDrawer(false)}
                PaperProps={{
                  sx: {backgroundColor: "#996AF9", color: "white"}
                }}
              >
                <Box
                  sx={{ width: 250 }}
                  role="presentation"
                  onClick={toggleDrawer(false)}
                  onKeyDown={toggleDrawer(false)}
                >
                  <List>
                    {navLinks.map(({ label, to }) => (
                      <ListItem key={label} disablePadding>
                        <ListItemButton component={Link} to={to}>
                          <ListItemText primary={label} />
                        </ListItemButton>
                      </ListItem>
                    ))}

                    {user ? (
                      <>
                        <ListItem disablePadding>
                          <ListItemButton component={Link} to="/LoggedUser">
                            <ListItemText primary="Meu Perfil" />
                          </ListItemButton>
                        </ListItem>
                        <ListItem disablePadding>
                          <ListItemButton onClick={handleLogout}>
                            <ListItemText primary="Sair" />
                          </ListItemButton>
                        </ListItem>
                      </>
                    ) : (
                      <>
                        <ListItem disablePadding>
                          <ListItemButton component={Link} to="/login">
                            <ListItemText primary="Login" />
                          </ListItemButton>
                        </ListItem>
                        <ListItem disablePadding>
                          <ListItemButton component={Link} to="/cadastrar-se">
                            <ListItemText primary="Cadastrar-se" />
                          </ListItemButton>
                        </ListItem>
                      </>
                    )}
                  </List>
                </Box>
              </Drawer>
            </>
          ) : (
            <>
              {navLinks.map(({ label, to }) => (
                <Button key={label} color="inherit" component={Link} to={to}>
                  {label}
                </Button>
              ))}

              {user ? (
                <>
                  <Button color="inherit" component={Link} to="/LoggedUser">Meu Perfil</Button>
                  <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, ml: 2 }}>
                    <Avatar alt={userData?.name} src={userData?.avatar} sx={{ width: 32, height: 32 }} />
                    <Typography sx={{textIndent: '0px', marginRight: "15px"}} variant="body1">{userData?.name}</Typography>
                  </Box>
                  <Button color="inherit" onClick={handleLogout}>Sair</Button>
                </>
              ) : (
                <>
                  <Button color="inherit" component={Link} to="/login">Login</Button>
                  <Button color="inherit" component={Link} to="/cadastrar-se">Cadastrar-se</Button>
                </>
              )}
            </>
          )}
        </Toolbar>
      </AppBar>
    </Box>
  );
}