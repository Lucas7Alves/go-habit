import React, { useState, useEffect } from "react";
import { Fab } from "@mui/material";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";

export default function BotaoTopo() {
  const [visivel, setVisivel] = useState(false);

  useEffect(() => {
    const aoRolar = () => {
      setVisivel(window.scrollY > 300);
    };

    window.addEventListener("scroll", aoRolar);
    return () => window.removeEventListener("scroll", aoRolar);
  }, []);

  const voltarAoTopo = () => {
    window.scrollTo({ top: 0, behavior: "smooth" });
  };

  return (
    visivel && (
      <Fab
        color="primary"
        size="small"
        onClick={voltarAoTopo}
        sx={{
          position: "fixed",
          bottom: 16,
          right: 16,
          zIndex: 1000,
          bgcolor: "#996AF9",
          "&:hover": { bgcolor: "#7c4de3" }
        }}
      >
        <KeyboardArrowUpIcon />
      </Fab>
    )
  );
}