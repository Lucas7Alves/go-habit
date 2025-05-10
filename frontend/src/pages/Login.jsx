import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { signInWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebase/firebaseConfig";
import { Box, TextField, Button, Typography, Paper } from "@mui/material";

const Login = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const userCredential = await signInWithEmailAndPassword(auth, email, senha);
      const token = await userCredential.user.getIdToken();
      localStorage.setItem("token", token);
      navigate("/LoggedUser"); 
    } catch (error) {
      alert("Erro ao fazer login: " + error.message);
    }
  };

  return (
    <Box
      sx={{
        height: "100vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundImage: "url('../src/img/3380640.jpg')",
        backgroundSize: "cover",
        backgroundPosition: "center",
        backgroundRepeat: "no-repeat",
      }}
    >
      <Paper elevation={6} sx={{ p: 5, maxWidth: 400, width: "100%", textAlign: "center" }}>
        <Typography variant="h5" sx={{ mb: 2, fontWeight: "bold", color: "#996AF9" }}>
          Login
        </Typography>
        <TextField
          label="Email" variant="standard" fullWidth 
          sx={{ mb: 2 }}
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <TextField
          label="Senha" type="password" variant="standard" fullWidth 
          sx={{ mb: 3 }}
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />
        <Button
          variant="contained" fullWidth 
          sx={{ backgroundColor: "#996AF9", marginTop: "20px", "&:hover": { backgroundColor: "#7c4de3"} }}
          onClick={handleLogin}>Entrar</Button>
      </Paper>
    </Box>
  );
};

export default Login;