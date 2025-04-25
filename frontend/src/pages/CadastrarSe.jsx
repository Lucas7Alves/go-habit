// src/pages/CadastrarSe.jsx
import React, { useState } from "react";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { auth } from "../firebase/firebaseConfig";
import { useNavigate } from "react-router-dom";
import { Box, TextField, Button, Typography, Paper } from "@mui/material";

const CadastrarSe = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      await createUserWithEmailAndPassword(auth, email, senha);
      alert("Cadastro realizado com sucesso!");
      navigate("/login");
    } catch (error) {
      alert("Erro ao cadastrar: " + error.message);
    }
  };

  return (
    <Box
      sx={{
        minHeight: "90vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        background: "linear-gradient(to right, #ffffff, #f1e7ff)",
        padding: 2,
        paddingTop: "80px",  
      }}
    >
      <Paper elevation={6} sx={{ p: 5, maxWidth: 400, width: "100%", textAlign: "center" }}>
        <Typography variant="h5" sx={{ mb: 2, fontWeight: "bold", color: "#996AF9" }}>
          Cadastrar-se
        </Typography>
        <TextField
          label="Email"
          variant="outlined"
          fullWidth
          sx={{ mb: 2 }}
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />
        <TextField
          label="Senha"
          type="password"
          variant="outlined"
          fullWidth
          sx={{ mb: 3 }}
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />
        <Button
          variant="contained"
          fullWidth
          sx={{ backgroundColor: "#996AF9", "&:hover": { backgroundColor: "#7c4de3" } }}
          onClick={handleRegister}
        >
          Criar Conta
        </Button>
      </Paper>
    </Box>
  );
};

export default CadastrarSe;
