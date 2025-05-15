import React, { useEffect, useState } from 'react';

function RelatorioSemanal() {
  const [relatorio, setRelatorio] = useState('');
  const [erro, setErro] = useState('');

  useEffect(() => {
    fetch('http://localhost:5000/relatorio') // Ajuste a URL se necessário
      .then(res => res.json())
      .then(data => {
        if (data.relatorio) {
          setRelatorio(data.relatorio);
        } else {
          setErro(data.erro || 'Erro desconhecido');
        }
      })
      .catch(err => setErro(err.message));
  }, []);

  return (
    <div>
      <h2>Relatório Semanal</h2>
      {erro && <p style={{ color: 'red' }}>{erro}</p>}
      <pre>{relatorio}</pre>
    </div>
  );
}

export default RelatorioSemanal;
