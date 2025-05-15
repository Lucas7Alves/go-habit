import React, { useEffect, useState } from "react";

export default function Relatorio({ goals }) {
  const [relatorioIA, setRelatorioIA] = useState("");
  const [erro, setErro] = useState("");

  // Chamar backend para buscar o relatório gerado pela IA
  useEffect(() => {
    fetch("http://localhost:5000/relatorio") // Ajuste se for em produção
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

  // Função para gerar o relatório com base nas metas
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

  return (
    <div
      style={{
        width: "70vw",
        borderRadius: "10px",
        margin: "0 auto",
        paddingBottom: "55px",
        backgroundImage: "url('../src/img/3380640.jpg')",
        backgroundSize: "cover",
        backgroundPosition: "center",
        backgroundRepeat: "no-repeat",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        gap: "1rem",
      }}
    >
      <h3
        style={{
          marginBottom: "20px",
          padding: "3px 7px",
          fontSize: "40px",
          color: "#f6f6f6",
          background: "#4427AF",
          width: "auto",
          border: "6px solid #ffffff",
          borderRadius: "10px",
        }}
      >
        Relatório de Metas
      </h3>

      <div
        style={{
          display: "flex",
          width: "auto",
          fontSize: "25px",
          flexDirection: "column",
          alignItems: "center",
        }}
      >
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
                      width: "auto",
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

        {/* Relatório da IA */}
        {relatorioIA && (
          <div
            style={{
              marginTop: "30px",
              backgroundColor: "#ffffffbb",
              borderRadius: "10px",
              padding: "20px",
              maxWidth: "90%",
              textAlign: "center",
              color: "#222",
              fontWeight: "normal",
            }}
          >
            <h4 style={{ color: "#4427AF", marginBottom: "10px" }}>
              Análise da Semana (IA)
            </h4>
            {relatorioIA.split("\n").map((linha, idx) => (
              <p key={idx} style={{ marginBottom: "8px" }}>
                {linha}
              </p>
            ))}
          </div>
        )}

        {/* Erro (se houver) */}
        {erro && (
          <p style={{ color: "red", marginTop: "20px" }}>
            Erro ao carregar análise da IA: {erro}
          </p>
        )}
      </div>

      <ul
        style={{
          textDecoration: "none",
          listStyle: "none",
          paddingLeft: "0",
          fontSize: "25px",
        }}
      >
        {goals.map((item, idx) => (
          <li
            key={idx}
            style={{
              display: "flex",
              justifyContent: "space-between",
              backgroundColor: "#00000033",
              color: "#ffffff",
              borderRadius: "5px",
              fontWeight: "bold",
              marginBottom: "15px",
              padding: "10px",
            }}
          >
            {item.goal}:
            <strong
              id="status"
              style={{
                color:
                  item.status === "pendente" ? "#FF1751" : "#7DFF37",
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
