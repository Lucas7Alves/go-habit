import React, { useEffect, useState } from "react";
import useMediaQuery from "./UseMediaQuery"; // ajusta a responsividade da página

export default function Relatorio({ goals }) {
  const [relatorioIA, setRelatorioIA] = useState("");
  const [erro, setErro] = useState("");

  const isMobile = useMediaQuery("(max-width: 767px)");
  const isTablet = useMediaQuery("(min-width: 768px) and (max-width: 1023px)");
  const isDesktop = useMediaQuery("(min-width: 1024px)");

  useEffect(() => {
    fetch("http://localhost:5000/relatorio")
      .then((res) => res.json())
      .then((data) => {
        if (data.relatorio) {
          setRelatorioIA(data.relatorio);
        } else {
          setErro(data.erro || "Erro desconhecido");
        }
      })
      .catch((err) => setErro(err.message));
  }, []);

  const gerarRelatorioTexto = (goals) => {
    const total = goals.length;
    const concluidas = goals.filter((g) => g.status === "concluída").length;
    const pendentes = goals.filter((g) => g.status === "pendente").length;

    return `
      Você tem um total de ${total} metas semanais:
      ${concluidas} metas foram concluídas.
      ${pendentes} metas estão pendentes.
      Confira abaixo sua lista de metas:
    `;
  };

  const relatorioTexto = gerarRelatorioTexto(goals);

  const containerStyle = {
    width: isMobile ? "95vw" : isTablet ? "85vw" : "70vw",
    margin: "0 auto",
    borderRadius: "10px",
    paddingBottom: "55px",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    gap: "1rem",
  };

  const titleStyle = {
    marginBottom: "20px",
    padding: "3px 7px",
    fontSize: isMobile ? "24px" : isTablet ? "32px" : "40px",
    color: "#f6f6f6",
    background: "#4427AF",
    border: "6px solid #ffffff",
    borderRadius: "10px",
  };

  const relatorioStyle = {
    fontSize: isMobile ? "18px" : "22px",
    maxWidth: isMobile ? "95%" : "90%",
    padding: isMobile ? "15px" : "20px 25px",
  };

  const listItemStyle = {
    display: "flex",
    justifyContent: "space-between",
    backgroundColor: "#00000033",
    color: "#ffffff",
    borderRadius: "5px",
    fontWeight: "bold",
    marginBottom: "15px",
    padding: "10px",
    fontSize: isMobile ? "18px" : "25px",
  };

  return (
    <div style={containerStyle}>
      <h3 style={titleStyle}>Relatório de Metas</h3>

      <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
        {relatorioTexto
          .trim()
          .split("\n")
          .map((linha) => linha.trim())
          .filter((linha) => linha !== "")
          .map((linha, idx) => (
            <p
              key={idx}
              style={
                idx === 3
                  ? {
                      color: "#f6f6f6",
                      fontWeight: "bold",
                      background: "#4427AF",
                      borderRadius: "5px",
                      paddingRight: "20px",
                    }
                  : {
                      fontWeight: "bold",
                      color: "#4427AF",
                    }
              }
            >
              {linha}
            </p>
          ))}

        {relatorioIA && (
          <>
            <h4
              style={{
                color: "#f6f6f6",
                marginTop: "30px",
                fontSize: isMobile ? "22px" : "32px",
                background: "#4427AF",
                borderRadius: "10px",
                padding: "10px 15px",
                border: "4px solid #ffffff",
              }}
            >
              Análise da Semana
            </h4>

            <div
              style={{
                ...relatorioStyle,
                backgroundColor: "rgba(255, 255, 255, 0.85)",
                borderRadius: "10px",
                fontWeight: "bold",
                color: "#4427AF",
                lineHeight: "1.6",
              }}
            >
              {relatorioIA
                .split("\n")
                .map((linha) => linha.trim())
                .filter((linha) => linha !== "")
                .map((linha, idx) => (
                  <p key={idx} style={{ marginBottom: "12px" }}>
                    {linha}
                  </p>
                ))}
            </div>
          </>
        )}

        {erro && (
          <p style={{ color: "red", marginTop: "20px" }}>
            Erro ao carregar análise da IA: {erro}
          </p>
        )}
      </div>

      <ul
        style={{
          listStyle: "none",
          paddingLeft: "0",
        }}
      >
        {goals.map((item, idx) => (
          <li key={idx} style={listItemStyle}>
            {item.goal}:
            <strong
              style={{
                color: item.status === "pendente" ? "#FF1751" : "#7DFF37",
                marginLeft: "10px",
              }}
            >
              {item.status}
            </strong>
          </li>
        ))}
      </ul>
    </div>
  );
}
