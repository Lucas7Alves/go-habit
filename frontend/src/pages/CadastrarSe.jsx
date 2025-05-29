import React, { useState } from "react";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { auth, db } from "../firebase/firebaseConfig";
import { doc, setDoc } from "firebase/firestore";
import { useNavigate } from "react-router-dom";
import {
  Box,
  TextField,
  Button,
  Paper,
  FormControlLabel,
  Checkbox,
  Typography,
  Link,
} from "@mui/material";
import HorizontalNonLinearStepper from "../components/HorizontalNonLinearStepper";

const CadastrarSe = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [nome, setNome] = useState("");
  const [bio, setBio] = useState("");
  const [step, setStep] = useState(0);
  const [avatar, setAvatar] = useState("");
  const [termosAceitos, setTermosAceitos] = useState(false);
  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      const userCredential = await createUserWithEmailAndPassword(auth, email, senha);
      const user = userCredential.user;

      await setDoc(doc(db, "users", user.uid), {
        nome: nome,
        bio: bio,
        email: email,
        avatar: avatar,
      });

      alert("Cadastro realizado com sucesso!");
      navigate("/LoggedUser");
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
      <Paper elevation={6} sx={{ p: 5, textAlign: "center", width: "400px", margin: "40px" }}>
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
                "& .MuiInput-underline:after": { borderBottomColor: "#996AF9" },
                "& .MuiInputLabel-root.Mui-focused": { color: "#996AF9" },
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
                "& .MuiInput-underline:after": { borderBottomColor: "#996AF9" },
                "& .MuiInputLabel-root.Mui-focused": { color: "#996AF9" },
              }}
            />
          </>
        )}

        {step === 1 && (
          <>
            <Box sx={{ mb: 3 }}>
              <p>Escolha seu avatar:</p>
              <Box
                sx={{
                  display: "grid",
                  gridTemplateColumns: "repeat(5, 1fr)",
                  gap: 2,
                  justifyItems: "center",
                }}
              >
                {[
                  "avatar1.svg", "avatar2.svg", "avatar3.svg", "avatar4.svg", "avatar5.svg",
                  "avatar6.svg", "avatar7.svg", "avatar8.svg", "avatar9.svg", "avatar10.svg"
                ].map((img, index) => (
                  <Box
                    key={index}
                    component="img"
                    src={`/src/img/avatars/${img}`}
                    alt={`Avatar ${index + 1}`}
                    onClick={() => setAvatar(img)}
                    sx={{
                      width: 60,
                      height: 60,
                      borderRadius: "50%",
                      border: avatar === img ? "2px solid #996AF9" : "2px solid #ccc",
                      cursor: "pointer",
                      transition: "0.3s",
                    }}
                  />
                ))}
              </Box>
            </Box>

            <TextField
              label="Nome"
              variant="standard"
              fullWidth
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              sx={{
                mb: 2,
                "& .MuiInput-underline:after": { borderBottomColor: "#996AF9" },
                "& .MuiInputLabel-root.Mui-focused": { color: "#996AF9" },
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
                "& .MuiInput-underline:after": { borderBottomColor: "#996AF9" },
                "& .MuiInputLabel-root.Mui-focused": { color: "#996AF9" },
              }}
            />

            <FormControlLabel
              control={
                <Checkbox
                  checked={termosAceitos}
                  onChange={(e) => setTermosAceitos(e.target.checked)}
                  sx={{ color: "#996AF9", "&.Mui-checked": { color: "#996AF9" } }}
                />
              }
              label={
                <Typography variant="body2" style={{display: "flex", textIndent: "0", gap: "7px"}}>
                  Eu li e aceito os{" "}
                  <Link
                    href="/termos-de-uso"
                    target="_blank"
                    rel="noopener"
                    underline="hover"
                    sx={{ color: "#996AF9", fontWeight: "bold", "&:hover":{color: "#996AF9"}}}
                  >
                    Termos de Uso
                  </Link>
                </Typography>
              }
              sx={{ mt: 1, mb: 2, textAlign: "left" }}
            />

            <Button
              variant="contained"
              fullWidth
              disabled={!termosAceitos}
              sx={{
                backgroundColor: termosAceitos ? "#996AF9" : "#ccc",
                marginTop: "20px",
                "&:hover": {
                  backgroundColor: termosAceitos ? "#7c4de3" : "#ccc",
                  color: termosAceitos ? "rgb(233, 222, 73)" : "inherit",
                },
              }}
              onClick={handleRegister}
            >
              Criar Conta
            </Button>
          </>
        )}
      </Paper>
    </Box>
  );
};

export default CadastrarSe;
