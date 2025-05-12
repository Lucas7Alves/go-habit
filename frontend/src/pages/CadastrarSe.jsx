import React, { useState } from "react";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { auth, db } from "../firebase/firebaseConfig";
import { doc, setDoc } from "firebase/firestore";
import { useNavigate } from "react-router-dom";
import { Box, TextField, Button, Paper } from "@mui/material";
import HorizontalNonLinearStepper from "../components/HorizontalNonLinearStepper";

const CadastrarSe = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [nome, setNome] = useState("");
  const [bio, setBio] = useState("");
  const [step, setStep] = useState(0);
  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      const userCredential = await createUserWithEmailAndPassword(auth, email, senha);
      const user = userCredential.user;

      // Salva nome, bio e email no Firestore
      await setDoc(doc(db, "users", user.uid), {
        nome: nome,
        bio: bio,
        email: email,
        pontos: 0, // inicializa pontos, caso use gamificação
      });

      alert("Cadastro realizado com sucesso!");
      navigate("/login");
    } catch (error) {
      alert("Erro ao cadastrar: " + error.message);
    }
  };

  const handleNextStep = () => {
    setStep((prevStep) => prevStep + 1);
  };

  const handleBackStep = () => {
    setStep((prevStep) => prevStep - 1);
  };

  return (
    <Box
      sx={{
        flexGrow: 1,
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        backgroundImage: "url('../src/img/3380640.jpg')",
        backgroundSize: "cover",
        backgroundPosition: "center",
        backgroundRepeat: "no-repeat",
      }}
    >
      <Paper elevation={6} sx={{ p: 5, textAlign: "center", width: "400px" }}>
        <HorizontalNonLinearStepper
          step={step}
          handleNextStep={handleNextStep}
          handleBackStep={handleBackStep}
        />

        {step === 0 && (
          <>
            <TextField
              label="Email"
              variant="standard"
              fullWidth
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              sx={{
                mb: 2,
                "& .MuiInput-underline:after": {
                  borderBottomColor: "#996AF9",
                },
                "& .MuiInputLabel-root.Mui-focused": {
                  color: "#996AF9",
                },
              }}
            />
            <TextField
              label="Senha"
              type="password"
              variant="standard"
              fullWidth
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              sx={{
                mb: 3,
                "& .MuiInput-underline:after": {
                  borderBottomColor: "#996AF9",
                },
                "& .MuiInputLabel-root.Mui-focused": {
                  color: "#996AF9",
                },
              }}
            />
          </>
        )}

        {step === 1 && (
          <>
            <TextField
              label="Nome"
              variant="standard"
              fullWidth
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              sx={{
                mb: 2,
                "& .MuiInput-underline:after": {
                  borderBottomColor: "#996AF9",
                },
                "& .MuiInputLabel-root.Mui-focused": {
                  color: "#996AF9",
                },
              }}
            />
            <TextField
              label="Bio"
              variant="standard"
              fullWidth
              value={bio}
              onChange={(e) => setBio(e.target.value)}
              sx={{
                mb: 3,
                "& .MuiInput-underline:after": {
                  borderBottomColor: "#996AF9",
                },
                "& .MuiInputLabel-root.Mui-focused": {
                  color: "#996AF9",
                },
              }}
            />
          </>
        )}

        {step === 1 && (
          <Button
            variant="contained"
            fullWidth
            sx={{
              backgroundColor: "#996AF9",
              marginTop: "20px",
              "&:hover": { backgroundColor: "#7c4de3" },
            }}
            onClick={handleRegister}
          >
            Criar Conta
          </Button>
        )}
      </Paper>
    </Box>
  );
};

export default CadastrarSe;